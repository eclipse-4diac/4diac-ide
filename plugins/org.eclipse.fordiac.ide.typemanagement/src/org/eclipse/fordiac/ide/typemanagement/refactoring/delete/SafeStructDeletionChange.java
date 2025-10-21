/*******************************************************************************
 * Copyright (c) 2023, 2025 Primetals Technologies Austria GmbH
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
 *   Patrick Aigner - now based on ModelEditChange
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBInstanceModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateManipulatorModelEdit;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class SafeStructDeletionChange extends CompositeChange {
	private final List<ModelEdit<?>> changes = new ArrayList<>();

	public SafeStructDeletionChange(final StructuredType struct) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_SafeDeletionChangeTitle, struct.getName()));
		createChanges((DataTypeEntry) struct.getTypeEntry());
		final CompositeChange comp = ModelEditChange.fromModelEdits("", changes); //$NON-NLS-1$
		if (comp != null) {
			this.merge(comp);
		}
	}

	public void createChanges(final DataTypeEntry entry) {
		final Set<EObject> doneElements = new HashSet<>();
		if (entry != null) {
			final var results = new DataTypeInstanceSearch(entry).performSearch();
			results.forEach(eObject -> {
				if (eObject instanceof final VarDeclaration varDecl && doneElements.add(varDecl)) {
					if (varDecl.eContainer() instanceof StructuredType) {
						changes.add(new DeleteUpdateStructDataTypeMemberVariableModelEdit(varDecl));
						handleTransitiveRefactoring(varDecl, doneElements);
					} else if (isUntypedSubappPin(varDecl)) {
						changes.add(new UpdateUntypedSubappPinModelEdit(varDecl));
					} else if (isFbTypePin(varDecl)) {
						handleTransitiveRefactoring(varDecl, doneElements);
					}
				} else if (eObject instanceof final StructManipulator muxer && doneElements.add(muxer)) {
					changes.add(new UpdateManipulatorModelEdit(muxer));
				} else if (eObject instanceof final Attribute attribute) {
					changes.add(new DeleteAttributeModelEdit(attribute, IecTypes.GenericTypes.ANY_STRUCT));
				}
			});
		}
	}

	/**
	 *
	 * This method should handle a transiitve refactoring. For example Struct A = [
	 * INT A ; INT B ] Struct B = [ INT B ; Struct A] Struct C = [ INT C ; Struct B
	 * ]
	 *
	 * When A will be deleted it needs to handle its instance in Struct B first.
	 * After B has been processed (either deleted, changed to ANY_STRUCT, or
	 * creating an error marker) als C needs to be updated. e.g. the initial value
	 * of c.B is not valid any more.
	 *
	 */
	private void handleTransitiveRefactoring(final VarDeclaration varDecl, final Set<EObject> rootElements) {
		final DataTypeEntry dataTypeEntry = (DataTypeEntry) varDecl.getType().getTypeEntry();
		final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
		if (varDecl.getBlockFBNetworkElement() != null) {
			if (rootElements.add(varDecl.getBlockFBNetworkElement())) {
				changes.add(new UpdateFBInstanceModelEdit(varDecl.getBlockFBNetworkElement(), dataTypeEntry));
			}
		} else if (rootElements.add(rootContainer)) {
			if (rootContainer instanceof final StructuredType stElement) {
				createChanges((DataTypeEntry) stElement.getTypeEntry());
			} else if (rootContainer instanceof final FBType fbType
					&& dataTypeEntry.getType() instanceof final StructuredType type) {
				changes.add(new DeleteUpdateFBTypeInterfaceModelEdit(fbType, type));
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
}