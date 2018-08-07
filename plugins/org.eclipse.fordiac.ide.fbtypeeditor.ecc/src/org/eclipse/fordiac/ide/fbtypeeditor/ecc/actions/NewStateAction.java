/*******************************************************************************
 * Copyright (c) 2012 - 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.StateCreationFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

public class NewStateAction extends WorkbenchPartAction {

	/**
	 * Create State action id. Value: <code>"org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction"</code>
	 */
	public static final String CREATE_STATE = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction";//$NON-NLS-1$
	
	StateCreationFactory stateFactory = new StateCreationFactory();
	Control viewerControl;
	org.eclipse.swt.graphics.Point pos = new org.eclipse.swt.graphics.Point(0,0);
	
	public NewStateAction(IWorkbenchPart part) {
		super(part);
		setId(CREATE_STATE);
		setText(Messages.ECCActions_NEW_STATE);
	}
	
	public void setViewerControl(Control control){		
		viewerControl = control;
		if(null != viewerControl){
			viewerControl.addMenuDetectListener(new MenuDetectListener() {
				@Override
				public void menuDetected(MenuDetectEvent e) {
				  pos = viewerControl.toControl(e.x, e.y);			  
				}
			});
		}
	}

	@Override
	protected boolean calculateEnabled() {
		return true; // we can always be enabled
	}

	
	@Override
	public void run() {		
		ECCEditor editor = (ECCEditor)getWorkbenchPart();
		
		ECState model = (ECState) stateFactory.getNewObject();
		execute(new CreateECStateCommand( model, new Point(pos.x, pos.y), editor.getFbType().getECC()));
				
		editor.outlineSelectionChanged(model);
	}
}
