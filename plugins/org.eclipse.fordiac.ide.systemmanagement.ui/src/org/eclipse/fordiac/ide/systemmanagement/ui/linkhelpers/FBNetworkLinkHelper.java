/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListener;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;

public class FBNetworkLinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(final IEditorInput anInput) {
		final IWorkbenchPage aPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IEditorPart editor = aPage.findEditor(anInput);
		if (editor != null) {
			final FBNetwork fbNetwork = editor.getAdapter(FBNetwork.class);
			if (fbNetwork != null) {
				if (fbNetwork.eContainer() instanceof FBType) {
					return new StructuredSelection(fbNetwork);
				}
				return new StructuredSelection(fbNetwork.eContainer());
			}
			final FBNetworkElement fbElem = editor.getAdapter(FBNetworkElement.class);
			if (fbElem != null) {
				return new StructuredSelection(fbElem);
			}
		}
		// if we didn't find a suitable editor try to at least highlight the file
		final IFile file = ResourceUtil.getFile(anInput);
		if (file != null) {
			return new StructuredSelection(file);
		}

		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(final IWorkbenchPage aPage, final IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}
		final Object sel = aSelection.getFirstElement();
		if (sel instanceof EObject) {
			handleModelElementSelection(aPage, (EObject) sel);
		}

		if (sel instanceof IFile) {
			handleFileSelection(aPage, (IFile) aSelection.getFirstElement());
		}

	}

	private static void handleFileSelection(final IWorkbenchPage aPage, final IFile aFile) {
		if (SystemManager.isSystemFile(aFile)) {
			handleModelElementSelection(aPage, SystemManager.INSTANCE.getSystem(aFile));
		}
	}

	private static void handleModelElementSelection(final IWorkbenchPage aPage, final EObject sel) {
		final IEditorPart editor = getRootEditor(aPage, sel);
		if (editor != null) {
			final AbstractBreadCrumbEditor breadCrumbEditor = OpenListener.getBreadCrumbEditor(editor);
			if (breadCrumbEditor != null) {
				final EObject breadCrumbRef = getBreadCrumbRefElement(sel);
				breadCrumbEditor.getBreadcrumb().setInput(breadCrumbRef);
				if (breadCrumbRef != sel || sel instanceof Device) {
					HandlerHelper.selectElement(sel, editor);
				}
			}
		}
	}

	private static IEditorPart getRootEditor(final IWorkbenchPage aPage, final EObject sel) {
		final IFile modelFile = getFileForModel(sel);
		if (modelFile != null) {
			final IEditorInput fileInput = new FileEditorInput(modelFile);
			final IEditorPart editor = aPage.findEditor(fileInput);
			if (editor != null) {
				aPage.bringToTop(editor);
				return editor;
			}
		}
		return null;
	}

	private static IFile getFileForModel(final EObject sel) {
		final EObject root = EcoreUtil.getRootContainer(sel);
		if (root instanceof AutomationSystem) {
			return ((AutomationSystem) root).getSystemFile();
		} else if (root instanceof FBType) {
			return ((FBType) root).getTypeEntry().getFile();
		}
		return null;
	}

	private static EObject getBreadCrumbRefElement(final EObject sel) {
		if ((sel instanceof FBNetworkElement && ((FBNetworkElement) sel).getType() != null) || (sel instanceof Group)) {
			return sel.eContainer().eContainer();
		}
		return sel;
	}

}
