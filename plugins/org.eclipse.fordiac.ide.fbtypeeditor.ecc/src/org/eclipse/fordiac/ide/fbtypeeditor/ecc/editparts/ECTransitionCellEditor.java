/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner
 *     - initial API and implementation and/or initial documentation
 *   Melanie Winter - moved part of the code to AbstractCombinedCellEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.gef.editparts.AbstractCombinedCellEditor;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ECTransitionCellEditor extends AbstractCombinedCellEditor<ECTransition> {

	protected ECTransitionCellEditor(final ECTransition element) {
		super(element);
	}

	public ECTransitionCellEditor(final Composite parent) {
		super(parent);
	}

	protected ECTransitionCellEditor(final ECTransition element, final Composite parent) {
		this(element, parent, SWT.NONE);
	}

	protected ECTransitionCellEditor(final ECTransition element, final Composite parent, final int style) {
		super(element, parent, style);
	}

	@Override
	public void checkTextEnabled() {
		if (null != getCCombo() && null != text) {
			text.setVisible(!getCCombo().getItem(getCCombo().getSelectionIndex())
					.equals(ECCContentAndLabelProvider.ONE_CONDITION));
		}
	}

	@Override
	protected void editText() {
		if (getCCombo().getItem(getCCombo().getSelectionIndex()).equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			text.setText(""); //$NON-NLS-1$
		}
	}

	@Override
	protected int getIndexToSelect(final List<String> eventNames) {
		if (getTransition().getConditionEvent() != null) {
			return eventNames.indexOf(getTransition().getConditionEvent().getName());
		}
		if (getTransition().getConditionExpression().equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			return eventNames.indexOf(ECCContentAndLabelProvider.ONE_CONDITION);
		}
		return eventNames.size() - 1;
	}

	private ECTransition getTransition() {
		return super.getElement();
	}
	@Override
	protected void initData() {
		if (null == getTransition()) {
			return;
		}
		final List<String> eventNames = ECCContentAndLabelProvider
				.getTransitionConditionEventNames(getTransition().getECC().getBasicFBType());

		final int selected = getIndexToSelect(eventNames);

		super.populateComboBoxItems(eventNames);
		getCombobox().select(selected);
		if (!eventNames.get(selected).equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			text.setText(getTransition().getConditionExpression());
		}
		checkTextEnabled();
	}
}
