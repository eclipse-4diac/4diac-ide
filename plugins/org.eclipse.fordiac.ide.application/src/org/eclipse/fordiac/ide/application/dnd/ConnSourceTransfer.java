/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.dnd;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.dnd.SimpleObjectTransfer;

final class ConnSourceTransfer extends SimpleObjectTransfer {

	private static final ConnSourceTransfer INSTANCE = new ConnSourceTransfer();
	private static final String TYPE_NAME = "ConnSourceTransver";//$NON-NLS-1$
	private static final int TYPEID = registerType(TYPE_NAME);


	public static ConnSourceTransfer getInstance() {
		return INSTANCE;
	}

	@Override
	public InterfaceEditPart getObject() {
		return (InterfaceEditPart) super.getObject();
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

}