/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl;

import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;

@SuppressWarnings("java:S1172")
public final class STFunctionAnnotations {
	private STFunctionAnnotations() {
	}

	static EList<ITypedElement> getInputParameters(final STFunction function) {
		return ECollections.unmodifiableEList(
				function.getVarDeclarations().stream().filter(STVarInputDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}

	static EList<ITypedElement> getOutputParameters(final STFunction function) {
		return ECollections.unmodifiableEList(
				function.getVarDeclarations().stream().filter(STVarOutputDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}

	static EList<ITypedElement> getInOutParameters(final STFunction function) {
		return ECollections.unmodifiableEList(
				function.getVarDeclarations().stream().filter(STVarInOutDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}
}
