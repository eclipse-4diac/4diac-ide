/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hengy, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.MoveBendpointCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.TransitionBendPointEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.STStringTokenHandling;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Path;

public class ECTransitionEditPart extends AbstractConnectionEditPart {
	
	private class TransitionOrderDecorator extends Ellipse implements RotatableDecoration{

		private Label orderLabel;
		
		public TransitionOrderDecorator() {
			super();
			setLayoutManager(new StackLayout());			
			orderLabel = new Label();
			orderLabel.setOpaque(false);
			this.setFill(true);
			this.setAntialias(1);
			this.setBackgroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ECC_TRANSITION_COLOR));
			this.setOutline(false);
			orderLabel.setForegroundColor(ColorConstants.white);
			orderLabel.setFont(new Font(null,"Tahoma",7,0)); //$NON-NLS-1$
			add(orderLabel);
		}
		
		@Override
		public void setReferencePoint(Point p) {
			//we don't want this decorator to be rotated so that the number keeps readable			
		}
		
		@Override
		public void setLocation(Point p) {
			//Position the decorator centered on the start of the transition 
			p.x -= getSize().width() / 2;
			p.y -= getSize().height() / 2;			
			super.setLocation(p);
		}
		
		public void setText(String val){
			orderLabel.setText(val);
			if("".equals(orderLabel.getText())){ //$NON-NLS-1$
				setSize(0, 0);     //this hads the decorator
			}else{
				setSize(14, 14);  //TODO find a better way to determine the required size
			}
		}
			
	}
	
	private Label condition;
	private TransitionOrderDecorator transitionOrderDecorator;

	
	
	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			refresh();
		}
	};

	private void updateOrderLabel() {
		ECTransition transition = getCastedModel();
		if (null != transition.getSource()) {
			if (1 < transition.getSource().getOutTransitions().size()) {
				int i = 1;
				for (ECTransition runner : transition.getSource()
						.getOutTransitions()) {
					if (runner.equals(transition)) {
						transitionOrderDecorator.setText(Integer.toString(i));
					}
					i++;
				}
			} else {
				// if we are the only transition we don't need to enumerate it
				transitionOrderDecorator.setText(""); //$NON-NLS-1$
			}
		}
	}

	/** The adapter. */
	private final EContentAdapter interfaceAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getEventType() == Notification.REMOVE) {
				if ((notification.getOldValue() == getCastedModel()
						.getConditionEvent())
						|| ((getCastedModel().getConditionEvent() instanceof AdapterEvent)
								&& (notification.getOldValue() instanceof AdapterDeclaration) && (((AdapterEvent) getCastedModel()
								.getConditionEvent()).getAdapterDeclaration() == notification
								.getOldValue()))) {
					AbstractDirectEditableEditPart.executeCommand(new ChangeConditionEventCommand(getCastedModel(), "")); //$NON-NLS-1$
				}
			} else if (notification.getEventType() == Notification.SET) {
				if (null != getCastedModel().getConditionEvent()) {
					handleCondiationEventUpdate(notification);
				}

				if (notification.getNotifier() instanceof VarDeclaration) {
					checkConditionExpresion(notification);
				}
			}
		}

		private void handleCondiationEventUpdate(Notification notification) {
			if (notification.getNewValue() instanceof String) {
				String newValue = (String)notification.getNewValue();
				if ((getCastedModel().getConditionEvent().getName().equals(newValue)) ||
						((getCastedModel().getConditionEvent() instanceof AdapterEvent) && 
								(((AdapterEvent) getCastedModel().getConditionEvent()).getAdapterDeclaration().getName().equals(newValue)))) {
					super.notifyChanged(notification);
					refresh();
				}
			}
		}

		private void checkConditionExpresion(Notification notification) {
			if (notification.getNewValue() instanceof String) {
				Object feature = notification.getFeature();
				if ((LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) && 
						(null != getCastedModel().getConditionExpression()) && 
						(-1 != getCastedModel().getConditionExpression().indexOf(notification.getOldStringValue()))) {
					String expresion = STStringTokenHandling.replaceSTToken(getCastedModel().getConditionExpression(),
							notification.getOldStringValue(), notification.getNewStringValue());
					getCastedModel().setConditionExpression(expresion);
					refresh();
				}
			}
		}
	};

	/**
	 * Gets the casted model.
	 * 
	 * @return the casted model
	 */
	public ECTransition getCastedModel() {
		return (ECTransition) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// // Selection handle edit policy.
		// // Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());

		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				new TransitionBendPointEditPolicy(getCastedModel()));

		// // Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {

					@Override
					protected Command getDeleteCommand(
							final GroupRequest request) {
						return new DeleteTransitionCommand(getCastedModel());
					}

				});

		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {

			@Override
			public Command getCommand(Request request) {
				if (RequestConstants.REQ_MOVE.equals(request.getType()) && request instanceof ChangeBoundsRequest) {
					return getTransitionMoveCommand((ChangeBoundsRequest) request);
				}
				return null;
			}

			@Override
			public boolean understandsRequest(Request request) {
				return RequestConstants.REQ_MOVE.equals(request.getType());

			}

			private Command getTransitionMoveCommand(ChangeBoundsRequest request) {

				Point p = new Point(getCastedModel().getX(), getCastedModel().getY());
				p.x += request.getMoveDelta().x;
				p.y += request.getMoveDelta().y;

				return new MoveBendpointCommand(getCastedModel(), p);
			}

			@Override
			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef
	 * .Request)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		condition.setText(getCastedModel().getConditionText());
		updateOrderLabel();

		AbsoluteBendpoint ab = new AbsoluteBendpoint(getCastedModel().getX(), getCastedModel().getY());
		List<Bendpoint> bendPoints = new ArrayList<>();
		bendPoints.add(ab);

		getConnectionFigure().getConnectionRouter().setConstraint(
				getConnectionFigure(), bendPoints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {

		final PolylineConnection connection = new PolylineConnection() {
			@Override
			protected void outlineShape(Graphics g) {
				Path p = new Path(null);
				p.moveTo(getStart().x, getStart().y);
				p.quadTo(getPoints().getMidpoint().x, getPoints().getMidpoint().y, getEnd().x, getEnd().y);
				g.drawPath(p);
				p.dispose();
			}
			
		};
		connection.setLineWidth(2);
		connection.setAntialias(SWT.ON);


		PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		rectDec.setScale(7, 4);
		rectDec.setFill(true);

		connection.setForegroundColor(PreferenceGetter
				.getColor(PreferenceConstants.P_ECC_TRANSITION_COLOR));

		connection.setTargetDecoration(rectDec);

		ConnectionLocator constraintLocator = new ConnectionLocator(connection,
				ConnectionLocator.MIDDLE) {
			@Override
			protected Point getReferencePoint() {
				
				Path path = new Path(null);
				path.moveTo(connection.getStart().x, connection.getStart().y);
				path.quadTo(connection.getPoints().getMidpoint().x, connection.getPoints().getMidpoint().y, connection.getEnd().x, connection.getEnd().y);
				PointList ptl = new PointList();
				float[] linePoints = path.getPathData().points;
				for (int i = 0; i+1 < linePoints.length; i+=2) {
					int x = (int)linePoints[i];
					int y = (int)linePoints[i+1];
					ptl.addPoint(x,y);
				}
				path.dispose();
				Point p = getLocation(ptl);
				getConnection().translateToAbsolute(p);
				return p;
			}
		};
		condition = new Label(getCastedModel().getConditionText());
		condition.setBorder(new MarginBorder(3, 6, 3, 6));
		condition.setOpaque(true);
		connection.add(condition, constraintLocator);

		AbsoluteBendpoint ab = new AbsoluteBendpoint(getCastedModel().getX(), getCastedModel().getY());
		List<Bendpoint> bendPoints = new ArrayList<Bendpoint>();
		bendPoints.add(ab);

		BendpointConnectionRouter bcr = new BendpointConnectionRouter();
		bcr.setConstraint(connection, bendPoints);
		connection.setConnectionRouter(bcr);
		
		transitionOrderDecorator =  new TransitionOrderDecorator();
		connection.setSourceDecoration(transitionOrderDecorator);
		updateOrderLabel();

		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getCastedModel().eAdapters().add(adapter);
			getCastedModel().eContainer().eAdapters().add(adapter);

			// Adapt to the fbtype so that we get informed on interface changes
			((BasicFBType) getCastedModel().eContainer().eContainer())
					.getInterfaceList().eAdapters().add(interfaceAdapter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getCastedModel().eAdapters().remove(adapter);
			getCastedModel().eContainer().eAdapters().remove(adapter);

			((BasicFBType) getCastedModel().eContainer().eContainer())
					.getInterfaceList().eAdapters().remove(interfaceAdapter);
		}
	}
	
	public void highlight(boolean highlight) {
		PolylineConnection pc = null;
		if (getConnectionFigure() instanceof PolylineConnection) {
			pc = (PolylineConnection) getConnectionFigure();
		}
		if (highlight && pc != null) {
			pc.setLineWidth(3);
		} else if (!highlight && pc != null) {
			pc.setLineWidth(2);
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

}
