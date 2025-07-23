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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;

public class STCoreResourceDescriptionManager extends DefaultResourceDescriptionManager {

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Override
	protected IResourceDescription internalGetResourceDescription(final Resource resource,
			final IDefaultResourceDescriptionStrategy strategy) {
		return new STCoreResourceDescription(resource, strategy, getCache(), nameConverter);
	}

	public IQualifiedNameConverter getNameConverter() {
		return nameConverter;
	}
}
