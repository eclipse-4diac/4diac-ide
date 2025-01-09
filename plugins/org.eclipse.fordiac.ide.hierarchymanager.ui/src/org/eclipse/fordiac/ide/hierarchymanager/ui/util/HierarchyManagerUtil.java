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
 *   Patrick Aigner
 *     - extracted methods from OpenLeafAction
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class HierarchyManagerUtil {
	private HierarchyManagerUtil() {
		// static class
	}

	public static EObject getElementReferencedbyLeaf(final Leaf leaf, final IProject project) {
		final EObject object = getTypeEditable(leaf.getContainerFileName(), project);
		final String[] refPath = leaf.getRef().split("\\."); //$NON-NLS-1$
		if (object instanceof final AutomationSystem sys) {
			return findRefInSystem(sys, refPath);
		}
		if (object instanceof final SubAppType subAppType) {
			return parseSubappPath(subAppType.getFBNetwork(), refPath);
		}
		return null;
	}

	public static EObject getTypeEditable(final String containerFileName, final IProject project) {
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

	public static EObject findRefInSystem(final AutomationSystem system, final String[] refPath) {
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

	public static EObject parseSubappPath(FBNetwork network, final String[] path) {
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

	@FunctionalInterface
	public interface LeafMatcher {
		boolean match(String s);
	}

	public static List<Leaf> searchLeaf(final RootLevel rootLevel, final LeafMatcher matcher) {
		final List<Leaf> result = new ArrayList<>();

		for (final Level level : rootLevel.getLevels()) {
			searchLeaf(level, result, matcher);
		}
		return result;
	}

	public static List<Leaf> searchLeaf(final Level level, final List<Leaf> result, final LeafMatcher matcher) {
		for (final Node node : level.getChildren()) {
			if (node instanceof final Level l) {
				searchLeaf(l, result, matcher);
			}
			if (node instanceof final Leaf leaf && matcher.match(leaf.getRef())) {
				result.add(leaf);
			}
		}
		return result;
	}

}
