/*******************************************************************************
 * Copyright (c) 2019, 2021 Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - altered for instance comment
 *   Alois Zoitl - added support for multiline comments
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.editors.InstanceCommentDEManager;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.ui.IEditorPart;

public class InstanceCommentEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

	private class InstanceCommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost() instanceof final InstanceCommentEditPart instanceCommentEditPart) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ChangeCommentCommand(instanceCommentEditPart.getModel().getRefElement(), str);
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getFigure().setText(value);
		}
	}

	@Override
	public void activate() {
		super.activate();
		getModel().getRefElement().eAdapters().add(contentAdapter);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getRefElement().eAdapters().remove(contentAdapter);
	}

	private final Adapter contentAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
				refreshValue();
			}
		}
	};

	void refreshValue() {
		getFigure().setText(getModel().getInstanceComment());
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
	}

	@Override
	public InstanceCommentFigure getFigure() {
		return (InstanceCommentFigure) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		// FBNetwork elements need a special rename command therefore we remove the
		// standard edit policy and add a adjusted one
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new InstanceCommentRenameEditPolicy());
	}

	@Override
	public InstanceComment getModel() {
		return (InstanceComment) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		return new InstanceCommentFigure();
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		final IEditorPart editor = EditorUtils.getCurrentActiveEditor();

		if ((getModel().getRefElement() instanceof Application
				|| (getModel().getRefElement() instanceof FBNetworkElement
						&& !((FBNetworkElement) (getModel().getRefElement())).isContainedInTypedInstance()))
				&& ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
						|| (request.getType() == RequestConstants.REQ_OPEN))
				&& editor.getAdapter(FBNetwork.class) != null) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	private void performDirectEdit() {
		new InstanceCommentDEManager(this).show();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return null;
	}

}
