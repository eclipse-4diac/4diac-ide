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
import java.util.stream.IntStream;

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

	private static int maxInterfaceBarWidth = -1;

	/*
	 * Constants for width and height adjustments to account for borders, padding,
	 * and so on.
	 */
	private static final int WIDTH_ADJUST_NAME = 5;
	private static final int WIDTH_ADJUST_TYPE_NAME = 47;
	private static final int WIDTH_ADJUST_STRUCT_NAME = 48;
	private static final int WIDTH_ADJUST_INTERFACE = 24;
	private static final int HEIGHT_ADJUST = 14;
	private static final int HEIGHT_ADJUST_STRUCT = 1; // additional padding for struct name
	private static final int HEIGHT_ADJUST_HIDDEN = 15; // additional padding hidden pin indicator

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
	public static int getWidth(final FBNetworkElement element) {
		final double averageCharacterWidth = CoordinateConverter.INSTANCE.getAverageCharacterWidth();
		final int nameWidth = (int) (element.getName().length() * averageCharacterWidth) + WIDTH_ADJUST_NAME;
		final int typeNameWidth = (int) (getTypeNameCharacters(element) * averageCharacterWidth)
				+ WIDTH_ADJUST_TYPE_NAME;
		final int structNameWidth = (int) (getStructNameCharacters(element) * averageCharacterWidth)
				+ WIDTH_ADJUST_STRUCT_NAME;
		final int inputCharacters = getInterfaceCharacters(element, IInterfaceElement::isIsInput);
		final int outputCharacters = getInterfaceCharacters(element, Predicate.not(IInterfaceElement::isIsInput));
		final int interfaceWidth = (int) ((inputCharacters + outputCharacters) * averageCharacterWidth)
				+ WIDTH_ADJUST_INTERFACE;
		return IntStream.of(nameWidth, typeNameWidth, structNameWidth, interfaceWidth).max().orElse(0);
	}

	/**
	 * Get the height of an FB network element
	 *
	 * @param element The element
	 * @return The height in Y coordinates
	 */
	public static int getHeight(final FBNetworkElement element) {
		final double lineHeight = CoordinateConverter.INSTANCE.getLineHeight();
		final int fbLines = element instanceof StructManipulator ? 3 : 2;
		final int inputLines = getInterfaceLines(element, IInterfaceElement::isIsInput);
		final int outputLines = getInterfaceLines(element, Predicate.not(IInterfaceElement::isIsInput));
		final int lines = fbLines + Math.max(inputLines, outputLines);
		final int heightAdjust = getHeightAdjust(element);
		return (int) (lines * lineHeight) + heightAdjust;
	}

	public static int getMaxInterfaceBarWidth() {
		if (maxInterfaceBarWidth == -1) {
			maxInterfaceBarWidth = (int) (CoordinateConverter.INSTANCE.getAverageCharacterWidth()
					* MAX_INTERFACE_BAR_SIZE + 2) + WIDTH_ADJUST_INTERFACE;
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

	protected static int getHeightAdjust(final FBNetworkElement element) {
		int heightAdjust = HEIGHT_ADJUST;
		if (element instanceof StructManipulator) {
			heightAdjust += HEIGHT_ADJUST_STRUCT;
		}
		if (element.getInterface().getAllInterfaceElements().stream()
				.anyMatch(Predicate.not(IInterfaceElement::isVisible))) {
			heightAdjust += HEIGHT_ADJUST_HIDDEN;
		}
		return heightAdjust;
	}

	private FBShapeHelper() {
		throw new UnsupportedOperationException();
	}
}
