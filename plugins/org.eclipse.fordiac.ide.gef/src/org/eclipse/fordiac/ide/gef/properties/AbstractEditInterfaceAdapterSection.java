/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.InterfaceElementColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.TypedElementConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.TypedElementTableColumn;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.AdapterTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.widgets.AdapterTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Group;

public abstract class AbstractEditInterfaceAdapterSection extends AbstractEditInterfaceSection<AdapterDeclaration> {

	protected AdapterType getLastUsedAdapterType(final InterfaceList interfaceList,
			final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			return (AdapterType) interfaceElement.getType();
		}
		final EList<AdapterDeclaration> adapterList = getAdapterList(interfaceList, isInput);
		if (!adapterList.isEmpty()) {
			return adapterList.get(adapterList.size() - 1).getType();
		}
		return getTypeLibrary().getAdapterTypes().values().iterator().next().getType();
	}

	@Override
	protected int getInsertingIndex(final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getAdapterList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<AdapterDeclaration> getAdapterList(final InterfaceList interfaceList, final boolean isInput) {
		return isInput ? interfaceList.getSockets() : interfaceList.getPlugs();
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final AdapterDeclaration adapterDeclaration) {
			cmd.add(newInsertCommand(adapterDeclaration, isInput, index));
		}
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final AdapterDeclaration adapterDeclaration) {
			cmd.add(newDeleteCommand(adapterDeclaration));
		}
	}

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		outputProvider = new ChangeableListDataProvider<>(new InterfaceElementColumnAccessor<>(this) {
			@Override
			public org.eclipse.gef.commands.Command onNameChange(final IInterfaceElement rowObject,
					final Object newValue) {
				if (newValue instanceof final String text) {
					return AbstractEditInterfaceAdapterSection.this.onNameChange(rowObject, text);
				}
				return null;
			}
		});
		final DataLayer outputDataLayer = new DataLayer(outputProvider);
		outputDataLayer.setConfigLabelAccumulator(new TypedElementConfigLabelAccumulator(outputProvider));
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer,
				new NatTableColumnProvider<>(TypedElementTableColumn.DEFAULT_COLUMNS), getSectionEditableRule(),
				createTypeSelectionButton(), this, false);
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(new InterfaceElementColumnAccessor<>(this) {
			@Override
			public org.eclipse.gef.commands.Command onNameChange(final IInterfaceElement rowObject,
					final Object newValue) {
				if (newValue instanceof final String text) {
					return AbstractEditInterfaceAdapterSection.this.onNameChange(rowObject, text);
				}
				return null;
			}
		});
		final DataLayer inputDataLayer = new DataLayer(inputProvider);
		inputDataLayer.setConfigLabelAccumulator(new TypedElementConfigLabelAccumulator(inputProvider));
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer,
				new NatTableColumnProvider<>(TypedElementTableColumn.DEFAULT_COLUMNS), getSectionEditableRule(),
				createTypeSelectionButton(), this, true);
	}

	@Override
	public void setTableInput(final InterfaceList il) {
		inputProvider.setInput(il.getSockets());
		outputProvider.setInput(il.getPlugs());
	}

	private TypeSelectionButton createTypeSelectionButton() {
		return new TypeSelectionButton(this::getTypeLibrary, AdapterTypeSelectionContentProvider.INSTANCE,
				AdapterTypeSelectionTreeContentProvider.INSTANCE, AdapterTreeNodeLabelProvider.INSTANCE);
	}

	public static class AdapterTreeNodeLabelProvider extends DataTypeTreeSelectionDialog.TreeNodeLabelProvider {

		public static final AdapterTreeNodeLabelProvider INSTANCE = new AdapterTreeNodeLabelProvider();

		@Override
		public Image getImage(final Object element) {
			if (element instanceof final TypeNode node && node.getType() != null) {
				final LibraryElement type = node.getType();

				if (type instanceof AdapterType) {
					return FordiacImage.ICON_ADAPTER_TYPE.getImage();
				}
			}
			return super.getImage(element);
		}
	}

}
