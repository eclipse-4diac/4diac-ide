package org.eclipse.fordiac.ide.fb.interpreter.compare;

import java.util.Iterator;
import java.util.function.BiFunction;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

public class FordiacForteInterpreterMatcher implements IEObjectMatcher {

	@Override
	public void createMatches(final Comparison comparison, final Iterator<? extends EObject> leftEObjects,
			final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
			final Monitor monitor) {
		// create the root match from EventManagers
		final EventManager rightEventManager = getManagerFromIterator(rightEObjects);
		final EventManager leftEventManager = getManagerFromIterator(leftEObjects);
		final Match rootMatch = createMatch(leftEventManager, rightEventManager);

		matchList(leftEventManager.getTransactions(), rightEventManager.getTransactions(), rootMatch,
				FordiacForteInterpreterMatcher::matchTransaction);

		comparison.getMatches().add(rootMatch);
	}

	private static Match matchTransaction(final Transaction left, final Transaction right) {
		final Match match = createMatch(left, right);

		if (left instanceof final FBTransaction fbL && right instanceof final FBTransaction fbR) {
			final Match m = matchEventOccurrence(fbL.getInputEventOccurrence(), fbR.getInputEventOccurrence());
			match.getSubmatches().add(m);

			matchList(fbL.getOutputEventOccurrences(), fbR.getOutputEventOccurrences(), match,
					FordiacForteInterpreterMatcher::matchEventOccurrence);
		}
		return match;
	}

	private static Match matchEventOccurrence(final EventOccurrence left, final EventOccurrence right) {
		final Match match = createMatch(left, right);

		if (left != null && right != null) {
			addMatchChecked(left.getFbRuntime(), right.getFbRuntime(), match);
			addMatchChecked(left.getResultFBRuntime(), right.getResultFBRuntime(), match);
		}
		return match;
	}

	private static <T extends EObject> void matchList(final EList<T> left, final EList<T> right, final Match parent,
			final BiFunction<T, T, Match> matcher) {
		int i = 0;

		while (i < left.size() || i < right.size()) {
			final T l = i < left.size() ? left.get(i) : null;
			final T r = i < right.size() ? right.get(i) : null;
			parent.getSubmatches().add(matcher.apply(l, r));
			i++;
		}
	}

	private static void addMatchChecked(final EObject left, final EObject right, final Match parent) {
		if (left != null || right != null) {
			parent.getSubmatches().add(createMatch(left, right));
		}
	}

	private static Match createMatch(final EObject leftEObject, final EObject rightEObject) {
		final Match match = CompareFactory.eINSTANCE.createMatch();
		match.setLeft(leftEObject);
		match.setRight(rightEObject);
		return match;
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
