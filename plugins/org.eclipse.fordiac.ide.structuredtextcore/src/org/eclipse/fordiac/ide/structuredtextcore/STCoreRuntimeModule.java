/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - suppress EObject validation errors
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters;
import org.eclipse.xtext.conversion.IValueConverterService;

/** Use this class to register components to be used at runtime / without the Equinox extension registry. */
public class STCoreRuntimeModule extends AbstractSTCoreRuntimeModule {
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return STCoreValueConverters.class;
	}
}
