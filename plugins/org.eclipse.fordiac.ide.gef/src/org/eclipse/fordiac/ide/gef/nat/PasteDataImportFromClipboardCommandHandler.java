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
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.ImportTransfer;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.PasteDataFromClipboardCommandHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.nebula.widgets.nattable.copy.command.PasteDataCommand;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionModel;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;

public class PasteDataImportFromClipboardCommandHandler extends PasteDataFromClipboardCommandHandler {

	private final BiFunction<TypeLibrary, String, TypeEntry> typeResolver;
	private final Supplier<CommandStack> cmdstk;
	private final Map<String, String> conflicts = new HashMap<>();
	private final NatTableColumnProvider<? extends NatTableColumn> columnProvider;
	private final List<? extends NatTableColumn> columns;

	public PasteDataImportFromClipboardCommandHandler(final SelectionLayer selectionLayer,
			final Supplier<CommandStack> cmdStack, final BiFunction<TypeLibrary, String, TypeEntry> typeResolver,
			final NatTableColumnProvider<? extends NatTableColumn> columnProvider,
			final List<? extends NatTableColumn> columns) {
		super(selectionLayer);
		this.cmdstk = cmdStack;
		this.typeResolver = typeResolver;
		this.columnProvider = columnProvider;
		this.columns = columns;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final LibraryElement rootElement = getRootElement();
		if (rootElement != null) {
			final var namespaces = Arrays.stream(getClipboardContent()).map(imp -> getImportNamespace(rootElement, imp))
					.filter(Objects::nonNull).toList();
			if (selectionLayer.getSelectionModel() instanceof final SelectionModel selModel) {
				selModel.setClearSelectionOnChange(false);
				namespaces.forEach(namespace -> cmdstk.get().execute(new AddNewImportCommand(rootElement, namespace)));
				selModel.setClearSelectionOnChange(true);
			} else {
				namespaces.forEach(namespace -> cmdstk.get().execute(new AddNewImportCommand(rootElement, namespace)));
			}
		}
		conflicts.clear();
		super.doCommand(command);
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

	private LibraryElement getRootElement() {
		if (selectionLayer.getUnderlyingLayerByPosition(0, 0) instanceof final DataLayer dataLayer) {
			final ListDataProvider<?> provider = (ListDataProvider<?>) dataLayer.getDataProvider();
			final var frow = provider.getRowObject(0);
			if (frow instanceof final EObject eobj
					&& EcoreUtil.getRootContainer(eobj) instanceof final LibraryElement libElement) {
				return libElement;
			}
		}
		return null;
	}

	protected static String[] getClipboardContent() {
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
	public Class<PasteDataCommand> getCommandClass() {
		return PasteDataCommand.class;
	}
}
