/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *               - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class FBTypeContentOutline extends ContentOutlinePage {

	private TreeViewer contentOutlineViewer;

	private final LibraryElementItemProviderAdapterFactory adapterFactory = new LibraryElementItemProviderAdapterFactory();
	private final DataItemProviderAdapterFactory dataFactory = new DataItemProviderAdapterFactory();

	private final ComposedAdapterFactory caf = new ComposedAdapterFactory();
	private final FBType fbType;

	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			final int type = notification.getEventType();
			if ((type == Notification.ADD || type == Notification.ADD_MANY)
					&& !getTreeViewer().getControl().isDisposed()) {
				// trigger expandAll after viewer has been refreshed
				// use a larger delay than AdapterFactoryContentProvider#getViewerRefreshDelay()
				Display.getDefault().timerExec(300, () -> {
					if (!getTreeViewer().getControl().isDisposed()) {
						getTreeViewer().expandAll();
					}
				});
			}
		}
	};

	FBTypeContentOutline(final FBType fbType) {
		this.fbType = fbType;
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		caf.addAdapterFactory(adapterFactory);
		caf.addAdapterFactory(dataFactory);

		contentOutlineViewer = getTreeViewer();
		contentOutlineViewer.setUseHashlookup(true);
		contentOutlineViewer.addSelectionChangedListener(this);

		// Set up the tree viewer.
		//
		contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(caf));
		contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(caf));
		contentOutlineViewer.setInput(fbType);
		contentOutlineViewer.expandAll();

		if (fbType != null) {
			fbType.eAdapters().add(adapter);
		}
	}

	@Override
	public void dispose() {
		caf.removeAdapterFactory(adapterFactory);
		caf.removeAdapterFactory(dataFactory);
		if (fbType != null) {
			fbType.eAdapters().remove(adapter);
		}
		contentOutlineViewer.removeSelectionChangedListener(this);
		super.dispose();
	}

}
