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
 *   	- initial API and implementation and/or initial documentation
 *   Mario Kastner
 *   	- redesign of manifest editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;

public class ManifestEditor extends FormEditor implements IGotoMarker {

	private final IUndoContext undoContext = new ObjectUndoContext(this);

	private static final String DEPENDENCY_PAGE_ID = "fordiac.ide.library.ui.editors.manifestEditorDependencyPage"; //$NON-NLS-1$
	private static final String PRODUCT_PAGE_ID = "fordiac.ide.library.ui.editors.manifestEditorProductPage"; //$NON-NLS-1$

	private Manifest manifest;
	private IProject project;

	private IUndoableOperation savePosition;

	@Override
	protected void addPages() {
		loadManifest();

		savePosition = null;

		final var dependencyPage = new ManifestEditorDependencyPage(this, DEPENDENCY_PAGE_ID, "Dependencies"); //$NON-NLS-1$
		final var productPage = new ManifestEditorProductPage(this, PRODUCT_PAGE_ID, "Product"); //$NON-NLS-1$

		try {
			int index = addPage(productPage);
			setPageText(index, productPage.getTitle());
			setPageImage(index, productPage.getTitleImage());

			index = addPage(dependencyPage);
			setPageText(index, dependencyPage.getTitle());
			setPageImage(index, dependencyPage.getTitleImage());
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (!canSave()) {
			getInvalidPage().ifPresent(p -> setActivePage(p.getId()));
			getActivePageInstance().getManagedForm().getMessageManager().update();
			return;
		}
		ManifestHelper.saveManifest(manifest);
		savePosition = OperationHistoryFactory.getOperationHistory().getUndoOperation(getUndoContext());
		firePropertyChange(PROP_DIRTY);
	}

	public void execute(final IUndoableOperation operation) {
		operation.addContext(getUndoContext());
		try {
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
		} catch (final ExecutionException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	public IUndoContext getUndoContext() {
		return undoContext;
	}

	private Optional<ManifestEditorPage<EObject>> getInvalidPage() {
		return getPages().filter(Predicate.not(ManifestEditorPage::isValid)).findFirst();
	}

	private void loadManifest() {
		if (getEditorInput() instanceof final FileEditorInput input) {
			manifest = ManifestHelper.getManifest(input.getFile());
		}
	}

	private Stream<ManifestEditorPage<EObject>> getPages() {
		return pages.stream().filter(ManifestEditorPage.class::isInstance).map(ManifestEditorPage.class::cast);
	}

	private boolean canSave() {
		return getPages().allMatch(ManifestEditorPage::isValid);
	}

	@Override
	public boolean isDirty() {
		return OperationHistoryFactory.getOperationHistory().getUndoOperation(getUndoContext()) != savePosition;
	}

	@Override
	public void doSaveAs() {
		// Save As is not supported.
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public Manifest getManifest() {
		return manifest;
	}

	public IProject getProject() {
		if (project == null && getEditorInput() instanceof final FileEditorInput input && input.getFile() != null) {
			project = input.getFile().getProject();
		}
		return project;
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		final EObject markerElement = resolveModelElement(marker);

		if (markerElement == null) {
			return;
		}

		getPages().filter(p -> p.containsElement(markerElement)).findFirst().ifPresent(p -> {
			setActivePage(p.getId());
			p.reveal(markerElement);
		});
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(buildListener, IResourceChangeEvent.POST_BUILD);
		OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(operationHistoryListener);
	}

	@Override
	public void dispose() {
		OperationHistoryFactory.getOperationHistory().removeOperationHistoryListener(operationHistoryListener);
		OperationHistoryFactory.getOperationHistory().dispose(getUndoContext(), true, true, true);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(buildListener);
		super.dispose();
	}

	private EObject resolveModelElement(final IMarker marker) {
		final URI targetUri = FordiacErrorMarker.getTargetUri(marker);
		final Resource resource = manifest != null ? manifest.eResource() : null;

		if (resource == null || targetUri == null || !targetUri.hasFragment()) {
			return null;
		}

		if (!targetUri.trimFragment().equals(resource.getURI())) {
			return null;
		}

		return resource.getEObject(targetUri.fragment());
	}

	private final IOperationHistoryListener operationHistoryListener = event -> {
		final IUndoableOperation operation = event.getOperation();

		if (operation != null && operation.hasContext(undoContext) && switch (event.getEventType()) {
		case OperationHistoryEvent.OPERATION_ADDED, OperationHistoryEvent.OPERATION_REMOVED,
				OperationHistoryEvent.UNDONE, OperationHistoryEvent.REDONE ->
			true;
		default -> false;
		}) {
			firePropertyChange(PROP_DIRTY);
		}
	};

	private final IResourceChangeListener buildListener = new IResourceChangeListener() {
		private final IPath externalLibPath = new Path(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		private final IPath stdLibPath = new Path(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);

		@Override
		public void resourceChanged(final IResourceChangeEvent event) {
			final IResourceDelta rootDelta = event.getDelta();
			if (rootDelta == null || getProject() == null) {
				return;
			}

			final IResourceDelta projectDelta = rootDelta.findMember(getProject().getFullPath());
			if (projectDelta == null || projectDelta.findMember(externalLibPath) == null
					&& projectDelta.findMember(stdLibPath) == null) {
				return;
			}

			final Display display = getSite().getShell().getDisplay();

			if (display.isDisposed()) {
				return;
			}

			display.asyncExec(() -> {
				if (getContainer().isDisposed()) {
					return;
				}

				getPages().forEach(ManifestEditorPage::refresh);
			});
		}
	};

}
