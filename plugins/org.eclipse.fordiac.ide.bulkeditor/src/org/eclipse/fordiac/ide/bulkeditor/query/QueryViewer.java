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
import org.eclipse.core.runtime.ILog;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

/** Query page of the bulk editor, which loads, saves and shows the query. */
public class QueryViewer {
	private static final String QUERY_ECORE_URI = "/org.eclipse.fordiac.ide.model/model/searchQuery.ecore"; //$NON-NLS-1$
	private static final String QUERY_FILE_EXTENSION = "query"; //$NON-NLS-1$

	private final IProject project;
	private final ComposedAdapterFactory adapterFactory;
	private final AdapterFactoryEditingDomain editingDomain;
	private final EPackage queryPackage;
	private final QueryGraphicalViewer graphicalViewer;
	private Resource queryResource;
	private EObject queryRoot;

	public QueryViewer(final Composite parent, final IProject project, final BulkEditor editor) {
		this.project = project;
		adapterFactory = createAdapterFactory();
		editingDomain = createEditingDomain(adapterFactory);
		queryPackage = loadQueryPackage(getResourceSet());

		graphicalViewer = new QueryGraphicalViewer(parent, editor, project);
		graphicalViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		graphicalViewer.createContextMenu(queryPackage, this::saveQueryToFile, this::loadQueryFromFile,
				editor::onSearchRequested);

		showQuery(createEmptyQuery());
	}

	public EObject getQueryRoot() {
		return queryRoot;
	}

	public ISelectionProvider getSelectionProvider() {
		return graphicalViewer;
	}

	public ZoomManager getZoomManager() {
		return graphicalViewer.getZoomManager();
	}

	public IPropertySheetPage createPropertySheetPage() {
		final AdapterFactoryContentProvider contentProvider = new AdapterFactoryContentProvider(adapterFactory);
		final PropertySheetPage page = new PropertySheetPage();
		// the viewer selects edit parts, the properties are the ones of their elements
		page.setPropertySourceProvider(object -> contentProvider
				.getPropertySource(object instanceof final EditPart editPart ? editPart.getModel() : object));
		return page;
	}

	public void loadQueryFromString(final String xmi) {
		if (xmi == null || xmi.isBlank()) {
			return;
		}
		final Resource restored = getResourceSet().createResource(URI.createURI("temp:/restored.query")); //$NON-NLS-1$
		try {
			restored.load(new ByteArrayInputStream(xmi.getBytes(StandardCharsets.UTF_8)), null);
		} catch (final IOException | RuntimeException e) {
			ILog.get().warn("Could not restore query model, starting empty", e); //$NON-NLS-1$
			getResourceSet().getResources().remove(restored);
			return;
		}
		if (restored.getContents().isEmpty()) {
			getResourceSet().getResources().remove(restored);
			return;
		}
		queryResource.unload();
		getResourceSet().getResources().remove(queryResource);
		showQuery(restored);
	}

	public String saveQueryToString() {
		if (queryResource.getContents().isEmpty()) {
			return null;
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			queryResource.save(out, Map.of(XMLResource.OPTION_ENCODING, StandardCharsets.UTF_8.name()));
			return out.toString(StandardCharsets.UTF_8);
		} catch (final IOException e) {
			ILog.get().warn("Could not serialize query model", e); //$NON-NLS-1$
			return null;
		}
	}

	private void saveQueryToFile() {
		final FileDialog dialog = createQueryFileDialog(SWT.SAVE, Messages.SaveQuery);
		dialog.setOverwrite(true);
		final String filePath = dialog.open();
		if (filePath == null) {
			return;
		}

		// detach the query before a resource of the same file is unloaded
		queryResource.getContents().remove(queryRoot);
		final URI fileUri = URI.createFileURI(filePath);
		final Resource existing = getResourceSet().getResource(fileUri, false);
		if (existing != null) {
			existing.unload();
			getResourceSet().getResources().remove(existing);
		}

		final Resource fileResource = getResourceSet().createResource(fileUri);
		fileResource.getContents().add(queryRoot);
		showQuery(fileResource);
		try {
			fileResource.save(null);
		} catch (final IOException e) {
			ILog.get().error("Could not save query file", e); //$NON-NLS-1$
		}
	}

