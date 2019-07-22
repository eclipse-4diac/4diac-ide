/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added diagram font preference 
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
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class FBTypeEditPart extends AbstractDirectEditableEditPart {

	private ControlListener controlListener;
	private ZoomManager zoomManager;
	private EventInputContainer eic;
	private EventOutputContainer eoc;
	private VariableInputContainer vic;
	private SocketContainer socketcont;
	private VariableOutputContainer voc;
	private PlugContainer plugcont;
	private DiagramFontChangeListener fontChangeListener;

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (Notification.REMOVING_ADAPTER != notification.getEventType()) {
				Object feature = notification.getFeature();
				if ((LibraryElementPackage.eINSTANCE.getVersionInfo().equals(feature))
						|| (LibraryElementPackage.eINSTANCE.getVersionInfo_Version().equals(feature))) {
					getFigure().updateVersionInfoLabel();
				}

				Display.getDefault().asyncExec(() -> {
					if ((null != getFigure()) && (getFigure().isShowing())) {
						refresh();
					}
				});
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		getModel().eAdapters().add(adapter);
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().eAdapters().remove(adapter);
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	@Override
	public FBType getModel() {
		return (FBType) super.getModel();
	}

	FBTypeEditPart(ZoomManager zoomManager) {
		super();
		this.zoomManager = zoomManager;
	}

	@Override
	protected IFigure createFigure() {
		return new FBTypeFigure(getModel(), zoomManager);
	}

	@Override
	public void setSelected(int value) {
	}

	@Override
	public void refreshName() {
		Display.getDefault().asyncExec(() -> getNameLabel().setText(getINamedElement().getName()));
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		if (null == eic) {
			eic = new EventInputContainer(getModel());
		}
		if (null == eoc) {
			eoc = new EventOutputContainer(getModel());
		}
		if (null == vic) {
			vic = new VariableInputContainer(getModel());
		}
		if (null == socketcont) {
			socketcont = new SocketContainer(getModel());
		}
		if (null == voc) {
			voc = new VariableOutputContainer(getModel());
		}
		if (null == plugcont) {
			plugcont = new PlugContainer(getModel());
		}
		ArrayList<Object> temp = new ArrayList<>(6);
		temp.add(eic);
		temp.add(eoc);
		temp.add(vic);
		if (!(getModel() instanceof AdapterFBType)) {
			// adaptertypes cannot have sockets
			temp.add(socketcont);
		}
		temp.add(voc);
		if (!(getModel() instanceof AdapterFBType)) {
			// adaptertypes cannot have plugs
			temp.add(plugcont);
		}
		return temp;
	}

	@Override
	public FBTypeFigure getFigure() {
		return (FBTypeFigure) super.getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceContainerEditPart) {
			if (childEditPart.getModel() instanceof EventInputContainer) {
				getFigure().getInputEvents().add(child);
			}
			if (childEditPart.getModel() instanceof EventOutputContainer) {
				getFigure().getOutputEvents().add(child);
			}
			if (childEditPart.getModel() instanceof VariableInputContainer) {
				getFigure().getInputVariables().add(child);
			}
			if (childEditPart.getModel() instanceof VariableOutputContainer) {
				getFigure().getOutputVariables().add(child);
			}
			if (childEditPart.getModel() instanceof SocketContainer) {
				getFigure().getSockets().add(child);
			}
			if (childEditPart.getModel() instanceof PlugContainer) {
				getFigure().getPlugs().add(child);
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
				getFigure().getInputEvents().remove(child);
			}
			if (childEditPart.getModel() instanceof EventOutputContainer) {
				getFigure().getOutputEvents().remove(child);
			}
			if (childEditPart.getModel() instanceof VariableInputContainer) {
				getFigure().getInputVariables().remove(child);
			}
			if (childEditPart.getModel() instanceof VariableOutputContainer) {
				getFigure().getOutputVariables().remove(child);
			}
			if (childEditPart.getModel() instanceof SocketContainer) {
				getFigure().getSockets().remove(child);
			}
			if (childEditPart.getModel() instanceof PlugContainer) {
				getFigure().getPlugs().remove(child);
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private void update(final Rectangle bounds) {
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
	}

	@Override
	protected void refreshVisuals() {
		if (controlListener == null) {
			controlListener = new ControlListener() {

				@Override
				public void controlResized(final ControlEvent e) {
					Point p = getParent().getViewer().getControl().getSize();
					Dimension dim = getFigure().getPreferredSize(-1, -1);

					Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y / 2 - dim.height / 2, -1, -1);
					// rectangle rect = new Rectangle()

					update(rect);
				}

				@Override
				public void controlMoved(final ControlEvent e) {

				}

			};
			getParent().getViewer().getControl().addControlListener(controlListener);
		}
		Point p = getParent().getViewer().getControl().getSize();
		Dimension dim = getFigure().getPreferredSize(-1, -1);
		Rectangle rect = new Rectangle(p.x / 2 - dim.width / 2, p.y / 2 - dim.height / 2, -1, -1);
		update(rect);
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getTypeNameLabel();
	}
}
