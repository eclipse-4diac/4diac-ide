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

import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.EventType;

public interface IVariableVisitor {

	Integer parseValue(AnyIntType type);

	Boolean parseValue(BoolType type);

	String parseValue(AnyStringType type);

	Integer parseValue(EventType type);

	void setValue(Object object);
}
