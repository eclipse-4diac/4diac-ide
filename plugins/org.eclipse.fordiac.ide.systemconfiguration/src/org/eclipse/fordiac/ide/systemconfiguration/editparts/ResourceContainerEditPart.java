/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.policies.ResourceContainerLayoutEditPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class ResourceContainerEditPart extends AbstractGraphicalEditPart {

	private final Adapter contentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refreshChildren();
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().getDevice().eAdapters().add(contentAdapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().getDevice().eAdapters().remove(contentAdapter);
		}
	}

	@Override
	protected IFigure createFigure() {
		final Figure fig = new Figure();
		final ToolbarLayout containerFigureLayout = new ToolbarLayout();
		containerFigureLayout.setStretchMinorAxis(true);
		fig.setLayoutManager(containerFigureLayout);
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ResourceContainerLayoutEditPolicy());
	}

	@Override
	public ResourceContainer getModel() {
		return (ResourceContainer) super.getModel();
	}

	@Override
	protected List<Resource> getModelChildren() {
		return getModel().getDevice().getResource();
	}

}
