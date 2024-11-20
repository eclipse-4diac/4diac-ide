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
import java.util.Objects;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.ImportTransfer;
import org.eclipse.fordiac.ide.ui.widget.PasteDataFromClipboardCommandHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.nebula.widgets.nattable.copy.command.PasteDataCommand;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;

public class PasteDataImportFromClipboardCommandHandler extends PasteDataFromClipboardCommandHandler {

	private final CommandStack cmdStack;
	private final BiFunction<TypeLibrary, String, TypeEntry> typeResolver;

	public PasteDataImportFromClipboardCommandHandler(final SelectionLayer selectionLayer,
			final CommandStack cmdStack) {
		this(selectionLayer, cmdStack, (typeLib, name) -> typeLib.getDataTypeLibrary().getDerivedTypeEntry(name));
	}

	public PasteDataImportFromClipboardCommandHandler(final SelectionLayer selectionLayer, final CommandStack cmdStack,
			final BiFunction<TypeLibrary, String, TypeEntry> typeResolver) {
		super(selectionLayer);
		this.cmdStack = cmdStack;
		this.typeResolver = typeResolver;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final LibraryElement rootElement = getRootElement();
		if (rootElement != null) {
			Arrays.stream(getClipboardContent()).map(imp -> getImportNamespace(rootElement, imp))
					.filter(Objects::nonNull)
					.forEach(namespace -> cmdStack.execute(new AddNewImportCommand(rootElement, namespace)));
		}
		super.doCommand(command);
		return false;
	}

	private String getImportNamespace(final LibraryElement rootElement, final String imp) {
		final TypeEntry resolvedType = ImportHelper.resolveImport(PackageNameHelper.extractPlainTypeName(imp),
				rootElement, name -> typeResolver.apply(rootElement.getTypeLibrary(), name), name -> null);

		if (resolvedType == null) {
			return imp;
		}

		if (resolvedType.getFullTypeName().equals(imp)) {
			return null;
		}

		return null; // TODO paste FQN now;
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
			return (String[]) clipboard.getContents(ImportTransfer.getInstance());
		} finally {
			clipboard.dispose();
		}
	}

	@Override
	public Class<PasteDataCommand> getCommandClass() {
		return PasteDataCommand.class;
	}
}
