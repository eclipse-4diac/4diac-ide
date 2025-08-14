/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenBulkEditorHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		if (HandlerUtil.getCurrentStructuredSelection(event) instanceof final TreeSelection treeSelection) {
			final Map<IProject, List<URI>> projectMap = new HashMap<>();
			Arrays.stream(treeSelection.getPaths()).forEach(treePath -> {
				final IProject project = getProjectFromPath(treePath);
				if (project == null) {
					return;
				}
				projectMap.computeIfAbsent(project, key -> new ArrayList<URI>());

				if (treePath.getLastSegment() instanceof final UntypedSubApp untypedSubapp) {
					projectMap.get(project).add(EcoreUtil.getURI(untypedSubapp));
				} else if (treePath.getLastSegment() instanceof final IFile file && TypeLibraryManager.INSTANCE
						.getTypeEntryForFile(file) instanceof final SubAppTypeEntry typeEntry) {
					projectMap.get(project).add(EcoreUtil.getURI(typeEntry.getType()));
				}
			});

			projectMap.forEach((project, subAppList) -> {
				final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				try {
					window.getActivePage().openEditor(new BulkEditorInput(project, subAppList),
							"org.eclipse.fordiac.ide.bulkeditor.BulkEditor"); //$NON-NLS-1$
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});
		}
		return null;
	}

	private static IProject getProjectFromPath(final TreePath treePath) {
		for (int i = 0; i < treePath.getSegmentCount(); i++) {
			if (treePath.getSegment(i) instanceof final IProject project && project.isOpen()) {
				return project;
			}
		}

		return null;
	}
}
