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
package org.eclipse.fordiac.ide.gef.nat;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

public abstract class AbstractAnnotatedConfigLabelAccumulator<T> implements IConfigLabelAccumulator {
	private final IRowDataProvider<T> dataProvider;
	private final Supplier<GraphicalAnnotationModel> annotationModelSupplier;

	protected AbstractAnnotatedConfigLabelAccumulator(final IRowDataProvider<T> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		this.dataProvider = dataProvider;
		this.annotationModelSupplier = annotationModelSupplier;
	}

	protected void accumulateAttributeConfigLabels(final LabelStack configLabels, final Object target) {
		accumulateAttributeConfigLabels(configLabels, target, annotation -> true);
	}

	protected void accumulateAttributeConfigLabels(final LabelStack configLabels, final Object target,
			final Predicate<GraphicalAnnotation> filter) {
		final GraphicalAnnotationModel annotationModel = getAnnotationModel();
		if (annotationModel == null) {
			return;
		}

		if (annotationModel.hasAnnotation(target, GraphicalAnnotation.TYPE_ERROR, filter)) {
			configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
		} else if (annotationModel.hasAnnotation(target, GraphicalAnnotation.TYPE_WARNING, filter)) {
			configLabels.addLabelOnTop(NatTableWidgetFactory.WARNING_CELL);
		}
	}

	public IRowDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public GraphicalAnnotationModel getAnnotationModel() {
		return annotationModelSupplier.get();
	}

	public Supplier<GraphicalAnnotationModel> getAnnotationModelSupplier() {
		return annotationModelSupplier;
	}
}
