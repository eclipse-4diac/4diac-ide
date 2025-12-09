/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionManager;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;

public class STAlgorithmResourceDescriptionManager extends STCoreResourceDescriptionManager {

	@Override
	protected IResourceDescription internalGetResourceDescription(final Resource resource,
			final IDefaultResourceDescriptionStrategy strategy) {
		return new STAlgorithmResourceDescription(resource, strategy, getCache(), getNameConverter());
	}
}
