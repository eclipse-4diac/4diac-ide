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

public record GlobalConstantsPartition(String packageName, EList<Import> imports, EList<VarDeclaration> constants) {
	public GlobalConstantsPartition(final String packageName, final EList<Import> imports,
			final EList<VarDeclaration> constants) {
		this.packageName = packageName;
		this.imports = Objects.requireNonNullElseGet(imports, ECollections::emptyEList);
		this.constants = Objects.requireNonNullElseGet(constants, ECollections::emptyEList);
	}
}
