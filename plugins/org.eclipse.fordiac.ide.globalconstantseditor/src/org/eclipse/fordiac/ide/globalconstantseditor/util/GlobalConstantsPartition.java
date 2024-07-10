/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import java.util.Objects;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;

public class GlobalConstantsPartition extends STCorePartition {
	private final EList<VarDeclaration> constants;

	public GlobalConstantsPartition(final String packageName, final EList<Import> imports, final String originalSource,
			final EList<VarDeclaration> constants) {
		super(packageName, imports, originalSource);
		this.constants = Objects.requireNonNullElseGet(constants, ECollections::emptyEList);
	}

	public EList<VarDeclaration> getConstants() {
		return constants;
	}
}
