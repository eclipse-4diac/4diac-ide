/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

public class FileChooserControl extends AbstractChooserControl {

	public FileChooserControl(final Composite parent, final int style, final String label) {
		super(parent, style, label);
	}

	@Override
	protected String openChooserDialog(final String labelText, final String value) {
		final FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN);
		dialog.setText(labelText);

		if (!value.isEmpty()) {
			dialog.setFilterPath(value);
		}

		return dialog.open();
	}

}
