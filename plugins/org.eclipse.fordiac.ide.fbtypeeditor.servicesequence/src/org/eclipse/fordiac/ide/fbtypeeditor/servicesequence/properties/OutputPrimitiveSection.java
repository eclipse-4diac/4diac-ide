/*******************************************************************************
 * Copyright (c) 2014  fortiss GmbH
 *				 2021 Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;

public class OutputPrimitiveSection extends AbstractPrimitiveSection {

	@Override
	protected OutputPrimitive getType() {
		return (OutputPrimitive) type;
	}

	@Override
	protected EList<Event> getRelevantEvents(final FBType fb) {
		return fb.getInterfaceList().getEventOutputs();
	}
}
