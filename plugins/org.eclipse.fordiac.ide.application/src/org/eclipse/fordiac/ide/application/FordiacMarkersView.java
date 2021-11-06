/*******************************************************************************
 * Copyright (c) 2010 - 2014 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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
	public void createPartControl(final Composite parent) {
		// TODO find a better way, as super.createPartControl normally is
		// restricted for access
		super.createPartControl(parent);
		final Object provider = getSite().getSelectionProvider();
		if (provider instanceof StructuredViewer) {

			new OpenAndLinkWithEditorHelper((StructuredViewer) provider) {
				/*
				 * (non-Javadoc)
				 *
				 * @see org.eclipse.ui.OpenAndLinkWithEditorHelper#activate(org.eclipse
				 * .jface.viewers.ISelection )
				 */
				@Override
				protected void activate(final ISelection selection) {
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
				 * @see org.eclipse.ui.OpenAndLinkWithEditorHelper#linkToEditor(org .eclipse
				 * .jface.viewers .ISelection)
				 */
				@Override
				protected void linkToEditor(final ISelection selection) {
					// Not supported by this part
				}

				/*
				 * (non-Javadoc)
				 *
				 * @see org.eclipse.ui.OpenAndLinkWithEditorHelper#open(org.eclipse .jface
				 * .viewers.ISelection, boolean)
				 */
				@Override
				protected void open(final ISelection selection, final boolean activate) {
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
		final
		IMarker[] markers = getSelectedMarkers();
		for (final IMarker marker : markers) {
			Object markerObject;
			try {
				markerObject = marker.getAttribute("org.eclipse.fordiac.ide.application.marker.fb"); //$NON-NLS-1$
				if (markerObject instanceof FB) {
					FordiacLogHelper.logWarning("should open markerObject"); //$NON-NLS-1$
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

}
