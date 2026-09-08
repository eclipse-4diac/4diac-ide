/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.editor;

import org.eclipse.fordiac.ide.model.search.dialog.AbstractTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.GlobalConstantsEntryDataHandler;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditor;

public class GlobalConstantsTypeEditor extends AbstractTypeEditor {

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants"; //$NON-NLS-1$
	}

	@Override
	protected AbstractTypeEntryDataHandler<? extends TypeEntry> createTypeEntryDataHandler() {
		return new GlobalConstantsEntryDataHandler(getTypeEntry());
	}

	@Override
	protected GlobalConstantsEntry getTypeEntry() {
		return (GlobalConstantsEntry) super.getTypeEntry();
	}
}
