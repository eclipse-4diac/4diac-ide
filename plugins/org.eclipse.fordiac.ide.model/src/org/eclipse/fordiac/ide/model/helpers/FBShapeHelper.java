/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - changed sizes calculation to correctly represent figures
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.preferences.ModelPreferenceConstants;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;

public final class FBShapeHelper {
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

	private static final double WIDTH_ADJUST_INTERFACE = CoordinateConverter.INSTANCE.screenToIEC61499(12);
	private static final double WIDTH_ADJUST_INTERFACE_ADAPTER = CoordinateConverter.INSTANCE.screenToIEC61499(17);
	private static final double WIDTH_ADJUST_INTERFACE_INOUT = CoordinateConverter.INSTANCE.screenToIEC61499(18);
	private static final double WIDTH_ADJUST_HIDDEN = CoordinateConverter.INSTANCE.screenToIEC61499(21);
	private static final double WIDTH_ADJUST_SUBAPP_INTERFACE = CoordinateConverter.INSTANCE.screenToIEC61499(35);

	private static final double HEIGHT_ADJUST = CoordinateConverter.INSTANCE.screenToIEC61499(7);
	private static final double HEIGHT_ADJUST_MUX = CoordinateConverter.INSTANCE.screenToIEC61499(8);
	private static final double HEIGHT_ADJUST_HIDDEN = CoordinateConverter.INSTANCE.screenToIEC61499(15);
	private static final double HEIGHT_ADJUST_SUBAPP = CoordinateConverter.INSTANCE.screenToIEC61499(19);
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
		final IProject project = getProjectFromFBNE(element);
		final int minPinLabelSize = PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID,
				ModelPreferenceConstants.MIN_PIN_LABEL_SIZE, 0, project);
		final int maxPinLabelSize = PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID,
				ModelPreferenceConstants.MAX_PIN_LABEL_SIZE, 60, project) + 1;
		final int maxTypeLabelSize = PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID,
				ModelPreferenceConstants.MAX_TYPE_LABEL_SIZE, 120, project) + 1;

		final double nameWidth = element.getName().length() * AVARAGE_CHAR_WIDTH + WIDTH_ADJUST_NAME;
		final double typeNameWidth = getTypeNameCharacters(element, maxTypeLabelSize) * AVARAGE_CHAR_WIDTH
				+ WIDTH_ADJUST_TYPE_NAME;
		final double structNameWidth = getStructNameWidth(element);

		final double inputWidthTop = getMaxEventPinWidth(element.getInterface().getEventInputs().stream(),
				minPinLabelSize, maxPinLabelSize);
		final double outputWidthTop = getMaxEventPinWidth(element.getInterface().getEventOutputs().stream(),
				minPinLabelSize, maxPinLabelSize);

		final double inputWidthBottom = getMaxPinWidth(element, minPinLabelSize, maxPinLabelSize, true);
		final double outputWidthBottom = getMaxPinWidth(element, minPinLabelSize, maxPinLabelSize, false);

		final double interfaceWidth = Math.max(inputWidthTop + outputWidthTop, inputWidthBottom + outputWidthBottom);
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

		final InterfaceList interfaceList = element.getInterface();
		final int eventLines = getMaxLinesCounter(interfaceList.getEventInputs(), interfaceList.getEventOutputs());
		final int dataLines = getMaxLinesCounter(interfaceList.getInputVars(), interfaceList.getOutputVars());
		final int adapterLines = getMaxLinesCounter(interfaceList.getPlugs(), interfaceList.getSockets());
		final int inOutLines = getMaxLinesCounter(interfaceList.getInOutVars(), interfaceList.getOutMappedInOutVars());
		final int errorLines = getMaxLinesCounter(
				interfaceList.getErrorMarker().stream().filter(IInterfaceElement::isIsInput),
				interfaceList.getErrorMarker().stream().filter(Predicate.not(IInterfaceElement::isIsInput)));

		final int lines = eventLines + fbLines + dataLines + inOutLines + adapterLines + errorLines;
		final double heightAdjust = element instanceof StructManipulator ? HEIGHT_ADJUST_MUX : HEIGHT_ADJUST;
		return lines * IEC61499_LINE_HEIGHT + getHiddenHeightAdjust(element) + heightAdjust;
	}

	/**
	 * Get the max width of the interface bars of an expanded SubApp
	 *
	 * @param element The SubApp
	 * @return The width in X coordinates
	 */
	public static double getSubappWidthAdjust(final FBNetworkElement element) {
		final IProject project = getProjectFromFBNE(element);
		final int maxInterfaceBarSize = PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID,
				ModelPreferenceConstants.MAX_INTERFACE_BAR_SIZE, 40, project);
		return 2 * (AVARAGE_CHAR_WIDTH * maxInterfaceBarSize + WIDTH_ADJUST_SUBAPP_INTERFACE);
	}

	/**
	 * Get the height difference of an expanded SubApp and it's inner network
	 *
	 * @param element The SubApp
	 * @return The height in Y coordinates
	 */
	public static double getSubappHeightAdjust(final FBNetworkElement element) {
		int commentLines = 1;
		if (element.getComment() != null && !element.getComment().isBlank()) {
			commentLines += element.getComment().chars().filter(c -> c == '\n').count();
		}

		return (commentLines + 1) * FBShapeHelper.IEC61499_LINE_HEIGHT + HEIGHT_ADJUST_SUBAPP;
	}

	private static int getTypeNameCharacters(final FBNetworkElement element, final int maxTypeLabelSize) {
		return element.getTypeName() != null ? Math.min(element.getTypeName().length(), maxTypeLabelSize) : 0;
	}

	private static double getStructNameWidth(final FBNetworkElement element) {
		if (element instanceof final StructManipulator structManipulator && structManipulator.getDataType() != null) {
			return structManipulator.getDataType().getName().length() * AVARAGE_CHAR_WIDTH + WIDTH_ADJUST_STRUCT_NAME;
		}
		return 0;
	}

	private static double getMaxEventPinWidth(final Stream<Event> pins, final int minPinLabelSize,
			final int maxPinLabelSize) {
		int topInputLabelSize = pins.filter(IInterfaceElement::isVisible).map(IInterfaceElement::getName)
				.mapToInt(String::length).max().orElse(0);
		topInputLabelSize = Math.clamp(topInputLabelSize, minPinLabelSize, maxPinLabelSize);

		return topInputLabelSize * AVARAGE_CHAR_WIDTH + WIDTH_ADJUST_INTERFACE;
	}

	private static double getMaxPinWidth(final FBNetworkElement element, final int minPinLabelSize,
			final int maxPinLabelSize, final boolean isInput) {
		final Predicate<IInterfaceElement> inputFilter = isInput ? IInterfaceElement::isIsInput
				: Predicate.not(IInterfaceElement::isIsInput);

		final double largestInterface = element.getInterface().getAllInterfaceElements().stream()
				.filter(Predicate.not(Event.class::isInstance)).filter(IInterfaceElement::isVisible).filter(inputFilter)
				.mapToDouble(ie -> {
					final int labelSize = Math.clamp(ie.getName().length(), minPinLabelSize, maxPinLabelSize);

					final double symbolSize = switch (ie) {
					case final VarDeclaration v when v.isInOutVar() -> WIDTH_ADJUST_INTERFACE_INOUT;
					case final AdapterDeclaration a -> WIDTH_ADJUST_INTERFACE_ADAPTER;
					default -> WIDTH_ADJUST_INTERFACE;
					};

					return labelSize * AVARAGE_CHAR_WIDTH + symbolSize;
				}).max().orElse(0.0);

		if (largestInterface < WIDTH_ADJUST_HIDDEN) {
			// hidden marker is bigger than normal pin with 1 character
			final double hiddenSize = getHiddenWidthAdjust(element, isInput);
			if (hiddenSize != 0.0) {
				return hiddenSize;
			}
		}

		return largestInterface;
	}

	protected static double getHiddenWidthAdjust(final FBNetworkElement element, final boolean isInput) {
		final Predicate<IInterfaceElement> inputFilter = isInput ? IInterfaceElement::isIsInput
				: Predicate.not(IInterfaceElement::isIsInput);

		if (element.getInterface().getAllInterfaceElements().stream().filter(inputFilter)
				.anyMatch(Predicate.not(IInterfaceElement::isVisible)) || (!isInput && checkHiddenDemuxPin(element))) {
			return WIDTH_ADJUST_HIDDEN;
		}
		return 0.0;
	}

	protected static double getHiddenHeightAdjust(final FBNetworkElement element) {
		if (element.getInterface().getAllInterfaceElements().stream()
				.anyMatch(Predicate.not(IInterfaceElement::isVisible)) || checkHiddenDemuxPin(element)) {
			return HEIGHT_ADJUST_HIDDEN;
		}
		return 0.0;
	}

	private static boolean checkHiddenDemuxPin(final FBNetworkElement element) {
		if (element instanceof final Demultiplexer demux && demux.getDataType() != null) {
			final int visibleVars = demux.getMemberVars().size();
			final int allVars = ((StructuredType) demux.getDataType()).getMemberVariables().size();
			if (visibleVars != allVars) {
				return true;
			}
		}
		return false;
	}

	private static int getMaxLinesCounter(final List<? extends IInterfaceElement> inputPins,
			final List<? extends IInterfaceElement> outputPins) {
		return getMaxLinesCounter(inputPins.stream(), outputPins.stream());
	}

	private static int getMaxLinesCounter(final Stream<? extends IInterfaceElement> inputPins,
			final Stream<? extends IInterfaceElement> outputPins) {
		return Math.max(countVisiblePins(inputPins), countVisiblePins(outputPins));
	}

	private static int countVisiblePins(final Stream<? extends IInterfaceElement> pins) {
		return (int) pins.filter(IInterfaceElement::isVisible).count();
	}

	private static IProject getProjectFromFBNE(final FBNetworkElement element) {
		if (EcoreUtil.getRootContainer(element) instanceof final LibraryElement libElement) {
			return libElement.getTypeEntry().getFile().getProject();
		}
		return null;
	}

	private FBShapeHelper() {
		throw new UnsupportedOperationException();
	}
}
