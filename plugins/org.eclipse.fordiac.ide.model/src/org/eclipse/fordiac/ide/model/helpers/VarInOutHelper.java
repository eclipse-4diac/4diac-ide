/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class VarInOutHelper {

	/**
	 * Get the defining InOut variable
	 * <p>
	 * The defining InOut variable in a network of connected InOut variables is the
	 * one that has no input connections.
	 * <p>
	 * A special case exists for untyped subapps where the search stops at the
	 * subapp boundary, indicated by a source endpoint that is an input. When passed
	 * an InOut variable on the interface of an untyped subapp, this method searches
	 * the outside for an input variable and the inside for an output variable.
	 *
	 * @param varDeclaration The variable to search from
	 * @return The defining variable
	 * @apiNote In order to get meaningful results, the variable to search from
	 *          should be an input variable, except for untyped subapps, where an
	 *          output variable may also have input connections.
	 */
	public static VarDeclaration getDefiningVarInOutDeclaration(VarDeclaration varDeclaration) {
		final Set<VarDeclaration> visited = new HashSet<>();
		while (!varDeclaration.getInputConnections().isEmpty()
				&& varDeclaration.getInputConnections().get(0).getSource() instanceof final VarDeclaration source) {
			if (source.isIsInput()) {
				return source; // subapp boundary
			}
			varDeclaration = source.getInOutVarOpposite();
			if (!visited.add(varDeclaration)) {
				return null; // loop
			}
		}
		return varDeclaration;
	}

	/**
	 * Get the path to the defining InOut variable
	 * <p>
	 * The defining InOut variable in a network of connected InOut variables is the
	 * one that has no input connections.
	 * <p>
	 * A special case exists for untyped subapps where the search stops at the
	 * subapp boundary, indicated by a source endpoint that is an input. When passed
	 * an InOut variable on the interface of an untyped subapp, this method searches
	 * the outside for an input variable and the inside for an output variable.
	 *
	 * @param varDeclaration The variable to search from
	 * @return The path to the defining variable, excluding the variable to search
	 *         from or an empty set if there is a loop
	 * @apiNote In order to get meaningful results, the variable to search from
	 *          should be an input variable, except for untyped subapps, where an
	 *          output variable may also have input connections.
	 */
	public static SequencedSet<VarDeclaration> getDefiningVarInOutDeclarationPath(VarDeclaration varDeclaration) {
		final SequencedSet<VarDeclaration> visited = new LinkedHashSet<>();
		while (!varDeclaration.getInputConnections().isEmpty()
				&& varDeclaration.getInputConnections().get(0).getSource() instanceof final VarDeclaration source) {
			if (source.isIsInput()) {
				return visited; // subapp boundary
			}
			varDeclaration = source.getInOutVarOpposite();
			if (!visited.add(varDeclaration)) {
				return Collections.emptyNavigableSet(); // loop
			}
		}
		return visited;
	}

	private VarInOutHelper() {
		throw new UnsupportedOperationException();
	}
}
