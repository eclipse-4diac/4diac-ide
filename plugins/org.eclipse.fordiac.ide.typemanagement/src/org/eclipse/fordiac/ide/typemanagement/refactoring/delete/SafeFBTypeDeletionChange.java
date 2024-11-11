/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafeFBTypeDeletionChange extends CompositeChange {

	public SafeFBTypeDeletionChange(final FBType type) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle, type.getName()));
		addUpdateChanges(this, type);
	}

	public static void addUpdateChanges(final CompositeChange change, final FBType type) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(type.getTypeEntry());
		search.performSearch().stream().filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast)
				.forEach(fbnEl -> {
					if (fbnEl instanceof final FB fb && fbnEl.eContainer() instanceof BaseFBType) {
						// we have an internal FB and we should delete it
						change.add(new DeleteInternalFBChange(fb));
					}
				});
	}

}
