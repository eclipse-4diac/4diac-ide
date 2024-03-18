/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved getCurrentActiveEditor and openEditor helper functions
 *   			   to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.editors;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

public final class EditorUtils {

	public static final EditorAction CloseEditor = (final IEditorPart part) -> PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage().closeEditor(part, false);

	private EditorUtils() {
		throw new AssertionError();
	}

	public static IEditorPart getCurrentActiveEditor() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null) {
			return window.getActivePage().getActiveEditor();
		}
		return null;
	}

	public static GraphicalViewer getGraphicalViewerFromCurrentActiveEditor() {
		final IEditorPart currentActiveEditor = getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			return currentActiveEditor.getAdapter(GraphicalViewer.class);
		}
		return null;
	}

	public static IEditorPart openEditor(final IEditorInput input, final String editorId) {
		IEditorPart editor = null;
		final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			editor = activePage.openEditor(input, editorId, true, IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID);
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return editor;
	}

	public static IEditorPart findEditor(final EditorFilter filter) {
		final IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();

		for (final IEditorReference editorReference : editorReferences) {
			final IEditorPart editor = editorReference.getEditor(false);
			if (null != editor && filter.filter(editor)) {
				return editor;
			}
		}
		return null;
	}

	public static void forEachOpenEditorFiltered(final EditorFilter filter, final EditorAction action) {
		Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows()).flatMap(window -> Stream.of(window.getPages()))
				.flatMap(page -> Stream.of(page.getEditorReferences())).map(ref -> ref.getEditor(false))
				.filter(Objects::nonNull).filter(filter::filter).forEach(action::run);
	}

	public static void closeEditorsFiltered(final EditorFilter filter) {
		forEachOpenEditorFiltered(filter, CloseEditor);
	}

	public static void refreshPropertySheetWithSelection(final IEditorPart activeEditor, final EditPartViewer viewer,
			final Object obj) {
		if (viewer != null) {
			viewer.select((EditPart) obj);
		}
		final IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.findView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
		if (activeEditor != null && view instanceof final PropertySheet propertySheet) {
			final ISelection selection = activeEditor.getSite().getSelectionProvider().getSelection();
			propertySheet.selectionChanged(activeEditor, selection);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(view);
		}
	}
}
