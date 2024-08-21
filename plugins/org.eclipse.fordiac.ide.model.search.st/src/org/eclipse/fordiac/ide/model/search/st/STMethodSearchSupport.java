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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;

public class STMethodSearchSupport extends StructuredTextSearchSupport {
	private final STMethod method;
	private org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod parseResult;

	public STMethodSearchSupport(final STMethod method) {
		this.method = method;
	}

	@Override
	public Stream<EObject> prepare() throws CoreException {
		if (parseResult == null) {
			final List<String> errors = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(method, errors, null, null);
			if (parseResult == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining(", ")))); //$NON-NLS-1$
			}
		}
		return Stream.of(parseResult);
	}
}
