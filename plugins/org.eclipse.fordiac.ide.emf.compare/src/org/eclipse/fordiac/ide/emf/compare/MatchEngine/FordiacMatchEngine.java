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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.MatchEngine;


import java.util.function.Function;

import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class FordiacMatchEngine extends DefaultMatchEngine {


	public FordiacMatchEngine() {
		super(createCustomMatcher(), new DefaultComparisonFactory(new DefaultEqualityHelperFactory()));
	}


	public static IEObjectMatcher createCustomMatcher() {
		final IEObjectMatcher fallBackMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
		return new FordiacIdentifierEObjectMatcher(fallBackMatcher, idFunction);

	}


	static Function<EObject, String> idFunction = input -> {
		if (input instanceof FBType || input instanceof AutomationSystem || input instanceof StructuredType) {
			return input.getClass().getName();
		}

		return null;
	};


}
