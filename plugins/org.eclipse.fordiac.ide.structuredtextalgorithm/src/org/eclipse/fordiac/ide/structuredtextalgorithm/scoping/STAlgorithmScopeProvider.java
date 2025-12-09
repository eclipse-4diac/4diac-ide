/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STCoreScopeProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * This class contains custom scoping description.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class STAlgorithmScopeProvider extends AbstractSTAlgorithmScopeProvider {
	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STAlgorithmPackage.Literals.ST_METHOD__RETURN_TYPE) {
			final IScope globalScope = this.delegateGetScope(context, reference);
			return scopeForNonUserDefinedDataTypes(globalScope);
		}
		if (reference == STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE) {
			final STExpression receiver = STCoreScopeProvider.getReceiver(context);
			if (receiver instanceof final STBuiltinFeatureExpression builtinFeatureExpression) {
				final STBuiltinFeature feature = builtinFeatureExpression.getFeature();
				if (feature == STBuiltinFeature.THIS) {
					final STAlgorithmSource source = EcoreUtil2.getContainerOfType(context, STAlgorithmSource.class);
					if (source != null) {
						return qualifiedScope(source.getElements().stream().filter(STMethod.class::isInstance).toList(),
								reference, super.getScope(context, reference));
					}
				}
			}
		}
		return super.getScope(context, reference);
	}

	@Override
	protected boolean isApplicableForVariableReference(final IEObjectDescription description) {
		final EClass clazz = description.getEClass();
		return super.isApplicableForVariableReference(description)
				// ensure the VarDeclaration is not in an internal FB
				&& !(LibraryElementPackage.eINSTANCE.getVarDeclaration().isSuperTypeOf(clazz)
						&& description.getEObjectOrProxy().eContainer() instanceof final InterfaceList interfaceList
						&& interfaceList.eContainer() instanceof FB);
	}

	@Override
	protected boolean isApplicableForFeatureReference(final IEObjectDescription description) {
		final EClass clazz = description.getEClass();
		return super.isApplicableForFeatureReference(description)
				// ensure the VarDeclaration is not in an internal FB
				&& !(LibraryElementPackage.eINSTANCE.getVarDeclaration().isSuperTypeOf(clazz)
						&& description.getEObjectOrProxy().eContainer() instanceof final InterfaceList interfaceList
						&& interfaceList.eContainer() instanceof FB)
				// reject Algorithm from library element
				&& !LibraryElementPackage.eINSTANCE.getAlgorithm().isSuperTypeOf(clazz)
				// reject Method from library element
				&& !LibraryElementPackage.eINSTANCE.getMethod().isSuperTypeOf(clazz)
				// reject STAlgorithm
				&& !STAlgorithmPackage.eINSTANCE.getSTAlgorithm().isSuperTypeOf(clazz);
	}
}
