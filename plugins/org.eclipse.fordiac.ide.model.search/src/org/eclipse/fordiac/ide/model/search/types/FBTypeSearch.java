/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch.FBTypeInstanceSearchFilter;

public class FBTypeSearch extends InstanceSearch {

	public static FBTypeSearch createFBTypeSearch(final FBType type) {
		return new FBTypeSearch(new FBTypeInstanceSearchFilter(type));
	}

	public FBTypeSearch(final SearchFilter filter) {
		super(filter);
	}
}
