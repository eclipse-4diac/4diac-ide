/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.refactoring;

import java.util.Objects;

import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreResourceRelocationStrategy;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;

public class STFunctionResourceRelocationStrategy extends STCoreResourceRelocationStrategy {

	@Override
	protected void updateTypeName(final STCoreResource resource, final String oldName, final String newName) {
		if (resource.getParseResult().getRootASTElement() instanceof final STFunctionSource source) {
			source.getFunctions().stream().filter(func -> Objects.equals(func.getName(), oldName))
					.forEach(func -> func.setName(newName));
		}
		super.updateTypeName(resource, oldName, newName);
	}
}
