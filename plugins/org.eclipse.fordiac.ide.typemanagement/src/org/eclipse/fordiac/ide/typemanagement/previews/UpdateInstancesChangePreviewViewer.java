/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Schwarz - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.previews;

import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateInstancesChange;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

@SuppressWarnings("restriction")
public class UpdateInstancesChangePreviewViewer implements IChangePreviewViewer {

	private Composite parent;
	private Control previewControl;

	@Override
	public void createControl(final Composite parent) {
		parent.setSize(500, 500);

		final Composite control = new Composite(parent, SWT.NONE);
		control.setLayoutData(new FillLayout());

		final Table table = new Table(control, SWT.CHECK | SWT.BORDER);

	}

	@Override
	public Control getControl() {
		return previewControl;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		if (!(input.getChange() instanceof UpdateInstancesChange)) {
			return;
		}

	}

}
