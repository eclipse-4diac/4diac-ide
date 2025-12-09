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
package org.eclipse.fordiac.ide.structuredtextalgorithm.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;

public class STAlgorithmPartition extends STCorePartition {
	private final List<ICallable> callables;

	public STAlgorithmPartition(final String packageName, final List<Import> imports, final String originalSource,
			final List<ICallable> callables) {
		super(packageName, imports, originalSource);
		this.callables = Objects.requireNonNullElseGet(callables, Collections::emptyList);
	}

	public List<ICallable> getCallables() {
		return callables;
	}
}
