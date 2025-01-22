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
package org.eclipse.fordiac.ide.gef.nat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.providers.RowHeaderDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.ImportTransfer;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.PasteDataFromClipboardCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.PasteDataCommand;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;

public class PasteDataImportFromClipboardCommandHandler extends PasteDataFromClipboardCommandHandler {

	private final BiFunction<TypeLibrary, String, TypeEntry> typeResolver;
	private final CommandExecutor commandExecutor;
	private final Map<String, String> conflicts = new HashMap<>();
	private final NatTableColumnProvider<? extends NatTableColumn> columnProvider;
	private final List<? extends NatTableColumn> columns;

	public PasteDataImportFromClipboardCommandHandler(final SelectionLayer selectionLayer,
			final CommandExecutor commandExecutor, final BiFunction<TypeLibrary, String, TypeEntry> typeResolver,
			final NatTableColumnProvider<? extends NatTableColumn> columnProvider,
			final List<? extends NatTableColumn> columns, final RowHeaderDataProvider rowHeaderDataProvider) {
		super(selectionLayer, commandExecutor instanceof final I4diacNatTableUtil section ? section : null,
				rowHeaderDataProvider);
		this.commandExecutor = commandExecutor;
		this.typeResolver = typeResolver;
		this.columnProvider = columnProvider;
		this.columns = columns;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final LibraryElement rootElement = EditorUtils.getCurrentActiveEditor().getAdapter(LibraryElement.class);
		if (rootElement != null) {
			Arrays.stream(getClipboardImports()).map(imp -> getImportNamespace(rootElement, imp))
					.filter(Objects::nonNull).forEach(namespace -> commandExecutor
							.executeCommand(new AddNewImportCommand(rootElement, namespace)));
		}

		super.doCommand(command);
		conflicts.clear();
		return true;
	}

	@Override
	protected String[][] parseContent(final Object contents) {
		final var content = super.parseContent(contents);

		if (conflicts.isEmpty() || selectionLayer.getSelectionModel().getSelections().isEmpty()) {
			return content;
		}
		final var location = selectionLayer.getSelectionAnchor();

		for (final var column : columns) {
			final int idx = columnProvider.getColumns().indexOf(column);
			final int colIndex = idx - location.getColumnPosition();
			for (final String[] row : content) {
				if (colIndex >= 0 && colIndex < row.length && conflicts.containsKey(row[colIndex])) {
					row[colIndex] = conflicts.get(row[colIndex]);
				}
			}
		}

		return content;
	}

	private String getImportNamespace(final LibraryElement rootElement, final String imp) {
		if (ImportHelper.matchesImports(imp, ImportHelper.getImports(rootElement))) {
			return null;
		}
		final TypeEntry resolvedType = ImportHelper.resolveImport(PackageNameHelper.extractPlainTypeName(imp),
				rootElement, name -> typeResolver.apply(rootElement.getTypeLibrary(), name), name -> null);

		if (resolvedType == null) {
			return imp;
		}

		if (resolvedType.getFullTypeName().equalsIgnoreCase(imp)) {
			return null;
		}

		conflicts.put(PackageNameHelper.extractPlainTypeName(imp), imp);
		return null;
	}

	protected static String[] getClipboardImports() {
		final Clipboard clipboard = new Clipboard(Display.getDefault());
		try {
			if (clipboard.getContents(ImportTransfer.getInstance()) instanceof final String[] stringArray) {
				return stringArray;
			}
			return new String[0];
		} finally {
			clipboard.dispose();
		}
	}

	@Override
	protected void updateNewRow(final int rowIndex) {
		if (selectionLayer.getUnderlyingLayerByPosition(0, 0) instanceof final DataLayer dataLayer) {
			final IDataProvider dataProvider = dataLayer.getDataProvider();
			for (final var column : columns) {
				final int colIdx = columnProvider.getColumns().indexOf(column);
				final var cellValue = dataProvider.getDataValue(colIdx, rowIndex);
				if (conflicts.containsKey(cellValue)) {
					dataProvider.setDataValue(colIdx, rowIndex, conflicts.get(cellValue));
				}
			}
		}
	}

	@Override
	public Class<PasteDataCommand> getCommandClass() {
		return PasteDataCommand.class;
	}
}
