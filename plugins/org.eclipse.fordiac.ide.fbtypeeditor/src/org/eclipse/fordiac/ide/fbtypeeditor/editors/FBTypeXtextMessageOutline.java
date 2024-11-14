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
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.jface.layout.FillLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class FBTypeXtextMessageOutline extends Page implements IContentOutlinePage {

	private final String message;

	private Composite composite;
	private Label label;

	public FBTypeXtextMessageOutline(final String message) {
		this.message = message;
	}

	@Override
	public void createControl(final Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		FillLayoutFactory.fillDefaults().margins(5, 5).applyTo(composite);

		label = new Label(composite, SWT.LEFT | SWT.TOP | SWT.WRAP);
		label.setText(message);
	}

	@Override
	public Control getControl() {
		return composite;
	}

	@Override
	public void setFocus() {
		label.setFocus();
	}

	@Override
	public ISelection getSelection() {
		return StructuredSelection.EMPTY;
	}

	@Override
	public void setSelection(final ISelection selection) {
		// do nothing
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// do nothing
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// do nothing
	}
}
