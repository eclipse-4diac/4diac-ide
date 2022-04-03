/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.application.policies.GroupXYLayoutPolicy;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.SelectionRequest;

public class GroupContentEditPart extends AbstractContainerContentEditPart {

	private class CreateInstanceDirectEditPolicy extends DirectEditPolicy {

		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			final Object value = request.getCellEditor().getValue();
			final Point refPoint = getInsertionPoint(request);
			if (value instanceof PaletteEntry) {
				return new CreateFBElementInGroupCommand((PaletteEntry) value, getModel().getGroup(), refPoint.x,
						refPoint.y);
			}
			return null;
		}

		private Point getInsertionPoint(final DirectEditRequest request) {
			final Point refPoint = new Point(FBNetworkRootEditPart.getInsertPos(request, getViewer(), getZoom()));
			final Point topLeft = getFigure().getClientArea().getTopLeft();
			refPoint.translate(-topLeft.x, -topLeft.y);
			return refPoint;
		}

		private double getZoom() {
			final RootEditPart root = getHost().getRoot();
			if (root instanceof ScalableFreeformRootEditPart) {
				return ((ScalableFreeformRootEditPart) root).getZoomManager().getZoom();
			}
			return 1.0;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			// we don't need to do anything here for creating new fb instances
		}

	}

	@Override
	public GroupContentNetwork getModel() {
		return (GroupContentNetwork) super.getModel();
	}

	@Override
	protected List<FBNetworkElement> getNetworkElements() {
		// our model is the group and the getNetworkElements all elements in the group we want to show as children
		return getModel().getNetworkElements();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GroupXYLayoutPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new CreateInstanceDirectEditPolicy());
	}

	@Override
	public void setLayoutConstraint(final EditPart child, final IFigure childFigure, final Object constraint) {
		if ((constraint instanceof Rectangle)  && (child instanceof ValueEditPart)){
			final Rectangle rectConstraint = (Rectangle) constraint;
			final Point topLeft = getFigure().getClientArea().getTopLeft();
			rectConstraint.performTranslate(-topLeft.x, -topLeft.y);
		}
		super.setLayoutConstraint(child, childFigure, constraint);
	}

	@Override
	public void performRequest(final Request request) {
		final Object type = request.getType();
		if ((type == RequestConstants.REQ_DIRECT_EDIT || type == RequestConstants.REQ_OPEN)
				&& (request instanceof SelectionRequest)) {
			performDirectEdit((SelectionRequest) request);
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public Object getAdapter(final Class key) {
		if (key == Group.class) {
			return getModel().getGroup();
		}
		return super.getAdapter(key);
	}

	private NewInstanceDirectEditManager createDirectEditManager() {
		return new NewInstanceDirectEditManager(this, getPalette(), false);
	}

	private Palette getPalette() {
		final EObject root = EcoreUtil.getRootContainer(getModel().getGroup());
		return (root instanceof LibraryElement) ? ((LibraryElement) root).getPaletteEntry().getPalette() : null;
	}

	void performDirectEdit(final SelectionRequest request) {
		final NewInstanceDirectEditManager directEditManager = createDirectEditManager();
		directEditManager.updateRefPosition(
				new org.eclipse.swt.graphics.Point(request.getLocation().x, request.getLocation().y));
		if (request.getExtendedData().isEmpty()) {
			directEditManager.show();
		} else {
			final Object key = request.getExtendedData().keySet().iterator().next();
			if (key instanceof String) {
				directEditManager.show((String) key);
			}
		}
	}

}
