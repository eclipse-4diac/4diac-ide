/*******************************************************************************
 * Copyright (c) 2012, 2022 Profactor GmbH, fortiss GmbH,
 * 							Johannes Kepler University
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
 *   Alois Zoitl - Harmonized deployment and monitoring
 *               - extracted common baseclass for deployment annotations
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractDeploymentAnnotations extends Annotation {

	/** The line. */
	private final int line;

	/** The position. */
	private Position position;


	protected AbstractDeploymentAnnotations(final String type, final String text, final int line) {
		super(type, true, text);
		this.line = line;
	}


	/**
	 * Gets the line.
	 *
	 * @return the line
	 */
	public int getLine() {
		return line;
	}


	public abstract Image getImage();
	public abstract int getLayer();

	public abstract String getTypLabel();


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