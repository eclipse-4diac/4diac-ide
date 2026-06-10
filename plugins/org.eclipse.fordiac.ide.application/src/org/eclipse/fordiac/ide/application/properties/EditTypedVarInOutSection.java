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

import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;

public class EditTypedVarInOutSection extends AbstractEditTypeVarInOutSection {
	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement ie) {
		return null;
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement ie, final int index) {
		return null;
	}

	@Override
	protected BlockFBNetworkElement getInputType(final Object input) {
		return TypedInterfaceEditingFilter.getTypedElementFromSelectedElement(input);
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
		return new NatTableColumnEditableRule<>(
				IEditableRule.ALWAYS_EDITABLE, VarDeclarationTableColumn
						.defaultColumnsWith(VarDeclarationTableColumn.VISIBLEIN, VarDeclarationTableColumn.VISIBLEOUT),
				VarDeclarationTableColumn.DEFAULT_EDITABLE);
	}
}
