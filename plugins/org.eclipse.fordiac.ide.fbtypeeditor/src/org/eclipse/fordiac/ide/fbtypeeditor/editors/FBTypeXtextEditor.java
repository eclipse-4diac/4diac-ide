/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from StructuredTextFBTypeEditor, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

public abstract class FBTypeXtextEditor extends XtextEditor implements IFBTEditorPart {

	@Inject
	private AbstractUIPlugin languageUIPlugin;

	private boolean restoringSelection;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		installFBTypeUpdater();
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		removeFBTypeUpdater();
		super.doSetInput(input);
	}

	@Override
	public void dispose() {
		removeFBTypeUpdater();
		super.dispose();
	}

	@Override
	public void reveal(final int offset, final int length) {
		revealEditor();
		super.reveal(offset, length);
	}

	@Override
	protected void selectAndReveal(final int selectionStart, final int selectionLength, final int revealStart,
			final int revealLength) {
		// do not reveal editor when restoring a selection to avoid unintended switch of
		// the editor tab
		if (!restoringSelection) {
			revealEditor();
		}
		super.selectAndReveal(selectionStart, selectionLength, revealStart, revealLength);
	}

	protected void revealEditor() {
		if (getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			final MultiPageEditorPart multiPageEditor = multiPageEditorSite.getMultiPageEditor();
			if (multiPageEditor.getSelectedPage() != this) {
				multiPageEditor.setActiveEditor(this);
			}
		}
	}

	@Override
	protected void restoreSelection() {
		restoringSelection = true;
		try {
			super.restoreSelection();
		} finally {
			restoringSelection = false;
		}
	}

	public CommandStack getCommandStack() {
		if (getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			return multiPageEditorSite.getMultiPageEditor().getAdapter(CommandStack.class);
		}
		return null;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// do nothing
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// do not react to selection changes from the FB type editor, until we can
		// determine the text position for the selected element without:
		// - blocking the UI thread waiting for a lock on the Xtext document,
		// - interrupting the Xtext validation job when executed with priority.
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		// not implemented
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		try {
			return marker.getType().startsWith(
					languageUIPlugin.getBundle().getSymbolicName() + "." + getLanguageShortName().toLowerCase()); //$NON-NLS-1$
		} catch (final CoreException e) {
			return false;// marker does not exist
		}
	}

	protected String getLanguageShortName() {
		final String languageName = getLanguageName();
		return languageName.substring(languageName.lastIndexOf('.') + 1);
	}

	@Override
	public void reloadType() {
		doRevertToSaved();
	}

	@Override
	public Object getSelectableObject() {
		return null;
	}

	@Override
	protected void markInNavigationHistory() {
		if (getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			getSite().getPage().getNavigationHistory().markLocation(multiPageEditorSite.getMultiPageEditor());
		} else {
			super.markInNavigationHistory();
		}
	}

	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return new FBTypeXtextNavigationLocation(this, false);
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		return new FBTypeXtextNavigationLocation(this, true);
	}

	protected abstract void removeFBTypeUpdater();

	protected abstract void installFBTypeUpdater();

	protected abstract boolean selectAndReveal(Object selectedElement, boolean b);
}
