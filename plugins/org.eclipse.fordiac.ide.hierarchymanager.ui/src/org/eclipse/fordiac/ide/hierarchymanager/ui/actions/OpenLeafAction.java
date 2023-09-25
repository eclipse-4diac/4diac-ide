/*******************************************************************************
 * Copyright (c) 2020, 2023 Primetals Technology Austria GmbH,
 * 							Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - the searching for ref elements is taken from an old version of
 *                 AutomationSystemEditor where I did a similar search for
 *                 book marks
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.actions;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class OpenLeafAction extends BaseSelectionListenerAction {

	public static final String OPEN_LEAF_ACTION_ID = "org.eclipse.fordiac.ide.hierarchymanager.ui.openLeafAction";//$NON-NLS-1$

	final PlantHierarchyView phView;

	public OpenLeafAction(final PlantHierarchyView phView) {
		super(Messages.OpenEditorAction_text);
		setId(OPEN_LEAF_ACTION_ID);
		this.phView = phView;
	}

	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		if ((getProject() == null) || (selection.size() != 1)) {
			return false;
		}
		final Object model = getStructuredSelection().getFirstElement();
		return (model instanceof Leaf);
	}

	@Override
	public void run() {
		if (getStructuredSelection().getFirstElement() instanceof final Leaf leaf) {
			final EObject refObj = getElementReferencedbyLeaf(leaf);
			OpenListenerManager.openEditor(refObj);
		}
	}

	private EObject getElementReferencedbyLeaf(final Leaf leaf) {
		final EObject object = getTypeEditable(leaf.getContainerFileName());
		final String[] refPath = leaf.getRef().split("\\."); //$NON-NLS-1$
		if (object instanceof final AutomationSystem sys) {
			return findRefInSystem(sys, refPath);
		}
		if (object instanceof final SubAppType subAppType) {
			return parseSubappPath(subAppType.getFBNetwork(), refPath);
		}
		return null;
	}

	private IProject getProject() {
		return phView.getCurrentProject();
	}

	private EObject getTypeEditable(final String containerFileName) {
		final IProject project = getProject();
		if (project != null) {
			final IFile file = project.getFile(containerFileName);
			if (file.exists()) {
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
				if (typeEntry != null) {
					return typeEntry.getTypeEditable();
				}
			}
		}
		return null;
	}

	private static EObject findRefInSystem(final AutomationSystem system, final String[] refPath) {
		EObject retVal = system.getApplicationNamed(refPath[0]);
		if (null != retVal) {
			if (refPath.length > 1) {
				// we are within a subapplication in the application
				retVal = parseSubappPath(((Application) retVal).getFBNetwork(),
						Arrays.copyOfRange(refPath, 1, refPath.length));
			}
		} else if (refPath.length > 2) {
			// we need to have at least a device and a resource in the path
			retVal = system.getDeviceNamed(refPath[0]);
			if (null != retVal) {
				retVal = ((Device) retVal).getResourceNamed(refPath[1]);
				if ((null != retVal) && (refPath.length > 2)) {
					// we are within a subapplication in the resource
					retVal = parseSubappPath(((Resource) retVal).getFBNetwork(),
							Arrays.copyOfRange(refPath, 2, refPath.length));
				}
			}
		}
		return retVal;
	}

	private static EObject parseSubappPath(FBNetwork network, final String[] path) {
		EObject retVal = null;
		for (final String element : path) {
			retVal = network.getElementNamed(element);
			if (retVal instanceof final SubApp subApp) {
				network = subApp.getSubAppNetwork();
			} else if (retVal instanceof final SubAppType subAppType) {
				network = subAppType.getFBNetwork();
			} else {
				return null;
			}
		}
		return retVal;
	}

}
