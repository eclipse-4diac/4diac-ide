/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

public class STCoreLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(final ILinkingDiagnosticContext context) {
		if (context.getReference() == STCorePackage.Literals.ST_VAR_DECLARATION__TYPE
				|| context.getReference() == STCorePackage.Literals.ST_TYPE_DECLARATION__TYPE
				|| STCoreScopeProvider.isAnyElementaryLiteral(context.getReference())) {
			return createDataTypeDiagnosticMessage(context);
		}
		if (context.getReference() == STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE) {
			final var receiverType = getReceiverType(context);
			if (context.getContext() instanceof final STFeatureExpression expression && expression.isCall()) {
				final List<INamedElement> argumentTypes = expression.getParameters().stream()
						.map(STCallArgument::getDeclaredResultType).toList();
				return createCallableDiagnosticMessage(context, argumentTypes, receiverType);
			}
			return createVariableDiagnosticMessage(context, receiverType);
		}
		if (context.getReference() == STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER
				|| context.getReference() == STCorePackage.Literals.ST_CALL_NAMED_OUTPUT_ARGUMENT__PARAMETER) {
			final INamedElement feature = STCoreScopeProvider.getFeature(context.getContext());
			return createParameterDiagnosticMessage(context, feature);
		}
		if (context.getReference() == STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE) {
			return createVariableDiagnosticMessage(context, null);
		}
		if (context.getReference() == STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE) {
			final var container = context.getContext().eContainer();
			if (container instanceof final STStructInitializerExpression structInitializerExpression
					&& structInitializerExpression.getResultType() instanceof final StructuredType structType
					&& !structType.eIsProxy()) {
				return createVariableDiagnosticMessage(context, structType);
			}
			return createVariableDiagnosticMessage(context, null);
		}
		return super.getUnresolvedProxyMessage(context);
	}

	protected static INamedElement getReceiverType(final ILinkingDiagnosticContext context) {
		final var receiver = STCoreScopeProvider.getReceiver(context.getContext());
		// if there is a receiver, which is not the same EObject as the context
		if (receiver != null && receiver != context.getContext()) {
			return receiver.getResultType();
		}
		return null;
	}

	protected static DiagnosticMessage createDataTypeDiagnosticMessage(final ILinkingDiagnosticContext context) {
		return new DiagnosticMessage(MessageFormat
				.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedDataType, context.getLinkText()),
				Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
	}

	protected static DiagnosticMessage createVariableDiagnosticMessage(final ILinkingDiagnosticContext context,
			final INamedElement type) {
		if (type != null && !type.eIsProxy()) {
			return new DiagnosticMessage(
					MessageFormat.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedVariableForType,
							context.getLinkText(), type.getQualifiedName()),
					Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
		}
		return new DiagnosticMessage(MessageFormat
				.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedVariable, context.getLinkText()),
				Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
	}

	protected static DiagnosticMessage createCallableDiagnosticMessage(final ILinkingDiagnosticContext context,
			final List<INamedElement> argumentTypes, final INamedElement type) {
		final String argumentTypesString = argumentTypes.stream()
				.map(t -> t != null ? t.getName() : Messages.STCoreLinkingDiagnosticMessageProvider_UnknownType)
				.collect(Collectors.joining(", ")); //$NON-NLS-1$
		if (type != null && !type.eIsProxy()) {
			return new DiagnosticMessage(
					MessageFormat.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedCallableForType,
							context.getLinkText(), argumentTypesString, type.getQualifiedName()),
					Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
		}
		return new DiagnosticMessage(
				MessageFormat.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedCallable,
						context.getLinkText(), argumentTypesString),
				Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
	}

	protected static DiagnosticMessage createParameterDiagnosticMessage(final ILinkingDiagnosticContext context,
			final INamedElement feature) {
		if (feature instanceof final ICallable callable && !callable.eIsProxy()) {
			return new DiagnosticMessage(
					MessageFormat.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedParameterForCallable,
							context.getLinkText(), callable.getQualifiedName()),
					Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
		}
		return new DiagnosticMessage(MessageFormat
				.format(Messages.STCoreLinkingDiagnosticMessageProvider_UndefinedParameter, context.getLinkText()),
				Severity.ERROR, Diagnostic.LINKING_DIAGNOSTIC);
	}
}
