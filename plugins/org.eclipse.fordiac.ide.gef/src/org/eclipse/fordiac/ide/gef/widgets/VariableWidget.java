/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.fordiac.ide.gef.nat.VariableColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VariableConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VariableEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VariableTableColumn;
import org.eclipse.fordiac.ide.gef.nat.VariableTreeData;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.event.DataUpdateEvent;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class VariableWidget {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	private final List<IVariableModificationListener> modificationListeners = new CopyOnWriteArrayList<>();

	private NatTable table;
	private VariableTreeData data;

	public Composite createWidget(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(composite);

		data = new VariableTreeData(new VariableColumnAccessor());
		final DataLayer dataLayer = new DataLayer(data);
		dataLayer.setConfigLabelAccumulator(new VariableConfigLabelAccumulator());
		dataLayer.addLayerListener(this::handleDataLayerEvent);
		table = NatTableWidgetFactory.createTreeNatTable(composite, dataLayer, data,
				new NatTableColumnProvider<>(VariableTableColumn.DEFAULT_COLUMNS),
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE, VariableTableColumn.DEFAULT_COLUMNS,
						VariableTableColumn.ALL_EDITABLE));

		table.addConfiguration(new VariableEditorConfiguration(data));
		table.configure();

		return composite;
	}

	public void setInput(final List<Variable<?>> variables) {
		data.setInput(variables);
		table.refresh();
	}

	public void addVariableModificationListener(final IVariableModificationListener listener) {
		modificationListeners.add(listener);
	}

	public void removeVariableModificationListener(final IVariableModificationListener listener) {
		modificationListeners.remove(listener);
	}

	private void handleDataLayerEvent(final ILayerEvent event) {
		if (event instanceof final DataUpdateEvent dataUpdateEvent) {
			final Variable<?> variable = data.getRowObject(dataUpdateEvent.getRowPosition());
			final String oldValue = Objects.toString(dataUpdateEvent.getOldValue(), NULL_DEFAULT);
			final String newValue = Objects.toString(dataUpdateEvent.getNewValue(), NULL_DEFAULT);
			modificationListeners.forEach(listener -> listener.variableModified(variable, oldValue, newValue));
		}
	}

	@FunctionalInterface
	public interface IVariableModificationListener {

		void variableModified(Variable<?> variable, String oldValue, String newValue);
	}
}
