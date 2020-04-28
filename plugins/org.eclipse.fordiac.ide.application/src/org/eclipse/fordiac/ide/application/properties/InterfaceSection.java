/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Bianca Wiesmayr - make struct settable for Multiplexers 
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.MultiplexEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public class InterfaceSection extends AbstractInterfaceSection {

	protected CCombo muxStructSelector;
	protected CLabel muxLabel;

	@Override
	protected FBNetworkElement getInputType(Object input) {
		if (input instanceof MultiplexEditPart) {
			return ((MultiplexEditPart) input).getModel();
		} else if (input instanceof AbstractFBNElementEditPart) {
			return ((AbstractFBNElementEditPart) input).getModel();
		} else if (input instanceof FBNetworkElement) {
			return (FBNetworkElement) input;
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(Composite parent) {
		super.createFBInfoGroup(parent);
		createStructInfo(nameText.getParent());
	}

	private void createStructInfo(Composite composite) {
		muxLabel = getWidgetFactory().createCLabel(composite, "Structured Type" + ":");
		muxLabel.setVisible(false);
		muxStructSelector = getWidgetFactory().createCCombo(composite);
		muxStructSelector.setVisible(false);
	}

	@Override
	public void refresh() {
		super.refresh();
		if ((getType() instanceof Multiplexer) && (((Multiplexer) getType()).getFbNetwork() != null)) {
			muxLabel.setVisible(true);
			muxStructSelector.setVisible(true);
			String structName = ((Multiplexer) getType()).getStructType().getName();
			muxStructSelector.removeAll();
			for (DataType dtp : getDatatypeLibrary().getDataTypesSorted()) {
				if (dtp instanceof StructuredType) {
					muxStructSelector.add(dtp.getName());
					if (dtp.getName().contentEquals(structName)) {
						muxStructSelector.select(muxStructSelector.getItemCount() - 1);
					}
				}
			}
			muxStructSelector.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = muxStructSelector.getSelectionIndex();
					String structName = muxStructSelector.getItem(index);
					if (!structName.contentEquals(((Multiplexer) getType()).getStructType().getName())
							&& (null != getDatatypeLibrary())) {
						Command cmd = new ChangeStructCommand((Multiplexer) getType(),
								(StructuredType) getDatatypeLibrary().getType(structName));
						commandStack.execute(cmd);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});

		} else {
			muxStructSelector.setVisible(false);
			muxLabel.setVisible(false);
		}
	}

	private DataTypeLibrary getDatatypeLibrary() {
		try {
			return ((Multiplexer) getType()).getFbNetwork().getAutomationSystem().getPalette().getTypeLibrary()
					.getDataTypeLibrary();
		} catch (NullPointerException e) {
			return null;
		}
	}

}
