/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
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
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.ui.IEditorInput;

public class ApplicationEditor extends FBNetworkEditor {

	/** The adapter. */
	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			final int type = notification.getEventType();
			final int featureId = notification.getFeatureID(Application.class);

			if ((Notification.SET == type) && (LibraryElementPackage.INAMED_ELEMENT__NAME == featureId)) {
				updateEditorTitle(getModel().getApplication().getName());
			}
		}

	};

	@Override
	public void setInput(final IEditorInput input) {
		if (!(input instanceof final ApplicationEditorInput appInput)) {
			throw new IllegalArgumentException(
					"Application editors only accept ApplicationEditorInput as valid inputs!"); //$NON-NLS-1$
		}
		if (getEditorInput() == null) {
			// initial editor setup
			final Application app = appInput.getContent();
			setModel(app.getFBNetwork());

			// register Adapter to be informed on changes of the application name
			app.eAdapters().add(adapter);

		}
		super.setInput(input);
	}

	@Override
	public void dispose() {
		if (getModel() != null && getModel().eAdapters().contains(adapter)) {
			getModel().eAdapters().remove(adapter);
		}
		super.dispose();
	}

}
