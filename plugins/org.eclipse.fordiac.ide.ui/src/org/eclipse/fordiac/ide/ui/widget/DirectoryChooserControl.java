/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * The Class DirectoryChooserControl.
 *
 */
public class DirectoryChooserControl extends AbstractChooserControl {

	/**
	 * Instantiates a new directory chooser control.
	 *
	 * @param parent the parent
	 * @param style  the style
	 * @param label  the label
	 */
	public DirectoryChooserControl(final Composite parent, final int style, final String label) {
		super(parent, style, label);
	}

	@Override
	protected String openChooserDialog(final String labelText, final String value) {
		final DirectoryDialog dialog = new DirectoryDialog(this.getShell(), SWT.SAVE);
		dialog.setText(labelText);
		dialog.setMessage(FordiacMessages.DirectoryChooserControl_LABEL_SelectdDirectoryDialogMessage);

		if (!value.isEmpty()) {
			dialog.setFilterPath(value);
		}
		return dialog.open();
	}

}
