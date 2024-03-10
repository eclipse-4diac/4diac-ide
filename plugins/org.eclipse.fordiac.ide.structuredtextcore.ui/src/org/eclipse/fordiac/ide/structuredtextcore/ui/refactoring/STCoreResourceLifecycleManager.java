/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider.RelatedResource;
import org.eclipse.xtext.ide.serializer.impl.ResourceLifecycleManager;

@SuppressWarnings("restriction")
public class STCoreResourceLifecycleManager extends ResourceLifecycleManager {

	@Override
	public Resource openAndApplyReferences(final ResourceSet resourceSet, final RelatedResource toLoad) {
		final Resource resource = super.openAndApplyReferences(resourceSet, toLoad);
		if (resource instanceof final STCoreResource coreResource) {
			coreResource.updateExpectedType();
		}
		return resource;
	}
}
