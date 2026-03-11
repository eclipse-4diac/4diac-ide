/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class LibraryElementDependencyTracker extends EContentAdapter {

	private final Map<TypeEntry, AtomicInteger> dependencies = new ConcurrentHashMap<>();

	public Set<TypeEntry> getDependencies() {
		return Collections.unmodifiableSet(dependencies.keySet());
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getFeature() instanceof final EStructuralFeature structuralFeature
				&& isRelevant(structuralFeature)) {
			switch (notification.getEventType()) {
			case Notification.SET -> setValue(notification.getOldValue(), notification.getNewValue());
			case Notification.ADD, Notification.ADD_MANY -> addValue(notification.getNewValue());
			case Notification.REMOVE, Notification.REMOVE_MANY, Notification.UNSET ->
				removeValue(notification.getOldValue());
			default -> {
				// ignore
			}
			}
		}
		super.notifyChanged(notification);
	}

	@Override
	protected void addAdapter(final Notifier notifier) {
		super.addAdapter(notifier);
		if (notifier instanceof final EObject eObject) {
			eObject.eClass().getEAllStructuralFeatures().stream().filter(LibraryElementDependencyTracker::isRelevant)
					.map(eObject::eGet).forEach(this::addValue);
		}
	}

	@Override
	protected void removeAdapter(final Notifier notifier) {
		if (notifier instanceof final EObject eObject) {
			eObject.eClass().getEAllStructuralFeatures().stream().filter(LibraryElementDependencyTracker::isRelevant)
					.map(eObject::eGet).forEach(this::removeValue);
		}
		super.removeAdapter(notifier);
	}

	protected void setValue(final Object oldValue, final Object newValue) {
		addValue(newValue);
		removeValue(oldValue);
	}

	protected void addValue(final Object object) {
		switch (object) {
		case final Collection<?> collection -> collection.forEach(this::addValue);
		case final LibraryElement libraryElement -> addDependency(libraryElement.getTypeEntry());
		case final TypeEntry typeEntry -> addDependency(typeEntry);
		case null, default -> {
			// ignore
		}
		}
	}

	protected void removeValue(final Object object) {
		switch (object) {
		case final Collection<?> collection -> collection.forEach(this::removeValue);
		case final LibraryElement libraryElement -> removeDependency(libraryElement.getTypeEntry());
		case final TypeEntry typeEntry -> removeDependency(typeEntry);
		case null, default -> {
			// ignore
		}
		}
	}

	protected boolean addDependency(final TypeEntry typeEntry) {
		return typeEntry != null
				&& dependencies.computeIfAbsent(typeEntry, unused -> new AtomicInteger()).getAndIncrement() == 0;
	}

	protected boolean removeDependency(final TypeEntry typeEntry) {
		return typeEntry != null && dependencies.computeIfPresent(typeEntry,
				(unused, old) -> old.decrementAndGet() == 0 ? null : old) == null;
	}

	protected static boolean isRelevant(final EStructuralFeature structuralFeature) {
		return switch (structuralFeature) {
		case final EAttribute attribute -> attribute.getEAttributeType() == LibraryElementPackage.Literals.TYPE_ENTRY;
		case final EReference reference when !reference.isContainment() ->
			LibraryElementPackage.Literals.LIBRARY_ELEMENT.isSuperTypeOf(reference.getEReferenceType());
		default -> false;
		};
	}

	@Override
	protected boolean useRecursion() {
		return false;
	}
}
