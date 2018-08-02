/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.swt.widgets.Composite;

/**
 * The Interface IAlgorithmEditorCreator.
 */
public interface IAlgorithmEditorCreator {

	/**
	 * Creates the algorithm editor.
	 * 
	 * @param parent
	 *            the parent
	 * 
	 * @return the i algorithm editor
	 */
	IAlgorithmEditor createAlgorithmEditor(Composite parent, BasicFBType fbType);

}
