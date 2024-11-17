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
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl;

import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;

@SuppressWarnings("java:S1172")
public final class STAlgorithmAnnotations {
	static EList<ITypedElement> getInputParameters(final STAlgorithm algorithm) {
		return ECollections.emptyEList();
	}

	static EList<ITypedElement> getOutputParameters(final STAlgorithm algorithm) {
		return ECollections.emptyEList();
	}

	static EList<ITypedElement> getInOutParameters(final STAlgorithm algorithm) {
		return ECollections.emptyEList();
	}

	static DataType getReturnType(final STAlgorithm algorithm) {
		return null;
	}

	static EList<ITypedElement> getInputParameters(final STMethod method) {
		return ECollections.unmodifiableEList(
				method.getBody().getVarDeclarations().stream().filter(STVarInputDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}

	static EList<ITypedElement> getOutputParameters(final STMethod method) {
		return ECollections.unmodifiableEList(
				method.getBody().getVarDeclarations().stream().filter(STVarOutputDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}

	static EList<ITypedElement> getInOutParameters(final STMethod method) {
		return ECollections.unmodifiableEList(
				method.getBody().getVarDeclarations().stream().filter(STVarInOutDeclarationBlock.class::isInstance)
						.map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream).toList());
	}

	private STAlgorithmAnnotations() {
		throw new UnsupportedOperationException();
	}
}
