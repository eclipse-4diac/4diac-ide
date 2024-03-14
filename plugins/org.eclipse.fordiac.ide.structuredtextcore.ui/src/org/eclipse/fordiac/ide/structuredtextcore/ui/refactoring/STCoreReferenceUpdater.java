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

import org.eclipse.xtext.ide.serializer.hooks.IReferenceUpdaterContext;
import org.eclipse.xtext.ide.serializer.impl.ReferenceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider.RelatedResource;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreReferenceUpdater extends ReferenceUpdater {

	@Inject
	private STCoreImportUpdater importUpdater;

	@Override
	protected void updateExternalReferences(final IReferenceUpdaterContext context,
			final RelatedResource relatedResource) {
		super.updateExternalReferences(context, relatedResource);
		context.modifyModel(() -> importUpdater.updateImports(context));
	}
}
