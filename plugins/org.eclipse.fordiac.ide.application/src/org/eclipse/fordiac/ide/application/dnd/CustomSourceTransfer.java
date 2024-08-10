/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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

import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.SimpleObjectTransfer;

final class CustomSourceTransfer extends SimpleObjectTransfer {

	public static final String CONNECTIONS_LIST = "Connections"; //$NON-NLS-1$

	private static final CustomSourceTransfer INSTANCE = new CustomSourceTransfer();
	private static final String TYPE_NAME = "CustomSourceTransfer";//$NON-NLS-1$
	private static final int TYPEID = registerType(TYPE_NAME);

	public static CustomSourceTransfer getInstance() {
		return INSTANCE;
	}

	@Override
	public Request getObject() {
		return (Request) super.getObject();
	}

	@Override
	public void setObject(final Object obj) {
		if (obj != null && !(obj instanceof Request)) {
			throw new IllegalArgumentException("CustomSourceTransfer requires a request as object!"); //$NON-NLS-1$
		}
		super.setObject(obj);
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