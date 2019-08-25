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

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.util.UntypedEditorInput;
import org.eclipse.ui.IPersistableElement;

public class CompositeInstanceViewerInput extends UntypedEditorInput {

	private FBEditPart fbEditPart;

	public FBEditPart getFbEditPart() {
		return fbEditPart;
	}

	public void setFbEditPart(FBEditPart fbEditPart) {
		this.fbEditPart = fbEditPart;
	}

	public CompositeInstanceViewerInput(FBEditPart fbEditPart, Object content, String name) {
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
