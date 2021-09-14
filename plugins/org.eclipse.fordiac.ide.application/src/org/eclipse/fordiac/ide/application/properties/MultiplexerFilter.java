/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.MultiplexerEditPart;
import org.eclipse.fordiac.ide.application.editparts.StructInterfaceEditPart;
import org.eclipse.jface.viewers.IFilter;

/**
 * filter that checks whether object toTest is a Multiplexer
 */
public class MultiplexerFilter implements IFilter {
	@Override
	public boolean select(final Object toTest) {
		return (toTest instanceof MultiplexerEditPart)
				|| ((toTest instanceof StructInterfaceEditPart)
				&& ((StructInterfaceEditPart)toTest).getParent() instanceof MultiplexerEditPart);	
	}
}