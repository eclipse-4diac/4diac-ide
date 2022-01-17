/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class EvaluatorDebugModelPresentation implements IDebugModelPresentation {

	@Override
	public IEditorInput getEditorInput(Object element) {
		if (element instanceof FBType) {
			FBType fbType = (FBType) element;
			return new FBTypeEditorInput(fbType, fbType.getPaletteEntry());
		} else if (element instanceof Resource) {
			Resource resource = (Resource) element;
			URI uri = resource.getURI();
			if (uri.isPlatformResource()) {
				String path = uri.toPlatformString(true);
				IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
				if (workspaceResource instanceof IFile) {
					return new FileEditorInput((IFile) workspaceResource);
				}
			} else {
				return new URIEditorInput(uri);
			}
		}
		return null;
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		if (input instanceof FileEditorInput) {
			String fileName = ((FileEditorInput) input).getFile().getName();
			IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(fileName);
			if (editorDescriptor != null) {
				return editorDescriptor.getId();
			}
		} else if (input instanceof URIEditorInput) {
			URI uri = ((URIEditorInput) input).getURI();
			IEditorDescriptor editorDescriptor = EditUIUtil.getDefaultEditor(uri, null);
			if (editorDescriptor != null) {
				return editorDescriptor.getId();
			}
		} else if (input instanceof FBTypeEditorInput) {
			String fileName = ((FBTypeEditorInput) input).getPaletteEntry().getFile().getName();
			IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(fileName);
			if (editorDescriptor != null) {
				return editorDescriptor.getId();
			}
		}
		return null;
	}

	@Override
	public void setAttribute(String attribute, Object value) {
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		return null;
	}

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}
}
