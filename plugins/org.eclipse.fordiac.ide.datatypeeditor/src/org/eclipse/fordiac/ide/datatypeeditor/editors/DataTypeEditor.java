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
package org.eclipse.fordiac.ide.datatypeeditor.editors;

import org.eclipse.fordiac.ide.datatypeeditor.wizards.SaveAsStructTypeWizard;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.search.dialog.AbstractTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.StructuredDataTypeDataHandler;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditor;
import org.eclipse.jface.wizard.WizardDialog;

public class DataTypeEditor extends AbstractTypeEditor {

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.datatypeeditor.editors.DataTypeEditor"; //$NON-NLS-1$
	}

	@Override
	protected AbstractTypeEntryDataHandler<? extends TypeEntry> createTypeEntryDataHandler() {
		return new StructuredDataTypeDataHandler(getTypeEntry());
	}

	@Override
	protected boolean dependencyAffectingTypeChange() {
		// For data types all changes are dependency affecting changes
		return true;
	}

	@Override
	public void doSaveAs() {
		if (getType() instanceof final StructuredType structuredType) {
			new WizardDialog(null, new SaveAsStructTypeWizard(structuredType, this)).open();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return (getType() instanceof StructuredType);
	}

	@Override
	protected DataTypeEntry getTypeEntry() {
		return (DataTypeEntry) super.getTypeEntry();
	}

}
