/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz,
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
 *   Daniel Lindhuber - struct force dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.dialogs.StructForceDialog;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.fordiac.ide.monitoring.views.StructParser;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ForceHandler extends AbstractMonitoringHandler {

	@Override
	protected void doExecute(final ExecutionEvent event, final StructuredSelection structSel) {
		showDialogAndProcess(getVariable(structSel.getFirstElement()));
	}

	public static void showDialogAndProcess(final VarDeclaration variable, final IInterfaceElement interfaceElement) {
		if (interfaceElement == null) {
			showDialogAndProcess(variable);
			return;
		}
		final MonitoringManager manager = MonitoringManager.getInstance();
		final MonitoringBaseElement element = manager.getMonitoringElement(variable);
		if (element instanceof final MonitoringElement monitoringElement) {
			String oldValue = ""; //$NON-NLS-1$
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
					Messages.MonitoringWatchesView_ForceValue, Messages.MonitoringWatchesView_Value, oldValue,
					(newValue -> {
						if (interfaceElement instanceof final VarDeclaration varDecl) {
							return ForceHandler.validateForceInput(varDecl, newValue);
						}
						return null;
					}));
			final int ret = input.open();
			if (ret == org.eclipse.jface.window.Window.OK) {
				manager.forceValue(monitoringElement,
						StructParser.changeStructNodeValue(monitoringElement, interfaceElement, input.getValue()));
			}

		}

	}

	public static void showDialogAndProcess(final VarDeclaration variable) {
		if (null != variable) {
			final MonitoringManager manager = MonitoringManager.getInstance();
			final MonitoringBaseElement element = manager.getMonitoringElement(variable);
			if (element instanceof final MonitoringElement monitoringElement) {
				final IInterfaceElement interfaceElement = monitoringElement.getPort().getInterfaceElement();

				String input;
				if (variable.getType() instanceof final StructuredType structType) {
					input = showStructForceDialog(structType, monitoringElement);

					if (variable.isArray() && input != null) {
						input = StructParser.removeArrayIndexes(input);
					}
				} else {
					input = showForceDialog(monitoringElement, interfaceElement);
				}

				if (input != null) {
					manager.forceValue(monitoringElement, input);
				}
			}
		}
	}

	private static String showStructForceDialog(final StructuredType type, final MonitoringElement monitoringElement) {
		final StructForceDialog dialog = new StructForceDialog(Display.getDefault().getActiveShell(), type,
				monitoringElement);
		final int ret = dialog.open();
		return ret == org.eclipse.jface.window.Window.OK ? dialog.getValue() : null;
	}

	private static String showForceDialog(final MonitoringElement monitoringElement,
			final IInterfaceElement interfaceElement) {
		final InputDialog input = new InputDialog(Display.getDefault().getActiveShell(),
				Messages.MonitoringWatchesView_ForceValue, Messages.MonitoringWatchesView_Value,
				monitoringElement.isForce() ? monitoringElement.getForceValue() : "", //$NON-NLS-1$
				(newValue -> {
					if (interfaceElement instanceof final VarDeclaration varDecl) {
						return ForceHandler.validateForceInput(varDecl, newValue);
					}
					return null;
				}));
		final int ret = input.open();
		return ret == org.eclipse.jface.window.Window.OK ? input.getValue() : null;
	}

	private static String validateForceInput(final VarDeclaration varDeclaration, final String newValue) {
		if (!newValue.isBlank()) {
			final String validationMsg = VariableOperations.validateValue(varDeclaration, newValue);
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

		if (selection instanceof final StructuredSelection sel) {
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
		if (object instanceof final InterfaceEditPart iep) {
			ie = iep.getModel();
		} else if (object instanceof final MonitoringEditPart me) {
			ie = me.getModel().getPort().getInterfaceElement();
		}
		if (ie instanceof final VarDeclaration varDecl) {
			return varDecl;
		}
		return null;
	}

}
