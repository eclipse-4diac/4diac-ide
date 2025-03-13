/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.contracts.model.ContractElement;
import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class ContractUtils {

	private ContractUtils() {

	}

	public static boolean isContractSubapp(final FBNetworkElement element) {
		return element instanceof final SubApp containedSubapp
				&& containedSubapp.getName().startsWith(ContractKeywords.CONTRACT);
	}

	public static boolean isInterval(final String[] parts, final int pos, final CharSequence div) {
		return parts[pos].contains(div);
	}

	public static String createInterval(final ContractElement source) {
		final StringBuilder interval = new StringBuilder();
		interval.append(ContractKeywords.INTERVAL_OPEN);
		interval.append(source.getMin());
		interval.append(ContractKeywords.INTERVAL_DIVIDER);
		interval.append(source.getMax());
		interval.append(ContractKeywords.INTERVAL_CLOSE);
		return interval.toString();
	}

	public static int getStartPosition(final String[] parts, final int posTime) {
		return parts[posTime].length() - ContractKeywords.UNIT_OF_TIME.length();
	}

	public static String createSingleEvent(final List<Event> pins, final String time) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addEventList(pins).addSpace().addOccurs().addWithin().add(time).addMs();
		return cb.getString();
	}

	public static String createRepetition(final List<Event> pins, final String time, final String offset) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addEventList(pins).addSpace().addOccurs().addEvery().add(time).addMs();
		cb.addSpace().addWith().addOffet().add(offset).addMs();
		return cb.getString();
	}

	public static String createReaction(final List<Event> iPins, final List<Event> oPins, final String time) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addWhenever().addEventExpr(iPins).addOccurs().addThen();
		cb.addEventExpr(oPins).addOccurs().addWithin().add(time).addMs();
		return cb.getString();
	}

	public static String createAge(final List<Event> iPins, final List<Event> oPins, final String time) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addWhenever().addEventExpr(oPins).addOccurs().addThen();
		cb.addEventExpr(iPins).addHasOccurred().addWithin().add(time).addMs();
		return cb.getString();
	}

	public static String createCausalReaction(final Event iPin, final Event oPin, final String time) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addReaction().addEOpen().add(iPin.getName()).addComma().add(oPin.getName()).addEClose();
		cb.addWithin().add(time).addMs();
		return cb.getString();
	}

	public static String createCausalAge(final Event iPin, final Event oPin, final String time) {
		final ContractElementBuilder cb = new ContractElementBuilder();
		cb.addAge().addEOpen().add(oPin.getName()).addComma().add(iPin.getName()).addEClose();
		cb.addWithin().add(time).addMs();
		return cb.getString();
	}

	public static String createAssumptionString(final String event, final String time) {
		final ContractElementBuilder elementStr = new ContractElementBuilder();
		elementStr.addEventSpace(event).addOccurs().addEvery().addTime(time).addTimeUnit();
		return elementStr.getString();
	}

	public static String createOffsetString(final String time) {
		final ContractElementBuilder elementStr = new ContractElementBuilder();
		elementStr.addWith().addOffet().addTime(time).addTimeUnit();
		return elementStr.getString();
	}

	public static String createReactionString(final String inputEvent, final String outputEvent, final String time) {
		final ContractElementBuilder elementStr = new ContractElementBuilder();
		elementStr.addReaction().addEOpen().addEvent(inputEvent).addComma().addEvent(outputEvent).addEClose();
		elementStr.addWithin().addTime(time).addTimeUnit();
		return elementStr.getString();
	}

	public static String createGuaranteeString(final String inputEvent, final String outputEvent, final String time) {
		final ContractElementBuilder elementStr = new ContractElementBuilder();
		elementStr.addWhenever().addEventSpace(inputEvent).addOccurs();
		elementStr.addThen().addEventSpace(outputEvent).addOccurs().addWithin().addTime(time).addTimeUnit();
		return elementStr.getString();
	}

	public static String createGuaranteeTwoEvents(final String inputEvent, final String outputEvent,
			final String secondOutputEvent, final String time) {
		final ContractElementBuilder elementStr = new ContractElementBuilder();
		elementStr.addWhenever().addEventSpace(inputEvent).addOccurs();
		elementStr.addThen().addEOpen().addEvent(outputEvent).addComma().addEvent(secondOutputEvent);
		elementStr.addEClose().addOccurs().addWithin().addTime(time).addTimeUnit();
		return elementStr.getString();
	}
}
