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

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;

public class DefaultImportCopyPasteLayerConfiguration extends AbstractLayerConfiguration<NatTable> {

	private final NatTableColumnProvider<? extends NatTableColumn> columnProvider;
	private final Supplier<CommandStack> cmdStack;

	public DefaultImportCopyPasteLayerConfiguration(
			final NatTableColumnProvider<? extends NatTableColumn> columnProvider,
			final Supplier<CommandStack> cmdStack) {
		this.columnProvider = columnProvider;
		this.cmdStack = cmdStack;
	}

	@Override
	public void configureTypedLayer(final NatTable layer) {
		final SelectionLayer sel = NatTableWidgetFactory.getSelectionLayer(layer);
		final NatTableColumn firstColumn = columnProvider.getColumns().get(0);
		sel.registerCommandHandler(new CopyDataImportCommandHandler(sel, columnProvider, getColMap(firstColumn)));
		sel.registerCommandHandler(new PasteDataImportFromClipboardCommandHandler(sel, cmdStack,
				getTypeResolver(firstColumn), columnProvider, getColList(firstColumn)));
	}

	// TODO: adjust DirectlyDerivedTypeTableColumn, TypedElementTableColumn,
	// VariableTableColumn
	private static BiFunction<TypeLibrary, String, TypeEntry> getTypeResolver(final NatTableColumn column) {
		return switch (column) {
		case final AttributeTableColumn col -> (typeLib, name) -> {
			final AttributeTypeEntry attributeEntry = typeLib.getAttributeTypeEntry(name);
			if (attributeEntry != null) {
				return attributeEntry;
			}
			return typeLib.getDataTypeLibrary().getDerivedTypeEntry(name);
		};
		case final DirectlyDerivedTypeTableColumn col ->
			(typeLib, name) -> typeLib.getDataTypeLibrary().getDerivedTypeEntry(name);
		case final TypedElementTableColumn col ->
			(typeLib, name) -> typeLib.getDataTypeLibrary().getDerivedTypeEntry(name);
		case final VarDeclarationTableColumn col ->
			(typeLib, name) -> typeLib.getDataTypeLibrary().getDerivedTypeEntry(name);
		case final VariableTableColumn col -> (typeLib, name) -> typeLib.getDataTypeLibrary().getDerivedTypeEntry(name);
		default -> throw new IllegalArgumentException("Unexpected value: " + column);
		};
	}

	private static List<? extends NatTableColumn> getColList(final NatTableColumn column) {
		return switch (column) {
		case final AttributeTableColumn col -> List.of(AttributeTableColumn.NAME, AttributeTableColumn.TYPE);
		case final DirectlyDerivedTypeTableColumn col -> List.of(DirectlyDerivedTypeTableColumn.BASE_TYPE);
		case final TypedElementTableColumn col -> List.of(TypedElementTableColumn.TYPE);
		case final VarDeclarationTableColumn col -> List.of(VarDeclarationTableColumn.TYPE);
		case final VariableTableColumn col -> List.of(VariableTableColumn.TYPE);
		default -> throw new IllegalArgumentException("Unexpected value: " + column);
		};
	}

	private static Map<? extends NatTableColumn, Function<EObject, LibraryElement>> getColMap(
			final NatTableColumn column) {
		return switch (column) {
		case final AttributeTableColumn col ->
			Map.of(AttributeTableColumn.NAME, eObject -> ((Attribute) eObject).getAttributeDeclaration(),
					AttributeTableColumn.TYPE, eObject -> ((Attribute) eObject).getType());
		case final DirectlyDerivedTypeTableColumn col ->
			Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType());
		case final TypedElementTableColumn col ->
			Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType());
		case final VarDeclarationTableColumn col ->
			Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType());
		case final VariableTableColumn col ->
			Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType());
		default -> throw new IllegalArgumentException("Unexpected value: " + column);
		};
	}
}
