/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SourceElement;

public interface STCoreReconciler {

	void reconcile(LibraryElement dest, Optional<? extends STCorePartition> source);

	static boolean hasDuplicates(final Stream<? extends SourceElement> sourceElements) {
		return !sourceElements.filter(INamedElement.class::isInstance).map(INamedElement.class::cast)
				.map(INamedElement::getName).allMatch(new HashSet<>()::add);
	}
}
