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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.Objects;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Import;

public class STCorePartition {

	private final String packageName;
	private final EList<Import> imports;
	private final String originalSource;

	public STCorePartition(final String packageName, final EList<Import> imports, final String originalSource) {
		this.packageName = packageName;
		this.imports = Objects.requireNonNullElseGet(imports, ECollections::emptyEList);
		this.originalSource = originalSource;
	}

	public String getPackageName() {
		return packageName;
	}

	public EList<Import> getImports() {
		return imports;
	}

	public String getOriginalSource() {
		return originalSource;
	}

	@Override
	public String toString() {
		return String.format("STCorePartition [packageName=%s, imports=%s]", packageName, imports); //$NON-NLS-1$
	}
}
