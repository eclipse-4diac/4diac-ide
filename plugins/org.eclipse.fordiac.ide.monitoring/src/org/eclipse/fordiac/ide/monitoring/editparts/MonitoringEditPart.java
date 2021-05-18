/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.widgets.Display;

public class MonitoringEditPart extends AbstractMonitoringBaseEditPart {

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(PreferenceConstants.P_MONITORING_TRANSPARENCY)) {
			((SetableAlphaLabel) getFigure()).setAlpha(PreferenceConstants.getMonitoringTransparency());
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

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
		if (!isEvent()) {
			// only allow direct edit if it is not an event, see Bug 510735 for details.
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
				@Override
				protected Command getDirectEditCommand(final DirectEditRequest request) {
					final String value = (String) request.getCellEditor().getValue();
					final MonitoringEditPart editPart = (MonitoringEditPart) getHost();
					MonitoringManager.getInstance().writeValue(editPart.getModel(), value);
					return null;
				}

				@Override
				protected void showCurrentEditValue(final DirectEditRequest request) {
					final String value = (String) request.getCellEditor().getValue();
					final MonitoringEditPart editPart = (MonitoringEditPart) getHost();
					if (null != editPart) {
						editPart.getNameLabel().setText(value);
					}
				}
			});
		}
	}

	@Override
	protected void setBackgroundColor(final IFigure l) {
		if (getModel().isForce()) {
			l.setBackgroundColor(PreferenceGetter.getColor(Activator.getDefault().getPreferenceStore(),
					org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_FORCE_COLOR));
		} else {
			super.setBackgroundColor(l);
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		final SetableAlphaLabel l = new SetableAlphaLabel();
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
		l.setAlpha(PreferenceConstants.getMonitoringTransparency());
		return l;
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				Display.getDefault().asyncExec(() -> {
					setValue(getModel().getCurrentValue());
					refreshVisuals();
				});
			}

		};
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			if (!isEvent()) {
				performDirectEdit();
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			return isVariable(); // Currently only allow direct when we are a variable
		}
		return super.understandsRequest(request);
	}

	public void setValue(final String string) {
		if (isActive() && getFigure() != null) {
			if (getModel().isForce() && getModel().getForceValue() != null) {
				((Label) getFigure()).setText(getModel().getForceValue() + " (" + string + ")"); //$NON-NLS-1$//$NON-NLS-2$
			} else {
				((Label) getFigure()).setText(string);
			}
			refreshVisuals();
		}
	}

}
