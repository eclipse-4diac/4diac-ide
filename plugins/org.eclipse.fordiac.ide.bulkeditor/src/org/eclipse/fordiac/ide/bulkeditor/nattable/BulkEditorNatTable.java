/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.resize.command.AutoResizeColumnsCommand;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class BulkEditorNatTable {
	private final CommandExecutor commandExecutor;
	private final Composite parent;

	private BulkEditorMode currentMode;
	private NatTable natTable;

	private ChangeableListDataProvider<? extends EObject> provider;
	private SorterModel<? extends EObject> sorterModel;

	private List<Attribute> currentAttributeList = Collections.emptyList();

	public BulkEditorNatTable(final Composite parent, final CommandExecutor commandExecutor,
			final BulkEditorMode initialMode) {
		this.parent = parent;
		this.commandExecutor = commandExecutor;
		changeNatTable(initialMode, null);
	}

	public NatTable getCurrentTable() {
		return natTable;
	}

	public List<Attribute> getCurrentList() {
		return currentAttributeList;
	}

	public void changeNatTable(final BulkEditorMode mode, final AttributeDeclaration attributeDeclaration) {
		this.currentMode = mode;
		disposeCurrent();

		final BuiltNatTable<? extends EObject> built = switch (this.currentMode) {
		case VARIABLE -> VarDeclarationTableBuilder.create(parent, commandExecutor);
		case ADVANCED_ATTRIBUTE -> AttributeTableBuilder.create(parent, commandExecutor);
		case SIMPLE_ATTRIBUTE -> attributeDeclaration != null
				? DynamicAttributeTableBuilder.create(parent, commandExecutor, attributeDeclaration)
				: AttributeTableBuilder.create(parent, commandExecutor);
		};

		this.natTable = built.natTable();
		this.provider = built.provider();
		this.sorterModel = built.sorterModel();

		configureCommonBehavior();
	}

	private void configureCommonBehavior() {
		natTable.addConfiguration(new SingleClickSortConfiguration());
		natTable.configure();
		installMouseWheelForward(this.currentMode == BulkEditorMode.SIMPLE_ATTRIBUTE);
		refreshNatTableLayout(0);
	}

	private void installMouseWheelForward(final boolean shiftBypass) {
		natTable.addListener(SWT.MouseWheel, event -> {
			if (shiftBypass && (event.stateMask & SWT.SHIFT) != 0) {
				return;
			}
			final ScrolledComposite scrolledParent = ((ScrolledComposite) parent.getParent());
			final Point origin = scrolledParent.getOrigin();
			final int newY = Math.max(0, origin.y - event.count * 20);
			scrolledParent.setOrigin(origin.x, newY);
		});
	}

	private void disposeCurrent() {
		if (natTable != null) {
			natTable.dispose();
			natTable = null;
		}
	}

	public void updateList(final List<EObject> mappedList) {
		currentAttributeList = Collections.emptyList();

		if (currentMode == BulkEditorMode.VARIABLE
				&& (mappedList.isEmpty() || mappedList.getFirst() instanceof VarDeclaration)) {
			applyInput(provider, sorterModel, filterByType(mappedList, VarDeclaration.class));
		} else if (BulkEditorMode.isAttributeMode(currentMode)
				&& (mappedList.isEmpty() || mappedList.getFirst() instanceof Attribute)) {
			final List<Attribute> list = filterByType(mappedList, Attribute.class);
			currentAttributeList = list;
			applyInput(provider, sorterModel, list);

			if (currentMode == BulkEditorMode.SIMPLE_ATTRIBUTE) {
				autoResizeDynamicColumns();
			}
		}

		refreshNatTableLayout(mappedList.size());
	}

	@SuppressWarnings("unchecked")
	private static <T extends EObject> void applyInput(final ChangeableListDataProvider<? extends EObject> provider,
			final SorterModel<? extends EObject> sorterModel, final List<T> list) {
		((ChangeableListDataProvider<T>) provider).setInput(list);
		((SorterModel<T>) sorterModel).setSortingList(list);
	}

	private void autoResizeDynamicColumns() {
		final DataLayer dataLayer = NatTableWidgetFactory.getDataLayer(natTable);
		if (dataLayer.getRowCount() <= 0) {
			return;
		}
		dataLayer.doCommand(
				new AutoResizeColumnsCommand(natTable, IntStream.range(0, dataLayer.getColumnCount()).toArray()));
		for (int colPos = 0; colPos < dataLayer.getColumnCount(); colPos++) {
			final int currentWidth = dataLayer.getColumnWidthByPosition(colPos);
			dataLayer.setColumnWidthByPosition(colPos, Math.max(currentWidth, 100));
		}
	}

	private void refreshNatTableLayout(final int rowCount) {
		natTable.getDisplay().asyncExec(() -> {
			// make sure NatTable is drawn to get correct cell height
			final GridData natTableGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
			int height = (int) (24 * (double) Display.getCurrent().getDPI().x / 96);
			if (rowCount > 0) {
				height = Math.max(height,
						NatTableWidgetFactory.getDataLayer(natTable).getBoundsByPosition(0, 0).height);
			}
			natTableGridData.heightHint = Math.max(300, rowCount * height + 1);
			natTable.setLayoutData(natTableGridData);

			parent.layout(true, true);
			final var size = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			final var scrolledComposite = (ScrolledComposite) parent.getParent();
			scrolledComposite.setMinSize(size);
			natTable.refresh();
		});
	}

	private static <T> List<T> filterByType(final List<EObject> source, final Class<T> clazz) {
		final List<T> result = new ArrayList<>();
		for (final EObject obj : source) {
			if (clazz.isInstance(obj)) {
				result.add(clazz.cast(obj));
			}
		}
		return result;
	}

	public static TypeLibrary typeLibraryForSelection(final ChangeableListDataProvider<? extends EObject> provider,
			final NatTable natTable) {
		if (natTable == null) {
			return null;
		}
		final int relevantRowIndex = NatTableWidgetFactory.getSelectionLayer(natTable).getLastSelectedCellPosition()
				.getRowPosition();
		if (EcoreUtil
				.getRootContainer(provider.getRowObject(relevantRowIndex)) instanceof final LibraryElement libElement) {
			return libElement.getTypeLibrary();
		}
		return null;
	}
}
