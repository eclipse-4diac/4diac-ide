/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *     - convert to styled label provider to show annotations
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.provider;

import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.TextualAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;

public class PackageLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private final Supplier<GraphicalAnnotationModel> annotationModelSupplier;

	public PackageLabelProvider(final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		this.annotationModelSupplier = annotationModelSupplier;
	}

	@Override
	public StyledString getStyledText(final Object element) {
		final GraphicalAnnotationModel annotationModel = annotationModelSupplier.get();
		if (element instanceof final Import imp) {
			if (annotationModel != null) {
				return new StyledString(imp.getImportedNamespace(),
						TextualAnnotationStyles.getAnnotationStyle(annotationModel.getAnnotations(imp)));
			}
			return new StyledString(imp.getImportedNamespace());
		}
		return null;
	}
}
