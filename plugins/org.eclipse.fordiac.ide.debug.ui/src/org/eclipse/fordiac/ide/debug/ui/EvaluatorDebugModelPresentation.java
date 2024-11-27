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
package org.eclipse.fordiac.ide.debug.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.preferences.FordiacDebugPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typeeditor.TypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class EvaluatorDebugModelPresentation implements IDebugModelPresentation {

	@Override
	public IEditorInput getEditorInput(final Object element) {
		if (element instanceof final EObject object) {
			final EObject root = EcoreUtil.getRootContainer(object);
			if (root instanceof final FBType fbType) {
				return new TypeEditorInput(fbType, fbType.getTypeEntry());
			}
			return getEditorInput(((EObject) element).eResource());
		}
		if (element instanceof final Resource resource) {
			final URI uri = resource.getURI();
			if (!uri.isPlatformResource()) {
				return new URIEditorInput(uri);
			}
			final String path = uri.toPlatformString(true);
			final IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
			if (workspaceResource instanceof final IFile file) {
				return new FileEditorInput(file);
			}
		}
		return null;
	}

	@Override
	public String getEditorId(final IEditorInput input, final Object element) {
		if (input instanceof final IFileEditorInput fileEditorInput) {
			final String fileName = fileEditorInput.getFile().getName();
			final IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(fileName);
			if (editorDescriptor != null) {
				return editorDescriptor.getId();
			}
		} else if (input instanceof final URIEditorInput uriEditorInput) {
			final URI uri = uriEditorInput.getURI();
			final IEditorDescriptor editorDescriptor = EditUIUtil.getDefaultEditor(uri, null);
			if (editorDescriptor != null) {
				return editorDescriptor.getId();
			}
		}
		return null;
	}

	@Override
	public void setAttribute(final String attribute, final Object value) {
		// not needed
	}

	@Override
	public Image getImage(final Object element) {
		return null;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof final EvaluatorDebugVariable variable) {
			return getVariableText(variable);
		}
		return null;
	}

	protected static String getVariableText(final EvaluatorDebugVariable variable) {
		final StringBuilder buffer = new StringBuilder();
		final String valueString = variable.getValue().getValueString();
		final int valueMaxDisplayLength = FordiacDebugPreferences.getValueMaxDisplayLength();
		buffer.append(variable.getName());
		buffer.append(" := "); //$NON-NLS-1$
		if (valueString.length() <= valueMaxDisplayLength) {
			buffer.append(valueString);
		} else {
			buffer.append(valueString.substring(0, valueMaxDisplayLength));
			buffer.append('\u2026');
		}
		return buffer.toString();
	}

	@Override
	public void computeDetail(final IValue value, final IValueDetailListener listener) {
		try {
			listener.detailComputed(value, value.getValueString());
		} catch (final DebugException e) {
			FordiacLogHelper.logWarning("Cannot compute value detail", e); //$NON-NLS-1$
		}
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// not needed
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// not needed
	}

	@Override
	public void dispose() {
		// not needed
	}
}
