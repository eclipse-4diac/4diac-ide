/*******************************************************************************
 * Copyright (c) 2007, 2026 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							Johannes Kepler University,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - Extracted from DeploymentExecutor and extended for Configurable move
 *  Zijun Tang - resolve package-qualified GenericClassName for deployment
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBManagement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;

public class ForteTypeNameCreator implements TypeNameCreator {

	public static final TypeNameCreator TYPE_NAME_CREATOR = new ForteTypeNameCreator();

	@Override
	public String getTypeName(final TypeEntry entry) {
		final String override = getDeployTypeOverride(entry.getType());
		if (override != null) {
			return override;
		}
		return convertFullTypeNameToFORTE(entry.getFullTypeName());
	}

	@Override
	public String getTypeName(final FBNetworkElement fb) {
		if (fb != null && fb.getTypeEntry() != null) {
			if (fb instanceof final ConfigurableFB confFB) {
				return getConfigureFBType(confFB);
			}
			final String override = getDeployTypeOverride(fb.getType());
			if (override != null) {
				return override;
			}
			return getForteTypeName(fb);
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getTypeNameWithHash(final TypeEntry entry) throws LibraryElementHashException {
		final String hash = entry.getTypeHash();
		if (hash.isEmpty()) {
			return getTypeName(entry);
		}
		return getTypeName(entry) + '#' + hash;
	}

	/**
	 * Resolve a package-qualified deploy type name from GenericClassName. Short
	 * values such as {@code GEN_PUBLISH} are ignored so existing typelib FBs keep
	 * deploying their concrete type name.
	 */
	private static String getDeployTypeOverride(final LibraryElement type) {
		if (type == null) {
			return null;
		}
		final String genericClassName = getGenericClassName(type);
		if (genericClassName != null && genericClassName.contains("::")) { //$NON-NLS-1$
			return convertFullTypeNameToFORTE(genericClassName);
		}
		return null;
	}

	private static String getGenericClassName(final LibraryElement type) {
		Attribute attribute = type.getAttribute(TypeLibraryTags.GENERIC_CLASS_NAME_ATTRIBUTE_FULL_NAME);
		if (attribute == null) {
			attribute = type.getAttribute(TypeLibraryTags.GENERIC_CLASS_NAME_ATTRIBUTE_NAME);
		}
		if (attribute == null) {
			return null;
		}
		return stripQuotes(attribute.getValue());
	}

	private static String stripQuotes(final String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		if (value.length() >= 2 && value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
			return value.substring(1, value.length() - 1);
		}
		return value;
	}

	private static String getForteTypeName(final FBNetworkElement fb) {
		return convertFullTypeNameToFORTE(fb.getFullTypeName());
	}

	private static String getConfigureFBType(final ConfigurableFB confFB) {
		final DataType dt = confFB.getDataType();
		String typeName = getForteTypeName(confFB);
		if (dt != null) {
			// The _1 is needed for 4diac FORTE to separate type name from configuration
			// part
			typeName += "_1" + convertFullTypeNameToFORTE(PackageNameHelper.getFullTypeName(dt)); //$NON-NLS-1$
			if (confFB instanceof final Demultiplexer demux && isConfigured(demux)) {
				// The ____ is needed for 4diac FORTE to separate the configured name from the
				// visible children part
				typeName += "____" + ConfigurableFBManagement.buildVisibleChildrenString(demux.getMemberVars()); //$NON-NLS-1$
			}
		}
		return typeName;
	}

	private static boolean isConfigured(final Demultiplexer demux) {
		final EObject eObj = demux.eContainer().eContainer();
		if (eObj instanceof Resource) {
			return ((Demultiplexer) demux.getOpposite()).isIsConfigured();
		}
		return demux.isIsConfigured();
	}

	private static String convertFullTypeNameToFORTE(final String fullTypeName) {
		return fullTypeName;
	}

}
