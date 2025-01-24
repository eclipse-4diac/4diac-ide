/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *               2023 Martin Erich Jobst
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
 *     Tom Eicher <eclipse@tom.eicher.name> - fix minimum width
 *     Martin Jobst - copied and adapted TextCellEditor for StyledText
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import static org.eclipse.swt.events.KeyListener.keyReleasedAdapter;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A cell editor that manages a text entry field. The cell editor's value is the
 * text string itself.
 * <p>
 * This class may be instantiated or subclassed.
 * </p>
 */
public class StyledTextCellEditor extends CellEditor {

	/** The text control; initially <code>null</code>. */
	protected StyledText text;

	private ModifyListener modifyListener;

	/** State information for updating action enablement */
	private boolean isSelection = false;

	private boolean isDeleteable = false;

	private boolean isSelectable = false;

	/**
	 * Default StyledTextCellEditor style specify no borders on text widget as cell
	 * outline in table already provides the look of a border.
	 */
	private static final int DEFAULT_STYLE = SWT.SINGLE;

	/**
	 * Creates a new text string cell editor with no control The cell editor value
	 * is the string itself, which is initially the empty string. Initially, the
	 * cell editor has no cell validator.
	 *
	 * @since 2.1
	 */
	public StyledTextCellEditor() {
		setStyle(DEFAULT_STYLE);
	}

	/**
	 * Creates a new text string cell editor parented under the given control. The
	 * cell editor value is the string itself, which is initially the empty string.
	 * Initially, the cell editor has no cell validator.
	 *
	 * @param parent the parent control
	 */
	public StyledTextCellEditor(final Composite parent) {
		this(parent, DEFAULT_STYLE);
	}

	/**
	 * Creates a new text string cell editor parented under the given control. The
	 * cell editor value is the string itself, which is initially the empty string.
	 * Initially, the cell editor has no cell validator.
	 *
	 * @param parent the parent control
	 * @param style  the style bits
	 * @since 2.1
	 */
	public StyledTextCellEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * Checks to see if the "deletable" state (can delete/ nothing to delete) has
	 * changed and if so fire an enablement changed notification.
	 */
	private void checkDeleteable() {
		final boolean oldIsDeleteable = isDeleteable;
		isDeleteable = isDeleteEnabled();
		if (oldIsDeleteable != isDeleteable) {
			fireEnablementChanged(DELETE);
		}
	}

	/**
	 * Checks to see if the "selectable" state (can select) has changed and if so
	 * fire an enablement changed notification.
	 */
	private void checkSelectable() {
		final boolean oldIsSelectable = isSelectable;
		isSelectable = isSelectAllEnabled();
		if (oldIsSelectable != isSelectable) {
			fireEnablementChanged(SELECT_ALL);
		}
	}

	/**
	 * Checks to see if the selection state (selection / no selection) has changed
	 * and if so fire an enablement changed notification.
	 */
	private void checkSelection() {
		final boolean oldIsSelection = isSelection;
		isSelection = text.getSelectionCount() > 0;
		if (oldIsSelection != isSelection) {
			fireEnablementChanged(COPY);
			fireEnablementChanged(CUT);
		}
	}

