/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil, Bianca Wiesmayr - added internalVarSection to Simple FB
 *   Bianca Wiesmayr - extracted common base class for InternalVarSections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeRootEditPart;
import org.eclipse.fordiac.ide.gef.properties.InternalVarsSection;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class InternalVarsSectionSimple extends InternalVarsSection {
	@Override
	protected Object getInputType(Object input) {
		if (input instanceof FBTypeRootEditPart) {
			FBType type = ((FBTypeRootEditPart) input).getModel();
			if (type instanceof BaseFBType) {
				return type;
			}
		} else if (input instanceof BaseFBType) {
			return input;
		}
		return null;
	}
}
