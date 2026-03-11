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
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.resourceediting.editparts.ResourceDiagramEditPartFactory;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.LayerConstants;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;

/**
 * The main editor for ResourceDiagramEditors (mapping and resource editing).
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditor extends FBNetworkEditor {

	private final Adapter colorChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()) {
				updateGridColor();
			}
		}
	};

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == Resource.class) {
			return adapter.cast(getResource());
		}
		return super.getAdapter(adapter);
	}

	private Resource getResource() {
		return getModel() != null ? (Resource) getModel().eContainer() : null;
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
	public void setInput(final IEditorInput input) {
		final Resource resource = LibraryElementProvider.INSTANCE.getElement(input, Resource.class);
		if (resource == null) {
			throw new IllegalArgumentException("Resource editors only accept resources as valid inputs!"); //$NON-NLS-1$
		}
		removeColorChangeListener();
		setModel(resource.getFBNetwork());
		addColorChangeListener();
		super.setInput(input);
	}

	@Override
	public void dispose() {
		removeColorChangeListener();
		super.dispose();
	}

	protected void addColorChangeListener() {
		if (null != getResource()) {
			getResource().getDevice().eAdapters().add(colorChangeListener);
		}
	}

	protected void removeColorChangeListener() {
		if (null != getResource()) {
			getResource().getDevice().eAdapters().remove(colorChangeListener);
		}
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
