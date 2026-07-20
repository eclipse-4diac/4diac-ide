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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

public class ManifestEditor extends FormEditor {

	private static final String DEPENDENCY_PAGE_ID = "fordiac.ide.library.ui.editors.manifestEditorDependencyPage"; //$NON-NLS-1$

	private Manifest manifest;
	private boolean isDirty;

	@Override
	protected void addPages() {
		loadManifest();
		isDirty = false;
		final ManifestEditorDependencyPage dependencyPage = new ManifestEditorDependencyPage(this, DEPENDENCY_PAGE_ID,
				"Dependencies"); //$NON-NLS-1$

		try {
			final int index = addPage(dependencyPage);
			setPageText(index, dependencyPage.getTitle());
			setPageImage(index, dependencyPage.getTitleImage());
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (isDirty() && canSave()) {
			ManifestHelper.saveManifest(manifest);
			setDirty(false);
		}
	}

	private void loadManifest() {
		if (getEditorInput() instanceof final FileEditorInput input) {
			manifest = ManifestHelper.getManifest(input.getFile());
		}
	}

	private boolean canSave() {
		return manifest != null && manifest.getDependencies() != null && manifest.getDependencies().getRequired()
				.stream().map(Required::getVersion).allMatch(VersionComparator::isValidRange);
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(final boolean dirty) {
		if (isDirty != dirty) {
			isDirty = dirty;
			firePropertyChange(PROP_DIRTY);
		}
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
}