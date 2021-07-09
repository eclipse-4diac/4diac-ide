/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.ui.editors.EditorCloserAdapter;
import org.eclipse.ui.IEditorPart;

public class FBNElemEditorCloser extends EditorCloserAdapter {

	private final FBNetworkElement fbNetworkElement;

	public FBNElemEditorCloser(final IEditorPart part, final FBNetworkElement fbNetworkElement) {
		super(part);
		this.fbNetworkElement = fbNetworkElement;
	}

	@Override
	public void notifyChanged(final Notification msg) {
		super.notifyChanged(msg);
		final Object feature = msg.getFeature();
		if ((LibraryElementPackage.eINSTANCE.getFBNetwork_NetworkElements().equals(feature))
				&& ((msg.getEventType() == Notification.REMOVE) || (msg.getEventType() == Notification.REMOVE_MANY))
				&& (msg.getOldValue() == fbNetworkElement)) {
			((EObject) msg.getNotifier()).eAdapters().remove(this);
			// the subapp/cfb was removed from the network
			closeEditor();
		}
	}
}