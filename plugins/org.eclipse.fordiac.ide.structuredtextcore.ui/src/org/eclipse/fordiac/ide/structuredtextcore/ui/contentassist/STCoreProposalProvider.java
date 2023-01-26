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

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.naming.QualifiedName;
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
						if (current instanceof STFeatureExpression && callable.isPresent()) {
							final var featureExpr = (STFeatureExpression) current;
							final var called = featureExpr.getFeature() instanceof ICallable
									? (ICallable) featureExpr.getFeature()
									: null;
							return isFunction(arg0) || isLocal(arg0, callable.get()) || isParameter(arg0, called);
						}
						if (callable.isPresent()) {
							return isFunction(arg0) || isLocal(arg0, callable.get());
						}
						return false;
					}
				});
	}

	protected Optional<ICallable> getICallable(final EObject object) {
		EObject current = object;
		while (current != null) {
			if (current instanceof ICallable) {
				return Optional.of((ICallable) current);
			}
			current = current.eContainer();
		}
		return Optional.empty();
	}

	protected boolean isFunction(final IEObjectDescription description) {
		return "STStandardFunction".equals(description.getEClass().getName())
				|| "STFunction".equals(description.getEClass().getName());
	}

	protected boolean isLocal(final IEObjectDescription description, final ICallable callable) {
		final var candidateFQN = description.getQualifiedName();
		final var currentCallableName = callable.getName();
		final var candidateFileURI = description.getEObjectURI().trimFragment();
		final var callableFileContextURI = callable.eResource().getURI().trimFragment();
		final var contextFileName = callableFileContextURI.lastSegment();

		var contextName = candidateFQN.getFirstSegment();
		// if the candidate is from an FB's ST section, the relevant information is in
		// the second segment
		if (isFBStructuredTextContext(candidateFQN) && candidateFQN.getSegmentCount() > 1) {
			contextName = candidateFQN.getSegment(1);
			// In case of a method, it needs to be checked that the method is in the same
			// file context (FB definition file)
			if (candidateFQN.getSegmentCount() == 2) {
				return callableFileContextURI.equals(candidateFileURI);
			}
		}
		// if the candidate is not in an ST context, then it needs to be in the same
		// file for FBs (ins, outs, internals)
		else if (contextFileName.contains(contextName)) {
			return true;
		}
		// in other cases, the candidate is a algorithm- or method-specific variable,
		// which needs to be in the file and in the callable context
		return contextName.equals(currentCallableName) && callableFileContextURI.equals(candidateFileURI);
	}

	private boolean isFBStructuredTextContext(final QualifiedName candidateFQN) {
		return "st".contentEquals(candidateFQN.getFirstSegment());
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
