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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.widget.ImportTransfer;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class CopyDataImportCommandHandler extends CopyDataCommandHandler {
	private final NatTableColumnProvider<? extends NatTableColumn> columnProvider;
	private final Map<? extends NatTableColumn, Function<EObject, LibraryElement>> colMapper;

	public CopyDataImportCommandHandler(final SelectionLayer selectionLayer,
			final NatTableColumnProvider<? extends NatTableColumn> columnProvider,
			final Map<? extends NatTableColumn, Function<EObject, LibraryElement>> colMapper) {
		super(selectionLayer);
		this.columnProvider = columnProvider;
		this.colMapper = colMapper;
	}

	@Override
	protected void internalDoCommand(final CopyDataToClipboardCommand command,
			final ILayerCell[][] assembledCopiedDataStructure) {
		super.internalDoCommand(command, assembledCopiedDataStructure);
		final var imports = getImports(assembledCopiedDataStructure);

		final Clipboard clipboard = new Clipboard(Display.getDefault());
		final var textContent = clipboard.getContents(TextTransfer.getInstance());
		clipboard.setContents(new Object[] { textContent, imports },
				new Transfer[] { TextTransfer.getInstance(), ImportTransfer.getInstance() });
		clipboard.dispose();
	}

	private String[] getImports(final ILayerCell[][] assembledCopiedDataStructure) {
		if (selectionLayer.getUnderlyingLayerByPosition(0, 0) instanceof final DataLayer dataLayer) {
			final ListDataProvider<?> provider = (ListDataProvider<?>) dataLayer.getDataProvider();
			return Arrays.stream(assembledCopiedDataStructure).flatMap(Arrays::stream).filter(Objects::nonNull)
					.filter(cell -> colMapper.containsKey(columnProvider.getColumns().get(cell.getColumnIndex())))
					.map(cell -> {
						if (provider.getRowObject(cell.getRowIndex()) instanceof final EObject decl) {
							final LibraryElement element = colMapper
									.get(columnProvider.getColumns().get(cell.getColumnIndex())).apply(decl);
							if (element instanceof final DataType dt
									&& !(IecTypes.ElementaryTypes.getAllElementaryType().contains(dt)
											|| IecTypes.GenericTypes.isAnyType(dt))) {
								return PackageNameHelper.getFullTypeName(element);
							}
						}
						return null;
					}).filter(Objects::nonNull).distinct().toArray(size -> new String[size]);
		}
		return new String[0];
	}
}
