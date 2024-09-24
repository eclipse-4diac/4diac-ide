/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.st;

import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPageDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class AbstractSTEditorDescriptor implements ITypeEditorPageDescriptor {

	@Inject
	Provider<StructuredTextFBTypeEditor> provider;

	@Override
	public ITypeEditorPage createEditorPage() {
		return provider.get();
	}

}