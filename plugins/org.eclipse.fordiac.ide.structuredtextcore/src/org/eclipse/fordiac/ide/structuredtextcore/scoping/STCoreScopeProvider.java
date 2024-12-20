/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies GmbH,
 *               2022-2003 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - literal data types
 *       - add scope for adapter variables
 *       - add scope for internal FBs
 *       - fix scope ignoreCase, cleanup
 *       - exclude events from global scope
 *   Martin Melik Merkumians
 *       - added scope for struct in struct
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.FilteringScope;
import org.eclipse.xtext.scoping.impl.IScopeWrapper;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;
import org.eclipse.xtext.scoping.impl.ScopeBasedSelectable;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * This class contains custom scoping description.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class STCoreScopeProvider extends AbstractSTCoreScopeProvider {
	private static final List<IEObjectDescription> NON_USER_DEFINED_TYPES_DESCRIPTIONS = DataTypeLibrary
			.getNonUserDefinedDataTypes().stream().map(STCoreScopeProvider::descriptionForType).toList();

	private static final List<IEObjectDescription> LITERAL_TYPES_DESCRIPTIONS = Stream
			.concat(NON_USER_DEFINED_TYPES_DESCRIPTIONS.stream(), Stream.of(//
					descriptionForType("D", ElementaryTypes.DATE), //$NON-NLS-1$
					descriptionForType("LD", ElementaryTypes.LDATE), //$NON-NLS-1$
					descriptionForType("T", ElementaryTypes.TIME), //$NON-NLS-1$
					descriptionForType("LT", ElementaryTypes.LTIME) //$NON-NLS-1$
			)).toList();

	private static final Set<EReference> ANY_ELEMENTARY_LITERAL_REFERENCES = Set.of(
			STCorePackage.Literals.ST_NUMERIC_LITERAL__TYPE, STCorePackage.Literals.ST_DATE_LITERAL__TYPE,
			STCorePackage.Literals.ST_TIME_LITERAL__TYPE, STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE,
			STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE, STCorePackage.Literals.ST_STRING_LITERAL__TYPE);

	@Inject
	IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	STStandardFunctionProvider standardFunctionProvider;

	private IScopeWrapper scopeWrapper;

	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STCorePackage.Literals.ST_VAR_DECLARATION__TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			return scopeForNonUserDefinedDataTypes(filterScope(globalScope, this::isApplicableForVariableType));
		}
		if (reference == STCorePackage.Literals.ST_TYPE_DECLARATION__TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			return scopeForNonUserDefinedDataTypes(filterScope(globalScope, this::isApplicableForTypeDeclaration));
		}
		if (isAnyElementaryLiteral(reference)) {
			return new SimpleScope(LITERAL_TYPES_DESCRIPTIONS, true);
		}
		if (reference == STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE) {
			final var receiver = getReceiver(context);
			// if there is a receiver, which is not the same EObject as the context
			if (receiver != null && receiver != context) {
				final var receiverType = receiver.getResultType();
				if (receiverType != null) {
					if (receiverType instanceof final StructuredType structuredVarType) {
						return qualifiedScope(structuredVarType.getMemberVariables(), reference);
					}
					if (receiverType instanceof final FBType fbType) {
						final var interfaceList = fbType.getInterfaceList();
						return qualifiedScope(Iterables.concat(interfaceList.getInputVars(),
								interfaceList.getOutputVars(), interfaceList.getEventInputs()), reference);
					}
				}
				return IScope.NULLSCOPE;
			}
			if (context instanceof final STFeatureExpression expression && expression.isCall()) {
				final List<DataType> argumentTypes = expression.getParameters().stream()
						.map(STCallArgument::getDeclaredResultType).map(DataType.class::cast)
						.map(type -> Objects.requireNonNullElse(type, GenericTypes.ANY)).toList();
				return new STStandardFunctionScope(
						filterScope(super.getScope(context, reference), this::isApplicableForFeatureReference),
						standardFunctionProvider, argumentTypes, true);
			}
			return new STStandardFunctionScope(
					filterScope(super.getScope(context, reference), this::isApplicableForFeatureReference),
					standardFunctionProvider, Collections.emptyList(), true);
		}
		if (reference == STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER) {
			final var feature = getFeature(context);
			if (feature instanceof final ICallable callable) {
				return qualifiedScope(Iterables.concat(callable.getInputParameters(), callable.getInOutParameters()),
						reference);
			}
		} else if (reference == STCorePackage.Literals.ST_CALL_NAMED_OUTPUT_ARGUMENT__PARAMETER) {
			final var feature = getFeature(context);
			if (feature instanceof final ICallable callable) {
				return qualifiedScope(callable.getOutputParameters(), reference);
			}
		} else if (reference == STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE) {
			return filterScope(super.getScope(context, reference), this::isApplicableForVariableReference);
		} else if (reference == STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE) {
			final var container = context.eContainer();
			if (container instanceof final STStructInitializerExpression structInitializerExpression
					&& structInitializerExpression.getResultType() instanceof final StructuredType structType) {
				return qualifiedScope(structType.getMemberVariables(), reference);
			}
		} else if (reference == STCorePackage.Literals.ST_ATTRIBUTE__DECLARATION) {
			return super.getScope(context, reference);
		}
		return super.getScope(context, reference);
	}

	protected static IScope scopeForNonUserDefinedDataTypes(final IScope parent) {
		return new SimpleScope(parent, NON_USER_DEFINED_TYPES_DESCRIPTIONS, true);
	}

	protected IScope qualifiedScope(final Iterable<? extends EObject> elements, final EReference reference) {
		return qualifiedScope(elements, reference, IScope.NULLSCOPE);
	}

	protected IScope qualifiedScope(final Iterable<? extends EObject> elements, final EReference reference,
			final IScope parent) {
		final Iterable<IEObjectDescription> descriptions = Scopes.scopedElementsFor(elements, qualifiedNameProvider);
		final ScopeBasedSelectable importFrom = new ScopeBasedSelectable(wrap(new SimpleScope(descriptions, true)));
		final List<ImportNormalizer> importNormalizers = createImportNormalizers(descriptions);
		if (importNormalizers.isEmpty()) {
			return SelectableBasedScope.createScope(parent, importFrom, reference.getEReferenceType(), true);
		}
		return new ImportScope(importNormalizers, parent, importFrom, reference.getEReferenceType(), true);
	}

	protected static List<ImportNormalizer> createImportNormalizers(final Iterable<IEObjectDescription> descriptions) {
		return StreamSupport.stream(descriptions.spliterator(), false).map(IEObjectDescription::getQualifiedName)
				.map(name -> name.skipLast(1)).filter(java.util.function.Predicate.not(QualifiedName::isEmpty))
				.distinct().map(STCoreScopeProvider::createImportNormalizer).toList();
	}

	protected static ImportNormalizer createImportNormalizer(final QualifiedName importedNamespace) {
		return new ImportNormalizer(importedNamespace, true, true);
	}

	protected static IScope filterScope(final IScope scope, final Predicate<IEObjectDescription> filter) {
		return new FilteringScope(scope, filter);
	}

	protected boolean isApplicableForVariableType(final IEObjectDescription description) {
		final var clazz = description.getEClass();
		return DataPackage.eINSTANCE.getDataType().isSuperTypeOf(clazz)
				|| LibraryElementPackage.eINSTANCE.getFBType().isSuperTypeOf(clazz);
	}

	protected boolean isApplicableForTypeDeclaration(final IEObjectDescription description) {
		final var clazz = description.getEClass();
		return DataPackage.eINSTANCE.getDataType().isSuperTypeOf(clazz);
	}

	protected boolean isApplicableForVariableReference(final IEObjectDescription description) {
		final var clazz = description.getEClass();
		return STCorePackage.eINSTANCE.getSTVarDeclaration().isSuperTypeOf(clazz)
				|| LibraryElementPackage.eINSTANCE.getVarDeclaration().isSuperTypeOf(clazz);
	}

	protected boolean isApplicableForFeatureReference(final IEObjectDescription description) {
		// mirror changes here in
		// ExpressionAnnotations.getResultType(STFeatureExpression)
		final var clazz = description.getEClass();
		return STCorePackage.eINSTANCE.getSTVarDeclaration().isSuperTypeOf(clazz)
				|| LibraryElementPackage.eINSTANCE.getVarDeclaration().isSuperTypeOf(clazz)
				|| LibraryElementPackage.eINSTANCE.getAdapterDeclaration().isSuperTypeOf(clazz)
				|| LibraryElementPackage.eINSTANCE.getFB().isSuperTypeOf(clazz)
				|| (LibraryElementPackage.eINSTANCE.getICallable().isSuperTypeOf(clazz)
						&& (!LibraryElementPackage.eINSTANCE.getFBType().isSuperTypeOf(clazz)
								|| LibraryElementPackage.eINSTANCE.getFunctionFBType().isSuperTypeOf(clazz))
						&& !LibraryElementPackage.eINSTANCE.getEvent().isSuperTypeOf(clazz));
	}

	protected static boolean isAnyElementaryLiteral(final EReference reference) {
		return ANY_ELEMENTARY_LITERAL_REFERENCES.contains(reference);
	}

	protected static STExpression getReceiver(final EObject context) {
		if (context instanceof STFeatureExpression) {
			return getReceiver(context.eContainer());
		}
		if (context instanceof final STMemberAccessExpression memberAccessExpression) {
			return memberAccessExpression.getReceiver();
		}
		return null;
	}

	protected static INamedElement getFeature(final EObject context) {
		if (context instanceof STCallArgument) {
			return getFeature(context.eContainer());
		}
		if (context instanceof final STFeatureExpression featureExpression) {
			return featureExpression.getFeature();
		}
		return null;
	}

	protected static IEObjectDescription descriptionForType(final DataType type) {
		return new EObjectDescription(QualifiedName.create(type.getName()), type, null);
	}

	protected static IEObjectDescription descriptionForType(final String name, final DataType type) {
		return new EObjectDescription(QualifiedName.create(name), type, null);
	}

	@Override
	public void setWrapper(final IScopeWrapper wrapper) {
		super.setWrapper(wrapper);
		this.scopeWrapper = wrapper;
	}

	protected IScope wrap(final IScope scope) {
		return scopeWrapper != null ? scopeWrapper.wrap(scope) : scope;
	}
}
