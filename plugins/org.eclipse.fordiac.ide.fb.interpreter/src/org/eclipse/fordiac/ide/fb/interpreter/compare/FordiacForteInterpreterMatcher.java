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
		// Create the Root Match
		createMatch(leftEventManager, rightEventManager, matches);

		// Iterate over the left objects
		while (leftEObjects.hasNext()) {
			final EObject leftEObject = leftEObjects.next();
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			// Check the type of the element
			if (leftEObject instanceof FBTransaction) {
				createListItemMatch(leftEventManager.getTransactions(), rightEventManager.getTransactions(),
						leftEObject, matches);
			} else if (leftEObject instanceof EventOccurrence) {
				matchEventOccurrence(matches, leftEObject);
			} else if (leftEObject instanceof FBRuntimeAbstract) {
				matchRuntime(matches, leftEObject);
			} else if (leftEObject instanceof final RuntimeMapImpl leftMatch) {
				final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer()))
						.findFirst().orElse(null);
				if ((match != null) && (match.getRight() instanceof final FBNetworkRuntime rightMatch)) {
					createMapKeyMatch(leftMatch, rightMatch, matches);
				}
			}
		}
		// Add all the matches to the comparison object
		Iterables.addAll(comparison.getMatches(), matches);
	}

	private static void matchRuntime(final Set<Match> matches, final EObject leftEObject) {
		final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer())).findFirst()
				.orElse(null);
		if (match != null) {
			// This is to check if this object is an FBRuntime or FBResult
			final EStructuralFeature structFeat = leftEObject.eContainingFeature();
			if (structFeat.getFeatureID() == OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME) {
				if(leftEObject.eClass().equals(((EventOccurrence) match.getRight()).getFbRuntime().eClass())) 
					createMatch(leftEObject, ((EventOccurrence) match.getRight()).getFbRuntime(), matches);
			} else {
				if(leftEObject.eClass().equals(((EventOccurrence) match.getRight()).getResultFBRuntime().eClass())) 
					createMatch(leftEObject, ((EventOccurrence) match.getRight()).getResultFBRuntime(), matches);
			}
		}		
	}

	private static void matchEventOccurrence(final Set<Match> matches, final EObject leftEObject) {
		final Match match = matches.stream().filter(m -> m.getLeft().equals(leftEObject.eContainer())).findFirst()
				.orElse(null);
		if (match != null) {
			// This is to check if this object is an Output or Input EO
			final EStructuralFeature structFeat = leftEObject.eContainingFeature();
			if (structFeat.getFeatureID() == OperationalSemanticsPackage.FB_TRANSACTION__INPUT_EVENT_OCCURRENCE) {
				createMatch(leftEObject, ((FBTransaction) match.getRight()).getInputEventOccurrence(), matches);
			} else {
				createListItemMatch(((FBTransaction) leftEObject.eContainer()).getOutputEventOccurrences(),
						((FBTransaction) match.getRight()).getOutputEventOccurrences(), leftEObject, matches);
			}
		}
	}

	private static void createMapKeyMatch(final RuntimeMapImpl leftRuntimeMap,
			final FBNetworkRuntime rightFBNetworkRuntime, final Set<Match> matches) {
		final var fbRuntimeAbstract = rightFBNetworkRuntime.getTypeRuntimes().get(leftRuntimeMap.getKey());
		if (fbRuntimeAbstract != null) {
			createMatch(leftRuntimeMap, fbRuntimeAbstract.eContainer(), matches);
		}
	}

	private static void createMatch(final EObject leftEObject, final EObject rightEObject, final Set<Match> matches) {
		final Match match = CompareFactory.eINSTANCE.createMatch();
		match.setLeft(leftEObject);
		match.setRight(rightEObject);
		// Add Match
		matches.add(match);
	}

	/* This method matches the left object with the right object in the same position */
	private static void createListItemMatch(final EList<? extends EObject> leftEObjects,
			final EList<? extends EObject> rightEObjects, final EObject leftEObject, final Set<Match> matches) {
		final int index = leftEObjects.indexOf(leftEObject);
		final int size = rightEObjects.size();
		if (index < size) {
			createMatch(leftEObject, rightEObjects.get(index), matches);
		}
	}

	// Match the root objects. EventManager
	private static EventManager getManagerFromIterator(final Iterator<? extends EObject> eObjects) {
		// Get the right EventManager
		if (eObjects.hasNext()) {
			final EObject root = eObjects.next();
			if (root instanceof final EventManager em) {
				return em;
			}
		}
		throw new IllegalArgumentException("EventManager cannot be found"); //$NON-NLS-1$
	}
}
