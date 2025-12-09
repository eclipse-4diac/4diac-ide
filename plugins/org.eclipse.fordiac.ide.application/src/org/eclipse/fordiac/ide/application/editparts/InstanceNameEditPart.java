/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IEditorPart;

public class InstanceNameEditPart extends AbstractGraphicalEditPart
		implements NodeEditPart, AnnotableGraphicalEditPart {

	private DiagramFontChangeListener fontChangeListener;

	@Override
	public void activate() {
		super.activate();
		getModel().getRefElement().eAdapters().add(contentAdapter);
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getRefElement().eAdapters().remove(contentAdapter);
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	private final Adapter contentAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
				refreshValue();
			}
		}
	};

	void refreshValue() {
		getFigure().setText(getModel().getInstanceName());
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel().getRefElement(), event,
				FordiacAnnotationUtil::showOnTargetName, FordiacAnnotationUtil::showOnTargetPosition);
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
	}

	@Override
	public InstanceNameFigure getFigure() {
		return (InstanceNameFigure) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		// FBNetwork elements need a special rename command therefore we remove the
		// standard edit policy and add a adjusted one
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof final InstanceNameEditPart instanceNameEditPart) {
					return ChangeNameCommand.forName(instanceNameEditPart.getModel().getRefElement(),
							(String) request.getCellEditor().getValue());
				}
				return null;
			}
		});
	}

	@Override
	public InstanceName getModel() {
		return (InstanceName) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final InstanceNameFigure figure = new InstanceNameFigure();
		if (isResoruceTypeFBNElement()) {
			figure.setIcon(FordiacImage.ICON_LOCKED_STATE.getImage());
		}
		return figure;
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		final IEditorPart editor = EditorUtils.getCurrentActiveEditor();

		if (!getModel().getRefElement().isContainedInTypedInstance() && editor.getAdapter(FBNetwork.class) != null
				&& (request.getType() == RequestConstants.REQ_DIRECT_EDIT
						|| request.getType() == RequestConstants.REQ_OPEN)) {
			performDirectEdit();

		} else {
			super.performRequest(request);
		}
	}

	private DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getFigure());
	}

	private void performDirectEdit() {
		createDirectEditManager().show();
	}

	private boolean isResoruceTypeFBNElement() {
		return getModel().getRefElement() instanceof final FB fb && fb.isResourceTypeFB();
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
