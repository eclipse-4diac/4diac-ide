/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;

public interface IFBTEditorPart extends ITypeEditorPage {

	@Override
	default FBType getType() {
		if (ITypeEditorPage.super.getType() instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}

}
