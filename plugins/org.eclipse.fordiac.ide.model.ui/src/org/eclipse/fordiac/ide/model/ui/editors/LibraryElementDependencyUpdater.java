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
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;

public class LibraryElementDependencyUpdater extends LibraryElementDependencyTracker {

	private LibraryElement libraryElement;

	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getNotifier() instanceof final TypeEntry dependency) {
			if (getDependencies().contains(notification.getNotifier()) && isRelevant(notification)) {
				updateDependency(dependency);
			}
		} else {
			super.notifyChanged(notification);
		}
	}

	private static boolean isRelevant(final Notification notification) {
		return ((notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_FEATURE
				&& !(notification.getNotifier() instanceof InterfaceTypeEntry))
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_INTERFACE_FEATURE
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE);
	}

	public void updateDependency(final TypeEntry dependency) {
		switch (dependency) {
		case final AttributeTypeEntry attributeTypeEntry -> updateAttributeDependency(attributeTypeEntry);
		case final DataTypeEntry dataTypeEntry -> updateDataTypeDependency(dataTypeEntry);
		case final AdapterTypeEntry adapterTypeEntry -> updateAdapterDependency(adapterTypeEntry);
		case final InterfaceTypeEntry interfaceTypeEntry -> updateBlockDependency(interfaceTypeEntry);
		case null, default -> {
			// do nothing
		}
		}
	}

	private void updateAttributeDependency(final AttributeTypeEntry dependency) {
		final AttributeDeclaration attributeDeclaration = dependency.getType();
		if (attributeDeclaration == null) {
			return;
		}
		final AnyDerivedType attributeDataType = attributeDeclaration.getType();
		if (attributeDataType == null) {
			return;
		}

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

	private void updateDataTypeDependency(final DataTypeEntry dependency) {
		final AnyDerivedType dataType = dependency.getType();
		if (dataType == null) {
			return;
		}

		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final Attribute attribute when matches(attribute.getType(), dependency) -> attribute.setType(dataType);
			case final AttributeDeclaration attributeDeclaration when matches(attributeDeclaration.getType(),
					dependency) ->
				attributeDeclaration.setType(dataType);
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

	private void updateAdapterDependency(final AdapterTypeEntry dependency) {
		final AdapterType adapterType = dependency.getType();
		if (adapterType == null) {
			return;
		}

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

	private void updateBlockDependency(final InterfaceTypeEntry dependency) {
		final TreeIterator<EObject> contents = libraryElement.eAllContents();
		while (contents.hasNext()) {
			switch (contents.next()) {
			case final FB fb when matches(fb.getType(), dependency)
					&& fb.eContainingFeature() == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS ->
				executeCommand(new UpdateInternalFBCommand(fb, dependency));
			case final BlockFBNetworkElement element when matches(element.getType(), dependency) -> {
				if (!element.isMapped() || element.getMapping().getFrom() == element) {
					executeCommand(new UpdateFBTypeCommand(element, dependency));
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
	}

	private void uninstall() {
		if (libraryElement != null) {
			libraryElement.eAdapters().remove(this);
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
