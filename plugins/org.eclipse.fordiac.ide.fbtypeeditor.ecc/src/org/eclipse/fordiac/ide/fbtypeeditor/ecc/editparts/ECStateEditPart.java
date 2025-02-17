/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020        Johannes Kepler University Linz
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
 *   Virendra Ashiwal, Bianca Wiesmayr
 *     - added tooltip functionality at state
 *     - added refresh tooltip at state
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECStateFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateSelectionPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.TransitionNodeEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.util.IPropertyChangeListener;

public class ECStateEditPart extends AbstractDirectEditableEditPart implements NodeEditPart {
	private List<Object> stateChildren;

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (Notification.REMOVING_ADAPTER != notification.getEventType()) {
				final Object feature = notification.getFeature();
				if ((!(LibraryElementPackage.eINSTANCE.getECAction_Algorithm().equals(feature))
						&& !(LibraryElementPackage.eINSTANCE.getECAction_Output().equals(feature))
						&& !(LibraryElementPackage.eINSTANCE.getECState().equals(feature)))) {
					refreshStateTooltip();
					refresh();
				}
			}
		}

	};

	private void refreshStateTooltip() {
		getFigure().getToolTip().setECState(this.getModel());

	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public IFigure getContentPane() {
		return getFigure().getContentPane();
	}

	public List<Object> getCurrentChildren() {
		return stateChildren;
	}

	@Override
	protected List<Object> getModelChildren() {
		if (null == stateChildren) {
			stateChildren = new ArrayList<>();
		} else {
			stateChildren.clear();
		}
		for (final ECAction ecAction : getModel().getECAction()) {
			stateChildren.add(new ECActionAlgorithm(ecAction));
			stateChildren.add(new ECActionOutputEvent(ecAction));
		}
		return stateChildren;
	}

	@Override
	protected IFigure createFigure() {
		return new ECStateFigure(getModel());
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
		final Rectangle rect = new Rectangle(getModel().getPosition().toScreenPoint(), new Dimension(-1, -1));
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);
		getFigure().setHasAction(!getModel().getECAction().isEmpty());
		refreshStateTooltip();
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
		if ((connection.getTarget() != null) && connection.getTarget().equals(connection.getSource())) {
			return new FixedAnchor(getFigure().getNameLabel(), false);
		}
		return new ChopboxAnchor(getFigure().getNameLabel());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure().getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if ((connection.getSource() != null) && connection.getSource().equals(connection.getTarget())) {
			return new FixedAnchor(getFigure().getNameLabel(), true);
		}
		return new ChopboxAnchor(getFigure().getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure().getNameLabel());
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getNameLabel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_STATE_COLOR)) {
			getNameLabel().setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_COLOR));
		}
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR)) {
			getNameLabel().setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR));
			getFigure().getLine()
					.setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR));
		}
	};

	public void highlightTransitions(final boolean highlight) {
		for (final Object obj : getSourceConnections()) {
			if (obj instanceof final ECTransitionEditPart ectEP) {
				ectEP.highlight(highlight);
			}
		}
		for (final Object obj : getTargetConnections()) {
			if (obj instanceof final ECTransitionEditPart ectEP) {
				ectEP.highlight(highlight);
			}
		}
	}

	@Override
	public ECStateFigure getFigure() {
		return (ECStateFigure) super.getFigure();
	}

	@Override
	public boolean isConnectable() {
		return true;
	}
}