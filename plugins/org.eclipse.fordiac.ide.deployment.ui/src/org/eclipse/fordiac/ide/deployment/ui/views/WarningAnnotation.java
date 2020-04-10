/*******************************************************************************
 * Copyright (c) 2008, 2015 Profactor GbmH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The Class ErrorAnnotation.
 */
public class WarningAnnotation extends Annotation {

	/** The marker. */
	private IMarker marker;

	/** The text. */
	private String text;

	/** The line. */
	private int line;

	/** The position. */
	private Position position;

	/**
	 * Instantiates a new error annotation.
	 * 
	 * @param marker the marker
	 */
	public WarningAnnotation(final IMarker marker) {
		this.marker = marker;
	}

	/**
	 * Instantiates a new error annotation.
	 * 
	 * @param line the line
	 * @param text the text
	 */
	public WarningAnnotation(final int line, final String text) {
		super("download_warning", true, null); //$NON-NLS-1$
		this.marker = null;
		this.line = line;
		this.text = text;
	}

	/**
	 * Gets the marker.
	 * 
	 * @return the marker
	 */
	public IMarker getMarker() {
		return marker;
	}

	/**
	 * Gets the line.
	 * 
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.Annotation#getText()
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_WARNING);
	}

	/**
	 * Gets the layer.
	 * 
	 * @return the layer
	 */
	public int getLayer() {
		return 4;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position the new position
	 */
	public void setPosition(final Position position) {
		this.position = position;
	}
}