/*******************************************************************************
 * Copyright (c) 2012, 2022 Original authors and others.
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 469486
 *     Martin Jobst - copied from TextCellEditor and adapted for StyledText
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.RenderErrorHandling;
import org.eclipse.nebula.widgets.nattable.edit.editor.AbstractCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ControlDecorationProvider;
import org.eclipse.nebula.widgets.nattable.edit.editor.EditorSelectionEnum;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IEditErrorHandler;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * {@link ICellEditor} implementation that wraps a SWT {@link StyledText}
 * control to support text editing.
 */
public class StyledTextCellEditor extends AbstractCellEditor {

	/**
	 * The StyledText control which is the editor wrapped by this
	 * StyledTextCellEditor.
	 */
	private StyledText text = null;

	/** Flag to configure if the wrapped text editor control is editable or not. */
	private boolean editable = true;

	/**
	 * Flag to configure whether the editor should commit and move the selection in
	 * the corresponding way if the up or down key is pressed.
	 */
	private final boolean commitOnUpDown;

	/**
	 * Flag to configure whether the editor should commit and move the selection in
	 * the corresponding way if the left or right key is pressed on the according
	 * content edge.
	 */
	private final boolean commitOnLeftRight;

	/**
	 * Flag to configure whether the selection should move after a value was
	 * committed after pressing enter.
	 */
	private final boolean moveSelectionOnEnter;

	/**
	 * The selection mode that should be used on activating the wrapped text
	 * control. By default the behaviour is to set the selection at the end of the
	 * containing text if the text editor control is activated with an initial
	 * value. If it is activated only specifying the original canonical value, the
	 * default behaviour is to select the whole text contained in the text editor
	 * control.
	 *
	 * <p>
	 * You can override this default behaviour by setting an
	 * {@link EditorSelectionEnum} explicitly. With this you are able e.g. to set
	 * the selection at the beginning of the contained text, so writing in the text
	 * control will result in prefixing.
	 *
	 * <p>
	 * Note that on overriding the behaviour, you override both activation cases.
	 */
	private EditorSelectionEnum selectionMode;

	/**
	 * The {@link ControlDecorationProvider} responsible for adding a
	 * {@link ControlDecoration} to the wrapped editor control. Can be configured
	 * via convenience methods of this StyledTextCellEditor.
	 */
	protected final ControlDecorationProvider decorationProvider = new ControlDecorationProvider();

	/**
	 * The {@link IEditErrorHandler} that is used for showing conversion errors on
	 * typing into this editor. By default this is the {@link RenderErrorHandling}
	 * which will render the content in the editor red to indicate a conversion
	 * error.
	 */
	private IEditErrorHandler inputConversionErrorHandler = new RenderErrorHandling(this.decorationProvider);

	/**
	 * The {@link IEditErrorHandler} that is used for showing validation errors on
	 * typing into this editor. By default this is the {@link RenderErrorHandling}
	 * which will render the content in the editor red to indicate a validation
	 * error.
	 */
	private IEditErrorHandler inputValidationErrorHandler = new RenderErrorHandling(this.decorationProvider);

	/**
	 * Flag to determine whether this editor should try to commit and close on
	 * pressing the ENTER key. The default is <code>true</code>. For a multi line
	 * text editor, the ENTER key might be used to insert a new line instead of
	 * committing the value when opened in a dialog. In that case the value should
	 * not be committed, as applying the dialog will trigger the commit. For inline
	 * editors setting {@link #commitWithCtrlKey} to <code>true</code> might be
	 * interesting in combination with setting this value to <code>true</code>,
	 * which means that the commit operation is only performed if CTRL + ENTER is
	 * pressed.
	 */
	protected boolean commitOnEnter = true;

	/**
	 * Flag to determine whether this editor should try to commit and close on
	 * pressing the enter key in combination with the CTRL key. It is only
	 * interpreted with {@link #commitOnEnter} set to <code>true</code>, and it is
	 * needed for a multi line text editor where a simple enter press should add a
	 * new line and a combination with CTRL should commit the value.
	 *
	 * @since 1.6
	 */
	private boolean commitWithCtrlKey = false;

