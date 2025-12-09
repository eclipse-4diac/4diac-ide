/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.internal.StructuredtextalgorithmActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class STAlgorithmExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(StructuredtextalgorithmActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		StructuredtextalgorithmActivator activator = StructuredtextalgorithmActivator.getInstance();
		return activator != null ? activator.getInjector(StructuredtextalgorithmActivator.ORG_ECLIPSE_FORDIAC_IDE_STRUCTUREDTEXTALGORITHM_STALGORITHM) : null;
	}

}
