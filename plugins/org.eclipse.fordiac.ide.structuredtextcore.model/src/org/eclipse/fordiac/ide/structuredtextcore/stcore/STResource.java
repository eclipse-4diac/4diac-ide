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
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public interface STResource {

	String OPTION_EXPECTED_TYPE = STResource.class.getName() + ".EXPECTED_TYPE"; //$NON-NLS-1$

	INamedElement getExpectedType();

	void setExpectedType(INamedElement expectedType);

	INamedElement getExpectedType(STExpression expression);

	INamedElement getExpectedType(STInitializerExpression expression);
}
