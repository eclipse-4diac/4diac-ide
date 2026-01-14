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

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.ui.IEditorInput;

public class ApplicationEditor extends FBNetworkEditor {

	@Override
	public void setInput(final IEditorInput input) {
		final Application application = LibraryElementProvider.INSTANCE.getElement(input, Application.class);
		if (application == null) {
			throw new IllegalArgumentException("Application editors only accept applications as valid inputs!"); //$NON-NLS-1$
		}
		setModel(application.getFBNetwork());
		super.setInput(input);
	}
}
