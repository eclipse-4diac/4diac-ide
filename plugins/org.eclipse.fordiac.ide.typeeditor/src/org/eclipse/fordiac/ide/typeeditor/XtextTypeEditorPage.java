/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - extracted from FBTypeXtextEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.gef.commands.OperationHistoryCommandStack;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextViewerExtension6;
import org.eclipse.jface.text.IUndoManagerExtension;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

public class XtextTypeEditorPage extends XtextEditor implements ITypeEditorPage {

	@Inject
	private AbstractUIPlugin languageUIPlugin;
	private boolean restoringSelection;
	private boolean performanceMode;
	private boolean performanceModeShowDialog;
	private IUndoContext typeEditorUndoContext;

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		setupUndoContext();
		checkPerformanceMode();
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		super.doSetInput(input);
		setupUndoContext();
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
		if (adapter == IContentOutlinePage.class && performanceMode) {
			return adapter
					.cast(new TypeEditorMessageOutline(Messages.XtextTypeEditorPage_PerformanceModeOutlineMessage));
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void setFocus() {
		if (performanceModeShowDialog) {
			performanceModeShowDialog = false;
			MessageDialog.openInformation(getShell(), Messages.XtextTypeEditorPage_PerformanceModeDialogTitle,
					Messages.XtextTypeEditorPage_PerformanceModeDialogMessage);
		}
		super.setFocus();
	}

	protected void checkPerformanceMode() {
		if (performanceMode) {
			return;
		}
		if (shouldEnablePerformanceMode()) {
			enablePerformanceMode();
		}
	}

	@SuppressWarnings("static-method") // subclasses should override
	protected boolean shouldEnablePerformanceMode() {
		return false; // disabled by default
	}

	protected void enablePerformanceMode() {
		performanceMode = true;
		performanceModeShowDialog = true;
		if (getSourceViewer() instanceof final ProjectionViewer projectionViewer) {
			projectionViewer.disableProjection();
		}
		uninstallFoldingSupport();
		uninstallHighlightingHelper();
		if (getSourceViewer() instanceof final SourceViewer sourceViewer) {
			sourceViewer.setCodeMiningProviders(null);
		}
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
		setupUndoContext();
	}

	@Override
	protected void handleEditorInputChanged() {
		// avoid asking the user for a refresh or save as
		// and leave updates to the enclosing FB type editor
	}

	@Override
	protected boolean isPrefQuickDiffAlwaysOn() {
		return false;
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
		return new XtextTypeEditorPageNavigationLocation(this, false);
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		return new XtextTypeEditorPageNavigationLocation(this, true);
	}

	protected void setupUndoContext() {
		if (getUndoContext() instanceof final ObjectUndoContext objectUndoContext) {
			if (typeEditorUndoContext != null) {
				objectUndoContext.removeMatch(typeEditorUndoContext);
			}
			if (getCommandStack() instanceof final OperationHistoryCommandStack operationHistoryCommandStack) {
				typeEditorUndoContext = operationHistoryCommandStack.getUndoContext();
			}
			if (typeEditorUndoContext != null && !objectUndoContext.matches(typeEditorUndoContext)) {
				objectUndoContext.addMatch(typeEditorUndoContext);
			}
		}
	}

	protected IUndoContext getUndoContext() {
		if (getSourceViewer() instanceof final ITextViewerExtension6 textViewerExtension
				&& textViewerExtension.getUndoManager() instanceof final IUndoManagerExtension undoManagerExtension) {
			return undoManagerExtension.getUndoContext();
		}
		return null;
	}
}