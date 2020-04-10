/*******************************************************************************
 * Copyright (c) 2011 - 2019 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
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
 *   Bianca Wiesmayr
 *     -  consistent dropdown menu edit
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
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
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.GradientLabel;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public class ECActionOutputEventEditPart extends AbstractDirectEditableEditPart {
	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			refreshEventLabel();
		}
	};

	private final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (notification.getEventType() == Notification.REMOVE) {
				if ((notification.getOldValue() == getAction().getOutput())
						|| ((getAction().getOutput() instanceof AdapterEvent)
								&& (notification.getOldValue() instanceof AdapterDeclaration)
								&& (((AdapterEvent) getAction().getOutput()).getAdapterDeclaration() == notification
										.getOldValue()))) {
					executeCommand(new ChangeOutputCommand(getAction(), null));
				}
			} else if (notification.getEventType() == Notification.SET) {
				if (null != getAction().getOutput() && notification.getNewValue() instanceof String) {
					if (getAction().getOutput().getName().equals(notification.getNewValue())) {
						refreshEventLabel();
					} else if ((getAction().getOutput() instanceof AdapterEvent)
							&& (((AdapterEvent) getAction().getOutput()).getAdapterDeclaration().getName()
									.equals(notification.getNewValue()))) {
						refreshEventLabel();
					}
				}
			}
		}
	};

	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(PreferenceConstants.P_ECC_EVENT_COLOR)) {
				getFigure().setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_EVENT_COLOR));
			}
			if (event.getProperty().equals(PreferenceConstants.P_ECC_EVENT_BORDER_COLOR)) {
				getFigure().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_EVENT_BORDER_COLOR));
			}
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
			FBType fbType = ECCContentAndLabelProvider.getFBType(getAction());
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
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					Integer value = (Integer) request.getCellEditor().getValue();
					if (null != value) {
						int selected = value.intValue();
						List<Event> events = ECCContentAndLabelProvider
								.getOutputEvents(ECCContentAndLabelProvider.getFBType(getAction()));
						Event ev = null;
						if (0 <= selected && selected < events.size()) {
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
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy() {
		});
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
	protected DirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(getNameLabel()),
				getNameLabel());
	}

	@Override
	public void performDirectEdit() {
		List<String> eventNames = ECCContentAndLabelProvider
				.getOutputEventNames(ECCContentAndLabelProvider.getFBType(getAction()));
		int selected = (getAction().getOutput() != null) ? eventNames.indexOf(getAction().getOutput().getName())
				: eventNames.size() - 1;
		((ComboDirectEditManager) getManager()).updateComboData(eventNames);
		((ComboDirectEditManager) getManager()).setSelectedItem(selected);
		getManager().show();
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
		Label eventLabel = new GradientLabel(((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager());
		eventLabel.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_EVENT_COLOR));
		eventLabel.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_EVENT_BORDER_COLOR));
		eventLabel.setOpaque(true);
		eventLabel.setText(getAction().getOutput() != null ? getAction().getOutput().getName() : ""); //$NON-NLS-1$
		eventLabel.setBorder(new LineBorder() {
			@Override
			public Insets getInsets(final IFigure figure) {
				return new Insets(3, 6, 3, 6);
			}
		});
		eventLabel.setTextAlignment(PositionConstants.LEFT);
		eventLabel.setLabelAlignment(PositionConstants.LEFT);
		return eventLabel;
	}

	private void refreshEventLabel() {
		getNameLabel().setText(getAction().getOutput() != null ? getAction().getOutput().getName() : ""); //$NON-NLS-1$
	}
}
