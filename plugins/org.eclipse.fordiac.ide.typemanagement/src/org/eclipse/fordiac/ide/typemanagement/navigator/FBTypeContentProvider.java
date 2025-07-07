/*******************************************************************************
 * Copyright (c) 2011, 2025 TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Patrick Aigner
 *     - moved methods to LibraryElementContentProvider
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonViewer;

public class FBTypeContentProvider extends LibraryElementContentProvider implements IResourceChangeListener {

	public FBTypeContentProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.POST_BUILD);
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final IFile file) {
			return file.getParent();
		}
		return super.getParent(element);
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IFile) {
			return true;
		}
		return super.hasChildren(element);
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		try {
			final List<IResource> list = new ArrayList<>();
			event.getDelta().accept(delta -> {
				for (final IMarkerDelta markerDelta : delta.getMarkerDeltas()) {
					IResource resource = markerDelta.getResource();
					while (resource != null) {
						list.add(resource);
						resource = resource.getParent();
					}
				}
				return true;
			});
			if (!list.isEmpty()) {
				performViewerRefresh(list);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Couldn't refresh markers", e); //$NON-NLS-1$
		}
	}

	private void performViewerRefresh(final List<IResource> list) {
		Display.getDefault().asyncExec(() -> {
			if (viewer instanceof final CommonViewer cViewer && !viewer.getControl().isDisposed()) {
				cViewer.update(list.toArray(), null);
			}
		});
	}
}
