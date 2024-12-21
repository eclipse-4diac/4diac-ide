/*******************************************************************************
 * Copyright (c) 2020, 2204 Johannes Kepler University Linz,
 *                          Primetals Technlogies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Alois Zoitl      - reworked to use FilteredItemsSelectionDialog so that not
 *                      all types have to be provided on creating the dialog.
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.handlers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.TypeImageProvider;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.FrameworkUtil;

public class OpenTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final TypeSearchDialog dialog = new TypeSearchDialog(shell);
		dialog.open();

		final Object result = dialog.getFirstResult();
		if (result instanceof final TypeEntry typeEntry) {
			openEditor(typeEntry.getFile());
		}
		return null;
	}

	private static void openEditor(final IFile file) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
						.getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (final PartInitException e1) {
					FordiacLogHelper.logError(e1.getMessage(), e1);
					final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(shell, Messages.OpenTypeHandler_OPEN_TYPE_ERROR_TITLE,
							Messages.OpenTypeHandler_EDITOR_OPEN_ERROR_MESSAGE);
				}
			}
		}
	}

	private static class TypeSearchDialog extends FilteredItemsSelectionDialog {

		private final List<IProject> projects;
		private final ResultListLabelProvider listLabelProvider = new ResultListLabelProvider();
		private String selectedProject = null;

		public TypeSearchDialog(final Shell shell) {
			super(shell);
			setTitle(Messages.OpenTypeHandler_OPEN_TYPE_TITLE);
			setMessage(Messages.OpenTypeHandler_DialogMessage);
			setSelectionHistory(getSelectionHistory());
			setListLabelProvider(listLabelProvider);
			setDetailsLabelProvider(new TypeSearchDetailsLabelProvider());
			projects = Arrays.stream(ResourcesPlugin.getWorkspace().getRoot().getProjects())
					.filter(TypeSearchDialog::isOpen4diacIDEProject).toList();
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Composite container = (Composite) super.createDialogArea(parent);
			final Combo combo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(combo);

			combo.addListener(SWT.Selection, e -> {
				final int selectionIndex = combo.getSelectionIndex();
				selectedProject = (selectionIndex == 0) ? null : combo.getItem(selectionIndex);
				applyFilter();
			});
			combo.setItems(getProjectComboEntries());
			combo.select(0);
			return container;
		}

		private String[] getProjectComboEntries() {
			return Stream.concat( //
					Stream.of(Messages.OpenTypeHandler_SearchInAllProjects), // add the all projects to the beginning of
																				// the stream
					projects.stream().map(IProject::getName).sorted(Comparator.naturalOrder())).toArray(String[]::new);
		}

		@Override
		protected Control createExtendedContentArea(final Composite parent) {
			return null;
		}

		@Override
		protected IDialogSettings getDialogSettings() {
			final IDialogSettings settings = PlatformUI
					.getDialogSettingsProvider(FrameworkUtil.getBundle(TypeSearchDialog.class)).getDialogSettings();
			return DialogSettings.getOrCreateSection(settings, getClass().getSimpleName());
		}

		@Override
		protected IStatus validateItem(final Object item) {
			return Status.OK_STATUS;
		}

		@Override
		protected ItemsFilter createFilter() {
			return new TypeEntryFilter(selectedProject);
		}

		@Override
		protected Comparator getItemsComparator() {
			return Comparator.comparing(this::getElementName);
		}

		@Override
		protected void fillContentProvider(final AbstractContentProvider contentProvider, final ItemsFilter itemsFilter,
				final IProgressMonitor progressMonitor) throws CoreException {
			listLabelProvider.setSearchString(itemsFilter.getPattern());
			if (itemsFilter.getPattern().isBlank()) {
				// an empty filter should lead to no results, so for performance reasons we can
				// skip here
				return;
			}

			if (selectedProject == null) {
				// search in all projects
				for (final IProject proj : projects) {
					if (progressMonitor.isCanceled()) {
						return;
					}
					fillForProject(contentProvider, itemsFilter, progressMonitor, proj);
				}
			} else {
				fillForProject(contentProvider, itemsFilter, progressMonitor, getSelectedProject());
			}
		}

		private IProject getSelectedProject() {
			return projects.stream().filter(proj -> proj.getName().equals(selectedProject)).findAny().orElse(null);
		}

		private static void fillForProject(final AbstractContentProvider contentProvider, final ItemsFilter itemsFilter,
				final IProgressMonitor progressMonitor, final IProject proj) {
			for (final TypeEntry entry : TypeLibraryManager.INSTANCE.getTypeLibrary(proj).getAllTypes()) {
				if (progressMonitor.isCanceled()) {
					return;
				}
				if (!(entry instanceof SystemEntry) && itemsFilter.matchItem(entry)) {
					contentProvider.add(entry, itemsFilter);
				}
			}
		}

		@Override
		public String getElementName(final Object item) {
			if (item instanceof final TypeEntry typeEntry) {
				return typeEntry.getFullTypeName();
			}
			return null;
		}

		private static boolean isOpen4diacIDEProject(final IProject proj) {
			try {
				return proj.isOpen() && proj.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Could not read project nature", e); //$NON-NLS-1$
			}
			return false;
		}

		private final class TypeEntryFilter extends ItemsFilter {

			final String selectedProject;

			public TypeEntryFilter(final String selectedProject) {
				this.selectedProject = selectedProject;
			}

			@Override
			public boolean matchItem(final Object item) {
				if (item instanceof final TypeEntry typeEntry) {
					return matches(typeEntry.getTypeName()) || matches(typeEntry.getPackageName())
							|| matches(typeEntry.getComment());
				}
				return false;
			}

			@Override
			public boolean isConsistentItem(final Object item) {
				return true;
			}

			@Override
			public boolean equalsFilter(final ItemsFilter filter) {
				if (filter instanceof final TypeEntryFilter typeEntryFilter
						&& typeEntryFilter.selectedProject != selectedProject) {
					return false;
				}
				return super.equalsFilter(filter);
			}

			@Override
			public boolean isSubFilter(final ItemsFilter filter) {
				if (filter instanceof final TypeEntryFilter typeEntryFilter
						&& typeEntryFilter.selectedProject != selectedProject) {
					return false;
				}
				return super.isSubFilter(filter);
			}
		}

		private static class TypeSearchDetailsLabelProvider extends LabelProvider implements IStyledLabelProvider {

			@Override
			public StyledString getStyledText(final Object element) {
				if (element instanceof final TypeEntry entry) {
					return ResultListLabelProvider.getTypeEntryStyledText(entry);
				}
				if (element != null) {
					return new StyledString(element.toString());
				}
				return new StyledString();
			}

			@Override
			public String getText(final Object element) {
				return getStyledText(element).getString();
			}

			@Override
			public Image getImage(final Object element) {
				if (element instanceof final TypeEntry entry) {
					return TypeImageProvider.getImageForTypeEntry(entry);
				}
				return null;
			}
		}
	}

}
