/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;

public class LinkedElementsEditableRule extends EditableRule {

	private final ChangeableListDataProvider<? extends EObject> provider;

	public LinkedElementsEditableRule(final ChangeableListDataProvider<? extends EObject> provider) {
		this.provider = provider;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		final var rootElement = EcoreUtil.getRootContainer(provider.getRowObject(rowIndex));
		if (rootElement instanceof final LibraryElement libElement) {
			return SearchHelper.linkedElementsFilter.test(libElement.getTypeEntry());
		}
		return true;
	}
}
