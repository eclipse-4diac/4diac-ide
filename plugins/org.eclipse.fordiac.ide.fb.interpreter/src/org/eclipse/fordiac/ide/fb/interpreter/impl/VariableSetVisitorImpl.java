/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.impl;

import java.util.function.Function;

import org.eclipse.fordiac.ide.fb.interpreter.api.IVariableSetVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ArrayVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable;

public class VariableSetVisitorImpl implements IVariableSetVisitor {

	public final Variable variable;

	public VariableSetVisitorImpl(Variable variable) {
		this.variable = variable;
	}

	public static Function<Object, Object> of(IVariableSetVisitor variableVisitor, Object object) {
		return new LambdaVisitor<>()
				.on(PrimaryVariable.class).then(p -> variableVisitor.setValuePrimary(object))
				.on(ArrayVariable.class).then(a -> variableVisitor.setValueArray(object))
				;
	}

	public static void setVariable(Variable variable, Object object) {
		VariableSetVisitorImpl.of(new VariableSetVisitorImpl(variable), object).apply(variable);
	}

	@Override
	public PrimaryVariable setValuePrimary(Object object) {
		((PrimaryVariable) variable).getVar().getValue().setValue(object.toString());
		return (PrimaryVariable) variable;
	}

	@Override
	public ArrayVariable setValueArray(Object object) {
		throw new UnsupportedOperationException("Not supported");	 //$NON-NLS-1$
	}
}
