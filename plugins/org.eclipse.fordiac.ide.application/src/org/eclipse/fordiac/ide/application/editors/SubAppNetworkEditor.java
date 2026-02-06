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
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.ui.editors.EditorCloserAdapter;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.ui.IEditorInput;

public class SubAppNetworkEditor extends FBNetworkEditor {

	private final Adapter adapter = new EditorCloserAdapter(this) {

		@Override
		public void notifyChanged(final Notification notification) {
			final int featureId = notification.getFeatureID(TypedConfigureableObject.class);
			if (((LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY == featureId) && (getSubApp().isTyped()))
					|| isSubAppToggledToExpanded(notification)) {
				// undo of detached from the subapp type or because of subapp beeing expanded
				closeEditor();
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
		removeAdapters();
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	public void setInput(final IEditorInput input) {
		final SubApp subApp = LibraryElementProvider.INSTANCE.getElement(input, SubApp.class);
		if (subApp == null) {
			throw new IllegalArgumentException("SubApp editors only accept sub-applications as valid inputs!"); //$NON-NLS-1$
		}
		removeAdapters();
		setModel(subApp.getSubAppNetwork());
		addAdapters();
		super.setInput(input);
	}

	protected void addAdapters() {
		if (getSubApp() != null) {
			getSubApp().eAdapters().add(adapter);
			final EObject container = getSubApp().eContainer();
			if (container != null) {
				fbNetworkAdapter = new FBNElemEditorCloser(this, getSubApp());
				container.eAdapters().add(fbNetworkAdapter);
			}
		}
	}

	protected void removeAdapters() {
		if (getSubApp() != null) {
			getSubApp().eAdapters().remove(adapter);
			final EObject container = getSubApp().eContainer();
			if (container != null) {
				container.eAdapters().remove(fbNetworkAdapter);
			}
		}
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new UntypedSubAppEditPartFactory(this);
	}

	private SubApp getSubApp() {
		return getModel() != null ? (SubApp) getModel().eContainer() : null;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (FBNetworkElement.class == adapter) {
			return adapter.cast(getSubApp());
		}
		return super.getAdapter(adapter);
	}
}
