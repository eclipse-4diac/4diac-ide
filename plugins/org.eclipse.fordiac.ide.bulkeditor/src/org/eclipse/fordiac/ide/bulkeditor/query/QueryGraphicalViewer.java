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
package org.eclipse.fordiac.ide.bulkeditor.query;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.zoom.MouseLocationZoomScrollPolicy;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.bulkeditor.query.editparts.QueryDiagram;
import org.eclipse.fordiac.ide.bulkeditor.query.editparts.QueryEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.handlers.AdvancedGraphicalViewerKeyHandler;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstantsCache;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

/** Graphical viewer showing a query as tree of nodes. */
public final class QueryGraphicalViewer extends AdvancedScrollingGraphicalViewer {

	private final ActionRegistry actionRegistry;
	private QueryDiagram diagram;

	public QueryGraphicalViewer(final Composite parent, final IEditorPart editor, final IProject project) {
		super(new GefPreferenceConstantsCache(project));
		actionRegistry = editor.getAdapter(ActionRegistry.class);
		createControl(parent);

		final ScalableFreeformRootEditPart root = new ZoomScalableFreeformRootEditPart(editor.getSite(),
				actionRegistry);
		root.getZoomManager().setScrollPolicy(new MouseLocationZoomScrollPolicy(getControl()));
		setRootEditPart(root);
		setEditPartFactory(new QueryEditPartFactory(project));
		setEditDomain(createEditDomain(editor));
		setKeyHandler(createKeyHandler(editor));
		setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
	}

	/** Shows the query, keeping the collapsed nodes if it is already shown. */
	public void setQuery(final Resource resource) {
		final EObject queryRoot = resource.getContents().isEmpty() ? null : resource.getContents().get(0);
		if (diagram == null || diagram.getQueryRoot() != queryRoot) {
			diagram = new QueryDiagram(queryRoot);
			setContents(diagram);
		}
	}

	public void createContextMenu(final EPackage queryPackage, final Runnable onSave, final Runnable onLoad,
			final Runnable onSearch) {
		final ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) getRootEditPart();
		setContextMenu(new QueryContextMenuProvider(this, root.getZoomManager(), actionRegistry, queryPackage, onSave,
				onLoad, onSearch));
	}

	private KeyHandler createKeyHandler(final IEditorPart editor) {
		final DeleteAction deleteAction = new DeleteAction((IWorkbenchPart) editor);
		deleteAction.setSelectionProvider(this);
		addSelectionChangedListener(_ -> deleteAction.update());
		actionRegistry.registerAction(deleteAction);

		final KeyHandler keyHandler = new AdvancedGraphicalViewerKeyHandler(this);
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), deleteAction);
		return keyHandler;
	}

	private static DefaultEditDomain createEditDomain(final IEditorPart editor) {
		final DefaultEditDomain editDomain = new DefaultEditDomain(editor);
		// query changes are undone together with the other bulk editor changes
		editDomain.setCommandStack(editor.getAdapter(CommandStack.class));
		editDomain.setDefaultTool(new AdvancedPanningSelectionTool());
		editDomain.setActiveTool(editDomain.getDefaultTool());
		return editDomain;
	}
}
