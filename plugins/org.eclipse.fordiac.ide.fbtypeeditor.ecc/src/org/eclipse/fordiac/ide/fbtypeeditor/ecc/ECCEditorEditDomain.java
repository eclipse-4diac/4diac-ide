/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.util.AdvancedPanningSelectionTool;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.IEditorPart;

final class ECCEditorEditDomain extends FBTypeEditDomain {
	private static class StateCreationTool extends CreationTool {
		
		private StateCreationFactory stateFactory = new StateCreationFactory();  
		private ActionCreationFactory actionFactory = new ActionCreationFactory();
		
		public StateCreationTool() {
			setFactory(stateFactory);
			setUnloadWhenFinished(false);
		}
		
		@Override
		protected void handleFinished() {
			super.handleFinished(); 
			handleMove();
		}
				
		@Override
		protected boolean updateTargetUnderMouse() {
			boolean changed = super.updateTargetUnderMouse();
			if(changed) {
				if(getTargetEditPart() instanceof ECStateEditPart || 
						getTargetEditPart() instanceof ECActionAlgorithmEditPart|| 
						getTargetEditPart() instanceof ECActionOutputEventEditPart ) {
					setFactory(actionFactory);
				} else {
					setFactory(stateFactory);
				}				
			}			
			return changed;
		}
		
		public void setLocationActivation(Point point) {
			getCurrentInput().setMouseLocation(point.x, point.y);
			handleMove();
		}
		
	}
	
	private static class ECCPanningSelectionTool extends AdvancedPanningSelectionTool{
		public Point getLastLocation() {
			return ((LocationRequest)super.getTargetHoverRequest()).getLocation();
		}
	}
	
	private StateCreationTool creationTool = new StateCreationTool();
	
	
	ECCEditorEditDomain(IEditorPart editorPart, CommandStack commandStack) {
		super(editorPart, commandStack);
		setDefaultTool(new ECCPanningSelectionTool());
	}

	@Override
	public void keyDown(KeyEvent keyEvent, EditPartViewer viewer) {
		if(keyEvent.keyCode == SWT.MOD1) { // Ctrl or Command key was pressed
			setActiveTool(creationTool);
			super.keyDown(keyEvent, viewer);
			creationTool.setLocationActivation(((ECCPanningSelectionTool)getDefaultTool()).getLastLocation());
		} else {
			super.keyDown(keyEvent, viewer);
		}
	}

	@Override
	public void keyUp(KeyEvent keyEvent, EditPartViewer viewer) {
		if(keyEvent.keyCode == SWT.MOD1) { // Ctrl or Command key was pressed
			setActiveTool(getDefaultTool());
		}
		super.keyUp(keyEvent, viewer);
	}
}