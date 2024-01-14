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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.attributetypeeditor.widgets;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.gef.nat.DirectlyDerivedTypeTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDirectlyDerivedBaseTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDirectlyDerivedInitialValueCommand;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.nat.TypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class DirectlyDerivedTypeComposite extends Composite implements ISelectionProvider {

	private DirectlyDerivedType directlyDerivedType;
	private final CommandStack commandStack;
	private NatTable natTable;

	public DirectlyDerivedTypeComposite(final Composite parent, final DirectlyDerivedType directlyDerivedType,
			final CommandStack commandStack) {
		super(parent, SWT.NONE);
		this.directlyDerivedType = directlyDerivedType;
		this.commandStack = commandStack;
		init();
	}

	public void init() {
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final IDataProvider prov = new IDataProvider() {
			@Override
			public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
				switch (columnIndex) {
				case 0 -> commandStack.execute(
						ChangeDirectlyDerivedBaseTypeCommand.forTypeName(directlyDerivedType, (String) newValue));
				case 1 -> commandStack.execute(
						ChangeDirectlyDerivedInitialValueCommand.forName(directlyDerivedType, (String) newValue));
				case 2 -> commandStack.execute(new ChangeCommentCommand(directlyDerivedType, (String) newValue));
				default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex); //$NON-NLS-1$
				}
			}

			@Override
			public int getRowCount() {
				return 1;
			}

			@Override
			public Object getDataValue(final int columnIndex, final int rowIndex) {
				return switch (columnIndex) {
				case 0 -> directlyDerivedType.getBaseType().getName();
				case 1 -> directlyDerivedType.getInitialValue();
				case 2 -> directlyDerivedType.getComment();
				default -> throw new IllegalArgumentException("Unexpected value: " + columnIndex); //$NON-NLS-1$
				};
			}

			@Override
			public int getColumnCount() {
				return 3;
			}
		};
		final DataLayer inputDataLayer = new DataLayer(prov);
		inputDataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (columnPosition == 0) {
				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			}
		});

		natTable = NatTableWidgetFactory.createNatTable(this, inputDataLayer,
				new NatTableColumnProvider<>(DirectlyDerivedTypeTableColumn.DEFAULT_COLUMNS),
				IEditableRule.ALWAYS_EDITABLE, createTypeSelectionButton());
		natTable.configure();

	}

	public void setDirectlyDerivedType(final DirectlyDerivedType directlyDerivedType) {
		if (this.directlyDerivedType != directlyDerivedType) {
			this.directlyDerivedType = directlyDerivedType;
			if (natTable != null && !natTable.isDisposed()) {
				natTable.refresh();
			}
		}
	}

	private TypeSelectionButton createTypeSelectionButton() {
		return new TypeSelectionButton(((AttributeDeclaration) directlyDerivedType.eContainer())::getTypeLibrary,
				new ITypeSelectionContentProvider() {
					@Override
					public Collection<LibraryElement> getTypes(final Object input) {
						return Collections
								.unmodifiableCollection(ElementaryTypes.getAllElementaryType().stream().toList());
					}

					@Override
					public Collection<TypeEntry> getTypeEntries(final Object input) {
						return Collections.emptyList();
					}
				}, new TypeSelectionTreeContentProvider() {
					@Override
					protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
						return ElementaryTypes.getAllElementaryType().stream().map(TypeNode::new).toList();
					}
				});
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// nothing to do here
	}

	@Override
	public ISelection getSelection() {
		return new StructuredSelection(directlyDerivedType);
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// nothing to do here
	}

	@Override
	public void setSelection(final ISelection selection) {
		// nothing to do here
	}
}