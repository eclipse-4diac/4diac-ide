/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - Extracted the class from NatTableWidgetFactory
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class DefaultRegistryConfiguration extends AbstractRegistryConfiguration {
	IEditableRule editableRule;
	Map<String, List<String>> proposals;
	ICellEditor proposalCellEditor;

	public DefaultRegistryConfiguration(final IEditableRule editableRule, final ICellEditor proposalCellEditor) {
		this.editableRule = editableRule;
		this.proposalCellEditor = proposalCellEditor;
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		if (editableRule != null) {
			configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, editableRule);
		}
		if (proposalCellEditor != null) {
			configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, proposalCellEditor,
					DisplayMode.EDIT, NatTableWidgetFactory.PROPOSAL_CELL);
		}
	}
}