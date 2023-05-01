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
package org.eclipse.fordiac.ide.hierarchymanager.ui.providers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.provider.HierarchyItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class HierarchyContentProvider extends AdapterFactoryContentProvider {
	private static final String PLANT_HIERARCHY_FILE_NAME = ".plant.hier"; //$NON-NLS-1$
	private static final String PLANT_HIERARCHY_FILE_NAME_EXTENSION = "hier"; //$NON-NLS-1$

	final Map<String, Object> loadOptions = new HashMap<>();
	private final ResourceSet hierarchyResouceSet = new ResourceSetImpl();

	public HierarchyContentProvider() {
		super(new HierarchyItemProviderAdapterFactory());
		setupEMFInfra();

	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final IProject proj) {
			return loadHierachyForProject(proj);
		}
		return super.getChildren(parentElement);
	}

	private Object[] loadHierachyForProject(final IProject proj) {
		final IFile file = proj.getFile(PLANT_HIERARCHY_FILE_NAME);
		if (file.exists()) {
			final URI uri = URI.createFileURI(file.getLocation().toOSString());
			// we don't want to load the resource content as we can not give the mapping options
			Resource resource = hierarchyResouceSet.getResource(uri, false);
			try {
				if (resource == null) {
					resource = new XMLResourceImpl(uri);
					hierarchyResouceSet.getResources().add(resource);
					resource.load(loadOptions);
				}
				final EObject root = resource.getContents().get(0);
				return super.getChildren(root);
			} catch (final IOException e) {
				FordiacLogHelper.logWarning("Could not load plant hierarchy", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	private void setupLoadOptions() {
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
	}

	private void setupEMFInfra() {
		EPackage.Registry.INSTANCE.put(HierarchyPackage.eNS_URI, HierarchyPackage.eINSTANCE);

		// add file extension to registry
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(PLANT_HIERARCHY_FILE_NAME_EXTENSION,
				new GenericXMLResourceFactoryImpl());
		setupLoadOptions();
	}
}
