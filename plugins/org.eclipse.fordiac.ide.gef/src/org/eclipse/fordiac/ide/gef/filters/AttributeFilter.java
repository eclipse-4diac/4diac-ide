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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class AttributeFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return parseObject(toTest) instanceof ConfigurableObject;
	}

	public static Object parseObject(final Object input) {
		if (input instanceof ConfigurableObject) {
			return input;
		}

		if (input instanceof final EditPart editpart) {
			Object inputHelper = editpart.getModel();

			if (!(inputHelper instanceof EObject) && inputHelper instanceof final IAdaptable adaptable) {
				inputHelper = adaptable.getAdapter(ConfigurableObject.class);
			}

			// handle exception: interface elements of functions
			if (inputHelper instanceof final IInterfaceElement interfaceElement
					&& interfaceElement.eContainer() instanceof final InterfaceList interfaceList
					&& interfaceList.eContainer() instanceof FunctionFBType) {
				return null;
			}

			return (inputHelper instanceof final FBNetwork fbNetwork) ? fbNetwork.eContainer() : inputHelper;
		}
		return null;
	}
}
