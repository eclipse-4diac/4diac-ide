/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorHelper;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

/** Keeps the rows read-only that {@link BulkEditorHelper#isReadOnly(EObject)} reports. */
public class ReadOnlyElementsEditableRule extends EditableRule {

	private final IEditableRule parent;
	private final IRowDataProvider<? extends EObject> provider;

	public ReadOnlyElementsEditableRule(final IEditableRule parent,
			final IRowDataProvider<? extends EObject> provider) {
		this.parent = parent;
		this.provider = provider;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		return parent.isEditable(columnIndex, rowIndex)
				&& !BulkEditorHelper.isReadOnly(provider.getRowObject(rowIndex));
	}
}
