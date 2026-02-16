/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.handles.TargetLabelConnectionHandle;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.jface.viewers.StructuredSelection;

public class TargetInterfaceSelectionPolicy extends ModifiedNonResizeableEditPolicy {
	private final List<TargetLabelConnectionHandle> connectionHandles = new ArrayList<>();

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> handles = new ArrayList<>(super.createSelectionHandles());
		handles.addAll(this.connectionHandles);
		return handles;
	}

	@Override
	public void showSourceFeedback(final Request request) {
		super.showSourceFeedback(request);
		if (request instanceof final CreateConnectionRequest req) {
			// TODO: only update the selected handle
			connectionHandles.forEach(handle -> handle.update(req));
		}
	}

	@Override
	public void eraseSourceFeedback(final Request request) {
		super.eraseSourceFeedback(request);
		connectionHandles.forEach(TargetLabelConnectionHandle::reset);
	}

	@Override
	protected void showSelection() {
		createHandles();
		super.showSelection();
		connectionHandles.forEach(handle -> {
			final var conn = handle.getConnection();
			addFeedback(conn);
		});
	}

	@Override
	protected void hideSelection() {
		connectionHandles.forEach(handle -> {
			final var conn = handle.getConnection();
			removeFeedback(conn);
		});
		super.hideSelection();
		connectionHandles.clear();
	}

	@Override
	public TargetInterfaceElementEditPart getHost() {
		return (TargetInterfaceElementEditPart) super.getHost();
	}

	private void createHandles() {
		if (getHost().getParent() instanceof final InterfaceEditPart iep && !iep.isInput()) {
			if (iep.getChildren().stream().filter(TargetInterfaceElementEditPart.class::isInstance).limit(2)
					.count() == 1) {
				final var viewer = getHost().getViewer();
				final List<EditPart> selectedEditParts = new ArrayList<>(
						viewer.getSelectedEditParts().stream().filter(ep -> ep != getHost()).toList());
				selectedEditParts.addAll(iep.getTargetConnections());
				selectedEditParts.add(getHost()); // add Host last for Primary Selection
				viewer.setSelection(new StructuredSelection(selectedEditParts));
				return;
			}

			final ConnectionRouter connectionRouter = ((ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER))
					.getConnectionRouter();
			iep.getTargetConnections().forEach(connectionEP -> {
				if (connectionEP.getSource() instanceof final InterfaceEditPart connectionSourceIEP) {
					final var newHandle = new TargetLabelConnectionHandle(getHost(), connectionSourceIEP);
					newHandle.getConnection().setConnectionRouter(connectionRouter);
					connectionHandles.add(newHandle);
				}
			});
		}
	}
}
