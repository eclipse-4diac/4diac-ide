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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class QueryViewer {
	private static final String QUERY_ECORE_URI = "/org.eclipse.fordiac.ide.model/model/searchQuery.ecore"; //$NON-NLS-1$

	private ComposedAdapterFactory queryAdapterFactory;
	private AdapterFactoryEditingDomain queryEditingDomain;
	private Resource queryResource;
	private EObject queryRoot;
	private EPackage queryPackage;
	private final IProject project;
	private QueryZestGraphViewer graphViewer;

	public QueryViewer(final Composite parent, final IProject project, final BulkEditor editor) {
		this.project = project;
		createQueryViewer(parent, editor);
	}

	private void createQueryViewer(final Composite parent, final BulkEditor editor) {
		graphViewer = new QueryZestGraphViewer(parent, getOrCreateQueryEditingDomain());
		graphViewer.setProject(project);
		loadQueryModel();
		graphViewer.addContextMenu(queryPackage);
		graphViewer.setSaveLoadCallbacks(this::saveQueryToXmi, this::loadQueryFromXmi);
		graphViewer.setSearchCallback(editor::onSearchRequested);

		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		graphViewer.getGraphViewer().getGraphControl().setLayoutData(gd);

		initializeEmptyQuery();
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

	public EObject getQueryRoot() {
		return queryRoot;
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

	private void initializeEmptyQuery() {
		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final EClass queryClass = (EClass) queryPackage.getEClassifier(QueryModelHelper.QUERY);
		queryRoot = queryPackage.getEFactoryInstance().create(queryClass);
		QueryModelHelper.ensureMandatoryChildren(queryPackage, queryRoot);
		final URI tempUri = URI.createURI("temp:/query.query"); //$NON-NLS-1$
		queryResource = resourceSet.createResource(tempUri);
		queryResource.getContents().add(queryRoot);
		graphViewer.setInput(queryResource);
	}

	private void saveQueryToXmi() {
		if (queryRoot == null) {
			return;
		}

		final Shell shell = graphViewer.getGraphViewer().getGraphControl().getShell();
		final FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText(Messages.SaveQuery);
		dialog.setFilterExtensions("*.query"); //$NON-NLS-1$
		dialog.setFilterNames(Messages.QueryFileFilterName);
		dialog.setOverwrite(true);

		final String projectPath = project.getLocation() != null ? project.getLocation().toOSString() : null;
		if (projectPath != null) {
			dialog.setFilterPath(projectPath);
		}

		final String filePath = dialog.open();
		if (filePath == null) {
			return;
		}

		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final URI fileUri = URI.createFileURI(filePath);

		if (queryResource != null && queryResource.getContents().contains(queryRoot)) {
			queryResource.getContents().remove(queryRoot);
		}

		final Resource existing = resourceSet.getResource(fileUri, false);
		if (existing != null) {
			existing.unload();
			resourceSet.getResources().remove(existing);
		}

		queryResource = resourceSet.createResource(fileUri);
		queryResource.getContents().add(queryRoot);

		try {
			queryResource.save(null);
		} catch (final IOException ex) {
			FordiacLogHelper.logError("Could not save query file", ex); //$NON-NLS-1$
		}
	}

	public void loadQueryFromString(final String xmi) {
		if (xmi == null || xmi.isBlank()) {
			return;
		}
		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final Resource restored = resourceSet.createResource(URI.createURI("temp:/restored.query")); //$NON-NLS-1$
		try {
			restored.load(new ByteArrayInputStream(xmi.getBytes(StandardCharsets.UTF_8)), null);
		} catch (final IOException | RuntimeException e) {
			FordiacLogHelper.logWarning("Could not restore query model, starting empty", e); //$NON-NLS-1$
			resourceSet.getResources().remove(restored);
			return;
		}
		if (restored.getContents().isEmpty()) {
			resourceSet.getResources().remove(restored);
			return;
		}
		if (queryResource != null) {
			queryResource.unload();
			resourceSet.getResources().remove(queryResource);
		}
		queryResource = restored;
		queryRoot = queryResource.getContents().get(0);
		QueryModelHelper.ensureMandatoryChildren(queryPackage, queryRoot);
		graphViewer.setInput(queryResource);
	}

	public String saveQueryToString() {
		if (queryResource == null || queryResource.getContents().isEmpty()) {
			return null;
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			queryResource.save(out, Map.of(XMLResource.OPTION_ENCODING, StandardCharsets.UTF_8.name()));
			return out.toString(StandardCharsets.UTF_8);
		} catch (final IOException e) {
			FordiacLogHelper.logError("Could not serialize query model", e); //$NON-NLS-1$
			return null;
		}
	}

	private void loadQueryFromXmi() {
		final Shell shell = graphViewer.getGraphViewer().getGraphControl().getShell();
		final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(Messages.LoadQuery);
		dialog.setFilterExtensions("*.query"); //$NON-NLS-1$
		dialog.setFilterNames(Messages.QueryFileFilterName);

		final String projectPath = project.getLocation() != null ? project.getLocation().toOSString() : null;
		if (projectPath != null) {
			dialog.setFilterPath(projectPath);
		}

		final String filePath = dialog.open();
		if (filePath == null) {
			return;
		}

		final ResourceSet resourceSet = getOrCreateQueryEditingDomain().getResourceSet();
		final URI fileUri = URI.createFileURI(filePath);

		final Resource existing = resourceSet.getResource(fileUri, false);
		if (existing != null) {
			existing.unload();
			resourceSet.getResources().remove(existing);
		}

		try {
			queryResource = resourceSet.getResource(fileUri, true);
			queryRoot = queryResource.getContents().get(0);
		} catch (final Exception e) {
			final EClass queryClass = (EClass) queryPackage.getEClassifier(QueryModelHelper.QUERY);
			queryRoot = queryPackage.getEFactoryInstance().create(queryClass);
			queryResource = resourceSet.createResource(fileUri);
			queryResource.getContents().add(queryRoot);
		}
		QueryModelHelper.ensureMandatoryChildren(queryPackage, queryRoot);

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