/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;

public class InstanceCommentEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

	private DirectEditManager manager;

	@Override
	public void activate() {
		super.activate();
		getModel().getRefElement().eAdapters().add(contentAdapter);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getRefElement().eAdapters().remove(contentAdapter);
		if (manager != null) {
			manager = null;
		}
	}

	private final Adapter contentAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(Notification notification) {
			Object feature = notification.getFeature();
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
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(DirectEditRequest request) {
				if (getHost() instanceof InstanceCommentEditPart) {
					String str = (String) request.getCellEditor().getValue();
					final String EMPTY_COMMENT = "[" + Messages.InstanceCommentEditPart_EMPTY_COMMENT + "]"; //$NON-NLS-1$ //$NON-NLS-3$
					if (!EMPTY_COMMENT.equals(str)) {
						return new ChangeCommentCommand(
								((InstanceCommentEditPart) getHost()).getModel().getRefElement(), str);
					}
				}
				return null;
			}
		});
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

		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();

		} else {
			super.performRequest(request);
		}
	}

	private DirectEditManager getManager() {
		if (null == manager) {
			manager = createDirectEditManager();
		}
		return manager;
	}

	private DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getFigure());
	}

	private void performDirectEdit() {
		getManager().show();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return null;
	}

}
