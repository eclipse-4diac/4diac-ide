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
package org.eclipse.fordiac.ide.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SpatialHash<T> {

	private static final int INITIAL_BUCKET_SIZE = 10;

	private final int cellSize;
	private final int gridSize;
	private final Entry<T>[][] table;
	private int size;

	public SpatialHash(final int cellSize, final int gridSize) {
		this.cellSize = cellSize;
		this.gridSize = gridSize;
		table = newTable(gridSize * gridSize);
	}

	public Set<T> get(final int x1, final int y1, final int x2, final int y2) {
		final int x1Index = Math.floorDiv(x1, cellSize);
		final int y1Index = Math.floorDiv(y1, cellSize);
		final int x2Index = Math.floorDiv(x2, cellSize);
		final int y2Index = Math.floorDiv(y2, cellSize);
		final Set<T> result = new HashSet<>();
		for (int x = x1Index; x <= x2Index; x++) {
			for (int y = y1Index; y <= y2Index; y++) {
				getEntries(tableIndex(x, y), x1, y1, x2, y2, result);
			}
		}
		return result;
	}

	protected void getEntries(final int tableIndex, final int x1, final int y1, final int x2, final int y2,
			final Set<T> result) {
		final Entry<T>[] bucket = table[tableIndex];
		if (bucket != null) {
			for (final Entry<T> entry : bucket) {
				if (entry == null) {
					break;
				}
				if (entry.intersects(x1, y1, x2, y2)) {
					result.add(entry.value);
				}
			}
		}
	}

	public Set<T> put(final int x1, final int y1, final int x2, final int y2, final T value) {
		final int x1Index = Math.floorDiv(x1, cellSize);
		final int y1Index = Math.floorDiv(y1, cellSize);
		final int x2Index = Math.floorDiv(x2, cellSize);
		final int y2Index = Math.floorDiv(y2, cellSize);
		final Set<T> result = new HashSet<>();
		final Entry<T> newEntry = new Entry<>(x1, y1, x2, y2, value);
		for (int x = x1Index; x <= x2Index; x++) {
			for (int y = y1Index; y <= y2Index; y++) {
				putEntry(tableIndex(x, y), newEntry, result);
			}
		}
		size++;
		return result;
	}

	protected void putEntry(final int tableIndex, final Entry<T> newEntry, final Set<T> result) {
		final Entry<T>[] bucket = table[tableIndex];
		if (bucket != null) {
			for (int i = 0; i < bucket.length; i++) {
				final Entry<T> entry = bucket[i];
				if (entry == newEntry) {
					return;
				}
				if (entry == null) {
					bucket[i] = newEntry;
					return;
				}
				if (entry.intersects(newEntry)) {
					result.add(entry.value);
				}
			}
			final Entry<T>[] newBucket = newBucket(bucket.length << 1);
			System.arraycopy(bucket, 0, newBucket, 0, bucket.length);
			newBucket[bucket.length] = newEntry;
			table[tableIndex] = newBucket;
		} else {
			final Entry<T>[] newBucket = newBucket(INITIAL_BUCKET_SIZE);
			newBucket[0] = newEntry;
			table[tableIndex] = newBucket;
		}
	}

	public Set<T> put(final Entry<T> entry) {
		return put(entry.x1, entry.y1, entry.x2, entry.y2, entry.value);
	}

	protected int tableIndex(final int x, final int y) {
		return Math.floorMod(x, gridSize) + Math.floorMod(y, gridSize) * gridSize;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		Arrays.fill(table, null);
		size = 0;
	}

	@SuppressWarnings("unchecked")
	protected Entry<T>[][] newTable(final int length) {
		return new Entry[length][];
	}

	@SuppressWarnings("unchecked")
	protected Entry<T>[] newBucket(final int length) {
		return new Entry[length];
	}

	public static class Entry<T> {
		private final int x1;
		private final int y1;
		private final int x2;
		private final int y2;
		private final T value;

		public Entry(final int x1, final int y1, final int x2, final int y2, final T value) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.value = value;
		}

		public boolean intersects(final Entry<?> other) {
			return x1 < other.x2 && other.x1 < x2 && y1 < other.y2 && other.y1 < y2;
		}

		public boolean intersects(final int x1, final int y1, final int x2, final int y2) {
			return x1 < this.x2 && this.x1 < x2 && y1 < this.y2 && this.y1 < y2;
		}

		public int getX1() {
			return x1;
		}

		public int getY1() {
			return y1;
		}

		public int getX2() {
			return x2;
		}

		public int getY2() {
			return y2;
		}

		public T getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x1;
			result = prime * result + y1;
			result = prime * result + x2;
			result = prime * result + y2;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Entry)) {
				return false;
			}
			final Entry<?> other = (Entry<?>) obj;
			return x1 == other.x1 && x2 == other.x2 && y1 == other.y1 && y2 == other.y2
					&& Objects.equals(value, other.value);
		}
	}
}
