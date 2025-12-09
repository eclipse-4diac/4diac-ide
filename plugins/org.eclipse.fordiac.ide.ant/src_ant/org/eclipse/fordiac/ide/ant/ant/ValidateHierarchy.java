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
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceImpl;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;

public class ValidateHierarchy extends Task {
	private static final String PLANT_HIERARCHY_FILE_NAME = ".plant.hier"; //$NON-NLS-1$

	private final Map<String, Object> loadOptions = new HashMap<>();
	private final ResourceSet hierarchyResouceSet = new ResourceSetImpl();
	private String projectName;
	private IProject selproject;

	public void setProjectName(final String value) {
		projectName = value;
	}

	public ValidateHierarchy() {
		setupLoadOptions();
	}

	@Override
	public void execute() throws BuildException {
		if (projectName == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		selproject = workspace.getRoot().getProject(projectName);

		if (selproject == null) {
			throw new BuildException("Project named '" + projectName + "' not in workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}
		final IFile file = selproject.getFile(new Path(PLANT_HIERARCHY_FILE_NAME));
		if (file == null || !file.exists()) {
			throw new BuildException("Project named '" + projectName + "' doesn't contain a plant hierarchy");//$NON-NLS-1$ //$NON-NLS-2$
		}
		final URI uri = URI.createURI(file.getLocationURI().toString(), true);
		Resource resource;
		RootLevel root;
		try {
			resource = hierarchyResouceSet.getResource(uri, true);
			if (resource == null) {
				resource = new HierarchyResourceImpl(uri);
				hierarchyResouceSet.getResources().add(resource);
				resource.load(loadOptions);
			}
			root = (RootLevel) resource.getContents().get(0);
		} catch (final IOException e) {
			root = null;
		}
		if (root == null) {
			throw new BuildException("Could not load plant hierarchy for project '" + projectName + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final LinkedList<String> errors = new LinkedList<>();
		final HashSet<String> foundApps = new HashSet<>();

		root.getLevels().forEach(level -> checkErrors(level, new LinkedList<>(), foundApps, errors));

		if (!errors.isEmpty()) {
			throw new BuildException(
					"Found errors in hierarchy of project '" + projectName + "':\n" + String.join("\n", errors)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		log("Valid hierarchy for project '" + projectName + "':"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void checkErrors(final Node node, final LinkedList<String> path, final HashSet<String> foundApps,
			final LinkedList<String> errors) {
		if (node instanceof final Level level) {
			path.addLast(level.getName());
			level.getChildren().forEach(child -> checkErrors(child, path, foundApps, errors));
			path.removeLast();
		} else if (node instanceof final Leaf leaf) {
			if (foundApps.contains(leaf.getRef())) {
				errors.add(String.join("/", path) + " - Duplicate reference: " + leaf.getRef()); //$NON-NLS-1$ //$NON-NLS-2$
			} else if (typeExists(leaf)) {
				foundApps.add(leaf.getRef());
			} else {
				errors.add(String.join("/", path) + " - Non-existing reference: " + leaf.getRef()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	private boolean typeExists(final Leaf leaf) {
		return HierarchyManagerUtil.getElementReferencedbyLeaf(leaf, selproject) != null;
	}

	private void setupLoadOptions() {
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
		hierarchyResouceSet.getLoadOptions().put(XMLResource.OPTION_XML_MAP, map);
	}
}
