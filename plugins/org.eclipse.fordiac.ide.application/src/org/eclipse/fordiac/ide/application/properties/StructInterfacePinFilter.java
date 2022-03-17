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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.jface.viewers.IFilter;

// Filtering the additional pin tab out

public class StructInterfacePinFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		final IInterfaceElement ie = TypedInterfacePinFilter.getInterfaceElementFromSelectedElement(toTest);

		return ((ie != null) && (ie.getFBNetworkElement() instanceof StructManipulator)
				&& (ie.getType() instanceof StructuredType)
				&& (ElementEditPartFactory.isMuxOutput(ie) || ElementEditPartFactory.isDemuxInput(ie)));
	}

}
