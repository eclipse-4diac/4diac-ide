/*******************************************************************************
 * Copyright (c) 2014, 2021 fortiss GmbH, Primetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added own selection provider to correctly handle selection
 *                 inside of the SubAppNetworkBreadCrumbEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.subapptypeeditor.editors.SubAppNetworkBreadCrumbEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.MultiPageSelectionProvider;

public class SubAppTypeEditor extends FBTypeEditor {

	private static final int SUBAPP_NETWORL_EDITOR_INDEX = 1;

	/**
	 * reworked FormEditorSelection provider allowing to change the current
	 * multipage editor see
	 * {@link org.eclipse.ui.forms.editor.FormEditor.FormEditorSelectionProvider}
	 */
	private static class SubAppTypeEditorSelectionProvider extends MultiPageSelectionProvider {
		private ISelection globalSelection;

		private FormEditor currentFormEditor;

		@Override
		public FormEditor getMultiPageEditor() {
			return currentFormEditor;
		}

		public void setMultiPageEditor(final FormEditor formEditor) {
			currentFormEditor = formEditor;
		}

		/** @param formEditor the editor */
		public SubAppTypeEditorSelectionProvider(final FormEditor formEditor) {
			super(formEditor);
			currentFormEditor = formEditor;
		}

		@Override
		public ISelection getSelection() {
			final IEditorPart activeEditor = getMultiPageEditor().getActiveEditor();
			if (activeEditor != null) {
				final ISelectionProvider selectionProvider = activeEditor.getSite().getSelectionProvider();
				if (selectionProvider != null) {
					return selectionProvider.getSelection();
				}
			}
			if (globalSelection != null) {
				return globalSelection;
			}
			return StructuredSelection.EMPTY;
		}

		@Override
		public void setSelection(final ISelection selection) {
			final IEditorPart activeEditor = getMultiPageEditor().getActiveEditor();
			if (activeEditor != null) {
				final ISelectionProvider selectionProvider = activeEditor.getSite().getSelectionProvider();
				if (selectionProvider != null) {
					selectionProvider.setSelection(selection);
				}
			} else {
				this.globalSelection = selection;
				fireSelectionChanged(new SelectionChangedEvent(this, globalSelection));
			}
		}
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		site.setSelectionProvider(new SubAppTypeEditorSelectionProvider(this));
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		if ((SUBAPP_NETWORL_EDITOR_INDEX == getCurrentPage()) && (newPageIndex != SUBAPP_NETWORL_EDITOR_INDEX)) {
			// the SubAppNetworkBreadCrumbEditor is open restore our editor as the selection
			// editor
			getSelectionProvider().setMultiPageEditor(this);
		} else if ((SUBAPP_NETWORL_EDITOR_INDEX != getCurrentPage()) && (newPageIndex == SUBAPP_NETWORL_EDITOR_INDEX)) {
			// the SubAppNetworkBreadCrumbEditor is about to open set the subappnetwork
			// editor as multipage editor
			getSelectionProvider()
					.setMultiPageEditor((SubAppNetworkBreadCrumbEditor) getEditor(SUBAPP_NETWORL_EDITOR_INDEX));
		}
		super.pageChange(newPageIndex);
	}

	private SubAppTypeEditorSelectionProvider getSelectionProvider() {
		return (SubAppTypeEditorSelectionProvider) getSite().getSelectionProvider();
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == AbstractBreadCrumbEditor.class) {
			return key.cast(getEditor(SUBAPP_NETWORL_EDITOR_INDEX));
		}
		return super.getAdapter(key);
	}

}
