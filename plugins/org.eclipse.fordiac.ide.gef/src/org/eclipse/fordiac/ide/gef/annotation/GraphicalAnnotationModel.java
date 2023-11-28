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
import java.util.function.Predicate;

public interface GraphicalAnnotationModel {

	/**
	 * Add an annotation model listener
	 *
	 * @param listener The listener
	 */
	void addAnnotationModelListener(GraphicalAnnotationModelListener listener);

	/**
	 * Add an annotation model listener
	 *
	 * @param listener     The listener
	 * @param initialEvent Whether to send an initial avant marking all current
	 *                     annotations as added
	 */
	void addAnnotationModelListener(GraphicalAnnotationModelListener listener, boolean initialEvent);

	/**
	 * Remove an annotation model listener
	 *
	 * @param listener The listener
	 */
	void removeAnnotationModelListener(GraphicalAnnotationModelListener listener);

	/**
	 * Get the set of annotated targets
	 *
	 * @return An unmodifiable copy of the set of annotated targets
	 */
	Set<Object> getAnnotatedTargets();

	/**
	 * Check whether a particular target has any associated annotations
	 *
	 * @param target The target
	 * @return whether the target has any associated annotations
	 */
	boolean hasAnnotations(Object target);

	/**
	 * Check whether a particular target has any associated annotations of a
	 * particular type
	 *
	 * @param target The target
	 * @param type   The annotation type
	 * @return whether the target has any associated annotations of the type
	 */
	boolean hasAnnotation(Object target, String type);

	/**
	 * Check whether a particular target has any associated annotations matching a
	 * predicate
	 *
	 * @param target    The target
	 * @param predicate The predicate
	 * @return whether the target has any associated annotations matching the
	 *         predicate
	 */
	boolean hasAnnotation(Object target, Predicate<GraphicalAnnotation> filter);

	/**
	 * Check whether a particular target has any associated annotations of a
	 * particular type and matching a predicate
	 *
	 * @param target    The target
	 * @param type      The annotation type
	 * @param predicate The predicate
	 * @return whether the target has any associated annotations matching the
	 *         predicate
	 */
	boolean hasAnnotation(Object target, String type, Predicate<GraphicalAnnotation> filter);

	/**
	 * Get the annotations for a particular target
	 *
	 * @param target The target
	 * @return An unmodifiable copy of the annotations for the target (or an empty
	 *         set if none)
	 */
	Set<GraphicalAnnotation> getAnnotations(Object target);

	/**
	 * Add a single annotation.
	 *
	 * @param annotation The annotation to add
	 * @return whether the annotation was not present and subsequently added
	 */
	boolean addAnnotation(GraphicalAnnotation annotation);

	/**
	 * Remove a single annotation.
	 *
	 * @param annotation The annotation to remove
	 * @return whether the annotation was present and subsequently removed
	 */
	boolean removeAnnotation(GraphicalAnnotation annotation);

	/**
	 * Changed a single annotation.
	 *
	 * @param annotation The annotation that was changed
	 * @return whether the annotation was present
	 */
	boolean changedAnnotation(GraphicalAnnotation annotation);

	/**
	 * Check if this model contains a particular annotation.
	 *
	 * @param annotation The annotation
	 * @return whether the annotation is present
	 */
	boolean containsAnnotation(GraphicalAnnotation annotation);

	/**
	 * Update annotations by first remoiving and then adding the supplied
	 * annotations in a bulk operation.
	 *
	 * @param add     The annotations to add
	 * @param remove  The annotations to remove
	 * @param changed That annotations that were changed
	 * @return The event representing the change or {@code null} if no changes
	 *         occurred and thus no event was fired
	 */
	GraphicalAnnotationModelEvent updateAnnotations(Set<GraphicalAnnotation> add, Set<GraphicalAnnotation> remove,
			Set<GraphicalAnnotation> changed);

	/**
	 * Get the (implementation-specific) modification stamp.
	 *
	 * @return The modification stamp
	 */
	long getModificationStamp();

	/**
	 * Dispose annotation model
	 */
	void dispose();
}
