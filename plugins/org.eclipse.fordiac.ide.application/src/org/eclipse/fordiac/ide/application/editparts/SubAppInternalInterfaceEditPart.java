/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Johannes Kepler University,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - allow navigation to parent by double-clicking on subapp
 *                 interface element
 *               - show hidden connections in interface bar
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.ui.IEditorPart;

public class SubAppInternalInterfaceEditPart extends UntypedSubAppInterfaceElementEditPart {

	@Override
	protected IFigure createFigure() {
		final IFigure figure = super.createFigure();
		figure.setBorder(new ConnectorBorder(getModel()) {
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			targetInteraceAdapter = new TargetInterfaceAdapter(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			if (targetInteraceAdapter != null) {
				targetInteraceAdapter.deactivate();
			}
			super.deactivate();
		}
	}

	@Override
	public boolean isInput() {
		return !super.isInput();
	}

	@Override
	protected boolean isUnfoldedSubapp() {
		return false; // in the subapp editor we are always not unfolded
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}

	@Override
	protected void refreshVisuals() {
		getFigure().setToolTip(new ToolTipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));
		super.refreshVisuals();
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// REQ_OPEN -> doubleclick
			goToParent();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected Adapter createContentAdapter() {
		return new UntypedSubappIEAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();

				if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getAttribute_Value().equals(feature)) {
					updatePadding(getYPositionFromAttribute(getModel()));
				}
				super.notifyChanged(notification);
			}
		};
	}

	private void goToParent() {
		final IEditorPart newEditor = HandlerHelper.openParentEditor(getModel().getFBNetworkElement());
		final GraphicalViewer viewer = newEditor.getAdapter(GraphicalViewer.class);
		HandlerHelper.selectElement(getModel(), viewer);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected int getMaxWidth() {
		// we always want the max width of the interface bar
		return getInterfaceBarMaxWidth();
	}
}
