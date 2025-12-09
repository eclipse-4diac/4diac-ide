/**
 * Copyright (c) 2022 Primetals Technologies GmbH
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.fordiac.ide.structuredtextcore.DTPLanguageConstants;
import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class DTPExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	@Override
	protected Bundle getBundle() {
		return StructuredtextcoreActivator.getInstance().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return StructuredtextcoreActivator.getInstance()
				.getInjector(DTPLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_MODEL_DATATYPE_DTP);
	}
}
