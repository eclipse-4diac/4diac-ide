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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class SubAppVarDeclarationWatch extends AbstractSubAppInterfaceWatch implements IVarDeclarationWatch {

	public SubAppVarDeclarationWatch(final String name, final VarDeclaration varDeclaration,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(VariableOperations.newVariable(name, VariableOperations.evaluateResultType(varDeclaration)),
				varDeclaration, debugTarget);
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		super.setValue(expression);
		writeWatch();
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		super.setValue(value);
		writeWatch();
	}

	@Override
	public void setValue(final Value value) throws DebugException {
		getInternalVariable().setValue(value);
		fireContentChanged();
		writeWatch();
	}

	@Override
	protected void childValueChanged() throws DebugException {
		writeWatch();
	}

	protected void writeWatch() throws DebugException {
		for (final IVariableWatch watch : getWatches()) {
			((IVarDeclarationWatch) watch).setValue(getInternalVariable().getValue());
		}
	}

	@Override
	public void forceValue(final String value) throws DebugException {
		super.setValue(value);
		writeForce();
	}

	@Override
	public void forceValue(final Value value) throws DebugException {
		getInternalVariable().setValue(value);
		fireContentChanged();
		writeForce();
	}

	protected void writeForce() throws DebugException {
		for (final IVariableWatch watch : getWatches()) {
			((IVarDeclarationWatch) watch).forceValue(getInternalVariable().getValue());
		}
	}

	@Override
	public void clearForce() throws DebugException {
		for (final IVariableWatch watch : getWatches()) {
			((IVarDeclarationWatch) watch).clearForce();
		}
	}

	@Override
	public boolean isForced() {
		return getWatches().stream().map(IVarDeclarationWatch.class::cast).allMatch(IVarDeclarationWatch::isForced);
	}

	@Override
	protected void updateValue(final List<Value> values) {
		// all values must be equal
		if (values.stream().distinct().count() == 1) {
			updateValue(values.getFirst());
		}
	}

	@Override
	public VarDeclaration getWatchedElement() {
		return (VarDeclaration) super.getWatchedElement();
	}

	@Override
	public AnyValue getInternalValue() {
		return (AnyValue) super.getInternalValue();
	}
}
