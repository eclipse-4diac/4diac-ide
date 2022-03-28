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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Lukas Wais - implemented parsing for structs
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public final class StructParser {

	public static WatchValueTreeNode createStructFromString(String struct, final StructuredType structType,
			final MonitoringElement monitoringElement, final WatchValueTreeNode parent) {
		// remove first () for easier parsing
		if (!struct.isEmpty()) {
			struct = struct.substring(1, struct.length() - 1);
		}

		final StringTokenizer tokenizer = new StringTokenizer(struct, ","); //$NON-NLS-1$
		final IInterfaceElement interfaceElement = monitoringElement.getPort().getInterfaceElement();
		interfaceElement.getName();
		final WatchValueTreeNode structRoot = new WatchValueTreeNode(monitoringElement, structType,
				interfaceElement.getName(), "root", null, parent); //$NON-NLS-1$

		WatchValueTreeNode previous = structRoot;

		final Deque<WatchValueTreeNode> structParentLevelStack = new ArrayDeque<>();
		structParentLevelStack.push(structRoot);

		while (tokenizer.hasMoreTokens()) {
			final String nextToken = tokenizer.nextToken();
			final String[] parsedAssignment = parseAssignment(nextToken, previous);

			// check if we have split a string
			if (!"".equals(parsedAssignment[0])) { //$NON-NLS-1$

				final String varName = parsedAssignment[0];
				final String value = parsedAssignment[1];


				final VarDeclaration varDeclaration = findVarDeclaration(structType, varName);
				final WatchValueTreeNode newNode = new WatchValueTreeNode(monitoringElement, structType, varName, value,
						varDeclaration, structParentLevelStack.peek());

				previous = newNode;
				structParentLevelStack.peek().addChild(newNode);

				if (value.charAt(0) == '(') {
					structParentLevelStack.push(newNode);
				}

				if (value.charAt(value.length() - 1) == ')') {
					newNode.setValue(value.substring(0, value.length() - 1)); // remove )
					structParentLevelStack.pop();
				}
			}
		}

		return structRoot;
	}

	private static VarDeclaration findVarDeclaration(final StructuredType structType, final String varName) {
		return structType.getMemberVariables().stream().filter(variable -> variable.getName().equals(varName)).findAny()
				.orElse(null);
	}

	private static String[] parseAssignment(final String assignment, final WatchValueTreeNode previous) {
		// split by first :=
		if (assignment.contains(":=")) { //$NON-NLS-1$
			final String[] splittedAssignment = assignment.split(":=", 2); //$NON-NLS-1$

			final String variableName = splittedAssignment[0];
			final String value = splittedAssignment[1];
			return new String[] { variableName, value };
		}

		// otherwise we have a split a string, we attach it to the value of the previous node
		final StringBuilder newValueBuilder = new StringBuilder();

		String value = "null"; //$NON-NLS-1$
		if (previous != null) {
			value = previous.getValue();
		}

		newValueBuilder.append(value);
		newValueBuilder.append(assignment);

		if (previous != null) {
			previous.setValue(newValueBuilder.toString());
		}

		return new String[] { "", "" }; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static void buildTree(final StructuredType structType, final WatchValueTreeNode parent,
			final MonitoringBaseElement element) {

		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final WatchValueTreeNode treeNode = (WatchValueTreeNode) parent.addChild(memberVariable, element,
					structType);

			if ((memberVariable.getType() instanceof StructuredType)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree((StructuredType) memberVariable.getType(), treeNode, element);
			}
		}
	}

	public static String changeStructNodeValue(final MonitoringElement monitoringElement,
			final IInterfaceElement interfaceElement, final String newNodeValue) {
		if(monitoringElement.getCurrentValue() != null && !monitoringElement.getCurrentValue().equals("N/A"))//$NON-NLS-1$
		{
			final int startIndex = monitoringElement.getCurrentValue().toLowerCase()
					.indexOf(interfaceElement.getName().toLowerCase());
			int endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(',', startIndex);
			if(endIndex == -1 ) {
				endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(')', startIndex);
			}
			return monitoringElement.getCurrentValue().substring(0, startIndex)
					+ interfaceElement.getName() + ":=" + newNodeValue //$NON-NLS-1$
					+ monitoringElement.getCurrentValue().substring(endIndex);
		}
		return "";

	}

	private StructParser() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}

}
