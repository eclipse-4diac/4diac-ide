/*******************************************************************************
 * Copyright (c) 2014, 2023 Dirk Fauth and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *    Roman Flueckiger <roman.flueckiger@mac.com - switched to concurrent hash maps to prevent a concurrency issue
 *    Dirk Fauth <dirk.fauth@googlemail.com> - Bug 459246
 *    Martin Erich Jobst - copied from SafeCalculatedValueCache and changed to be safe for concurrent requests
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.CellVisualChangeEvent;
import org.eclipse.nebula.widgets.nattable.util.ICalculatedValueCache;
import org.eclipse.nebula.widgets.nattable.util.ICalculatedValueCacheKey;
import org.eclipse.nebula.widgets.nattable.util.ICalculator;

/** A cache that properly handles concurrent requests without starting additional tasks that redundantly calculate the
 * value. */
public class SafeCalculatedValueCache implements ICalculatedValueCache {

	/** The ILayer this value cache is connected to. Needed to perform cell update events when background calculation
	 * processes are finished. */
	private ILayer layer;

	/** ExecutorService that is used to create background threads to process calculations. */
	private final ExecutorService executor;

	/** Cache that contains the calculated values. Introduced for performance reasons since the calculation could be CPU
	 * intensive.
	 * <p>
	 * This cache will receive updates, e.g. gets cleared on data structure updates, and will be used to determine
	 * whether a new calculation is necessary. */
	private final Map<ICalculatedValueCacheKey, Object> cache = new ConcurrentHashMap<>();

	/** Cache copy of the calculated values.
	 * <p>
	 * This cache will be used to return the value to display. If a value was calculated before it will be returned
	 * until it is recalculated.
	 * <p>
	 * Using a cache copy we ensure smooth updates of calculated values as the prior calculated values will be returned
	 * and updated after the new calculation has finished instead of switching to the default calculation value on
	 * updates. */
	private final Map<ICalculatedValueCacheKey, Object> cacheCopy = new ConcurrentHashMap<>();

	/** Currently executing calculations.
	 * <p>
	 * This will be used to avoid redundant calculations for concurrent requests. */
	private final Set<ICalculatedValueCacheKey> executing = ConcurrentHashMap.newKeySet();

	/** Flag to specify if the column position should be used as cache key.
	 * <p>
	 * Can be used together with the row position, so the column/row coordinates will be used as cache key together. */
	private final boolean useColumnAsKey;

	/** Flag to specify if the row position should be used as cache key.
	 * <p>
	 * Can be used together with the column position, so the column/row coordinates will be used as cache key
	 * together. */
	private final boolean useRowAsKey;

	/** Flag to specify if the updates on re-calculation should be performed smoothly or not. If this value is
	 * <code>true</code> the values that were calculated before will be returned until the new value calculation is
	 * done. Otherwise <code>null</code> will be returned until the calculation is finished. */
	private final boolean smoothUpdates;

	/** Creates a new SafeCalculatedValueCache for the specified layer that performs smooth updates of the calculated
	 * values.
	 * <p>
	 * Setting one or both key flags to <code>true</code> will enable automatic cache key resolution dependent on the
	 * configuration. Setting both values to <code>false</code> will leave the developer to use
	 * {@link SafeCalculatedValueCache#getCalculatedValue(int, int, ICalculatedValueCacheKey, boolean, ICalculator)} as
	 * it is not possible to determine the ICalculatedValueCacheKey automatically.
	 *
	 * @param layer          The layer to which the SafeCalculatedValueCache is connected.
	 * @param useColumnAsKey Flag to specify if the column position should be used as cache key.
	 * @param useRowAsKey    Flag to specify if the row position should be used as cache key. */
	public SafeCalculatedValueCache(final ILayer layer, final boolean useColumnAsKey, final boolean useRowAsKey) {
		this(layer, useColumnAsKey, useRowAsKey, true);
	}

	/** Creates a new SafeCalculatedValueCache for the specified layer. This constructor additionally allows to specify
	 * if the updates of the calculated values should be performed smoothly or not. That means if a value needs to be
	 * recalculated, on smooth updating the old value will be returned until the new value is calculated. Non-smooth
	 * updates will return <code>null</code> until the re-calculation is done.
	 * <p>
	 * Setting one or both key flags to <code>true</code> will enable automatic cache key resolution dependent on the
	 * configuration. Setting both values to <code>false</code> will leave the developer to use
	 * {@link SafeCalculatedValueCache#getCalculatedValue(int, int, ICalculatedValueCacheKey, boolean, ICalculator)} as
	 * it is not possible to determine the ICalculatedValueCacheKey automatically.
	 *
	 * @param layer          The layer to which the SafeCalculatedValueCache is connected.
	 * @param useColumnAsKey Flag to specify if the column position should be used as cache key.
	 * @param useRowAsKey    Flag to specify if the row position should be used as cache key.
	 * @param smoothUpdates  Flag to specify if the update of the calculated values should be performed smoothly. */
	public SafeCalculatedValueCache(final ILayer layer, final boolean useColumnAsKey, final boolean useRowAsKey,
			final boolean smoothUpdates) {
		this.layer = layer;
		this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
				Runtime.getRuntime().availableProcessors() + 1, 5000, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>());

