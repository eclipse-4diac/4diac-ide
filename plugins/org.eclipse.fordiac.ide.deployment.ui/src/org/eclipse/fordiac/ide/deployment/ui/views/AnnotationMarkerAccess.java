/*******************************************************************************
 * Copyright (c) 2008 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
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
public class AnnotationMarkerAccess implements IAnnotationAccess,
		IAnnotationAccessExtension {

	/**
	 * Instantiates a new annotation marker access.
	 */
	public AnnotationMarkerAccess() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#getTypeLabel(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public String getTypeLabel(final Annotation annotation) {
		if (annotation instanceof ErrorAnnotation) {
			return Messages.AnnotationMarkerAccess_LABEL_ErrorAnnotation;
		}
		if (annotation instanceof WarningAnnotation) {
			return Messages.AnnotationMarkerAccess_LABEL_WarningAnnotation;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#getLayer(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public int getLayer(final Annotation annotation) {
		if (annotation instanceof ErrorAnnotation) {
			return ((ErrorAnnotation) annotation).getLayer();
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#paint(org.eclipse.jface.text.source.Annotation,
	 *      org.eclipse.swt.graphics.GC, org.eclipse.swt.widgets.Canvas,
	 *      org.eclipse.swt.graphics.Rectangle)
	 */
	@Override
	public void paint(final Annotation annotation, final GC gc,
			final Canvas canvas, final Rectangle bounds) {
		if (annotation instanceof ErrorAnnotation) {
		ImageUtilities.drawImage(((ErrorAnnotation) annotation).getImage(), gc,
				canvas, bounds, SWT.CENTER, SWT.TOP);
		}
		if (annotation instanceof WarningAnnotation) {
			ImageUtilities.drawImage(((WarningAnnotation) annotation).getImage(), gc,
					canvas, bounds, SWT.CENTER, SWT.TOP);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#isPaintable(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public boolean isPaintable(final Annotation annotation) {
		if (annotation instanceof ErrorAnnotation) {
			return ((ErrorAnnotation) annotation).getImage() != null;
		}
		if (annotation instanceof WarningAnnotation) {
			return ((WarningAnnotation) annotation).getImage() != null;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#isSubtype(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public boolean isSubtype(final Object annotationType,
			final Object potentialSupertype) {
		if (annotationType.equals(potentialSupertype)) {
			return true;
		}

		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccessExtension#getSupertypes(java.lang.Object)
	 */
	@Override
	public Object[] getSupertypes(final Object annotationType) {
		return new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccess#getType(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public Object getType(final Annotation annotation) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccess#isMultiLine(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public boolean isMultiLine(final Annotation annotation) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationAccess#isTemporary(org.eclipse.jface.text.source.Annotation)
	 */
	@Override
	public boolean isTemporary(final Annotation annotation) {
		return false;
	}
}