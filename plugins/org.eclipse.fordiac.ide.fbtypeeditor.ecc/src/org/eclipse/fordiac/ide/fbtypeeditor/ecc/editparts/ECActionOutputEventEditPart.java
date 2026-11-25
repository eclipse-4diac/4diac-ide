/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 *                    Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr -  consistent dropdown menu edit, redesign ECC
 *   Alois Zoitl     - updated for new adapter FB handling
 *   Alois Zoitl     - modernized and reworked ECC look
 *   Tisya Gupta     - extracted common EC action edit part base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.List;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class ECActionOutputEventEditPart extends AbstractECActionElementEditPart {

	private final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			switch (notification.getEventType()) {
			case Notification.REMOVE:
				handleRemove(notification);
				break;
			case Notification.SET:
				handleSet(notification);
				break;
			default:
				break;
			}
		}

		private void handleSet(final Notification notification) {
			if ((null != getAction().getOutput()) && (notification.getNewValue() instanceof String)
					&& (isOutputEvent(notification) || isAdapterOutputEvent(notification))) {
				refreshVisuals();
			}
		}

		private boolean isOutputEvent(final Notification notification) {
			return getAction().getOutput().getName().equals(notification.getNewValue());
		}

		private boolean isAdapterOutputEvent(final Notification notification) {
			return isAdapterNotification(notification.getNewValue(), getAction().getOutput());
		}

		private void handleRemove(final Notification notification) {
			if ((notification.getOldValue() == getAction().getOutput())
					|| isAdapterNotification(notification.getOldValue(), getAction().getOutput())) {
				executeCommand(new ChangeOutputCommand(getAction(), null));
			}
		}
	};

	public static boolean isAdapterNotification(final Object change, final Event ev) {
		if (ev != null) {
			final FBNetworkElement fbNetworkElement = ev.getBlockFBNetworkElement();
			return (fbNetworkElement instanceof final AdapterFB adapterFB)
					&& ((adapterFB.getAdapterDecl() == change) || (fbNetworkElement.getName().equals(change)));
		}
		return false;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			// Adapt to the fbtype so that we get informed on interface changes
			final FBType fbType = ECCContentAndLabelProvider.getFBType(getAction());
			if (fbType != null) {
				fbType.getInterfaceList().eAdapters().add(interfaceAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			final FBType fbType = ECCContentAndLabelProvider.getFBType(getAction());
			if (fbType != null) {
				fbType.getInterfaceList().eAdapters().remove(interfaceAdapter);
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}

	@Override
	protected DirectEditPolicy createDirectEditPolicy() {
		return new DirectEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				final Integer value = (Integer) request.getCellEditor().getValue();
				if (null != value) {
					final int selected = value.intValue();
					final List<Event> events = ECCContentAndLabelProvider
							.getOutputEvents(ECCContentAndLabelProvider.getFBType(getAction()));
					Event ev = null;
					if ((0 <= selected) && (selected < events.size())) {
						ev = events.get(selected);
					}
					return new ChangeOutputCommand(getAction(), ev);
				}
				return null;
			}

			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		};
	}

	@Override
	public void performDirectEdit() {
		final List<String> eventNames = ECCContentAndLabelProvider
				.getOutputEventNames(ECCContentAndLabelProvider.getFBType(getAction()));
		final int selected = (getAction().getOutput() != null) ? eventNames.indexOf(getAction().getOutput().getName())
				: eventNames.size() - 1;
		final ComboDirectEditManager editManager = createDirectEditManager();
		editManager.updateComboData(eventNames);
		editManager.setSelectedItem(selected);
		editManager.show();
	}

	public ECActionOutputEvent getCastedModel() {
		return (ECActionOutputEvent) getModel();
	}

	@Override
	public ECAction getAction() {
		return getCastedModel().getAction();
	}

	@Override
	protected void setupFigure(final Label label) {
		label.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccEventColor());
		label.setForegroundColor(FBTypeEditorPreferenceConstants.getEccEventTextColor());
		refreshEventLabel(label);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshEventLabel(getNameLabel());
	}

	private void refreshEventLabel(final Label eventLabel) {
		if (eventLabel != null) {
			eventLabel.setText(getActionOutputLabelText(getAction().getOutput()));
		}
	}

	private static String getActionOutputLabelText(final Event event) {
		if (event == null) {
			return ""; //$NON-NLS-1$
		}

		if (event.getBlockFBNetworkElement() instanceof AdapterFB) {
			return event.getBlockFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}
}
