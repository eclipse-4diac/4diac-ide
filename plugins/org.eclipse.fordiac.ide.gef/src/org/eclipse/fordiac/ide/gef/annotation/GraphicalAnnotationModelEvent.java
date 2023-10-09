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

import java.util.Set;

public class GraphicalAnnotationModelEvent {
	private final GraphicalAnnotationModel model;

	private final Set<GraphicalAnnotation> added;
	private final Set<GraphicalAnnotation> removed;
	private final Set<GraphicalAnnotation> changed;

	private final long modificationStamp;

	public GraphicalAnnotationModelEvent(final GraphicalAnnotationModel model, final Set<GraphicalAnnotation> added,
			final Set<GraphicalAnnotation> removed, final Set<GraphicalAnnotation> changed,
			final long modificationStamp) {
		this.model = model;
		this.added = added;
		this.removed = removed;
		this.changed = changed;
		this.modificationStamp = modificationStamp;
	}

	public GraphicalAnnotationModel getModel() {
		return model;
	}

	public Set<GraphicalAnnotation> getAdded() {
		return added;
	}

	public Set<GraphicalAnnotation> getRemoved() {
		return removed;
	}

	public Set<GraphicalAnnotation> getChanged() {
		return changed;
	}

	public long getModificationStamp() {
		return modificationStamp;
	}
}
