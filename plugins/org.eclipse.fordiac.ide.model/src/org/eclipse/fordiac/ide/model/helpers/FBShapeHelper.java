/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.function.Predicate;
import java.util.stream.DoubleStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

public final class FBShapeHelper {

	/*
	 * Constants for preference access
	 */
	private static final String DIAGRAM_PREFERENCE_QUALIFIER = "org.eclipse.fordiac.ide.gef"; //$NON-NLS-1$
	private static int minPinLabelSize = Platform.getPreferencesService().getInt(DIAGRAM_PREFERENCE_QUALIFIER,
			"MinPinLabelSize", 0, null); //$NON-NLS-1$
	private static int maxPinLabelSize = Platform.getPreferencesService().getInt(DIAGRAM_PREFERENCE_QUALIFIER,
			"MaxPinLabelSize", 60, null); //$NON-NLS-1$
	private static int maxTypeLabelSize = Platform.getPreferencesService().getInt(DIAGRAM_PREFERENCE_QUALIFIER,
			"MaxTypeLabelSize", 120, null); //$NON-NLS-1$
	private static final int MAX_INTERFACE_BAR_SIZE = Platform.getPreferencesService()
			.getInt(DIAGRAM_PREFERENCE_QUALIFIER, "MaxInterfaceBarSize", 0, null); //$NON-NLS-1$

	private static double maxInterfaceBarWidth = -1;

	public static final double IEC61499_LINE_HEIGHT = 100.0;
	private static final double AVARAGE_CHAR_WIDTH = CoordinateConverter.INSTANCE
			.screenToIEC61499((int) CoordinateConverter.INSTANCE.getAverageCharacterWidth());

	/*
	 * Constants for width and height adjustments to account for borders, padding,
	 * and so on.
	 */
	private static final double WIDTH_ADJUST_NAME = CoordinateConverter.INSTANCE.screenToIEC61499(5);
	private static final double WIDTH_ADJUST_TYPE_NAME = CoordinateConverter.INSTANCE.screenToIEC61499(47);
	private static final double WIDTH_ADJUST_STRUCT_NAME = CoordinateConverter.INSTANCE.screenToIEC61499(48);
	private static final double WIDTH_ADJUST_INTERFACE = CoordinateConverter.INSTANCE.screenToIEC61499(24);
	// additional padding hidden pin indicator
	private static final double HEIGHT_ADJUST_HIDDEN = CoordinateConverter.INSTANCE.screenToIEC61499(15);

	/*
	 * Note for debugging: Add a tracepoint with the following condition in
	 * FBNetworkElementFigure.setupMouseListener(...).new MouseMotionListener(){...}
	 * .mouseEntered(MouseEvent):
	 *
	 * System.out.println("entered: " + getBounds() + " - " +
	 * model.getVisibleWidth() + ", " + model.getVisibleHeight());
	 */

	/**
	 * Get the width of an FB network element
	 *
	 * @param element The element
	 * @return The width in X coordinates
	 */
	public static double getWidth(final FBNetworkElement element) {
		final double nameWidth = (element.getName().length() * AVARAGE_CHAR_WIDTH) + WIDTH_ADJUST_NAME;
		final double typeNameWidth = (getTypeNameCharacters(element) * AVARAGE_CHAR_WIDTH) + WIDTH_ADJUST_TYPE_NAME;
		final double structNameWidth = (getStructNameCharacters(element) * AVARAGE_CHAR_WIDTH)
				+ WIDTH_ADJUST_STRUCT_NAME;
		final int inputCharacters = getInterfaceCharacters(element, IInterfaceElement::isIsInput);
		final int outputCharacters = getInterfaceCharacters(element, Predicate.not(IInterfaceElement::isIsInput));
		final double interfaceWidth = ((inputCharacters + outputCharacters) * AVARAGE_CHAR_WIDTH)
				+ WIDTH_ADJUST_INTERFACE;
		return DoubleStream.of(nameWidth, typeNameWidth, structNameWidth, interfaceWidth).max().orElse(0);
	}

	/**
	 * Get the height of an FB network element
	 *
	 * @param element The element
	 * @return The height in Y coordinates
	 */
	public static double getHeight(final FBNetworkElement element) {
		final int fbLines = element instanceof StructManipulator ? 3 : 2;
		final int inputLines = getInterfaceLines(element, IInterfaceElement::isIsInput);
		final int outputLines = getInterfaceLines(element, Predicate.not(IInterfaceElement::isIsInput));
		final int lines = fbLines + Math.max(inputLines, outputLines);
		return lines * IEC61499_LINE_HEIGHT + getHeightAdjust(element);
	}

	public static double getMaxInterfaceBarWidth() {
		if (maxInterfaceBarWidth == -1) {
			maxInterfaceBarWidth = (AVARAGE_CHAR_WIDTH * MAX_INTERFACE_BAR_SIZE + 2) + WIDTH_ADJUST_INTERFACE;
		}
		return maxInterfaceBarWidth;
	}

	private static int getInterfaceLines(final FBNetworkElement element, final Predicate<IInterfaceElement> filter) {
		return (int) element.getInterface().getAllInterfaceElements().stream().filter(IInterfaceElement::isVisible)
				.filter(filter).count();
	}

	private static int getInterfaceCharacters(final FBNetworkElement element,
			final Predicate<IInterfaceElement> filter) {
		final int pinLabelSize = element.getInterface().getAllInterfaceElements().stream()
				.filter(IInterfaceElement::isVisible).filter(filter).map(IInterfaceElement::getName)
				.mapToInt(String::length).max().orElse(0);
		return Math.clamp(pinLabelSize, minPinLabelSize, maxPinLabelSize + 1);
	}

	private static int getTypeNameCharacters(final FBNetworkElement element) {
		return element.getTypeName() != null ? Math.min(element.getTypeName().length(), maxTypeLabelSize + 1) : 0;
	}

	private static int getStructNameCharacters(final FBNetworkElement element) {
		if (element instanceof final StructManipulator structManipulator && structManipulator.getDataType() != null) {
			return structManipulator.getDataType().getName().length();
		}
		return 0;
	}

	protected static double getHeightAdjust(final FBNetworkElement element) {
		if (element.getInterface().getAllInterfaceElements().stream()
				.anyMatch(Predicate.not(IInterfaceElement::isVisible))) {
			return HEIGHT_ADJUST_HIDDEN;
		}
		return 0.0;
	}

	private FBShapeHelper() {
		throw new UnsupportedOperationException();
	}
}
