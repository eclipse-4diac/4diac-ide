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
 *   Daniel Lindhuber - added copy and paste
 *   Bianca Wiesmayr - flattened hierarchy
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteAlgorithmCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.insert.InsertAlgorithmCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AlgorithmsSection extends AbstractSection implements I4diacTableUtil {
	private final AlgorithmGroup algorithmGroup = new AlgorithmGroup();
	private AlgorithmList algorithmList;

	@Override
	protected BasicFBType getType() {
		return (BasicFBType) type;
	}

	@Override
	protected Object getInputType(Object input) {
		return ECCSection.getECCInputType(input);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
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
	public void addEntry(Object entry, int index, CompoundCommand cmd) {
		if (entry instanceof Algorithm) {
			cmd.add(new InsertAlgorithmCommand(getAlgorithmList().getType(), (Algorithm) entry, index));
		}
	}

	@Override
	public Object removeEntry(int index, CompoundCommand cmd) {
		Algorithm entry = (Algorithm) getEntry(index);
		cmd.add(new DeleteAlgorithmCommand(getAlgorithmList().getType(), entry));
		return entry;
	}

	@Override
	public void executeCompoundCommand(CompoundCommand cmd) {
		getAlgorithmList().executeCommand(cmd);
		getViewer().refresh();
	}


}
