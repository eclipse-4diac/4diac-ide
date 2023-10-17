/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyFactory;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class CreateLeafOperation extends AbstractChangeHierarchyOperation {

	private final Level parent;
	private final SubApp subapp;
	private Leaf newLeaf;

	public CreateLeafOperation(final Level parent, final SubApp subapp) {
		super("Create leaf");
		this.parent = parent;
		this.subapp = subapp;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		newLeaf = HierarchyFactory.eINSTANCE.createLeaf();
		newLeaf.setRef(getSubAppHierName(subapp));
		newLeaf.setContainerFileName(getFileName());
		parent.getChildren().add(newLeaf);
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	public static String getSubAppHierName(final SubApp subapp) {
		final String qualifiedName = subapp.getQualifiedName();
		final int firstDot = qualifiedName.indexOf('.');
		if (firstDot != -1) {
			// strip system name
			return qualifiedName.substring(firstDot + 1);
		}
		return qualifiedName;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		parent.getChildren().add(newLeaf);
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		parent.getChildren().remove(newLeaf);
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private String getFileName() {
		final EObject rootContainer = EcoreUtil.getRootContainer(subapp);
		if (rootContainer instanceof final LibraryElement libEl) {
			return libEl.getTypeEntry().getFile().getProjectRelativePath().toPortableString();
		}
		return ""; //$NON-NLS-1$
	}

}
