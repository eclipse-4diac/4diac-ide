/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added functionality to update nested DataTypes
 *   Patrick Aigner - changed to handle updates in Function Blocks
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FBTypeUpdateDialog<T extends TypeEntry> extends MessageDialog {

	protected final AbstractTypeEntryDataHandler<T> data;

	public FBTypeUpdateDialog(final Shell parentShell, final String dialogTitle, final String dialogMessage,
			final String[] dialogButtonLabels, final int defaultIndex,
			final AbstractTypeEntryDataHandler<T> dataHandler) {
		super(parentShell, dialogTitle, null, dialogMessage, MessageDialog.NONE, dialogButtonLabels, defaultIndex);
		this.data = dataHandler;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		return new FBTypeSearchResultTable(parent, data);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public AbstractTypeEntryDataHandler<T> getDataHandler() {
		return data;
	}
}
