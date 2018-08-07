/*******************************************************************************
 * Copyright (c) 2008 - 2014 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Shows a Thumbnail of the content in the outline.
 */
class DiagramOutlinePage extends org.eclipse.ui.part.Page implements
		IContentOutlinePage, IAdaptable {
	// Shows a "Thumbnail" of the current "drawing"
	/** The page book. */
	private PageBook pageBook;

	/** The overview. */
	private Canvas overview;

	/** The thumbnail. */
	private Thumbnail thumbnail;

	/** The dispose listener. */
	private DisposeListener disposeListener;
	
	private GraphicalViewer graphicalViewer;

	public DiagramOutlinePage(GraphicalViewer graphicalViewer) {
		super();
		this.graphicalViewer = graphicalViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite
	 * )
	 */
	@Override
	public void createControl(final Composite parent) {
		pageBook = new PageBook(parent, SWT.NONE);
		overview = new Canvas(pageBook, SWT.NONE);

		if (thumbnail == null) {
			initializeOverview();
		}

		pageBook.showPage(overview);
		thumbnail.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#dispose()
	 */
	@Override
	public void dispose() {
		if (thumbnail != null) {
			thumbnail.deactivate();
			thumbnail = null;
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#getControl()
	 */
	@Override
	public Control getControl() {
		return pageBook;
	}

	/**
	 * Initialize overview.
	 */
	protected void initializeOverview() {
		LightweightSystem lws = new LightweightSystem(overview);
		RootEditPart rep = getGraphicalViewer().getRootEditPart();
		if (rep instanceof ScalableFreeformRootEditPart) {
			ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
			thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
			thumbnail.setBorder(new MarginBorder(3));
			thumbnail.setSource(root
					.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(final DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getEditor().addDisposeListener(disposeListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class type) {
		if (type == ZoomManager.class) {
			return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.Page#setFocus()
	 */
	@Override
	public void setFocus() {
		if (getControl() != null) {
			getControl().setFocus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addSelectionChangedListener(
			final ISelectionChangedListener listener) {
		// not used

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		// not used
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.viewers.ISelectionProvider#
	 * removeSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		// not used

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	@Override
	public void setSelection(final ISelection selection) {
		// not used

	}
	
	protected GraphicalViewer getGraphicalViewer() {
		return graphicalViewer;
	}
	
	protected FigureCanvas getEditor() {
		return (FigureCanvas) getGraphicalViewer().getControl();
	}

}
