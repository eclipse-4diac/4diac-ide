/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class VarContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		final ArrayList<VarDeclaration> vars = new ArrayList<>();
		if (inputElement instanceof IInterfaceElement) {
			final IInterfaceElement ielem = (IInterfaceElement) inputElement;
			final FBType fbtype = (FBType) ielem.eContainer().eContainer();
			// filter adapter elements as the are not allowed to be connected by with
			final EList<VarDeclaration> sourceVarList = (ielem.isIsInput()) ? fbtype.getInterfaceList().getInputVars()
					: fbtype.getInterfaceList().getOutputVars();

			for (final VarDeclaration variable : sourceVarList) {
				if (!(variable.getType() instanceof AdapterType)) {
					vars.add(variable);
				}
			}
		}
		return vars.toArray();
	}
}
