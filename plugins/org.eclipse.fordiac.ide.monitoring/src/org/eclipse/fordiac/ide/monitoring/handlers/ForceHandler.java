/*******************************************************************************
 * Copyright (c) 2015, 2021 fortiss GmbH, Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   			 - Added value validation for the force input dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.validation.ValueValidator;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ForceHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final VarDeclaration variable = getVariable(selection.getFirstElement());
		showDialogAndProcess(variable);
		return null;
	}

	public static void showDialogAndProcess(final VarDeclaration variable, final IInterfaceElement interfaceElement) {
		if (interfaceElement == null) {
			showDialogAndProcess(variable);
		}
		final MonitoringManager manager = MonitoringManager.getInstance();
		final MonitoringBaseElement element = manager.getMonitoringElement(variable);
		if (element instanceof MonitoringElement) {
			final MonitoringElement monitoringElement = (MonitoringElement) element;
			String oldValue = "";
			if (monitoringElement.isForce() && monitoringElement.getForceValue() != null) {
				oldValue = monitoringElement.getForceValue().substring(
						monitoringElement.getForceValue().toLowerCase().indexOf(
								interfaceElement.getName().toLowerCase()) + interfaceElement.getName().length() + 2,
						monitoringElement.getForceValue().toLowerCase().indexOf(',',
								monitoringElement.getForceValue().toLowerCase()
								.indexOf(interfaceElement.getName().toLowerCase())) == -1
								? monitoringElement.getForceValue().toLowerCase().indexOf(')',
										monitoringElement.getForceValue().toLowerCase()
										.indexOf(interfaceElement.getName().toLowerCase()))
										: monitoringElement.getForceValue().toLowerCase().indexOf(',',
												monitoringElement.getForceValue().toLowerCase()
												.indexOf(interfaceElement.getName().toLowerCase())));

			}
			final InputDialog input = new InputDialog(Display.getDefault().getActiveShell(),
					Messages.MonitoringWatchesView_ForceValue, Messages.MonitoringWatchesView_Value,
					oldValue,
					(newValue -> {
						if (interfaceElement instanceof VarDeclaration) {
							return ForceHandler.validateForceInput((VarDeclaration) interfaceElement, newValue);
						}
						return null;
					}));
			final int ret = input.open();
			if (ret == org.eclipse.jface.window.Window.OK) {
				final int startIndex = monitoringElement.getCurrentValue().toLowerCase()
						.indexOf(interfaceElement.getName().toLowerCase());
				int endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(',', startIndex);
				if(endIndex == -1 ) {
					endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(')', startIndex);
				}
				final String newValue = monitoringElement.getCurrentValue().substring(0, startIndex)
						+ interfaceElement.getName() + ":=" + input.getValue() //$NON-NLS-1$
						+ monitoringElement.getCurrentValue().substring(endIndex);
				manager.forceValue(monitoringElement, interfaceElement, newValue);

			}

		}

	}

	public static void showDialogAndProcess(final VarDeclaration variable) {
		if (null != variable) {
			final MonitoringManager manager = MonitoringManager.getInstance();
			final MonitoringBaseElement element = manager.getMonitoringElement(variable);
			if (element instanceof MonitoringElement) {
				final MonitoringElement monitoringElement = (MonitoringElement) element;
				final IInterfaceElement interfaceElement = monitoringElement.getPort().getInterfaceElement();

				final InputDialog input = new InputDialog(Display.getDefault().getActiveShell(),
						Messages.MonitoringWatchesView_ForceValue,
						Messages.MonitoringWatchesView_Value,
						monitoringElement.isForce() ? monitoringElement.getForceValue() : "", //$NON-NLS-1$
								(newValue -> {
									if (interfaceElement instanceof VarDeclaration) {
										return ForceHandler.validateForceInput((VarDeclaration) interfaceElement, newValue);
									}
									return null;
								}));
				final int ret = input.open();
				if (ret == org.eclipse.jface.window.Window.OK) {
					manager.forceValue(monitoringElement, null, input.getValue());
				}
			}
		}
	}

	private static String validateForceInput(final VarDeclaration varDeclaration, final String newValue) {
		if (!newValue.isBlank()) {
			final String validationMsg = ValueValidator.validateValue(varDeclaration, newValue);
			if ((validationMsg != null) && (!validationMsg.trim().isEmpty())) {
				return validationMsg;
			}
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		boolean needToAdd = false;
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);

		if (selection instanceof StructuredSelection) {
			final StructuredSelection sel = (StructuredSelection) selection;
			final MonitoringManager manager = MonitoringManager.getInstance();

			if (1 == sel.size()) {
				// only allow to force a value if only one element is selected
				final VarDeclaration variable = getVariable(sel.getFirstElement());
				if ((null != variable) && manager.containsPort(variable)) {
					needToAdd = true;
				}
			}
		}
		setBaseEnabled(needToAdd);
	}

	static VarDeclaration getVariable(final Object object) {
		IInterfaceElement ie = null;
		if (object instanceof InterfaceEditPart) {
			ie = ((InterfaceEditPart) object).getModel();
		} else if (object instanceof MonitoringEditPart) {
			ie = ((MonitoringEditPart) object).getModel().getPort().getInterfaceElement();
		}
		if (ie instanceof VarDeclaration) {
			return (VarDeclaration) ie;
		}
		return null;
	}

}
