/*******************************************************************************
 * Copyright (c) 2019, 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;

public class InputPrimitiveSection extends AbstractPrimitiveSection {

	@Override
	protected InputPrimitive getType() {
		return (InputPrimitive) type;
	}

	@Override
	protected EList<Event> getRelevantEvents(final FBType fb) {
		return fb.getInterfaceList().getEventInputs();
	}
}
