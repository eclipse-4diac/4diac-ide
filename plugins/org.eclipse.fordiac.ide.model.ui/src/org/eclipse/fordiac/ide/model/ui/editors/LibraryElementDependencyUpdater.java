/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;

public class LibraryElementDependencyUpdater extends LibraryElementDependencyTracker {

	private LibraryElement libraryElement;
	private TypeLibrary typeLibrary;
	private boolean updating;

	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getNotifier() instanceof final TypeEntry dependency) {
			if (getDependencies().contains(dependency)) {
				// react to TYPE_ENTRY_FILE_CONTENT_FEATURE for changed dependencies
				// react to TYPE_ENTRY_TYPE_LIBRARY_FEATURE for deleted dependencies
				if (TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE.equals(notification.getFeature())
						|| TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE.equals(notification.getFeature())) {
					Display.getDefault().asyncExec(() -> updateDependency(dependency, dependency.getFullTypeName()));
				}
				// react to TYPE_ENTRY_FULL_TYPE_NAME_FEATURE for renamed dependencies
				if (TypeEntry.TYPE_ENTRY_FULL_TYPE_NAME_FEATURE.equals(notification.getFeature())) {
					Display.getDefault()
							.asyncExec(() -> updateDependency(dependency, notification.getOldStringValue()));
				}
			}
		} else if (notification.getNotifier() == typeLibrary
				&& TypeLibrary.TYPE_ENTRY_NAME_REFERENCES_FEATURE.equals(notification.getFeature())) {
			// react when a dependency is no longer visible through this library
			updateTypeLibraryDependency(notification);
		} else {
			super.notifyChanged(notification);
		}
	}

	private void updateTypeLibraryDependency(final Notification notification) {
		switch (notification.getEventType()) {
		case Notification.REMOVE, Notification.REMOVE_MANY ->
			updateRemovedTypeEntryNameReference(notification.getOldValue());
		default -> {
			// ignore
		}
		}
	}

	private void updateRemovedTypeEntryNameReference(final Object object) {
		switch (object) {
		case final Collection<?> collection -> {
			for (final Object value : collection) {
				updateRemovedTypeEntryNameReference(value);
			}
		}
		case final TypeEntry dependency when getDependencies().contains(dependency) ->
			Display.getDefault().asyncExec(() -> updateDependency(dependency, dependency.getFullTypeName()));
		case null, default -> {
			// ignore
		}
		}
	}

	public void updateDependency(final TypeEntry dependency, final String fullTypeName) {
		if (libraryElement == null) {
			return;
		}
		final TypeLibrary typeLibrary = libraryElement.getTypeLibrary();
		if (typeLibrary == null) {
			return;
		}

		if (updating) {
			throw new IllegalStateException(
					"Already updating dependencies for " + libraryElement.getTypeEntry().getFile()); //$NON-NLS-1$
		}
		try {
			updating = true;
			switch (dependency) {
			case final AttributeTypeEntry attributeTypeEntry ->
				updateAttributeDependency(attributeTypeEntry, fullTypeName, typeLibrary);
			case final DataTypeEntry dataTypeEntry ->
				updateDataTypeDependency(dataTypeEntry, fullTypeName, typeLibrary);
			case final AdapterTypeEntry adapterTypeEntry ->
				updateAdapterDependency(adapterTypeEntry, fullTypeName, typeLibrary);
			case final InterfaceTypeEntry interfaceTypeEntry ->
				updateBlockDependency(interfaceTypeEntry, fullTypeName, typeLibrary);
			case null, default -> {
				// do nothing
			}
			}
		} finally {
			updating = false;
		}
	}

	private void updateAttributeDependency(final AttributeTypeEntry dependency, final String fullTypeName,
			final TypeLibrary typeLibrary) {
		AttributeTypeEntry entry = typeLibrary.getAttributeTypeEntry(fullTypeName);
		if (entry == null) {
			entry = (AttributeTypeEntry) typeLibrary.createErrorTypeEntry(fullTypeName, dependency.getTypeEClass());
		}

		final AttributeDeclaration attributeDeclaration = entry.getType();
		final AnyDerivedType attributeDataType = attributeDeclaration.getType();
		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final Attribute attribute when matches(attribute.getAttributeDeclaration(), dependency) -> {
				attribute.setAttributeDeclaration(attributeDeclaration);
				attribute.setType(attributeDataType);
			}
			default -> {
				// ignore
			}
			}
		}
	}

	private void updateDataTypeDependency(final DataTypeEntry dependency, final String fullTypeName,
			final TypeLibrary typeLibrary) {
		final DataType dataType = typeLibrary.getDataTypeLibrary().getType(fullTypeName);
		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final Attribute attribute when matches(attribute.getType(), dependency) -> attribute.setType(dataType);
			case final AttributeDeclaration attributeDeclaration when matches(attributeDeclaration.getType(),
					dependency) && dataType instanceof final AnyDerivedType derivedType ->
				attributeDeclaration.setType(derivedType);
			case final IInterfaceElement interfaceElement when matches(interfaceElement.getType(), dependency) ->
				interfaceElement.setType(dataType);
			case final StructManipulator structManipulator when matches(structManipulator.getDataType(),
					dependency) -> {
				executeCommand(new ChangeStructCommand(structManipulator, dataType));
				contents.prune(); // contents handled by command
			}
			case final ConfigurableFB configurableFB when matches(configurableFB.getDataType(), dependency) -> {
				executeCommand(new ConfigureFBCommand(configurableFB, dataType));
				contents.prune(); // contents handled by command
			}
			default -> {
				// ignore
			}
			}
		}
	}

	private void updateAdapterDependency(final AdapterTypeEntry dependency, final String fullTypeName,
			final TypeLibrary typeLibrary) {
		AdapterTypeEntry entry = typeLibrary.getAdapterTypeEntry(fullTypeName);
		if (entry == null) {
			entry = (AdapterTypeEntry) typeLibrary.createErrorTypeEntry(fullTypeName, dependency.getTypeEClass());
		}

		final AdapterType adapterType = entry.getType();
		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final AdapterDeclaration adapterDeclaration when matches(adapterDeclaration.getType(), dependency) -> {
				executeCommand(ChangeDataTypeCommand.forDataType(adapterDeclaration, adapterType));
				contents.prune(); // contents handled by command
			}
			default -> {
				// ignore
			}
			}
		}
	}

	private void updateBlockDependency(final InterfaceTypeEntry dependency, final String fullTypeName,
			final TypeLibrary typeLibrary) {
		TypeEntry entry = switch (dependency) {
		case final FBTypeEntry fb -> typeLibrary.getFBTypeEntry(fullTypeName);
		case final SubAppTypeEntry subApp -> typeLibrary.getSubAppTypeEntry(fullTypeName);
		default -> typeLibrary.find(fullTypeName);
		};
		if (entry == null) {
			entry = typeLibrary.createErrorTypeEntry(fullTypeName, dependency.getTypeEClass());
		}

		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final FB fb when matches(fb.getType(), dependency)
					&& fb.eContainingFeature() == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS ->
				executeCommand(new UpdateInternalFBCommand(fb, entry));
			case final BlockFBNetworkElement element when matches(element.getType(), dependency) -> {
				if (!element.isMapped() || element.getMapping().getFrom() == element) {
					executeCommand(new UpdateFBTypeCommand(element, entry));
				}
				contents.prune(); // contents handled by command or opposite
			}
			default -> {
				// ignore
			}
			}
		}
	}

	private static boolean matches(final LibraryElement reference, final TypeEntry typeEntry) {
		return reference != null && Objects.equals(reference.getTypeEntry(), typeEntry);
	}

	private static void executeCommand(final Command command) {
		if (command.canExecute()) {
			command.execute();
		}
	}

	public void setLibraryElement(final LibraryElement libraryElement) {
		if (libraryElement != this.libraryElement) {
			uninstall();
			this.libraryElement = libraryElement;
			install();
		}
	}

	private void install() {
		if (libraryElement != null && !libraryElement.eAdapters().contains(this)) {
			libraryElement.eAdapters().add(this);
		}
		if (libraryElement != null) {
			typeLibrary = libraryElement.getTypeLibrary();
			if (typeLibrary != null && !typeLibrary.eAdapters().contains(this)) {
				typeLibrary.eAdapters().add(this);
			}
		}
	}

	private void uninstall() {
		if (libraryElement != null) {
			libraryElement.eAdapters().remove(this);
		}
		if (typeLibrary != null) {
			typeLibrary.eAdapters().remove(this);
			typeLibrary = null;
		}
	}

	@Override
	protected boolean addDependency(final TypeEntry typeEntry) {
		if (super.addDependency(typeEntry)) {
			if (!typeEntry.eAdapters().contains(this)) {
				typeEntry.eAdapters().add(this);
			}
			return true;
		}
		return false;
	}

	@Override
	protected boolean removeDependency(final TypeEntry typeEntry) {
		if (super.removeDependency(typeEntry)) {
			typeEntry.eAdapters().remove(this);
			return true;
		}
		return false;
	}
}
