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
package org.eclipse.fordiac.ide.gef.nat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.tree.ITreeData;

public class VariableTreeData extends ListDataProvider<Variable<?>> implements ITreeData<Variable<?>> {

	private final Integer ZERO = Integer.valueOf(0);

	private Map<Variable<?>, Integer> depthMap;
	private Map<Variable<?>, Integer> indexMap;

	public VariableTreeData(final IColumnAccessor<Variable<?>> columnAccessor) {
		super(Collections.emptyList(), columnAccessor);
	}

	public VariableTreeData(final List<Variable<?>> roots, final IColumnAccessor<Variable<?>> columnAccessor) {
		super(Collections.emptyList(), columnAccessor);
		setInput(roots);
	}

	@Override
	public int getDepthOfData(final Variable<?> object) {
		return depthMap.getOrDefault(object, ZERO).intValue();
	}

	@Override
	public int getDepthOfData(final int index) {
		return getDepthOfData(getDataAtIndex(index));
	}

	@Override
	public Variable<?> getDataAtIndex(final int index) {
		return getList().get(index);
	}

	@Override
	public int indexOf(final Variable<?> child) {
		final Integer index = indexMap.get(child);
		return index != null ? index.intValue() : -1;
	}

	@Override
	public boolean hasChildren(final Variable<?> object) {
		return object.getChildren().findAny().isPresent();
	}

	@Override
	public boolean hasChildren(final int index) {
		return hasChildren(getDataAtIndex(index));
	}

	@Override
	public List<Variable<?>> getChildren(final Variable<?> object) {
		return object.getChildren().toList();
	}

	@Override
	public List<Variable<?>> getChildren(final Variable<?> object, final boolean fullDepth) {
		return fullDepth ? flatten(object).skip(1).toList() : getChildren(object);
	}

	@Override
	public List<Variable<?>> getChildren(final int index) {
		return getChildren(getDataAtIndex(index));
	}

	@Override
	public int getElementCount() {
		return getList().size();
	}

	@Override
	public boolean isValidIndex(final int index) {
		return index >= 0 && index < getElementCount();
	}

	public void setInput(final List<Variable<?>> variables) {
		list = variables.stream().flatMap(VariableTreeData::flatten).toList();
		depthMap = variables.stream().flatMap(variable -> flattenDepth(variable, 0))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		indexMap = IntStream.range(0, list.size()).mapToObj(Integer::valueOf)
				.collect(Collectors.toUnmodifiableMap(list::get, Function.identity()));
	}

	private static Stream<Variable<?>> flatten(final Variable<?> object) {
		return Stream.concat(Stream.of(object), object.getChildren().flatMap(VariableTreeData::flatten));
	}

	private static Stream<Map.Entry<Variable<?>, Integer>> flattenDepth(final Variable<?> object, final int depth) {
		return Stream.concat(Stream.of(Map.entry(object, Integer.valueOf(depth))),
				object.getChildren().flatMap(child -> flattenDepth(child, depth + 1)));
	}
}
