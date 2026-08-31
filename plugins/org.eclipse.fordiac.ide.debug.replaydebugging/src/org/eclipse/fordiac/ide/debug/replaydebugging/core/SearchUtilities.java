/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Utilities to search for values of data points using certain conditions
 *
 * The main idea is to search in timelines recursive for datapoints, get their
 * values and do some comparison. The ValueHandle templated record is used for
 * two things: 1. transform the value String into the templated type 2. Check if
 * the value fulfills certain conditions (less than, equal, is present, ...)
 * 
 * Some common ValueHandle are already provided
 */
public class SearchUtilities {
	private SearchUtilities() {
		/* This utility class should not be instantiated */
	}

	/**
	 * Converts a String to T (string -> T), and checks certain condition ( (T, T)
	 * -> boolean)
	 *
	 * @param <T> The type to convert to from String s
	 */
	public record ValueHandler<T>(Function<String, T> parser, BiPredicate<T, T> matcher) {
	}

	// Strings
	public static final ValueHandler<String> StringEquals = new ValueHandler<>(Function.identity(), String::equals);

	// Booleans
	public static final ValueHandler<Boolean> BooleanEquals = new ValueHandler<>(Boolean::parseBoolean,
			Boolean::equals);
	public static final ValueHandler<Boolean> BooleanNotEquals = new ValueHandler<>(Boolean::parseBoolean,
			(actual, expected) -> !actual.equals(expected));

	// Integers
	public static final ValueHandler<Integer> IntegerEquals = new ValueHandler<>(Integer::parseInt, Integer::equals);
	public static final ValueHandler<Integer> IntegerNotEquals = new ValueHandler<>(Integer::parseInt,
			(actual, expected) -> !actual.equals(expected));
	public static final ValueHandler<Integer> IntegerGreaterThan = new ValueHandler<>(Integer::parseInt,
			(actual, expected) -> actual.intValue() > expected.intValue());
	public static final ValueHandler<Integer> IntegerLessThan = new ValueHandler<>(Integer::parseInt,
			(actual, expected) -> actual.intValue() < expected.intValue());
	public static final ValueHandler<Integer> IntegerGreaterThanOrEqual = new ValueHandler<>(Integer::parseInt,
			(actual, expected) -> actual.intValue() >= expected.intValue());
	public static final ValueHandler<Integer> IntegerLessThanOrEqual = new ValueHandler<>(Integer::parseInt,
			(actual, expected) -> actual.intValue() <= expected.intValue());

	// Doubles
	public static final ValueHandler<Double> DoubleEquals = new ValueHandler<>(Double::parseDouble, Double::equals);
	public static final ValueHandler<Double> DoubleNotEquals = new ValueHandler<>(Double::parseDouble,
			(actual, expected) -> !actual.equals(expected));
	public static final ValueHandler<Double> DoubleGreaterThan = new ValueHandler<>(Double::parseDouble,
			(actual, expected) -> actual.doubleValue() > expected.doubleValue());
	public static final ValueHandler<Double> DoubleLessThan = new ValueHandler<>(Double::parseDouble,
			(actual, expected) -> actual.doubleValue() < expected.doubleValue());
	public static final ValueHandler<Double> DoubleGreaterThanOrEqual = new ValueHandler<>(Double::parseDouble,
			(actual, expected) -> actual.doubleValue() >= expected.doubleValue());
	public static final ValueHandler<Double> DoubleLessThanOrEqual = new ValueHandler<>(Double::parseDouble,
			(actual, expected) -> actual.doubleValue() <= expected.doubleValue());

	// Presence
	public static final ValueHandler<Void> Presence = new ValueHandler<>(_ -> null, (_, _) -> true);

	public static <T> List<ReplayNavigator.EventPosition> searchFor(final Timeline timeline, final String datapoint,
			final String value, final ValueHandler<T> handler) {
		final var result = new ArrayList<ReplayNavigator.EventPosition>();
		final T expected = handler.parser().apply(value);
		process(timeline, datapoint, expected, handler, result);
		return result;
	}

	private static <T> void process(final Timeline timeline, final String datapoint, final T expected,
			final ValueHandler<T> handler, final List<ReplayNavigator.EventPosition> result) {
		final var events = timeline.getEventsFrom(0);
		for (var i = 0; i < events.size(); i++) {
			for (final var changedDatapoint : events.get(i).newValues()) {
				if (changedDatapoint.datapoint().equals(datapoint)) {
					final T actual = handler.parser().apply(changedDatapoint.newValue());

					if (handler.matcher().test(actual, expected)) {
						result.add(new ReplayNavigator.EventPosition(timeline, i));
					}
					// if we found the datapoint, we can stop looking at the rest of the changed
					// datapoints for this event
					break;
				}
			}
		}
		for (final var spawnedTimeline : timeline.getSpawnedTimelines()) {
			process(spawnedTimeline, datapoint, expected, handler, result);
		}
	}

}
