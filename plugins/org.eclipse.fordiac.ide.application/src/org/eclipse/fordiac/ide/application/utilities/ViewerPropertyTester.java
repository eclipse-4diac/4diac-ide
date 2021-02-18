/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais, Bianca Wiesmayr,
 *   Daniel Lindhuber, Michael Oberlehner - initial implementation
 *   										and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.ui.IEditorPart;

public class ViewerPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final IEditorPart part = EditorUtils.getCurrentActiveEditor();
		final FBNetwork network = part.getAdapter(FBNetwork.class);

		return null == network;
	}
}
