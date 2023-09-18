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
package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;

/** @deprecated Use {@link LibraryElementXtextResource} directly instead */
@Deprecated(forRemoval = true)
public class FBTypeXtextResource extends LibraryElementXtextResource {

	/** @deprecated Use {@link LibraryElementXtextResource#updateInternalLibraryElement()} instead */
	@Deprecated(forRemoval = true)
	protected void updateInternalFBType() {
		updateInternalLibraryElement();
	}

	/** @deprecated Use {@link LibraryElementXtextResource#clearInternalLibraryElement()} instead */
	@Deprecated(forRemoval = true)
	protected void clearInternalFBType() {
		clearInternalLibraryElement();
	}

	/** @deprecated Use {@link LibraryElementXtextResource#getLibraryElement()} instead */
	@Deprecated(forRemoval = true)
	public FBType getFbType() {
		if (getLibraryElement() instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}

	/** @deprecated Use {@link LibraryElementXtextResource#setLibraryElement()} instead */
	@Deprecated(forRemoval = true)
	public void setFbType(final FBType fbType) {
		setLibraryElement(fbType);
	}
}
