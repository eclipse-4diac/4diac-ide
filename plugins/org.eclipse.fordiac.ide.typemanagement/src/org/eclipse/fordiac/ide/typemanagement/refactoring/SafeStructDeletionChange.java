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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafeStructDeletionChange extends CompositeChange {
	final DataTypeEntry deletedStruct;
	/* map connecting root nodes with their composite change */
	final Map<EObject, RootNodeChange> rootChanges = new HashMap<>();

	public SafeStructDeletionChange(final StructuredType struct) {
		super(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle);
		deletedStruct = (DataTypeEntry) struct.getTypeEntry();
		createChanges(deletedStruct);
	}

	private void createChanges(final DataTypeEntry entry) {
		final Set<EObject> doneElements = new HashSet<>();
		if (entry != null) {
			final var results = new DataTypeInstanceSearch(entry).performSearch();
			results.forEach(obj -> {
				final RootNodeChange rootChange = createRootChange(obj);
				if (obj instanceof final VarDeclaration varDecl && doneElements.add(varDecl)) {
					if (varDecl.eContainer() instanceof StructuredType) {
						rootChange.add(new DeleteMemberVariableChange(varDecl));
					} else if (isUntypedSubappPin(varDecl)) {
						rootChange.add(new DeleteUntypedSubappPinChange(varDecl));
					} else if (isFbTypePin(varDecl)) {
						rootChange.add(new UpdateFBTypeInterfaceChange((FBType) varDecl.eContainer().eContainer(),
								(StructuredType) varDecl.getType()));
					}
					handleRootElement(varDecl, rootChange, doneElements);
				} else if (obj instanceof final StructManipulator muxer && doneElements.add(muxer)) {
					final boolean isDeletion = deletedStruct == muxer.getDataType().getTypeEntry();
					rootChange.addUpdate(new UpdateManipulatorChange(muxer, isDeletion));
				}
			});
		}
	}

	private RootNodeChange createRootChange(EObject eObject) {
		if (eObject instanceof final INamedElement node) {
			eObject = EcoreUtil.getRootContainer(eObject);
			final RootNodeChange change = new RootNodeChange(eObject);
			if (!rootChanges.containsKey(eObject)) {
				rootChanges.put(eObject, change);
				this.add(change);
			}
		}
		return rootChanges.get(eObject);
	}

	private void handleRootElement(final VarDeclaration varDecl, final RootNodeChange rootChange,
			final Set<EObject> rootElements) {
		final DataTypeEntry dataTypeEntry = (DataTypeEntry) varDecl.getType().getTypeEntry();
		final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
		if (varDecl.getFBNetworkElement() != null) {
			if (rootElements.add(varDecl.getFBNetworkElement())) {
				rootChange.addUpdate(new UpdateInstanceChange(varDecl.getFBNetworkElement(), dataTypeEntry));
			}
		} else if (rootElements.add(rootContainer)) {
			if (rootContainer instanceof final StructuredType stElement) {
				final CompositeChange change = new CompositeChange(MessageFormat
						.format(Messages.Refactoring_AffectedStruct, stElement.getName(), dataTypeEntry.getTypeName()));
				rootChange.addUpdate(change);
				change.add(new StructuredTypeMemberChange(stElement, dataTypeEntry));
				createChanges((DataTypeEntry) stElement.getTypeEntry());
			} else if (rootContainer instanceof final FBType fbType) {
				rootChange.addUpdate(new InterfaceDataTypeChange(fbType, dataTypeEntry));
			}
		}
	}

	private static boolean isUntypedSubappPin(final VarDeclaration varDecl) {
		return varDecl.eContainer() != null && varDecl.eContainer().eContainer() instanceof final SubApp sub
				&& !sub.isTyped() && !sub.isContainedInTypedInstance();
	}

	private static boolean isFbTypePin(final VarDeclaration varDecl) {
		return varDecl.eContainer() != null && varDecl.eContainer().eContainer() instanceof FBType;
	}

	public static class RootNodeChange extends CompositeChange {
		private final List<Change> updateChanges = new ArrayList<>();

		public RootNodeChange(final EObject node) {
			super(Messages.SafeStructDeletionChange_RootNodeChangeText + getName(node) + getFileEnding(node));
		}

		private static String getFileEnding(final EObject node) {
			if (node instanceof final LibraryElement le && le.getTypeEntry() != null) {
				return "." + le.getTypeEntry().getFile().getFileExtension();
			}
			return null;
		}

		private static String getName(final EObject eObj) {
			INamedElement node = null;
			if (!(eObj instanceof final INamedElement n)) {
				return "";
			}
			node = n;

			if (node instanceof final IInterfaceElement iel && iel.getFBNetworkElement() != null) {
				return iel.getFBNetworkElement().getQualifiedName() + iel.getQualifiedName();
			}
			if (node instanceof VarDeclaration && node.eContainer() instanceof final StructuredType struct) {
				return struct.getName() + "." + node.getName(); //$NON-NLS-1$
			}
			return node.getQualifiedName();
		}

		public void addUpdate(final Change change) {
			updateChanges.add(change);
		}

		@Override
		public Change[] getChildren() {
			final ArrayList<Change> list = new ArrayList<>();
			list.addAll(Arrays.asList(super.getChildren()));
			list.addAll(updateChanges);
			return list.toArray(new Change[0]);
		}

		@Override
		public Change perform(final IProgressMonitor pm) throws CoreException {
			// add update changes to the end of the list for correct execution order
			addAll(updateChanges.toArray(new Change[0]));
			return super.perform(pm);
		}
	}

	public static class DeleteMemberVariableChange extends CompositeChange implements IFordiacPreviewChange {
		private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);
		private final TypeEntry entry;
		private final VarDeclaration toChange;

		public DeleteMemberVariableChange(final VarDeclaration toChange) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteMemberVariable, toChange.getName(),
					toChange.getTypeName(), ((INamedElement) toChange.eContainer()).getName()));
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
			return EnumSet.of(ChangeState.DELETE, ChangeState.CHANGE_TO_ANY, ChangeState.NO_CHANGE);
		}

		@Override
		public EnumSet<ChangeState> getDefaultSelection() {
			return EnumSet.of(ChangeState.NO_CHANGE);
		}

	}

	public static class DeleteUntypedSubappPinChange extends CompositeChange {
		VarDeclaration varDecl;

		public DeleteUntypedSubappPinChange(final VarDeclaration varDecl) {
			super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteSubappPins, varDecl.getName(),
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
}