/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.fordiac.ide.model.data.AnyDerivedType;

public interface DataTypeEntry extends TypeEntry {

	@Override
	AnyDerivedType getType();

	/**
	 * @deprecated see {@link TypeEntry#getTypeEditable()}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	AnyDerivedType getTypeEditable();

	@Override
	AnyDerivedType copyType();
}
