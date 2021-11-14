/*******************************************************************************
 * Copyright (c) 2008 - 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH, AIT,
 * 							 Johannes Kepler University Linz,
 * 							 Primetals Technologies Germany GmbH,
 *                           Primetals Technologies Austria GmbH
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
 *               - reworked connection selection and hover feedback
 *   Lukas Wais	 - reworked connection colors
 *   Michael Oberlehner - added support for hidden connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.ConnectionTooltipFigure;
import org.eclipse.fordiac.ide.application.policies.DeleteConnectionEditPolicy;
import org.eclipse.fordiac.ide.application.tools.FBNScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.handles.ScrollingConnectionEndpointHandle;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.router.BendpointPolicyRouter;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
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
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public class ConnectionEditPart extends AbstractConnectionEditPart {

	private static final class FBNConnectionEndPointHandle extends ScrollingConnectionEndpointHandle {
		private FBNConnectionEndPointHandle(final org.eclipse.gef.ConnectionEditPart owner, final int endPoint) {
			super(owner, endPoint);
			setLocator(new ConnectionLocator(getConnection(), endPoint) {
				@Override
				protected Point getLocation(final PointList points) {
					final Point p = super.getLocation(points);
					// ensure that the returned point is such that the endpoint handle is on the
					// connection
					switch (getAlignment()) {
					case SOURCE:
						p.x += ((getPreferredSize().width / 2) - 4); // TODO replace with connection
						// border size
						break;
					case TARGET:
						p.x += ((-getPreferredSize().width / 2) + 4);
						break;
					default:
						break;
					}
					return p;
				}
			});
		}

		@Override
		protected void init() {
			super.init();
			setPreferredSize((ConnectionPreferenceValues.HANDLE_SIZE * 4) / 3, ConnectionPreferenceValues.HANDLE_SIZE);
		}

		@Override
		protected void paintHandleCenter(final Graphics g, final Rectangle r) {
			final int xbuf = r.x;
			final int wbuf = r.width;

			final int shrinkVal = getInnerShrinkVal();
			r.shrink(shrinkVal, shrinkVal + 1);
			switch (getEndPoint()) {
			case ConnectionLocator.SOURCE:
				r.x = xbuf;
				break;
			case ConnectionLocator.TARGET:
				r.x = (xbuf + wbuf) - r.width;
				break;
			default:
				break;
			}
			g.fillRoundRectangle(r, r.height / 2, r.height / 2);

		}

		@Override
		protected ConnectionEndpointTracker createConnectionEndPointTracker(
				final org.eclipse.gef.ConnectionEditPart connectionEditPart) {
			return new FBNScrollingConnectionEndpointTracker(connectionEditPart);
		}
	}

	private static final float[] BROKEN_CONNECTION_DASH_PATTERN = new float[] { 5.0f, 5.0f };

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
			getFigure().setVisible(!((Boolean) event.getNewValue()).booleanValue());
		}
		if (event.getProperty().equals(PreferenceConstants.P_HIDE_EVENT_CON)
				&& (getModel() instanceof EventConnection)) {
			getFigure().setVisible(!((Boolean) event.getNewValue()).booleanValue());
		}
	};

	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new FeedbackConnectionEndpointEditPolicy() {
			@Override
			protected ConnectionEndpointHandle createConnectionEndPointHandle(
					final org.eclipse.gef.ConnectionEditPart connectionEditPart, final int connectionLocator) {
				return new FBNConnectionEndPointHandle(connectionEditPart, connectionLocator);
			}
		});

		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new DeleteConnectionEditPolicy());

		if (getConnectionFigure().getConnectionRouter() instanceof BendpointPolicyRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					((BendpointPolicyRouter) getConnectionFigure().getConnectionRouter())
					.getBendpointPolicy(getModel()));
		}
	}

	@Override
	protected IFigure createFigure() {
		final HideableConnection connectionFigure = new HideableConnection();
		connectionFigure.setModel(getModel());
		setConnectionColor(connectionFigure);  // needs to be done before setHidden
		connectionFigure.setHidden(!getModel().isVisible());

		performConnTypeConfiguration(connectionFigure);
		connectionFigure.setToolTip(new ConnectionTooltipFigure(getModel()));
		connectionFigure.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		return connectionFigure;
	}

	private void performConnTypeConfiguration(final HideableConnection connectionFigure) {
		if (getModel() instanceof EventConnection) {
			connectionFigure.setVisible(
					!UIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_HIDE_EVENT_CON));
		}

		if (getModel() instanceof DataConnection) {
			connectionFigure.setVisible(
					!UIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_HIDE_DATA_CON));
		}
	}

	@Override
	public HideableConnection getFigure() {
		return (HideableConnection) super.getFigure();
	}

	@Override
	public HideableConnection getConnectionFigure() {
		return (HideableConnection) super.getConnectionFigure();
	}

	private void setConnectionColor(final PolylineConnection connection) {
		if (getModel() instanceof EventConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		}

		if (getModel() instanceof AdapterConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		}

		if (getModel() instanceof DataConnection) {
			connection.setForegroundColor(getDataConnectioncolor());
		}
	}

	private Color getDataConnectioncolor() {
		IInterfaceElement refElement = getModel().getSource();

		// if the connections end point fb type could not be loaded it source or
		// destination may be null
		if ((null == refElement)) {
			refElement = getModel().getDestination();
		}

		if (null != refElement) {
			final DataType dataType = refElement.getType();
			//check if source is not of type for which we can determine the color
			if (!isColoredDataype(dataType) && (refElement == getModel().getSource())) {
				// if source is of a non defined color type the connection should be colored the other way
				// take destination for determining the color
				refElement = getModel().getDestination();
			}
		}

		if (null != refElement) {
			return PreferenceGetter.getDataColor(refElement.getType().getName());
		}

		return PreferenceGetter.getDefaultDataColor();
	}

	private static boolean isColoredDataype(final DataType dataType) {
		return (dataType == IecTypes.ElementaryTypes.BOOL) || (dataType instanceof AnyBitType)
				|| (dataType instanceof AnyIntType) || (dataType instanceof AnyRealType)
				|| (dataType instanceof AnyStringType) || (dataType instanceof StructuredType);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (getModel() != null) {
			if (getModel().isBrokenConnection()) {
				getConnectionFigure().setLineStyle(SWT.LINE_CUSTOM);
				getConnectionFigure().setLineDash(BROKEN_CONNECTION_DASH_PATTERN);

			} else {
				getConnectionFigure().setLineStyle(SWT.LINE_SOLID);
				getConnectionFigure().setLineDash(null);
			}
			getConnectionFigure().setHidden(!getModel().isVisible());
			getConnectionFigure().updateConLabels();
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
			contentAdapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					final Object feature = notification.getFeature();
					refreshVisuals();
					if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getConnection_Source().equals(feature)) {
						refreshComment();
					}
					if (LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)) {
						setConnectionColor(getFigure());
						// reset the line width so that any to struct connections have the right width
						getFigure().setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
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

	public void setTransparency(final int value) {
		final PolylineConnection connection = getFigure();
		connection.setAlpha(value);
		for (final Object fig : connection.getChildren()) {
			if (fig instanceof Shape) {
				((Shape) fig).setAlpha(value);
			}
		}
	}
}
