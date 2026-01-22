/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Muttenthaler Benjamin
 *     - fixed reload of view if file on file system did change
 *     - use new saveType method of AbstractTypeExporter
 *     - replaced DataTypeListener by AdapterImpl
 *     - keep a copy of the datatype object in the view, otherwise the content of the file is changed even the save button was not pressed
 *   Lukas Wais - enabled Save As
 *   Sebastian Hollersbacher
 *     - Changed from DataTypeEditor to AttributeTypeEditor
 *   Alois Zoitl - extracted from AttributeTypeEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

public abstract class AbstractTypeEditorPage extends EditorPart implements ITypeEditorPage, CommandStackEventListener {

	private CommandStack commandStack;
	private ActionRegistry actionRegistry;
	private final List<String> selectionActions = new ArrayList<>();
	private final List<String> stackActions = new ArrayList<>();
	private final List<String> propertyActions = new ArrayList<>();

	private void createActions() {
		final ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());
	}

	@Override
	public void dispose() {
		getCommandStack().removeCommandStackEventListener(this);
		removeSelectionListener();
		getActionRegistry().dispose();
		super.dispose();
	}

	private void removeSelectionListener() {
		final IWorkbenchPartSite site = getSite();
		if (site == null) {
			return;
		}
		final IWorkbenchWindow workbenchWindow = site.getWorkbenchWindow();
		if (workbenchWindow == null) {
			return;
		}
		final ISelectionService selectionService = workbenchWindow.getSelectionService();
		if (selectionService == null) {
			return;
		}
		selectionService.removeSelectionListener(this);
	}

	private ActionRegistry getActionRegistry() {
		if (null == actionRegistry) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		// adapting to the command stack is needed for the undo/redo actions
		if (key == CommandStack.class) {
			return key.cast(getCommandStack());
		}
		if (key == ActionRegistry.class) {
			return key.cast(getActionRegistry());
		}
		return super.getAdapter(key);
	}

	protected CommandStack getCommandStack() {
		return commandStack;
	}

	private List<String> getStackActions() {
		return stackActions;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		setupCommandStack();
		initializeActionRegistry();
		setActionHandlers(site);
	}

	private void initializeActionRegistry() {
		createActions();
		updateActions(propertyActions);
		updateActions(stackActions);
	}

	@Override
	public boolean isDirty() {
		return LibraryElementProvider.INSTANCE.canSaveLibraryElement(getEditorInput());
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (!(getSite() instanceof final MultiPageEditorSite multiPageEditorSite)) {
			return;
		}
		final IWorkbenchPage page = multiPageEditorSite.getPage();
		if (page == null) {
			removeSelectionListener();
			return;
		}

		if (multiPageEditorSite.getMultiPageEditor().equals(page.getActiveEditor())) {
			updateActions(selectionActions);
		}

	}

	private void setActionHandlers(final IEditorSite site) {
		final ActionRegistry registry = getActionRegistry();
		final IActionBars bars = site.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		bars.updateActionBars();
	}

	@Override
	public void setInput(final IEditorInput input) {
		super.setInput(input);
	}

	private void setupCommandStack() {
		if ((getSite() instanceof final MultiPageEditorSite multiPageEditorSite)) {
			commandStack = multiPageEditorSite.getMultiPageEditor().getAdapter(CommandStack.class);
			commandStack.addCommandStackEventListener(this);
		}
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		if (event.isPostChangeEvent()) {
			updateActions(stackActions);
		}
	}

	private void updateActions(final List<String> actionIds) {
		final ActionRegistry registry = getActionRegistry();
		actionIds.forEach(id -> {
			final IAction action = registry.getAction(id);
			if (action instanceof final UpdateAction updateAction) {
				updateAction.update();
			}
		});
	}

}