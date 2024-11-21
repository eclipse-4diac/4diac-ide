/*******************************************************************************
 * Copyright (c) 2013 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.ui.editors.UntypedEditorInput;
import org.eclipse.ui.IPersistableElement;

public class CompositeAndSubAppInstanceViewerInput extends UntypedEditorInput {

	public CompositeAndSubAppInstanceViewerInput(final FBNetworkElement content) {
		super(content, content.getName());
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public FBNetworkElement getContent() {
		return (FBNetworkElement) super.getContent();
	}

}
