/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.properties.GlobalConstantsPropertySheetPage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class GlobalConstantsEditor extends XtextEditor
		implements CommandStackEventListener, ITabbedPropertySheetPageContributor {

	private final CommandStack commandStack = new CommandStack();
	private final ActionRegistry actionRegistry = new ActionRegistry();

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		commandStack.addCommandStackEventListener(this);
	}

	@Override
	public void dispose() {
		actionRegistry.dispose();
		commandStack.removeCommandStackEventListener(this);
		super.dispose();
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		updateActions();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public boolean isDirty() {
		return super.isDirty() || commandStack.isDirty();
	}

	@Override
	public void doSave(final IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		commandStack.markSaveLocation();
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		super.doSetInput(input);
		commandStack.flush();
	}

	@Override
	public void doRevertToSaved() {
		super.doRevertToSaved();
		commandStack.flush();
	}

	@Override
	protected void createActions() {
		super.createActions();
		actionRegistry.registerAction(new UndoAction(this));
		actionRegistry.registerAction(new RedoAction(this));
	}

	private void updateActions() {
		actionRegistry.getActions().forEachRemaining(action -> {
			if (action instanceof final UpdateAction updateAction) {
				updateAction.update();
			}
		});
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
			return key.cast(new GlobalConstantsPropertySheetPage(this));
		}
		if (key == CommandStack.class) {
			return key.cast(getCommandStack());
		}
		if (key == ActionRegistry.class) {
			return key.cast(getActionRegistry());
		}
		return super.getAdapter(key);
	}

	protected final CommandStack getCommandStack() {
		return commandStack;
	}

	protected final ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	@Override
	public String getContributorId() {
		return getLanguageName();
	}
}
