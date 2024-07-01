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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.libraryElement.Import;

public class STCorePartition {
	private final String packageName;
	private final List<Import> imports;
	private final String originalSource;

	public STCorePartition(final String packageName, final List<Import> imports, final String originalSource) {
		this.packageName = packageName;
		this.imports = Objects.requireNonNullElseGet(imports, Collections::emptyList);
		this.originalSource = originalSource;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<Import> getImports() {
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
