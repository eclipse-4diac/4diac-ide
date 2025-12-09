/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Moved position calculation to the comment type edit part
 *   Virendra Ashiwal - extracted "getnumEventwith" method out of calculateWithPos() method
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.DeleteInterfaceEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.WithNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class InterfaceEditPart extends AbstractInterfaceElementEditPart
		implements NodeEditPart, AnnotableGraphicalEditPart {

	public class InterfaceFigure extends Label implements InteractionStyleFigure {
		public InterfaceFigure() {
			setText(getINamedElement().getName());
			setBorder(new ConnectorBorder(getCastedModel()));
			setOpaque(false);
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
		}

		@Override
		public int getIntersectionStyle(final Point location) {
			if (isInput()) {
				final Rectangle bounds = getBounds().getCopy();
				bounds.width = 5;
				if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
					return InteractionStyleFigure.REGION_CONNECTION;
				}
				return InteractionStyleFigure.REGION_DRAG;
			}
			final Rectangle bounds = getBounds().getCopy();
			bounds.x = bounds.x + (bounds.width - 5);
			bounds.width = 5;
			if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_CONNECTION;
			}
			return InteractionStyleFigure.REGION_DRAG;
		}

		public void updateConnectorColor() {
			Border curBorder = getBorder();
			if (curBorder instanceof final CompoundBorder comBorder) {
				curBorder = comBorder.getOuterBorder();
			}
			if (curBorder instanceof final ConnectorBorder conBorder) {
				conBorder.updateColor();
			}
		}
	}

	@Override
	protected IFigure createFigure() {
		final IFigure fig = new InterfaceFigure();
		fig.addAncestorListener(createAncestorListener());
		return fig;
	}

	protected AncestorListener createAncestorListener() {
		return new AncestorListener() {
			@Override
			public void ancestorRemoved(final IFigure ancestor) {
				// nothing to do here
			}

			@Override
			public void ancestorMoved(final IFigure ancestor) {
				refreshVisuals();
			}

			@Override
			public void ancestorAdded(final IFigure ancestor) {
				refreshVisuals();
			}

		};
	}

	@Override
	protected Adapter createAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				refresh();
				if (LibraryElementPackage.eINSTANCE.getEvent_With().equals(notification.getFeature())) {
					refreshTypeRoot();
				}
			}
		};
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel(), event,
				FordiacAnnotationUtil::showOnTarget, FordiacAnnotationUtil::showOnTargetName);
		final CommentTypeEditPart commentTypeEditPart = findAssociatedCommentTypeEP();
		if (commentTypeEditPart != null) {
			commentTypeEditPart.updateAnnotations(event);
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (getCastedModel() instanceof Event && null != sourceConnections) {
			for (final ConnectionEditPart con : sourceConnections) {
				final WithEditPart with = (WithEditPart) con;
				with.updateWithPos();
			}
		}
		if ((getCastedModel() instanceof VarDeclaration) && (getFigure() instanceof InterfaceFigure)) {
			((InterfaceFigure) getFigure()).updateConnectorColor();
		}
	}

	@Override
	public IInterfaceElement getCastedModel() {
		return (IInterfaceElement) getModel();
	}

	@Override
	public void activate() {
		super.activate();
		checkAssociatedCommentType();
		// tell the root edipart that we are here and that it should add the type
		// comment children
		refreshTypeRoot();
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

	private void refreshTypeRoot() {
		final FBTypeRootEditPart typeRootEP = getFBTypeRootEP();
		if (typeRootEP != null) {
			typeRootEP.refresh();
			typeRootEP.getChildren().stream().filter(CommentTypeEditPart.class::isInstance)
					.forEach(ep -> ((CommentTypeEditPart) ep).refreshVisuals());
		}
	}

	private FBTypeRootEditPart getFBTypeRootEP() {
		for (final Object part : getRoot().getChildren()) {
			if (part instanceof final FBTypeRootEditPart fbtRootEP) {
				return fbtRootEP;
			}
		}
		return null;
	}

	public void setInOutConnectionsWith(final int with) {
		for (final ConnectionEditPart cep : getSourceConnections()) {
			if (cep.getFigure() instanceof final PolylineConnection plc) {
				plc.setLineWidth(with);
			}
		}
		for (final ConnectionEditPart cep : getTargetConnections()) {
			if (cep.getFigure() instanceof final PolylineConnection plc) {
				plc.setLineWidth(with);
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		// allow delete of a FB
		if (isInterfaceEditable()) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteInterfaceEditPolicy());

			installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new WithNodeEditPolicy());
		}

	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// Perform double click as direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
	}

	@Override
	protected List<With> getModelSourceConnections() {
		if (isEvent()) {
			return ((Event) getModel()).getWith();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<With> getModelTargetConnections() {
		if (isVariable()) {
			return ((VarDeclaration) getModel()).getWiths();
		}
		return Collections.emptyList();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		final int pos = calculateWithPos((With) connection.getModel(), isInput());
		if (isInput()) {
			return new InputWithAnchor(getFigure(), pos, this);
		}
		return new OutputWithAnchor(getFigure(), pos, this);
	}

	public static int calculateWithPos(final With with, final boolean isInput) {
		final Event event = (Event) with.eContainer();
		final InterfaceList interfaceList = (InterfaceList) event.eContainer();
		if (null != interfaceList) {
			return getnumEventwith(isInput ? interfaceList.getEventInputs() : interfaceList.getEventOutputs(), event);
		}
		return 0;
	}

	protected static int getnumEventwith(final EList<Event> eList, final Event event) {
		int nrOfEventWITH = 0;
		for (final Event ele : eList) {
			if (!ele.getWith().isEmpty()) {
				nrOfEventWITH++;
			}
			if (ele == event) {
				break;
			}
		}
		return nrOfEventWITH;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return getSourceConnectionAnchor(connection);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	private void checkAssociatedCommentType() {
		final CommentTypeEditPart ep = findAssociatedCommentTypeEP();
		if (ep != null && ep.getReferencedInterface() == null) {
			// the associated comment type editpart was created before us
			ep.setupReferencedEP();
		}
	}

	private CommentTypeEditPart findAssociatedCommentTypeEP() {
		return (CommentTypeEditPart) getViewer().getEditPartRegistry().values().stream()
				.filter(CommentTypeEditPart.class::isInstance)
				.filter(c -> this.getModel().equals(((CommentTypeEditPart) c).getInterfaceElement())).findAny()
				.orElse(null);
	}
}
