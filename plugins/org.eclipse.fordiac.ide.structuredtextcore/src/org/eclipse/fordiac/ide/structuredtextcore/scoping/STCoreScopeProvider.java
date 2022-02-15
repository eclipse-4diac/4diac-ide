/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
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
 *   Martin Melik Merkumians
 *       - added scope for struct in struct
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/** This class contains custom scoping description.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping on how and when to use it. */
public class STCoreScopeProvider extends AbstractSTCoreScopeProvider {
	private static final EObjectDescription[] ADDITIONAL_LITERAL_TYPES = {
			descriptionForType("D", ElementaryTypes.DATE), //$NON-NLS-1$
			descriptionForType("LD", ElementaryTypes.LDATE), //$NON-NLS-1$
			descriptionForType("T", ElementaryTypes.TIME),  //$NON-NLS-1$
			descriptionForType("LT", ElementaryTypes.LTIME) //$NON-NLS-1$
	};

	private static boolean isAnyElementaryLiteral(final EReference reference) {
		return (reference == STCorePackage.Literals.ST_NUMERIC_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_DATE_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_TIME_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_STRING_LITERAL__TYPE);
	}

	private STExpression getReceiver(final EObject context) {
		if (context instanceof STFeatureExpression) {
			return getReceiver(context.eContainer());
		} else if (context instanceof STMemberAccessExpression) {
			return ((STMemberAccessExpression) context).getReceiver();
		}
		return null;
	}

	private static INamedElement getExpressionType(final STExpression expression) {
		if (expression instanceof STFeatureExpression) {
			final INamedElement feature = ((STFeatureExpression) expression).getFeature();
			if (feature instanceof STVarDeclaration) {
				return ((STVarDeclaration) feature).getType();
			} else if (feature instanceof VarDeclaration) {
				return ((VarDeclaration) feature).getType();
			}
			// TODO function return type

		} else if (expression instanceof STMemberAccessExpression) {
			final STExpression memberExpression = ((STMemberAccessExpression) expression).getMember();
			return getExpressionType(memberExpression);
		} else if (expression instanceof STBinaryExpression) {
			// ...
		}
		return null;
	}

	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STCorePackage.Literals.ST_VAR_DECLARATION__TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			return new SimpleScope(globalScope, Scopes.scopedElementsFor(DataTypeLibrary.getNonUserDefinedDataTypes(),
					QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)), true);
		} else if (isAnyElementaryLiteral(reference)) {
			return new SimpleScope(Stream.concat(
					StreamSupport.stream(Scopes.scopedElementsFor(DataTypeLibrary.getNonUserDefinedDataTypes(),
							QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)).spliterator(), false),
					Stream.of(ADDITIONAL_LITERAL_TYPES)).collect(Collectors.toList()), true);
		}

		final var receiver = getReceiver(context);
		// if there is a receiver, which is not the same EObject as the context
		if (receiver != null && receiver != context) {
			final var receiverType = getExpressionType(receiver);
			if (receiverType != null) {
				if (receiverType instanceof StructuredType) {
					final var structuredVarType = (StructuredType) receiverType;
					return Scopes.scopeFor(structuredVarType.getMemberVariables());
				}
				return IScope.NULLSCOPE; // If its an elementary type, no scope is needed
			}
		}

		return super.getScope(context, reference);
	}

	private static EObjectDescription descriptionForType(final String name, final DataType type) {
		return new EObjectDescription(QualifiedName.create(name), type, null);
	}
}
