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

import java.util.LinkedList;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ErrorMessageHandler {

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

	private static final int DEFAULT_DISABLE_AFTER_MS = 3000;

	private static final String TOPIC_ERRORMESSAGES = "ORG/ECLIPSE/FORDIAC/IDE/ERRORMESSAGES"; //$NON-NLS-1$

	private IEventBroker eventBroker;

	private boolean eventsRegistered = false;

	private Integer errorMessageHash;
	private Integer titleHash;

	private LinkedList<Composite> windows = new LinkedList<>();

	private static class ErrorMessageDialog extends PopupDialog {

		private static final int MOUSE_CURSOR_OFFSET_Y = 10;
		private static final int MOUSE_CURSOR_OFFSET_X = 5;
		private static final Point MOUSE_CURSOR_OFFSET = new Point(MOUSE_CURSOR_OFFSET_X, MOUSE_CURSOR_OFFSET_Y);
		private final String errorMsg;
		private final String title;
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

		private ErrorMessageDialog(ErrorMessageHandler container, String errorMsg, String title, int timeout) {
			super(null, HOVER_SHELLSTYLE, false, false, false, false, false, null, null);
			this.errorMsg = errorMsg;
			this.title = title;
			this.timeout = timeout > 0 ? timeout : DEFAULT_DISABLE_AFTER_MS;
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

		@Override
		protected Control createDialogArea(Composite parent) {
			final Composite warningComposite = new Composite(parent, SWT.NONE);
			warningComposite.setLayout(new GridLayout(2, false));
			warningComposite.setLayoutData(new GridData(0, 0, true, false));

			Label image = new Label(warningComposite, SWT.NONE);
			image.setImage(parent.getDisplay().getSystemImage(SWT.ICON_WARNING));
			image.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			Label text = new Label(warningComposite, SWT.NONE);
			text.setText(title + "\n" + errorMsg); //$NON-NLS-1$ // title & message are never null (instanceof check in
													// receiver)
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

			final Control focused = getShell().getDisplay().getFocusControl();
			if (isTextInput(focused)) {
				clearPopupOnKeypress(focused);
				clearPopupOnLostFocus(focused);
			} else {
				clearPopupAfterTimeout(timeout);
			}

			return warningComposite;
		}

		private void clearPopupAfterTimeout(int t) {
			getShell().getDisplay().timerExec(t, () -> container.clearDialog(getShell()));
		}

		private void clearPopupOnLostFocus(final Control focused) {
			focused.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					container.clearDialog(getShell());
				}

				@Override
				public void focusGained(FocusEvent e) {
					// Nothing to do
				}
			});
		}

		private void clearPopupOnKeypress(final Control focused) {
			focused.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent e) {
					// NOP
				}

				@Override
				public void keyPressed(KeyEvent e) {
					container.clearDialog(getShell());
				}
			});
		}

	}

	private synchronized void clearDialog(Composite dialogArea) {
		// This method is not inside ErrorMessageDialog because this would use a
		// different synchronization reference than used in closeDialog
		// calling close on a disposed element will fail; must not be reentrant
		if (null != dialogArea && !dialogArea.isDisposed()) {
			closeDialog(dialogArea);
			setHashes(null, null);
		}
	}

	private synchronized void closeDialog(Composite dialogArea) {
		// calling close on a disposed element will fail; must not be reentrant
		if (null != dialogArea && !dialogArea.isDisposed()) {
			windows.remove(dialogArea.getShell());
			dialogArea.getShell().close();
		}
	}

	protected synchronized void setHashes(Integer pErrorMessageHash, Integer pTitleHash) {
		// Hash values can be changed from two threads (GUI and EventBroker)
		// must not be reentrant
		errorMessageHash = pErrorMessageHash;
		titleHash = pTitleHash;
	}

	protected synchronized boolean isNewMessage(String errorMsg, String title) {
		// the hash values may change at any point
		// must not be interrupted by setHashes
		final int errorMessageHashValue = errorMsg.hashCode();
		final int titleHashValue = title.hashCode();
		final boolean isNewMessage = //
				(null == errorMessageHash || null == titleHash) || // check for valid hashes
						(!errorMessageHash.equals(errorMessageHashValue)) || // are the hashes identical?
						(!titleHash.equals(titleHashValue) //
						);
		if (isNewMessage) {
			setHashes(errorMessageHashValue, titleHashValue);
		}
		return isNewMessage;
	}

	private void showErrorMessageDialog(String errorMsg, String title, int timeout) {
		Display.getDefault().asyncExec(() -> {
			if (isNewMessage(errorMsg, title)) {

				for (Composite w = windows.pollFirst(); null != w; w = windows.pollFirst()) {
					closeDialog(w);
				}

				final ErrorMessageDialog dialog = new ErrorMessageDialog(this, errorMsg, title, timeout);
				dialog.open();
				windows.push(dialog.getShell());
			}
		});
	}

	private EventHandler receiver = (Event event) -> {
		Object message = event.getProperty("message"); //$NON-NLS-1$
		Object title = event.getProperty("title"); //$NON-NLS-1$
		Object timeout = event.getProperty("timeout"); //$NON-NLS-1$
		if (eventsRegistered && message instanceof String && title instanceof String && timeout instanceof Integer) {
			showErrorMessageDialog((String) message, (String) title, (Integer) timeout);
		}
	};

}
