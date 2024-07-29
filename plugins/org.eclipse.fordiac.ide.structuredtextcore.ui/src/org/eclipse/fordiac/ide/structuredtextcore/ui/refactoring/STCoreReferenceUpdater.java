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

import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionDiffBuilder;
import org.eclipse.xtext.ide.serializer.hooks.IReferenceUpdaterContext;
import org.eclipse.xtext.ide.serializer.hooks.IUpdatableReference;
import org.eclipse.xtext.ide.serializer.impl.ReferenceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider.RelatedResource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.serializer.tokens.SerializerScopeProviderBinding;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreReferenceUpdater extends ReferenceUpdater {

	@Inject
	private STCoreImportUpdater importUpdater;

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Inject
	@SerializerScopeProviderBinding
	private IScopeProvider scopeProvider;

	@Override
	protected void updateExternalReferences(final IReferenceUpdaterContext context,
			final RelatedResource relatedResource) {
		super.updateExternalReferences(context, relatedResource);
		context.modifyModel(() -> importUpdater.updateImports(context));
	}

	@Override
	public void updateReference(final ITextRegionDiffBuilder rewriter, final IUpdatableReference upd) {
		final IUpdatableReference updatable = upd;
		if (rewriter.isModified(updatable.getReferenceRegion())) {
			return;
		}
		final IScope scope = scopeProvider.getScope(updatable.getSourceEObject(), updatable.getEReference());
		final ISemanticRegion region = updatable.getReferenceRegion();
		final QualifiedName oldName = nameConverter.toQualifiedName(region.getText());
		final IEObjectDescription oldDesc = scope.getSingleElement(oldName);
		if (oldDesc != null && oldDesc.getEObjectOrProxy() == updatable.getTargetEObject()
				&& oldDesc.getName().equals(oldName)) {
			return;
		}
		final String newName = findValidName(updatable, scope);
		if (newName != null && !newName.equals(region.getText())) {
			rewriter.replace(region, newName);
		}
	}
}
