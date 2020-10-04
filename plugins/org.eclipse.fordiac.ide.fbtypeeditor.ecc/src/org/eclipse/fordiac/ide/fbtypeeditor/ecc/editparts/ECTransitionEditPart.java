/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hengy, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted TransitionFigure code and changed to cubic spline
 *   Alois Zoitl - reworked transition and handle widths
 *   Bianca Wiesmayr, Ernst Blecha - added tooltip
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.MoveBendpointCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECTransitionFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECTransitionFeedbackEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.TransitionBendPointEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.fordiac.ide.util.STStringTokenHandling;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.util.IPropertyChangeListener;

public class ECTransitionEditPart extends AbstractConnectionEditPart {

	private static final int NORMAL_WIDTH = 2;

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			refreshTransitionTooltip();
			refresh();
		}
	};

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(PreferenceConstants.P_ECC_TRANSITION_COLOR)) {
			getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_TRANSITION_COLOR));
		}
	};

	private void updateOrderLabel() {
		ECTransition transition = getModel();
		if (null != transition.getSource()) {
			if (transition.getSource().getOutTransitions().size() > 1) {
				getConnectionFigure().setTransitionOrder(Integer.toString(transition.getPriority()));
			} else {
				// if we are the only transition we don't need to enumerate it
				getConnectionFigure().setTransitionOrder(""); //$NON-NLS-1$
			}
		}
	}

	private void refreshTransitionTooltip() {
		getFigure().getToolTip().setECTransition(this.getModel());
	}

	/** The adapter. */
	private final Adapter interfaceAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getEventType() == Notification.REMOVE) {
				if ((notification.getOldValue() == getModel().getConditionEvent())
						|| ((getModel().getConditionEvent() instanceof AdapterEvent)
								&& (notification.getOldValue() instanceof AdapterDeclaration)
								&& (((AdapterEvent) getModel().getConditionEvent())
										.getAdapterDeclaration() == notification.getOldValue()))) {
					AbstractDirectEditableEditPart.executeCommand(new ChangeConditionEventCommand(getModel(), "")); //$NON-NLS-1$
				}
			} else if (notification.getEventType() == Notification.SET) {
				if (null != getModel().getConditionEvent()) {
					handleCondiationEventUpdate(notification);
				}

				if (notification.getNotifier() instanceof VarDeclaration) {
					checkConditionExpression(notification);
				}
			}
		}

		private void handleCondiationEventUpdate(Notification notification) {
			if (notification.getNewValue() instanceof String) {
				String newValue = (String) notification.getNewValue();
				if ((getModel().getConditionEvent().getName().equals(newValue))
						|| ((getModel().getConditionEvent() instanceof AdapterEvent)
								&& (((AdapterEvent) getModel().getConditionEvent()).getAdapterDeclaration().getName()
										.equals(newValue)))) {
					super.notifyChanged(notification);
					refresh();
				}
			}
		}

		private void checkConditionExpression(Notification notification) {
			if (notification.getNewValue() instanceof String) {
				Object feature = notification.getFeature();
				if ((LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature))
						&& (null != getModel().getConditionExpression())
						&& (-1 != getModel().getConditionExpression().indexOf(notification.getOldStringValue()))) {
					String expresion = STStringTokenHandling.replaceSTToken(getModel().getConditionExpression(),
							notification.getOldStringValue(), notification.getNewStringValue());
					getModel().setConditionExpression(expresion);
					refresh();
				}
			}
		}
	};

	@Override
	public ECTransition getModel() {
		return (ECTransition) super.getModel();
	}

	@Override
	public ECTransitionFigure getConnectionFigure() {
		return (ECTransitionFigure) super.getConnectionFigure();
	}

	@Override
	protected void createEditPolicies() {
		// // Selection handle edit policy.
		// // Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ECTransitionFeedbackEditPolicy());

		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new TransitionBendPointEditPolicy(getModel()));

		// // Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {

			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteTransitionCommand(getModel());
			}

		});

		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {

			@Override
			public Command getCommand(Request request) {
				if (RequestConstants.REQ_MOVE.equals(request.getType()) && (request instanceof ChangeBoundsRequest)) {
					return getTransitionMoveCommand((ChangeBoundsRequest) request);
				}
				return null;
			}

			@Override
			public boolean understandsRequest(Request request) {
				return RequestConstants.REQ_MOVE.equals(request.getType());

			}

			private Command getTransitionMoveCommand(ChangeBoundsRequest request) {
				Point p = new Point(getModel().getX(), getModel().getY());
				double scaleFactor = ((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager().getZoom();
				p.scale(scaleFactor);
				p.x += request.getMoveDelta().x;
				p.y += request.getMoveDelta().y;
				p.scale(1.0 / scaleFactor);
				return new MoveBendpointCommand(getModel(), p);
			}

			@Override
			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}

		});
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// TODO implement direct edit

		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected void refreshVisuals() {
		getConnectionFigure().setConditionText(getModel().getConditionText());
		getConnectionFigure().updateBendPoints(getModel());
		updateOrderLabel();
		refreshTransitionTooltip();
	}

	@Override
	protected IFigure createFigure() {
		ECTransitionFigure figure = new ECTransitionFigure(getModel());
		figure.setLineWidth(NORMAL_WIDTH);
		return figure;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
			getModel().getECC().eAdapters().add(adapter);

			// Adapt to the fbtype so that we get informed on interface changes
			getModel().getECC().getBasicFBType().getInterfaceList().eAdapters().add(interfaceAdapter);

			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);
			getModel().getECC().eAdapters().remove(adapter);
			getModel().getECC().getBasicFBType().getInterfaceList().eAdapters().remove(interfaceAdapter);

			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

	public void highlight(boolean highlight) {
		PolylineConnection pc = getConnectionFigure();
		if (null != pc) {
			pc.setLineWidth((highlight) ? ConnectionPreferenceValues.HIGHLIGTHED_LINE_WIDTH : NORMAL_WIDTH);
		}
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new org.eclipse.gef.tools.DragEditPartsTracker(this) {

			@Override
			protected boolean isMove() {
				if (getSourceEditPart() instanceof ECTransitionEditPart) {
					return true;
				}
				return super.isMove();
			}

		};
	}

	@Override
	public ECTransitionFigure getFigure() {
		return (ECTransitionFigure) super.getFigure();
	}
}
