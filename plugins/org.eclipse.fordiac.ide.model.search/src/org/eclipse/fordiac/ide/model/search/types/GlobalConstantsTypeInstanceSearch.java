/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.GlobalConstantsMatcher;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.search.ISearchSupport;
import org.eclipse.fordiac.ide.model.search.LocalLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;

public class GlobalConstantsTypeInstanceSearch extends IEC61499ElementSearch {

	public GlobalConstantsTypeInstanceSearch(final GlobalConstantsEntry gcEntry) {
		super(new LocalLiveSearchContext(gcEntry.getTypeLibrary()),
				createSearchFilter(new GlobalConstantsMatcher(gcEntry)),
				new GlobalConstantsTypeInstanceSearchChildrenProvider());
	}

	public static IEC61499SearchFilter createSearchFilter(final GlobalConstantsMatcher matcher) {
		return searchCandidate -> searchCandidate != null && switch (searchCandidate) {
		case final VarDeclaration varDecl -> isGlobalConstantValue(varDecl, matcher);
		case final Attribute attr -> isGlobalConstantValue(attr, matcher);
		case final Algorithm alg -> isGlobalConstantValue(alg, matcher);
		case final Method meth -> isGlobalConstantValue(meth, matcher);
		case final STFunctionBody func -> isGlobalConstantValue(func, matcher);
		default -> false;
		};
	}

	public static boolean isGlobalConstantValue(final VarDeclaration varDecl, final GlobalConstantsMatcher matcher) {
		if (varDecl.getValue() == null) {
			return false;
		}
		final String value = varDecl.getValue().getValue();
		if (value == null || value.isBlank() || value.chars().noneMatch(Character::isLetter)
				|| VariableOperations.isSimpleInitialValue(varDecl)) {
			return false;
		}

		return isGlobalConstantValue((EObject) varDecl, matcher);
	}

	private static boolean isGlobalConstantValue(final Attribute attr, final GlobalConstantsMatcher matcher) {
		final String value = attr.getValue();
		if (value == null || value.isBlank() || value.chars().noneMatch(Character::isLetter)
				|| VariableOperations.isSimpleAttributeValue(attr)) {
			return false;
		}

		return isGlobalConstantValue((EObject) attr, matcher);
	}

	private static boolean isGlobalConstantValue(final EObject context, final GlobalConstantsMatcher matcher) {
		final ISearchSupport searchSupport = ISearchFactory.createSearchSupport(context,
				context.eClass().getInstanceClass());
		return searchSupport != null && searchSupport.search(matcher).count() > 0;
	}

	private static final class GlobalConstantsTypeInstanceSearchChildrenProvider implements ISearchChildrenProvider {

		@Override
		public boolean hasChildren(final EObject obj) {
			return (obj instanceof FBType) || (obj instanceof AutomationSystem) || (obj instanceof UntypedSubApp)
					|| (obj instanceof final StructuredType) || (obj instanceof final AttributeDeclaration)
					|| (obj instanceof final Application) || (obj instanceof FBNetworkElement)
					|| (obj instanceof IInterfaceElement);
		}

		@Override
		public Stream<? extends EObject> getChildren(final EObject obj) {
			if (obj instanceof final FBType fbType) {
				var stream = SearchChildrenProviderHelper.getFBTypeChildren(fbType);
				if (obj instanceof final BaseFBType base) {
					stream = Stream.concat(stream, base.getAlgorithm().stream());
					stream = Stream.concat(stream, base.getMethods().stream());
				} else if (obj instanceof final FunctionFBType func) {
					stream = Stream.concat(stream, Stream.of(func.getBody()));
				}
				return stream;
			}
			if (obj instanceof final AutomationSystem system) {
				return Stream.concat(system.getAttributes().stream(), system.getApplication().stream());
			}

			if (obj instanceof final Application application) {
				return Stream.concat(application.getFBNetwork().getNetworkElements().stream(),
						application.getAttributes().stream());
			}

			if (obj instanceof final UntypedSubApp untypedSubapp) {
				return SearchChildrenProviderHelper.getUntypedSubappChildren(untypedSubapp);
			}
			if (obj instanceof final StructuredType structType) {
				return SearchChildrenProviderHelper.getStructChildren(structType);
			}
			if (obj instanceof final AttributeDeclaration attrdecl) {
				return SearchChildrenProviderHelper.getAttributeDeclChildren(attrdecl);
			}

			if (obj instanceof final BlockFBNetworkElement elem) {
				return Stream.concat(elem.getAttributes().stream(),
						SearchChildrenProviderHelper.getInterfaceListChildren(elem.getInterface()));
			}

			if (obj instanceof final IInterfaceElement interfaceElement) {
				return interfaceElement.getAttributes().stream();
			}

			if (obj instanceof final ConfigurableObject object) {
				return object.getAttributes().stream();
			}

			return Stream.empty();
		}
	}
}