	@Override
	protected Control createControl(final Composite parent) {
		text = createStyledText(parent);
		// widgetDefaultSelected is not called for StyledTexts
		// use verify key listener since the regular key listener would be too late
		text.addVerifyKeyListener(event -> {
			// if the proposal popup is open we do not handle keystrokes
			// ourself to ensure proposal handling is working correctly
			if (event.doit && !isProposalPopupOpen()) {
				if (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {

					boolean commit = event.stateMask != SWT.MOD3;
					if (isCommitWithCtrlKey()) {
						commit = event.stateMask == SWT.MOD1;
					}

					if (commit) {
						fireApplyEditorValue();
						deactivate();
						event.doit = false;
					}
				} else if (event.keyCode == SWT.ESC && event.stateMask == 0) {
					fireCancelEditor();
					event.doit = false;
				}
			}
		});
		text.addKeyListener(keyReleasedAdapter(event -> {
			// as a result of processing the above call, clients may have
			// disposed this cell editor
			if ((getControl() == null) || getControl().isDisposed()) {
				return;
			}
			checkSelection(); // see explanation below
			checkDeleteable();
			checkSelectable();
		}));
		text.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
			}
		});
		// We really want a selection listener but it is not supported so we
		// use a key listener and a mouse listener to know when selection changes
		// may have occurred
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(final MouseEvent e) {
				checkSelection();
				checkDeleteable();
				checkSelectable();
			}
		});
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				StyledTextCellEditor.this.focusLost();
			}
		});
		text.setFont(parent.getFont());
		text.setBackground(parent.getBackground());
		text.setText("");//$NON-NLS-1$
		text.addModifyListener(getModifyListener());
		return text;
	}

	@Override
	protected void focusLost() {
		if (!isProposalPopupOpen()) {
			super.focusLost();
		}
	}

	/**
	 * Create the text control
	 *
	 * @param parent The parent control
	 * @return The text control
	 */
	protected StyledText createStyledText(final Composite parent) {
		return new StyledText(parent, getStyle());
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> framework method returns the text string.
	 *
	 * @return the text string
	 */
	@Override
	protected Object doGetValue() {
		return text.getText();
	}

	@Override
	protected void doSetFocus() {
		if (text != null) {
			text.selectAll();
			text.setFocus();
			checkSelection();
			checkDeleteable();
			checkSelectable();
		}
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> framework method accepts a text string (type
	 * <code>String</code>).
	 *
	 * @param value a text string (type <code>String</code>)
	 */
	@Override
	protected void doSetValue(final Object value) {
		Assert.isTrue(text != null && (value instanceof String));
		text.removeModifyListener(getModifyListener());
		text.setText((String) value);
		text.addModifyListener(getModifyListener());
	}

	/**
	 * Processes a modify event that occurred in this text cell editor. This
	 * framework method performs validation and sets the error message accordingly,
	 * and then reports a change via <code>fireEditorValueChanged</code>. Subclasses
	 * should call this method at appropriate times. Subclasses may extend or
	 * reimplement.
	 *
	 * @param e the SWT modify event
	 */
	protected void editOccured(final ModifyEvent e) {
		String value = text.getText();
		if (value == null) {
			value = "";//$NON-NLS-1$
		}
		final Object typedValue = value;
		final boolean oldValidState = isValueValid();
		final boolean newValidState = isCorrect(typedValue);
		if (!newValidState) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(), value));
		}
		valueChanged(oldValidState, newValidState);
	}

	/** Since a text editor field is scrollable we don't set a minimumSize. */
	@Override
	public LayoutData getLayoutData() {
		final LayoutData data = new LayoutData();
		data.minimumWidth = 0;
		return data;
	}

	/** Return the modify listener. */
	private ModifyListener getModifyListener() {
		if (modifyListener == null) {
			modifyListener = this::editOccured;
		}
		return modifyListener;
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method returns <code>true</code> if the current
	 * selection is not empty.
	 */
	@Override
	public boolean isCopyEnabled() {
		if (text == null || text.isDisposed()) {
			return false;
		}
		return text.getSelectionCount() > 0;
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method returns <code>true</code> if the current
	 * selection is not empty.
	 */
	@Override
	public boolean isCutEnabled() {
		if (text == null || text.isDisposed()) {
			return false;
		}
		return text.getSelectionCount() > 0;
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method returns <code>true</code> if there is a
	 * selection or if the caret is not positioned at the end of the text.
	 */
	@Override
	public boolean isDeleteEnabled() {
		if (text == null || text.isDisposed()) {
			return false;
		}
		return text.getSelectionCount() > 0 || text.getCaretOffset() < text.getCharCount();
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method always returns <code>true</code>.
	 */
	@Override
	public boolean isPasteEnabled() {
		return (text == null || text.isDisposed());
	}

	/**
	 * Check if save all is enabled
	 *
	 * @return true if it is
	 */
	public boolean isSaveAllEnabled() {
		return (text == null || text.isDisposed());
	}

	/**
	 * Returns <code>true</code> if this cell editor is able to perform the select
	 * all action.
	 * <p>
	 * This default implementation always returns <code>false</code>.
	 * </p>
	 * <p>
	 * Subclasses may override
	 * </p>
	 *
	 * @return <code>true</code> if select all is possible, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean isSelectAllEnabled() {
		if (text == null || text.isDisposed()) {
			return false;
		}
		return text.getCharCount() > 0;
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method copies the current selection to the clipboard.
	 */
	@Override
	public void performCopy() {
		text.copy();
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method cuts the current selection to the clipboard.
	 */
	@Override
	public void performCut() {
		text.cut();
		checkSelection();
		checkDeleteable();
		checkSelectable();
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method deletes the current selection or, if there is
	 * no selection, the character next character from the current position.
	 */
	@Override
	public void performDelete() {
		if (text.getSelectionCount() > 0) {
			// remove the contents of the current selection
			text.insert(""); //$NON-NLS-1$
		} else {
			// remove the next character
			final int pos = text.getCaretOffset();
			if (pos < text.getCharCount()) {
				text.setSelection(pos, pos + 1);
				text.insert(""); //$NON-NLS-1$
			}
		}
		checkSelection();
		checkDeleteable();
		checkSelectable();
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method pastes the the clipboard contents over the
	 * current selection.
	 */
	@Override
	public void performPaste() {
		text.paste();
		checkSelection();
		checkDeleteable();
		checkSelectable();
	}

	/**
	 * The <code>StyledTextCellEditor</code> implementation of this
	 * <code>CellEditor</code> method selects all of the current text.
	 */
	@Override
	public void performSelectAll() {
		text.selectAll();
		checkSelection();
		checkDeleteable();
	}

	/**
	 * This implementation of {@link CellEditor#dependsOnExternalFocusListener()}
	 * returns false if the current instance's class is StyledTextCellEditor, and
	 * true otherwise. Subclasses that hook their own focus listener should override
	 * this method and return false. See also bug 58777.
	 *
	 * @since 3.4
	 */
	@Override
	protected boolean dependsOnExternalFocusListener() {
		return getClass() != StyledTextCellEditor.class;
	}

	/**
	 * Check whether a proposal popup is open
	 *
	 * @return The popup proposal open status
	 */
	@SuppressWarnings("static-method")
	protected boolean isProposalPopupOpen() {
		return false;
	}

	/**
	 * Returns whether to commit by pressing enter with the control key
	 *
	 * @return The commit with control key behavior
	 */
	private boolean isCommitWithCtrlKey() {
		return (getStyle() & SWT.MULTI) != 0;
	}
}
