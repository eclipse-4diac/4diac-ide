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
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.editors.NewInstanceCellEditor;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

public class NewInstanceDirectEditManager extends TextDirectEditManager {

	private static final Insets BORDER_INSETS = new Insets(0, 0, 0, 0);
	private static final AbstractBorder BORDER = new AbstractBorder() {

		@Override
		public Insets getInsets(final IFigure figure) {
			return BORDER_INSETS;
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
			// don't draw any border to make the direct editor smaller
		}
	};

	public static class NewInstanceCellEditorLocator implements CellEditorLocator {
		private Point refPoint = new Point(0, 0);

		@Override
		public void relocate(final CellEditor celleditor) {
			if (null != celleditor) {
				final Control control = celleditor.getControl();
				final Point pref = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				control.setBounds(refPoint.x - 1, refPoint.y - 1, pref.x * 2 + 1, pref.y + 1);
			}
		}

		public void setRefPoint(final Point refPoint) {
			this.refPoint = refPoint;
		}

		public Point getRefPoint() {
			return refPoint;
		}
	}

	private final TypeLibrary typeLib;
	private final boolean useChangeFBType;
	private String initialValue;

	public NewInstanceDirectEditManager(final GraphicalEditPart source, final TypeLibrary typeLib,
			final boolean useChangeFBType) {
		super(source, new NewInstanceCellEditorLocator());
		this.typeLib = typeLib;
		this.useChangeFBType = useChangeFBType;
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		return new NewInstanceCellEditor(composite);
	}

	@Override
	public void show() {
		initialValue = null;
		super.show();
	}

	public void show(final String initialValue) {
		this.initialValue = initialValue;
		super.show();
		if (initialValue != null) {
			final Text text = getCellEditor().getText();
			text.setSelection(text.getText().length());
			setDirty(true);
		}
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().getMenuButton().addListener(SWT.Selection, event -> showFBInsertPopUpMenu());
		getCellEditor().setTypeLibrary(typeLib);
		super.initCellEditor();
		if (null != initialValue) {
			getCellEditor().setValue(initialValue);
		}
	}

	@Override
	public NewInstanceCellEditorLocator getLocator() {
		return (NewInstanceCellEditorLocator) super.getLocator();
	}

	@Override
	protected NewInstanceCellEditor getCellEditor() {
		return (NewInstanceCellEditor) super.getCellEditor();
	}

	@Override
	protected DirectEditRequest createDirectEditRequest() {
		final DirectEditRequest directEditRequest = super.createDirectEditRequest();
		directEditRequest.setLocation(new org.eclipse.draw2d.geometry.Point(getLocator().getRefPoint()));
		return directEditRequest;
	}

	public void updateRefPosition(final Point refPoint) {
		getLocator().setRefPoint(refPoint);
	}

	@Override
	protected IFigure getCellEditorFrame() {
		final IFigure cellEditorFrame = super.getCellEditorFrame();
		cellEditorFrame.setBorder(BORDER);
		return cellEditorFrame;
	}

	private void showFBInsertPopUpMenu() {
		final EditPartViewer viewer = getEditPart().getViewer();
		final MenuManager mgr = new MenuManager();
		((FBNetworkContextMenuProvider) viewer.getContextMenu()).buildFBInsertMenu(mgr, getLocator().getRefPoint(),
				useChangeFBType);
		final Menu menu = mgr.createContextMenu(viewer.getControl());
		menu.setVisible(true);
		// put the menu on top of the editor
		menu.setLocation(viewer.getControl().toDisplay(getLocator().getRefPoint()));
		// get rid of the editor
		getCellEditor().fireCancelEditor();
	}
}
