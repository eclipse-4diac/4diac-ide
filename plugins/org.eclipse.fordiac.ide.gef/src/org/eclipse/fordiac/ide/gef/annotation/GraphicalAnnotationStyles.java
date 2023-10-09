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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public final class GraphicalAnnotationStyles {

	public static Color getAnnotationColor(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationColor(annotations, annotation -> true);
	}

	public static Color getAnnotationColor(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_ERROR)) {
			return ColorConstants.red;
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_WARNING)) {
			return ColorConstants.yellow;
		}
		return null;
	}

	public static Color getAnnotationColor(final GraphicalAnnotation annotation) {
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_ERROR)) {
			return ColorConstants.red;
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_WARNING)) {
			return ColorConstants.yellow;
		}
		return null;
	}

	public static Image getAnnotationImage(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationImage(annotations, annotation -> true);
	}

	public static Image getAnnotationImage(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_ERROR)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_WARNING)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_INFO)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
		return null;
	}

	public static Image getAnnotationImage(final GraphicalAnnotation annotation) {
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_ERROR)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_WARNING)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_INFO)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
		return null;
	}

	private static boolean containsType(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter, final String type) {
		return annotations.stream().filter(filter).anyMatch(annotation -> annotation.getType().equals(type));
	}

	private GraphicalAnnotationStyles() {
		throw new UnsupportedOperationException();
	}
}
