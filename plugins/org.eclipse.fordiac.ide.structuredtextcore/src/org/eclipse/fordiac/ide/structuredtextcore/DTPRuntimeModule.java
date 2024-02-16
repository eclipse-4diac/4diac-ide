/**
 * Copyright (c) 2022, 2023 Primetals Technologies GmbH
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
 *   Martin Erich Jobst
 *       - add qualified name bindings
 */
package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameConverter;
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

@SuppressWarnings("static-method")
public class DTPRuntimeModule extends AbstractGenericResourceRuntimeModule {
	@Override
	protected String getFileExtensions() {
		return "dtp"; //$NON-NLS-1$
	}

	@Override
	protected String getLanguageName() {
		return DTPLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_MODEL_DATATYPE_DTP;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return STCoreQualifiedNameProvider.class;
	}

	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return STCoreQualifiedNameConverter.class;
	}
}
