/*******************************************************************************
 * Copyright (c) 2023, 2025 Paul Pavlicek and others
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
 *   Felix Schmid
 *     - simplified by using format strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

@SuppressWarnings("nls") // translating doesn't make sense here
public final class ContractUtils {

	private ContractUtils() {

	}

	public static String createSingleEvent(final List<Event> pins, final String timeExpr) {
		return createSingleEvent(createEventList(pins), timeExpr);
	}

	public static String createSingleEvent(final String pins, final String timeExpr) {
		return "%s occurs within %s".formatted(pins, timeExpr);
	}

	public static String createRepetition(final List<Event> pins, final String timeExpr, final String offsetExpr,
			final String jitterExpr) {
		return createRepetition(createEventList(pins), timeExpr, offsetExpr, jitterExpr);
	}

	public static String createRepetition(final String pins, final String timeExpr, final String offsetExpr,
			final String jitterExpr) {
		String repetition = "%s occurs every %s".formatted(pins, timeExpr);

		final boolean offset = offsetExpr != null && !offsetExpr.isBlank();
		final boolean jitter = jitterExpr != null && !jitterExpr.isBlank();

		if (offset && jitter) {
			repetition += " with offset %s and jitter %s".formatted(offsetExpr, jitterExpr);
		} else if (offset) {
			repetition += " with offset %s".formatted(offsetExpr);
		} else if (jitter) {
			repetition += " with jitter %s".formatted(jitterExpr);
		}
		return repetition;
	}

	public static String createReaction(final List<Event> iPins, final List<Event> oPins, final String timeExpr,
			final boolean once, final int n, final int outOf) {
		return createReaction(createEventSequence(iPins), createEventSequence(oPins), timeExpr, once, n, outOf);
	}

	public static String createReaction(final String iPins, final String oPins, final String timeExpr,
			final boolean once, final int n, final int outOf) {
		final String reaction = "whenever %s occurs then %s occurs within %s".formatted(iPins, oPins, timeExpr);
		return addReactionProperties(reaction, once, n, outOf);
	}

	public static String createAge(final List<Event> iPins, final List<Event> oPins, final String timeExpr,
			final boolean once, final int n, final int outOf) {
		return createAge(createEventSequence(iPins), createEventSequence(oPins), timeExpr, once, n, outOf);
	}

	public static String createAge(final String iPins, final String oPins, final String timeExpr, final boolean once,
			final int n, final int outOf) {
		final String age = "whenever %s occurs then %s has occurred within %s".formatted(oPins, iPins, timeExpr);
		return addReactionProperties(age, once, n, outOf);
	}

	public static String createCausalReaction(final Event iPin, final Event oPin, final String timeExpr) {
		return createCausalReaction(iPin.getName(), oPin.getName(), timeExpr);
	}

	public static String createCausalReaction(final String iPin, final String oPin, final String timeExpr) {
		return "Reaction(%s, %s) within %s".formatted(iPin, oPin, timeExpr);
	}

	public static String createCausalAge(final Event iPin, final Event oPin, final String timeExpr) {
		return createCausalAge(iPin.getName(), oPin.getName(), timeExpr);
	}

	public static String createCausalAge(final String iPin, final String oPin, final String timeExpr) {
		return "Age(%s, %s) within %s".formatted(oPin, iPin, timeExpr);
	}

	private static String createEventList(final List<Event> pins) {
		return String.join(", ", pins.stream().map(Event::getName).toList()); //$NON-NLS-1$
	}

	private static String createEventSequence(final List<Event> pins) {
		if (pins.size() == 1) {
			return pins.get(0).getName();
		}
		final StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(createEventList(pins));
		sb.append(')');
		return sb.toString();
	}

	@SuppressWarnings("boxing")
	private static String addReactionProperties(String rule, final boolean once, final int n, final int outOf) {
		if (once) {
			rule += " once";
		}
		if (n != outOf) {
			rule += " %d out of %d times".formatted(n, outOf);
		}
		return rule;
	}
}
