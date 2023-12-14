/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - fixed untyped subapp interface updates and according code
 *                 cleanup
 *               - allow navigation to parent by double-clicking on subapp
 *                 interface element
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.figures.UntypedSubappConnectorBorder;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.SWT;

public class UntypedSubAppInterfaceElementEditPart extends InterfaceEditPartForFBNetwork {

	public static class TargetInterfaceElement {
		private final IInterfaceElement refElement;

		public TargetInterfaceElement(final IInterfaceElement refElement) {
			this.refElement = refElement;
		}

		public IInterfaceElement getRefElement() {
			return refElement;
		}
	}

	public class UntypedSubappIEAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
				refresh();
			} else if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_Type().equals(feature)) {
				updateConnectorBorderColor();
				refreshToolTip();
			}
			super.notifyChanged(notification);
		}

		private void updateConnectorBorderColor() {
			Border border = getFigure().getBorder();
			if (border instanceof final CompoundBorder comBorder) {
				border = comBorder.getOuterBorder();
			}
			if (border instanceof final ConnectorBorder conBorder) {
				conBorder.updateColor();
				getFigure().repaint();
			}
		}

		public UntypedSubAppInterfaceElementEditPart getUntypedSubAppInterfaceElementEditPart() {
			return UntypedSubAppInterfaceElementEditPart.this;
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof final UntypedSubAppInterfaceElementEditPart editPart) {
					return new ResizeGroupOrSubappCommand((GraphicalEditPart) editPart.getParent(),
							ChangeNameCommand.forName(getModel(), (String) request.getCellEditor().getValue()));
				}
				return null;
			}
		});
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
			createDirectEditManager().show();
		} else {
			super.performRequest(request);
		}
	}

	private DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getNameLabel());
	}

	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new UntypedSubappIEAdapter();
	}

	@Override
	public void refresh() {
		super.refresh();
		getNameLabel().setText(getLabelText());
		refreshToolTip();
	}

	private void refreshToolTip() {
		getFigure().setToolTip(new ToolTipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));
	}

	@Override
	protected IFigure createFigure() {
		final InterfaceFigure figure = new InterfaceFigure() {
			@Override
			public String getSubStringText() {
				return (getChildren().isEmpty()) ? super.getSubStringText() : "";
			}

			@Override
			protected void paintFigure(final Graphics graphics) {
				if (!getChildren().isEmpty()) {
					graphics.fillRoundRectangle(getBounds(), 8, 8);
				}
				super.paintFigure(graphics);
			}
		};
		figure.setToolTip(new ToolTipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));

		figure.setBorder(new UntypedSubappConnectorBorder(getModel()));
		return figure;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		if (isInput()) {
			// we are unfolded and this is an internal connection
			return new FixedAnchor(getFigure(), !isInput());
		}
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if (!isInput()) {
			// we are unfolded and this is an internal connection
			return new FixedAnchor(getFigure(), !isInput());
		}
		return super.getTargetConnectionAnchor(connection);
	}

	@Override
	protected List getModelChildren() {
		// TODO cash created elements and update them accordingly
		return getTargetPins().map(TargetInterfaceElement::new).toList();
	}

	private Stream<IInterfaceElement> getTargetPins() {
		// TODO Distinguish between expanded subapp pins, fbs, and container subapp pin
		return getModel().getOutputConnections().stream().filter(con -> !con.isVisible())
				.flatMap(con -> con.getDestination().getOutputConnections().stream()).map(Connection::getDestination);

	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final var epFigure = getFigure();
		if (epFigure.getLayoutManager() == null) {
			final var tbLayout = new GridLayout(1, true);
			tbLayout.marginHeight = 2;
			tbLayout.verticalSpacing = 2;
			epFigure.setLayoutManager(tbLayout);
			epFigure.setBackgroundColor(ColorConstants.white);
		}
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		getContentPane().add(child, new GridData(SWT.FILL, SWT.BEGINNING, true, false), index);
	}

}