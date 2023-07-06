/*******************************************************************************
 * Copyright (c) 2014, 2023 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added var in out support
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class VarContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		final ArrayList<VarDeclaration> vars = new ArrayList<>();
		if (inputElement instanceof final IInterfaceElement ielem) {
			final FBType fbtype = (FBType) ielem.eContainer().eContainer();

			if (ielem.isIsInput()) {
				vars.addAll(fbtype.getInterfaceList().getInputVars());
				vars.addAll(fbtype.getInterfaceList().getInOutVars());
			} else {
				vars.addAll(fbtype.getInterfaceList().getOutputVars());
				vars.addAll(fbtype.getInterfaceList().getOutMappedInOutVars());
			}
		}
		return vars.toArray();
	}
}
