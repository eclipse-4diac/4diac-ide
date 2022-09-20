/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugStackFrame;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugThread;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

public class FBDebugView extends ViewPart {

	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private static final int NUM_COLUMNS = 1;
	private static final String DEBUG_VIEW = "Debug";
	private FBType fbType;
	private ISelectionListener selectionListener;
	private KeyHandler sharedKeyHandler;

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(parent);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		fbType = AbstractInterpreterTest.loadFBType("E_CTU");
		createGraphicalViewer(composite);
		setTitleImage(FordiacImage.ICON_BASIC_FB.getImage());
	}

	private void createGraphicalViewer(final Composite parent) {
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		configureGraphicalViewer();
		initializeGraphicalViewer();
		hookGraphicalViewer();
	}


	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		final ScalableFreeformRootEditPart root = createRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		viewer.setContextMenu(new FordiacContextMenuProvider(viewer, root.getZoomManager(), getActionRegistry()));
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		final KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);

	}

	private void initializeGraphicalViewer() {
		// Placing a listener and setting the appropriate content
		displaySelectedType();
	}

	private void displaySelectedType() {
		selectionListener = (part, sel) -> {
			if (part.getTitle().equals(DEBUG_VIEW) && !sel.isEmpty() && sel instanceof TreeSelection) {
				final TreeSelection ts = (TreeSelection) sel;
				try {
					checkSelectionType(ts.getPaths());
				} catch (final DebugException e) {
					e.printStackTrace();
				}
			}
		};
		getSite().getPage().addSelectionListener(selectionListener);
	}

	private void checkSelectionType(final TreePath[] paths) throws DebugException {
		String[] fbNameAndExtension = new String[0];
		if (paths[0].getLastSegment() instanceof EvaluatorDebugTarget) {

			final EvaluatorDebugTarget debugTarget = (EvaluatorDebugTarget) paths[0].getLastSegment();
			try {
				fbNameAndExtension = debugTarget.getName().split("\\.");
				fbType = AbstractInterpreterTest.loadFBType(fbNameAndExtension[0]);
			} catch (final DebugException e) {
				e.printStackTrace();
			}
			viewer.setContents(fbType);
		} else if (paths[0].getLastSegment() instanceof EvaluatorDebugThread) {
			final EvaluatorDebugThread debugThread = (EvaluatorDebugThread) paths[0].getLastSegment();
			if (debugThread.getTopStackFrame().getEvaluator().getContext().getType() instanceof BasicFBType) {
				// Get the type's name:
				fbType = AbstractInterpreterTest.loadFBType(
						((BasicFBType) debugThread.getTopStackFrame().getEvaluator().getContext().getType()).getName());
				viewer.setContents(fbType);
			}
		} else if (paths[0].getLastSegment() instanceof EvaluatorDebugStackFrame) {
			final EvaluatorDebugStackFrame debugStackFrame = (EvaluatorDebugStackFrame) paths[0].getLastSegment();
			debugStackFrame.getEvaluator().getSourceElement();

			if (debugStackFrame.getEvaluator().getContext().getType() instanceof BasicFBType) {
				// Get the type's name:
				fbType = AbstractInterpreterTest
						.loadFBType(((BasicFBType) debugStackFrame.getEvaluator().getContext().getType()).getName());
				viewer.setContents(fbType);
			}

		}
	}

	private FBType loadFBType(final String selectedFbName) {

		// (FBType) TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource).getType();

		final List<AutomationSystem> searchRootSystems = new ArrayList<>();

		Display.getDefault().syncExec(() -> {
			final IEditorPart openEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			final IProject project = openEditor.getAdapter(FBNetwork.class).getAutomationSystem().getTypeLibrary()
					.getProject();
			searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(project));
		});

		TypeEntry te = fbType.getTypeEntry();
		for (final AutomationSystem sys : searchRootSystems) {
			te = searchTypeLibrary(sys, selectedFbName);
		}

		if (te.getType() instanceof FBType) {
			return (FBType) te.getType();
		}
		// TODO: think of adapters and don't return null
		return null;

	}

	private static TypeEntry searchTypeLibrary(final AutomationSystem sys, final String selectedFbName) {
		final TypeLibrary lib = sys.getTypeLibrary();
		for (final TypeEntry entry : lib.getFbTypes().values()) {
			if (entry.getTypeName().equals(selectedFbName)) {
				return entry;
			}
		}
		for (final TypeEntry entry : lib.getAdapterTypes().values()) {
			if (entry.getTypeName().equals(selectedFbName)) {
				return entry;
			}
		}
		return null;

	}

	private void hookGraphicalViewer() {
		getSite().setSelectionProvider(viewer);
	}

	private static ScalableFreeformRootEditPart createRootEditPart() {
		return new ScalableFreeformRootEditPart() {

			@Override
			protected ScalableFreeformLayeredPane createScaledLayers() {
				final ScalableFreeformLayeredPane layers = new ScalableFreeformLayeredPane();
				// Removed the grid
				layers.add(getPrintableLayers(), PRINTABLE_LAYERS);
				// TODO: layers.add(new FeedbackLayer(), SCALED_FEEDBACK_LAYER);
				return layers;
			}

			@Override
			protected void refreshGridLayer() {
				// No grid
			}

			// This is to overwrite the comments + add buttons to the viewer
			@Override
			protected List getModelChildren() {
				getModelChildren();
				return super.getModelChildren();
			}

			@Override
			protected void refreshChildren() {
				super.refreshChildren();
			}

		};
	}

	private FBInterfaceEditPartFactory getEditpartFactory() {
		final TypeLibrary typeLib = fbType.getTypeLibrary();
		return new FBInterfaceEditPartFactory(null, typeLib);
	}

	private ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(selectionListener);
	}

	private KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
					KeyStroke.getPressed('+', 0x3d, SWT.CTRL),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	@Override
	public void setFocus() {
		// Nothing for now
	}

}
