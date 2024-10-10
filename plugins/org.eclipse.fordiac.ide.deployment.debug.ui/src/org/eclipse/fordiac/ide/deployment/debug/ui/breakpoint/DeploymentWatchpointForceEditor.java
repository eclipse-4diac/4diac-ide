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
package org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint;

import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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
import org.eclipse.ui.IWorkbenchPartConstants;

public class DeploymentWatchpointForceEditor {
	public static final int PROP_FORCE_VALUE = 1;
	public static final int PROP_FORCE_ENABLED = 2;

	private final ListenerList<IPropertyListener> listeners = new ListenerList<>();

	private DeploymentWatchpoint input;
	private VarDeclaration varDeclaration;

	private Control control;
	private Button forceCheckbox;
	private Text forceText;
	private Button dialogButton;

	private boolean dirty;
	private boolean suppressPropertyChanges;

	public Control createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		forceCheckbox = new Button(comp, SWT.CHECK);
		forceCheckbox.setText(Messages.DeploymentWatchpointForceEditor_ForceLabel);
		forceCheckbox.setEnabled(false);
		forceCheckbox.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			if (forceText != null) {
				forceText.setEditable(input != null && forceCheckbox.getSelection());
			}
			firePropertyChange(PROP_FORCE_ENABLED);
			setDirty(true);
		}));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forceCheckbox);

		final Control forceEditor = createForceEditor(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forceEditor);

		control = comp;
		return comp;
	}

	protected Control createForceEditor(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		forceText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		forceText.setEditable(false);
		forceText.addModifyListener(event -> {
			firePropertyChange(PROP_FORCE_VALUE);
			setDirty(true);
		});
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forceText);
		dialogButton = new Button(composite, SWT.FLAT);
		dialogButton.setText("\u2026"); //$NON-NLS-1$
		dialogButton.setEnabled(false);
		dialogButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> openDialog()));
		GridDataFactory.swtDefaults().applyTo(dialogButton);
		return composite;
	}

	protected void openDialog() {
		try {
			VariableDialog.open(control.getShell(), varDeclaration, forceText.getText()).ifPresent(forceText::setText);
		} finally {
			forceText.forceFocus();
		}
	}

	private boolean isStructuredVariable() {
		return varDeclaration != null
				&& (varDeclaration.isArray() || varDeclaration.getType() instanceof StructuredType);
	}

	public void setInput(final DeploymentWatchpoint input) {
		try {
			suppressPropertyChanges = true;
			this.input = input;
			varDeclaration = Optional.ofNullable(input).filter(DeploymentWatchpoint::isForceSupported)
					.flatMap(DeploymentWatchpoint::getTarget).filter(VarDeclaration.class::isInstance)
					.map(VarDeclaration.class::cast).orElse(null);
			forceCheckbox.setEnabled(input != null && input.isForceSupported());
			forceCheckbox.setSelection(input != null && input.isForceEnabled());
			forceText.setEditable(input != null && input.isForceEnabled());
			forceText.setText(input != null ? input.getForceValue() : ""); //$NON-NLS-1$
			dialogButton.setEnabled(input != null && isStructuredVariable());
			setDirty(false);
		} finally {
			suppressPropertyChanges = false;
		}
	}

	public void doSave() {
		try {
			if (input != null && input.getMarker() != null && input.getMarker().exists()) {
				input.setForceEnabled(forceCheckbox.getSelection());
				input.setForceValue(forceText.getText());
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
		if (dirty != this.dirty) {
			this.dirty = dirty;
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		}
	}

	public boolean setFocus() {
		return forceText.setFocus();
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
		if (!suppressPropertyChanges) {
			for (final IPropertyListener listener : listeners) {
				listener.propertyChanged(this, property);
			}
		}
	}
}
