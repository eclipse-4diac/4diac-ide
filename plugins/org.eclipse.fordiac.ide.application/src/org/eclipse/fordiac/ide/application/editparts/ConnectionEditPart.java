/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH, AIT,
 * 							Johannes Kepler University Linz,
 * 							Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - extracted FBNConnectionEndPointHandle to own java file
 *               - update selection on visibilty toggling
 *   Prankur Agarwal - update property listener for max hidden connection label size
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.ConnectionTooltipFigure;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnectionLabel;
import org.eclipse.fordiac.ide.application.policies.DeleteConnectionEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNConnectionEndpointPolicy;
import org.eclipse.fordiac.ide.application.tools.ConnectionSelectEditPartTracker;
import org.eclipse.fordiac.ide.application.widgets.OppositeSelectionDialog;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.gef.router.BendpointPolicyRouter;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

public class ConnectionEditPart extends AbstractConnectionEditPart implements AnnotableGraphicalEditPart {

	private class ConnectionContentAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();

			refreshVisuals();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_Source().equals(feature)) {
				refreshTooltip();
			}
			if (LibraryElementPackage.eINSTANCE.getConnection_Source().equals(feature)) {
				addSourceAdapters();
			}
			if (LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)) {
				addDestinationAdapters();
				setConnectionColor(getFigure());
				// reset the line width so that any to struct connections have the right width
				getFigure().setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
			}

			if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(feature)) {
				// the hidden property was changed inform source and destination so that all
				// labels are updated
				handleVisibilityUpdate();
			}

			if (LibraryElementPackage.eINSTANCE.getDataConnection_FBNetwork().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getEventConnection_FBNetwork().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getAdapterConnection_FBNetwork().equals(feature)) {
				getFigure().handleVisibilityChange(!getModel().isVisible());
			}
		}

		private void handleVisibilityUpdate() {
			getModel().getSource()
					.eNotify(new ENotificationImpl((InternalEObject) getModel().getSource(), Notification.SET,
							LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections(), getModel(),
							getModel()));
			getModel().getDestination()
					.eNotify(new ENotificationImpl((InternalEObject) getModel().getDestination(), Notification.SET,
							LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections(), getModel(),
							getModel()));
			final FBNConnectionEndpointPolicy conEPPolicy = getConnectionEndPointPolicy();
			if (conEPPolicy.isSelectionFeedbackShowing()) {
				// redraw selection to update to new connection figure
				conEPPolicy.hideSelection();
				conEPPolicy.showSelection();
			}
		}
	}

	private final class SrcDstAdapter extends AdapterImpl {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getFBNetworkElement_Group().equals(feature)) {
				if ((getSource() instanceof final InterfaceEditPartForFBNetwork editPart
						&& !editPart.getModel().isIsInput())) {
					updateConnectionLables(editPart.getSourceConnections());
				}
				if ((getTargetEP() instanceof final InterfaceEditPartForFBNetwork editPart
						&& editPart.getModel().isIsInput())) {
					updateConnectionLables(editPart.getTargetConnections());
				}
			}
			if (LibraryElementPackage.eINSTANCE.getFBNetworkElement_Group().equals(feature)
					|| (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
							.equals(notification.getFeature()) && unfoldedStateChanged(notification))) {
				getFigure().handleVisibilityChange(getFigure().isHidden()); // triggers new label creation
			}
		}

		private boolean unfoldedStateChanged(final Notification notification) {
			if (notification.getNewValue() instanceof final Attribute attribute
					&& attribute.getAttributeDeclaration() == InternalAttributeDeclarations.UNFOLDED) {
				return true;
			}
			return notification.getOldValue() instanceof final Attribute attribute
					&& attribute.getAttributeDeclaration() == InternalAttributeDeclarations.UNFOLDED;
		}
	}

	private static void updateConnectionLables(final List<?> editPartConnections) {
		editPartConnections.stream().filter(ConnectionEditPart.class::isInstance).map(ConnectionEditPart.class::cast)
				.map(ConnectionEditPart::getFigure).forEach(FBNetworkConnection::updateConLabels);
	}

	private EditPart getTargetEP() {
		// needed so that name is not shadowed in SrcDstAdapter! otherwise delivers
		// target of adapter
		return getTarget();
	}

	private static final float[] BROKEN_CONNECTION_DASH_PATTERN = new float[] { 5.0f, 5.0f };

	public ConnectionEditPart() {
		// nothing to do here
	}

	@Override
	public Connection getModel() {
		return (Connection) super.getModel();
	}

	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR)
				&& (getModel() instanceof EventConnection)) {
			getFigure().setForegroundColor(PreferenceGetter.getColor(UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		}
		if (event.getProperty().equals(UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR)
				&& (getModel() instanceof AdapterConnection)) {
			getFigure().setForegroundColor(PreferenceGetter.getColor(UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
		}
		if (UIPreferenceConstants.isDataConnectorProperty(event.getProperty())
				&& (getModel() instanceof DataConnection)) {
			getFigure().setForegroundColor(getDataConnectioncolor());
		}
		if (event.getProperty().equals(UIPreferenceConstants.P_HIDE_DATA_CON)
				&& (getModel() instanceof DataConnection)) {
			getFigure().setVisible(!((Boolean) event.getNewValue()).booleanValue());
		}
		if (event.getProperty().equals(UIPreferenceConstants.P_HIDE_EVENT_CON)
				&& (getModel() instanceof EventConnection)) {
			getFigure().setVisible(!((Boolean) event.getNewValue()).booleanValue());
		}
		if (event.getProperty().equals(GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE)) {
			getFigure().updateConLabels();
		}
	};

	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new FBNConnectionEndpointPolicy());

		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new DeleteConnectionEditPolicy());

		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				((BendpointPolicyRouter) getConnectionFigure().getConnectionRouter()).getBendpointPolicy(getModel()));
	}

	@Override
	protected IFigure createFigure() {
		final FBNetworkConnection connectionFigure = new FBNetworkConnection(this);
		setConnectionColor(connectionFigure); // needs to be done before setHidden
		connectionFigure.setHidden(!getModel().isVisible());

		performConnTypeConfiguration(connectionFigure);
		connectionFigure
				.setToolTip(new ConnectionTooltipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));
		connectionFigure.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		return connectionFigure;
	}

	private void performConnTypeConfiguration(final FBNetworkConnection connectionFigure) {
		if (getModel() instanceof EventConnection) {
			connectionFigure
					.setVisible(!UIPreferenceConstants.STORE.getBoolean(UIPreferenceConstants.P_HIDE_EVENT_CON));
		}

		if (getModel() instanceof DataConnection) {
			connectionFigure.setVisible(!UIPreferenceConstants.STORE.getBoolean(UIPreferenceConstants.P_HIDE_DATA_CON));
		}
	}

	@Override
	public FBNetworkConnection getFigure() {
		return (FBNetworkConnection) super.getFigure();
	}

	@Override
	public FBNetworkConnection getConnectionFigure() {
		return (FBNetworkConnection) super.getConnectionFigure();
	}

	private void setConnectionColor(final PolylineConnection connection) {
		if (getModel() instanceof EventConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR));
		}

		if (getModel() instanceof AdapterConnection) {
			connection.setForegroundColor(PreferenceGetter.getColor(UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR));
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
			// check if source is not of type for which we can determine the color
			if (!isColoredDataype(dataType) && (refElement == getModel().getSource())) {
				// if source is of a non defined color type the connection should be colored the
				// other way
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
			getConnectionFigure().revalidate();
		}
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			UIPreferenceConstants.STORE.addPropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().add(getContentAdapter());
			addSourceAdapters();
			addDestinationAdapters();
		}
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_OPEN && request instanceof final SelectionRequest selReq)) {
			if (isDecoratorTargeted(selReq.getLocation(), getFigure().getSourceDecoration())) {
				final IInterfaceElement sourcePin = getModel().getSource();
				if (sourcePin.getOutputConnections().size() > 1) {
					followMultipleTargetConnections(sourcePin,
							sourcePin.getOutputConnections().stream().map(Connection::getDestination).toList());
				} else {
					followConnection(getTargetEP());
				}
			}
			if (isDecoratorTargeted(selReq.getLocation(), getFigure().getTargetDecoration())) {
				final IInterfaceElement destPin = getModel().getDestination();
				if (destPin.getInputConnections().size() > 1) {
					followMultipleTargetConnections(destPin,
							destPin.getInputConnections().stream().map(Connection::getSource).toList());
				} else {
					followConnection(getSource());
				}
			}
		}
		super.performRequest(request);
	}

	private void followMultipleTargetConnections(final IInterfaceElement originPin,
			final List<IInterfaceElement> targetList) {
		final GraphicalViewer viewer = (GraphicalViewer) getViewer();
		final GraphicalEditPart firstTargetEP = (GraphicalEditPart) viewer.getEditPartForModel(targetList.getFirst());
		HandlerHelper.selectEditPart(viewer, firstTargetEP);
		viewer.flush();

		final var dialog = new OppositeSelectionDialog(targetList, originPin, viewer.getControl(),
				firstTargetEP.getFigure(),
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor());
		dialog.open();
	}

	private void followConnection(final EditPart targetEP) {
		final EditPartViewer viewer = getViewer();
		HandlerHelper.selectEditPart(viewer, targetEP);
	}

	private static boolean isDecoratorTargeted(Point location, final FBNetworkConnectionLabel decoration) {
		if (decoration != null) {
			location = location.getCopy();
			decoration.translateToRelative(location);
			return decoration.getBounds().contains(location);
		}
		return false;
	}

	private void addDestinationAdapters() {
		if (getModel().getDestination() != null && dstPinAdapter.getTarget() == null) {
			getModel().getDestination().eAdapters().add(dstPinAdapter);
			if (getModel().getDestinationElement() != null) {
				getModel().getDestinationElement().eAdapters().add(dstFBAdapter);
			}
		}
	}

	private void addSourceAdapters() {
		if (getModel().getSource() != null && srcPinAdapter.getTarget() == null) {
			getModel().getSource().eAdapters().add(srcPinAdapter);
			if (getModel().getSourceElement() != null) {
				getModel().getSourceElement().eAdapters().add(srcFBAdapter);
			}
		}
	}

	private Adapter contentAdapter;
	private final Adapter srcPinAdapter = new SrcDstAdapter();
	private final Adapter srcFBAdapter = new SrcDstAdapter();
	private final Adapter dstPinAdapter = new SrcDstAdapter();
	private final Adapter dstFBAdapter = new SrcDstAdapter();

	private Adapter getContentAdapter() {
		if (contentAdapter == null) {
			contentAdapter = new ConnectionContentAdapter();
		}
		return contentAdapter;
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			UIPreferenceConstants.STORE.removePropertyChangeListener(propertyChangeListener);
			getModel().eAdapters().remove(getContentAdapter());

			if (srcPinAdapter.getTarget() != null) {
				srcPinAdapter.getTarget().eAdapters().remove(srcPinAdapter);
			}
			if (srcFBAdapter.getTarget() != null) {
				srcFBAdapter.getTarget().eAdapters().remove(srcFBAdapter);
			}
			if (dstPinAdapter.getTarget() != null) {
				dstPinAdapter.getTarget().eAdapters().remove(dstPinAdapter);
			}
			if (dstFBAdapter.getTarget() != null) {
				dstFBAdapter.getTarget().eAdapters().remove(dstFBAdapter);
			}
		}
	}

	public void setTransparency(final int value) {
		final PolylineConnection connection = getFigure();
		connection.setAlpha(value);
		for (final Object fig : connection.getChildren()) {
			if (fig instanceof final Shape shape) {
				shape.setAlpha(value);
			}
		}
	}

	@Override
	public DragTracker getDragTracker(final Request req) {
		return new ConnectionSelectEditPartTracker(this);
	}

	public boolean isSelectionShown() {
		return getConnectionEndPointPolicy().isSelectionFeedbackShowing();
	}

	private FBNConnectionEndpointPolicy getConnectionEndPointPolicy() {
		return (FBNConnectionEndpointPolicy) getEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE);
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel(), event);
		refreshTooltip();
	}

	private void refreshTooltip() {
		getFigure().setToolTip(new ConnectionTooltipFigure(getModel(),
				FordiacAnnotationUtil.getAnnotationModel(ConnectionEditPart.this)));
	}
}