		((ThreadPoolExecutor) this.executor).allowCoreThreadTimeOut(true);

		this.useColumnAsKey = useColumnAsKey;
		this.useRowAsKey = useRowAsKey;
		this.smoothUpdates = smoothUpdates;
	}

	@Override
	public Object getCalculatedValue(final int columnPosition, final int rowPosition,
			final boolean calculateInBackground, final ICalculator calculator) {

		ICalculatedValueCacheKey key = null;
		if (this.useColumnAsKey && this.useRowAsKey) {
			key = new CoordinateValueCacheKey(columnPosition, rowPosition);
		} else if (this.useColumnAsKey) {
			key = new PositionValueCacheKey(columnPosition);
		} else if (this.useRowAsKey) {
			key = new PositionValueCacheKey(rowPosition);
		} else {
			throw new IllegalStateException(
					"SafeCalculatedValueCacheKey is configured to not use column or row position. " //$NON-NLS-1$
							+ "Use getCalculatedValue() with ICalculatedValueCacheKey parameter instead."); //$NON-NLS-1$
		}

		return getCalculatedValue(columnPosition, rowPosition, key, calculateInBackground, calculator);
	}

	@Override
	public Object getCalculatedValue(final int columnPosition, final int rowPosition,
			final ICalculatedValueCacheKey key, final boolean calculateInBackground, final ICalculator calculator) {

		Object result = null;

		if (calculateInBackground) {
			final Object cacheValue = this.cache.get(key);
			final Object cacheCopyValue = this.cacheCopy.get(key);

			result = cacheCopyValue;

			// if the calculated value is not the same as the cache value, we
			// need to start the calculation process
			if (cacheCopyValue == null || !Objects.equals(cacheValue, cacheCopyValue)) {

				// if this SafeCalculatedValueCache is not configured for smooth
				// updates, return null instead of the previous calculated value
				if (!this.smoothUpdates) {
					result = null;
				}

				if (executing.add(key)) {
					this.executor.execute(() -> {
						final Object summaryValue = calculator.executeCalculation();
						addToCache(key, summaryValue);
						executing.remove(key);

						// only fire an update event if the new calculated value
						// is different to the value in the cache copy
						if (!Objects.equals(summaryValue, cacheCopyValue)
								&& SafeCalculatedValueCache.this.layer != null) {
							SafeCalculatedValueCache.this.layer.fireLayerEvent(new CellVisualChangeEvent(
									SafeCalculatedValueCache.this.layer, columnPosition, rowPosition));
						}
					});
				}
			}
		} else {
			// Execute the calculation in the same thread to make printing and
			// exporting work
			// Note: this could cause a performance leak and should be used
			// carefully
			result = calculator.executeCalculation();
			addToCache(key, result);
		}

		return result;
	}

	@Override
	public void clearCache() {
		this.cache.clear();
	}

	@Override
	public void killCache() {
		this.cache.clear();
		this.cacheCopy.clear();
	}

	/** Adds the given value to the cache and the cache-copy. This way the new calculated value gets propagated to both
	 * cache instances.
	 *
	 * @param key   The key to which the calculated value belongs to.
	 * @param value The value for the given coordinates to be cached. */
	protected void addToCache(final ICalculatedValueCacheKey key, final Object value) {
		if (value != null) {
			this.cache.put(key, value);
			this.cacheCopy.put(key, value);
		} else {
			this.cache.remove(key);
			this.cacheCopy.remove(key);
		}
	}

	@Override
	public void dispose() {
		this.executor.shutdownNow();
	}

	@Override
	public void setLayer(final ILayer layer) {
		this.layer = layer;
	}

	/** ICalculatedValueCacheKey that uses either the column or row position as key. */
	class PositionValueCacheKey implements ICalculatedValueCacheKey {

		private final int position;

		public PositionValueCacheKey(final int position) {
			this.position = position;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + this.position;
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final PositionValueCacheKey other = (PositionValueCacheKey) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			return this.position == other.position;
		}

		private SafeCalculatedValueCache getOuterType() {
			return SafeCalculatedValueCache.this;
		}
	}

	/** ICalculatedValueCacheKey that uses the column and row position as key. */
	class CoordinateValueCacheKey implements ICalculatedValueCacheKey {

		private final int columnPosition;
		private final int rowPosition;

		public CoordinateValueCacheKey(final int columnPosition, final int rowPosition) {
			this.columnPosition = columnPosition;
			this.rowPosition = rowPosition;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + this.columnPosition;
			result = prime * result + this.rowPosition;
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final CoordinateValueCacheKey other = (CoordinateValueCacheKey) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (this.columnPosition != other.columnPosition) {
				return false;
			}
			return this.rowPosition == other.rowPosition;
		}

		private SafeCalculatedValueCache getOuterType() {
			return SafeCalculatedValueCache.this;
		}
	}
}
