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
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class DefaultRegistryConfiguration extends AbstractRegistryConfiguration {
	IEditableRule editableRule;
	Map<String, List<String>> proposals;
	AbstractSelectionButton typeSelection;
	public DefaultRegistryConfiguration(final IEditableRule editableRule, AbstractSelectionButton typeSelection) {
		super();
		this.editableRule = editableRule;
		this.typeSelection = typeSelection;
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, editableRule);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, 
				 typeSelection, DisplayMode.EDIT, NatTableWidgetFactory.PROPOSAL_CELL);
	}
}