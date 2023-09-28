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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.document;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocumentUpdater.DefaultLibraryElementChangeAdapterFilter;

public class STFunctionDocumentUpdaterChangeAdapterFilter extends DefaultLibraryElementChangeAdapterFilter {

	@Override
	public boolean shouldAdapt(final Notifier notifier) {
		return super.shouldAdapt(notifier) && !(notifier instanceof FunctionBody || notifier instanceof InterfaceList
				|| notifier instanceof CompilerInfo);
	}

	@Override
	public boolean shouldNotify(final Notification notification) {
		return super.shouldNotify(notification) && switch (notification.getFeatureID(FunctionFBType.class)) {
		case LibraryElementPackage.FUNCTION_FB_TYPE__NAME, LibraryElementPackage.FUNCTION_FB_TYPE__COMMENT,
				LibraryElementPackage.FUNCTION_FB_TYPE__BODY ->
			false;
		default -> true;
		};
	}
}
