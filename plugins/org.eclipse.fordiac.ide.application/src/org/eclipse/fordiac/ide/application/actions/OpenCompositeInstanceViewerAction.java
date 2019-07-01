/*******************************************************************************
 * Copyright (c) 2013, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils  
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.viewer.composite.CompositeInstanceViewerInput;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.action.Action;

/**
 * The Class OpenSubApplicationEditorAction.
 */
public class OpenCompositeInstanceViewerAction extends Action {

	private static final String COMPOSITE_INSTANCE_VIEWER_ID = "org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer"; //$NON-NLS-1$
	/** The fb. */
	private FB fb;
	private FBEditPart fbEditPart;

	/**
	 * Constructor of the Action.
	 * 
	 * @param fb the fb
	 */
	public OpenCompositeInstanceViewerAction(FBEditPart fbEditPart, final FB fb) {
		this.fb = fb;
		this.fbEditPart = fbEditPart;
		setText(Messages.OpenCompositeInstanceViewerAction_Name);
	}

	/**
	 * Opens the editor for the specified Model or sets the focus to the editor if
	 * already opened.
	 */
	@Override
	public void run() {
		CompositeInstanceViewerInput input = new CompositeInstanceViewerInput(fbEditPart, fb, fb.getName());
		EditorUtils.openEditor(input, COMPOSITE_INSTANCE_VIEWER_ID);
	}

}
