/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;

public interface IFBTEditorPart extends ISelectionListener, IEditorPart {

	/**
	 * Inform the FBTEditorPart that an element has been selected in the outline
	 * view. If the selected element is handled by the FBTEditorPart the
	 * FBTEditorPart has to perform measure to show the selected element. By
	 * returning true the FBTypeEditor can perform measure to activate the correct
	 * tab.
	 * 
	 * @param selectedElement the element that has been selected in the FB outline
	 *                        view
	 * @return true if the selected element is handled in this editor part
	 */
	boolean outlineSelectionChanged(Object selectedElement);

	/**
	 * This allows to coordinating multipageeditpart to share a command stack among
	 * all sub-editor pages
	 * 
	 * @param commandStack the shared command stack
	 */
	void setCommonCommandStack(CommandStack commandStack);

}
