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
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditor;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

/** Query page of the bulk editor, which loads, saves and shows the query. */
public class QueryViewer {
	private static final String QUERY_ECORE_URI = "/org.eclipse.fordiac.ide.model/model/searchQuery.ecore"; //$NON-NLS-1$
	private static final String QUERY_FILE_EXTENSION = "query"; //$NON-NLS-1$

	public record QuerySnapshot(String xmi, URI queryFile, boolean dirty) {
	}

	private final IProject project;
	private final BulkEditor editor;
	private final AdapterFactoryEditingDomain editingDomain;
	private final EPackage queryPackage;
	private final QueryGraphicalViewer graphicalViewer;
	private Resource queryResource;
	private EObject queryRoot;
	private boolean restoredDirty;

	public QueryViewer(final Composite parent, final IProject project, final BulkEditor editor) {
		this.project = project;
		this.editor = editor;
		editingDomain = createEditingDomain(createAdapterFactory());
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

	public CommandStack getCommandStack() {
		return graphicalViewer.getEditDomain().getCommandStack();
	}

	public boolean isDirty() {
		return restoredDirty || getCommandStack().isDirty();
	}

	public URI getQueryFile() {
		return queryResource.getURI().isFile() ? queryResource.getURI() : null;
	}

	public ZoomManager getZoomManager() {
		return graphicalViewer.getZoomManager();
	}

	public void setSubappHierarchyScope(final List<String> hierarchy) {
		final EObject place = QueryModelHelper.getContainedChild(queryRoot, QueryModelHelper.REF_PLACE);
		final CompoundCommand command = new CompoundCommand(Messages.QueryScope_SubappHierarchy);
		command.add(new ChangeQueryFeatureCommand(place, QueryModelHelper.FEATURE_SUBAPP_HIERARCHY, Boolean.TRUE));
		command.add(
				new ChangeQueryFeatureCommand(place, QueryModelHelper.FEATURE_SUBAPP_HIERARCHY_ELEMENTS, hierarchy));
		getCommandStack().execute(command);
	}

	public void restoreSnapshot(final QuerySnapshot snapshot) {
		if (snapshot == null || snapshot.xmi() == null || snapshot.xmi().isBlank()) {
			return;
		}
		final URI uri = snapshot.queryFile() != null ? snapshot.queryFile() : URI.createURI("temp:/restored.query"); //$NON-NLS-1$
		final Resource restored = getResourceSet().createResource(uri);
		if (restored == null) {
			ILog.get().warn("Could not restore query model, unsupported query file: " + uri); //$NON-NLS-1$
			return;
		}
		try {
			restored.load(new ByteArrayInputStream(snapshot.xmi().getBytes(StandardCharsets.UTF_8)), null);
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
		restoredDirty = snapshot.dirty();
	}

	public QuerySnapshot createSnapshot() {
		final String xmi = saveQueryToString();
		return xmi != null ? new QuerySnapshot(xmi, getQueryFile(), isDirty()) : null;
	}

	private String saveQueryToString() {
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

	public boolean saveQuery() throws IOException {
		if (getQueryFile() == null) {
			return saveQueryAs();
		}
		saveQueryResource();
		return true;
	}

	private void saveQueryToFile() {
		try {
			saveQueryAs();
		} catch (final IOException e) {
			ILog.get().error("Could not save query file", e); //$NON-NLS-1$
		}
	}

	private boolean saveQueryAs() throws IOException {
		final FileDialog dialog = createQueryFileDialog(SWT.SAVE, Messages.SaveQuery);
		dialog.setOverwrite(true);
		final String filePath = dialog.open();
		if (filePath == null) {
			return false;
		}
		final URI fileUri = toQueryFileUri(filePath);
		if (!fileUri.toFileString().equals(filePath) && new File(fileUri.toFileString()).exists()
				&& !MessageDialog.openQuestion(graphicalViewer.getControl().getShell(), Messages.SaveQuery,
						NLS.bind(Messages.QueryFileExists, fileUri.toFileString()))) {
			return false;
		}

		// detach the query before a resource of the same file is unloaded
		queryResource.getContents().remove(queryRoot);
		final Resource existing = getResourceSet().getResource(fileUri, false);
		if (existing != null) {
			existing.unload();
			getResourceSet().getResources().remove(existing);
		}

		final Resource fileResource = getResourceSet().createResource(fileUri);
		fileResource.getContents().add(queryRoot);
		showQuery(fileResource);
		saveQueryResource();
		return true;
	}

	private static URI toQueryFileUri(final String filePath) {
		final URI fileUri = URI.createFileURI(filePath);
		return QUERY_FILE_EXTENSION.equals(fileUri.fileExtension()) ? fileUri
				: fileUri.appendFileExtension(QUERY_FILE_EXTENSION);
	}

	private void saveQueryResource() throws IOException {
		queryResource.save(null);
		getCommandStack().markSaveLocation();
		restoredDirty = false;
	}

	private void loadQueryFromFile() {
		if (!editor.promptToSaveQuery()) {
			return;
		}
		final String filePath = createQueryFileDialog(SWT.OPEN, Messages.LoadQuery).open();
		if (filePath != null) {
			loadQuery(URI.createFileURI(filePath));
		}
	}

	/**
	 * Shows the query of the query file instead of the current query, without
	 * asking to save the current query.
	 */
	public void loadQuery(final URI fileUri) {
		final EList<Resource> resources = getResourceSet().getResources();
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
			ILog.get().error(fileUri.toFileString(), e);
			return;
		}

		if (existing != null) {
			existing.unload();
		}
		if (queryResource != existing && queryResource != candidate) {
			queryResource.unload();
			resources.remove(queryResource);
		}
		getCommandStack().flush();
		showQuery(candidate);
		restoredDirty = false;
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
