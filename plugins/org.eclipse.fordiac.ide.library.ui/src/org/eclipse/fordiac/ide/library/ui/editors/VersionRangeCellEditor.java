/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

class VersionRangeCellEditor extends CellEditor {

	private Text text;

	public VersionRangeCellEditor(final Composite parent) {
		super(parent);
	}

	@Override
	protected Control createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(2).applyTo(container);

		text = new Text(container, SWT.SINGLE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Button dialogButton = new Button(container, SWT.FLAT);
		dialogButton.setText("..."); //$NON-NLS-1$
		dialogButton.setToolTipText(Messages.ManifestEditor_ConfigureVersionRange);
		dialogButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		dialogButton.addListener(SWT.Selection, _ -> openDialog());

		// Commit value when pressing enter
		text.addListener(SWT.DefaultSelection, _ -> {
			fireApplyEditorValue();
			deactivate();
		});

		container.addTraverseListener(event -> {
			if (event.detail == SWT.TRAVERSE_ESCAPE) {
				fireCancelEditor();
				deactivate();
				event.doit = false;
			}
		});

		return container;
	}

	private void openDialog() {
		final VersionRangeSelectionDialog dialog = new VersionRangeSelectionDialog(text.getShell(), text.getText());

		if (dialog.open() == Window.OK) {
			text.setText(dialog.getVersionRange());
			fireApplyEditorValue();
			deactivate();
		}
	}

	@Override
	protected Object doGetValue() {
		return text.getText();
	}

	@Override
	protected void doSetValue(final Object value) {
		text.setText(value instanceof final String string ? string : ""); //$NON-NLS-1$
	}

	@Override
	protected void doSetFocus() {
		text.setFocus();
		text.selectAll();
	}
}