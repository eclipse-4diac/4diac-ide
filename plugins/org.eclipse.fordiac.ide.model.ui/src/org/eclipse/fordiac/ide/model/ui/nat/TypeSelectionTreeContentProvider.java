/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the now AbstractSelectionButton class,
 *   took project finding from the search plug-in.
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.FileEditorInput;

public class TypeSelectionTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof HashMap<?, ?>) {
			final HashMap<String, List<String>> map = (HashMap<String, List<String>>) inputElement;
			return createTree(map);
		}
		return new Object[0];
	}

	protected Object[] createTree(final HashMap<String, List<String>> inputElement) {
		final TypeNode elementaries = new TypeNode(Messages.DataTypeDropdown_Elementary_Types);
		final TypeNode structures = new TypeNode(Messages.DataTypeDropdown_STRUCT_Types);

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(getCurrentProject());
		final List<DataType> dataTypes = typeLib.getDataTypeLibrary().getDataTypesSorted();
		final List<AdapterTypeEntry> adapterTypes = typeLib.getAdapterTypesSorted();
		final List<StructuredType> structuredTypes = typeLib.getDataTypeLibrary().getStructuredTypesSorted();

		inputElement.forEach((key, val) -> {
			if (val != null && !val.isEmpty()) {
				val.forEach(value -> {
					if (key.equals(Messages.DataTypeDropdown_Elementary_Types)) {
						final Optional<DataType> type = dataTypes.stream()
								.filter(dataType -> dataType.getName().equals(value)).findFirst();
						TypeNode newNode;
						if (!type.isEmpty()) {
							newNode = new TypeNode(type.get().getName(), type.get());
						} else {
							final Optional<AdapterTypeEntry> adapterType = adapterTypes.stream()
									.filter(adapter -> adapter.getType().getName().equals(value)).findFirst();
							newNode = new TypeNode(adapterType.get().getType().getName(), adapterType.get().getType());
						}
						elementaries.addChild(newNode);
					} else if (key.equals(Messages.DataTypeDropdown_STRUCT_Types)) {
						final Optional<StructuredType> type = structuredTypes.stream()
								.filter(structType -> structType.getName().equals(value)).findFirst();
						if (type.isPresent()) {
							if (null != type.get().getTypeEntry()) {
								final IPath parentPath = type.get().getTypeEntry().getFile().getParent()
										.getProjectRelativePath();
								createSubdirectories(structures, type.get(), parentPath);
							} else {
								final TypeNode runtimeNode = new TypeNode(type.get().getName(), type.get());
								runtimeNode.setParent(structures);
								structures.addChild(runtimeNode);
							}
						}
					}
				});
			}
		});

		if (elementaries.getChildren().isEmpty()) {
			return structures.getChildren().toArray();
		}
		if (structures.getChildren().isEmpty()) {
			return elementaries.getChildren().toArray();
		}

		return new TypeNode[] { elementaries, structures };
	}

	protected static void createSubdirectories(TypeNode node, final LibraryElement libraryElement,
			final IPath parentPath) {
		// split up the path in subdirectories
		final String[] paths = parentPath.segments();

		// start after Type Library
		for (int i = 1; i < paths.length; i++) {
			final TypeNode current = new TypeNode(paths[i]);
			// check if we already have a parent node
			final int index = node.getChildren().indexOf(current);
			if (-1 != index) {
				node = node.getChildren().get(index);
			} else {
				current.setParent(node);
				node.addChild(current);
				node = current;
			}
		}
		final TypeNode actualType = new TypeNode(libraryElement.getName(), libraryElement);
		actualType.setParent(node);
		node.addChild(actualType);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final TypeNode typeNode) {
			return typeNode.getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final TypeNode typeNode) {
			return typeNode.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final TypeNode typeNode) {
			return !typeNode.getChildren().isEmpty();
		}
		return false;
	}

	protected static IProject getCurrentProject() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IProject project = getProjectFromActiveEditor(page);
		if (project == null) {
			project = getProjectFromProjectExplorerSelction(page);
		}
		return project;
	}

	private static IProject getProjectFromActiveEditor(final IWorkbenchPage page) {
		final IEditorPart openEditor = page.getActiveEditor();
		if (openEditor != null) {
			final IEditorInput editorInput = openEditor.getEditorInput();
			if (editorInput instanceof final FileEditorInput fileEditorInput) {
				return fileEditorInput.getFile().getProject();
			}
		}
		return null;
	}

	private static IProject getProjectFromProjectExplorerSelction(final IWorkbenchPage page) {
		final IViewPart view = page.findView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$

		if (view instanceof final CommonNavigator commonNavigator) {
			final ISelection selection = commonNavigator.getCommonViewer().getSelection();
			if (selection instanceof final StructuredSelection structuredSelection && !structuredSelection.isEmpty()) {
				Object selElement = structuredSelection.getFirstElement();
				if (selElement instanceof final EObject obj) {
					selElement = getFileForModel(obj);
				}
				if (selElement instanceof final IResource resource) {
					return resource.getProject();
				}
			}
		}

		return null;
	}

	private static IFile getFileForModel(final EObject sel) {
		final EObject root = EcoreUtil.getRootContainer(sel);
		if (root instanceof final LibraryElement libraryElement) {
			return libraryElement.getTypeEntry().getFile();
		}
		return null;
	}

}
