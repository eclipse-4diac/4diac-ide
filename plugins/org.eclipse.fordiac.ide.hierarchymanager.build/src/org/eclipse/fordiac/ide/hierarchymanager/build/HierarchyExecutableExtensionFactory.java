/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class HierarchyExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	@Override
	protected Bundle getBundle() {
		return HierarchyBuildPlugin.getInstance().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return HierarchyBuildPlugin.getInstance()
				.getInjector(HierarchyLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_HIERARCHYMANAGER_HIERARCHY);
	}
}
