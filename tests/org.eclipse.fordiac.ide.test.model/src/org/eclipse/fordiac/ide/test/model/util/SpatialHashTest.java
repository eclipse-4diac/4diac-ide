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
package org.eclipse.fordiac.ide.test.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.util.SpatialHash;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" })
class SpatialHashTest {

	@Test
	void testSimple() {
		final SpatialHash<String> hash = new SpatialHash<>(10, 10);
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.put(0, 0, 5, 5, "zero").isEmpty());
		assertFalse(hash.isEmpty());
		assertEquals(1, hash.size());
		assertTrue(hash.get(0, 0, 0, 0).isEmpty());
		assertTrue(hash.get(-1, -1, 0, 0).isEmpty());
		assertTrue(hash.get(0, 6, 1, 7).isEmpty());
		assertTrue(hash.get(100, 100, 110, 110).isEmpty());
		assertEquals(Set.of("zero"), hash.get(0, 0, 5, 5));
		assertEquals(Set.of("zero"), hash.get(1, 1, 1, 1));
		assertEquals(Set.of("zero"), hash.get(1, 1, 2, 2));
		assertEquals(Set.of("zero"), hash.get(-1000, -1000, 1000, 1000));
		hash.clear();
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.get(-1000, -1000, 1000, 1000).isEmpty());
	}

	@Test
	void testNegative() {
		final SpatialHash<String> hash = new SpatialHash<>(10, 10);
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.put(-5, -5, -1, -1, "neg").isEmpty());
		assertFalse(hash.isEmpty());
		assertEquals(1, hash.size());
		assertTrue(hash.get(0, 0, 0, 0).isEmpty());
		assertTrue(hash.get(-1, -1, 0, 0).isEmpty());
		assertTrue(hash.get(-10, -6, -5, -5).isEmpty());
		assertEquals(Set.of("neg"), hash.get(-10, -10, -1, -1));
		assertEquals(Set.of("neg"), hash.get(-3, -3, -1, -1));
		assertEquals(Set.of("neg"), hash.get(-1000, -1000, 1000, 1000));
		hash.clear();
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.get(-1000, -1000, 1000, 1000).isEmpty());
	}

	@Test
	void testMultiple() {
		final SpatialHash<String> hash = new SpatialHash<>(10, 10);
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.put(0, 0, 5, 5, "zero").isEmpty());
		assertEquals(Set.of("zero"), hash.put(1, 1, 5, 5, "one"));
		assertFalse(hash.isEmpty());
		assertEquals(2, hash.size());
		assertTrue(hash.get(0, 0, 0, 0).isEmpty());
		assertTrue(hash.get(-1, -1, 0, 0).isEmpty());
		assertTrue(hash.get(0, 6, 1, 7).isEmpty());
		assertTrue(hash.get(100, 100, 110, 110).isEmpty());
		assertEquals(Set.of("zero"), hash.get(1, 1, 1, 1));
		assertEquals(Set.of("zero", "one"), hash.get(1, 1, 2, 2));
		assertEquals(Set.of("zero", "one"), hash.get(-1000, -1000, 1000, 1000));
		hash.clear();
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.get(-1000, -1000, 1000, 1000).isEmpty());
	}

	@Test
	@SuppressWarnings("boxing")
	void testMany() {
		final Set<SpatialHash.Entry<Integer>> entries = Set.of(//
				new SpatialHash.Entry<>(0, 0, 0, 0, 0), //
				new SpatialHash.Entry<>(20, 33, 25, 40, 1), //
				new SpatialHash.Entry<>(17, 4, 21, 42, 2), //
				new SpatialHash.Entry<>(-21, -50, -10, -10, 3), //
				new SpatialHash.Entry<>(120, 5, 125, 10, 4), //
				new SpatialHash.Entry<>(20, 20, 50, 50, 5)//
		);
		final SpatialHash<Integer> hash = new SpatialHash<>(10, 10);
		entries.forEach(hash::put);
		assertEquals(6, hash.size());
		assertEquals(entries.stream().map(SpatialHash.Entry::getValue).collect(Collectors.toSet()),
				hash.get(-1000, -1000, 1000, 1000));
		assertIntersectingEntriesEquals(entries, hash, -50, 150);
		hash.clear();
		assertTrue(hash.isEmpty());
		assertEquals(0, hash.size());
		assertTrue(hash.get(-1000, -1000, 1000, 1000).isEmpty());
	}

	private static void assertIntersectingEntriesEquals(final Set<SpatialHash.Entry<Integer>> entries,
			final SpatialHash<Integer> hash, final int start, final int end) {
		for (int x1 = start; x1 <= end; x1 += 5) {
			for (int y1 = start; y1 <= end; y1 += 5) {
				for (int x2 = Math.max(start, x1); x2 <= end; x2 += 5) {
					for (int y2 = Math.max(start, y1); y2 <= end; y2 += 5) {
						assertIntersectingEntriesEquals(entries, hash, x1, y1, x2, y2);
					}
				}
			}
		}
	}

	@SuppressWarnings("boxing")
	private static void assertIntersectingEntriesEquals(final Set<SpatialHash.Entry<Integer>> entries,
			final SpatialHash<Integer> hash, final int x1, final int y1, final int x2, final int y2) {
		assertEquals(
				entries.stream().filter(entry -> entry.intersects(x1, y1, x2, y2)).map(SpatialHash.Entry::getValue)
						.collect(Collectors.toSet()),
				hash.get(x1, y1, x2, y2), () -> String.format("%d, %d, %d, %d", x1, x2, y1, y2));
	}
}