	/**
	 * @see ContentProposalAdapter#ContentProposalAdapter(Control,
	 *      IControlContentAdapter, IContentProposalProvider, KeyStroke, char[])
	 * @since 1.4
	 */
	protected IControlContentAdapter controlContentAdapter;
	/**
	 * @see ContentProposalAdapter#ContentProposalAdapter(Control,
	 *      IControlContentAdapter, IContentProposalProvider, KeyStroke, char[])
	 * @since 1.4
	 */
	protected IContentProposalProvider proposalProvider;
	/**
	 * @see ContentProposalAdapter#ContentProposalAdapter(Control,
	 *      IControlContentAdapter, IContentProposalProvider, KeyStroke, char[])
	 * @since 1.4
	 */
	protected KeyStroke keyStroke;
	/**
	 * @see ContentProposalAdapter#ContentProposalAdapter(Control,
	 *      IControlContentAdapter, IContentProposalProvider, KeyStroke, char[])
	 * @since 1.4
	 */
	protected char[] autoActivationCharacters;
	/**
	 * @see ContentProposalAdapter#setProposalAcceptanceStyle(int)
	 * @since 2.0
	 */
	protected int proposalAcceptanceStyle = ContentProposalAdapter.PROPOSAL_REPLACE;
	/**
	 * @see ContentProposalAdapter#setAutoActivationDelay(int)
	 * @since 2.0
	 */
	protected int autoActivationDelay = 0;
	/**
	 * The active {@link ContentProposalAdapter} if supported.
	 *
	 * @since 2.0
	 */
	protected ContentProposalAdapter contentProposalAdapter;

	/**
	 * Creates the default StyledTextCellEditor that does not commit on pressing the
	 * up/down arrow keys and will not move the selection on committing a value by
	 * pressing enter.
	 */
	public StyledTextCellEditor() {
		this(false);
	}

	/**
	 * Creates a StyledTextCellEditor that will not move the selection on committing
	 * a value by pressing enter.
	 *
	 * @param commitOnUpDown Flag to configure whether the editor should commit and
	 *                       move the selection in the corresponding way if the up
	 *                       or down key is pressed.
	 */
	public StyledTextCellEditor(final boolean commitOnUpDown) {
		this(commitOnUpDown, false);
	}

	/**
	 * Creates a StyledTextCellEditor that will not move the selection on pressing
	 * the left or right arrow keys on the according edges.
	 *
	 * @param commitOnUpDown       Flag to configure whether the editor should
	 *                             commit and move the selection in the
	 *                             corresponding way if the up or down key is
	 *                             pressed.
	 * @param moveSelectionOnEnter Flag to configure whether the selection should
	 *                             move after a value was committed after pressing
	 *                             enter.
	 */
	public StyledTextCellEditor(final boolean commitOnUpDown, final boolean moveSelectionOnEnter) {
		this(commitOnUpDown, moveSelectionOnEnter, false);
	}

	/**
	 * Creates a StyledTextCellEditor.
	 *
	 * @param commitOnUpDown       Flag to configure whether the editor should
	 *                             commit and move the selection in the
	 *                             corresponding way if the up or down key is
	 *                             pressed.
	 * @param moveSelectionOnEnter Flag to configure whether the selection should
	 *                             move after a value was committed after pressing
	 *                             enter.
	 * @param commitOnLeftRight    Flag to configure whether the editor should
	 *                             commit and move the selection in the
	 *                             corresponding way if the left or right key is
	 *                             pressed on the according content edge.
	 * @since 1.4
	 */
	public StyledTextCellEditor(final boolean commitOnUpDown, final boolean moveSelectionOnEnter,
			final boolean commitOnLeftRight) {
		this.commitOnUpDown = commitOnUpDown;
		this.moveSelectionOnEnter = moveSelectionOnEnter;
		this.commitOnLeftRight = commitOnLeftRight;
	}

	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		this.text = createEditorControl(parent);

