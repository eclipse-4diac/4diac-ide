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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;

public class STFunctionPartition extends STCorePartition {
	public static final String LOST_AND_FOUND_NAME = "LOST_AND_FOUND"; //$NON-NLS-1$

	private final EList<STFunction> functions;

	public STFunctionPartition(final String packageName, final EList<Import> imports, final String originalSource,
			final EList<STFunction> functions) {
		super(packageName, imports, originalSource);
		this.functions = functions;
	}

	public EList<STFunction> getFunctions() {
		return functions;
	}
}
