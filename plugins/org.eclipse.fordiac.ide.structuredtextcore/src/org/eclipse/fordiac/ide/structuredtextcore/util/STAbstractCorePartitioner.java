/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

public abstract class STAbstractCorePartitioner<E extends INamedElement> implements STCorePartitioner {

	@Inject
	private IEObjectDocumentationProvider documentationProvider;

	protected static Import convertImport(final STImport decl) {
		if (decl.getImportedNamespace() == null || decl.getImportedNamespace().isEmpty()) {
			return null;
		}
		final var result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(decl.getImportedNamespace());
		return result;
	}

	protected VarDeclaration convertInputParameter(final STVarDeclaration declaration) {
		final var result = convertVariable(declaration);
		result.setIsInput(true);
		return result;
	}

	protected VarDeclaration convertOutputParameter(final STVarDeclaration declaration) {
		return convertVariable(declaration);
	}

	protected VarDeclaration convertInOutParameter(final STVarDeclaration declaration) {
		return convertInputParameter(declaration);
	}

	protected VarDeclaration convertVariable(final STVarDeclaration declaration) {
		final VarDeclaration result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		result.setName(declaration.getName());
		final String comment = getDocumentationProvider().getDocumentation(declaration);
		if (comment != null) {
			result.setComment(comment);
		}
		if (declaration.getType() instanceof final DataType type && !type.eIsProxy()) {
			result.setType(type);
		}
		if (declaration.isArray()) {
			ArraySizeHelper.setArraySize(result, extractArraySize(declaration));
		}
		if (declaration.getDefaultValue() != null) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(extractDefaultValue(declaration));
			result.setValue(value);
		}
		return result;
	}

	protected static boolean isValidParameter(final STVarDeclaration declaration) {
		return declaration.getName() != null && !declaration.getName().isEmpty()
				&& declaration.getType() instanceof final DataType type && !type.eIsProxy();
	}

	protected static String extractArraySize(final STVarDeclaration declaration) {
		return NodeModelUtils.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_Ranges())
				.stream().map(INode::getText).collect(Collectors.joining(",")); //$NON-NLS-1$
	}

	protected static String extractDefaultValue(final STVarDeclaration declaration) {
		return NodeModelUtils
				.findNodesForFeature(declaration, STCorePackage.eINSTANCE.getSTVarDeclaration_DefaultValue()).stream()
				.map(INode::getText).collect(Collectors.joining()).trim();
	}

	protected static void generatePackage(final LibraryElement libraryElement, final StringBuilder builder) {
		final String packageName = PackageNameHelper.getPackageName(libraryElement);
		if (!packageName.isBlank()) {
			builder.append("PACKAGE "); //$NON-NLS-1$
			builder.append(packageName);
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static void generateImports(final LibraryElement libraryElement, final StringBuilder builder) {
		ImportHelper.getImports(libraryElement).forEach(imp -> {
			builder.append("IMPORT "); //$NON-NLS-1$
			builder.append(imp.getImportedNamespace());
			builder.append(";"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
		});
		if (!ImportHelper.getImports(libraryElement).isEmpty()) {
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static void generateVariable(final VarDeclaration varDeclaration, final StringBuilder builder) {
		builder.append(varDeclaration.getName());
		builder.append(" : "); //$NON-NLS-1$
		builder.append(varDeclaration.getFullTypeName());
		if (varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank()) {
			builder.append(" := "); //$NON-NLS-1$
			builder.append(varDeclaration.getValue().getValue());
		}
		builder.append(";"); //$NON-NLS-1$
	}

	protected static void handleDuplicates(final List<? extends INamedElement> result) {
		result.stream().collect(Collectors.groupingBy(INamedElement::getName)).forEach((name, duplicates) -> IntStream
				.range(1, duplicates.size()).forEach(index -> duplicates.get(index).setName(name + "_" + index))); //$NON-NLS-1$
	}

	protected IEObjectDocumentationProvider getDocumentationProvider() {
		return documentationProvider;
	}
}
