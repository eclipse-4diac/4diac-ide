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
package org.eclipse.fordiac.ide.application.viewer.composite;

import org.eclipse.fordiac.ide.util.UntypedEditorInput;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IPersistableElement;

public class CompositeAndSubAppInstanceViewerInput extends UntypedEditorInput {

	private EditPart fbEditPart;

	public EditPart getFbEditPart() {
		return fbEditPart;
	}

	public void setFbEditPart(final EditPart fbEditPart) {
		this.fbEditPart = fbEditPart;
	}

	public CompositeAndSubAppInstanceViewerInput(final EditPart fbEditPart, final Object content,
			final String name) {
		super(content, name);
		this.fbEditPart = fbEditPart;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

}
