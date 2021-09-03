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

import org.eclipse.fordiac.ide.fb.interpreter.api.IVariableVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;

public class PrimaryVariableImpl implements IVariableVisitor {

	public final PrimaryVariable primaryVariable;

	public PrimaryVariableImpl(PrimaryVariable primaryVariable) {
		this.primaryVariable = primaryVariable;
	}

	public static Function<Object,Object> of(PrimaryVariable primaryVariable) {
		final var primaryVar= new PrimaryVariableImpl(primaryVariable);
		return of(primaryVar);
	}

	public static Function<Object, Object> of(IVariableVisitor variableVisitor) {
		return new LambdaVisitor<>()
				.on(AnyIntType.class).then(variableVisitor::parseValue)
				.on(BoolType.class).then(variableVisitor::parseValue)
				.on(AnyStringType.class).then(variableVisitor::parseValue)
				.on(EventType.class).then(variableVisitor::parseValue)
				;
	}

	@Override
	public Integer parseValue(AnyIntType type) {
		final VarDeclaration varDeclaration = this.primaryVariable.getVar();
		final int parsedValue = Integer.parseInt(varDeclaration.getValue().getValue());
		return Integer.valueOf(parsedValue);
	}

	@Override
	public Boolean parseValue(BoolType type) {
		return Boolean.valueOf(this.primaryVariable.getVar().getValue().getValue());
	}

	@Override
	public String parseValue(AnyStringType type) {
		return this.primaryVariable.getVar().getValue().getValue();
	}

	@Override
	public Integer parseValue(EventType type) {
		throw new UnsupportedOperationException("Currently the Event Type is not supported"); //$NON-NLS-1$
	}

	@Override
	public void setValue(Object object) {
		this.primaryVariable.getVar().getValue().setValue(object.toString());
	}
}
