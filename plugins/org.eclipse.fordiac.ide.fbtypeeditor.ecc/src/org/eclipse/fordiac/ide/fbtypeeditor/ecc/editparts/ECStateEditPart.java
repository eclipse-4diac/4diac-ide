/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateSelectionPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.TransitionNodeEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.GradientLabel;
import org.eclipse.fordiac.ide.gef.figures.HorizontalLineFigure;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;

public class ECStateEditPart extends AbstractDirectEditableEditPart implements NodeEditPart {
	private List<Object> stateChildren;

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (Notification.REMOVING_ADAPTER != notification.getEventType()) {
				Object feature = notification.getFeature();
				if (!((LibraryElementPackage.eINSTANCE.getECAction_Algorithm().equals(feature))
						|| (LibraryElementPackage.eINSTANCE.getECAction_Output().equals(feature))
						|| (LibraryElementPackage.eINSTANCE.getECState().equals(feature)))) {
					refresh();
				}
			}
		}
	};

	/** The ecc adapter. */
	private final EContentAdapter eccAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			updateBorder();
		}
	};

	public ECStateEditPart() {
		setConnectable(true);
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
			getModel().getECC().eAdapters().add(eccAdapter);
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);

			if (getModel().getECC() != null) {
				getModel().getECC().eAdapters().remove(eccAdapter);
			}
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

	public static class StateBorder extends LineBorder {
		private boolean initialState;

		public boolean isInitialState() {
			return initialState;
		}

		public void setInitialState(boolean initialState) {
			this.initialState = initialState;
		}

		public StateBorder() {
			super();
			initialState = false;
		}

		public StateBorder(boolean isInitialState) {
			this();
			initialState = isInitialState;
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			if (initialState) {
				return new Insets(6, 9, 6, 9);
			} else {
				return new Insets(3, 6, 3, 6);
			}
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {

			super.paint(figure, graphics, insets);

			graphics.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
			graphics.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			figure.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));

			graphics.drawRectangle(tempRect);
			if (initialState) {
				Rectangle tempRect2 = new Rectangle(tempRect);
				tempRect2.shrink(3, 3);
				graphics.drawRectangle(tempRect2);
			}
		}

	}

	public class ECStateFigure extends Figure implements InteractionStyleFigure {
		private StateBorder stateBorder = new StateBorder();
		private Label nameLabel;
		private final Figure line = new HorizontalLineFigure(15);
		private final Figure actionContainer = new Figure() {
			@Override
			public void add(IFigure figure, Object constraint, int index) {
				super.add(figure, constraint, index);
				setConstraint(figure, new GridData(SWT.FILL, SWT.BEGINNING, true, false));
			}
		};

		public ECStateFigure() {
			ToolbarLayout tbLayout = new ToolbarLayout();
			tbLayout.setStretchMinorAxis(false);
			tbLayout.setHorizontal(true);
			setLayoutManager(tbLayout);
			Figure stateLabel = new Figure();
			add(stateLabel);
			FlowLayout layout = new FlowLayout();
			layout.setStretchMinorAxis(true);
			layout.setMajorSpacing(0);
			layout.setMinorSpacing(0);
			layout.setHorizontal(true);
			layout.setMinorAlignment(OrderedLayout.ALIGN_CENTER);
			stateLabel.setLayoutManager(layout);
			nameLabel = new GradientLabel(((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager());
			stateLabel.add(nameLabel);
			nameLabel.setText(getModel().getName());
			nameLabel.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
			nameLabel.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			nameLabel.setOpaque(true);
			nameLabel.setBorder(new StateBorder(isInitialState()));
			stateLabel.add(line);
			line.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			add(actionContainer);
			GridLayout gl = new GridLayout(2, false);
			gl.horizontalSpacing = -1;
			gl.verticalSpacing = 0;
			gl.marginWidth = 0;
			gl.marginHeight = 0;
			actionContainer.setLayoutManager(gl);
		}

		/**
		 * Sets the checks for action.
		 *
		 * @param hasAction the new checks for action
		 */
		public void setHasAction(final boolean hasAction) {
			line.setVisible(hasAction);
		}

		public Figure getContentPane() {
			return actionContainer;
		}

		public Label getNameLabel() {
			return nameLabel;
		}

		public Figure getLine() {
			return line;
		}

		public StateBorder getStateBorder() {
			return stateBorder;
		}

		@Override
		public int getIntersectionStyle(Point location) {
			Rectangle bounds = nameLabel.getBounds().getCopy();
			bounds.x = bounds.x + 3;
			bounds.y = bounds.y + 3;
			bounds.width = bounds.width - 6;
			bounds.height = bounds.height - 6;
			if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_CONNECTION; // connection
			}
			return InteractionStyleFigure.REGION_DRAG; // move/drag
		}
	}

	private void updateBorder() {
		getNameLabel().setBorder(new StateBorder(isInitialState()));
	}

	private boolean isInitialState() {
		return getModel().isStartState();
	}

	@Override
	public IFigure getContentPane() {
		return ((ECStateFigure) getFigure()).getContentPane();
	}

	public List<Object> getCurrentChildren() {
		return stateChildren;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		if (null == stateChildren) {
			stateChildren = new ArrayList<>();
		} else {
			stateChildren.clear();
		}
		for (ECAction ecAction : getModel().getECAction()) {
			stateChildren.add(new ECActionAlgorithm(ecAction));
			stateChildren.add(new ECActionOutputEvent(ecAction));
		}
		return stateChildren;
	}

	@Override
	protected IFigure createFigure() {
		return new ECStateFigure();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ECStateLayoutEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new TransitionNodeEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				if (!getModel().getECC().getStart().equals(getModel())) {
					return new DeleteECStateCommand(getModel());
				}
				return null;
			}
		});
		// Highlight In and Out-Transitions of the selected State
		installEditPolicy("Highlight_IN_OUTS", //$NON-NLS-1$
				new ECStateSelectionPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// transform doubleclick to direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
	}

	@Override
	public ECState getModel() {
		return (ECState) super.getModel();
	}

	@Override
	protected void refreshVisuals() {
		Rectangle rect = new Rectangle(getModel().getX(), getModel().getY(), -1, -1);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);
		((ECStateFigure) getFigure()).setHasAction(!getModel().getECAction().isEmpty());
		super.refreshVisuals();
	}

	@Override
	protected List<ECTransition> getModelSourceConnections() {
		return getModel().getOutTransitions();
	}

	@Override
	protected List<ECTransition> getModelTargetConnections() {
		return getModel().getInTransitions();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		if (connection.getTarget() != null && connection.getTarget().equals(connection.getSource())) {
			return new FixedAnchor(((ECStateFigure) getFigure()).getNameLabel(), false);
		}
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if (connection.getSource() != null && connection.getSource().equals(connection.getTarget())) {
			return new FixedAnchor(((ECStateFigure) getFigure()).getNameLabel(), true);
		}
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public Label getNameLabel() {
		return ((ECStateFigure) getFigure()).getNameLabel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(PreferenceConstants.P_ECC_STATE_COLOR)) {
			getNameLabel().setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
		}
		if (event.getProperty().equals(PreferenceConstants.P_ECC_STATE_BORDER_COLOR)) {
			getNameLabel().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			((ECStateFigure) getFigure()).getLine()
					.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
		}
	};

	public void highlightTransitions(boolean highlight) {
		for (Object obj : getSourceConnections()) {
			if (obj instanceof ECTransitionEditPart) {
				((ECTransitionEditPart) obj).highlight(highlight);
			}
		}
		for (Object obj : getTargetConnections()) {
			if (obj instanceof ECTransitionEditPart) {
				((ECTransitionEditPart) obj).highlight(highlight);
			}
		}
	}

	@Override
	protected DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getNameLabel(), new IdentifierVerifyListener());
	}
}