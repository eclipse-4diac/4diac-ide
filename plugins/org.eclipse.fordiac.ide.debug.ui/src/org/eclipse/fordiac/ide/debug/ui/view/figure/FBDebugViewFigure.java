/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.figure;

import org.eclipse.draw2d.Container;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithAnchor;
import org.eclipse.swt.SWT;

public class FBDebugViewFigure extends Container {

	final Figure outerInputValues;
	final Figure inputWiths;
	final Figure innerInputValues;
	final Figure fbFigurecontainer;
	final Figure innerOutputValues;
	final Figure outputWiths;
	final Figure outerOutputValues;

	public FBDebugViewFigure(final int numEi, final int numEo) {
		super(createRootLayout());
		outerInputValues = createContainer();
		add(outerInputValues, createContainerGridData());

		inputWiths = createWithContainer(numEi);
		add(inputWiths, createContainerGridData());

		innerInputValues = createContainer();
		add(innerInputValues, createContainerGridData());

		fbFigurecontainer = createContainer();
		add(fbFigurecontainer, createContainerGridData());

		innerOutputValues = createContainer();
		add(innerOutputValues, createContainerGridData());

		outputWiths = createWithContainer(numEo);
		add(outputWiths, createContainerGridData());

		outerOutputValues = createContainer();
		add(outerOutputValues, createContainerGridData());
	}

	public IFigure getOuterInputValues() {
		return outerInputValues;
	}

	public IFigure getInputWiths() {
		return inputWiths;
	}

	public IFigure getInnerInputValues() {
		return innerInputValues;
	}

	public IFigure getFBFigureContainer() {
		return fbFigurecontainer;
	}

	public IFigure getInnerOutputValues() {
		return innerOutputValues;
	}

	public IFigure getOutputWiths() {
		return outputWiths;
	}

	public IFigure getOuterOutputValues() {
		return outerOutputValues;
	}

	private static GridLayout createRootLayout() {
		final GridLayout topLayout = new GridLayout(7, false);
		topLayout.marginHeight = 0;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	private static Figure createContainer() {
		final Figure newContainer = new Figure();
		newContainer.setLayoutManager(new XYLayout());
		return newContainer;
	}

	private static Figure createWithContainer(final int numEvents) {
		final Figure newContainer = new Figure() {
			@Override
			public Dimension getPreferredSize(final int wHint, final int hHint) {
				final Dimension prefSize = super.getPreferredSize(wHint, hHint);
				// as the connection figures do not correctly give us their size we have to
				// ensure a minum width
				prefSize.union(new Dimension(
						(int) (numEvents * WithAnchor.WITH_DISTANCE + WithAnchor.WITH_DISTANCE), 0));
				return prefSize;
			}
		};
		newContainer.setLayoutManager(new XYLayout());
		return newContainer;
	}

	private static GridData createContainerGridData() {
		return new GridData(SWT.BEGINNING, SWT.FILL, true, true);
	}
}
