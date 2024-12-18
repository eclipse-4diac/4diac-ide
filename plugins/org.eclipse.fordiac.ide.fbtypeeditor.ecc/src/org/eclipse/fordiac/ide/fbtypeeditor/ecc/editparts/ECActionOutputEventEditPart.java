/*******************************************************************************
 * Copyright (c) 2011, 2023 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 								Johannes Kepler University Linz (JKU)
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_HORIZONTAL;
import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_VERTICAL;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public class ECActionOutputEventEditPart extends AbstractDirectEditableEditPart {
	private static final Insets OUTPUT_EVENT_INSETS = new Insets(MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_VERTICAL,
			MARGIN_HORIZONTAL);

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refreshEventLabel(getNameLabel());
		}
	};

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
				refreshEventLabel(getNameLabel());
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
			final FBNetworkElement fbNetworkElement = ev.getFBNetworkElement();
			return (fbNetworkElement instanceof AdapterFB)
					&& ((((AdapterFB) fbNetworkElement).getAdapterDecl() == change)
							|| (fbNetworkElement.getName().equals(change)));
		}
		return false;
	}

	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_EVENT_COLOR)) {
			getFigure().setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_EVENT_COLOR));
		}
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_EVENT_TEXT_COLOR)) {
			getFigure().setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_EVENT_TEXT_COLOR));
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getAction().eAdapters().add(adapter);
			// Adapt to the fbtype so that we get informed on interface changes
			ECCContentAndLabelProvider.getFBType(getAction()).getInterfaceList().eAdapters().add(interfaceAdapter);

			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getAction().eAdapters().remove(adapter);
			final FBType fbType = ECCContentAndLabelProvider.getFBType(getAction());
			if (fbType != null) {
				fbType.getInterfaceList().eAdapters().remove(interfaceAdapter);
			}
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteECActionCommand(getAction());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
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
				}
				return null;
			}

			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		});
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// transform doubleclick to direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
	}

	@Override
	protected ComboDirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(getNameLabel()),
				getNameLabel());
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

	public ECAction getAction() {
		return getCastedModel().getAction();
	}

	@Override
	public INamedElement getINamedElement() {
		return getAction().getOutput();
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected IFigure createFigure() {
		final Label eventLabel = new Label();
		eventLabel.setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_EVENT_COLOR));
		eventLabel.setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_EVENT_TEXT_COLOR));
		eventLabel.setOpaque(true);
		refreshEventLabel(eventLabel);
		eventLabel.setBorder(new MarginBorder(OUTPUT_EVENT_INSETS));
		eventLabel.setTextAlignment(PositionConstants.LEFT);
		eventLabel.setLabelAlignment(PositionConstants.LEFT);
		return eventLabel;
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

		if (event.getFBNetworkElement() instanceof AdapterFB) {
			return event.getFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}
}