		// If the originalCanonicalValue is a Character it is possible the
		// editor is activated by keypress
		if (originalCanonicalValue instanceof Character) {
			this.text.setText(originalCanonicalValue.toString());
			selectText(this.selectionMode != null ? this.selectionMode : EditorSelectionEnum.END);
		}
		// if there is no initial value, handle the original canonical value to
		// transfer it to the text control
		else {
			setCanonicalValue(originalCanonicalValue);
			selectText(this.selectionMode != null ? this.selectionMode : EditorSelectionEnum.ALL);
		}

		if (!isEditable()) {
			this.text.setEditable(false);
		}

		// show an error decoration if this is enabled
		this.decorationProvider.createErrorDecorationIfRequired(this.text);

		// if the input error handlers are of type RenderErrorHandler (default)
		// than we also check for a possible configured error styling in the
		// configuration
		// Note: this is currently only implemented in here, as the
		// StyledTextCellEditor is the only editor that supports just in time
		// conversion/validation
		if (this.inputConversionErrorHandler instanceof final RenderErrorHandling renderErrorHandling) {
			final IStyle conversionErrorStyle = this.configRegistry
					.getConfigAttribute(EditConfigAttributes.CONVERSION_ERROR_STYLE, DisplayMode.EDIT, this.labelStack);
			renderErrorHandling.setErrorStyle(conversionErrorStyle);
		}

		if (this.inputValidationErrorHandler instanceof final RenderErrorHandling renderErrorHandling) {
			final IStyle validationErrorStyle = this.configRegistry
					.getConfigAttribute(EditConfigAttributes.VALIDATION_ERROR_STYLE, DisplayMode.EDIT, this.labelStack);
			renderErrorHandling.setErrorStyle(validationErrorStyle);
		}

		// if a IControlContentAdapter is registered, create and register a
		// ContentProposalAdapter
		if (this.controlContentAdapter != null) {
			configureContentProposalAdapter(new ContentProposalAdapter(this.text, this.controlContentAdapter,
					this.proposalProvider, this.keyStroke, this.autoActivationCharacters));
		}

		this.text.forceFocus();

