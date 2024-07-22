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
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Objects;

import org.eclipse.jface.fieldassist.ContentProposal;

public class ImportContentProposal extends ContentProposal {

	private final String importedNamespace;

	public ImportContentProposal(final String content, final String label, final String description,
			final String importedNamespace) {
		super(content, label, description);
		this.importedNamespace = Objects.requireNonNull(importedNamespace);
	}

	public String getImportedNamespace() {
		return importedNamespace;
	}
}
