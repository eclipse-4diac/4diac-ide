/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *******************************************************************************/

/*******************************************************************************
 * Copyright (c) 2012, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     Alexandra Buzila - Bug 450360
 *     Philip Langer - Bug 460778
 *     Stefan Dirix - Bugs 457652, 461011 and 461291
 *     Michael Oberlehner - Removed google guava dependecy
 *******************************************************************************/

/*
 * This is a copy of org.eclipse.emf.compare.match.eobject.IEObjectMatcher.class to avoid google dependencies
 *
 */

package org.eclipse.fordiac.ide.emf.compare.MatchEngine;

import static org.eclipse.emf.compare.EMFCompare.DIAGNOSTIC_SOURCE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ComparisonCanceledException;
import org.eclipse.emf.compare.EMFCompareMessages;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.eobject.EObjectIndex.Side;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xmi.XMIResource;

/**
 * This implementation of an {@link IEObjectMatcher} will create {@link Match}es
 * based on the input EObjects identifiers (either XMI:ID or attribute ID)
 * alone.
 *
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class FordiacIdentifierEObjectMatcher implements IEObjectMatcher {
	/**
	 * This instance might have a delegate matcher. The delegate matcher will be
	 * called when no ID is found and its results are aggregated with the current
	 * matcher.
	 */
	private final java.util.Optional<IEObjectMatcher> delegate;

	/**
	 * This will be used to determine what represents the "identifier" of an
	 * EObject. By default, we will use the following logic, in order (i.e. if
	 * condition 1 is fulfilled, we will not try conditions 2 and 3) :
	 * <ol>
	 * <li>If the given eObject is a proxy, it is uniquely identified by its URI
	 * fragment.</li>
	 * <li>If the eObject is located in an XMI resource and has an XMI ID, use this
	 * as its unique identifier.</li>
	 * <li>If the eObject's EClass has an eIdAttribute set, use this attribute's
	 * value.</li>
	 * </ol>
	 */
	private Function<EObject, String> idComputation = new DefaultIDFunction();

	/** A diagnostic to be used for reporting on the matches. */
	private BasicDiagnostic diagnostic;

	/** Creates an ID based matcher without any delegate. */
	public FordiacIdentifierEObjectMatcher() {
		this(null, new DefaultIDFunction());
	}

	/**
	 * Creates an ID based matcher with the given delegate when no ID can be found.
	 *
	 * @param delegateWhenNoID the matcher to delegate to when no ID is found.
	 */
	public FordiacIdentifierEObjectMatcher(final IEObjectMatcher delegateWhenNoID) {
		this(delegateWhenNoID, new DefaultIDFunction());
	}

	/**
	 * Creates an ID based matcher computing the ID with the given function.
	 *
	 * @param idComputation the function used to compute the ID.
	 */
	public FordiacIdentifierEObjectMatcher(final Function<EObject, String> idComputation) {
		this(null, idComputation);
	}

	/**
	 * Create an ID based matcher with a delegate which is going to be called when
	 * no ID is found for a given EObject. It is computing the ID with the given
	 * function
	 *
	 * @param delegateWhenNoID the delegate matcher to use when no ID is found.
	 * @param idComputation    the function used to compute the ID.
	 */
	public FordiacIdentifierEObjectMatcher(final IEObjectMatcher delegateWhenNoID,
			final Function<EObject, String> idComputation) {
		this.delegate = Optional.ofNullable(delegateWhenNoID);
		this.idComputation = idComputation;
	}

	/** {@inheritDoc} */
	@Override
	public void createMatches(final Comparison comparison, final Iterator<? extends EObject> leftEObjects,
			final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
			final Monitor monitor) {
		if (monitor.isCanceled()) {
			throw new ComparisonCanceledException();
		}
		final List<EObject> leftEObjectsNoID = new ArrayList<>();
		final List<EObject> rightEObjectsNoID = new ArrayList<>();
		final List<EObject> originEObjectsNoID = new ArrayList<>();

		diagnostic = new BasicDiagnostic(Diagnostic.OK, DIAGNOSTIC_SOURCE, 0,
				EMFCompareMessages.getString("IdentifierEObjectMatcher.diagnosticMessage"), null); //$NON-NLS-1$

		// TODO Change API to pass the monitor to matchPerId()
		final Set<Match> matches = matchPerId(leftEObjects, rightEObjects, originEObjects, leftEObjectsNoID,
				rightEObjectsNoID, originEObjectsNoID);

		addDiagnostic(comparison);
		comparison.getMatches().addAll(matches);

		if (!leftEObjectsNoID.isEmpty() || !rightEObjectsNoID.isEmpty() || !originEObjectsNoID.isEmpty()) {
			if (delegate.isPresent()) {
				doDelegation(comparison, leftEObjectsNoID, rightEObjectsNoID, originEObjectsNoID, monitor);
			} else {
				for (final EObject eObject : leftEObjectsNoID) {
					if (monitor.isCanceled()) {
						throw new ComparisonCanceledException();
					}
					final Match match = CompareFactory.eINSTANCE.createMatch();
					match.setLeft(eObject);
					matches.add(match);
				}
				for (final EObject eObject : rightEObjectsNoID) {
					if (monitor.isCanceled()) {
						throw new ComparisonCanceledException();
					}
					final Match match = CompareFactory.eINSTANCE.createMatch();
					match.setRight(eObject);
					matches.add(match);
				}
				for (final EObject eObject : originEObjectsNoID) {
					if (monitor.isCanceled()) {
						throw new ComparisonCanceledException();
					}
					final Match match = CompareFactory.eINSTANCE.createMatch();
					match.setOrigin(eObject);
					matches.add(match);
				}
			}
		}
	}

	/**
	 * Execute matching process for the delegated IEObjectMatcher.
	 *
	 * @param comparison         the comparison object that contains the matches
	 * @param monitor            the monitor instance to track the matching progress
	 * @param leftEObjectsNoID   remaining left objects after matching
	 * @param rightEObjectsNoID  remaining right objects after matching
	 * @param originEObjectsNoID remaining origin objects after matching
	 */
	protected void doDelegation(final Comparison comparison, final List<EObject> leftEObjectsNoID,
			final List<EObject> rightEObjectsNoID, final List<EObject> originEObjectsNoID, final Monitor monitor) {
		delegate.get().createMatches(comparison, leftEObjectsNoID.iterator(), rightEObjectsNoID.iterator(),
				originEObjectsNoID.iterator(), monitor);
	}

	/**
	 * Matches the EObject per ID.
	 *
	 * @param leftEObjects       the objects to match (left side).
	 * @param rightEObjects      the objects to match (right side).
	 * @param originEObjects     the objects to match (origin side).
	 * @param leftEObjectsNoID   remaining left objects after matching
	 * @param rightEObjectsNoID  remaining right objects after matching
	 * @param originEObjectsNoID remaining origin objects after matching
	 * @return the match built in the process.
	 */
	protected Set<Match> matchPerId(final Iterator<? extends EObject> leftEObjects,
			final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
			final List<EObject> leftEObjectsNoID, final List<EObject> rightEObjectsNoID,
			final List<EObject> originEObjectsNoID) {

		final MatchComputation computation = new MatchComputation(leftEObjects, rightEObjects, originEObjects,
				leftEObjectsNoID, rightEObjectsNoID, originEObjectsNoID);
		computation.compute();
		return computation.getMatches();
	}

	/**
	 * This method is used to determine the parent objects during matching. The
	 * default implementation of this method returns the eContainer of the given
	 * {@code eObject}. Can be overwritten by clients to still allow proper matching
	 * when using a more complex architecture.
	 *
	 * @param eObject The {@link EObject} for which the parent object is to
	 *                determine.
	 * @return The parent of the given {@code eObject}.
	 * @since 3.2
	 */
	protected static EObject getParentEObject(final EObject eObject) {
		final EObject parent;
		if (eObject != null) {
			parent = eObject.eContainer();
		} else {
			parent = null;
		}
		return parent;
	}

	/**
	 * Adds a warning diagnostic to the comparison for the duplicate ID.
	 *
	 * @param side    the side where the duplicate ID was found
	 * @param eObject the element with the duplicate ID
	 */
	private void reportDuplicateID(final Side side, final EObject eObject) {
		final String duplicateID = idComputation.apply(eObject);
		final String sideName = side.name().toLowerCase();
		final String uriString = getUriString(eObject);
		final String message;
		if (uriString != null) {
			message = EMFCompareMessages.getString("IdentifierEObjectMatcher.duplicateIdWithResource", //$NON-NLS-1$
					duplicateID, sideName, uriString);
		} else {
			message = EMFCompareMessages.getString("IdentifierEObjectMatcher.duplicateId", //$NON-NLS-1$
					duplicateID, sideName);
		}
		diagnostic.add(new BasicDiagnostic(Diagnostic.WARNING, DIAGNOSTIC_SOURCE, 0, message, null));
	}

	/**
	 * Returns a String representation of the URI of the given {@code eObject}'s
	 * resource.
	 * <p>
	 * If the {@code eObject}'s resource or its URI is <code>null</code>, this
	 * method returns <code>null</code>.
	 * </p>
	 *
	 * @param eObject The {@link EObject} for which's resource the string
	 *                representation of its URI is determined.
	 * @return A String representation of the given {@code eObject}'s resource URI.
	 */
	private static String getUriString(final EObject eObject) {
		String uriString = null;
		final Resource resource = eObject.eResource();
		if (resource != null && resource.getURI() != null) {
			final URI uri = resource.getURI();
			if (uri.isPlatform()) {
				uriString = uri.toPlatformString(true);
			} else {
				uriString = uri.toString();
			}
		}
		return uriString;
	}

	/**
	 * Adds the diagnostic to the comparison.
	 *
	 * @param comparison the comparison
	 */
	private void addDiagnostic(final Comparison comparison) {
		if (comparison.getDiagnostic() == null) {
			comparison.setDiagnostic(diagnostic);
		} else {
			((BasicDiagnostic) comparison.getDiagnostic()).merge(diagnostic);
		}
	}

	/**
	 * The default function used to retrieve IDs from EObject. You might want to
	 * extend or compose with it if you want to reuse its behavior.
	 */
	public static class DefaultIDFunction implements Function<EObject, String> {
		/**
		 * Return an ID for an EObject, null if not found.
		 *
		 * @param eObject The EObject for which we need an identifier.
		 * @return The identifier for that EObject if we could determine one.
		 *         <code>null</code> if no condition (see description above) is
		 *         fulfilled for the given eObject.
		 */
		@Override
		public String apply(final EObject eObject) {
			final String identifier;
			if (eObject == null) {
				identifier = null;
			} else if (eObject.eIsProxy()) {
				identifier = ((InternalEObject) eObject).eProxyURI().fragment();
			} else {
				final Resource eObjectResource = eObject.eResource();
				final String xmiID;
				if (eObjectResource instanceof XMIResource) {
					xmiID = ((XMIResource) eObjectResource).getID(eObject);
				} else {
					xmiID = null;
				}
				if (xmiID != null) {
					identifier = xmiID;
				} else {
					identifier = EcoreUtil.getID(eObject);
				}
			}
			return identifier;
		}
	}

	/**
	 * Helper class to manage two different maps within one class based on a switch
	 * boolean.
	 *
	 * @param <K> The class used as key in the internal maps.
	 * @param <V> The class used as value in the internal maps.
	 */
	private class SwitchMap<K, V> {

		/** Map used when the switch boolean is true. */
		final Map<K, V> trueMap = new HashMap<>();

		/** Map used when the switch boolean is false. */
		final Map<K, V> falseMap = new HashMap<>();

		/**
		 * Puts the key-value pair in the map corresponding to the switch.
		 *
		 * @param switcher The boolean variable defining which map is to be used.
		 * @param key      The key which is to be put into a map.
		 * @param value    The value which is to be put into a map.
		 * @return {@code true} if the key was already contained in the chosen map,
		 *         {@code false} otherwise.
		 */
		public boolean put(final boolean switcher, final K key, final V value) {
			final Map<K, V> selectedMap = getMap(switcher);
			final boolean isContained = selectedMap.containsKey(key);
			selectedMap.put(key, value);
			return isContained;
		}

		/**
		 * Returns the value mapped to key.
		 *
		 * @param switcher The boolean variable defining which map is to be used.
		 * @param key      The key for which the value is looked up.
		 * @return The value {@link V} if it exists, {@code null} otherwise.
		 */
		public V get(final boolean switcher, final K key) {
			final Map<K, V> selectedMap = getMap(switcher);
			return selectedMap.get(key);
		}

		/**
		 * Selects the map based on the given boolean.
		 *
		 * @param switcher Defined which map is to be used.
		 * @return {@link #trueMap} if {@code switcher} is true, {@link #falseMap}
		 *         otherwise.
		 */
		private Map<K, V> getMap(final boolean switcher) {
			if (switcher) {
				return falseMap;
			}
			return trueMap;
		}
	}

	/**
	 * Computes matches from eObjects. We'll only iterate once on each of the three
	 * sides, building the matches as we go.
	 *
	 * @author <a href="mailto:axel.richard@obeo.fr">Axel Richard</a>
	 */
	private class MatchComputation {

		/** Matches created by the {@link #compute()} process. */
		private final Set<Match> matches;

		/**
		 * We will try and mimic the structure of the input model. These maps do not
		 * need to be ordered, we only need fast lookup. Map each match to its left
		 * eObject.
		 */
		private final Map<EObject, Match> leftEObjectsToMatch;

		/** Map each match to its right eObject. */
		private final Map<EObject, Match> rightEObjectsToMatch;

		/** Map each match to its origin eObject. */
		private final Map<EObject, Match> originEObjectsToMatch;

		/** Left eObjects to match. */
		private final Iterator<? extends EObject> leftEObjects;

		/** Right eObjects to match. */
		private final Iterator<? extends EObject> rightEObjects;

		/** Origin eObjects to match. */
		private final Iterator<? extends EObject> originEObjects;

		/** Remaining left objects after matching. */
		private final List<EObject> leftEObjectsNoID;

		/** Remaining right objects after matching. */
		private final List<EObject> rightEObjectsNoID;

		/** Remaining origin objects after matching. */
		private final List<EObject> originEObjectsNoID;

		/**
		 * This lookup map will be used by iterations on right and origin to find the
		 * match in which they should add themselves.
		 */
		private final SwitchMap<String, Match> idProxyMap;

		/**
		 * Constructor.
		 *
		 * @param leftEObjects       Left eObjects to match.
		 * @param rightEObjects      Right eObjects to match.
		 * @param originEObjects     Origin eObjects to match.
		 * @param leftEObjectsNoID   Remaining left objects after matching.
		 * @param rightEObjectsNoID  Remaining left objects after matching.
		 * @param originEObjectsNoID Remaining left objects after matching.
		 */
		MatchComputation(final Iterator<? extends EObject> leftEObjects,
				final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
				final List<EObject> leftEObjectsNoID, final List<EObject> rightEObjectsNoID,
				final List<EObject> originEObjectsNoID) {
			this.matches = new LinkedHashSet<>();
			this.leftEObjectsToMatch = new HashMap<>();
			this.rightEObjectsToMatch = new HashMap<>();
			this.originEObjectsToMatch = new HashMap<>();
			this.idProxyMap = new SwitchMap<>();
			this.leftEObjects = leftEObjects;
			this.rightEObjects = rightEObjects;
			this.originEObjects = originEObjects;
			this.leftEObjectsNoID = leftEObjectsNoID;
			this.rightEObjectsNoID = rightEObjectsNoID;
			this.originEObjectsNoID = originEObjectsNoID;
		}

		/**
		 * Returns the matches created by this computation.
		 *
		 * @return the matches created by this computation.
		 */
		public Set<Match> getMatches() {
			return matches;
		}

		/** Computes matches. */
		public void compute() {
			computeLeftSide();
			computeRightSide();
			computeOriginSide();
			reorganizeMatches();
		}

		/** Computes matches for left side. */
		private void computeLeftSide() {
			while (leftEObjects.hasNext()) {
				final EObject left = leftEObjects.next();
				final String identifier = idComputation.apply(left);
				if (identifier != null) {
					final Match match = CompareFactory.eINSTANCE.createMatch();
					match.setLeft(left);
					// Can we find a parent? Assume we're iterating in containment order
					final EObject parentEObject = getParentEObject(left);
					final Match parent = leftEObjectsToMatch.get(parentEObject);
					if (parent != null) {
						((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
					} else {
						matches.add(match);
					}
					final boolean isAlreadyContained = idProxyMap.put(left.eIsProxy(), identifier, match);
					if (isAlreadyContained) {
						if (match.getLeft().hashCode() == idProxyMap.get(left.eIsProxy(), identifier).getLeft()
								.hashCode()) {
							reportDuplicateID(Side.LEFT, left);
						} else {
							System.out.println("Something wrong with; " + match.getLeft().toString());
						}
					}
					leftEObjectsToMatch.put(left, match);
				} else {
					leftEObjectsNoID.add(left);
				}
			}
		}

		/** Computes matches for right side. */
		private void computeRightSide() {
			while (rightEObjects.hasNext()) {
				final EObject right = rightEObjects.next();
				// Do we have an existing match?
				final String identifier = idComputation.apply(right);
				if (identifier != null) {
					Match match = idProxyMap.get(right.eIsProxy(), identifier);
					if (match != null) {
						if (match.getRight() != null) {
							reportDuplicateID(Side.RIGHT, right);
						}
						match.setRight(right);
						rightEObjectsToMatch.put(right, match);
					} else {
						// Otherwise, create and place it.
						match = CompareFactory.eINSTANCE.createMatch();
						match.setRight(right);
						// Can we find a parent?
						final EObject parentEObject = getParentEObject(right);
						final Match parent = rightEObjectsToMatch.get(parentEObject);
						if (parent != null) {
							((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
						} else {
							matches.add(match);
						}
						rightEObjectsToMatch.put(right, match);
						idProxyMap.put(right.eIsProxy(), identifier, match);
					}
				} else {
					rightEObjectsNoID.add(right);
				}
			}
		}

		/** Computes matches for origin side. */
		private void computeOriginSide() {
			while (originEObjects.hasNext()) {
				final EObject origin = originEObjects.next();
				// Do we have an existing match?
				final String identifier = idComputation.apply(origin);
				if (identifier != null) {
					Match match = idProxyMap.get(origin.eIsProxy(), identifier);
					if (match != null) {
						if (match.getOrigin() != null) {
							reportDuplicateID(Side.ORIGIN, origin);
						}
						match.setOrigin(origin);
						originEObjectsToMatch.put(origin, match);
					} else {
						// Otherwise, create and place it.
						match = CompareFactory.eINSTANCE.createMatch();
						match.setOrigin(origin);
						// Can we find a parent?
						final EObject parentEObject = getParentEObject(origin);
						final Match parent = originEObjectsToMatch.get(parentEObject);
						if (parent != null) {
							((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
						} else {
							matches.add(match);
						}
						idProxyMap.put(origin.eIsProxy(), identifier, match);
						originEObjectsToMatch.put(origin, match);
					}
				} else {
					originEObjectsNoID.add(origin);
				}
			}
		}

		/** Reorganize matches. */
		private void reorganizeMatches() {
			// For all root matches, check if they can be put under another match.
			for (final Match match : Set.copyOf(matches)) {
				EObject parentEObject = getParentEObject(match.getLeft());
				Match parent = leftEObjectsToMatch.get(parentEObject);
				if (parent != null) {
					matches.remove(match);
					((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
				} else {
					parentEObject = getParentEObject(match.getRight());
					parent = rightEObjectsToMatch.get(parentEObject);
					if (parent != null) {
						matches.remove(match);
						((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
					} else {
						parentEObject = getParentEObject(match.getOrigin());
						parent = originEObjectsToMatch.get(parentEObject);
						if (parent != null) {
							matches.remove(match);
							((InternalEList<Match>) parent.getSubmatches()).addUnique(match);
						}
					}
				}
			}
		}
	}
}
