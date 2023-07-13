package org.eclipse.fordiac.ide.fb.interpreter.compare;

/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;

public class EventManagerComparisonUtils {

	private EventManagerComparisonUtils() {
		throw new AssertionError("This class cannot be inherited"); //$NON-NLS-1$
	}

	public static Comparison compareEventManager(final Resource left, final Resource right) {
		// Default diff processor
		final IDiffProcessor diffProcessor = new DiffBuilder();
		final IDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor);
		
		final IComparisonScope scope = new DefaultComparisonScope(left, right, null);
		final IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
		registry.clear();
		// Custom Matcher
		final IEObjectMatcher fordiacForteIntepreterMatcher = new FordiacForteInterpreterMatcher();
		final IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		
		@SuppressWarnings("deprecation")
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(fordiacForteIntepreterMatcher,comparisonFactory);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		return EMFCompare.builder()
				.setMatchEngineFactoryRegistry(registry)
				.setDiffEngine(diffEngine).build()
				.compare(scope);
	}

}
