/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

public class ErrorSubAppTypeEntryImpl extends SubAppTypeEntryImpl {

	// error marker entries do not have a file and therefore we need to change getTypeName
	@Override
	public String getTypeName() {
		return getType().getName();
	}

}
