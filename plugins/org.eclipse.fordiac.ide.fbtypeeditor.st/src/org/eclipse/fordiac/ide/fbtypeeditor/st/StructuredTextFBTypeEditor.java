/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.st;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeXtextEditor;
import org.eclipse.fordiac.ide.model.edit.TypeEntryAdapter;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreSaveActionsEditor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocument;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocumentUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreEditorPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreNestedEditor;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;

import com.google.inject.Inject;

public class StructuredTextFBTypeEditor extends FBTypeXtextEditor
		implements STCoreSaveActionsEditor, STCoreNestedEditor {

	@Inject
	private STCoreMapper algorithmMapper;

	@Inject
	private ILocationInFileProvider locationProvider;

	@Inject
	private LibraryElementXtextDocumentUpdater fbTypeUpdater;

	@Inject
	private STCoreEditorPreferences editorPreferences;

	@Override
	protected void installFBTypeUpdater() {
		if (getDocument() instanceof final LibraryElementXtextDocument libraryElementXtextDocument) {
			fbTypeUpdater.install(libraryElementXtextDocument);
		}
	}

	@Override
	protected void removeFBTypeUpdater() {
		fbTypeUpdater.uninstall();
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		super.doSetInput(input);
		setPartName(FordiacMessages.Algorithm);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
	}

	@Override
	public void reloadType() {
		removeFBTypeUpdater();
		if (getDocument() instanceof final LibraryElementXtextDocument libraryElementXtextDocument) {
			libraryElementXtextDocument.internalModify(state -> {
				((LibraryElementXtextResource) state).setLibraryElement(getType());
				return null;
			});
		}
		super.reloadType();
		installFBTypeUpdater();
	}

	@Override
	protected boolean selectAndReveal(final Object element, final boolean revealEditor) {
		final ITextRegion location = getDocument().priorityReadOnly(resource -> {
			if (resource != null) {
				final EObject object = algorithmMapper.fromModel(resource, element);
				if (object != null) {
					return locationProvider.getSignificantTextRegion(object);
				}
			}
			return null;
		});
		if (location != null) {
			selectAndReveal(location.getOffset(), location.getLength());
			if (revealEditor) {
				revealEditor();
			}
		}
		return location != null;
	}

	@Override
	protected boolean shouldEnablePerformanceMode() {
		return getDocument().getNumberOfLines() > editorPreferences.getPerformanceModeThreshold();
	}

	@Override
	public String getEditorId() {
		return getLanguageName();
	}

	private boolean saveActionsDisabled;

	@Override
	public void doRevertToSaved() {
		setSaveActionsDisabled(true);
		try {
			super.doRevertToSaved();
		} finally {
			setSaveActionsDisabled(false);
		}
	}

	@Override
	public boolean isSaveActionsDisabled() {
		return saveActionsDisabled;
	}

	public void setSaveActionsDisabled(final boolean saveActionsDisabled) {
		this.saveActionsDisabled = saveActionsDisabled;
	}

	@Override
	public void setBlockUpdates(final boolean block) {
		final TypeEntryAdapter typeEntryAdapter = getTypeEntryAdapter();
		if (typeEntryAdapter != null) {
			typeEntryAdapter.setBlockUpdates(block);
		}
	}

	@Override
	public void doSaveOuterEditor(final IProgressMonitor monitor) {
		if (getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			multiPageEditorSite.getMultiPageEditor().doSave(monitor);
		}
	}
}
