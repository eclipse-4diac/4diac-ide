/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler Universiy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.Optional;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.ActionEditingComposite;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EventActionsSection extends AbstractSection {

	private Table eventInputs;
	private ActionEditingComposite<SimpleECAction> actionGroup;

	@Override
	protected SimpleFBType getType() {
		return (SimpleFBType) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));

		final Group group = getWidgetFactory().createGroup(parent, Messages.EventActionsSection_EventInputs);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final int style = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION;
		eventInputs = new Table(group, style);
		eventInputs.setLinesVisible(true);
		eventInputs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		eventInputs.addListener(SWT.Selection, event -> selectEventInput(eventInputs.getSelectionIndex()));

		actionGroup = new ActionEditingComposite<>(parent, getWidgetFactory(), this);
	}

	@Override
	protected void performRefresh() {
		actionGroup.refresh();
	}

	@Override
	protected void setInputInit() {
		eventInputs.removeAll();

		for (final Event event : getType().getInterfaceList().getEventInputs()) {
			final TableItem item = new TableItem(eventInputs, SWT.NONE);
			item.setText(event.getName());
		}
		selectEventInput(eventInputs.getSelectionIndex());
	}

	@Override
	protected Object getInputType(final Object input) {
		return SimpleFBTypeFilter.getFBTypeFromSelected(input);
	}

	private void selectEventInput(final int index) {
		if (index < 0 || index >= eventInputs.getItemCount()) {
			actionGroup.getGroupContainer().setVisible(false);
			return;
		}
		final Optional<SimpleECState> state = getType().getSimpleECStates().stream()
				.filter(s -> s.getName().equals(eventInputs.getItem(index).getText())).findFirst();

		actionGroup.getGroupContainer().setVisible(state.isPresent());
		if (state.isPresent()) {
			actionGroup.setTypeAndCommandStack(state.get(), getCurrentCommandStack());
			actionGroup.refresh();
		}
	}
}
