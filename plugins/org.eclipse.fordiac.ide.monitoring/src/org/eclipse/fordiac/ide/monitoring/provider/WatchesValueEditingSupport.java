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
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
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
		if (element instanceof final WatchValueTreeNode node) {
			final MonitoringElement monitoringElement = (MonitoringElement) node.getMonitoringBaseElement();

			if (isValid((String) value, monitoringElement)) {

				if (node.isStructNode() && node.getVariable() != null) {
					node.setValue((String) value);
					final String newStructLiteral = StructParser.toString(node);
					MonitoringManager.getInstance().writeValue(monitoringElement, newStructLiteral);

					if (needsOfflineSave(monitoringElement.getPort().getInterfaceElement())) {
						writeOnlineValueToOffline(monitoringElement, newStructLiteral);
					}
				} else {
					MonitoringManager.getInstance().writeValue(monitoringElement, (String) value);
					if (needsOfflineSave(monitoringElement.getPort().getInterfaceElement())) {
						writeOnlineValueToOffline(monitoringElement, (String) value);
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
					&& WatchValueTreeNodeUtils.isHexDecorationNecessary(value, ie.getType())) {
				return WatchValueTreeNodeUtils.decorateHexNumber(value);
			}
			return value;
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected TextCellEditor getCellEditor(final Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(final Object element) {
		if (element instanceof final WatchValueTreeNode watchNode) {
			return !(watchNode.getMonitoringBaseElement().getPort().getInterfaceElement() instanceof Event)
					&& !watchNode.isStructRootNode();
		}
		return false;
	}

	public static boolean isValid(final String newValue, final MonitoringBaseElement monElement) {
		if (!newValue.isBlank()) {
			final IInterfaceElement ie = monElement.getPort().getInterfaceElement();
			final String validationMsg = (ie instanceof VarDeclaration)
					? VariableOperations.validateValue((VarDeclaration) ie, newValue)
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
		if (element.getPort() != null && element.getPort().getSystem() != null
				&& element.getPort().getSystem().getCommandStack() != null
				&& element.getPort().getInterfaceElement() instanceof final VarDeclaration varDeclaration) {
			final CommandStack cmdStack = element.getPort().getSystem().getCommandStack();
			cmdStack.execute(new ChangeValueCommand(varDeclaration, value));
		}
	}
}