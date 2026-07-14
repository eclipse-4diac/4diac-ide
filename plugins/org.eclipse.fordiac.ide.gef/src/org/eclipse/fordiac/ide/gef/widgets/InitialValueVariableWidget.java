/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.fordiac.ide.gef.nat.InitialValueVariableConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.InitialValueVariableDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VariableTreeData;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

public class InitialValueVariableWidget extends VariableWidget {

	private final List<Variable<?>> baseVariables;
	private final Set<Variable<?>> explicitInitialValueVariables;
	private Map<Variable<?>, Variable<?>> baseVariableMap = Map.of();

	public InitialValueVariableWidget(final List<Variable<?>> baseVariables,
			final Set<Variable<?>> explicitInitialValueVariables) {
		this.baseVariables = List.copyOf(baseVariables);
		this.explicitInitialValueVariables = Collections.newSetFromMap(new IdentityHashMap<>());
		this.explicitInitialValueVariables.addAll(explicitInitialValueVariables);
	}

	public boolean isExplicitInitialValue(final Variable<?> variable) {
		return explicitInitialValueVariables.contains(variable);
	}

	@Override
	protected DataLayer createDataLayer(final VariableTreeData dataProvider) {
		return new InitialValueVariableDataLayer(dataProvider);
	}

	@Override
	protected IConfigLabelAccumulator createConfigLabelAccumulator(final VariableTreeData dataProvider) {
		return new InitialValueVariableConfigLabelAccumulator(dataProvider, this::isInheritedValue);
	}

	@Override
	protected void setVariableInput(final List<Variable<?>> variables) {
		super.setVariableInput(variables);
		baseVariableMap = mapBaseVariables(variables, baseVariables);
	}

	@Override
	protected void handleVariableModified(final Variable<?> variable, final String oldValue, final String newValue) {
		updateExplicitInitialValue(variable, oldValue, newValue);
		getTable().refresh();
	}

	private boolean isInheritedValue(final Variable<?> variable) {
		return isBaseValue(variable) && !hasExplicitInitialValue(variable);
	}

	private boolean isBaseValue(final Variable<?> variable) {
		final Variable<?> baseVariable = baseVariableMap.get(variable);
		return baseVariable != null && ValueOperations.equals(baseVariable.getValue(), variable.getValue());
	}

	private void updateExplicitInitialValue(final Variable<?> variable, final String oldValue, final String newValue) {
		if (!baseVariableMap.containsKey(variable)) {
			return;
		}
		if (!isBaseValue(variable) || Objects.equals(oldValue, newValue)) {
			markExplicitInitialValue(variable);
		} else {
			clearExplicitInitialValue(variable);
		}
	}

	private void markExplicitInitialValue(final Variable<?> variable) {
		final List<Variable<?>> children = variable.getChildren().toList();
		if (children.isEmpty()) {
			explicitInitialValueVariables.add(variable);
		} else {
			children.forEach(this::markExplicitInitialValue);
		}
	}

	private void clearExplicitInitialValue(final Variable<?> variable) {
		explicitInitialValueVariables.remove(variable);
		variable.getChildren().forEach(this::clearExplicitInitialValue);
	}

	private boolean hasExplicitInitialValue(final Variable<?> variable) {
		return explicitInitialValueVariables.contains(variable)
				|| variable.getChildren().anyMatch(this::hasExplicitInitialValue);
	}

	private static Map<Variable<?>, Variable<?>> mapBaseVariables(final List<Variable<?>> variables,
			final List<Variable<?>> baseVariables) {
		final Map<Variable<?>, Variable<?>> result = new HashMap<>();
		for (int i = 0; i < variables.size() && i < baseVariables.size(); i++) {
			mapBaseVariable(variables.get(i), baseVariables.get(i), result);
		}
		return Map.copyOf(result);
	}

	private static void mapBaseVariable(final Variable<?> variable, final Variable<?> baseVariable,
			final Map<Variable<?>, Variable<?>> result) {
		result.put(variable, baseVariable);
		variable.getChildren().forEach(child -> getBaseChild(baseVariable, child)
				.ifPresent(baseChild -> mapBaseVariable(child, baseChild, result)));
	}

	private static Optional<Variable<?>> getBaseChild(final Variable<?> baseVariable, final Variable<?> child) {
		return baseVariable.getChildren().filter(baseChild -> Objects.equals(baseChild.getName(), child.getName()))
				.findFirst();
	}
}
