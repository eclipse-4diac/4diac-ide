/*******************************************************************************
 * Copyright (c) 2015 - 2020 fortiss GmbH, Johannes Kepler University Linz (JKU)
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber
 *     - added copy and paste
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteAlgorithmCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AlgorithmsSection extends ECCSection implements I4diacTableUtil {
	private final AlgorithmGroup algorithmGroup = new AlgorithmGroup();
	private AlgorithmList algorithmList;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createAlgorithmControls(parent);
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}

	public void createAlgorithmControls(final Composite parent) {
		SashForm view = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		view.setLayout(new FillLayout());
		algorithmList = new AlgorithmList(view, getWidgetFactory());
		setLeftComposite(algorithmList.getComposite());

		getAlgorithmList().getViewer().addSelectionChangedListener(event -> {
			Object selection = ((IStructuredSelection) getAlgorithmList().getViewer().getSelection()).getFirstElement();
			algorithmGroup.setAlgorithm((selection instanceof Algorithm) ? (Algorithm) selection : null);
		});

		setRightComposite(getWidgetFactory().createComposite(view));
		getRightComposite().setLayout(new GridLayout());
		view.setWeights(new int[] { 1, 1 });
		view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		algorithmGroup.createControls(getRightComposite(), getWidgetFactory());
	}

	@Override
	protected void setInputCode() {
		// no action needed
	}

	@Override
	protected void setInputInit() {
		algorithmGroup.initialize(getType(), commandStack);
		getAlgorithmList().initialize(getType(), commandStack);
	}

	private AlgorithmList getAlgorithmList() {
		return algorithmList;
	}

	@Override
	public void refresh() {
		getAlgorithmList().refresh();
	}

	@Override
	public TableViewer getViewer() {
		return algorithmList.getViewer();
	}

	public Object getEntry(int index) {
		return getAlgorithmList().getType().getAlgorithm().get(index);
	}

	@Override
	public void addEntry(Object entry, int index) {
		if (entry instanceof Algorithm) {
			Command cmd = new CreateAlgorithmCommand(getAlgorithmList().getType(), index,
					((Algorithm) entry).getName());
			getAlgorithmList().executeCommand(cmd);
			getViewer().refresh();
		}
	}

	@Override
	public Object removeEntry(int index) {
		Algorithm entry = (Algorithm) getEntry(index);
		Command cmd = new DeleteAlgorithmCommand(getAlgorithmList().getType(), entry);
		getAlgorithmList().executeCommand(cmd);
		getViewer().refresh();
		return entry;
	}
}
