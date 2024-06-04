/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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
 *   Bianca Wiesmayr - rework to new struct search
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafeStructDeletionChange extends CompositeChange {

	public SafeStructDeletionChange(final StructuredType struct) {
		super(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle);
		final DataTypeEntry typeEntry = (DataTypeEntry) struct.getTypeEntry();
		createChanges(typeEntry, this);
	}

	private static void createChanges(final DataTypeEntry entry, final CompositeChange change) {
		final Set<EObject> rootElements = new HashSet<>();
		if (entry != null) {
			final var results = new DataTypeInstanceSearch(entry).performSearch();
			results.forEach(obj -> {
				if (obj instanceof final VarDeclaration varDecl && rootElements.add(varDecl)) {
					if (varDecl.eContainer() instanceof StructuredType) {
						change.add(new DeleteMemberVariableChange(varDecl));
					} else if (isUntypedSubappPin(varDecl)) {
						change.add(new DeleteUntypedSubappPinChange(varDecl));
					} else if (isFbTypePin(varDecl)) {
						change.add(new DeleteFBTypeInterfaceChange((FBType) varDecl.eContainer().eContainer(),
								(StructuredType) varDecl.getType()));
					}
					change.add(handleRootElement(varDecl, rootElements));
				} else if (obj instanceof final StructManipulator muxer && rootElements.add(muxer)) {
					change.add(new UpdateManipulatorChange(muxer));
				}
			});
		}
	}

	private static Change handleRootElement(final VarDeclaration varDecl, final Set<EObject> rootElements) {
		final DataTypeEntry dataTypeEntry = (DataTypeEntry) varDecl.getType().getTypeEntry();
		if (varDecl.getFBNetworkElement() != null) {
			if (rootElements.add(varDecl.getFBNetworkElement())) {
				return new UpdateInstancesChange(varDecl.getFBNetworkElement(), dataTypeEntry);
			}
		} else {
			final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
			if (rootElements.add(rootContainer)) {
				if (rootContainer instanceof final StructuredType stElement) {
					final CompositeChange change = new CompositeChange(MessageFormat.format(
							Messages.Refactoring_AffectedStruct, stElement.getName(), dataTypeEntry.getTypeName()));
					change.add(new StructuredTypeMemberChange(stElement, dataTypeEntry, dataTypeEntry.getTypeName(),
							dataTypeEntry.getTypeName()));
					createChanges((DataTypeEntry) stElement.getTypeEntry(), change);
					return change;
				}
				if (rootContainer instanceof final FBType fbType) {
					return new InterfaceDataTypeChange(fbType, dataTypeEntry, varDecl.getName());
				}
			}
		}
		return null;
	}

	private static boolean isUntypedSubappPin(final VarDeclaration varDecl) {
		return varDecl.eContainer() != null && varDecl.eContainer().eContainer() instanceof final SubApp sub
				&& !sub.isTyped() && !sub.isContainedInTypedInstance();
	}

	private static boolean isFbTypePin(final VarDeclaration varDecl) {
		return varDecl.eContainer() != null && varDecl.eContainer().eContainer() instanceof FBType;
	}

	public static class DeleteMemberVariableChange extends CompositeChange implements IFordiacPreviewChange {
		private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);
		private final TypeEntry entry;
		private final VarDeclaration toChange;

		public DeleteMemberVariableChange(final VarDeclaration toChange) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteMemberVariable,
					toChange.getTypeName()));
			this.entry = toChange.getType().getTypeEntry();
			this.toChange = toChange;
			this.state.addAll(getDefaultSelection());
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final StructuredType type = (StructuredType) entry.getTypeEditable();
			final Command cmd = getCommandForState(type);
			if (cmd != null) {
				AbstractLiveSearchContext.executeAndSave(cmd, type, pm);
			}
			return super.perform(pm);
		}

		private Command getCommandForState(final StructuredType type) {
			if (state.contains(ChangeState.DELETE)) {
				return new DeleteMemberVariableCommand(type, toChange);
			}
			if (state.contains(ChangeState.CHANGE_TO_ANY)) {
				return ChangeDataTypeCommand.forDataType(toChange, IecTypes.GenericTypes.ANY_STRUCT);
			}
			return null;
		}

		@Override
		public EnumSet<ChangeState> getState() {
			return state;
		}

		@Override
		public void addState(final ChangeState newState) {
			state.add(newState);
		}

		@Override
		public EnumSet<ChangeState> getAllowedChoices() {
			return EnumSet.of(ChangeState.DELETE, ChangeState.CHANGE_TO_ANY);
		}

		@Override
		public EnumSet<ChangeState> getDefaultSelection() {
			return EnumSet.of(ChangeState.CHANGE_TO_ANY);
		}

	}

	public static class DeleteUntypedSubappPinChange extends CompositeChange {
		VarDeclaration varDecl;

		public DeleteUntypedSubappPinChange(final VarDeclaration varDecl) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteSubappPins,
					getSubappName(varDecl)));
			this.varDecl = varDecl;
		}

		private static String getSubappName(final VarDeclaration varDecl) {
			return ((SubApp) varDecl.eContainer().eContainer()).getQualifiedName();
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			final Command cmd = new DeleteSubAppInterfaceElementCommand(varDecl);
			AbstractLiveSearchContext.executeAndSave(cmd, varDecl, pm);
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
			final Command cmd = new ChangeStructCommand(manipulator, getErrorMarkerEntry(manipulator.getDataType()),
					true);
			AbstractLiveSearchContext.executeAndSave(cmd, manipulator, pm);
			return super.perform(pm);
		}

		private ErrorMarkerDataType getErrorMarkerEntry(final DataType dtp) {
			final DataTypeLibrary lib = manipulator.getTypeEntry().getTypeLibrary().getDataTypeLibrary();
			return lib.createErrorMarkerType(dtp.getTypeEntry().getFullTypeName(), ""); //$NON-NLS-1$
		}

	}

	public static class UpdateFBTypeChange extends CompositeChange {

		public UpdateFBTypeChange(final FBType type) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateFBType, type.getName()));
		}

	}
}