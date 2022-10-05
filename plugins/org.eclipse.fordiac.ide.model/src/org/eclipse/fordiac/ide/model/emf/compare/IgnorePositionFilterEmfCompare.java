/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.emf.compare;


import org.eclipse.emf.compare.rcp.ui.structuremergeviewer.filters.AbstractDifferenceFilter;
import org.eclipse.emf.ecore.EObject;
import com.google.common.base.Predicate;

public class IgnorePositionFilterEmfCompare extends AbstractDifferenceFilter {

	@Override
	public Predicate<? super EObject> getPredicateWhenSelected() {
		// TODO implement filter that ignores if a function block changes its positions
		return input -> false;
	}
}
