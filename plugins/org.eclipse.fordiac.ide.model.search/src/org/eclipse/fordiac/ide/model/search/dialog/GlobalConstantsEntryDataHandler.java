/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.search.types.GlobalConstantsTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;

public class GlobalConstantsEntryDataHandler extends AbstractTypeEntryDataHandler<GlobalConstantsEntry> {

	public GlobalConstantsEntryDataHandler(final GlobalConstantsEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	protected Map<INamedElement, GlobalConstantsEntry> createInputSet(final GlobalConstantsEntry inputTypeEntry) {
		final GlobalConstantsTypeInstanceSearch search = new GlobalConstantsTypeInstanceSearch(inputTypeEntry);
		return search.performSearch().stream().map(obj -> switch (obj) {
		case final STFunctionBody func -> func.eContainer(); // STFunctionBody isn't an INamedElement
		default -> obj;
		}).filter(INamedElement.class::isInstance).map(INamedElement.class::cast)
				.collect(Collectors.toMap(el -> el, el -> inputTypeEntry));
	}

}
