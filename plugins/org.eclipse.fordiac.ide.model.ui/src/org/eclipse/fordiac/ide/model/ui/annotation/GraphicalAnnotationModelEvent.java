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
package org.eclipse.fordiac.ide.model.ui.annotation;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphicalAnnotationModelEvent {
	private final GraphicalAnnotationModel model;

	private final Set<Object> targets;
	private final Map<Object, Set<GraphicalAnnotation>> added;
	private final Map<Object, Set<GraphicalAnnotation>> removed;
	private final Map<Object, Set<GraphicalAnnotation>> changed;

	private final long modificationStamp;

	public GraphicalAnnotationModelEvent(final GraphicalAnnotationModel model, final Set<GraphicalAnnotation> added,
			final Set<GraphicalAnnotation> removed, final Set<GraphicalAnnotation> changed,
			final long modificationStamp) {
		this(model,
				added.stream()
						.collect(Collectors.groupingBy(GraphicalAnnotation::getTarget, Collectors.toUnmodifiableSet())),
				removed.stream()
						.collect(Collectors.groupingBy(GraphicalAnnotation::getTarget, Collectors.toUnmodifiableSet())),
				changed.stream()
						.collect(Collectors.groupingBy(GraphicalAnnotation::getTarget, Collectors.toUnmodifiableSet())),
				modificationStamp);
	}

	public GraphicalAnnotationModelEvent(final GraphicalAnnotationModel model,
			final Map<Object, Set<GraphicalAnnotation>> added, final Map<Object, Set<GraphicalAnnotation>> removed,
			final Map<Object, Set<GraphicalAnnotation>> changed, final long modificationStamp) {
		this.model = model;
		this.added = added;
		this.removed = removed;
		this.changed = changed;
		this.modificationStamp = modificationStamp;
		targets = Stream.of(added, removed, changed).map(Map::keySet).flatMap(Set::stream)
				.collect(Collectors.toUnmodifiableSet());
	}

	public GraphicalAnnotationModel getModel() {
		return model;
	}

	public Set<Object> getTargets() {
		return targets;
	}

	public Set<GraphicalAnnotation> getAdded(final Object target) {
		return Objects.requireNonNullElse(added.get(target), Set.of());
	}

	public Set<GraphicalAnnotation> getRemoved(final Object target) {
		return Objects.requireNonNullElse(removed.get(target), Set.of());
	}

	public Set<GraphicalAnnotation> getChanged(final Object target) {
		return Objects.requireNonNullElse(changed.get(target), Set.of());
	}

	public long getModificationStamp() {
		return modificationStamp;
	}

	@Override
	public String toString() {
		return String.format("%s [added=%s, removed=%s, changed=%s, modificationStamp=%s]", getClass().getName(), added, //$NON-NLS-1$
				removed, changed, Long.toString(modificationStamp));
	}
}
