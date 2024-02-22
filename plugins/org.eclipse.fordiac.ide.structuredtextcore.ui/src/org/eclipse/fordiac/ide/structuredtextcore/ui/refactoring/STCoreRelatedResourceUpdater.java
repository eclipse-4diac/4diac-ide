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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.xtext.ide.serializer.IEmfResourceChange;
import org.eclipse.xtext.ide.serializer.impl.EObjectDescriptionDeltaProvider.Deltas;
import org.eclipse.xtext.ide.serializer.impl.RelatedXtextResourceUpdater;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.IAcceptor;

@SuppressWarnings("restriction")
public class STCoreRelatedResourceUpdater extends RelatedXtextResourceUpdater {

	@Override
	public void applyChange(final Deltas deltas, final IAcceptor<IEmfResourceChange> changeAcceptor) {
		if (getResourceSet().getResource(getResource().getUri(), true) instanceof XtextResource) {
			super.applyChange(deltas, changeAcceptor);
		}
	}
}
