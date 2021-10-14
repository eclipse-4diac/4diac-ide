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
 *   Melanie Winter - extracted to common super class
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.gef.editparts.AbstractCombinedCellEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ServicePrimitiveCellEditor extends AbstractCombinedCellEditor<Primitive> {
	private static final int ASCII_UNDERSCORE = 95;

	protected ServicePrimitiveCellEditor(final Primitive parent) {
		super(parent);
	}

	public ServicePrimitiveCellEditor(final Composite parent) {
		super(parent);
	}

	protected ServicePrimitiveCellEditor(final Primitive element, final Composite parent) {
		this(element, parent, SWT.NONE);
	}

	protected ServicePrimitiveCellEditor(final Primitive element, final Composite parent, final int style) {
		super(element, parent, style);
	}

	@Override
	protected void checkTextEnabled() {
		if (null != getCCombo() && null != text) {
			text.setVisible(true);
		}
	}

	@Override
	protected void editText() {
		// nothing to do here
	}

	@Override
	protected void initData() {
		if (null == getPrimitive()) {
			return;
		}
		final List<String> eventNames = new ArrayList<>();
		if (this.getElement() instanceof OutputPrimitiveImpl) {
			for (final Event event : getPrimitive().getService().getFBType().getInterfaceList().getEventOutputs()) {
				eventNames.add(event.getName());
			}
		} else if (this.getElement() instanceof InputPrimitiveImpl) {

			for (final Event event : getPrimitive().getService().getFBType().getInterfaceList().getEventInputs())
			{
				eventNames.add(event.getName());
			}
		}

		final int selected = getIndexToSelect(eventNames);

		super.populateComboBoxItems(eventNames);
		getCombobox().select(selected);
		if (getPrimitive().getParameters() != null) {
			text.setText(getPrimitive().getParameters());
		}

		checkTextEnabled();
	}

	@Override
	protected int getIndexToSelect(final List<String> eventNames) {
		// handle qualifier QI
		String currentEvent = getPrimitive().getEvent();
		if (!currentEvent.isEmpty()) {
			final char lastChar = currentEvent.charAt(currentEvent.length() - 1);
			if (!(Character.isLetterOrDigit(lastChar) || (lastChar == ASCII_UNDERSCORE))) {
				currentEvent = currentEvent.substring(0, currentEvent.length() - 1);
			}
		}
		return eventNames.indexOf(currentEvent);
	}

	private Primitive getPrimitive() {
		return super.getElement();
	}

}
