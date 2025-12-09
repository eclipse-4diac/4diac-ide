/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Base class of all dialog fields. Dialog fields manage controls together with
 * the model, independent from the creation time of the widgets. - support for
 * automated layouting. - enable / disable, set focus a concept of the base
 * class.
 *
 * DialogField have a label.
 *
 * copied from Eclipse JDT
 */
public class DialogField {

	private Label fLabel;
	protected String fLabelText;

	private IDialogFieldListener fDialogFieldListener;

	private boolean fEnabled;

	public DialogField() {
		fEnabled = true;
		fLabel = null;
		fLabelText = ""; //$NON-NLS-1$
	}

	/**
	 * Sets the label of the dialog field.
	 *
	 * @param labeltext the label text
	 */
	public void setLabelText(final String labeltext) {
		fLabelText = labeltext;
		if (isOkToUse(fLabel)) {
			fLabel.setText(labeltext);
		}
	}

	// ------ change listener

	/**
	 * Defines the listener for this dialog field.
	 *
	 * @param listener the dialog field listener
	 */
	public final void setDialogFieldListener(final IDialogFieldListener listener) {
		fDialogFieldListener = listener;
	}

	/**
	 * Programmatical invocation of a dialog field change.
	 */
	public void dialogFieldChanged() {
		if (fDialogFieldListener != null) {
			fDialogFieldListener.dialogFieldChanged(this);
		}
	}

	// ------- focus management

	/**
	 * Tries to set the focus to the dialog field. Returns <code>true</code> if the
	 * dialog field can take focus. To be reimplemented by dialog field
	 * implementors.
	 *
	 * @return <code>true</code> if the dialog field can take focus
	 */
	public boolean setFocus() {
		return false;
	}

	/**
	 * Posts <code>setFocus</code> to the display event queue.
	 *
	 * @param display the Display
	 */
	public void postSetFocusOnDialogField(final Display display) {
		if (display != null) {
			display.asyncExec(this::setFocus);
		}
	}

	// ------- layout helpers

	/**
	 * Creates all controls of the dialog field and fills it to a composite. The
	 * composite is assumed to have <code>GridLayout</code> as layout. The dialog
	 * field will adjust its controls' spans to the number of columns given. To be
	 * reimplemented by dialog field implementors.
	 *
	 * @param parent   the parent composite
	 * @param nColumns number of columns
	 * @return controls of dialog field
	 */
	public Control[] doFillIntoGrid(final Composite parent, final int nColumns) {
		assertEnoughColumns(nColumns);

		final Label label = getLabelControl(parent);
		label.setLayoutData(gridDataForLabel(nColumns));

		return new Control[] { label };
	}

	/**
	 * Returns the number of columns of the dialog field. To be reimplemented by
	 * dialog field implementors.
	 *
	 * @return the number of columns of the dialog field
	 */
	public int getNumberOfControls() {
		return 1;
	}

	protected static GridData gridDataForLabel(final int span) {
		final GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = span;
		return gd;
	}

	// ------- ui creation

	/**
	 * Creates or returns the created label widget.
	 *
	 * @param parent The parent composite or <code>null</code> if the widget has
	 *               already been created.
	 * @return the label widget
	 */
	public Label getLabelControl(final Composite parent) {
		if (fLabel == null) {
			assertCompositeNotNull(parent);

			fLabel = new Label(parent, SWT.LEFT | SWT.WRAP);
			fLabel.setFont(parent.getFont());
			fLabel.setEnabled(fEnabled);
			if (fLabelText != null && !"".equals(fLabelText)) { //$NON-NLS-1$
				fLabel.setText(fLabelText);
			} else {
				// XXX: to avoid a 16 pixel wide empty label - revisit
				fLabel.setText("."); //$NON-NLS-1$
				fLabel.setVisible(false);
			}
		}
		return fLabel;
	}

	/**
	 * Creates a spacer control.
	 *
	 * @param parent The parent composite
	 * @return the spacer control
	 */
	public static Control createEmptySpace(final Composite parent) {
		return createEmptySpace(parent, 1);
	}

	/**
	 * Creates a spacer control with the given span. The composite is assumed to
	 * have <code>GridLayout</code> as layout.
	 *
	 * @param parent The parent composite
	 * @param span   the given span
	 * @return the spacer control
	 */
	public static Control createEmptySpace(final Composite parent, final int span) {
		final Label label = new Label(parent, SWT.LEFT);
		final GridData gd = new GridData();
		gd.horizontalAlignment = GridData.BEGINNING;
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalSpan = span;
		gd.horizontalIndent = 0;
		gd.widthHint = 0;
		gd.heightHint = 0;
		label.setLayoutData(gd);
		return label;
	}

	/**
	 * Tests is the control is not <code>null</code> and not disposed.
	 *
	 * @param control the Control
	 * @return <code>true</code> if the control is not <code>null</code> and not
	 *         disposed.
	 */
	protected final boolean isOkToUse(final Control control) {
		return (control != null) && (Display.getCurrent() != null) && !control.isDisposed();
	}

	// --------- enable / disable management

	/**
	 * Sets the enable state of the dialog field.
	 *
	 * @param enabled enable state
	 */
	public final void setEnabled(final boolean enabled) {
		if (enabled != fEnabled) {
			fEnabled = enabled;
			updateEnableState();
		}
	}

	/**
	 * Called when the enable state changed. To be extended by dialog field
	 * implementors.
	 */
	protected void updateEnableState() {
		if (fLabel != null) {
			fLabel.setEnabled(fEnabled);
		}
	}

	/**
	 * Brings the UI in sync with the model. Only needed when model was changed in
	 * different thread whil UI was lready created.
	 */
	public void refresh() {
		updateEnableState();
	}

	/**
	 * Gets the enable state of the dialog field.
	 *
	 * @return the enable state
	 */
	public final boolean isEnabled() {
		return fEnabled;
	}

	protected final void assertCompositeNotNull(final Composite comp) {
		Assert.isNotNull(comp, "uncreated control requested with composite null"); //$NON-NLS-1$
	}

	protected final void assertEnoughColumns(final int nColumns) {
		Assert.isTrue(nColumns >= getNumberOfControls(), "given number of columns is too small"); //$NON-NLS-1$
	}

}