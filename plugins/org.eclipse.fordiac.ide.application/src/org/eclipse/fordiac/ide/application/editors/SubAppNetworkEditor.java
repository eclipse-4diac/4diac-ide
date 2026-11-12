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

import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.ui.IEditorInput;

public class SubAppNetworkEditor extends FBNetworkEditor {

	@Override
	public void dispose() {
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	public void setInput(final IEditorInput input) {
		final SubApp subApp = LibraryElementProvider.INSTANCE.getElement(input, SubApp.class);
		if (subApp == null) {
			throw new IllegalArgumentException("SubApp editors only accept sub-applications as valid inputs!"); //$NON-NLS-1$
		}
		setModel(subApp.getSubAppNetwork());
		super.setInput(input);
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
