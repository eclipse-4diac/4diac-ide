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

import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Control;

/**
 * The Interface IAlgorithmEditor.
 */
public interface IAlgorithmEditor {
	
	
	/** Add a document listener for the algorithm editor
	 * 
	 * @param listener document listener to be added
	 */
	void addDocumentListener(IDocumentListener listener);

	
	/** Remove a document listener for the algorithm editor
	 * 
	 * @param listener document listener to be removed
	 */
	void removeDocumentListener(IDocumentListener listener);
	
	
	/** Set the text of the algorithm
	 * TODO consider to change this to AlgorithmObject itself to make it easier to implement non 
	 * textual algorithm editors
	 * 
	 * @param text content of the algorithm
	 */
	void setAlgorithmText(String text);
	
	/**get the text of the algorithm from the editor
	 */
	String getAlgorithmText();
	
	/**
	 * Gets the control.
	 * 
	 * @return the control
	 */
	Control getControl();
	
	/** Allows document listeners to check if the document is in a valid state before reading it back.
	 * 
	 * Sometimes the document change listener fires although the document is not in a valid sate to be read back
	 * into the algorithm (e.g., updating the prefix of XText editors). This function allows to check for 
	 * this and ignore such updates 
	 * 
	 * @return true if the document is in a valid state to be read back
	 */
	boolean isDocumentValid();
	
}
