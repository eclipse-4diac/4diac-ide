/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.edit;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateConfigurableFBModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.RenameUpdateStructDataTypeMemberVariableModelEdit;

public class DataTypeEditBuilder {

	public static void createStructuredDataTypeChanges(final DataTypeEntry dataTypeEntry,
			final List<ModelEdit<?>> modelEdits, final String targetTypeName, final Set<URI> handledElements) {
		DataTypeInstanceSearch.createNonDerivedDataTypeSearch(dataTypeEntry).performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				DataTypeEditBuilder.createSubChange(varDecl, dataTypeEntry, targetTypeName, handledElements,
						modelEdits);
			} else if (obj instanceof final ConfigurableFB configurableFB
					&& handledElements.add(EcoreUtil.getURI(configurableFB))) {
				modelEdits.add(new UpdateConfigurableFBModelEdit(configurableFB, dataTypeEntry));
			}
		});
	}

	public static void createSubChange(final VarDeclaration varDecl, final DataTypeEntry dataTypeEntry,
			final String targetTypeName, final Set<URI> handledElements, final List<ModelEdit<?>> modelEdits) {
		if (varDecl.getBlockFBNetworkElement() != null
				&& varDecl.getBlockFBNetworkElement() instanceof final SubApp subApp && !subApp.isTyped()
				&& !dataTypeEntry.getFullTypeName().equals(targetTypeName)
				&& handledElements.add(EcoreUtil.getURI(varDecl))) {
			modelEdits.add(new DataTypeEdit(Messages.MoveTypeToPackage_UpdateDataTypeInstance,
					EcoreUtil.getURI(varDecl), targetTypeName));
			return;
		}

		final EObject rootContainer = EcoreUtil.getRootContainer(varDecl);
		if (!handledElements.add(EcoreUtil.getURI(rootContainer))) {
			return;
		}
		if (rootContainer instanceof final StructuredType structuredType) {
			modelEdits.add(new RenameUpdateStructDataTypeMemberVariableModelEdit(varDecl, targetTypeName));
			createStructuredDataTypeChanges((DataTypeEntry) structuredType.getTypeEntry(), modelEdits,
					structuredType.getTypeEntry().getFullTypeName(), handledElements);
		}
		if (rootContainer instanceof AttributeDeclaration) {
			modelEdits.add(new RenameUpdateStructDataTypeMemberVariableModelEdit(varDecl, targetTypeName));
		}
		if (rootContainer instanceof FBType && dataTypeEntry.getType() instanceof StructuredType) {
			modelEdits.add(new DataTypeEdit(Messages.MoveTypeToPackage_UpdateDataTypeInstance,
					EcoreUtil.getURI(varDecl), targetTypeName));
		}
	}

	public static String getFullTypeName(final IPath newPath) {
		final String packageName = PackageNameHelper
				.getPackageNameFromURI(URI.createPlatformResourceURI(newPath.toString(), true));
		final String typeName = TypeEntry.getTypeNameFromFileName(newPath.lastSegment());
		if (packageName.isEmpty()) {
			return typeName;
		}
		return packageName + PackageNameHelper.PACKAGE_NAME_DELIMITER + typeName;
	}

}
