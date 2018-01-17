/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.util.preferences.PreferenceGetter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.swt.widgets.Display;


public class MonitoringEditPart extends AbstractMonitoringBaseEditPart  {


	public boolean isEvent() {
		return getInterfaceElement() instanceof Event;
	}

	public boolean isVariable() {
		return getInterfaceElement() instanceof VarDeclaration;
	}

	@Override
	public MonitoringElement getModel() {
		return (MonitoringElement) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		if(!isEvent()) {
			//only allow direct edit if it is not an event, see Bug 510735 for details.
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
					new DirectEditPolicy(){
	
						@Override
						protected Command getDirectEditCommand(DirectEditRequest request) {
							String value = (String) request.getCellEditor().getValue();
							MonitoringEditPart editPart = (MonitoringEditPart)getHost();
							MonitoringManager.getInstance().writeValue(editPart.getModel(), value);
							return null;
						}
	
						@Override
						protected void showCurrentEditValue(DirectEditRequest request) {
							String value = (String) request.getCellEditor().getValue();
							MonitoringEditPart editPart = (MonitoringEditPart)getHost();
							if (null != editPart) {
								editPart.getNameLabel().setText(value);
							}
							
						}
										
			});
		}
	}

	@Override
	protected void setBackgroundColor(IFigure l) {
		if (getModel().isForce()) {
			l.setBackgroundColor(PreferenceGetter
					.getColor(
							Activator.getDefault().getPreferenceStore(),
							org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_FORCE_COLOR));
		} else {
			super.setBackgroundColor(l);
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		SetableAlphaLabel l = new SetableAlphaLabel();
		setBackgroundColor(l);
		l.setOpaque(true);
		if (isInput()) {
			l.setLabelAlignment(PositionConstants.RIGHT);
			l.setTextAlignment(PositionConstants.RIGHT);
		} else {
			l.setTextAlignment(PositionConstants.LEFT);
			l.setLabelAlignment(PositionConstants.LEFT);
		}
		l.setBorder(new MarginBorder(0, 5, 0, 5));
		l.setText("N/A");
		l.setMinimumSize(new Dimension(50, 1));
		l.setAlpha(190);
		return l;
	}

	private EContentAdapter adapter;

	@Override
	protected EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {

				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							setValue(getModel().getCurrentValue());
							refreshVisuals();

						}
					});
				}

			};
		}
		return adapter;
	}
	
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			if(!isEvent()) {
				performDirectEdit();
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			return isVariable(); //Currently only allow direct when we are a variable
		} 
		return super.understandsRequest(request);
	}	

	public void setValue(String string) {
		if (isActive()) {
			if (getFigure() != null) {
				if (getModel().isForce() && getModel().getForceValue() != null) {
					((Label) getFigure()).setText(getModel().getForceValue() + " (" + string + ")");  //$NON-NLS-1$//$NON-NLS-2$
				} else {
					((Label) getFigure()).setText(string);
				}
				refreshVisuals();
			}
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateBreakpoint();
	}

	public void updateBreakpoint() {
		if (getModel().isBreakpoint()) {
			if ((((Label) getFigure()).getIcon() == null)) {
				((Label) getFigure()).setIcon(FordiacImage.ICON_BreakPoint.getImage());
			}
		} else {
			((Label) getFigure()).setIcon(null);
		}

		if (getModel().isBreakpointActive()) {
			//getFigure().setForegroundColor(org.eclipse.draw2d.ColorConstants.red);
			getFigure().setBackgroundColor(org.eclipse.draw2d.ColorConstants.red);
		} else {
			setBackgroundColor(getFigure());
		}
	}
	
}
