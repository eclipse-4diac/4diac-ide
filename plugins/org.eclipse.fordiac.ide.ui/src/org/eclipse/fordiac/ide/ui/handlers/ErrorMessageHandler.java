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
import org.eclipse.fordiac.ide.ui.handlers.internal.ErrorMessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ErrorMessageHandler {

	private static final int MOUSE_HOVER_DELAY = 700;

	boolean hovering = false;

	public synchronized void setHover(final boolean hovering) {
		this.hovering = hovering;
		if (!hovering) {
			messages.stream().forEach(ErrorMessage::setInvalid);
			closeAllDialogs();
		}
	}

	public boolean isHovering() {
		return hovering;
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

	@SuppressWarnings("squid:S3398")
	public synchronized void clearDialog(final Composite dialogArea) {
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
		final List<ErrorMessage> toRemove = messages.stream().filter((final ErrorMessage m) -> !m.isStillValid())
				.toList();
		for (final ErrorMessage m : toRemove) {
			ErrorMessenger.hashCleared(m.hashCode());
		}
		messages.removeAll(toRemove);
	}

	private void showErrorMessageDialog(final ErrorMessage m) {
		if (hovering) {
			updateMessageList(m);
			Display.getDefault().timerExec(MOUSE_HOVER_DELAY, () -> {
				if (hovering) {
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
		if (!messages.isEmpty()) {
			final String dialogContent = messages.stream().map(ErrorMessage::getMessage)
					.collect(Collectors.joining("\n")); //$NON-NLS-1$

			final ErrorMessageDialog dialog = new ErrorMessageDialog(getShell(), this, dialogContent, m.getTimeout());
			dialog.open();
			windows.push(dialog.getShell());
		}
	}

	private static Shell getShell() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			return window.getShell();
		}
		return null;
	}

	private final EventHandler receiver = (final Event event) -> {
		final Object data = event.getProperty(IEventBroker.DATA);
		if (eventsRegistered && data instanceof final ErrorMessage em) {
			showErrorMessageDialog(em);
		}
	};

}
