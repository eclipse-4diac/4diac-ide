/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;

public class GlobalConstantsReconciler implements STCoreReconciler {

	@Override
	public void reconcile(final LibraryElement dest, final Optional<? extends STCorePartition> source) {
		if (dest instanceof final GlobalConstants globalConstants && source.isPresent()
				&& source.get() instanceof final GlobalConstantsPartition globalConstantsPartition) {
			reconcile(globalConstants, globalConstantsPartition);
		}
	}

	public static void reconcile(final GlobalConstants dest, final GlobalConstantsPartition source) {
		// check duplicates in source (very bad)
		if (checkDuplicates(source.getConstants())) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		// update package & imports
		CompilerInfo compilerInfo = dest.getCompilerInfo();
		if (compilerInfo == null) {
			compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();
			dest.setCompilerInfo(compilerInfo);
		}
		compilerInfo.setPackageName(source.getPackageName());
		ECollections.setEList(compilerInfo.getImports(), source.getImports());
		// update constants
		ECollections.setEList(dest.getConstants(), source.getConstants());
		// update original source
		if (source.getOriginalSource() != null) {
			OriginalSource destOriginalSource = dest.getSource();
			if (destOriginalSource == null) {
				destOriginalSource = LibraryElementFactory.eINSTANCE.createOriginalSource();
				dest.setSource(destOriginalSource);
			}
			destOriginalSource.setText(source.getOriginalSource());
		}
	}

	protected static boolean checkDuplicates(final List<? extends VarDeclaration> list) {
		return list.stream().map(VarDeclaration::getName).distinct().count() != list.size();
	}
}
