/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
 *   Alois Zoitl - added zoom handling as explained in GEF logic editor example
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.draw2d.zoom.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.CellEditorActionHandler;

/**
 * A direct edit manager for text fields.
 *
 * This direct edit manager helps to setup the actions making the a direct edit
 * text field more usable.
 *
 * This class is a potential target for upstreaming to GEF
 */
public abstract class TextDirectEditManager extends DirectEditManager {

	private IActionBars actionBars;
	private CellEditorActionHandler actionHandler;
	private IAction copy;
	private IAction cut;
	private IAction paste;
	private IAction undo;
	private IAction redo;
	private IAction find;
	private IAction selectAll;
	private IAction delete;
	// we need to cash the zoom manager as some direct edit commands change the editpart and we loose connection to the
	// viewer
	private ZoomManager zoomMananger = null;

	private Font scaledFont;
	private final ZoomListener zoomListener = this::updateScaledFont;

	protected TextDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator) {
		super(source, null, locator);
	}

	protected TextDirectEditManager(final GraphicalEditPart source, final CellEditorLocator locator,
			final Object feature) {
		super(source, null, locator, feature);
	}

	@Override
	protected void initCellEditor() {
		setupActions();
		setupZoomHandling();
	}

	@Override
	protected void bringDown() {
		cleanUpActions();
		super.bringDown();
		disposeScaledFont();
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		return new TextCellEditor(composite);
	}

	@Override
	protected void unhookListeners() {
		super.unhookListeners();
		final ZoomManager zoomManager = getZoomManager();
		if (zoomManager != null) {
			zoomManager.removeZoomListener(zoomListener);
		}
	}

	// Hook the cell editor's copy/paste actions to the actionBars so that
	// they can be invoked via keyboard shortcuts.
	private void setupActions() {
		actionBars = EditorUtils.getCurrentActiveEditor().getEditorSite().getActionBars();
		saveCurrentActions(actionBars);
		actionHandler = new CellEditorActionHandler(actionBars);
		actionHandler.addCellEditor(getCellEditor());
		actionBars.updateActionBars();
	}

	private void setupZoomHandling() {
		final ZoomManager zoomManager = getZoomManager();
		if (zoomManager != null) {
			updateScaledFont(zoomManager.getZoom());
			zoomManager.addZoomListener(zoomListener);
		} else {
			getCellEditor().getControl().setFont(getEditPart().getFigure().getFont());
		}
	}

	private ZoomManager getZoomManager() {
		if (zoomMananger == null) {
			final EditPartViewer viewer = getEditPart().getViewer();
			if (viewer != null) {
				zoomMananger = (ZoomManager) viewer.getProperty(ZoomManager.class.toString());
			}
		}
		return zoomMananger;
	}

	private void cleanUpActions() {
		if (actionHandler != null) {
			actionHandler.dispose();
			actionHandler = null;
		}
		if (actionBars != null) {
			restoreSavedActions(actionBars);
			actionBars.updateActionBars();
			actionBars = null;
		}
	}

	private void restoreSavedActions(final IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), paste);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), delete);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAll);
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), cut);
		actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), find);
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
	}

	private void saveCurrentActions(final IActionBars actionBars) {
		copy = actionBars.getGlobalActionHandler(ActionFactory.COPY.getId());
		paste = actionBars.getGlobalActionHandler(ActionFactory.PASTE.getId());
		delete = actionBars.getGlobalActionHandler(ActionFactory.DELETE.getId());
		selectAll = actionBars.getGlobalActionHandler(ActionFactory.SELECT_ALL.getId());
		cut = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
		find = actionBars.getGlobalActionHandler(ActionFactory.FIND.getId());
		undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO.getId());
		redo = actionBars.getGlobalActionHandler(ActionFactory.REDO.getId());
	}

	private void updateScaledFont(final double zoom) {
		final Control control = getCellEditor().getControl();
		final Font font = getEditPart().getFigure().getFont();

		disposeScaledFont();
		if (0 == Double.compare(zoom, 1.0)) {
			control.setFont(font);
		} else {
			final FontData fd = font.getFontData()[0];
			fd.setHeight((int) (fd.getHeight() * zoom));
			scaledFont = new Font(null, fd);
			control.setFont(scaledFont);
		}
	}

	private void disposeScaledFont() {
		if (scaledFont != null) {
			scaledFont.dispose();
			scaledFont = null;
		}
	}

}