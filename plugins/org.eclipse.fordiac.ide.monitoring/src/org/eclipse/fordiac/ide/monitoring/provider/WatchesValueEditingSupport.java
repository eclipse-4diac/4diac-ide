/* Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.validation.ValueValidator;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.monitoring.views.StructParser;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNodeUtils;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Tree;

public class WatchesValueEditingSupport extends EditingSupport {
	private final TextCellEditor cellEditor;

	public WatchesValueEditingSupport(final ColumnViewer viewer, final Tree tree) {
		super(viewer);
		cellEditor = new TextCellEditor(tree);
	}

	@Override
	protected void setValue(final Object element, final Object value) {
		if (element instanceof WatchValueTreeNode) {
			final MonitoringBaseElement monitoringBaseElement = ((WatchValueTreeNode) element)
					.getMonitoringBaseElement();
			if (isValid((String) value, monitoringBaseElement)) {
				if (((WatchValueTreeNode) element).isStructNode()
						&& ((WatchValueTreeNode) element).getVariable() != null) {
					MonitoringManager.getInstance().writeValue((MonitoringElement) monitoringBaseElement,
							StructParser.changeStructNodeValue((MonitoringElement) monitoringBaseElement,
									((WatchValueTreeNode) element).getVariable(), (String) value));
					if (needsOfflineSave(monitoringBaseElement.getPort().getInterfaceElement())) {
						writeOnlineValueToOffline((MonitoringElement) monitoringBaseElement,
								StructParser.changeStructNodeValue((MonitoringElement) monitoringBaseElement,
										((WatchValueTreeNode) element).getVariable(), (String) value));
					}
				} else {
					MonitoringManager.getInstance().writeValue((MonitoringElement) monitoringBaseElement,
							(String) value);
					if (needsOfflineSave(monitoringBaseElement.getPort().getInterfaceElement())) {
						writeOnlineValueToOffline((MonitoringElement) monitoringBaseElement, (String) value);
					}
				}
			}
		}
	}

	@Override
	protected Object getValue(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			final String value = ((WatchValueTreeNode) element).getValue();
			final IInterfaceElement ie = ((WatchValueTreeNode) element).getMonitoringBaseElement().getPort()
					.getInterfaceElement();
			if (value != null && ie.getType() != null
					&& WatchValueTreeNodeUtils.isHexDecorationNecessary(value,
							ie.getType())) {
				return WatchValueTreeNodeUtils.decorateHexNumber(value);
			}
			return WatchValueTreeNodeUtils.decorateCellValue(ie.getType(), value);
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected TextCellEditor getCellEditor(final Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			final WatchValueTreeNode watchNode = (WatchValueTreeNode) element;
			return !(watchNode.getMonitoringBaseElement().getPort().getInterfaceElement() instanceof Event)
					&& !watchNode.isStructRootNode();
		}
		return false;
	}

	public static boolean isValid(final String newValue, final MonitoringBaseElement monElement) {
		if (!newValue.isBlank()) {
			final IInterfaceElement ie = monElement.getPort().getInterfaceElement();
			final String validationMsg = (ie instanceof VarDeclaration)
					? ValueValidator.validateValue((VarDeclaration) ie, newValue)
							: null;
			if ((validationMsg != null) && (!validationMsg.trim().isEmpty())) {
				ErrorMessenger.popUpErrorMessage(validationMsg);
				return false;
			}
		}
		return true;
	}

	public static boolean needsOfflineSave(final Object element) {
		// checks for preference, is input, has no connections, not in typedSubapp
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_MONITORING_WRITEBACKONLINEVALUES) && element instanceof VarDeclaration
				&& ((VarDeclaration) element).isIsInput() && ((VarDeclaration) element).getInputConnections().isEmpty()
				&& !FBNetworkElementHelper.isContainedInTypedInstance(((VarDeclaration) element).getFBNetworkElement());
	}

	public static void writeOnlineValueToOffline(final MonitoringElement element, final String value) {
		if (element.getPort() != null
				&& element.getPort().getSystem() != null
				&& element.getPort().getSystem().getCommandStack() != null) {
			final CommandStack cmdStack = element.getPort().getSystem().getCommandStack();
			cmdStack.execute(new ChangeValueCommand((VarDeclaration) element.getPort().getInterfaceElement(), value));
		}
	}
}