/*******************************************************************************
 * Copyright (c) 2020, 2022 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - added option to show error messages at the bottom right
 *                  instead of the mouse position
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.handlers.internal;

import org.eclipse.fordiac.ide.ui.handlers.ErrorMessageHandler;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public final class ErrorMessageDialog extends PopupDialog {

	private static final int ERROR_TEXT_WIDTH_HINT = 400;
	private static final int MOUSE_CURSOR_OFFSET_Y = 10;
	private static final int MOUSE_CURSOR_OFFSET_X = 5;
	private static final Point MOUSE_CURSOR_OFFSET = new Point(MOUSE_CURSOR_OFFSET_X, MOUSE_CURSOR_OFFSET_Y);
	private final String errorMsg;
	private final int timeout;

	private final ErrorMessageHandler container;

	@Override
	protected Color getBackground() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
	}

	@Override
	protected Color getForeground() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
	}

	public ErrorMessageDialog(final Shell parent, final ErrorMessageHandler container, final String errorMsg,
			final int timeout) {
		super(parent, HOVER_SHELLSTYLE, false, false, false, false, false, null, null);
		this.errorMsg = errorMsg;
		this.timeout = timeout;
		this.container = container;
	}

	@Override
	protected void adjustBounds() {
		super.adjustBounds();
		final Point pt = getDialogPosition();
		final Rectangle boundingBox = getShell().getBounds();
		boundingBox.x = pt.x;
		boundingBox.y = pt.y;
		getShell().setBounds(boundingBox);
	}

	public Point getDialogPosition() {
		final Control focused = getShell().getDisplay().getFocusControl();
		Point pt;
		if (isTextInput(focused)) {
			pt = getTextInputRelativeLocation(focused);
		} else if (isTableInput(focused)) {
			pt = getTableRelativeLocation(focused);
		} else {
			pt = getUnAttachedLocation();
		}
		return pt;
	}

	private static Point getTextInputRelativeLocation(final Control focused) {
		final Rectangle elementBounds = focused.getDisplay().getBounds();
		return focused.toDisplay(new Point(elementBounds.x, elementBounds.y + focused.getSize().y));
	}

	private static Point getTableRelativeLocation(final Control focused) {
		final Table table = (Table) focused;
		final TableItem ti = table.getItem(table.getSelectionIndex());
		final Rectangle elementBounds = ti.getBounds();
		return focused.toDisplay(new Point(elementBounds.x, elementBounds.y + ti.getBounds().height));
	}

	private Point getUnAttachedLocation() {
		if (showErrorAtMouse()) {
			return getMouseRelativeLocation();
		}
		return getBottomRightLocation();
	}

	public Point getMouseRelativeLocation() {
		final Point pt = getShell().getDisplay().getCursorLocation();
		pt.x += MOUSE_CURSOR_OFFSET.x;
		pt.y += MOUSE_CURSOR_OFFSET.y;
		return pt;
	}

	public Point getBottomRightLocation() {
		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();

		if (win == null) {
			// we couldn't get the workbench window still use mouse position as backup
			return getMouseRelativeLocation();
		}

		final Rectangle parentBounds = win.getShell().getBounds();
		final Point pt = new Point(parentBounds.x + parentBounds.width, parentBounds.y + parentBounds.height);

		final Rectangle boundingBox = getShell().getBounds();
		pt.x -= boundingBox.width;
		pt.y -= boundingBox.height;
		return pt;
	}

	private static boolean isTextInput(final Control focused) {
		return focused instanceof Text && ((Text) focused).getEditable();
	}

	private static boolean isTableInput(final Control focused) {
		if (focused instanceof final Table table) {
			return table.getSelectionCount() != 0 && table.getItem(table.getSelectionIndex()) != null;
		}
		return false;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite warningComposite = new Composite(parent, SWT.NONE);
		warningComposite.setLayout(new GridLayout(2, false));
		warningComposite.setLayoutData(new GridData(0, 0, true, true));

		final Label image = new Label(warningComposite, SWT.NONE);
		image.setImage(parent.getDisplay().getSystemImage(SWT.ICON_WARNING));
		image.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label errorText = new Label(warningComposite, SWT.WRAP);
		errorText.setText(errorMsg); // message is never null (instanceof check in receiver)

		final GridData errorTestLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		errorTestLayoutData.widthHint = ERROR_TEXT_WIDTH_HINT;
		errorText.setLayoutData(errorTestLayoutData);

		final Control focused = getShell().getDisplay().getFocusControl();
		if (isTextInput(focused)) {
			clearPopupOnKeypress(focused);
			clearPopupOnLostFocus(focused);
		} else if (isTableInput(focused)) {
			clearPopupOnKeypress(focused);
			clearPopupOnLostFocus(focused);
			clearPopupOnSelectionChange(focused);
		} else {
			clearPopupAfterTimeout(timeout);
		}

		return warningComposite;
	}

	private void clearPopupAfterTimeout(final int t) {
		if (!container.isHovering()) {
			getShell().getDisplay().timerExec(t, () -> container.clearDialog(getShell()));
		}
	}

	private final FocusListener lostFocusListener = new FocusListener() {
		@Override
		public void focusLost(final FocusEvent e) {
			container.clearDialog(getShell());
		}

		@Override
		public void focusGained(final FocusEvent e) {
			// Nothing to do
		}
	};

	private void clearPopupOnLostFocus(final Control focused) {
		focused.removeFocusListener(lostFocusListener);
		focused.addFocusListener(lostFocusListener);
	}

	private final KeyListener keypressListener = new KeyListener() {
		@Override
		public void keyReleased(final KeyEvent e) {
			// NOP
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			container.clearDialog(getShell());
		}
	};

	private void clearPopupOnKeypress(final Control focused) {
		focused.removeKeyListener(keypressListener);
		focused.addKeyListener(keypressListener);
	}

	private final SelectionListener selectionChangeListener = new SelectionListener() {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			container.clearDialog(getShell());
		}

		@Override
		public void widgetDefaultSelected(final SelectionEvent e) {
			// Nothing to do
		}
	};

	private void clearPopupOnSelectionChange(final Control focused) {
		if (focused instanceof final Table table) {
			table.removeSelectionListener(selectionChangeListener);
			table.addSelectionListener(selectionChangeListener);
		}
	}

	private static boolean showErrorAtMouse() {
		return UIPreferenceConstants.STORE.getBoolean(UIPreferenceConstants.P_SHOW_ERRORS_AT_MOUSE_CURSOR);
	}

}