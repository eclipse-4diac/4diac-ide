/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;

public class ConcurrentNotifierImpl implements Notifier {

	private final CopyOnWriteEList<Adapter> adapters = new CopyOnWriteEList<>();
	private volatile boolean deliver = true;

	@Override
	public EList<Adapter> eAdapters() {
		return adapters;
	}

	@Override
	public boolean eDeliver() {
		return deliver;
	}

	@Override
	public void eSetDeliver(final boolean deliver) {
		this.deliver = deliver;
	}

	@Override
	public void eNotify(final Notification notification) {
		if (deliver) {
			adapters.forEach(adapter -> adapter.notifyChanged(notification));
		}
	}

	public boolean eNotificationRequired() {
		return !adapters.isEmpty() && eDeliver();
	}
}
