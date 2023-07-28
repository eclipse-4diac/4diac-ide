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
package org.eclipse.fordiac.ide.globalconstantseditor.util;

import java.util.Optional;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class GlobalConstantsReconciler {

	public static void reconcile(final GlobalConstants dest, final Optional<EList<VarDeclaration>> source,
			final String originalSource) {
		// check duplicates in source (very bad)
		if (source.filter(GlobalConstantsReconciler::checkDuplicates).isPresent()) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		// update constants
		source.ifPresent(constants -> ECollections.setEList(dest.getConstants(), constants));
		// update original source
		if (originalSource != null) {
			OriginalSource destOriginalSource = dest.getSource();
			if (destOriginalSource == null) {
				destOriginalSource = LibraryElementFactory.eINSTANCE.createOriginalSource();
				dest.setSource(destOriginalSource);
			}
			destOriginalSource.setText(originalSource);
		}
	}

	protected static boolean checkDuplicates(final EList<? extends VarDeclaration> list) {
		return list.stream().map(VarDeclaration::getName).distinct().count() != list.size();
	}

	private GlobalConstantsReconciler() {
		throw new UnsupportedOperationException();
	}
}
