/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.util;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class GlobalConstantsPartitioner implements STCorePartitioner {
	@Inject
	private GlobalConstantsGrammarAccess grammarAccess;

	@Inject
	private IEObjectDocumentationProvider documentationProvider;

	@Override
	public String combine(final LibraryElement libraryElement) {
		if (libraryElement instanceof final GlobalConstants globalConstants) {
			return combine(globalConstants);
		}
		return ""; //$NON-NLS-1$
	}

	public String combine(final GlobalConstants globalConstants) {
		if (globalConstants.getSource() != null && globalConstants.getSource().getText() != null) {
			return globalConstants.getSource().getText();
		}
		return generateGlobalConstantsText(globalConstants);
	}

	protected String generateGlobalConstantsText(final GlobalConstants globalConstants) {
		return globalConstants.getConstants().stream().map(this::generateVarDeclarationText)
				.collect(Collectors.joining("\n\t", "VAR_GLOBAL CONSTANT\n\t", "\nEND_VAR\n")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected String generateVarDeclarationText(final VarDeclaration decl) {
		if (decl.getValue() != null && decl.getValue().getValue() != null && !decl.getValue().getValue().isBlank()) {
			return "%s : %s := %s;".formatted(decl.getName(), decl.getFullTypeName(), decl.getValue().getValue()); //$NON-NLS-1$
		}
		return "%s : %s;".formatted(decl.getName(), decl.getFullTypeName()); //$NON-NLS-1$
	}

	@Override
	public Optional<? extends STCorePartition> partition(final XtextResource resource) {
		if (resource.getEntryPoint() != null
				&& resource.getEntryPoint() != grammarAccess.getSTGlobalConstsSourceRule()) {
			return Optional.empty();
		}
		final var source = resource.getContents().get(0);
		if (source instanceof final STGlobalConstsSource globalConstsSource) {
			return partition(globalConstsSource);
		}
		return Optional.empty();
	}

	protected Optional<? extends STCorePartition> partition(final STGlobalConstsSource source) {
		try {
			final var node = NodeModelUtils.findActualNodeFor(source);
			final var text = node != null ? node.getText() : null;
			final var imports = source.getImports().stream().map(this::convertSourceElement).filter(Objects::nonNull)
					.collect(Collectors.<Import, EList<Import>>toCollection(ECollections::newBasicEList));
			final var constants = source.getElements().stream().flatMap(block -> block.getVarDeclarations().stream())
					.map(this::convertSourceElement).filter(Objects::nonNull).collect(Collectors
							.<VarDeclaration, EList<VarDeclaration>>toCollection(ECollections::newBasicEList));
			handleDuplicates(constants);
			return Optional.of(new GlobalConstantsPartition(source.getName(), imports, text, constants));
		} catch (final Exception e) {
			return Optional.empty();
		}
	}

	protected Import convertSourceElement(final STImport decl) {
		final var result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(decl.getImportedNamespace());
		return result;
	}

	protected VarDeclaration convertSourceElement(final STVarDeclaration decl) {
		final var node = NodeModelUtils.findActualNodeFor(decl);
		if (node != null && decl.getName() != null) {
			final var result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			result.setName(decl.getName());
			final String comment = documentationProvider.getDocumentation(decl);
			if (comment != null) {
				result.setComment(comment);
			}
			result.setType((DataType) decl.getType());
			if (decl.isArray()) {
				ArraySizeHelper.setArraySize(result, extractArraySize(decl));
			}
			if (decl.getDefaultValue() != null) {
				final Value value = LibraryElementFactory.eINSTANCE.createValue();
				value.setValue(extractDefaultValue(decl));
				result.setValue(value);
			}
			return result;
		}
		return null;
	}

	protected static String extractArraySize(final STVarDeclaration declaration) {
		return NodeModelUtils.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_Ranges())
				.stream().map(INode::getText).collect(Collectors.joining(",")).trim(); //$NON-NLS-1$
	}

	protected static String extractDefaultValue(final STVarDeclaration declaration) {
		return NodeModelUtils
				.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_DefaultValue()).stream()
				.map(INode::getText).collect(Collectors.joining()).trim();
	}

	protected static void handleDuplicates(final EList<VarDeclaration> result) {
		result.stream().collect(Collectors.groupingBy(VarDeclaration::getName)).forEach((name, duplicates) -> IntStream
				.range(1, duplicates.size()).forEach(index -> duplicates.get(index).setName(name + "_" + index))); //$NON-NLS-1$
	}
}
