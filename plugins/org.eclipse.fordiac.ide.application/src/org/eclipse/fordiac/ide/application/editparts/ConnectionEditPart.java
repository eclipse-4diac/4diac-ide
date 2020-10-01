/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH, AIT,
 * 				 2018 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Gerd Kainz,
 *   Filip Pröstl-Andren
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added separate colors for different data types
 *               - fixed hide event and data connection issues
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Shape;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.ConnectionTooltipFigure;
import org.eclipse.fordiac.ide.application.policies.ConnectionGraphicalNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.DeleteConnectionEditPolicy;
import org.eclipse.fordiac.ide.application.policies.DisableConnectionHandleRoleEditPolicy;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.policies.ConnectionHoverEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.router.BendpointPolicyRouter;
import org.eclipse.fordiac.ide.gef.router.RouterUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class ConnectionEditPart extends AbstractConnectionEditPart {
	private static final float[] BROKEN_CONNECTION_DASH_PATTERN = new float[] { 5.0f, 5.0f };
	private static final String HIDDEN = "HIDDEN"; //$NON-NLS-1$
	private static final String HIDEN_CON = "HIDEN_CON"; //$NON-NLS-1$

	public ConnectionEditPart() {
		super();
	}

	@Override
	public Connection getModel() {
		return (Connection) super.getModel();
	}

	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(PreferenceConstants.P_EVENT_CONNECTOR_COLOR)
				&& (getModel() instanceof EventConnection)) {
			getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		}
		if (event.getProperty().equals(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR)
				&& (getModel() instanceof AdapterConnection)) {
			getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		}
		if (PreferenceConstants.isDataConnectorProperty(event.getProperty())
				&& (getModel() instanceof DataConnection)) {
			getFigure().setForegroundColor(getDataConnectioncolor());
		}
		if (event.getProperty().equals(PreferenceConstants.P_HIDE_DATA_CON) && (getModel() instanceof DataConnection)) {
			getFigure().setVisible(!((Boolean) event.getNewValue()));
		}
		if (event.getProperty().equals(PreferenceConstants.P_HIDE_EVENT_CON)
				&& (getModel() instanceof EventConnection)) {
			getFigure().setVisible(!((Boolean) event.getNewValue()));
		}
	};

	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new FeedbackConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new DisableConnectionHandleRoleEditPolicy());

		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new DeleteConnectionEditPolicy());

		if (getConnectionFigure().getConnectionRouter() instanceof BendpointPolicyRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					((BendpointPolicyRouter) getConnectionFigure().getConnectionRouter())
							.getBendpointPolicy(getModel()));
		}

		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ConnectionGraphicalNodeEditPolicy());

		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ConnectionHoverEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = RouterUtil.getConnectionRouterFactory(null).createConnectionFigure();

		String status = getModel().getAttributeValue(HIDEN_CON);
		if (connection instanceof HideableConnection) {
			((HideableConnection) connection).setHidden((status != null) && status.equalsIgnoreCase(HIDDEN));
			if ((getModel() != null) && (getModel().getSourceElement() != null)) {
				((HideableConnection) connection)
						.setLabel(getModel().getSourceElement().getName() + "." + getModel().getSource().getName()); //$NON-NLS-1$
			}
			((HideableConnection) connection).setModel(getModel());
		}

		PolygonDecoration arrow = new PolygonDecoration();
		arrow.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		arrow.setScale(7, 4);
		connection.setTargetDecoration(arrow);

		if (getModel() instanceof EventConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
			connection.setVisible(
					!UIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_HIDE_EVENT_CON));
		}

		if (getModel() instanceof AdapterConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
			connection.setTargetDecoration(null);
			connection.setSourceDecoration(null);

		}

		if (getModel() instanceof DataConnection) {
			connection.setForegroundColor(getDataConnectioncolor());
			connection.setVisible(
					!UIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_HIDE_DATA_CON));

		}
		connection.setToolTip(new ConnectionTooltipFigure(getModel()));
		connection.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		return connection;
	}

	private Color getDataConnectioncolor() {
		// if the connections end point fb type could not be loaded it source or
		// destination may be null
		IInterfaceElement refElement = getModel().getSource();
		if (null == refElement) {
			refElement = getModel().getDestination();
		}
		if (null != refElement) {
			return PreferenceGetter.getDataColor(refElement.getType().getName());
		}
		return PreferenceGetter.getDefaultDataColor();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		if ((getConnectionFigure() instanceof PolylineConnection) && (getModel() != null)) {
			if (getModel().isBrokenConnection()) {
				((PolylineConnection) getConnectionFigure()).setLineStyle(SWT.LINE_CUSTOM);
				((PolylineConnection) getConnectionFigure()).setLineDash(BROKEN_CONNECTION_DASH_PATTERN);

			} else {
				((PolylineConnection) getConnectionFigure()).setLineStyle(SWT.LINE_SOLID);
			}
		}
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			UIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	private Adapter contentAdapter;

	private Adapter getContentAdapter() {
		if (contentAdapter == null) {
			contentAdapter = new AdapterImpl() {
				@Override
				public void notifyChanged(Notification notification) {
					Object feature = notification.getFeature();
					refreshVisuals();

					if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getConnection_Source().equals(feature)) {
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
			UIPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().remove(getContentAdapter());
		}
	}

	public void setTransparency(int value) {
		if (getFigure() instanceof PolylineConnection) {
			final PolylineConnection connection = ((PolylineConnection) getFigure());
			connection.setAlpha(value);
			for (final Object fig : connection.getChildren()) {
				if (fig instanceof Shape) {
					((Shape) fig).setAlpha(value);
				}
			}
		}
	}
}
