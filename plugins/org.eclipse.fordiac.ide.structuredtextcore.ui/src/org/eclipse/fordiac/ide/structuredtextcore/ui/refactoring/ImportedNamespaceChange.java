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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.xtext.ide.serializer.impl.EmfResourceChange;

@SuppressWarnings("restriction")
public class ImportedNamespaceChange extends EmfResourceChange {

	private final Import imp;
	private final String importedNamespace;

	public ImportedNamespaceChange(final Import imp, final String importedNamespace) {
		super(imp.eResource(), imp.eResource().getURI());
		this.imp = imp;
		this.importedNamespace = importedNamespace;
	}

	public Import getImport() {
		return imp;
	}

	public String getImportedNamespace() {
		return importedNamespace;
	}
}
