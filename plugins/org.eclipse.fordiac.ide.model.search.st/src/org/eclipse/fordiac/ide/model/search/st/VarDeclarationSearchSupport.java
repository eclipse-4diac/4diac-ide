/**
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.xtext.EcoreUtil2;

public class VarDeclarationSearchSupport extends StructuredTextSearchSupport {
	private final VarDeclaration varDeclaration;
	private STTypeDeclaration parseResultType;
	private STInitializerExpressionSource parseResult;

	public VarDeclarationSearchSupport(final VarDeclaration varDeclaration) {
		this.varDeclaration = varDeclaration;
	}

	@Override
	public Stream<EObject> prepare() throws CoreException {
		return Stream.of(prepareResultType(), prepareInitialValue()).filter(Objects::nonNull);
	}

	protected STTypeDeclaration prepareResultType() throws CoreException {
		if (parseResultType == null && varDeclaration.isArray()) {
			final List<String> errors = new ArrayList<>();
			parseResultType = StructuredTextParseUtil.parseType(varDeclaration, errors, null, null);
			if (parseResultType == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining(", ")))); //$NON-NLS-1$
			}
		}
		return parseResultType;
	}

	protected STInitializerExpressionSource prepareInitialValue() throws CoreException {
		if (parseResult == null && varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank()) {
			final List<String> errors = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(varDeclaration.getValue().getValue(),
					varDeclaration.eResource() != null ? varDeclaration.eResource().getURI() : null,
					VariableOperations.evaluateResultType(varDeclaration),
					EcoreUtil2.getContainerOfType(varDeclaration, LibraryElement.class), null, errors, null, null);
			if (parseResult == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining(", ")))); //$NON-NLS-1$
			}
		}
		return parseResult;
	}
}
