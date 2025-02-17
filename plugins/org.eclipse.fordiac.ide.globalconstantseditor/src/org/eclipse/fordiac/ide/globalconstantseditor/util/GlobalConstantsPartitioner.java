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
package org.eclipse.fordiac.ide.globalconstantseditor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.util.STAbstractCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class GlobalConstantsPartitioner extends STAbstractCorePartitioner<VarDeclaration> {

	@Inject
	private GlobalConstantsGrammarAccess grammarAccess;

	@Override
	public String combine(final LibraryElement libraryElement) {
		if (libraryElement instanceof final GlobalConstants globalConstants) {
			return combine(globalConstants);
		}
		return ""; //$NON-NLS-1$
	}

	public static String combine(final GlobalConstants globalConstants) {
		if (globalConstants.getSource() != null) {
			final String text = globalConstants.getSource().getText();
			if (text != null && text.contains("GLOBALCONSTANTS")) { //$NON-NLS-1$
				return text;
			}
		}
		return generateGlobalConstantsText(globalConstants);
	}

	protected static String generateGlobalConstantsText(final GlobalConstants globalConstants) {
		final StringBuilder builder = new StringBuilder();
		generatePackage(globalConstants, builder);
		generateImports(globalConstants, builder);
		builder.append("GLOBALCONSTANTS ").append(globalConstants.getName()); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		builder.append("    VAR_GLOBAL CONSTANT"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		globalConstants.getConstants().stream().forEach(constant -> {
			builder.append("        "); //$NON-NLS-1$
			generateVariable(constant, builder);
			builder.append(CommonElementExporter.LINE_END);
		});
		builder.append("    END_VAR"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		builder.append("END_GLOBALCONSTANTS"); //$NON-NLS-1$
		builder.append(CommonElementExporter.LINE_END);
		return builder.toString();
	}

	@Override
	public Optional<STCorePartition> partition(final XtextResource resource) {
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

	protected Optional<STCorePartition> partition(final STGlobalConstsSource source) {
		try {
			final var node = NodeModelUtils.findActualNodeFor(source);
			final var text = node != null ? node.getText() : null;
			final var imports = source.getImports().stream().map(GlobalConstantsPartitioner::convertImport)
					.filter(Objects::nonNull).toList();
			final var constants = Optional.ofNullable(source.getConstants()).stream()
					.map(STGlobalConstants::getElements).flatMap(List::stream)
					.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).map(this::convertVariable)
					.filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
			handleDuplicates(constants);
			return Optional.of(new GlobalConstantsPartition(source.getName(), imports, text, constants));
		} catch (final Exception e) {
			return Optional.empty();
		}
	}
}
