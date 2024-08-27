/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.search.st;

import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.search.ISearchSupport;

public class StructuredTextSearchFactory implements ISearchFactory {

	@Override
	public ISearchSupport createSearchSupport(final Object source) {
		if (source instanceof final STAlgorithm algorithm) {
			return new STAlgorithmSearchSupport(algorithm);
		}
		if (source instanceof final STMethod method) {
			return new STMethodSearchSupport(method);
		}
		if (source instanceof final ECTransition transition) {
			return new ECTransitionSearchSupport(transition);
		}
		if (source instanceof final STFunctionBody functionBody) {
			return new STFunctionBodySearchSupport(functionBody);
		}
		if (source instanceof final VarDeclaration varDeclaration) {
			return new VarDeclarationSearchSupport(varDeclaration);
		}
		if (source instanceof final Attribute attribute) {
			return new AttributeSearchSupport(attribute);
		}
		if (source instanceof final DirectlyDerivedType directlyDerivedType) {
			return new DirectlyDerivedTypeSearchSupport(directlyDerivedType);
		}
		return null;
	}

	public static void register() {
		final StructuredTextSearchFactory factory = new StructuredTextSearchFactory();
		ISearchFactory.Registry.INSTANCE.registerFactory(STAlgorithm.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(ECTransition.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(STMethod.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(STFunctionBody.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(VarDeclaration.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(Attribute.class, factory);
		ISearchFactory.Registry.INSTANCE.registerFactory(DirectlyDerivedType.class, factory);
	}
}
