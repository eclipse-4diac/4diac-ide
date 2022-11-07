/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class EventValueEntity {

	private final Event event;
	private final AtomicInteger count;

	public EventValueEntity(final Event event, final AtomicInteger count) {
		super();
		this.event = event;
		this.count = count;
	}

	public Event getEvent() {
		return event;
	}

	public AtomicInteger getCount() {
		return count;
	}
}
