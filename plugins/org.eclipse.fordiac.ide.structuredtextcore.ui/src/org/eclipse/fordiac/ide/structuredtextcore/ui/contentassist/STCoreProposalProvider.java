/**
 * Copyright (c) 2021 Primetals Technologies GmbH
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Predicate;

/**
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
public class STCoreProposalProvider extends AbstractSTCoreProposalProvider {

	@Override
	public void completeSTFeatureExpression_Feature(final EObject model, final Assignment assignment,
			final ContentAssistContext context, final ICompletionProposalAcceptor acceptor) {
		lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor,
				new Predicate<IEObjectDescription>() {
					@Override
					public boolean apply(final IEObjectDescription arg0) {
						final var current = context.getCurrentModel();
						final var callable = getICallable(current);
						if (current instanceof ICallable || current instanceof STStatement) {
							return isFunction(arg0) || isLocal(arg0, callable);
						}
						if (current instanceof STFeatureExpression) {
							final var featureExpr = (STFeatureExpression) current;
							final var called = featureExpr.getFeature() instanceof ICallable
									? (ICallable) featureExpr.getFeature()
									: null;
							return isFunction(arg0) || isLocal(arg0, callable) || isParameter(arg0, called);
						}
						return true;
					}
				});
	}

	protected ICallable getICallable(final EObject object) {
		EObject current = object;
		while (current != null) {
			if (current instanceof ICallable) {
				return (ICallable) current;
			}
			current = current.eContainer();
		}
		return null;
	}

	protected boolean isFunction(final IEObjectDescription description) {
		return "STStandardFunction".equals(description.getEClass().getName())
				|| "STFunction".equals(description.getEClass().getName());
	}

	protected boolean isLocal(final IEObjectDescription description, final ICallable callable) {
		final var candidateFQN = description.getQualifiedName();
		final var currentCallableName = callable.getName();
		return candidateFQN.getFirstSegment().equals(currentCallableName);
	}

	protected boolean isParameter(final IEObjectDescription description, final ICallable callable) {
		final var candidateFQN = description.getQualifiedName();
		if (callable == null || candidateFQN.getSegmentCount() > 1) { // Filter FQNs, we only need simple names
			return false;
		}
		final var candidateSimpleName = candidateFQN.getLastSegment();
		final var isInput = callable.getInputParameters().stream()
				.anyMatch(parameter -> parameter.getName().equals(candidateSimpleName));
		final var isOutput = callable.getOutputParameters().stream()
				.anyMatch(parameter -> parameter.getName().equals(candidateSimpleName));
		final var isInOut = callable.getInOutParameters().stream()
				.anyMatch(parameter -> parameter.getName().equals(candidateSimpleName));
		return isInOut || isInput || isOutput;

	}
}
