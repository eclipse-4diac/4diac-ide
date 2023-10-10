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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocumentUpdater.DefaultLibraryElementChangeAdapterFilter;

public class STAlgorithmDocumentUpdaterChangeAdapterFilter extends DefaultLibraryElementChangeAdapterFilter {

	@Override
	public boolean shouldAdapt(final Notifier notifier) {
		return super.shouldAdapt(notifier) && !(notifier instanceof Algorithm || notifier instanceof Method);
	}

	@Override
	public boolean shouldNotify(final Notification notification) {
		return super.shouldNotify(notification)
				&& notification.getFeatureID(BaseFBType.class) != LibraryElementPackage.BASE_FB_TYPE__CALLABLES;
	}
}
