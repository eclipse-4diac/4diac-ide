/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.attributetypeeditor.editors;

import org.eclipse.fordiac.ide.model.search.dialog.AbstractTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.AttributeTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditor;

public class AttributeTypeEditor extends AbstractTypeEditor {

	@Override
	protected AbstractTypeEntryDataHandler<? extends TypeEntry> createTypeEntryDataHandler() {
		return new AttributeTypeEntryDataHandler(getTypeEntry());
	}

	@Override
	protected boolean dependencyAffectingTypeChange() {
		// For data types all changes are dependency affecting changes
		return true;
	}

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.attributetypeeditor.editors.AttributeTypeEditor"; //$NON-NLS-1$
	}

	@Override
	protected AttributeTypeEntry getTypeEntry() {
		return (AttributeTypeEntry) super.getTypeEntry();
	}

}
