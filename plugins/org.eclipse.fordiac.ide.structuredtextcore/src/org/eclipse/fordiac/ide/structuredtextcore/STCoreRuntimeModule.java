/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH,
 *                          Martin Erich Jobst
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
 *       - add linking diagnostic message provider
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STCoreLinkingDiagnosticMessageProvider;
import org.eclipse.fordiac.ide.structuredtextcore.serializer.STCoreSerializer;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreCustomConfigurableIssueCodesProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
@SuppressWarnings("static-method")
public class STCoreRuntimeModule extends AbstractSTCoreRuntimeModule {
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return STCoreValueConverters.class;
	}

	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return STCoreLinkingDiagnosticMessageProvider.class;
	}

	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return STCoreSerializer.class;
	}

	@Override
	public Class<? extends ConfigurableIssueCodesProvider> bindConfigurableIssueCodesProvider() {
		return STCoreCustomConfigurableIssueCodesProvider.class;
	}
}
