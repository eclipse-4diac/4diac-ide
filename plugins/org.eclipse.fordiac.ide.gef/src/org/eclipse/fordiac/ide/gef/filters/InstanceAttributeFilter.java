/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class InstanceAttributeFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return toTest instanceof TypedSubApp || toTest instanceof final EditPart ep && ep.getModel() instanceof TypedSubApp;
	}
}
