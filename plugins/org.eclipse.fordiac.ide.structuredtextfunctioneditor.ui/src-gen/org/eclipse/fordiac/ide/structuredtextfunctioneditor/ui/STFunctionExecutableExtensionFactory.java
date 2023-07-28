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
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.internal.StructuredtextfunctioneditorActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class STFunctionExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(StructuredtextfunctioneditorActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		StructuredtextfunctioneditorActivator activator = StructuredtextfunctioneditorActivator.getInstance();
		return activator != null ? activator.getInjector(StructuredtextfunctioneditorActivator.ORG_ECLIPSE_FORDIAC_IDE_STRUCTUREDTEXTFUNCTIONEDITOR_STFUNCTION) : null;
	}

}
