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
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractGraphicalAnnotationModel implements GraphicalAnnotationModel {

	private final Map<Object, Set<GraphicalAnnotation>> annotations = new ConcurrentHashMap<>();
	private final Set<GraphicalAnnotationModelListener> listeners = ConcurrentHashMap.newKeySet();

	@Override
	public void addAnnotationModelListener(final GraphicalAnnotationModelListener listener) {
		addAnnotationModelListener(listener, false);
	}

	@Override
	public void addAnnotationModelListener(final GraphicalAnnotationModelListener listener,
			final boolean initialEvent) {
		listeners.add(listener);
		if (initialEvent) {
			listener.modelChanged(new GraphicalAnnotationModelEvent(this,
					annotations.values().stream().flatMap(Set::stream).collect(Collectors.toSet()),
					Collections.emptySet(), Collections.emptySet(), getModificationStamp()));
		}
	}

	@Override
	public void removeAnnotationModelListener(final GraphicalAnnotationModelListener listener) {
		listeners.remove(listener);
	}

	protected GraphicalAnnotationModelEvent fireModelChanged(final GraphicalAnnotationModelEvent event) {
		listeners.forEach(listener -> listener.modelChanged(event));
		return event;
	}

	@Override
	public Set<Object> getAnnotatedTargets() {
		return Set.copyOf(annotations.keySet());
	}

	@Override
	public boolean hasAnnotations(final Object target) {
		return annotations.containsKey(target);
	}

	@Override
	public boolean hasAnnotation(final Object target, final String type) {
		return hasAnnotation(target, annotation -> annotation.getType().equals(type));
	}

	@Override
	public boolean hasAnnotation(final Object target, final Predicate<GraphicalAnnotation> filter) {
		return annotations.getOrDefault(target, Collections.emptySet()).stream().anyMatch(filter);
	}

	@Override
	public boolean hasAnnotation(final Object target, final String type, final Predicate<GraphicalAnnotation> filter) {
		return annotations.getOrDefault(target, Collections.emptySet()).stream()
				.filter(annotation -> annotation.getType().equals(type)).anyMatch(filter);
	}

	@Override
	public Set<GraphicalAnnotation> getAnnotations(final Object target) {
		return Set.copyOf(annotations.getOrDefault(target, Collections.emptySet()));
	}

	@Override
	public boolean addAnnotation(final GraphicalAnnotation annotation) {
		if (addAnnotationInternal(annotation)) {
			fireModelChanged(new GraphicalAnnotationModelEvent(this, Set.of(annotation), Collections.emptySet(),
					Collections.emptySet(), getModificationStamp()));
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAnnotation(final GraphicalAnnotation annotation) {
		if (removeAnnotationInternal(annotation)) {
			fireModelChanged(new GraphicalAnnotationModelEvent(this, Collections.emptySet(), Set.of(annotation),
					Collections.emptySet(), getModificationStamp()));
			return true;
		}
		return false;
	}

	@Override
	public boolean changedAnnotation(final GraphicalAnnotation annotation) {
		if (containsAnnotation(annotation)) {
			fireModelChanged(new GraphicalAnnotationModelEvent(this, Collections.emptySet(), Collections.emptySet(),
					Set.of(annotation), getModificationStamp()));
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAnnotation(final GraphicalAnnotation annotation) {
		return annotations.getOrDefault(annotation.getTarget(), Collections.emptySet()).contains(annotation);
	}

	@Override
	public GraphicalAnnotationModelEvent updateAnnotations(final Set<GraphicalAnnotation> add,
			final Set<GraphicalAnnotation> remove, final Set<GraphicalAnnotation> changed) {
		final Set<GraphicalAnnotation> added = new HashSet<>();
		final Set<GraphicalAnnotation> removed = new HashSet<>();
		for (final GraphicalAnnotation annotation : remove) {
			if (removeAnnotationInternal(annotation)) {
				removed.add(annotation);
			}
		}
		for (final GraphicalAnnotation annotation : add) {
			if (addAnnotationInternal(annotation)) {
				added.add(annotation);
			}
		}
		if (added.isEmpty() && removed.isEmpty() && changed.isEmpty()) {
			return null;
		}
		return fireModelChanged(new GraphicalAnnotationModelEvent(this, added, removed,
				changed.isEmpty() ? Collections.emptySet() : Set.copyOf(changed), getModificationStamp()));
	}

	protected boolean addAnnotationInternal(final GraphicalAnnotation annotation) {
		return annotations.computeIfAbsent(annotation.getTarget(), key -> ConcurrentHashMap.newKeySet())
				.add(annotation);
	}

	protected boolean removeAnnotationInternal(final GraphicalAnnotation annotation) {
		final boolean[] result = new boolean[1];
		annotations.computeIfPresent(annotation.getTarget(), (key, value) -> {
			result[0] = value.remove(annotation);
			return value.isEmpty() ? null : value;
		});
		return result[0];
	}
}
