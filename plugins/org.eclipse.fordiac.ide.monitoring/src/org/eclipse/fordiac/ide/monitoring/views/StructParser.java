/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
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
 *   Fabio Gandolfi - added parsing of array of structs
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.AbstractStructTreeNode;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public final class StructParser {

	private static final char STRUCT_LIT_START = '(';
	private static final char STRUCT_LIT_END = ')';
	private static final char ARRAY_LIT_START = '[';
	private static final char ARRAY_LIT_END = ']';

	public static WatchValueTreeNode createStructFromString(final String struct, final StructuredType structType,
			final MonitoringElement monitoringElement, final WatchValueTreeNode parent) {

		final WatchValueTreeNode root = new WatchValueTreeNode(monitoringElement, structType,
				monitoringElement.getPort().getInterfaceElement().getName(), null, null, parent);

		if (isStructLiteral(struct) && !"N/A".equalsIgnoreCase(struct) //$NON-NLS-1$
				|| isArrayLiteral(struct) && !"N/A".equalsIgnoreCase(struct)) { //$NON-NLS-1$
			buildTree(root, structType, struct);
		}

		return root;
	}

	private static void buildTree(final WatchValueTreeNode parent, final StructuredType type, final String struct) {
		// do not consider outer parentheses
		final int START = 1;
		final int END = struct.length() - 1;

		boolean insideString = false;
		boolean process = true;
		int innerCnt = 0;
		int frameStart = START;
		int arrayIndex = 0;

		for (int i = START; i < END; i++) {
			final char c = struct.charAt(i);
			switch (c) {
			case ',':
				if (process && !insideString) {
					final String frame = struct.substring(frameStart, i);
					if (isArrayNode(struct, frame)) {
						addArrayNode(frame, parent, type, arrayIndex);
						arrayIndex++;
					} else {
						addNode(frame, parent, type);
					}
					frameStart = i + 1; // switch to next frame
				}
				break;
			case '$':
				i++; // skip escaped chars
				break;
			case '\'', '\"':
				insideString = !insideString;
				break;
			case ARRAY_LIT_START, STRUCT_LIT_START: // start of inner struct
				// skip the whole inner struct since this will be handled recursively
				if (!insideString) {
					process = false;
					innerCnt++;
				}
				break;
			case ARRAY_LIT_END, STRUCT_LIT_END: // end of inner struct
				if (!insideString) {
					innerCnt--;
					if (innerCnt == 0) {
						process = true;
					}
				}
				break;
			default:
				break;
			}
		}

		// handle last variable (no ',' triggers the node creation)
		if (frameStart != END) {
			final String frame = struct.substring(frameStart, END);
			if (isArrayNode(struct, frame)) {
				addArrayNode(frame, parent, type, arrayIndex);
			} else {
				addNode(frame, parent, type);
			}
		}
	}

	private static boolean isArrayNode(final String parentFrame, final String curFrame) {
		return curFrame.charAt(0) == STRUCT_LIT_START
				|| (curFrame.charAt(0) == ARRAY_LIT_START && parentFrame.charAt(0) == ARRAY_LIT_START);
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

		final WatchValueTreeNode node = new WatchValueTreeNode(monitoringElement, type, name, value, variable, parent);

		if (variable.getType() instanceof final StructuredType structType
				&& (isStructLiteral(value) || isArrayLiteral(value))) {
			buildTree(node, structType, value);
		}
		parent.addChild(node);
	}

	private static void addArrayNode(final String frame, final WatchValueTreeNode parent, final StructuredType type,
			final int arrayIndex) {
		final MonitoringBaseElement monitoringElement = parent.getMonitoringBaseElement();

		final VarDeclaration variable = null;
		final String name = "[" + arrayIndex + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		final String value = frame;

		final WatchValueTreeNode node = new WatchValueTreeNode(monitoringElement, type, name, value, variable, parent);

		if (isStructLiteral(value) || isArrayLiteral(value)) {
			buildTree(node, type, value);
		}
		parent.addChild(node);
	}

	private static boolean isArrayLiteral(final String value) {
		return isDerivedTypeLiteral(value, ARRAY_LIT_START, ARRAY_LIT_END);
	}

	private static boolean isStructLiteral(final String value) {
		return isDerivedTypeLiteral(value, STRUCT_LIT_START, ')');
	}

	private static boolean isDerivedTypeLiteral(final String value, final char opening, final char closing) {
		return !value.isBlank() && value.length() >= 2 && value.charAt(0) == opening
				&& value.charAt(value.length() - 1) == closing;
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

			if ((memberVariable.getType() instanceof final StructuredType memberStructType)
					&& (memberStructType != GenericTypes.ANY_STRUCT)) {
				buildTree(memberStructType, treeNode, element);
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

	public static String toString(final WatchValueTreeNode startNode) {
		final WatchValueTreeNode root = startNode.getRoot();
		final StringBuilder builder = new StringBuilder();
		final boolean isArray = isArray(root);
		toString(root, builder, isArray);
		return isArray ? removeArrayIndexes(builder.toString()) : builder.toString();

	}

	private static boolean isArray(final WatchValueTreeNode root) {
		final MonitoringElement element = (MonitoringElement) root.getMonitoringBaseElement();
		return element.getCurrentValue().startsWith("["); //$NON-NLS-1$
	}

	private static void toString(final WatchValueTreeNode startNode, final StringBuilder builder,
			final boolean isArray) {
		if (startNode.isStructLeaf()) {
			builder.append(startNode.getValue()); // also handles arrays inside of structs
			return;
		}

		if (isArray) {
			builder.append("["); //$NON-NLS-1$
		} else {
			builder.append("("); //$NON-NLS-1$
		}

		for (final AbstractStructTreeNode tmp : startNode.getChildren()) {
			final WatchValueTreeNode node = (WatchValueTreeNode) tmp;
			builder.append(node.getVarName());
			builder.append(":="); //$NON-NLS-1$
			toString(node, builder, false);
			builder.append(","); //$NON-NLS-1$
		}
		// remove last semicolon if necessary
		if (builder.charAt(builder.length() - 1) == ',') {
			builder.setLength(builder.length() - 1);
		}

		if (isArray) {
			builder.append("]"); //$NON-NLS-1$
		} else {
			builder.append(")"); //$NON-NLS-1$
		}
	}

	public static String removeArrayIndexes(final String input) {
		final String parsedInput = input.replaceAll("\\[[0-9]+\\]:=", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return "[" + parsedInput.substring(1, parsedInput.length() - 1) + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private StructParser() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}

}
