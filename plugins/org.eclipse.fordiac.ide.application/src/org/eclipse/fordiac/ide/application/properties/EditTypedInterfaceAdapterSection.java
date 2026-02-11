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
package org.eclipse.fordiac.ide.application.properties;

import java.util.Set;

import org.eclipse.fordiac.ide.gef.nat.TypedElementTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.gef.EditPart;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;

public class EditTypedInterfaceAdapterSection extends EditInterfaceAdapterSection {
	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		return null;
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		return null;
	}

	@Override
	protected SubApp getInputType(final Object input) {
		Object candidate = null;
		if (input instanceof final EditPart editPart) {
			candidate = editPart.getModel();
		}
		if (candidate instanceof final SubApp subapp && subapp.isTyped()) {
			return subapp;
		}
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return null;
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return null;
	}

	@Override
	public boolean isShowTableEditButtons() {
		return false;
	}

	@Override
	protected IEditableRule getSectionEditableRule() {
		return new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE, TypedElementTableColumn.DEFAULT_COLUMNS,
				Set.of(TypedElementTableColumn.COMMENT));
	}
}
