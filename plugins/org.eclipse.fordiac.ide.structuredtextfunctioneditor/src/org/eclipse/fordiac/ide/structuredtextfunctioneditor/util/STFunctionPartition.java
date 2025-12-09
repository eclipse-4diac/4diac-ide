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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;

public class STFunctionPartition extends STCorePartition {
	private final List<STFunction> functions;

	public STFunctionPartition(final String packageName, final List<Import> imports, final String originalSource,
			final List<STFunction> functions) {
		super(packageName, imports, originalSource);
		this.functions = Objects.requireNonNullElseGet(functions, Collections::emptyList);
	}

	public List<STFunction> getFunctions() {
		return functions;
	}
}
