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
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

public final class MappingTargetCreator {

	private MappingTargetCreator() {
		throw new UnsupportedOperationException("Utility class shall not be instantated!"); //$NON-NLS-1$
	}

	public static FBNetworkElement createMappingTarget(final Resource res, final FBNetworkElement srcElement,
			final String targetFBName) {
		final FBNetworkElement created = createFBNetworkElement(srcElement);

		if (created != null) {
			if (srcElement.getTypeEntry() != null) {
				created.setTypeEntry(srcElement.getTypeEntry());
			}
			// use the src interface to get all parameters
			created.setInterface(InterfaceListCopier.copy(srcElement.getInterface(), true, true));
			created.setPosition(EcoreUtil.copy(srcElement.getPosition()));
			if (srcElement instanceof final ConfigurableFB srcConfFB) {
				((ConfigurableFB) created).setDataType(srcConfFB.getDataType());
			}
			created.setName(extractTargetFBName(targetFBName));
			res.getFBNetwork().getNetworkElements().add(created);
		}
		return created;
	}

	public static void transferFBParams(final FBNetworkElement srcElement, final FBNetworkElement targetElement) {
		final List<VarDeclaration> destInputs = targetElement.getInterface().getInputVars();
		final List<VarDeclaration> srcInputs = srcElement.getInterface().getInputVars();

		for (int i = 0; i < destInputs.size(); i++) {
			final VarDeclaration srcVar = srcInputs.get(i);
			final VarDeclaration dstVar = destInputs.get(i);

			if ((srcVar.getValue() != null) && (!srcVar.getValue().getValue().isEmpty())) {
				if (dstVar.getValue() == null) {
					dstVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
				dstVar.getValue().setValue(srcVar.getValue().getValue());
			}
		}
	}

	private static FBNetworkElement createFBNetworkElement(final FBNetworkElement srcElement) {
		return switch (srcElement) {
		case final FB fb -> BlockInstanceFactory.createFBInstanceForTypeEntry((FBTypeEntry) fb.getTypeEntry());
		case final TypedSubApp typedSubapp -> LibraryElementFactory.eINSTANCE.createTypedSubApp();
		case final UntypedSubApp untypedSubapp -> LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		default -> null;
		};
	}

	private static String extractTargetFBName(final String targetFBName) {
		final var separator = targetFBName.lastIndexOf('.');
		if (separator != -1) {
			return targetFBName.substring(separator + 1);
		}
		return targetFBName;
	}

}
