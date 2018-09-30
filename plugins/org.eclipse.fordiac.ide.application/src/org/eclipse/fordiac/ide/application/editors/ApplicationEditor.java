/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.ui.IEditorInput;

public class ApplicationEditor extends FBNetworkEditor {
	
	/** The adapter. */
	EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(
				final Notification notification) {
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(Application.class);

			switch (type) {
			case Notification.SET:
				if (featureId == LibraryElementPackage.INAMED_ELEMENT__NAME) {
					updateEditorTitle(getModel().getApplication().getName());
				}
				break;
			default:
				break;
			}
			super.notifyChanged(notification);
		}

	};

	
	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof ApplicationEditorInput) {
			ApplicationEditorInput appInput = (ApplicationEditorInput) input;
			Application app = appInput.getContent();
			setModel(app.getFBNetwork());
			
			// register EContentAdapter to be informed on changes of the
			// application name
			app.eAdapters().add(adapter);
			
		}
		super.setModel(input);
	}
	
	@Override
	public void dispose() {
		if (adapter != null && getModel() != null && getModel().eAdapters().contains(adapter)) {
				getModel().eAdapters().remove(adapter);
		}
		super.dispose();
	}

}
