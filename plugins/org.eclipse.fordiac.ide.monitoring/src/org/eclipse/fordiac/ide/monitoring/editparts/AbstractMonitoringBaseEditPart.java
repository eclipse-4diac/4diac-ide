/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler 
 * 							 University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.editparts;

import java.util.ArrayList;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.util.preferences.PreferenceGetter;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public abstract class AbstractMonitoringBaseEditPart extends AbstractViewEditPart implements SpecificLayerEditPart{
	
	private InterfaceEditPart parentPart;

	private IPropertyChangeListener listener;


	
	/**
	 * FIXME implement deactivate
	 */
	@Override
	public void activate() {
		super.activate();
		for (Object object : getViewer().getEditPartRegistry().keySet()) {
			if (object instanceof IInterfaceElement) {
				IInterfaceElement interfaceElement = (IInterfaceElement) object;
				if (interfaceElement.equals(getInterfaceElement())) {
					EditPart part = (EditPart) getViewer().getEditPartRegistry().get(object);
					if (part instanceof InterfaceEditPart) {
						parentPart = (InterfaceEditPart) part;
						IFigure f = parentPart.getFigure();
						f.addAncestorListener(new AncestorListener() {

							@Override
							public void ancestorRemoved(IFigure ancestor) {
								// nothing to do
							}

							@Override
							public void ancestorMoved(IFigure ancestor) {
								refreshVisuals();
							}

							@Override
							public void ancestorAdded(IFigure ancestor) {
								// nothing to do
							}
						});
					}	
				}
				else if (interfaceElement instanceof AdapterDeclaration) {
					IInterfaceElement subInterfaceElement = null;
					InterfaceList interfaceList = ((AdapterType)((AdapterDeclaration)interfaceElement).getType()).getInterfaceList();
					ArrayList<IInterfaceElement> list = new ArrayList<IInterfaceElement>();
					list.addAll(interfaceList.getEventInputs());
					list.addAll(interfaceList.getEventOutputs());
					list.addAll(interfaceList.getInputVars());
					list.addAll(interfaceList.getOutputVars());
					for (IInterfaceElement element : list) {
						if (element.equals(getInterfaceElement()) && interfaceElement.eContainer().eContainer() == getModel().getPort().getFb()) {
							subInterfaceElement = element;
							break;
						}
					}
					
					if (subInterfaceElement != null) {
						Object subObject = null;
						for (Object obj : getViewer().getEditPartRegistry().values()) {
							if (obj instanceof MonitoringAdapterEditPart) {
								MonitoringAdapterEditPart part = (MonitoringAdapterEditPart)obj;
								if (part.getModel().getPort().getInterfaceElement() == interfaceElement) {
									for (Object subView : part.getModelChildren()) {
										if (((IInterfaceElement)subView).getName().equals(subInterfaceElement.getName())) {
											subObject = subView;
											break;
										}
									}
									if (subObject != null) {
										break;
									}
								}
							}
						}

						if (subObject != null) {
							EditPart part = (EditPart) getViewer().getEditPartRegistry().get(subObject);
							if (part instanceof InterfaceEditPart) {
								parentPart = (InterfaceEditPart) part;
								IFigure f = parentPart.getFigure();
								f.addAncestorListener(new AncestorListener() {
		
									@Override
									public void ancestorRemoved(IFigure ancestor) {
										// nothing to do
									}
		
									@Override
									public void ancestorMoved(IFigure ancestor) {
										// calculatePos()
										refreshVisuals();
		
									}
		
									@Override
									public void ancestorAdded(IFigure ancestor) {
										// nothing to do
									}
								});
							}
						}
					}
				}
			}
		}
		org.eclipse.fordiac.ide.monitoring.Activator.getDefault().getPreferenceStore().addPropertyChangeListener(getPreferenceChangeListener());
		refreshVisuals();
	}

	@Override
	public INamedElement getINamedElement() {
		return getInterfaceElement();
	}

	@Override
	public Label getNameLabel() {
		return (Label)getFigure();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		if (listener == null) {
			listener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					if (event
							.getProperty()
							.equals(org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_WATCH_COLOR)
							|| event.getProperty()
									.equals(org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_FORCE_COLOR)) {
						setBackgroundColor(getFigure());
					}
				}
			};
		}
		return listener;

	}



	@Override
	public String getSpecificLayer() {
		return ZoomScalableFreeformRootEditPart.TOPLAYER;
	}
	
	@Override
	public MonitoringBaseElement getModel() {
		return (MonitoringBaseElement) super.getModel();
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		return super.understandsRequest(request);
	}
	
	private Point calculatePos() {
		if (parentPart != null) {
			Rectangle bounds = parentPart.getFigure().getBounds();
			int x = 0;
			if (isInput()) {
				int width = 40;
				width = getFigure().getBounds().width;
				width = Math.max(40, width);
				x = bounds.x - 2 - width;
			}
			else {
				x = bounds.x + bounds.width + 2;
			}
			int y = bounds.y;
			return new Point(x, y);
		}
		return new Point(0, 0);
	}
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
		setBackgroundColor(getFigure());
		getFigure().setEnabled(!getModel().isOffline());
	}

	protected void refreshPosition() {
		if (getParent() != null) {
			Rectangle bounds = null;
			Point p = calculatePos();
			int width = getFigure().getPreferredSize().width;
			width = Math.max(40, width);
			bounds = new Rectangle(p.x, p.y, width, -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this,
					getFigure(), bounds);

		}
	}
	
	protected void setBackgroundColor(IFigure l) {
		l.setBackgroundColor(PreferenceGetter.getColor(
				Activator.getDefault().getPreferenceStore(),
				org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_WATCH_COLOR));
	}
	
	@Override
	protected void backgroundColorChanged(IFigure figure) {
		setBackgroundColor(figure);
	}


	protected boolean isInput() {
		return getInterfaceElement().isIsInput();
	}
	
	protected IInterfaceElement getInterfaceElement() {
		return getModel().getPort().getInterfaceElement();
	}


}
