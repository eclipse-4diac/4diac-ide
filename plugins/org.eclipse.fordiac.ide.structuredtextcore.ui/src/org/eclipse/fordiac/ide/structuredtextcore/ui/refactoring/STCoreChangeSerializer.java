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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.xtext.ide.serializer.impl.ChangeSerializer;
import org.eclipse.xtext.ide.serializer.impl.RelatedEmfResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider.RelatedResource;

@SuppressWarnings("restriction")
public class STCoreChangeSerializer extends ChangeSerializer {

	private ResourceSet resourceSet;

	@Override
	protected RelatedResourceUpdater createResourceUpdater(final RelatedResource relatedResource) {
		final URI uri = relatedResource.getUri();
		if (resourceSet.getResourceFactoryRegistry().getFactory(uri) instanceof FordiacTypeResourceFactory) {
			final RelatedResourceUpdater updater = getService(uri, RelatedEmfResourceUpdater.class);
			updater.init(this, resourceSet, relatedResource);
			return updater;
		}
		return super.createResourceUpdater(relatedResource);
	}

	@Override
	protected void beginRecordChanges(final Resource resource) {
		super.beginRecordChanges(resource);
		resourceSet = resource.getResourceSet();
	}

	@Override
	protected void resetState() {
		super.resetState();
		resourceSet = null;
	}
}
