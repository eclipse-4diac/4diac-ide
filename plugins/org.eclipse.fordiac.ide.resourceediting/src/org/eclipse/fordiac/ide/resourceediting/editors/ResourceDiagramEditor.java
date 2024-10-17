/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editparts.ResourceDiagramEditPartFactory;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;

/**
 * The main editor for ResourceDiagramEditors (mapping and resource editing).
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditor extends FBNetworkEditor {

	private final Adapter resourceAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if ((LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature))
					&& (getResource().equals(notification.getNotifier()))) {
				updateEditorTitle(ResourceEditorInput.getResourceEditorName(getResource()));
			}
			super.notifyChanged(notification);
		}

	};

	private final Adapter colorChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()) {
				updateGridColor();
			}
		}
	};

	private Resource getResource() {
		return (Resource) getModel().eContainer();
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		updateGridColor();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ResourceDiagramEditPartFactory(this);
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new FBNetworkContextMenuProvider(this, getActionRegistry(), zoomManager, getTypeLibrary());
	}

	@Override
	public void setInput(final IEditorInput input) {
		if (input instanceof final ResourceEditorInput resInput) {
			final Resource res = resInput.getContent();
			setModel(res.getFBNetwork());
			getResource().eAdapters().add(resourceAdapter);
			getResource().getDevice().eAdapters().add(colorChangeListener);
		}
		super.setInput(input);
	}

	@Override
	public void dispose() {
		if (null != getResource()) {
			getResource().eAdapters().remove(resourceAdapter);
			getResource().getDevice().eAdapters().remove(colorChangeListener);
		}

		super.dispose();
	}

	private void updateGridColor() {
		if (null != getResource()) {
			final IFigure layer = ((ZoomScalableFreeformRootEditPart) getViewer().getRootEditPart())
					.getLayer(LayerConstants.GRID_LAYER);
			if (null != layer) {
				final Color devColor = getResource().getDevice().getColor();
				if (null != devColor) {
					final org.eclipse.swt.graphics.Color newColor = ColorManager
							.getColor(new RGB(devColor.getRed(), devColor.getGreen(), devColor.getBlue()));
					layer.setForegroundColor(newColor);
				}
			}
		}
	}
}
