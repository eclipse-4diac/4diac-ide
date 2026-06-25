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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.model;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyFactory;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceFactoryImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceImpl;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class HierarchyManagerPersistenceHelper {

	public static final String PLANT_HIERARCHY_FILE_NAME_EXTENSION = "HIER"; //$NON-NLS-1$
	public static final String PLANT_HIERARCHY_FILE_NAME_EXTENSION_WITH_DOT = "." + PLANT_HIERARCHY_FILE_NAME_EXTENSION; //$NON-NLS-1$
	public static final String PLANT_HIERARCHY_FILE_NAME = ".plant" //$NON-NLS-1$
			+ PLANT_HIERARCHY_FILE_NAME_EXTENSION_WITH_DOT.toLowerCase();

	private static final XMLMapImpl XML_MAP = new XMLMapImpl();
	static {
		XML_MAP.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
	}

	private static final Map<String, Object> LOAD_OPTIONS = Map.of( //
			XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE, //
			XMLResource.OPTION_XML_MAP, XML_MAP //
	);

	private static final Map<String, Object> SAVE_OPTIONS = Map.of( //
			XMLResource.OPTION_ENCODING, "UTF-8", //$NON-NLS-1$
			XMLResource.OPTION_FORMATTED, Boolean.TRUE, //
			XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE, //
			XMLResource.OPTION_XML_MAP, XML_MAP);

	public static RootLevel loadPlantHierarchy(final IProject project) {
		final ResourceSet hierarchyResouceSet = createPlantHierarchyResourceSet();
		final IFile file = project.getFile(PLANT_HIERARCHY_FILE_NAME);
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		if (!file.exists()) {
			// try to create a new file
			return createNewHierarchyFile(file, uri, hierarchyResouceSet);
		}
		// we don't want to load the resource content as we can not give the mapping
		// options
		Resource resource = hierarchyResouceSet.getResource(uri, true);
		try {
			if (resource == null) {
				resource = new HierarchyResourceImpl(uri);
				hierarchyResouceSet.getResources().add(resource);
				resource.load(LOAD_OPTIONS);
			}
			return (RootLevel) resource.getContents().get(0);
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Could not load plant hierarchy", e); //$NON-NLS-1$
		}
		return null;
	}

	public static ResourceSet createPlantHierarchyResourceSet() {
		final ResourceSet hierarchyResouceSet = new ResourceSetImpl();

		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PLANT_HIERARCHY_FILE_NAME_EXTENSION, //
				new HierarchyResourceFactoryImpl());

		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PLANT_HIERARCHY_FILE_NAME_EXTENSION.toLowerCase(), //
				new HierarchyResourceFactoryImpl());

		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		hierarchyResouceSet.getLoadOptions().put(XMLResource.OPTION_XML_MAP, XML_MAP);
		return hierarchyResouceSet;
	}

	private static RootLevel createNewHierarchyFile(final IFile file, final URI uri,
			final ResourceSet hierarchyResouceSet) {
		Resource resource = hierarchyResouceSet.createResource(uri, ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		if (resource == null) {
			resource = new HierarchyResourceImpl(uri);
			hierarchyResouceSet.getResources().add(resource);
		}
		final RootLevel root = HierarchyFactory.eINSTANCE.createRootLevel();
		resource.getContents().add(root);
		saveNewResource(file, resource);
		return root;
	}

	private static void saveNewResource(final IFile file, final Resource resource) {
		final WorkspaceJob job = createPlantHierarchySaveJob(resource);
		job.setRule(file.getParent());
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError("Could not wait for plant hierarchy creation", e); //$NON-NLS-1$
			Thread.currentThread().interrupt();
		}
	}

	public static void saveHierarchy(final EObject node) {
		final Resource eResource = node.eResource();
		if (eResource != null) {
			final WorkspaceJob job = createPlantHierarchySaveJob(eResource);
			job.setRule(getRuleScope(eResource.getURI()));
			job.schedule();
		}
	}

	private static WorkspaceJob createPlantHierarchySaveJob(final Resource eResource) {
		final WorkspaceJob job = new WorkspaceJob("Save plant hierarchy: " + eResource.getURI().toFileString()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				try {
					eResource.save(SAVE_OPTIONS);
				} catch (final IOException e) {
					FordiacLogHelper.logError("Could not save plant hierarchy!", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.setSystem(true);
		job.setPriority(Job.SHORT);
		return job;
	}

	private static ISchedulingRule getRuleScope(final URI uri) {
		if (uri.isPlatformResource()) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return root.getFile(new Path(uri.toPlatformString(true)));
		}
		return null;
	}

	private HierarchyManagerPersistenceHelper() {
		// helper class shall not be instantiated
	}

}
