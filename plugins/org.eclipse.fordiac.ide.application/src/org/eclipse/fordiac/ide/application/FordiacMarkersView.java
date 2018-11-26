/*******************************************************************************
 * Copyright (c) 2010 - 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.OpenAndLinkWithEditorHelper;
import org.eclipse.ui.views.markers.MarkerSupportView;

public class FordiacMarkersView extends MarkerSupportView {

	public FordiacMarkersView() {
		super("org.eclipse.fordiac.ide.application.fordiacmarkerContentGenerator"); //$NON-NLS-1$
	}

	@SuppressWarnings("restriction")
	@Override
	public void createPartControl(Composite parent) {
		// TODO find a better way, as super.createPartControl normally is
		// restricted for access;
		super.createPartControl(parent);
		Object provider = getSite().getSelectionProvider();
		if (provider instanceof StructuredViewer) {

			new OpenAndLinkWithEditorHelper(
					(StructuredViewer)provider) {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.ui.OpenAndLinkWithEditorHelper#activate(org.eclipse
				 * .jface.viewers.ISelection )
				 */
				@Override
				protected void activate(ISelection selection) {
					final int currentMode = OpenStrategy.getOpenMethod();
					try {
						OpenStrategy.setOpenMethod(OpenStrategy.DOUBLE_CLICK);
						openMySelectedMarkers();
					} finally {
						OpenStrategy.setOpenMethod(currentMode);
					}
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.ui.OpenAndLinkWithEditorHelper#linkToEditor(org
				 * .eclipse .jface.viewers .ISelection)
				 */
				@Override
				protected void linkToEditor(ISelection selection) {
					// Not supported by this part
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.ui.OpenAndLinkWithEditorHelper#open(org.eclipse
				 * .jface .viewers.ISelection, boolean)
				 */
				@Override
				protected void open(ISelection selection, boolean activate) {
					openMySelectedMarkers();
				}
			};
		}
	}

	
	/**
	 * Open the selected markers
	 */
	void openMySelectedMarkers() {
		@SuppressWarnings("restriction")
		IMarker[] markers = getSelectedMarkers();
		for (int i = 0; i < markers.length; i++) {
			IMarker marker = markers[i];
			Object markerObject;
			try {
				markerObject = marker.getAttribute("org.eclipse.fordiac.ide.application.marker.fb"); //$NON-NLS-1$
				if (markerObject instanceof FB) {
					System.out.println("should open markerObject"); //$NON-NLS-1$
				}
			} catch (CoreException e) {
				ApplicationPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
	}

}
