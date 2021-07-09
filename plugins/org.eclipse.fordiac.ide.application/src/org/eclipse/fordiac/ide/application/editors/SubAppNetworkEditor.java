/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.EditorCloserAdapter;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.ui.IEditorInput;

public class SubAppNetworkEditor extends FBNetworkEditor {

	private Adapter adapter = new EditorCloserAdapter(this) {

		@Override
		public void notifyChanged(final Notification notification) {
			final int type = notification.getEventType();
			final int featureId = notification.getFeatureID(Application.class);

			if ((LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY == featureId)
					&& (getSubApp().isTyped())) {
				// undo of detached from the subapp type
				closeEditor();
			} else {
				if ((Notification.SET == type) && (LibraryElementPackage.SUB_APP__NAME == featureId)) {
					updateEditorTitle(getSubApp().getName());
				}
				firePropertyChange(PROP_DIRTY);
			}
		}
	};

	private Adapter fbNetworkAdapter;

	@Override
	public void dispose() {
		if ((adapter != null) && (getModel() != null) && (getSubApp().eAdapters().contains(adapter))) {
			getSubApp().eAdapters().remove(adapter);
			adapter = null;
			final EObject container = getSubApp().eContainer();
			if (container != null) {
				container.eAdapters().remove(fbNetworkAdapter);
			}
		}
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof SubApplicationEditorInput) {
			final SubApplicationEditorInput subAppInput = (SubApplicationEditorInput) input;
			setModel(subAppInput.getSubApp().getSubAppNetwork());
			// register Adapter to be informed on changes of the subapplication name
			getSubApp().eAdapters().add(adapter);
			final EObject container = getSubApp().eContainer();
			if (container != null) {
				fbNetworkAdapter = new FBNElemEditorCloser(this, getSubApp());
				container.eAdapters().add(fbNetworkAdapter);
			}
		}
		super.setModel(input);
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new UntypedSubAppEditPartFactory(this);
	}

	private SubApp getSubApp() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	public Object getAdapter(final Class adapter) {
		if (FBNetworkElement.class == adapter) {
			return getSubApp();
		}
		return super.getAdapter(adapter);
	}
}
