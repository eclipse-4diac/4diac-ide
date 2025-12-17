/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.AdapterTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.AttributeTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.part.FileEditorInput;

public class TypeEntryAdapter extends AbstractTypeEntryAdapter {

	private INavigationLocation location;
	private boolean blockUpdates;

	public TypeEntryAdapter(final ITypeEntryEditor editor, final IPartService partService) {
		super(editor, partService);
	}

	@Override
	protected ITypeEntryEditor getEditor() {
		return (ITypeEntryEditor) super.getEditor();
	}

	@Override
	protected void checkEditorActivated() {
		super.checkEditorActivated();
		performLocationRestore();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (location != null) {
			location.dispose();
			location = null;
		}
	}

	@Override
	protected void reloadEditorType() {
		getEditor().reloadType();
	}

	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		if (blockUpdates) {
			return;
		}

		if (!(notification.getFeature() instanceof final String feature)) {
			return;
		}

		switch (feature) {
		case TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE:
			handleFileContentChange();
			break;
		case TypeEntry.TYPE_ENTRY_FILE_FEATURE:
			if (notification.getNewValue() instanceof final IFile newFile) {
				final FileEditorInput newEditorInput = new FileEditorInput(newFile);
				Display.getDefault().execute(() -> {
					if (!editorClosed() && !newEditorInput.equals(getEditor().getEditorInput())) {
						getEditor().setInput(newEditorInput);
					}
				});
			}
			break;
		case TypeEntry.TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE:
			// if there is no typeEntry inside, then the notification is used wrong, and for
			// that we want to know that early
			Assert.isTrue(notification.getNotifier() instanceof TypeEntry);
			Assert.isTrue(notification.getNewValue() instanceof TypeEntry);
			final TypeEntry typeEntry = (TypeEntry) notification.getNewValue();
			handleDependencyUpdate(typeEntry);
			break;
		default:
			break;
		}
	}

	public void setBlockUpdates(final boolean blockupdates) {
		this.blockUpdates = blockupdates;
	}

	private void handleDependencyUpdate(final TypeEntry typeEntry) {
		final LibraryElement editedElement = getEditor().getAdapter(LibraryElement.class);
		if (editedElement != null) {
			Display.getDefault().asyncExec(() -> {
				if (editorClosed()) {
					// our editor was closed no update needed
					return;
				}

				switch (typeEntry) {
				// adapter type entry needs to be before FBTypeEntry
				case final AdapterTypeEntry adpEntry -> handleAdapterTypeDependenyUpdate(editedElement, adpEntry);
				case final FBTypeEntry fbEntry -> handleBlockTypeDependencyUpdate(editedElement, typeEntry);
				case final SubAppTypeEntry subAppEntry -> handleBlockTypeDependencyUpdate(editedElement, typeEntry);
				case final AttributeTypeEntry atEntry -> handleAttributeTypeEntryUpdate(editedElement, atEntry);
				case final DataTypeEntry dtEntry -> handleDataTypeEntryUpdate(editedElement, dtEntry);
				default -> {
					// do nothing
				}
				}
			});
		}
	}

	private static void handleAdapterTypeDependenyUpdate(final LibraryElement editedElement,
			final AdapterTypeEntry adpEntry) {
		final AdapterTypeInstanceSearch search = new AdapterTypeInstanceSearch(editedElement, adpEntry);
		final List<? extends EObject> result = search.performSearch();
		result.forEach(r -> {
			if (r instanceof final AdapterDeclaration adpDecl) {
				ChangeDataTypeCommand.forDataType(adpDecl, adpEntry.getType()).execute();
			}
		});
	}

	private static void handleAttributeTypeEntryUpdate(final LibraryElement editedElement,
			final AttributeTypeEntry atEntry) {
		final AttributeTypeInstanceSearch search = new AttributeTypeInstanceSearch(editedElement, atEntry);
		final List<? extends EObject> result = search.performSearch();

		result.forEach(at -> {
			// update attribute here
			if (at instanceof final ConfigurableObject co) {
				final Attribute attribute = co.getAttribute(atEntry.getTypeName());
				ChangeAttributeDeclarationCommand.forEntry(attribute, atEntry).execute();
			}
		});
	}

	private void handleBlockTypeDependencyUpdate(final LibraryElement editedElement, final TypeEntry typeEntry) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(editedElement, typeEntry);

		checkEditorLocation();

		search.performSearch().stream().filter(BlockFBNetworkElement.class::isInstance)
				.map(BlockFBNetworkElement.class::cast).map(fbnEl -> {
					if (fbnEl instanceof final FB fb && fbnEl.eContainer() == editedElement) {
						return new UpdateInternalFBCommand(fb, typeEntry);
					}

					if (fbnEl.isMapped() && fbnEl.getMapping().getTo() == fbnEl) {
						// the resource side will be update by its opposite so we do not need to do it
						// here
						return null;
					}
					return new UpdateFBTypeCommand(fbnEl, typeEntry);
				}).filter(Objects::nonNull).filter(Command::canExecute).forEach(Command::execute);

		if (isActiveEditor()) {
			performLocationRestore();
		}
	}

	private void checkEditorLocation() {
		if (location == null && getEditor().getAdapter(FBNetwork.class) == null
				&& getEditor().getAdapter(FBNetworkElement.class) != null
				&& getEditor() instanceof final INavigationLocationProvider provider) {
			location = provider.createNavigationLocation();
		}
	}

	private static void handleDataTypeEntryUpdate(final LibraryElement editedElement, final DataTypeEntry dtEntry) {
		final DataTypeInstanceSearch search = new DataTypeInstanceSearch(editedElement, dtEntry);
		final AnyDerivedType dataType = dtEntry.getType();
		search.performSearch().stream().map(item -> {
			if (item instanceof final Attribute attr) {
				return ChangeAttributeTypeCommand.forDataType(attr, dataType);
			}

			if (item instanceof final VarDeclaration varDecl) {
				return ChangeDataTypeCommand.forDataType(varDecl, dataType);
			}
			if (item instanceof final ConfigurableFB configFB) {
				if (item instanceof final StructManipulator structMan
						&& dataType instanceof final StructuredType struct) {
					return new ChangeStructCommand(structMan, struct);
				}
				return new ConfigureFBCommand(configFB, dataType);
			}
			return null;
		}).filter(Objects::nonNull).filter(Command::canExecute).forEach(Command::execute);
	}

	private void performLocationRestore() {
		if (location != null) {
			location.restoreLocation();
			location.dispose();
			location = null;
		}
	}

}