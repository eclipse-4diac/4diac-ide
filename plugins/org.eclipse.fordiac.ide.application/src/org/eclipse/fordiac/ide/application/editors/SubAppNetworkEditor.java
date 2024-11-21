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
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
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

			if (((LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY == featureId) && (getSubApp().isTyped()))
					|| isSubAppToggledToExpanded(notification)) {
				// undo of detached from the subapp type or because of subapp beeing expanded
				closeEditor();
			} else {
				if ((Notification.SET == type) && (LibraryElementPackage.SUB_APP__NAME == featureId)) {
					updateEditorTitle(getSubApp().getName());
				}
				firePropertyChange(PROP_DIRTY);
			}
		}

		private boolean isSubAppToggledToExpanded(final Notification msg) {
			return msg.getOldValue() == null && msg.getNewValue() instanceof final Attribute att
					&& att.getAttributeDeclaration() == InternalAttributeDeclarations.UNFOLDED
					&& "true".equals(att.getValue()); //$NON-NLS-1$
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
	public void setInput(final IEditorInput input) {
		if (!(input instanceof final SubApplicationEditorInput subAppEI)) {
			throw new IllegalArgumentException(
					"SuppApp editors only accept SubApplicationEditorInput as valid inputs!"); //$NON-NLS-1$
		}
		if (getEditorInput() == null) {
			// initial editor setup
			setModel(subAppEI.getSubApp().getSubAppNetwork());
			// register Adapter to be informed on changes of the subapplication name
			getSubApp().eAdapters().add(adapter);
			final EObject container = getSubApp().eContainer();
			if (container != null) {
				fbNetworkAdapter = new FBNElemEditorCloser(this, getSubApp());
				container.eAdapters().add(fbNetworkAdapter);
			}
		}
		super.setInput(input);
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new UntypedSubAppEditPartFactory(this);
	}

	private SubApp getSubApp() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (FBNetworkElement.class == adapter) {
			return adapter.cast(getSubApp());
		}
		return super.getAdapter(adapter);
	}
}
