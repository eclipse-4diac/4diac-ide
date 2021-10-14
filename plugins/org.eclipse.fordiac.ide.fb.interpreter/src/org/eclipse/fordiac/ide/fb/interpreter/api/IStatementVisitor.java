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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;

public interface IStatementVisitor {

	void evaluateAllStatements(EList<Statement> listOfStatements);

	AssignmentStatement evaluate(AssignmentStatement assigment);

	IfStatement evaluate(IfStatement ifAssigment);
}
