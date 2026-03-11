/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;
import org.eclipse.jface.preference.IPreferenceStore;

public class MarkPredecessorHandler extends AbstractMarkerHandler {

	@Override
	protected String getMarkerId() {
		return UtilityMarkerHelper.PREDECESSOR_MARKER_ID;
	}

	@Override
	protected String getMarkerName() {
		// explicitly use the full class name for this Message as it is pulled in from
		// another plugin
		return org.eclipse.fordiac.ide.gef.Messages.UtilityMarker_Predecessor;
	}

	@Override
	protected EObject getValidSelectedElement(final Object selectedObject) {
		if (selectedObject instanceof final AbstractBlockFBNElementEditPart ep
				&& ep.getModel().getOuterFBNetworkElement() instanceof final UntypedSubApp subApp
				&& subApp.isUnfolded()) {
			if (!manageEventConnectionsAutomatically(subApp)) {
				return null;
			}
			return ep.getModel();
		}
		return null;
	}

	private static boolean manageEventConnectionsAutomatically(final EObject refElement) {
		final IPreferenceStore preferenceStore = getPreferenceStore(refElement);
		if (preferenceStore == null) {
			return false;
		}
		return preferenceStore.getBoolean(GefPreferenceConstants.MANAGE_EVENT_CONNECTIONS_AUTOMATICALLY);
	}

	private static IPreferenceStore getPreferenceStore(final EObject refElement) {
		final EObject root = EcoreUtil.getRootContainer(refElement);
		if (!(root instanceof final LibraryElement libEl) || libEl.getTypeEntry() == null) {
			return null;
		}
		return PreferenceStoreProvider.getStore(GefPreferenceConstants.GEF_PREFERENCES_ID,
				libEl.getTypeEntry().getFile().getProject());
	}

}
