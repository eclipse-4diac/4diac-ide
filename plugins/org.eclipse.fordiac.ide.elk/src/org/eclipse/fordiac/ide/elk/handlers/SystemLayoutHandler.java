/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.elk.FordiacLayout;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

public class SystemLayoutHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var files = collectFiles(event);
		if (confirmation(files)) {
			final var elements = collectElements(event);
			final var job = createJob(elements);
			job.setUser(true); // show progress dialog
			job.schedule();
		}
		return Status.OK_STATUS;
	}

	private static Set<IFile> collectFiles(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		assert selection instanceof StructuredSelection;
		final var selectionList = ((StructuredSelection) selection).toList();

		final Set<IFile> files = new HashSet<>();

		for (final var obj : selectionList) {
			if (obj instanceof final Application app) {
				files.add(app.getAutomationSystem().getTypeEntry().getFile());
			} else if (obj instanceof final SubApp subapp) {
				final EObject root = EcoreUtil.getRootContainer(subapp);
				if (root instanceof final AutomationSystem sys) {
					files.add(sys.getTypeEntry().getFile());
				} else if (root instanceof final CompositeFBType type) {
					files.add(type.getTypeEntry().getFile());
				}
			} else if (obj instanceof final IFile file) {
				files.add(file);
			} else if (obj instanceof final IProject project) {
				final List<AutomationSystem> systems = SystemManager.INSTANCE.getProjectSystems(project);
				systems.forEach(sys -> files.add(sys.getTypeEntry().getFile()));
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
				typeLibrary.getCompositeFBTypes().forEach(typeEntry -> files.add(typeEntry.getFile()));
				typeLibrary.getSubAppTypes().forEach(typeEntry -> files.add(typeEntry.getFile()));
			}
		}

		return files;
	}

	private static List<Object> collectElements(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		assert selection instanceof StructuredSelection;
		final var selectionList = ((StructuredSelection) selection).toList();

		final List<Object> elements = new ArrayList<>();

		for (final var obj : selectionList) {
			if (obj instanceof final Application app) {
				collectElements(elements, List.of(app));
			} else if (obj instanceof final SubApp subapp) {
				collectElements(elements, List.of(subapp));
			} else if (obj instanceof final IFile file) {
				if (SystemManager.isSystemFile(file)) {
					final var system = SystemManager.INSTANCE.getSystem(file);
					collectElements(elements, system.getApplication());
				} else {
					// cfb and typed subapp
					elements.add(file);
				}
			} else if (obj instanceof final IProject project) {
				// @formatter:off
				final var applications = SystemManager.INSTANCE.getProjectSystems(project).stream()
					.map(AutomationSystem::getApplication)
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
				// @formatter:on
				collectElements(elements, applications);
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
				typeLibrary.getCompositeFBTypes().forEach(typeEntry -> elements.add(typeEntry.getFile()));
				typeLibrary.getSubAppTypes().forEach(typeEntry -> elements.add(typeEntry.getFile()));
			}
		}

		return elements;
	}

	private static <T extends EObject> void collectElements(final List<Object> saveList, final List<T> elements) {
		for (final var elem : elements) {
			saveList.add(elem);

			final FBNetwork network;
			if (elem instanceof final SubApp subapp) {
				network = subapp.getSubAppNetwork();
			} else if (elem instanceof final Application app) {
				network = app.getFBNetwork();
			} else {
				continue;
			}

			// @formatter:off
			final var subapps = network.getNetworkElements().stream()
					.filter(SubApp.class::isInstance)
					.map(SubApp.class::cast)
					.filter(Predicate.not(SubApp::isTyped))
					.collect(Collectors.toList());
			// @formatter:on

			collectElements(saveList, subapps);
		}
	}

	private static boolean confirmation(final Set<IFile> files) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final String title = Messages.SystemLayout_ConfirmationTitle;

		final var sb = new StringBuilder();
		sb.append(Messages.SystemLayout_ConfirmationMessage);

		sb.append(System.lineSeparator());
		sb.append(Messages.SystemLayout_ConfirmationFilesAffected);
		sb.append(System.lineSeparator());
		for (final var file : files) {
			sb.append(file.getName()).append(System.lineSeparator());
		}

		return MessageDialog.openQuestion(shell, title, sb.toString());
	}

	private static Job createJob(final List<Object> elements) {
		return new Job(Messages.SystemLayout_ProgressTitle) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask(Messages.SystemLayout_ProgressMessage, elements.size());
				final Set<Object> visited = new HashSet<>();
				final Set<IEditorPart> editors = new HashSet<>();
				for (final var elem : elements) {
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					if (elem instanceof final EObject modelElement) {
						Display.getDefault().syncExec(() -> layoutModel(modelElement, visited, editors));
					} else if (elem instanceof final IFile file) {
						Display.getDefault().syncExec(() -> layoutType(file, visited, editors));
					}
					monitor.worked(1);
				}
				Display.getDefault().syncExec(() -> {
					for (final var editor : editors) {
						editor.doSave(monitor);
					}
				});
				monitor.done();
				return Status.OK_STATUS;
			}
		};
	}

	private static void layoutModel(final EObject obj, final Set<Object> visited, final Set<IEditorPart> editors) {
		final EObject refElement = getBreadCrumbRefElement(obj);
		if (visited.contains(refElement)) {
			return;
		}
		visited.add(refElement);

		final EObject root = EcoreUtil.getRootContainer(obj);
		if (!(root instanceof LibraryElement)) {
			return;
		}

		if (root instanceof final SubAppType type && obj instanceof final SubApp subapp) {
			// switch to typed layout
			layoutSubappInTypedSubapp(subapp, type, visited);
			return;
		}

		final var file = ((LibraryElement) root).getTypeEntry().getFile();
		if (file == null) {
			return;
		}

		final var editor = (AbstractBreadCrumbEditor) openEditor(file);
		if (editor == null) {
			return;
		}

		layoutBreadcrumbEditor(refElement, editor);
		editors.add(editor);
	}

	// Handles the layout of an untyped subapp within a typed subapp.
	// This is somewhat problematic because this means we do not know which editor
	// to open beforehand, therefore this method is called from the layout run
	// for model objects as this is our default assumption for untyped subapps.
	private static void layoutSubappInTypedSubapp(final SubApp subapp, final SubAppType type,
			final Set<Object> visited) {
		final var file = type.getTypeEntry().getTypeEditable().getTypeEntry().getFile();
		if (openEditor(file) instanceof final FormEditor multiPageEditor) {
			final var breadcrumbEditor = multiPageEditor.getAdapter(AbstractBreadCrumbEditor.class);
			multiPageEditor.setActiveEditor(breadcrumbEditor);
			layoutBreadcrumbEditor(getBreadCrumbRefElement(subapp), breadcrumbEditor);

			final List<EObject> elements = new ArrayList<>();
			collectSubapps(elements, subapp.getSubAppNetwork());
			for (final var elem : elements) {
				layoutBreadcrumbEditor(getBreadCrumbRefElement(elem), breadcrumbEditor);
			}

			saveTypeEditor(multiPageEditor, type.getTypeEntry().getTypeEditable());
		}
	}

	private static void layoutType(final IFile file, final Set<Object> visited, final Set<IEditorPart> editors) {
		if (openEditor(file) instanceof final FormEditor multiPageEditor) {
			final var typeEditable = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file).getTypeEditable();
			if (typeEditable instanceof final SubAppType subappType) {
				handleSubappType(multiPageEditor, typeEditable, subappType);
			} else if (typeEditable instanceof CompositeFBType) {
				handleCompositeType(multiPageEditor, typeEditable);
			}
		}
	}

	private static void handleSubappType(final FormEditor multiPageEditor, final LibraryElement typeEditable,
			final SubAppType subappType) {
		final var breadcrumbEditor = multiPageEditor.getAdapter(AbstractBreadCrumbEditor.class);
		multiPageEditor.setActiveEditor(breadcrumbEditor);
		final var viewer = multiPageEditor.getAdapter(GraphicalViewer.class);
		viewer.flush();
		FordiacLayout.blockLayout(multiPageEditor, (AbstractFBNetworkEditPart) viewer.getRootEditPart().getContents());

		final List<EObject> elements = new ArrayList<>();
		collectSubapps(elements, subappType.getFBNetwork());
		for (final var elem : elements) {
			layoutBreadcrumbEditor(getBreadCrumbRefElement(elem), breadcrumbEditor);
		}

		saveTypeEditor(multiPageEditor, typeEditable);
	}

	private static void handleCompositeType(final FormEditor multiPageEditor, final LibraryElement typeEditable) {
		final var networkEditor = multiPageEditor.getAdapter(FBNetworkEditor.class);
		multiPageEditor.setActiveEditor(networkEditor);
		final var viewer = multiPageEditor.getAdapter(GraphicalViewer.class);
		viewer.flush();
		FordiacLayout.blockLayout(multiPageEditor, (AbstractFBNetworkEditPart) viewer.getRootEditPart().getContents());

		saveTypeEditor(multiPageEditor, typeEditable);
	}

	private static IEditorPart openEditor(final IFile file) {
		final var page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			return IDE.openEditor(page, file);
		} catch (final PartInitException e) {
			FordiacLogHelper.logError("System Explorer Layout: failed to open editor", e); //$NON-NLS-1$
			return null;
		}
	}

	private static void saveTypeEditor(final FormEditor multiPageEditor, final LibraryElement typeEditable) {
		multiPageEditor.getAdapter(org.eclipse.gef.commands.CommandStack.class).markSaveLocation();
		try {
			typeEditable.getTypeEntry().save(typeEditable);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		multiPageEditor.editorDirtyStateChanged();
	}

	private static void collectSubapps(final List<EObject> saveList, final FBNetwork network) {
		// @formatter:off
		final var subapps = network.getNetworkElements().stream()
				.filter(SubApp.class::isInstance)
				.map(SubApp.class::cast)
				.map(SubApp.class::cast)
				.filter(Predicate.not(SubApp::isTyped))
				.collect(Collectors.toList());
		// @formatter:on
		saveList.addAll(subapps);

		subapps.forEach(sub -> collectSubapps(saveList, sub.getSubAppNetwork()));
	}

	private static void layoutBreadcrumbEditor(final EObject refElement, final AbstractBreadCrumbEditor editor) {
		editor.getBreadcrumb().setInput(refElement);
		final var viewer = editor.getAdapter(GraphicalViewer.class);
		viewer.flush();
		FordiacLayout.blockLayout(editor, (AbstractFBNetworkEditPart) viewer.getRootEditPart().getContents());
	}

	private static EObject getBreadCrumbRefElement(final EObject sel) {
		EObject refElement = sel;
		if ((sel instanceof final FBNetworkElement fbnEl && fbnEl.getType() != null) || (sel instanceof Group)) {
			refElement = sel.eContainer().eContainer();
		}
		// For unfolded subapps find the next parent that is not expanded as refElement
		while (refElement instanceof final SubApp subApp && subApp.isUnfolded()) {
			refElement = refElement.eContainer().eContainer();
		}
		return refElement;
	}

}
