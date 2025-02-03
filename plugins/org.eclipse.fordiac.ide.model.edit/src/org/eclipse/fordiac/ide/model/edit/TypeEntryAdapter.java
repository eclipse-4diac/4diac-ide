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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
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
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.AttributeTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.part.FileEditorInput;

public class TypeEntryAdapter extends AdapterImpl {

	private final ITypeEntryEditor editor;
	private boolean reloadPending;

	public TypeEntryAdapter(final ITypeEntryEditor editor) {
		this.editor = editor;
	}

	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		String feature = ""; //$NON-NLS-1$
		if (notification.getFeature() instanceof final String string) {
			feature = string;
		}

		switch (feature) {
		case TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE:
			handleFileContentChange();
			break;
		case TypeEntry.TYPE_ENTRY_FILE_FEATURE:
			Display.getDefault().asyncExec(() -> {
				if (notification.getNewValue() instanceof final IFile newFile) {
					editor.setInput(new FileEditorInput(newFile));
				}
			});
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

	public void checkFileReload() {
		if (reloadPending) {
			performReload();
		}
	}

	private void handleFileContentChange() {
		if (editor.equals(editor.getSite().getPage().getActiveEditor())) {
			performReload();
		} else {
			reloadPending = true;
		}

	}

	private void performReload() {
		Display.getDefault().asyncExec(() -> {
			if (!editor.isDirty() || openFileChangedDialog() == 0) {
				editor.reloadType();
			}
		});
		reloadPending = false;
	}

	private int openFileChangedDialog() {
		final String message = MessageFormat.format(Messages.TypeEntryEditor_filedchanged_message,
				editor.getEditorInput().getName());
		final MessageDialog dialog = new MessageDialog(editor.getSite().getShell(),
				Messages.TypeEntryEditor_FileChangedTitle, null, message, MessageDialog.QUESTION,
				new String[] { Messages.TypeEntryEditor_replace_button_label,
						Messages.TypeEntryEditor_dontreplace_button_label },
				0);

		return dialog.open();
	}

	private void handleDependencyUpdate(final TypeEntry typeEntry) {
		final LibraryElement editedElement = editor.getAdapter(LibraryElement.class);
		if (editedElement != null) {
			Display.getDefault().asyncExec(() -> {
				if ((typeEntry instanceof FBTypeEntry || typeEntry instanceof SubAppTypeEntry)) {
					handleBlockTypeDependencyUpdate(editedElement, typeEntry);
				}
				if (typeEntry instanceof final DataTypeEntry dtEntry) {
					handleDataTypeEntryUpdate(editedElement, dtEntry);
				}
				if (typeEntry instanceof final AttributeTypeEntry atEntry) {
					handleAttributeTypeEntryUpdate(editedElement, atEntry);
				}
			});
		}
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

		final INavigationLocation location = getEditorLocation();

		search.performSearch().stream().filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast)
				.map(fbnEl -> {
					if (fbnEl instanceof final FB fb && fbnEl.eContainer() == editedElement) {
						return new UpdateInternalFBCommand(fb, typeEntry);
					}
					return new UpdateFBTypeCommand(fbnEl, typeEntry);
				}).forEach(Command::execute);

		if (location != null) {
			location.restoreLocation();
		}
	}

	private INavigationLocation getEditorLocation() {
		if (editor.getAdapter(FBNetwork.class) == null
				&& editor instanceof final INavigationLocationProvider provider) {
			return provider.createNavigationLocation();
		}
		return null;
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
		}).filter(Objects::nonNull).forEach(Command::execute);

	}

}