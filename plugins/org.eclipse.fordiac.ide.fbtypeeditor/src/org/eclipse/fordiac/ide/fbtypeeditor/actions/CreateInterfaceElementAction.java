/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CreateInterfaceElementAction extends WorkbenchPartAction {
	private final FBType fbType;

	public CreateInterfaceElementAction(final IWorkbenchPart part, final FBType fbType) {
		super(part);
		this.fbType = fbType;
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != fbType) && !(fbType instanceof FunctionFBType);
	}

	public FBType getFbType() {
		return fbType;
	}
}
