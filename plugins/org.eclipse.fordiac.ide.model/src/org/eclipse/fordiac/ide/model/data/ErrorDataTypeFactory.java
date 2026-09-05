/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.data;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.impl.ErrorDataTypeFactoryImpl;

public interface ErrorDataTypeFactory {

	ErrorDataTypeFactory INSTANCE = new ErrorDataTypeFactoryImpl();

	ErrorDataType create(String fullTypeName, final EClass eClass);

	ErrorDataType createErrorDataType(String fullTypeName);
}
