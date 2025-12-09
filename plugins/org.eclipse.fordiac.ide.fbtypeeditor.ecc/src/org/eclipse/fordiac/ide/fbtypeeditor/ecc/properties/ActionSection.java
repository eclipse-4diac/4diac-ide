/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz (JKU)
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
 *	 Bianca Wiesmayr - consistent dropdown menu edit#
 *   Alois Zoitl - fixed issues during deletion of actions
 *               - updated for new adapter FB handling
 ********************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEvent;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Section that appears in the Properties view, when an Action is selected in
 * the ECC
 *
 */
public class ActionSection extends AbstractSection {
	private Composite actionComposite;
	private CCombo algorithmCombo;
	private CCombo outputEventCombo;
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
	protected Object getInputType(final Object input) {
		if (input instanceof final ECActionAlgorithmEditPart aaEP) {
			return aaEP.getAction();
		}
		if (input instanceof final ECActionAlgorithm aa) {
			return aa.getAction();
		}
		if (input instanceof final ECActionOutputEventEditPart oeEP) {
			return oeEP.getAction();
		}
		if (input instanceof final ECActionOutputEvent oe) {
			return oe.getAction();
		}
		if (input instanceof ECAction) {
			return input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, true));
		createActionCombos(parent);
		algorithmGroup.createControls(parent, getWidgetFactory());
		createAlgorithmView(parent);
	}

	private void createActionCombos(final Composite parent) {
		actionComposite = getWidgetFactory().createComposite(parent);
		final GridData actionCompositeLayoutData = new GridData(GridData.FILL, 0, true, false);
		actionCompositeLayoutData.horizontalSpan = 3;
		actionComposite.setLayoutData(actionCompositeLayoutData);
		final RowLayout layout = new RowLayout();
		layout.fill = true;
		actionComposite.setLayout(layout);

		getWidgetFactory().createCLabel(actionComposite, Messages.ActionSection_Algorithm);
		algorithmCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), actionComposite);
		algorithmCombo.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			executeCommand(
					new ChangeAlgorithmCommand(getType(), getFBType().getAlgorithmNamed(algorithmCombo.getText())));
			algorithmGroup.setAlgorithm(getAlgorithm());
			algorithmList.refresh();
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(actionComposite, Messages.ActionSection_OutputEvent);
		outputEventCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), actionComposite);
		outputEventCombo.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			final List<Event> outputEvents = ECCContentAndLabelProvider.getOutputEvents(getFBType());
			final int selItem = outputEventCombo.getSelectionIndex();
			executeCommand(new ChangeOutputCommand(getType(),
					(selItem < outputEvents.size()) ? outputEvents.get(selItem) : null));
			addContentAdapter();
		});

	}

	private void createAlgorithmView(final Composite parent) {
		final Group algorithmComposite = getWidgetFactory().createGroup(parent, Messages.ActionSection_AllAlgorithms);
		algorithmComposite.setLayout(new GridLayout(1, false));
		algorithmComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		algorithmList = new AlgorithmList(algorithmComposite, getWidgetFactory());
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		final Object input = ((IStructuredSelection) selection).getFirstElement();
		setCurrentCommandStack(part, input);
		if (null == getCurrentCommandStack()) { // disable all fields
			outputEventCombo.removeAll();
			outputEventCombo.setEnabled(false);
			algorithmCombo.removeAll();
			algorithmCombo.setEnabled(false);
		}
		setType(input);
		if (null != getFBType()) {
			// during delete phases it can be that the input (i.e., Action) is not attached
			// to its type anymore
			algorithmList.initialize(getFBType());
		}
	}

	@Override
	protected void performRefresh() {
		if (getFBType() != null) {
			// during delete phases it can be that the input (i.e., Action) is not attached
			// to its type anymore. Therefore also the check if getFBType() is not null
			updateDropdown(outputEventCombo, ECCContentAndLabelProvider.getOutputEventNames(getFBType()));
			selectOutputEvent(getType().getOutput());

			updateDropdown(algorithmCombo, ECCContentAndLabelProvider.getAlgorithmNames(getFBType()));
			selectAlgorithm(getAlgorithm());

			actionComposite.layout();

			algorithmGroup.setAlgorithm(getAlgorithm());
			algorithmList.refresh();
		}
	}

	private static void updateDropdown(final CCombo comboBox, final List<String> names) {
		comboBox.removeAll();
		names.forEach(comboBox::add);
	}

	private void selectOutputEvent(final Event eo) {
		outputEventCombo.select((null == eo) ? outputEventCombo.getItemCount() - 1
				: outputEventCombo.indexOf(ECCContentAndLabelProvider.getEventName(eo)));
	}

	private void selectAlgorithm(final Algorithm alg) {
		algorithmCombo
				.select((null == alg) ? algorithmCombo.getItemCount() - 1 : algorithmCombo.indexOf(alg.getName()));
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
