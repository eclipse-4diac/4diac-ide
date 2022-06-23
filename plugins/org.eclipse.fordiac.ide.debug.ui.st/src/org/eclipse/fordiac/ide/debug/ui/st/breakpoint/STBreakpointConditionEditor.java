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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.st.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPropertyListener;

public class STBreakpointConditionEditor {
	public static final int PROP_CONDITION = 1;
	public static final int PROP_CONDITION_ENABLED = 2;

	private final ListenerList<IPropertyListener> listeners = new ListenerList<>();

	private STLineBreakpoint input;

	private Button conditional;
	private Text conditionText;

	private boolean dirty;

	public Control createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		conditional = new Button(comp, SWT.CHECK);
		conditional.setText(Messages.STBreakpointConditionEditor_Conditional);
		conditional.setEnabled(false);
		conditional.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			conditionText.setEditable(input != null && conditional.getSelection());
			firePropertyChange(PROP_CONDITION_ENABLED);
			setDirty(true);
		}));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(conditional);

		conditionText = new Text(comp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		conditionText.setEditable(false);
		conditionText.addModifyListener(e -> {
			firePropertyChange(PROP_CONDITION);
			setDirty(true);
		});
		GridDataFactory.fillDefaults().grab(true, true).applyTo(conditionText);

		return comp;
	}

	public void setInput(final STLineBreakpoint input) {
		this.input = input;
		conditional.setEnabled(input != null);
		conditional.setSelection(input != null && input.isConditionEnabled());
		conditionText.setEditable(input != null && input.isConditionEnabled());
		conditionText.setText(input != null ? input.getCondition() : ""); //$NON-NLS-1$
		setDirty(false);
	}

	public void doSave() {
		try {
			if (input != null && input.getMarker() != null && input.getMarker().exists()) {
				input.setCondition(conditionText.getText());
				input.setConditionEnabled(conditional.getSelection());
			}
			setDirty(false);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Couldn't set breakpoint condition on " + input, e); //$NON-NLS-1$
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(final boolean dirty) {
		this.dirty = dirty;
	}

	public boolean setFocus() {
		return conditionText.setFocus();
	}

	public void dispose() {
		listeners.clear();
	}

	public void addPropertyListener(final IPropertyListener listener) {
		listeners.add(listener);
	}

	public void removePropertyListener(final IPropertyListener listener) {
		listeners.remove(listener);
	}

	protected void firePropertyChange(final int property) {
		for (final IPropertyListener listener : listeners) {
			listener.propertyChanged(this, property);
		}
	}
}
