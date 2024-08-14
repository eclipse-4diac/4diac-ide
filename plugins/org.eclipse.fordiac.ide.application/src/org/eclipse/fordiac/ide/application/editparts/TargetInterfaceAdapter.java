/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.editparts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

public final class TargetInterfaceAdapter extends AdapterImpl {

	private final UntypedSubAppInterfaceElementEditPart refEditPart;
	private final Set<EObject> secondaryTargets = new HashSet<>();

	public TargetInterfaceAdapter(final UntypedSubAppInterfaceElementEditPart refEditPart) {
		this.refEditPart = refEditPart;
		refEditPart.getModel().eAdapters().add(this);
		refreshSecondaryTargets();
	}

	public void deactivate() {
		refEditPart.getModel().eAdapters().remove(this);
		secondaryTargets.forEach(target -> target.eAdapters().remove(TargetInterfaceAdapter.this));
		secondaryTargets.clear();
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (refEditPart.getParent() != null && notification.getEventType() != Notification.REMOVING_ADAPTER) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_Destination().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_Source().equals(feature)) {
				refEditPart.refresh();
			}
			refreshSecondaryTargets();
		}
		super.notifyChanged(notification);
	}

	private void refreshSecondaryTargets() {
		final Set<EObject> currentTargets = getCurrentTargets();

		// remove entries from our set if they are not in the list anymore
		secondaryTargets.removeIf(target -> {
			if (!currentTargets.contains(target)) {
				target.eAdapters().remove(TargetInterfaceAdapter.this);
				return true;
			}
			return false;
		});
		currentTargets.forEach(pin -> {
			if (secondaryTargets.add(pin)) {
				pin.eAdapters().add(TargetInterfaceAdapter.this);
			}
		});
	}

	private Set<EObject> getCurrentTargets() {
		final IInterfaceElement model = refEditPart.getModel();
		final Set<EObject> currTargets = new HashSet<>();

		if (refEditPart.isInput()) {
			model.getInputConnections().forEach(srcCon -> checkInputConns(currTargets, srcCon));
		} else {
			model.getOutputConnections().forEach(dstCon -> checkoutOutputConns(currTargets, dstCon));
		}
		return currTargets;
	}

	private static void checkInputConns(final Set<EObject> currTargets, final Connection con) {
		currTargets.add(con);
		final IInterfaceElement source = con.getSource();
		if (source != null) {
			currTargets.add(con.getSource());
			if (TargetPinManager.followConnections(source.getFBNetworkElement(), source.getInputConnections())) {
				source.getInputConnections().forEach(srcCon -> checkInputConns(currTargets, srcCon));
			}
		}
	}

	private static void checkoutOutputConns(final Set<EObject> currTargets, final Connection con) {
		currTargets.add(con);
		final IInterfaceElement dest = con.getDestination();
		if (dest != null) {
			currTargets.add(dest);
			if (TargetPinManager.followConnections(dest.getFBNetworkElement(), dest.getOutputConnections())) {
				dest.getOutputConnections().forEach(dstCon -> checkoutOutputConns(currTargets, dstCon));
			} else {
				currTargets.addAll(dest.getOutputConnections()); // needed for correctly handling undo
			}
		}
	}

}
