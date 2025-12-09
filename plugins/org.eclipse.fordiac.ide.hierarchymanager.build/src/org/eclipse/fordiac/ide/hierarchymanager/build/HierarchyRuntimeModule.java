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

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

public class HierarchyRuntimeModule extends AbstractGenericResourceRuntimeModule {

	@Override
	protected String getLanguageName() {
		return HierarchyLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_HIERARCHYMANAGER_HIERARCHY;
	}

	@Override
	protected String getFileExtensions() {
		return "hier"; //$NON-NLS-1$
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return DefaultDeclarativeQualifiedNameProvider.class;
	}

	@Override
	public Class<? extends IResourceDescription.Manager> bindIResourceDescription$Manager() {
		return HierarchyResourceDescriptionManager.class;
	}
}
