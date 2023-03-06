package org.eclipse.fordiac.ide.fb.interpreter.compare;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ComparisonCanceledException;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.RuntimeMapImpl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class FordiacForteInterpreterMatcher implements IEObjectMatcher {

	@Override
	public void createMatches(final Comparison comparison, final Iterator<? extends EObject> leftEObjects,
			final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
			final Monitor monitor) {
		// Store all the matches
		final Set<Match> matches = Sets.newLinkedHashSet();
		// EventManager
		final EventManager rightEventManager = getManagerFromIterator(rightEObjects);
		final EventManager leftEventManager = getManagerFromIterator(leftEObjects);
		//Create the Root Match
		createMatch(leftEventManager, rightEventManager, matches);

		// Iterate over the left objects
		while (leftEObjects.hasNext()) {
			final EObject leftEObject = leftEObjects.next();
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}

			if (leftEObject instanceof FBTransaction) {
				createListItemMatch(leftEventManager.getTransactions(), rightEventManager.getTransactions(),
						leftEObject, matches);
			} else if (leftEObject instanceof EventOccurrence) {
				final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer()))
						.findFirst()
						.orElse(null);
				if (match != null) {
					final EStructuralFeature structFeat = leftEObject.eContainingFeature();
					if (structFeat
							.getFeatureID() == OperationalSemanticsPackage.FB_TRANSACTION__INPUT_EVENT_OCCURRENCE) {
						createMatch(leftEObject, ((FBTransaction) match.getRight()).getInputEventOccurrence(), matches);
					} else {
						createListItemMatch(((FBTransaction) leftEObject.eContainer()).getOutputEventOccurrences(),
								((FBTransaction) match.getRight()).getOutputEventOccurrences(), leftEObject, matches);
					}
				} else {
					// createMatch(leftEObject, null, matches);
				}
			} else if (leftEObject instanceof FBRuntimeAbstract) {
				final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer()))
						.findFirst()
						.orElse(null);
				if (match != null) {
					createMatch(leftEObject, ((EventOccurrence)match.getRight()).getFbRuntime(), matches);
				} else {
					// createMatch(leftEObject, null, matches);
				}
			} else if (leftEObject instanceof RuntimeMapImpl) {
				final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer()))
						.findFirst().orElse(null);
				if (match != null) {
					createMapKeyMatch((RuntimeMapImpl) leftEObject, (FBNetworkRuntime) match.getRight(), matches);
				} else {
					// createMatch(leftEObject, null, matches);
				}
			}
		}
		// Add all the matches to the comparison object
		Iterables.addAll(comparison.getMatches(), matches);
	}

	private void createMapKeyMatch(final RuntimeMapImpl leftRuntimeMap, final FBNetworkRuntime rightFBNetworkRuntime,
			final Set<Match> matches) {
		final var fbRuntimeAbstract = rightFBNetworkRuntime.getTypeRuntimes()
				.get(leftRuntimeMap.getKey());
		if (fbRuntimeAbstract != null) {
			createMatch(leftRuntimeMap, fbRuntimeAbstract.eContainer(), matches);
		} else {
			// createMatch(leftRuntimeMap, null, matches);
		}
	}

	private void createMatch(final EObject leftEObject, final EObject rightEObject,
			final Set<Match> matches) {
		final Match match = CompareFactory.eINSTANCE.createMatch();
		match.setLeft(leftEObject);
		match.setRight(rightEObject);
		// Add Match
		matches.add(match);
	}

	private void createListItemMatch(final EList<? extends EObject> leftEObjects,
			final EList<? extends EObject> rightEObjects,
			final EObject leftEObject, final Set<Match> matches) {
		final int index = leftEObjects.indexOf(leftEObject);
		final int size = rightEObjects.size();
		if (index < size) {
			createMatch(leftEObject, rightEObjects.get(index), matches);
		} else {
			// createMatch(leftEObject, null, matches);
		}
	}

	private EventManager getManagerFromIterator(final Iterator<? extends EObject> eObjects) {
		// Get the right EventManager
		if (eObjects.hasNext()) {
			final EObject root = eObjects.next();
			if (root instanceof EventManager) {
				return (EventManager) root;
			}
		}
		throw new IllegalArgumentException("EventManager cannot be found"); //$NON-NLS-1$
	}
}