		return this.text;
	}

	@Override
	public String getEditorValue() {
		return this.text.getText();
	}

	@Override
	public void setEditorValue(final Object value) {
		this.text.setText(value != null && value.toString().length() > 0 ? value.toString() : ""); //$NON-NLS-1$
	}

	@Override
	public Control getEditorControl() {
		return this.text;
	}

	@Override
	public StyledText createEditorControl(final Composite parent) {
		int style = HorizontalAlignmentEnum.getSWTStyle(this.cellStyle);
		if (this.editMode == EditModeEnum.DIALOG) {
			style = style | SWT.BORDER;
		}
		return createEditorControl(parent, style);
	}

	/**
	 * Creates the editor control that is wrapped by this ICellEditor. Will use the
	 * style configurations in ConfigRegistry for styling the control.
	 *
	 * @param parent The Composite that will be the parent of the new editor
	 *               control. Can not be <code>null</code>
	 * @param style  The SWT style of the text control to create.
	 * @return The created editor control that is wrapped by this ICellEditor.
	 */
	protected StyledText createEditorControl(final Composite parent, final int style) {
		// create the Text control based on the specified style
		final StyledText textControl = createStyledText(parent, style);

		// set style information configured in the associated cell style
		textControl.setBackground(this.cellStyle.getAttributeValue(CellStyleAttributes.BACKGROUND_COLOR));
		textControl.setForeground(this.cellStyle.getAttributeValue(CellStyleAttributes.FOREGROUND_COLOR));
		textControl.setFont(this.cellStyle.getAttributeValue(CellStyleAttributes.FONT));

		textControl.setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_IBEAM));

		// add a key listener that will commit or close the editor for special
		// key strokes and executes conversion/validation on input to the editor
		// use verify key listener since the regular key listener would be too late
		textControl.addVerifyKeyListener(event -> {
			// if the proposal popup is open we do not handle keystrokes
			// ourself to ensure proposal handling is working correctly
			if (event.doit && !isProposalPopupOpen()) {
				if (isCommitOnEnter() && (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR)) {

					boolean commit = event.stateMask != SWT.MOD3;
					if (isCommitWithCtrlKey()) {
						commit = event.stateMask == SWT.MOD1;
					}
					MoveDirectionEnum move = MoveDirectionEnum.NONE;
					if (StyledTextCellEditor.this.moveSelectionOnEnter
							&& StyledTextCellEditor.this.editMode == EditModeEnum.INLINE) {
						if (event.stateMask == 0) {
							move = MoveDirectionEnum.DOWN;
						} else if (event.stateMask == SWT.MOD2) {
							move = MoveDirectionEnum.UP;
						}
					}

					if (commit) {
						commit(move);
						event.doit = false;
					}

					if (StyledTextCellEditor.this.editMode == EditModeEnum.DIALOG) {
						parent.forceFocus();
					}
				} else if (event.keyCode == SWT.ESC && event.stateMask == 0) {
					close();
					event.doit = false;
				} else if ((StyledTextCellEditor.this.commitOnUpDown || StyledTextCellEditor.this.commitOnLeftRight)
						&& StyledTextCellEditor.this.editMode == EditModeEnum.INLINE) {

					final Text control = (Text) event.widget;

					if (StyledTextCellEditor.this.commitOnUpDown && event.keyCode == SWT.ARROW_UP) {
						commit(MoveDirectionEnum.UP);
						event.doit = false;
					} else if (StyledTextCellEditor.this.commitOnUpDown && event.keyCode == SWT.ARROW_DOWN) {
						commit(MoveDirectionEnum.DOWN);
						event.doit = false;
					} else if (StyledTextCellEditor.this.commitOnLeftRight && control.getSelectionCount() == 0
							&& event.keyCode == SWT.ARROW_LEFT && control.getCaretPosition() == 0) {
						commit(MoveDirectionEnum.LEFT);
						event.doit = false;
					} else if (StyledTextCellEditor.this.commitOnLeftRight && control.getSelectionCount() == 0
							&& event.keyCode == SWT.ARROW_RIGHT
							&& control.getCaretPosition() == control.getCharCount()) {
						commit(MoveDirectionEnum.RIGHT);
						event.doit = false;
					}
				}
			}
		});
		textControl.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent event) {
				// handled in verify key listener above
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				try {
					// always do the conversion
					final Object canonicalValue = getCanonicalValue(
							StyledTextCellEditor.this.inputConversionErrorHandler);
					// and always do the validation, even if for committing the
					// validation should be skipped, on editing
					// a validation failure should be made visible
					// otherwise there would be no need for validation!
					validateCanonicalValue(canonicalValue, StyledTextCellEditor.this.inputValidationErrorHandler);
				} catch (final Exception ex) {
					// do nothing as exceptions caused by conversion or
					// validation are handled already we just need this catch
					// block for stopping the process if conversion failed with
					// an exception
				}
			}
		});

		return textControl;
	}

	/**
	 * Creates the styled text control.
	 *
	 * @param parent The parent control
	 * @param style  The style
	 * @return The text control
	 */
	@SuppressWarnings("static-method")
	protected StyledText createStyledText(final Composite parent, final int style) {
		return new StyledText(parent, style);
	}

	@Override
	public void close() {
		// ensure to reset the error handlers in case this editor was closed
		// rendering invalid this is necessary because if the editor is closed
		// rendering invalid, opening the editor again inserting an invalid
		// value again, it is not rendered invalid because of a wrong state in
		// the internal error handlers
		if (this.inputConversionErrorHandler != null) {
			this.inputConversionErrorHandler.removeError(this);
		}
		if (this.inputValidationErrorHandler != null) {
			this.inputValidationErrorHandler.removeError(this);
		}

		super.close();

		this.decorationProvider.dispose();
	}

	/**
	 * @return <code>true</code> if the wrapped Text control is editable,
	 *         <code>false</code> if not.
	 */
	public boolean isEditable() {
		return this.editable;
	}

	/**
	 * @param editable <code>true</code> if the wrapped Text control should be
	 *                 editable, <code>false</code> if not.
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/**
	 * Returns the current configured selection mode that is used on activating the
	 * wrapped text editor control. By default this is <code>null</code> which
	 * causes the following default behaviour. If the text editor control is
	 * activated with an initial value then the selection is set at the end of the
	 * containing text. If it is activated only specifying the original canonical
	 * value, the default behaviour is to select the whole text contained in the
	 * text editor control.
	 *
	 * @return The current configured selection mode, <code>null</code> for default
	 *         behaviour.
	 */
	public final EditorSelectionEnum getSelectionMode() {
		return this.selectionMode;
	}

	/**
	 * Set the selection mode that should be used on the content of the wrapped text
	 * editor control when it gets activated. By setting a value explicitly you
	 * configure the selection mode for both cases, activating the wrapped text
	 * editor control with and without an initial value. Setting this value to
	 * <code>null</code> will reactivate the default behaviour like described here
	 * {@link StyledTextCellEditor#getSelectionMode()}.
	 *
	 * @param selectionMode The selection mode that should be used on the content of
	 *                      the wrapped text editor control when it gets activated.
	 */
	public final void setSelectionMode(final EditorSelectionEnum selectionMode) {
		this.selectionMode = selectionMode;
	}

	/**
	 * Will set the selection to the wrapped text control regarding the configured
	 * {@link EditorSelectionEnum}.
	 *
	 * <p>
	 * This method is called
	 *
	 * @see Text#setSelection(int, int)
	 */
	private void selectText(final EditorSelectionEnum selectionMode) {
		final int textLength = this.text.getText().length();
		if (textLength > 0) {
			if (selectionMode == EditorSelectionEnum.ALL) {
				this.text.setSelection(0, textLength);
			} else if (selectionMode == EditorSelectionEnum.END) {
				this.text.setSelection(textLength, textLength);
			} else if (selectionMode == EditorSelectionEnum.START) {
				this.text.setSelection(0);
			}
		}
	}

	/**
	 * @return The {@link ControlDecorationProvider} responsible for adding a
	 *         {@link ControlDecoration} to the wrapped editor control.
	 */
	public ControlDecorationProvider getDecorationProvider() {
		return this.decorationProvider;
	}

	/**
	 * Enables/disables the error decoration for the wrapped text control.
	 *
	 * @param enabled <code>true</code> if an error decoration should be added to
	 *                the wrapped text control, <code>false</code> if not.
	 */
	public void setErrorDecorationEnabled(final boolean enabled) {
		this.decorationProvider.setErrorDecorationEnabled(enabled);
	}

	/**
	 * Force the error decoration hover to show immediately.
	 *
	 * @param customErrorText The text to show in the hover popup.
	 *
	 * @see ControlDecoration#show()
	 * @see ControlDecoration#showHoverText(String)
	 */
	public void showErrorDecorationHover(final String customErrorText) {
		this.decorationProvider.showErrorDecorationHover(customErrorText);
	}

	/**
	 * Set the id of the {@link FieldDecoration} to be used by the local
	 * {@link ControlDecorationProvider}.
	 *
	 * @param fieldDecorationId The String to determine the {@link FieldDecoration}
	 *                          to use by the {@link ControlDecoration} that is
	 *                          provided by this {@link ControlDecorationProvider}.
	 *
	 * @see org.eclipse.jface.fieldassist.FieldDecorationRegistry#getFieldDecoration(String)
	 */
	public void setFieldDecorationId(final String fieldDecorationId) {
		this.decorationProvider.setFieldDecorationId(fieldDecorationId);
	}

	/**
	 * Set the position of the control decoration relative to the control. It should
	 * include style bits describing both the vertical and horizontal orientation.
	 *
	 * @param decorationPositionOverride bit-wise or of position constants
	 *                                   (<code>SWT.TOP</code>,
	 *                                   <code>SWT.BOTTOM</code>,
	 *                                   <code>SWT.LEFT</code>,
	 *                                   <code>SWT.RIGHT</code>, and
	 *                                   <code>SWT.CENTER</code>).
	 *
	 * @see ControlDecoration#ControlDecoration(Control, int)
	 */
	public void setDecorationPositionOverride(final int decorationPositionOverride) {
		this.decorationProvider.setDecorationPositionOverride(decorationPositionOverride);
	}

	/**
	 * @return The {@link IEditErrorHandler} that is used for showing conversion
	 *         errors on typing into this editor. By default this is the
	 *         {@link RenderErrorHandling} which will render the content in the
	 *         editor red to indicate a conversion error.
	 */
	public IEditErrorHandler getInputConversionErrorHandler() {
		return this.inputConversionErrorHandler;
	}

	/**
	 * @param inputConversionErrorHandler The {@link IEditErrorHandler} that is
	 *                                    should be used for showing conversion
	 *                                    errors on typing into this editor.
	 */
	public void setInputConversionErrorHandler(final IEditErrorHandler inputConversionErrorHandler) {
		this.inputConversionErrorHandler = inputConversionErrorHandler;
	}

	/**
	 * @return The {@link IEditErrorHandler} that is used for showing validation
	 *         errors on typing into this editor. By default this is the
	 *         {@link RenderErrorHandling} which will render the content in the
	 *         editor red to indicate a validation error.
	 */
	public IEditErrorHandler getInputValidationErrorHandler() {
		return this.inputValidationErrorHandler;
	}

	/**
	 * @param inputValidationErrorHandler The {@link IEditErrorHandler} that is
	 *                                    should used for showing validation errors
	 *                                    on typing into this editor.
	 */
	public void setInputValidationErrorHandler(final IEditErrorHandler inputValidationErrorHandler) {
		this.inputValidationErrorHandler = inputValidationErrorHandler;
	}

	/**
	 * Configure the parameters necessary to create the content proposal adapter on
	 * opening an editor.
	 *
	 * @param controlContentAdapter    the <code>IControlContentAdapter</code> used
	 *                                 to obtain and update the control's contents
	 *                                 as proposals are accepted. May not be
	 *                                 <code>null</code>.
	 * @param proposalProvider         the <code>IContentProposalProvider</code>
	 *                                 used to obtain content proposals for this
	 *                                 control, or <code>null</code> if no content
	 *                                 proposal is available.
	 * @param keyStroke                the keystroke that will invoke the content
	 *                                 proposal popup. If this value is
	 *                                 <code>null</code>, then proposals will be
	 *                                 activated automatically when any of the auto
	 *                                 activation characters are typed.
	 * @param autoActivationCharacters An array of characters that trigger
	 *                                 auto-activation of content proposal. If
	 *                                 specified, these characters will trigger
	 *                                 auto-activation of the proposal popup,
	 *                                 regardless of whether an explicit invocation
	 *                                 keyStroke was specified. If this parameter is
	 *                                 <code>null</code>, then only a specified
	 *                                 keyStroke will invoke content proposal. If
	 *                                 this parameter is <code>null</code> and the
	 *                                 keyStroke parameter is <code>null</code>,
	 *                                 then all alphanumeric characters will
	 *                                 auto-activate content proposal.
	 *
	 * @see ContentProposalAdapter
	 * @since 1.4
	 */
	public void enableContentProposal(final IControlContentAdapter controlContentAdapter,
			final IContentProposalProvider proposalProvider, final KeyStroke keyStroke,
			final char[] autoActivationCharacters) {

		enableContentProposal(controlContentAdapter, proposalProvider, keyStroke, autoActivationCharacters,
				ContentProposalAdapter.PROPOSAL_REPLACE, 0);
	}

	/**
	 * Configure the parameters necessary to create the content proposal adapter on
	 * opening an editor.
	 *
	 * @param controlContentAdapter    the <code>IControlContentAdapter</code> used
	 *                                 to obtain and update the control's contents
	 *                                 as proposals are accepted. May not be
	 *                                 <code>null</code>.
	 * @param proposalProvider         the <code>IContentProposalProvider</code>
	 *                                 used to obtain content proposals for this
	 *                                 control, or <code>null</code> if no content
	 *                                 proposal is available.
	 * @param keyStroke                the keystroke that will invoke the content
	 *                                 proposal popup. If this value is
	 *                                 <code>null</code>, then proposals will be
	 *                                 activated automatically when any of the auto
	 *                                 activation characters are typed.
	 * @param autoActivationCharacters An array of characters that trigger
	 *                                 auto-activation of content proposal. If
	 *                                 specified, these characters will trigger
	 *                                 auto-activation of the proposal popup,
	 *                                 regardless of whether an explicit invocation
	 *                                 keyStroke was specified. If this parameter is
	 *                                 <code>null</code>, then only a specified
	 *                                 keyStroke will invoke content proposal. If
	 *                                 this parameter is <code>null</code> and the
	 *                                 keyStroke parameter is <code>null</code>,
	 *                                 then all alphanumeric characters will
	 *                                 auto-activate content proposal.
	 * @param proposalAcceptanceStyle  a constant indicating how an accepted
	 *                                 proposal should affect the control's content.
	 *                                 Should be one of
	 *                                 <code>PROPOSAL_INSERT</code>,
	 *                                 <code>PROPOSAL_REPLACE</code>, or
	 *                                 <code>PROPOSAL_IGNORE</code>
	 * @param autoActivationDelay      the time in milliseconds that will pass
	 *                                 before a popup is automatically opened
	 *
	 * @see ContentProposalAdapter
	 * @since 2.0
	 */
	public void enableContentProposal(final IControlContentAdapter controlContentAdapter,
			final IContentProposalProvider proposalProvider, final KeyStroke keyStroke,
			final char[] autoActivationCharacters, final int proposalAcceptanceStyle, final int autoActivationDelay) {

		this.controlContentAdapter = controlContentAdapter;
		this.proposalProvider = proposalProvider;
		this.keyStroke = keyStroke;
		this.autoActivationCharacters = autoActivationCharacters;
		this.proposalAcceptanceStyle = proposalAcceptanceStyle;
		this.autoActivationDelay = autoActivationDelay;

		this.traverseListener = new InlineTraverseListener() {
			@Override
			public void keyTraversed(final TraverseEvent event) {
				// only handle in case a content proposal popup is not open
				if (!isProposalPopupOpen()) {
					super.keyTraversed(event);
				}
			}
		};
	}

	/**
	 * Adds the listeners necessary for interaction between the control of this
	 * StyledTextCellEditor and the ContentProposalAdapter.
	 *
	 * @param contentProposalAdapter The {@link ContentProposalAdapter} that should
	 *                               be used to add content proposal abilities to
	 *                               this {@link StyledTextCellEditor}.
	 * @since 1.4
	 */
	protected void configureContentProposalAdapter(final ContentProposalAdapter contentProposalAdapter) {
		this.contentProposalAdapter = contentProposalAdapter;

		// add the necessary listeners to support the interaction between the
		// content proposal and this text editor
		contentProposalAdapter.addContentProposalListener(proposal -> {
			if (StyledTextCellEditor.this.editMode == EditModeEnum.INLINE) {
				commit(MoveDirectionEnum.NONE);
				StyledTextCellEditor.this.contentProposalAdapter = null;
			}
		});

		contentProposalAdapter.addContentProposalListener(new IContentProposalListener2() {

			@Override
			public void proposalPopupClosed(final ContentProposalAdapter adapter) {
				if (StyledTextCellEditor.this.focusListener instanceof final InlineFocusListener inlineFL) {
					inlineFL.handleFocusChanges = true;
				}
			}

			@Override
			public void proposalPopupOpened(final ContentProposalAdapter adapter) {
				if (StyledTextCellEditor.this.focusListener instanceof final InlineFocusListener inlineFL) {
					inlineFL.handleFocusChanges = false;
				}
			}
		});

		contentProposalAdapter.setProposalAcceptanceStyle(this.proposalAcceptanceStyle);
		contentProposalAdapter.setAutoActivationDelay(this.autoActivationDelay);
	}

	/**
	 * Return whether this editor should try to commit and close on pressing the
	 * ENTER key. The default is <code>true</code>. For a multi line text editor,
	 * the ENTER key might be used to insert a new line instead of committing the
	 * value when opened in a dialog. In that case the value should not be
	 * committed, as applying the dialog will trigger the commit. For inline editors
	 * setting {@link #commitWithCtrlKey} to <code>true</code> might be interesting
	 * in combination with setting this value to <code>true</code>, which means that
	 * the commit operation is only performed if CTRL + ENTER is pressed.
	 *
	 * @return <code>true</code> if commit and close is performed on pressing the
	 *         ENTER key, <code>false</code> if pressing ENTER does not perform a
	 *         commit operation.
	 *
	 * @since 1.6
	 */
	public boolean isCommitOnEnter() {
		return this.commitOnEnter;
	}

	/**
	 * Configure whether this editor should try to commit and close on pressing the
	 * ENTER key. The default is <code>true</code>. For a multi line text editor,
	 * the ENTER key might be used to insert a new line instead of committing the
	 * value when opened in a dialog. In that case the value should not be
	 * committed, as applying the dialog will trigger the commit. For inline editors
	 * setting {@link #commitWithCtrlKey} to <code>true</code> might be interesting
	 * in combination with setting this value to <code>true</code>, which means that
	 * the commit operation is only performed if CTRL + ENTER is pressed.
	 *
	 * @param commitOnEnter <code>true</code> to perform commit and close on
	 *                      pressing the ENTER key, <code>false</code> if pressing
	 *                      ENTER should not perform a commit operation.
	 *
	 * @since 1.6
	 */
	public void setCommitOnEnter(final boolean commitOnEnter) {
		this.commitOnEnter = commitOnEnter;
	}

	/**
	 * Return whether this editor should try to commit and close on pressing the
	 * ENTER key in combination with the CTRL state mask key, or if pressing ENTER
	 * solely should work. It is only interpreted with {@link #commitOnEnter} set to
	 * <code>true</code>, and it is needed for a multi line text editor where a
	 * simple enter press should add a new line and a combination with CTRL should
	 * commit the value.
	 *
	 * @return <code>true</code> if committing via pressing the ENTER key is only
	 *         working with the CTRL state mask key pressed. <code>false</code> if
	 *         pressing the ENTER key solely is performing the commit.
	 *
	 * @since 1.6
	 */
	public boolean isCommitWithCtrlKey() {
		return this.commitWithCtrlKey;
	}

	/**
	 * Configure whether this editor should try to commit and close on pressing the
	 * ENTER key in combination with the CTRL state mask key, or if pressing ENTER
	 * solely should work. It is only interpreted with {@link #commitOnEnter} set to
	 * <code>true</code>, and it is needed for a multi line text editor where a
	 * simple enter press should add a new line and a combination with CTRL should
	 * commit the value.
	 *
	 * @param commitWithCtrlKey <code>true</code> if committing via pressing the
	 *                          ENTER key should only work with the CTRL state mask
	 *                          key pressed. <code>false</code> if pressing the
	 *                          ENTER key solely should perform the commit.
	 *
	 * @since 1.6
	 */
	public void setCommitWithCtrlKey(final boolean commitWithCtrlKey) {
		this.commitWithCtrlKey = commitWithCtrlKey;
	}

	/**
	 * @return <code>true</code> if a {@link ContentProposalAdapter} is active and
	 *         the proposal popup is open.
	 * @since 2.0
	 */
	protected boolean isProposalPopupOpen() {
		return this.contentProposalAdapter != null && this.contentProposalAdapter.isProposalPopupOpen();
	}
}
