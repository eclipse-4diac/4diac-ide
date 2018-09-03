/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH, AIT,
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Gerd Kainz, 
 *   Filip Pröstl-Andren 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Shape;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.ConnectionTooltipFigure;
import org.eclipse.fordiac.ide.application.policies.ConnectionGraphicalNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.DeleteConnectionEditPolicy;
import org.eclipse.fordiac.ide.application.policies.DisableConnectionHandleRoleEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.router.BendpointPolicyRouter;
import org.eclipse.fordiac.ide.gef.router.RouterUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.fordiac.ide.util.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.util.preferences.PreferenceGetter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;

public class ConnectionEditPart extends AbstractConnectionEditPart {
	private static final String HIDDEN = "HIDDEN"; //$NON-NLS-1$
	private static final String HIDEN_CON = "HIDEN_CON"; //$NON-NLS-1$

	public ConnectionEditPart() {
		super();
	}

	@Override
	public Connection getModel() {
		return (Connection) super.getModel();
	}

	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(PreferenceConstants.P_EVENT_CONNECTOR_COLOR) &&  getModel() instanceof EventConnection) {
				getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
			}
			if (event.getProperty().equals(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR) && getModel() instanceof AdapterConnection) {
				getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
			}
			if (event.getProperty().equals(PreferenceConstants.P_DATA_CONNECTOR_COLOR) && getModel() instanceof DataConnection) {
				getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_DATA_CONNECTOR_COLOR));
			}
			if (event.getProperty().equals(PreferenceConstants.P_HIDE_DATA_CON) && getModel() instanceof DataConnection) {
				getFigure().setVisible(!((Boolean) event.getNewValue()));
			}
			if (event.getProperty().equals(PreferenceConstants.P_HIDE_EVENT_CON) && getModel() instanceof EventConnection) {
				getFigure().setVisible(!((Boolean) event.getNewValue()));
			}
		}
	};

	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new FeedbackConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new DisableConnectionHandleRoleEditPolicy());

		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new DeleteConnectionEditPolicy());

		if (getConnectionFigure().getConnectionRouter() instanceof BendpointPolicyRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					((BendpointPolicyRouter) getConnectionFigure()
							.getConnectionRouter())
							.getBendpointPolicy(getModel()));
		}
		
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ConnectionGraphicalNodeEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = RouterUtil.getConnectionRouterFactory(null).createConnectionFigure();

		String status = getModel().getAttributeValue(HIDEN_CON);
		if (connection instanceof HideableConnection) {
			((HideableConnection) connection)
					.setHidden((status != null && status.equalsIgnoreCase(HIDDEN) ? true : false));
			if (getModel() != null && getModel().getSourceElement() != null) {
				((HideableConnection) connection)
						.setLabel(getModel().getSourceElement().getName() + "." + getModel().getSource().getName()); //$NON-NLS-1$
			}
			((HideableConnection) connection).setModel(getModel());
		}

		PolygonDecoration arrow = new PolygonDecoration();
		arrow.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		arrow.setScale(7, 4);
		connection.setTargetDecoration(arrow);

		PolygonDecoration arrow1 = new PolygonDecoration();
		arrow1.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
		arrow1.setScale(7, 4);
		arrow1.setLocation(new org.eclipse.draw2d.geometry.Point(10, 10));
		connection.setSourceDecoration(arrow1);

		if (getModel() instanceof EventConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		}

		if (getModel() instanceof AdapterConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
			connection.setTargetDecoration(null);
			connection.setSourceDecoration(null);

		}

		if (getModel() instanceof DataConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_DATA_CONNECTOR_COLOR));
		}
		connection.setToolTip(new ConnectionTooltipFigure(getModel()));
		return connection;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		if ((getConnectionFigure() instanceof PolylineConnection) && (getModel() != null)) {
			if (getModel().isBrokenConnection()) {
				((PolylineConnection) getConnectionFigure()).setLineStyle(SWT.LINE_DASH);

			} else {
				((PolylineConnection) getConnectionFigure()).setLineStyle(SWT.LINE_SOLID);
			}
		}
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	private EContentAdapter contentAdapter;

	private Adapter getContentAdapter() {
		if (contentAdapter == null) {
			contentAdapter = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification notification) {
					Object feature = notification.getFeature();
					refreshVisuals();

					if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
						refreshComment();
					}
				}

			};
		}
		return contentAdapter;
	}

	private void refreshComment() {
		getFigure().setToolTip(new ConnectionTooltipFigure(getModel()));
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().remove(getContentAdapter());
		}
	}

	public void setTransparency(int value) {
		if (getFigure() instanceof PolylineConnection) {
			PolylineConnection connection = ((PolylineConnection) getFigure());
			connection.setAlpha(value);
			for (Object fig : connection.getChildren()) {
				if (fig instanceof Shape) {
					((Shape) fig).setAlpha(value);
				}
			}
		}
	}
}
