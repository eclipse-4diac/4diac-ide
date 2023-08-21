/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, fortiss GmbH
 * 							 Johannes Kepler University,
 *				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benjamin Muttenthaler - Extracted WatchValueTreeNode manipulation part from
 *   						 MonitoringEditPart to here
 *******************************************************************************/

package org.eclipse.fordiac.ide.monitoring.views;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.AbstractStructTreeNode;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class WatchValueTreeNodeUtils {

	private static final String HEX_PREFIX = "16#"; //$NON-NLS-1$
	public static final String ASSIGN = ":="; //$NON-NLS-1$
	public static final String CLAMP_OP = "("; //$NON-NLS-1$
	public static final String CLAMP_CL = ")"; //$NON-NLS-1$
	public static final String BRACK_OP = "["; //$NON-NLS-1$
	public static final String BRACK_CL = "]"; //$NON-NLS-1$
	public static final String DELIMITER = ","; //$NON-NLS-1$

	/**
	 * @param value ... online value to check
	 * @param type  ... type of the online value
	 * @param model ... the monitoring element
	 * @return all values of a datatype that inherits from ANY_BIT (except BOOL) is
	 *         represented as hex-decimal (doesn't matter if the value is within a
	 *         struct or not).
	 */
	public static String decorateHexValue(final String value, final DataType type, final MonitoringElement model) {
		if (isArray(value) && isHexValue(type)) {
			// only split array if type really needs decorating
			return decorateArrayHex(value, type, model);
		}
		if (isHexDecorationNecessary(value, type)) {
			return decorateHexNumber(value);
		}
		if (isStruct(type)) {
			try {
				final var parent = new WatchValueTreeNode(model);
				final var dbgStruct = StructParser.createStructFromString(value, (StructuredType) type, model, parent);
				dbgStruct.setIsArray(isArray(value));
				adaptAnyBitValues(dbgStruct.getChildren());
				return buildTreeString(dbgStruct);
			} catch (final Exception ex) {
				FordiacLogHelper.logWarning("Wrong Struct Value: " + value, ex); //$NON-NLS-1$
				return value; // return FORTE value
			}
		}

		return value;
	}

	private static boolean isArray(final String value) {
		return value.startsWith("["); //$NON-NLS-1$
	}

	private static String decorateArrayHex(final String value, final DataType type, final MonitoringElement model) {
		// @formatter:off
		return Arrays.stream(value.substring(1, value.length() - 1).split(",")) //$NON-NLS-1$
			.map(val -> decorateHexValue(val, type, model))
			.collect(Collectors.joining(",", "[", "]")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			.toString();
		// @formatter:on
	}

	/**
	 *
	 * @param value ... the online value to check
	 * @param type  ... type of the online value
	 * @return value datatype is inherited from ANY_BIT and not of datatype BOOL
	 */
	public static boolean isHexDecorationNecessary(final String value, final DataType type) {
		return isHexValue(type) && isNumeric(value) && !value.startsWith(HEX_PREFIX);
	}

	/**
	 * Decorates all values of a type that inherits from ANY_BIT (except BOOL)
	 *
	 * @param childrens ... of a struct
	 */
	public static void adaptAnyBitValues(final List<AbstractStructTreeNode> childrens) {
		for (final AbstractStructTreeNode structTreeNode : childrens) {
			if (structTreeNode.hasChildren()) {
				adaptAnyBitValues(structTreeNode.getChildren());
			}
			final VarDeclaration structTreeNodeVar = structTreeNode.getVariable();
			final WatchValueTreeNode watchedValueTreeNode = (WatchValueTreeNode) structTreeNode;
			if (structTreeNodeVar != null
					&& isHexDecorationNecessary(watchedValueTreeNode.getValue(), structTreeNodeVar.getType())) {
				watchedValueTreeNode.setValue(decorateHexNumber(watchedValueTreeNode.getValue()));
			}
		}
	}

	/**
	 * @param value ... to decorated
	 * @return the passed parameter as hex-decimal format
	 */
	public static String decorateHexNumber(final String value) {
		long parseInt;
		try {
			parseInt = Long.parseUnsignedLong(value);
		} catch (final NumberFormatException e) {
			parseInt = 0;
		}
		return convertIntegerToHexString(parseInt);
	}

	public static String buildTreeString(final WatchValueTreeNode dbgStruct) {
		final String open = dbgStruct.isArray() ? BRACK_OP : CLAMP_OP;
		final String close = dbgStruct.isArray() ? BRACK_CL : CLAMP_CL;

		// @formatter:off
		return dbgStruct.getChildren().stream()
			.map(WatchValueTreeNode.class::cast)
			.map(WatchValueTreeNodeUtils::convertNodeToString)
			.collect(Collectors.joining(DELIMITER, open, close));
		// @formatter:on
	}

	private static String convertNodeToString(final WatchValueTreeNode node) {
		final var builder = new StringBuilder();
		final var parent = (WatchValueTreeNode) node.getParent();

		if (!parent.isArray()) {
			builder.append(node.getVarName());
			builder.append(ASSIGN);
		}

		builder.append(node.hasChildren() ? buildTreeString(node) : node.getValue());
		return builder.toString();
	}

	private static boolean isStruct(final DataType type) {
		return type instanceof StructuredType;
	}

	private static String convertIntegerToHexString(final long number) {
		return HEX_PREFIX + Long.toHexString(number).toUpperCase();
	}

	private static boolean isNumeric(final String input) {
		return input.chars().allMatch(Character::isDigit);
	}

	// checks if the value should be converted to a hexstring
	private static boolean isHexValue(final DataType type) {
		return (type instanceof AnyBitType) && !(type instanceof BoolType);
	}

	private WatchValueTreeNodeUtils() {
		throw new UnsupportedOperationException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
