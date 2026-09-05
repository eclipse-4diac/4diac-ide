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
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataTypeFactory;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

public class ErrorDataTypeFactoryImpl implements ErrorDataTypeFactory {

	private final DataFactory delegate;

	public ErrorDataTypeFactoryImpl() {
		this(DataFactory.eINSTANCE);
	}

	public ErrorDataTypeFactoryImpl(final DataFactory delegate) {
		this.delegate = delegate;
	}

	@Override
	public ErrorDataType create(final String fullTypeName, final EClass eClass) {
		if (!DataPackage.Literals.DATA_TYPE.isSuperTypeOf(eClass)) {
			throw new IllegalArgumentException("Not a valid class " + eClass); //$NON-NLS-1$
		}
		return createErrorDataType(fullTypeName);
	}

	@Override
	public ErrorDataType createErrorDataType(final String fullTypeName) {
		final ErrorDataType errorDataType = delegate.createErrorDataType();
		PackageNameHelper.setFullTypeName(errorDataType, fullTypeName);
		return errorDataType;
	}
}
