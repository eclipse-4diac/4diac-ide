/*******************************************************************************
 * Copyright (c) 2011, 2023 Profactor GmbH, fortiss GmbH,
 * 							Primetals Austria GmbH
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
 *   Alois Zoitl - copied and adapted from VariableOutputContainer
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class VarInOutOutputContainer extends AbstractContainerElement {

	public VarInOutOutputContainer(final FBType fbtype) {
		super(fbtype);
	}

	@Override
	public List<? extends IInterfaceElement> getChildren() {
		return getFbType().getInterfaceList().getOutMappedInOutVars();
	}
}