	private void loadQueryFromFile() {
		final String filePath = createQueryFileDialog(SWT.OPEN, Messages.LoadQuery).open();
		if (filePath == null) {
			return;
		}

		final EList<Resource> resources = getResourceSet().getResources();
		final URI fileUri = URI.createFileURI(filePath);
		final Resource existing = getResourceSet().getResource(fileUri, false);
		final int existingIndex = existing != null ? resources.indexOf(existing) : -1;
		if (existing != null) {
			resources.remove(existing);
		}

		final Resource candidate = getResourceSet().createResource(fileUri);
		try {
			if (candidate == null) {
				throw new IOException("Could not create resource for file" + fileUri); //$NON-NLS-1$
			}
			candidate.load(getResourceSet().getLoadOptions());
			if (candidate.getContents().isEmpty()) {
				throw new IOException("Query file is empty: " + fileUri); //$NON-NLS-1$
			}
		} catch (final IOException | RuntimeException e) {
			if (candidate != null) {
				candidate.unload();
				resources.remove(candidate);
			}
			if (existing != null) {
				resources.add(Math.min(existingIndex, resources.size()), existing);
			}
			ILog.get().error(filePath, e);
			return;
		}

		if (existing != null) {
			existing.unload();
		}
		if (queryResource != existing && queryResource != candidate) {
			queryResource.unload();
			resources.remove(queryResource);
		}
		editingDomain.getCommandStack().flush();
		graphicalViewer.getEditDomain().getCommandStack().flush();
		showQuery(candidate);
	}

	private void showQuery(final Resource resource) {
		queryResource = resource;
		queryRoot = resource.getContents().get(0);
		QueryModelHelper.ensureMandatoryChildren(queryPackage, queryRoot);
		graphicalViewer.setQuery(resource);
	}

	private Resource createEmptyQuery() {
		final Resource resource = getResourceSet().createResource(URI.createURI("temp:/query.query")); //$NON-NLS-1$
		resource.getContents().add(queryPackage.getEFactoryInstance()
				.create((EClass) queryPackage.getEClassifier(QueryModelHelper.QUERY)));
		return resource;
	}

	private FileDialog createQueryFileDialog(final int style, final String title) {
		final FileDialog dialog = new FileDialog(graphicalViewer.getControl().getShell(), style);
		dialog.setText(title);
		dialog.setFilterExtensions("*." + QUERY_FILE_EXTENSION); //$NON-NLS-1$
		dialog.setFilterNames(Messages.QueryFileFilterName);
		if (project.getLocation() != null) {
			dialog.setFilterPath(project.getLocation().toOSString());
		}
		return dialog;
	}

	private ResourceSet getResourceSet() {
		return editingDomain.getResourceSet();
	}

	private static ComposedAdapterFactory createAdapterFactory() {
		final ComposedAdapterFactory factory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		factory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		factory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		factory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		return factory;
	}

	private static AdapterFactoryEditingDomain createEditingDomain(final ComposedAdapterFactory adapterFactory) {
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory,
				new BasicCommandStack(), new HashMap<>());
		final Map<String, Object> factories = domain.getResourceSet().getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		factories.put("ecore", new EcoreResourceFactoryImpl()); //$NON-NLS-1$
		factories.put(QUERY_FILE_EXTENSION, new XMIResourceFactoryImpl());
		return domain;
	}

	private static EPackage loadQueryPackage(final ResourceSet resourceSet) {
		final Resource ecoreResource = resourceSet.getResource(URI.createPlatformPluginURI(QUERY_ECORE_URI, true),
				true);
		final EPackage queryPackage = (EPackage) ecoreResource.getContents().get(0);
		resourceSet.getPackageRegistry().put(queryPackage.getNsURI(), queryPackage);
		return queryPackage;
	}
}
