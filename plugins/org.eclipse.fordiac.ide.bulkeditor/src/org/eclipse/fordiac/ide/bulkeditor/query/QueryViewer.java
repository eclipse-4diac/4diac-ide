/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.query;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class QueryViewer {
	private static final String QUERY_ECORE_URI = "/org.eclipse.fordiac.ide.model/model/searchQuery.ecore"; //$NON-NLS-1$
	private static final String QUERY_FILE_NAME = "/testQuery.query"; //$NON-NLS-1$

	private ComposedAdapterFactory queryAdapterFactory;
	private AdapterFactoryEditingDomain queryEditingDomain;
	private Resource queryResource;
	private EObject queryRoot;
	private EPackage queryPackage;
	private final IProject project;
	private QueryZestGraphViewer graphViewer;

	public QueryViewer(final Composite parent, final IProject project) {
		this.project = project;
		createQueryViewer(parent);
	}

	private void createQueryViewer(final Composite parent) {
		graphViewer = new QueryZestGraphViewer(parent, getOrCreateQueryEditingDomain());
		loadQueryModel();
		graphViewer.addContextMenu(queryPackage);
		graphViewer.setSaveLoadCallbacks(this::saveQueryToXmi, this::loadQueryFromXmi);

		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		graphViewer.getGraphViewer().getGraphControl().setLayoutData(gd);

		loadQueryFromXmi();
	}

	private ComposedAdapterFactory getQueryAdapterFactory() {
		if (queryAdapterFactory == null) {
			queryAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			queryAdapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
			queryAdapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			queryAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		}
		return queryAdapterFactory;
	}

	private AdapterFactoryEditingDomain getOrCreateQueryEditingDomain() {
		if (queryEditingDomain != null) {
			return queryEditingDomain;
		}
		queryEditingDomain = new AdapterFactoryEditingDomain(getQueryAdapterFactory(), new BasicCommandStack(),
				new HashMap<>());
		final ResourceSet resourceSet = queryEditingDomain.getResourceSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("query", //$NON-NLS-1$
				new XMIResourceFactoryImpl());
		return queryEditingDomain;
	}

	private void loadQueryModel() {
		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final URI ecoreUri = URI.createPlatformPluginURI(QUERY_ECORE_URI, true);
		final Resource ecoreResource = resourceSet.getResource(ecoreUri, true);
		queryPackage = (EPackage) ecoreResource.getContents().get(0);
		resourceSet.getPackageRegistry().put(queryPackage.getNsURI(), queryPackage);
	}

	private void saveQueryToXmi() {
		if (queryResource == null || queryRoot == null) {
			return;
		}
		try {
			queryResource.save(null);
		} catch (final IOException ex) {
			// TODO error-handling
		}
	}

	private void loadQueryFromXmi() {
		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final URI xmiUri = URI.createPlatformResourceURI(project.getName() + QUERY_FILE_NAME, true);

		final Resource existing = resourceSet.getResource(xmiUri, false);
		if (existing != null) {
			existing.unload();
			resourceSet.getResources().remove(existing);
		}

		try {
			queryResource = resourceSet.getResource(xmiUri, true);
			queryRoot = queryResource.getContents().get(0);
		} catch (final Exception e) {
			final EClass queryClass = (EClass) queryPackage.getEClassifier(QueryModelHelper.QUERY);
			queryRoot = queryPackage.getEFactoryInstance().create(queryClass);
			queryResource = resourceSet.createResource(xmiUri);
			queryResource.getContents().add(queryRoot);
		}
		graphViewer.setInput(queryResource);
	}

	public ISelectionProvider getSelectionProvider() {
		return graphViewer.getGraphViewer();
	}

	public IPropertySheetPage createPropertySheetPage() {
		final PropertySheetPage page = new PropertySheetPage();
		page.setPropertySourceProvider(new AdapterFactoryContentProvider(getQueryAdapterFactory()));
		return page;
	}
}
