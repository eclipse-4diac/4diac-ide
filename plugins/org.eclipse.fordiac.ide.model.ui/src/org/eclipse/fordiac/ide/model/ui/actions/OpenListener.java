/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, fortiss GmbH, Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *               - added helper functions for breadcrumb based editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

/**
 * Helper class for reducing the effort to implement open listeners
 */
public abstract class OpenListener implements IOpenListener {

	private IEditorPart editor = null;

	private static final String SUBAPP_TYPE_EDITOR = "org.eclipse.fordiac.ide.subapptypeeditor.SubAppTypeEditor"; //$NON-NLS-1$
	private static final String FB_TYPE_EDITOR = "org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor"; //$NON-NLS-1$

	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
		// nothing to do
	}

	@Override
	public IEditorPart getOpenedEditor() {
		return editor;
	}

	@Override
	public final Action getOpenListenerAction() {
		return new OpenListenerAction(this);
	}

	protected void openEditor(final IEditorInput input, final String editorId) {
		editor = EditorUtils.openEditor(input, editorId);
	}

	protected void openInBreadCrumbEditor(final IFile file, final String editorId, final EObject element) {
		openEditor(new FileEditorInput(file), editorId);
		final IEditorPart openedEditor = getOpenedEditor();
		if (null != openedEditor) {
			final AbstractBreadCrumbEditor breadCrumbEditor = getBreadCrumbEditor(openedEditor);
			final EObject elementToOpen = getElementToOpen(openedEditor, element);
			if (breadCrumbEditor != null && elementToOpen != null) {
				if (sameLevelAsParent(elementToOpen)) {
					breadCrumbEditor.getBreadcrumb().setInput(elementToOpen.eContainer().eContainer());
					HandlerHelper.selectElement(elementToOpen, openedEditor);
				} else {
					breadCrumbEditor.getBreadcrumb().setInput(elementToOpen);
				}
			}
		}
	}

	protected void openInSubappTypeEditor(final SubAppType root, final EObject element) {
		openInBreadCrumbEditor(root.getTypeEntry().getFile(), SUBAPP_TYPE_EDITOR, element);
	}

	protected void openInFBTypeEditor(final FBType root, final EObject element) {
		openInBreadCrumbEditor(root.getTypeEntry().getFile(), FB_TYPE_EDITOR, element);
	}

	public static AbstractBreadCrumbEditor getBreadCrumbEditor(final IEditorPart openedEditor) {
		final AbstractBreadCrumbEditor breadCrumbEditor = openedEditor.getAdapter(AbstractBreadCrumbEditor.class);
		if ((breadCrumbEditor != null)
				&& (breadCrumbEditor.getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite)) {
			final MultiPageEditorPart multiPageEditor = multiPageEditorSite.getMultiPageEditor();
			if (multiPageEditor.getSelectedPage() != breadCrumbEditor) {
				multiPageEditor.setActiveEditor(breadCrumbEditor);
			}
		}
		return breadCrumbEditor;
	}

	private static boolean sameLevelAsParent(final Object element) {
		return element instanceof Group;
	}

	public static EObject getElementToOpen(final IEditorPart openedEditor, final EObject element) {
		final LibraryElement editorLibElement = openedEditor.getAdapter(LibraryElement.class);
		if (editorLibElement == null) {
			return null;
		}

		if (editorLibElement == EcoreUtil.getRootContainer(element)) {
			// we are in the same model we can safely use element
			return element;
		}
		final URI elementURI = EcoreUtil.getURI(element);
		return editorLibElement.eResource().getEObject(elementURI.fragment());
	}

}
