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
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.nebula.widgets.nattable.command.DisposeResourcesCommand;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.layer.event.IStructuralChangeEvent;
import org.eclipse.nebula.widgets.nattable.layer.event.IVisualChangeEvent;
import org.eclipse.nebula.widgets.nattable.util.ICalculatedValueCache;

public abstract class AbstractCachingDataLayer extends DataLayer {

	private final ICalculatedValueCache cache;

	protected AbstractCachingDataLayer(final IDataProvider dataProvider) {
		super(dataProvider);
		cache = new SafeCalculatedValueCache(this, true, true);
	}

	protected AbstractCachingDataLayer(final IDataProvider dataProvider, final int defaultColumnWidth,
			final int defaultRowHeight) {
		super(dataProvider, defaultColumnWidth, defaultRowHeight);
		cache = new SafeCalculatedValueCache(this, true, true);
	}

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		if (cache != null && isCachedValue(columnIndex, rowIndex)) {
			final Object value = cache.getCalculatedValue(columnIndex, rowIndex, true,
					() -> super.getDataValue(columnIndex, rowIndex));
			if (value == null) {
				return getPlaceholderValue(columnIndex, rowIndex);
			}
			return value;
		}
		return super.getDataValue(columnIndex, rowIndex);
	}

	protected abstract boolean isCachedValue(final int columnIndex, final int rowIndex);

	protected abstract Object getPlaceholderValue(final int columnIndex, final int rowIndex);

	@Override
	public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
		clearCache();
		super.setDataValue(columnIndex, rowIndex, newValue);
	}

	@Override
	public boolean doCommand(final ILayerCommand command) {
		if (command instanceof DisposeResourcesCommand && cache != null) {
			cache.killCache();
			cache.dispose();
		}
		return super.doCommand(command);
	}

	@Override
	public void fireLayerEvent(final ILayerEvent event) {
		if (event instanceof IStructuralChangeEvent) {
			killCache();
		} else if (event instanceof IVisualChangeEvent) {
			clearCache();
		}
		super.fireLayerEvent(event);
	}

	@Override
	public void setDataProvider(final IDataProvider dataProvider) {
		super.setDataProvider(dataProvider);
		clearCache();
	}

	public void clearCache() {
		if (cache != null) {
			cache.clearCache();
		}
	}

	public void killCache() {
		if (cache != null) {
			cache.killCache();
		}
	}
}
