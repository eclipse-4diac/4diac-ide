/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.figures.FBTypeFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class FBTypeEditPart extends AbstractDirectEditableEditPart{
	
	private ControlListener controlListener;
	private ZoomManager zoomManager;
	private EventInputContainer eic;
	private EventOutputContainer eoc;
	private VariableInputContainer vic;
	private SocketContainer socketcont;
	private VariableOutputContainer voc;
	private PlugContainer plugcont;

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if(Notification.REMOVING_ADAPTER != notification.getEventType()){
				Object feature = notification.getFeature();
				if((LibraryElementPackage.eINSTANCE.getVersionInfo().equals(feature)) || 
						(LibraryElementPackage.eINSTANCE.getVersionInfo_Version().equals(feature))){
					getCastedFigure().updateVersionInfoLabel();
				}

				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if((null != getFigure()) && (getFigure().isShowing())){
							refresh();
						}
					}
				});
			}
		}
	};
	
	@Override
	public void activate() {
		super.activate();
		getCastedModel().eAdapters().add(adapter);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getCastedModel().eAdapters().remove(adapter);
	}

	public FBType getCastedModel() {
		return (FBType) getModel();
	}

	FBTypeEditPart(ZoomManager zoomManager) {
		super();
		this.zoomManager = zoomManager;
	}
	
	@Override
	protected IFigure createFigure() {
		return new FBTypeFigure((FBType) getModel(), zoomManager);
	}

	@Override
	public void setSelected(int value) {
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
	}
	
	@Override
	public void refreshName() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				getNameLabel().setText(getINamedElement().getName());
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		if (eic == null) {
			eic = new EventInputContainer(getCastedModel());
		}
		if (eoc == null) {
			eoc = new EventOutputContainer(getCastedModel());
		}
		if (vic == null) {
			vic = new VariableInputContainer(getCastedModel());
		}	
		if (socketcont == null) {
			socketcont = new SocketContainer(getCastedModel());
		}		
		if (voc == null) {
			voc = new VariableOutputContainer(getCastedModel());
		}
		if (plugcont == null) {
			plugcont = new PlugContainer(getCastedModel());
		}
		ArrayList<Object> temp = new ArrayList<Object>();
		temp.add(eic);
		temp.add(eoc);
		temp.add(vic);
		temp.add(socketcont);
		temp.add(voc);
		temp.add(plugcont);
		return temp;
		// return super.getModelChildren();
	}

	private FBTypeFigure getCastedFigure() {
		return (FBTypeFigure) getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceContainerEditPart) {
			if (childEditPart.getModel() instanceof EventInputContainer) {
				getCastedFigure().getInputEvents().add(child);
			}
			if (childEditPart.getModel() instanceof EventOutputContainer) {
				getCastedFigure().getOutputEvents().add(child);
			}
			if (childEditPart.getModel() instanceof VariableInputContainer) {
				getCastedFigure().getInputVariables().add(child);
			}
			if (childEditPart.getModel() instanceof VariableOutputContainer) {
				getCastedFigure().getOutputVariables().add(child);
			}
			if (childEditPart.getModel() instanceof SocketContainer) {
				getCastedFigure().getSockets().add(child);
			}
			if (childEditPart.getModel() instanceof PlugContainer) {
				getCastedFigure().getPlugs().add(child);
			}
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}
	
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceContainerEditPart) {
			if (childEditPart.getModel() instanceof EventInputContainer) {
				getCastedFigure().getInputEvents().remove(child);
			}
			if (childEditPart.getModel() instanceof EventOutputContainer) {
				getCastedFigure().getOutputEvents().remove(child);
			}
			if (childEditPart.getModel() instanceof VariableInputContainer) {
				getCastedFigure().getInputVariables().remove(child);
			}
			if (childEditPart.getModel() instanceof VariableOutputContainer) {
				getCastedFigure().getOutputVariables().remove(child);
			}
			if (childEditPart.getModel() instanceof SocketContainer) {
				getCastedFigure().getSockets().remove(child);
			}
			if (childEditPart.getModel() instanceof PlugContainer) {
				getCastedFigure().getPlugs().remove(child);
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}
	
	private void update(final Rectangle bounds) {
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
	}

	@Override
	protected void refreshVisuals() {
		if (controlListener == null) {
			controlListener = new ControlListener() {

				public void controlResized(final ControlEvent e) {
					Point p = getParent().getViewer().getControl().getSize();
					Dimension dim = getFigure().getPreferredSize(-1, -1);

					Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y
							/ 2 - dim.height / 2, -1, -1);
					// rectangle rect = new Rectangle()

					update(rect);
				}

				public void controlMoved(final ControlEvent e) {

				}

			};
			getParent().getViewer().getControl()
					.addControlListener(controlListener);
		}
		Point p = getParent().getViewer().getControl().getSize();
		Dimension dim = getFigure().getPreferredSize(-1, -1);
		Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y / 2
				- dim.height / 2, -1, -1);
		update(rect);
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	@Override
	public Label getNameLabel() {
		return getCastedFigure().getTypeNameLabel();
	}
}
