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
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
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
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;

public class SystemExplorerLinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(final IEditorInput anInput) {
		final IWorkbenchPage aPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IEditorPart editor = aPage.findEditor(anInput);
		if (editor != null) {
			if (editor.getAdapter(FBType.class) != null) {
				return handleTypeEditor(editor, anInput);
			}
			final FBNetwork fbNetwork = editor.getAdapter(FBNetwork.class);
			if (fbNetwork != null) {
				return new StructuredSelection(fbNetwork.eContainer());
			}
			final FBNetworkElement fbElem = editor.getAdapter(FBNetworkElement.class);
			if (fbElem != null) {
				return new StructuredSelection(fbElem);
			}
			final SystemConfiguration sysConf = editor.getAdapter(SystemConfiguration.class);
			if (sysConf != null) {
				return new StructuredSelection(sysConf);
			}
		}
		// if we didn't find a suitable editor try to at least highlight the file
		return defaultFileSelection(anInput);
	}

	@Override
	public void activateEditor(final IWorkbenchPage aPage, final IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}
		final Object sel = aSelection.getFirstElement();
		if (sel instanceof final EObject eObj) {
			handleModelElementSelection(aPage, eObj);
		}

		if (sel instanceof final IFile iFile) {
			handleFileSelection(aPage, iFile);
		}
	}

	private static IStructuredSelection handleTypeEditor(final IEditorPart editor, final IEditorInput anInput) {
		final IEditorPart activeEditor = ((FormEditor) editor).getActiveEditor();

		final InterfaceList il = activeEditor.getAdapter(InterfaceList.class);
		if (il != null) {
			return new StructuredSelection(il);
		}
		final Service service = activeEditor.getAdapter(Service.class);
		if (service != null) {
			return new StructuredSelection(service);
		}
		final ECC ecc = activeEditor.getAdapter(ECC.class);
		if (ecc != null) {
			return new StructuredSelection(ecc);
		}

		final FBNetwork fbNetwork = activeEditor.getAdapter(FBNetwork.class);
		if (fbNetwork != null) {
			if (fbNetwork.eContainer() instanceof FBType) {
				return new StructuredSelection(fbNetwork);
			}
			return new StructuredSelection(fbNetwork.eContainer());
		}
		// if we didn't find a suitable editor try to at least highlight the file
		return defaultFileSelection(anInput);
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
			final EObject elementToOpen = OpenListener.getElementToOpen(editor, sel);
			if (breadCrumbEditor != null && elementToOpen != null) {
				final EObject breadCrumbRef = getBreadCrumbRefElement(elementToOpen);
				breadCrumbEditor.getBreadcrumb().setInput(breadCrumbRef);
				if (breadCrumbRef != elementToOpen || elementToOpen instanceof Device) {
					if (elementToOpen instanceof final SubApp subapp && subapp.isUnfolded()) {
						HandlerHelper.showExpandedSubapp(subapp, editor);
					} else {
						HandlerHelper.selectElement(elementToOpen, editor);
					}
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
		if (root instanceof final LibraryElement libElem) {
			return libElem.getTypeEntry().getFile();
		}
		return null;
	}

	private static EObject getBreadCrumbRefElement(final EObject sel) {
		EObject refElement = sel;
		if ((sel instanceof final FBNetworkElement fbnEl && fbnEl.getType() != null) || (sel instanceof Group)) {
			refElement = sel.eContainer().eContainer();
		}
		// For unfolded subapps find the next parent that is not expanded as refElement
		while (refElement instanceof final SubApp subApp && subApp.isUnfolded()) {
			refElement = refElement.eContainer().eContainer();
		}
		return refElement;
	}

	private static IStructuredSelection defaultFileSelection(final IEditorInput anInput) {
		final IFile file = ResourceUtil.getFile(anInput);
		if (file != null) {
			return new StructuredSelection(file);
		}

		return StructuredSelection.EMPTY;
	}

}
