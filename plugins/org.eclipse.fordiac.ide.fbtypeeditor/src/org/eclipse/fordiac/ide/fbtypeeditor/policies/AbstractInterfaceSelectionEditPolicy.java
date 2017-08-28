/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import java.util.List;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.handles.PlusHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.RelativeHandleLocator;

public abstract class AbstractInterfaceSelectionEditPolicy extends ModifiedNonResizeableEditPolicy {


	public AbstractInterfaceSelectionEditPolicy(int cornerDim, Insets insets) {
		super(cornerDim, insets);
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();
		getInterfaceEditPart().setInOutConnectionsWith(0);
	}

	@Override
	protected void showSelection() {
		super.showSelection();
		getInterfaceEditPart().setInOutConnectionsWith(2);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List createSelectionHandles() {
		List list = super.createSelectionHandles();
		InterfaceEditPart part = getInterfaceEditPart();
		
		PlusHandle top = createHandle(part, PositionConstants.NORTH);
		list.add(top);
		top.addMouseListener(new MouseListener(){

			@Override
			public void mousePressed(MouseEvent me) {
				createInterfaceElement(false);				
			}

			@Override
			public void mouseReleased(MouseEvent me) {					
			}

			@Override
			public void mouseDoubleClicked(MouseEvent me) {					
			}			
		});
		
		PlusHandle bottom = createHandle(part, PositionConstants.SOUTH);
		list.add(bottom);
		bottom.addMouseListener(new MouseListener(){

			@Override
			public void mousePressed(MouseEvent me) {
				createInterfaceElement(true);				
			}

			@Override
			public void mouseReleased(MouseEvent me) {					
			}

			@Override
			public void mouseDoubleClicked(MouseEvent me) {					
			}			
		});
		return list;
	}

	
	private InterfaceEditPart getInterfaceEditPart(){
		return (InterfaceEditPart)getHost();
	}
	
	private IInterfaceElement getModel(){
		return (IInterfaceElement)getInterfaceEditPart().getModel();
	}
	
	private PlusHandle createHandle(InterfaceEditPart part, int direction){
		return new PlusHandle(part, new RelativeHandleLocator(part.getFigure(), direction));
	}
	
	private void createInterfaceElement(boolean after) {
		List<? extends IInterfaceElement> list = getInterfaceElementList();
		int ref = list.indexOf(getModel());
		if(-1 != ref && after){
			ref++;
		}
		Command cmd = getIECreateCommand(getModel().getType(), ref);
		if(null != cmd){
			AbstractDirectEditableEditPart.executeCommand(cmd);
		}
	}

	protected abstract List<? extends IInterfaceElement> getInterfaceElementList();

	protected abstract Command getIECreateCommand(DataType refElement, int ref);

}
