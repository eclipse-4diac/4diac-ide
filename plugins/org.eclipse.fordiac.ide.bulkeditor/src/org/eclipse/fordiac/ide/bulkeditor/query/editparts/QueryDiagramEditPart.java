/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import java.util.List;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryConnectionRouter;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryTreeLayout;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.SnapToGrid;

/** Edit part of the query viewer contents, showing the visible elements. */
public class QueryDiagramEditPart extends AbstractDiagramEditPart {

	private final Adapter contentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (!notification.isTouch()) {
				handleModelChange(notification);
			}
		}

		private void handleModelChange(final Notification notification) {
			if (notification.getFeature() instanceof final EReference reference && reference.isContainment()) {
				refreshChildren();
			}
			if (notification.getNotifier() instanceof final EObject element
					&& getViewer().getEditPartRegistry().get(getNodeElement(element)) instanceof final EditPart node) {
				node.refresh();
			}
		}

		private static EObject getNodeElement(final EObject element) {
			// field constraints are shown in the node of their constraint
			return QueryModelHelper.isOfType(element, QueryModelHelper.FIELD_CONSTRAINT) ? element.eContainer()
					: element;
		}
	};

	@Override
	public QueryDiagram getModel() {
		return (QueryDiagram) super.getModel();
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			if (getModel().getQueryRoot() != null) {
				getModel().getQueryRoot().eAdapters().add(contentAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			if (getModel().getQueryRoot() != null) {
				getModel().getQueryRoot().eAdapters().remove(contentAdapter);
			}
		}
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = super.createFigure();
		figure.setLayoutManager(new QueryTreeLayout());
		return figure;
	}

	@Override
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		return new QueryConnectionRouter();
	}

	@Override
	protected List<EObject> getModelChildren() {
		return getModel().getVisibleElements();
	}

	@Override
	protected void showGrid() {
		getViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, Boolean.FALSE);
	}
}
