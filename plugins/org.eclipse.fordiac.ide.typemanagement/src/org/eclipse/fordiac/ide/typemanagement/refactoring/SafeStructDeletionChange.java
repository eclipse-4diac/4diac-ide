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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class SafeStructDeletionChange extends CompositeChange {

	private static List<String> editorNames = null;

	public SafeStructDeletionChange(final StructuredType struct) {
		super(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle);
		editorNames = loadOpenEditorNames();
		addDeleteChanges(this, struct);
		addUpdateStructManipulatorChanges(this, struct);
	}

	private static List<String> loadOpenEditorNames() {
		// @formatter:off
		final var wrapper = new Object() { IEditorReference[] refs; };
		Display.getDefault().syncExec(() -> {
			wrapper.refs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getEditorReferences();
		});

		return Arrays.stream(wrapper.refs)
			.map(IEditorReference::getName)
			.map(SafeStructDeletionChange::removeFileEnding)
			.collect(Collectors.toList());
		// @formatter:on
	}

	private static String removeFileEnding(final String name) {
		final int lastIndexOf = name.lastIndexOf('.');
		if (lastIndexOf <= 0) {
			return name;
		}
		return name.substring(0, lastIndexOf);
	}

	public static boolean hasOpenEditor(final EObject modelObj) {
		final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(modelObj));
		if (rootContainer instanceof final LibraryElement elem) {
			editorNames.contains(elem.getName());
		}
		return true;
	}

	private static void addDeleteChanges(final CompositeChange change, final StructuredType struct) {
		// @formatter:off
		// struct members
		StructDataTypeSearch.createStructMemberSearch(struct)
			.searchStructuredTypes(struct.getTypeLibrary())
			.stream()
			.map(StructuredType.class::cast)
			.map(StructuredType::getTypeEntry)
			.map(entry -> new DeleteMemberVariableChange(entry, struct.getName()))
			.forEach(change::add);

		// fb types and its instances
		StructDataTypeSearch.createStructInterfaceSearch(struct)
			.performTypeLibBlockSearch(struct.getTypeLibrary())
			.stream()
			.map(FBType.class::cast)
			.map(fbType -> new DeleteFBTypeInterfaceChange(fbType, struct))
			.forEach(updateFBTypeChange -> addFbTypeInstances(updateFBTypeChange, change, struct));


		// untyped subapps
		StructDataTypeSearch.createStructSubappPinSearch(struct).performCompleteSearch().stream()
			.map(SubApp.class::cast)
			.map(sub -> new DeleteUntypedSubappPinsChange(sub, struct))
			.forEach(change::add);

		// @formatter:on
	}

	public static void addUpdateChanges(final CompositeChange parentChange, final TypeEntry entry) {
		final StructuredType type = (StructuredType) entry.getType();
		addUpdateStructManipulatorChanges(parentChange, type);
		addUpdateFBTypeChanges(parentChange, type);
		addUpdateUntypedSubappPinChanges(parentChange, type);
	}

	private static void addUpdateStructManipulatorChanges(final CompositeChange parentChange,
			final StructuredType struct) {
		// @formatter:off
		final StructDataTypeSearch search = StructDataTypeSearch.createStructManipulatorSearch(struct);
		SystemManager.INSTANCE.getProjectSystems(struct.getTypeEntry().getFile().getProject()).stream()
			.map(search::performApplicationSearch)
			.flatMap(Collection::stream)
			.map(StructManipulator.class::cast)
			.map(UpdateManipulatorChange::new)
			.forEach(parentChange::add);
		// @formatter:off
	}

	private static void addUpdateFBTypeChanges(final CompositeChange parentChange, final StructuredType struct) {
		// @formatter:off
		StructDataTypeSearch.createStructInterfaceSearch(struct)
			.performTypeLibBlockSearch(struct.getTypeLibrary())
			.stream()
			.map(FBType.class::cast)
			.map(UpdateFBTypeChange::new)
			.forEach(parentChange::add);
		// @formatter:on
	}

	private static void addFbTypeInstances(final DeleteFBTypeInterfaceChange updateFBTypeChange,
			final CompositeChange parentChange, final StructuredType struct) {

		final FBType fbType = updateFBTypeChange.getFBType();
		final var fbInstances = new FBInstanceSearch(fbType)
				.performProjectSearch(fbType.getTypeEntry().getFile().getProject());

		fbInstances.forEach(fbn -> {
			updateFBTypeChange.add(new UpdateInstancesChange((FBNetworkElement) fbn));
		});

		parentChange.add(updateFBTypeChange);

	}

	private static void addUpdateUntypedSubappPinChanges(final CompositeChange parentChange,
			final StructuredType struct) {
		// @formatter:off
		StructDataTypeSearch.createStructSubappPinSearch(struct).performCompleteSearch().stream()
			.map(SubApp.class::cast)
			.map(sub -> new UpdateUntypedSubappChange(sub, struct))
			.forEach(parentChange::add);
		// @formatter:on
	}

	private static class DeleteMemberVariableChange extends CompositeChange {

		final TypeEntry entry;
		final String deleteName;

		public DeleteMemberVariableChange(final TypeEntry entry, final String deleteName) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteMemberVariable,
					entry.getTypeName()));
			this.entry = entry;
			this.deleteName = deleteName;
			if (!hasOpenEditor(entry.getType())) {
				SafeStructDeletionChange.addUpdateChanges(this, entry);
			}
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final CompoundCommand cmd = new CompoundCommand();
			final StructuredType type = (StructuredType) entry.getTypeEditable();
			// @formatter:off
			type.getMemberVariables()
				.stream()
				.filter(decl -> decl.getTypeName().equals(deleteName))
				.map(decl -> new DeleteMemberVariableCommand(type, decl))
				.forEach(cmd::add);
			// @formatter:on
			SafeStructDeletionChange.executeChange(cmd, type, pm);
			return super.perform(pm);
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
			// could be problematic because as far as i know there is no "update FBs" dialog
			// if you save a subapp that is contained inside a cfb/typed subapp
			if (!hasOpenEditor(subapp)) {
				addUpdateChanges();
			}
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
			SafeStructDeletionChange.executeChange(cmd, subapp, pm);
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
			final DataTypeLibrary lib = manipulator.getType().getTypeLibrary().getDataTypeLibrary();
			StructuredType updated = (StructuredType) lib
					.getTypeIfExists(manipulator.getStructType().getQualifiedName());
			if (updated == null) {
				updated = GenericTypes.ANY_STRUCT;
			}

			final Command cmd = new ChangeStructCommand(manipulator, updated);

			SafeStructDeletionChange.executeChange(cmd, manipulator, pm);
			return super.perform(pm);
		}

	}

	private static class UpdateFBTypeChange extends CompositeChange {

		public UpdateFBTypeChange(final FBType type) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateFBType, type.getName()));
			if (!hasOpenEditor(type)) {
				SafeFBTypeDeletionChange.addUpdateChanges(this, type, false);
			}
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
			if (!hasOpenEditor(subapp)) {
				addUpdateChanges();
			}
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
				.map(ie -> ChangeDataTypeCommand.forDataType(ie, struct))
				.forEach(cmd::add);
			// @formatter:on
			SafeStructDeletionChange.executeChange(cmd, subapp, pm);
			return super.perform(pm);
		}

	}

	public static void executeChange(final Command cmd, final EObject modelObj, final IProgressMonitor pm) {
		final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(modelObj));
		if (rootContainer instanceof final LibraryElement elem) {
			Display.getDefault().syncExec(() -> {
				final TypeEntry entry = elem.getTypeEntry();
				final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.findEditor(new FileEditorInput(entry.getFile()));
				if (editor == null) {
					cmd.execute();
					try {
						entry.save(pm);
					} catch (final CoreException e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				} else {
					editor.getAdapter(CommandStack.class).execute(cmd);
					editor.doSave(pm);
				}
			});
		}
	}

}
