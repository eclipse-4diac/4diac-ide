/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.handlers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessage;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ErrorMessageHandler {

	private static final int MOUSE_HOVER_DELAY = 700;

	private boolean isHovering = false;

	public synchronized void setHover(final boolean hovering) {
		isHovering = hovering;
		if(!hovering) {
			messages.stream().forEach(ErrorMessage::setInvalid);
			closeAllDialogs();
		}
	}

	public ErrorMessageHandler(final BundleContext context) {
		initEventBroker(context);
	}

	public void stop() {
		if (eventsRegistered) {
			eventsRegistered = !(eventBroker.unsubscribe(receiver));
		}
	}

	public void start() {
		if (!eventsRegistered) {
			eventsRegistered = eventBroker.subscribe(TOPIC_ERRORMESSAGES, receiver);
		}
	}

	private synchronized void initEventBroker(final BundleContext context) {
		if (null == eventBroker) {
			eventBroker = EclipseContextFactory.getServiceContext(context).get(IEventBroker.class);
		}
	}

	private static final String TOPIC_ERRORMESSAGES = "ORG/ECLIPSE/FORDIAC/IDE/ERRORMESSAGES"; //$NON-NLS-1$

	private IEventBroker eventBroker;

	private boolean eventsRegistered = false;

	private final LinkedList<Composite> windows = new LinkedList<>();

	private final Set<ErrorMessage> messages = new HashSet<>();

	private static class ErrorMessageDialog extends PopupDialog {

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

		private ErrorMessageDialog(final ErrorMessageHandler container, final String errorMsg, final int timeout) {
			super(null, HOVER_SHELLSTYLE, false, false, false, false, false, null, null);
			this.errorMsg = errorMsg;
			this.timeout = timeout;
			this.container = container;
		}

		@Override
		protected void adjustBounds() {
			super.adjustBounds();

			final Control focused = getShell().getDisplay().getFocusControl();

			Point pt;
			if (isTextInput(focused)) {
				final Rectangle elementBounds = focused.getDisplay().getBounds();
				pt = focused.toDisplay(new Point(elementBounds.x, elementBounds.y + focused.getSize().y));
			} else if (isTableInput(focused)) {
				final Table table = (Table) focused;
				final TableItem ti = table.getItem(table.getSelectionIndex());
				final Rectangle elementBounds = ti.getBounds();
				pt = focused.toDisplay(new Point(elementBounds.x, elementBounds.y + ti.getBounds().height));
			} else {
				pt = getShell().getDisplay().getCursorLocation();
				pt.x += MOUSE_CURSOR_OFFSET.x;
				pt.y += MOUSE_CURSOR_OFFSET.y;
			}
			final Rectangle boundingBox = getShell().getBounds();
			boundingBox.x = pt.x;
			boundingBox.y = pt.y;
			getShell().setBounds(boundingBox);
		}

		private boolean isTextInput(final Control focused) {
			return focused instanceof Text && ((Text) focused).getEditable();
		}

		private boolean isTableInput(final Control focused) {
			if (focused instanceof Table) {
				final Table table = (Table) focused;
				return table.getSelectionCount() != 0 && table.getItem(table.getSelectionIndex()) instanceof TableItem;
			}
			return false;
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Composite warningComposite = new Composite(parent, SWT.NONE);
			warningComposite.setLayout(new GridLayout(2, false));
			warningComposite.setLayoutData(new GridData(0, 0, true, false));

			final Label image = new Label(warningComposite, SWT.NONE);
			image.setImage(parent.getDisplay().getSystemImage(SWT.ICON_WARNING));
			image.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			final Label text = new Label(warningComposite, SWT.NONE);
			text.setText(errorMsg); // message is never null (instanceof check in receiver)

			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

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
			if(!container.isHovering) {
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
			if(focused instanceof Table) {
				final Table table = (Table) focused;
				table.removeSelectionListener(selectionChangeListener);
				table.addSelectionListener(selectionChangeListener);
			}
		}

	}

	private synchronized void clearDialog(final Composite dialogArea) {
		// This method is not inside ErrorMessageDialog because this would use a
		// different synchronization reference than used in closeDialog
		// calling close on a disposed element will fail; must not be reentrant
		if (null != dialogArea && !dialogArea.isDisposed()) {
			closeDialog(dialogArea);
		}
	}

	private synchronized void closeDialog(final Composite dialogArea) {
		// calling close on a disposed element will fail; must not be reentrant
		if (null != dialogArea && !dialogArea.isDisposed()) {
			windows.remove(dialogArea.getShell());
			dialogArea.getShell().close();
		}
		removeInvalidMessages();
	}

	private void removeInvalidMessages() {
		final List<ErrorMessage> toRemove = messages.stream().filter((final ErrorMessage m) -> !m.isStillValid()).collect(Collectors.toList());
		for(final ErrorMessage m: toRemove) {
			ErrorMessenger.hashCleared(m.hashCode());
		}
		messages.removeAll(toRemove);
	}

	private void showErrorMessageDialog(final ErrorMessage m) {
		if(isHovering) {
			updateMessageList(m);
			Display.getDefault().timerExec(MOUSE_HOVER_DELAY, () -> {
				if(isHovering) {
					closeAllDialogs();
					showMessages(m);
					m.setInvalid(); // hover-messages will not be reshown
				} else {
					ErrorMessenger.hashCleared(m.hashCode());
				}
			});
		} else {
			Display.getDefault().asyncExec(() -> activateErrorMessageDialog(m));
		}
	}

	private void activateErrorMessageDialog(final ErrorMessage m) {
		updateMessageList(m);
		closeAllDialogs();
		showMessages(m);
		ErrorMessenger.hashCleared(m.hashCode());
	}

	private void updateMessageList(final ErrorMessage m) {
		messages.add(m);
		removeInvalidMessages();
	}

	private void closeAllDialogs() {
		for (Composite w = windows.pollFirst(); null != w; w = windows.pollFirst()) {
			closeDialog(w);
		}
	}

	private void showMessages(final ErrorMessage m) {
		if(!messages.isEmpty()) {
			final String dialogContent = messages.stream().map(ErrorMessage::getMessage).collect(Collectors.joining("\n")); //$NON-NLS-1$

			final ErrorMessageDialog dialog = new ErrorMessageDialog(this, dialogContent, m.getTimeout());
			dialog.open();
			windows.push(dialog.getShell());
		}
	}

	private final EventHandler receiver = (final Event event) -> {
		final Object data = event.getProperty(IEventBroker.DATA);
		if (eventsRegistered && data instanceof ErrorMessage) {
			showErrorMessageDialog((ErrorMessage)data);
		}
	};

}
