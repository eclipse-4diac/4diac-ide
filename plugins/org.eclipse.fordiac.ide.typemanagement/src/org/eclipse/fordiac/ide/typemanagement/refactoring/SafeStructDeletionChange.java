/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.swt.widgets.Display;

public class SafeStructDeletionChange extends CompositeChange {

	public SafeStructDeletionChange(final StructuredType struct) {
		super(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle);
		addDeleteChanges(this, struct);
		addUpdateStructManipulatorChanges(this, struct);
	}

	private static void addDeleteChanges(final CompositeChange change, final StructuredType struct) {
		// @formatter:off
		// struct members
		StructDataTypeSearch.createStructMemberSearch(struct)
			.searchStructuredTypes(struct.getTypeLibrary())
			.stream()
			.map(StructuredType.class::cast)
			.map(type -> (StructuredType) type.getTypeEntry().getTypeEditable())
			.map(StructuredType::getMemberVariables)
			.flatMap(Collection::stream)
			.filter(decl -> decl.getTypeName().equals(struct.getName()))
			.map(DeleteMemberVariableChange::new)
			.forEach(change::add);

		// fb types
		StructDataTypeSearch.createStructInterfaceSearch(struct)
			.performTypeLibBlockSearch(struct.getTypeLibrary())
			.stream()
			.map(FBType.class::cast)
			.map(fbType -> new DeleteFBTypeInterfaceChange(fbType, struct))
			.forEach(change::add);

		// untyped subapps
		StructDataTypeSearch.createStructSubappPinSearch(struct).performCompleteSearch().stream()
			.map(SubApp.class::cast)
			.map(sub -> new DeleteUntypedSubappPinsChange(sub, struct))
			.forEach(change::add);
		// @formatter:on
	}

	public static void addUpdateChanges(final CompositeChange change, final StructuredType struct) {
		addUpdateStructManipulatorChanges(change, struct);
		addUpdateFBTypeChanges(change, struct);
		addUpdateUntypedSubappPinChanges(change, struct);
	}

	private static void addUpdateStructManipulatorChanges(final CompositeChange change, final StructuredType struct) {
		// @formatter:off
		final StructDataTypeSearch search = StructDataTypeSearch.createStructManipulatorSearch(struct);
		SystemManager.INSTANCE.getProjectSystems(struct.getTypeEntry().getFile().getProject()).stream()
			.map(search::performApplicationSearch)
			.flatMap(Collection::stream)
			.map(StructManipulator.class::cast)
			.map(UpdateManipulatorChange::new)
			.forEach(change::add);
		// @formatter:off
	}

	private static void addUpdateFBTypeChanges(final CompositeChange change, final StructuredType struct) {
		// @formatter:off
		StructDataTypeSearch.createStructInterfaceSearch(struct)
			.performTypeLibBlockSearch(struct.getTypeLibrary())
			.stream()
			.map(FBType.class::cast)
			.map(UpdateFBTypeChange::new)
			.forEach(change::add);
		// @formatter:on
	}

	private static void addUpdateUntypedSubappPinChanges(final CompositeChange change, final StructuredType struct) {
		// @formatter:off
		StructDataTypeSearch.createStructSubappPinSearch(struct).performCompleteSearch().stream()
			.map(SubApp.class::cast)
			.map(sub -> new UpdateUntypedSubappChange(sub, struct))
			.forEach(change::add);
		// @formatter:on
	}

	private static class DeleteMemberVariableChange extends CompositeChange {

		final VarDeclaration decl;
		final StructuredType struct;

