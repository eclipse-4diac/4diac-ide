/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.FBInterfacePaletteFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.ruler.FordiacRulerComposite;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class FBInterfaceEditor extends GraphicalEditorWithFlyoutPalette implements IFBTEditorPart{

	private RulerComposite rulerComp;
	private CommandStack commandStack;
	private FBType fbType;
	private KeyHandler sharedKeyHandler;
	protected PaletteRoot paletteRoot;
	protected Palette palette;

	public FBInterfaceEditor() {}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			fbType = untypedInput.getContent();
			EObject group = untypedInput.getPaletteEntry().getGroup();
			while (group.eContainer() != null) {
				group = group.eContainer();
			}
			if (group instanceof Palette) {
				palette = (Palette) group;
			}
			if (null == palette) {
				palette = TypeLibrary.getInstance().getPalette();
			}
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName("Interface");
		setTitleImage(FordiacImage.ICON_InterfaceEditor.getImage());
		super.init(site, input);
	}

	@Override
	protected void createGraphicalViewer(final Composite parent) {
		rulerComp = new FordiacRulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	@Override
	protected Control getGraphicalControl() {
		return rulerComp;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();

		ScalableFreeformRootEditPart root = new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry());
		
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditPartFactory());

		viewer.setContextMenu(new InterfaceContextMenuProvider(
				viewer, root.getZoomManager(), getActionRegistry()));
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

		KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer)
				.setParent(getCommonKeyHandler());

		viewer.setKeyHandler(viewerKeyHandler);
	}
	
	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();	
		InterfaceContextMenuProvider.createInterfaceEditingActions(this, registry, fbType);
		super.createActions();
	}

	protected EditPartFactory getEditPartFactory() {
		return new FBInterfaceEditPartFactory(this, palette, getZoomManger());
	}
	
	protected ZoomManager getZoomManger(){		
		return ((ScalableFreeformRootEditPart)(getGraphicalViewer().getRootEditPart())).getZoomManager();
	}

	@Override
	public void createPartControl(final Composite parent) {
		Composite graphicaEditor = new Composite(parent, SWT.NONE);
		graphicaEditor.setLayout(new FillLayout());
		super.createPartControl(graphicaEditor);
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		// enable drag from palette
		viewer.addDropTargetListener(new TemplateTransferDropTargetListener(viewer));
		viewer.setContents(fbType);
	}

	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler
					.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
							getActionRegistry().getAction(
									ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(
					KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(
							GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
			KeyStroke.getPressed('+', 0x3d, SWT.CTRL), getActionRegistry()
					.getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		paletteRoot = FBInterfacePaletteFactory.createPalette(palette);
		return paletteRoot;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		//currently nothing needs to be done here
	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {	
		Object editpart = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
		getGraphicalViewer().flush();
		if (editpart != null && editpart instanceof EditPart && ((EditPart) editpart).isSelectable()) {
			getGraphicalViewer().select((EditPart) editpart);
			return true;
		}
		if (selectedElement instanceof InterfaceList) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return FBInterfacePaletteFactory.createPalettePreferences();
	}
	
	/** Override so that we can add a template transferdragsourcelistener for drag and drop
	 */
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(final PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
						viewer));
			}
		};
	}

}
