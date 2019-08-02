/*******************************************************************************
 * Copyright (c) 2015 - 2019 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *	Bianca Wiesmayr
 *     - consistent dropdown menu edit     
 ********************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEvent;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Section that appears in the Properties view, when an Action is selected in
 * the ECC
 *
 */
public class ActionSection extends AbstractECSection {
	private Composite actionComposite;
	private Combo algorithmCombo;
	private Combo outputEventCombo;
	private final AlgorithmGroup algorithmGroup = new AlgorithmGroup();
	private AlgorithmList algorithmList;

	@Override
	protected ECAction getType() {
		return (ECAction) type;
	}

	protected Algorithm getAlgorithm() {
		return getType().getAlgorithm();
	}

	private BasicFBType getFBType() {
		return ECCContentAndLabelProvider.getFBType(getType());
	}

	@Override
	protected Object getInputType(Object input) {
		if (input instanceof ECActionAlgorithmEditPart) {
			return ((ECActionAlgorithmEditPart) input).getAction();
		}
		if (input instanceof ECActionAlgorithm) {
			return ((ECActionAlgorithm) input).getAction();
		}
		if (input instanceof ECActionOutputEventEditPart) {
			return ((ECActionOutputEventEditPart) input).getAction();
		}
		if (input instanceof ECActionOutputEvent) {
			return ((ECActionOutputEvent) input).getAction();
		}
		if (input instanceof ECAction) {
			return input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createActionCombos(parent);
		algorithmGroup.createControls(parent, getWidgetFactory());
		createAlgorithmView(parent);
	}

	private void createActionCombos(Composite parent) {
		actionComposite = getWidgetFactory().createComposite(parent);
		GridData actionCompositeLayoutData = new GridData(GridData.FILL, 0, true, false);
		actionCompositeLayoutData.horizontalSpan = 3;
		actionComposite.setLayoutData(actionCompositeLayoutData);
		RowLayout layout = new RowLayout();
		layout.fill = true;
		actionComposite.setLayout(layout);

		getWidgetFactory().createCLabel(actionComposite, "Algorithm: ");
		algorithmCombo = new Combo(actionComposite, SWT.SINGLE | SWT.READ_ONLY);
		algorithmCombo.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			executeCommand(
					new ChangeAlgorithmCommand(getType(), getFBType().getAlgorithmNamed(algorithmCombo.getText())));
			algorithmGroup.setAlgorithm(getAlgorithm());
			algorithmList.refresh();
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(actionComposite, "Output Event: ");
		outputEventCombo = new Combo(actionComposite, SWT.SINGLE | SWT.READ_ONLY);
		outputEventCombo.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			List<Event> outputEvents = ECCContentAndLabelProvider.getOutputEvents(getFBType());
			int selItem = outputEventCombo.getSelectionIndex();
			executeCommand(new ChangeOutputCommand(getType(),
					(selItem < outputEvents.size()) ? outputEvents.get(selItem) : null));
			addContentAdapter();
		});

	}

	private void createAlgorithmView(Composite parent) {
		Group algorithmComposite = getWidgetFactory().createGroup(parent, "All Algorithms");
		algorithmComposite.setLayout(new GridLayout(1, false));
		algorithmComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		algorithmList = new AlgorithmList(algorithmComposite, getWidgetFactory());
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		commandStack = getCommandStack(part, input);
		if (null == commandStack) { // disable all fields
			outputEventCombo.removeAll();
			outputEventCombo.setEnabled(false);
			algorithmCombo.removeAll();
			algorithmCombo.setEnabled(false);
		}
		setType(input);
		algorithmGroup.initialize(getFBType(), commandStack);
		algorithmList.initialize(getFBType(), commandStack);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			setDropdown(outputEventCombo, getType().getOutput(),
					ECCContentAndLabelProvider.getOutputEventNames(getFBType()));
			setDropdown(algorithmCombo, getAlgorithm(), ECCContentAndLabelProvider.getAlgorithmNames(getFBType()));
			actionComposite.layout();

			algorithmGroup.setAlgorithm(getAlgorithm());
			algorithmList.refresh();
		}
		commandStack = commandStackBuffer;
	}

	private static void setDropdown(Combo comboBox, INamedElement el, List<String> names) {
		comboBox.removeAll();
		names.forEach(comboBox::add);
		// pre-selects the elements that are now in the action:
		comboBox.select((null == el) ? names.size() - 1 : comboBox.indexOf(el.getName()));
	}

	@Override
	protected void setInputCode() {
		// nothing to be done here
	}

	@Override
	protected void setInputInit() {
		// nothing to be done here
	}
}