		public DeleteMemberVariableChange(final VarDeclaration decl) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteMemberVariable,
					decl.getQualifiedName()));
			this.decl = decl;
			this.struct = (StructuredType) decl.eContainer();
			SafeStructDeletionChange.addUpdateChanges(this, struct);
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final Command cmd = new DeleteMemberVariableCommand(struct, decl);
			cmd.execute();
			struct.getTypeEntry().save();
			return super.perform(pm);
		}

	}

	private static class UpdateManipulatorChange extends CompositeChange {

		final StructManipulator manipulator;

		public UpdateManipulatorChange(final StructManipulator manipulator) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateManipulator,
					manipulator.getQualifiedName()));
			this.manipulator = manipulator;
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final Command cmd = new UpdateFBTypeCommand(manipulator);
			Display.getDefault().syncExec(cmd::execute);
			return super.perform(pm);
		}

	}

	private static class DeleteFBTypeInterfaceChange extends CompositeChange {

		final FBType type;
		final StructuredType struct;

		public DeleteFBTypeInterfaceChange(final FBType type, final StructuredType struct) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteFBTypeInterface, type.getName()));
			this.type = type;
			this.struct = struct;
			SafeFBTypeDeletionChange.addUpdateChanges(this, type, false);
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			// @formatter:off
			final VarDeclaration varDeclaration = type.getInterfaceList().getAllInterfaceElements().stream()
					.filter(VarDeclaration.class::isInstance)
					.map(VarDeclaration.class::cast)
					.filter(decl -> decl.getType().getName().equals(struct.getName()))
					.findFirst()
					.orElse(null);
			// @formatter:on
			final ErrorMarkerDataType markerType = LibraryElementFactory.eINSTANCE.createErrorMarkerDataType();
			markerType.setName(varDeclaration.getTypeName());

			final Command cmd = ChangeDataTypeCommand.forDataType(varDeclaration, markerType);
			Display.getDefault().syncExec(cmd::execute);
			return super.perform(pm);
		}

	}

	private static class UpdateFBTypeChange extends CompositeChange {

		public UpdateFBTypeChange(final FBType type) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateFBType, type.getName()));
			SafeFBTypeDeletionChange.addUpdateChanges(this, type, false);
		}

	}

	private static class DeleteUntypedSubappPinsChange extends CompositeChange {

		final SubApp subapp;
		final StructuredType struct;

		public DeleteUntypedSubappPinsChange(final SubApp subapp, final StructuredType struct) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteSubappPins,
					subapp.getQualifiedName()));
			this.subapp = subapp;
			this.struct = struct;
			addUpdateChanges();
		}

		// triggers the update chain if the subapp is contained inside a typed network
		private void addUpdateChanges() {
			// CFB cannot contain a subapp -> only a typed subapp can
			if (EcoreUtil.getRootContainer(subapp) instanceof final SubAppType type) {
				SafeFBTypeDeletionChange.addUpdateChanges(this, type, false);
			}
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final CompoundCommand cmd = new CompoundCommand();
			// @formatter:off
			subapp.getInterface().getAllInterfaceElements().stream()
				.filter(ie -> ie.getTypeName().equalsIgnoreCase(struct.getQualifiedName()))
				.map(DeleteSubAppInterfaceElementCommand::new)
				.forEach(cmd::add);
			// @formatter:on
			Display.getDefault().syncExec(cmd::execute);
			return super.perform(pm);
		}

	}

	private static class UpdateUntypedSubappChange extends CompositeChange {

		final SubApp subapp;
		final StructuredType struct;

		public UpdateUntypedSubappChange(final SubApp subapp, final StructuredType struct) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateSubappPins,
					subapp.getQualifiedName()));
			this.subapp = subapp;
			this.struct = struct;
			addUpdateChanges();
		}

		// triggers the update chain if the subapp is contained inside a typed network
		private void addUpdateChanges() {
			// CFB cannot contain a subapp -> only a typed subapp can
			if (EcoreUtil.getRootContainer(subapp) instanceof final SubAppType type) {
				SafeFBTypeDeletionChange.addUpdateChanges(this, type, false);
			}
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final CompoundCommand cmd = new CompoundCommand();
			// @formatter:off
			subapp.getInterface().getAllInterfaceElements().stream()
				.filter(ie -> ie.getTypeName().equalsIgnoreCase(struct.getName()))
				.map(ie -> new ChangeDataTypeCommand(ie, struct))
				.forEach(cmd::add);
			// @formatter:on
			Display.getDefault().syncExec(cmd::execute);
			return super.perform(pm);
		}

	}

}
