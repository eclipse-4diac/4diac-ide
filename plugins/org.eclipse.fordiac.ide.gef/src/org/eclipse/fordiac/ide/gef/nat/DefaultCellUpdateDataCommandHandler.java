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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.edit.event.DataUpdateEvent;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class DefaultCellUpdateDataCommandHandler extends UpdateDataCommandHandler {
	private final DataLayer dataLayer;

	public DefaultCellUpdateDataCommandHandler(final DataLayer dataLayer) {
		super(dataLayer, true);
		this.dataLayer = dataLayer;
	}

	@Override
	protected boolean doCommand(final UpdateDataCommand command) {
		try {
			final int columnPosition = command.getColumnPosition();
			final int rowPosition = command.getRowPosition();

			final Object currentValue = this.dataLayer.getDataValue(columnPosition, rowPosition);
			final LabelStack labelStack = dataLayer.getConfigLabelsByPosition(columnPosition, rowPosition);
			if (labelStack != null && labelStack.hasLabel(NatTableWidgetFactory.DEFAULT_CELL)
					&& !currentValue.equals("")) { //$NON-NLS-1$
				// we always set value if it was a defaultValue (exception: empty cell)
				this.dataLayer.setDataValueByPosition(columnPosition, rowPosition, command.getNewValue());
				this.dataLayer.fireLayerEvent(new DataUpdateEvent(this.dataLayer, columnPosition, rowPosition,
						currentValue, command.getNewValue()));
				return true;
			}
			return super.doCommand(command);
		} catch (final Exception e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.NatTable_Update_Failed, command.getNewValue()), e);
			return false;
		}
	}
}
