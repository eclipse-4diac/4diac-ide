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
package org.eclipse.fordiac.ide.fb.interpreter.api;

import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StringLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.TimeLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;

public interface IEvaluateExpressionVisitor {

	//Expressions
	Object evaluate(BinaryExpression binaryExpression);

	Object evaluate(UnaryExpression binaryExpression);

	//Constants
	Boolean evaluate(BoolLiteral boolLiteral);

	String evaluate(StringLiteral stringLiteral);

	String evaluate(TimeLiteral timeLiteral);

	//NumericLiteral
	long evaluate(IntLiteral intLiteral);

	double evaluate(RealLiteral realLiteral);

	//Variables
	Object evaluate(AdapterVariable variable);

	Object evaluate(PrimaryVariable variable);
}
