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
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.xtext.EcoreUtil2;

public class DirectlyDerivedTypeSearchSupport extends StructuredTextSearchSupport {
	private final DirectlyDerivedType directlyDerivedType;
	private STInitializerExpressionSource parseResult;

	public DirectlyDerivedTypeSearchSupport(final DirectlyDerivedType directlyDerivedType) {
		this.directlyDerivedType = directlyDerivedType;
	}

	@Override
	public Stream<EObject> prepare() throws CoreException {
		return Stream.<EObject>of(prepareInitialValue()).filter(Objects::nonNull);
	}

	protected STInitializerExpressionSource prepareInitialValue() throws CoreException {
		if (parseResult == null && directlyDerivedType.getInitialValue() != null
				&& !directlyDerivedType.getInitialValue().isBlank()) {
			final List<String> errors = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(directlyDerivedType.getInitialValue(),
					directlyDerivedType.eResource() != null ? directlyDerivedType.eResource().getURI() : null,
					directlyDerivedType.getBaseType(),
					EcoreUtil2.getContainerOfType(directlyDerivedType, LibraryElement.class), null, errors, null, null);
			if (parseResult == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining(", ")))); //$NON-NLS-1$
			}
		}
		return parseResult;
	}
}
