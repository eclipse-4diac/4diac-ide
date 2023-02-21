/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.TsnParameters;

public class Annotations {
	// TsnWindow
	public static String getTsnWindowName(final TsnWindow w) {
		return TsnParameters.TSN_WINDOW_NAME + ((TsnConfiguration) w.eContainer()).getWindows().indexOf(w);
	}

	public static EList<VarDeclaration> getParameters(final EList<TsnWindow> windows) {
		final VarDeclaration vardec = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		vardec.setName("NoChannel");
		vardec.setValue(LibraryElementFactory.eINSTANCE.createValue());
		vardec.getValue().setValue(String.valueOf(windows.size()));
		final BasicEList<VarDeclaration> list = new BasicEList<>();
		list.add(vardec);
		for (final TsnWindow window : windows) {
			final VarDeclaration win = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			win.setName(window.getName());
			win.setValue(LibraryElementFactory.eINSTANCE.createValue());
			win.getValue().setValue(String.valueOf(window.getDuration()));
			list.add(win);
		}
		return list;
	}

	private Annotations() {
		throw new UnsupportedOperationException("do not instantiate this class"); ////$NON-NLS-1$
	}
}
