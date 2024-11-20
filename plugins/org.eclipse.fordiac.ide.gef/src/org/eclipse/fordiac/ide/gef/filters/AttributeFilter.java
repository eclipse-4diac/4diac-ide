/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.filters;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class AttributeFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return parseObject(toTest) instanceof ConfigurableObject;
	}

	public static Object parseObject(final Object input) {
		return switch (input) {
		// for var_in_outs we can only have attributes on the input side
		case final VarDeclaration varDecl when varDecl.isInOutVar() && !varDecl.isIsInput() ->
			varDecl.getInOutVarOpposite();
		// handle exception: interface elements of functions
		case final IInterfaceElement interfaceElement when interfaceElement
				.eContainer() instanceof final InterfaceList interfaceList
				&& interfaceList.eContainer() instanceof FunctionFBType ->
			null;
		case final ConfigurableObject configurableObject -> configurableObject;
		case final FBNetwork fbNetwork -> parseObject(fbNetwork.eContainer());
		case final EditPart editpart -> parseObject(editpart.getModel());
		case final IAdaptable adaptable -> adaptable.getAdapter(ConfigurableObject.class);
		case null, default -> null;
		};
	}
}
