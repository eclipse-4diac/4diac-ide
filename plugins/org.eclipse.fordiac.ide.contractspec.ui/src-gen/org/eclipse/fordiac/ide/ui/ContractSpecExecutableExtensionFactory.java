/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.contractspec.ui.internal.ContractspecActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class ContractSpecExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(ContractspecActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		ContractspecActivator activator = ContractspecActivator.getInstance();
		return activator != null ? activator.getInjector(ContractspecActivator.ORG_ECLIPSE_FORDIAC_IDE_CONTRACTSPEC) : null;
	}

}
