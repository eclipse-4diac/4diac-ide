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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.refactoring;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCorePartialSerializer;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.formatting2.regionaccess.IEObjectRegion;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.serializer.analysis.IGrammarConstraintProvider.IConstraint;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STFunctionPartialSerializer extends STCorePartialSerializer {

	@Inject
	private STFunctionGrammarAccess grammarAccess;

	@Override
	protected List<SerializationStrategy> trySerializeSingleValue(final EObject owner, final FeatureChange change,
			final IEObjectRegion ownerRegion, final IConstraint constraint) {
		if (owner instanceof final STFunctionSource source
				&& change.getFeature() == STFunctionPackage.Literals.ST_FUNCTION_SOURCE__NAME
				&& source.getName() == null) {
			final List<ISemanticRegion> packageRegions = ownerRegion.getRegionFor()
					.elements(grammarAccess.getSTFunctionSourceAccess().getGroup_1());
			if (!packageRegions.isEmpty()) {
				return List.of(new DeleteRegionsStrategy(packageRegions));
			}
		}
		return super.trySerializeSingleValue(owner, change, ownerRegion, constraint);
	}
}
