/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH, fortiss GmbH 
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
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;

/**
 * The Interface IChangeStringEditPart.
 * 
 * @author gebenh
 */
public interface IChangeStringEditPart {

	/**
	 * Gets the feature id.
	 * 
	 * @return the featureid to be changed
	 */
	int getFeatureID();

	/**
	 * Gets the element.
	 * 
	 * @return the element that should be changed
	 */
	EObject getElement();

	/**
	 * Gets the label.
	 * 
	 * @return the label containing the text to be changed
	 */
	Label getLabel();

}
