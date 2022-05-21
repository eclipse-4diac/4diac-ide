/*******************************************************************************
 * Copyright (c) 2008 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * The Class AnnotationMarkerAccess.
 */
public class AnnotationMarkerAccess implements IAnnotationAccess, IAnnotationAccessExtension {

	/**
	 * Instantiates a new annotation marker access.
	 */
	public AnnotationMarkerAccess() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.text.source.IAnnotationAccessExtension#getTypeLabel(org.
	 * eclipse.jface.text.source.Annotation)
	 */
	@Override
	public String getTypeLabel(final Annotation annotation) {
		return annotation.getType();
	}

	@Override
	public int getLayer(final Annotation annotation) {
		if (annotation instanceof AbstractDeploymentAnnotations) {
			return ((AbstractDeploymentAnnotations) annotation).getLayer();
		}

		return 0;
	}

	@Override
	public void paint(final Annotation annotation, final GC gc, final Canvas canvas, final Rectangle bounds) {
		if (annotation instanceof AbstractDeploymentAnnotations) {
			ImageUtilities.drawImage(((AbstractDeploymentAnnotations) annotation).getImage(), gc, canvas, bounds,
					SWT.CENTER,
					SWT.TOP);
		}
	}

	@Override
	public boolean isPaintable(final Annotation annotation) {
		if (annotation instanceof AbstractDeploymentAnnotations) {
			return ((AbstractDeploymentAnnotations) annotation).getImage() != null;
		}
		return false;
	}

	@Override
	public boolean isSubtype(final Object annotationType, final Object potentialSupertype) {
		return annotationType.equals(potentialSupertype);
	}

	@Override
	public Object[] getSupertypes(final Object annotationType) {
		return new Object[0];
	}

	@Override
	public Object getType(final Annotation annotation) {
		return annotation.getType();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.text.source.IAnnotationAccess#isMultiLine(org.eclipse.jface
	 * .text.source.Annotation)
	 */
	@Override
	public boolean isMultiLine(final Annotation annotation) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.text.source.IAnnotationAccess#isTemporary(org.eclipse.jface
	 * .text.source.Annotation)
	 */
	@Override
	public boolean isTemporary(final Annotation annotation) {
		return false;
	}
}