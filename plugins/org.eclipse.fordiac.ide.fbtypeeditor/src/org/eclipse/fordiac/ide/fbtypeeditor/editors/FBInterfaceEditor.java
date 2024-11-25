/*******************************************************************************
 * Copyright (c) 2011 - 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				             Johannes Kepler University Linz
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
 *   Alois Zoitl - inherited FBInterface editor from the common diagram editor to
 *   				to reduce code duplication and more common look and feel
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.FBInterfacePaletteFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.typeeditor.TypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class FBInterfaceEditor extends DiagramEditorWithFlyoutPalette implements IFBTEditorPart {

	private CommandStack commandStack;

	private PaletteRoot paletteRoot;
	private TypeLibrary typeLib;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		if (input instanceof final TypeEditorInput untypedInput) {
			typeLib = untypedInput.getTypeEntry().getTypeLibrary();
		}
		super.init(site, input);
		setPartName(FordiacMessages.Interface);
		setTitleImage(FordiacImage.ICON_INTERFACE_EDITOR.getImage());
	}

	@Override
	protected DefaultEditDomain createEditDomain() {
		return new FBTypeEditDomain(this, commandStack);
	}

	@Override
	protected void createActions() {
		final ActionRegistry registry = getActionRegistry();
		InterfaceContextMenuProvider.createInterfaceEditingActions(this, registry, getModel());
		super.createActions();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new FBInterfaceEditPartFactory(this, typeLib);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		if (null == paletteRoot) {
			paletteRoot = FBInterfacePaletteFactory.createPalette(typeLib, !(getType() instanceof AdapterType));
		}
		return paletteRoot;
	}

	@Override
	public <T> T getAdapter(final Class<T> type) {
		if (type == IContentOutlinePage.class) {
			return null; // use outline page from FBTypeEditor
		}
		if (type == InterfaceList.class) {
			return type.cast(getModel().getInterfaceList());
		}
		if (type == SubAppType.class) {
			return getType() instanceof final SubAppType subApp ? type.cast(subApp) : null;
		}
		return super.getAdapter(type);
	}

	protected TypeLibrary getTypeLib() {
		return typeLib;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// currently nothing needs to be done here
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		final EditPart ep = getGraphicalViewer().getEditPartForModel(selectedElement);
		getGraphicalViewer().flush();
		if (ep != null && ep.isSelectable()) {
			getGraphicalViewer().select(ep);
			return true;
		}
		return (selectedElement instanceof InterfaceList);
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return FBInterfacePaletteFactory.PALETTE_PREFERENCES;
	}

	/**
	 * Override so that we can add a template transferdragsourcelistener for drag
	 * and drop
	 */
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(final PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	@Override
	public FBType getModel() {
		return getType();
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new InterfaceContextMenuProvider(viewer, zoomManager, getActionRegistry(), typeLib.getDataTypeLibrary());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		// we don't need an additional transferdroptarget listener
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		return null; // this is currently needed as the base class is targeted for system editors
	}

	@Override
	public void doSaveAs() {
		// nothing to do here
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// For now we don't handle markers in this editor
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// For now we don't handle markers in this editor
		return false;
	}

	@Override
	public void reloadType() {
		getGraphicalViewer().setContents(getType());
	}

	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			protected AbstractFreeformFigure createDrawingAreaContainer() {
				return new MinSpaceFreeformFigure();
			}

			@Override
			protected IFigure createFigure() {
				final IFigure rootFigure = super.createFigure();
				final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
				if (grid != null) {
					// it does not make sense to have a grid in the interface layer so hide it
					grid.setVisible(false);
				}
				return rootFigure;
			}

			@Override
			protected void refreshGridLayer() {
				// empty to be sure that grid will not be drawn
			}
		};
	}

	@Override
	protected void performInitialsationScroll(final AdvancedScrollingGraphicalViewer viewer) {
		// In order that the interface editor can get the size of the canvas we need to
		// run the initial scroll asynchronously so that SWT will layout the editor
		// first.
		Display.getDefault().asyncExec(() -> super.performInitialsationScroll(viewer));
	}

	@Override
	protected Point getInitialScrollPos(final GraphicalEditPart rootEditPart) {
		final Point figureCanvasSize = getViewer().getFigureCanvasSize();
		final Rectangle drawingAreaBounds = rootEditPart.getContentPane().getBounds();
		final org.eclipse.draw2d.geometry.Point center = drawingAreaBounds.getCenter();
		return new Point(center.x - figureCanvasSize.x / 2, center.y - figureCanvasSize.y / 2);
	}

	@Override
	public Object getSelectableObject() {
		if (getGraphicalViewer() == null) {
			return null;
		}
		return getGraphicalViewer().getEditPartForModel(getType());
	}

}
