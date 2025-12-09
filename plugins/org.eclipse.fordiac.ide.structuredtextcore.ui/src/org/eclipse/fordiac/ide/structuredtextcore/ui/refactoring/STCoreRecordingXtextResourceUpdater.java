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
import org.eclipse.xtext.ide.serializer.impl.RecordingXtextResourceUpdater;
import org.eclipse.xtext.util.IAcceptor;

@SuppressWarnings("restriction")
public class STCoreRecordingXtextResourceUpdater extends RecordingXtextResourceUpdater {

	@Override
	public void applyChange(final Deltas deltas, final IAcceptor<IEmfResourceChange> changeAcceptor) {
		// make sure there is something to refactor
		if (getSnapshot().getRegions().regionForRootEObject().getAllSemanticRegions().iterator().hasNext()) {
			super.applyChange(deltas, changeAcceptor);
		}
	}
}
