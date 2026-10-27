/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class BreadcrumbEditorModelWatcher extends AdapterImpl {

	private final Runnable processChange;

	private Set<EObject> scope = Set.of();

	public BreadcrumbEditorModelWatcher(final Runnable processChange) {
		this.processChange = processChange;
	}

	public void rebuild(final Collection<EObject> anchors) {
		final Set<EObject> newScope = new HashSet<>();
		for (final EObject anchor : anchors) {
			for (EObject e = anchor; e != null; e = e.eContainer()) {
				if (!newScope.add(e)) {
					break; // remainder of this chain is already known
				}
			}
		}

		final Set<EObject> old = scope;
		old.stream().filter(e -> !newScope.contains(e)).forEach(e -> e.eAdapters().remove(this));
		newScope.stream().filter(e -> !old.contains(e)).forEach(e -> e.eAdapters().add(this));
		scope = newScope; // publish last
	}

	public void dispose() {
		rebuild(Set.of());
	}

	@Override
	public void notifyChanged(final Notification n) {
		if (!n.isTouch() && n.getFeature() instanceof final EReference ref && ref.isContainment()
				&& removesFromScope(n)) {
			processChange.run();
		}
	}

	private boolean removesFromScope(final Notification n) {
		final Set<EObject> current = scope;
		return switch (n.getEventType()) {
		case Notification.REMOVE, Notification.SET, Notification.UNSET -> current.contains(n.getOldValue());
		case Notification.REMOVE_MANY -> ((Collection<?>) n.getOldValue()).stream().anyMatch(current::contains);
		default -> false;
		};
	}

}
