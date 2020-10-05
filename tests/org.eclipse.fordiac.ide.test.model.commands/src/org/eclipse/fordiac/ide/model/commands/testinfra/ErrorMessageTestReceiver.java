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
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ErrorMessageTestReceiver {

	public ErrorMessageTestReceiver() {
		initEventBroker();
	}

	public void stop() {
		if (eventsRegistered) {
			eventsRegistered = !(eventBroker.unsubscribe(receiver));
			messages = null;
		}
	}

	public void start() {
		if (!eventsRegistered) {
			eventsRegistered = eventBroker.subscribe(TOPIC_ERRORMESSAGES, receiver);
			messages = new ArrayList<>();
		}
	}

	public List<String> getMessages() {
		return messages;
	}

	private synchronized void initEventBroker() {
		if (null == eventBroker) {
			eventBroker = EclipseContextFactory.getServiceContext(UIPlugin.getDefault().getBundle().getBundleContext())
					.get(IEventBroker.class);
		}
	}

	private IEventBroker eventBroker;

	private static final String TOPIC_ERRORMESSAGES = "ORG/ECLIPSE/FORDIAC/IDE/ERRORMESSAGES"; //$NON-NLS-1$

	private boolean eventsRegistered = false;

	private List<String> messages;

	private EventHandler receiver = (Event event) -> {
		Object message = event.getProperty("message"); //$NON-NLS-1$
		Object title = event.getProperty("title"); //$NON-NLS-1$
		Object timeout = event.getProperty("timeout"); //$NON-NLS-1$
		if (eventsRegistered && message instanceof String && title instanceof String && timeout instanceof Integer) {
			messages.add((String) message);
		}
	};

}