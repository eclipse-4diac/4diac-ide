/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st.breakpoint;

import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class STBreakpointPropertyPage extends PropertyPage {

	private STBreakpointConditionEditor editor;

	@Override
	protected Control createContents(final Composite parent) {
		noDefaultAndApplyButton();
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);

		editor = new STBreakpointConditionEditor();
		editor.createControl(comp);
		editor.setInput((STLineBreakpoint) getElement());

		return comp;
	}

	@Override
	public boolean performOk() {
		if (editor.isDirty()) {
			editor.doSave();
		}
		return super.performOk();
	}
}
