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
 *   Lukas Wais, Daniel Lindhuber - implemented parsing for structs
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public final class StructParser {

	public static WatchValueTreeNode createStructFromString(final String struct, final StructuredType structType,
			final MonitoringElement monitoringElement, final WatchValueTreeNode parent) {

		final WatchValueTreeNode root = new WatchValueTreeNode(
				monitoringElement,
				structType,
				monitoringElement.getPort().getInterfaceElement().getName(),
				null,
				null,
				parent
				);

		if (!struct.isBlank() && !struct.equalsIgnoreCase("N/A")) {//$NON-NLS-1$
			buildTree(root, structType, struct);
		}

		return root;
	}

	private static void buildTree(final WatchValueTreeNode parent, final StructuredType type, final String struct) {

		// do not consider outer parentheses
		final int START = 1;
		final int END = struct.length() - 1;

		boolean process = true;
		int innerStructCnt = 0;
		int frameStart = START;

		for (int i = START; i < END; i++) {
			final char c = struct.charAt(i);
			switch (c) {
			case ',':
				if (process) {
					final String frame = struct.substring(frameStart, i);
					addNode(frame, parent, type);
					frameStart = i + 1; // switch to next frame
				}
				break;
			case '(': // start of inner struct
				// skip the whole inner struct since this will be handled recursively
				process = false;
				innerStructCnt++;
				break;
			case ')': // end of inner struct
				innerStructCnt--;
				if (innerStructCnt == 0) {
					process = true;
				}
				break;
			default:
				break;
			}
		}

		// handle last variable (no ',' triggers the node creation)
		if (frameStart != END) {
			final String frame = struct.substring(frameStart, END);
			addNode(frame, parent, type);
		}

	}

	private static void addNode(final String frame, final WatchValueTreeNode parent, final StructuredType type) {
		final MonitoringBaseElement monitoringElement = parent.getMonitoringBaseElement();

		// split on first encounter
		final String[] split = frame.split(":=", 2); //$NON-NLS-1$
		final String name = split[0];
		final String value = split[1];
		final VarDeclaration variable = findVarDeclaration(type, name);

		if (variable == null || variable.getType() == null) {
			throw new IllegalArgumentException("Error during struct parsing."); //$NON-NLS-1$
		}

		final WatchValueTreeNode node = new WatchValueTreeNode(
				monitoringElement,
				type,
				name,
				value,
				variable,
				parent
				);

		if (isInnerStruct(value)) {
			// recursive call
			buildTree(node, (StructuredType) variable.getType(), value);
		}

		parent.addChild(node);
	}

	private static boolean isInnerStruct(final String value) {
		return !value.isBlank() &&
				value.length() >= 2 &&
				value.charAt(0) == '(' &&
				value.charAt(value.length() - 1) == ')';
	}

	private static VarDeclaration findVarDeclaration(final StructuredType structType, final String varName) {
		return structType.getMemberVariables().stream().filter(variable -> variable.getName().equals(varName)).findAny()
				.orElse(null);
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
		if (monitoringElement.getCurrentValue() != null && !monitoringElement.getCurrentValue().equals("N/A"))//$NON-NLS-1$
		{
			final int startIndex = monitoringElement.getCurrentValue().toLowerCase()
					.indexOf(interfaceElement.getName().toLowerCase());
			int endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(',', startIndex);
			if (endIndex == -1) {
				endIndex = monitoringElement.getCurrentValue().toLowerCase().indexOf(')', startIndex);
			}
			return monitoringElement.getCurrentValue().substring(0, startIndex) + interfaceElement.getName() + ":=" //$NON-NLS-1$
					+ newNodeValue + monitoringElement.getCurrentValue().substring(endIndex);
		}
		return ""; //$NON-NLS-1$

	}

	private StructParser() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}

}
