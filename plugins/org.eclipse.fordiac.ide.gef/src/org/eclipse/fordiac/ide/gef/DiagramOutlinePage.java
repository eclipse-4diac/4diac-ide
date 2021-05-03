/*******************************************************************************
 * Copyright (c) 2008 - 2014 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 				 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Reworked so that multipage editors can updated the outline
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Shows a Thumbnail of the content in the outline.
 */
public class DiagramOutlinePage extends org.eclipse.ui.part.Page implements IContentOutlinePage, IAdaptable {
	private static final int THUMBNAIL_BORDER_MARGIN = 3;

	// Shows a "Thumbnail" of the current "drawing"
	/** The page book. */
	private PageBook pageBook;

	/** The overview. */
	private Canvas overview;

	private LightweightSystem lws;

	/** The thumbnail. */
	private Thumbnail thumbnail;

	private DisposeListener disposeListener = ev -> removeThumbnail();

	private GraphicalViewer graphicalViewer;

	/***
	 *
	 * @param graphicalViewer the viewer for which the overview should be shown, may
	 *                        be null
	 */
	public DiagramOutlinePage(GraphicalViewer graphicalViewer) {
		super();
		this.graphicalViewer = graphicalViewer;
	}

	@Override
	public void createControl(final Composite parent) {
		pageBook = new PageBook(parent, SWT.NONE);
		overview = new Canvas(pageBook, SWT.NONE);
		lws = new LightweightSystem(overview);
		pageBook.showPage(overview);
		if (null == thumbnail) {
			GraphicalViewer viewer = getGraphicalViewer();
			graphicalViewer = null; // to avoid any issues with unhooking listeners
			viewerChanged(viewer);
		}
	}

	@Override
	public void dispose() {
		removeThumbnail();
		removeDisposeListener();
		super.dispose();
	}

	@Override
	public Control getControl() {
		return pageBook;
	}

	private void removeThumbnail() {
		if (null != thumbnail) {
			thumbnail.deactivate();
			thumbnail = null;
		}
	}

	private void removeDisposeListener() {
		if (null != graphicalViewer) {
			getViewerControl().removeDisposeListener(disposeListener);
		}
	}

	/**
	 * allows to change the content of the outline for a given editor (e.g.,
	 * multipage editor)
	 *
	 * @param graphicalViewer the new graphical viewer for which content should be
	 *                        shown in the outline
	 */
	public void viewerChanged(GraphicalViewer graphicalViewer) {
		removeThumbnail();
		removeDisposeListener();
		this.graphicalViewer = graphicalViewer;
		thumbnail = createNewThumbnail();
		if (null != thumbnail) {
			thumbnail.setVisible(true);
		}
		if (!overview.isDisposed()) {
			overview.redraw();
		}
	}

	private Thumbnail createNewThumbnail() {
		if (null != graphicalViewer) {
			RootEditPart rep = graphicalViewer.getRootEditPart();
			if (rep instanceof ScalableFreeformRootEditPart) {
				ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
				FreeformViewport viewport = (FreeformViewport) root.getFigure();
				Thumbnail newThumbnail = new ScrollableThumbnail(viewport);
				newThumbnail.setBorder(new MarginBorder(THUMBNAIL_BORDER_MARGIN));
				newThumbnail.setSource(viewport.getContents());
				lws.setContents(newThumbnail);
				getViewerControl().addDisposeListener(e -> removeThumbnail());
				return newThumbnail;
			}
		}
		return null;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if ((adapter == ZoomManager.class) && (null != getGraphicalViewer())) {
			return adapter.cast(getGraphicalViewer().getProperty(ZoomManager.class.toString()));
		}
		return null;
	}

	@Override
	public void setFocus() {
		if (getControl() != null) {
			getControl().setFocus();
		}
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// not used

	}

	@Override
	public ISelection getSelection() {
		// not used
		return null;
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// not used

	}

	@Override
	public void setSelection(final ISelection selection) {
		// not used

	}

	protected GraphicalViewer getGraphicalViewer() {
		return graphicalViewer;
	}

	protected FigureCanvas getViewerControl() {
		return (FigureCanvas) getGraphicalViewer().getControl();
	}

}
