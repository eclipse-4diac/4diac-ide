/*******************************************************************************
 * Copyright (c) 2007 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							Johannes Kepler University,
 * 							Primetals Technologies Austria GmbH,
 * 							Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - Extracted from DeploymentExecutor and extended for Configurable move
 *  Zijun Tang - resolve package-qualified ForteTypeOverride for deployment
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.text.MessageFormat;
import java.util.Optional;

import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;

public class ForteTypeNameCreator implements TypeNameCreator {

	public static final TypeNameCreator TYPE_NAME_CREATOR = new ForteTypeNameCreator();

	/** FORTE deploy type override attribute (package-qualified or unqualified). */
	private static final String FORTE_TYPE_OVERRIDE_ATTRIBUTE = "eclipse4diac::core::ForteTypeOverride"; //$NON-NLS-1$

	@Override
	public String getTypeName(final TypeEntry entry) throws DeploymentException {
		final String override = getDeployTypeOverride(entry.getType());
		if (override != null) {
			return override;
		}
		return convertFullTypeNameToFORTE(entry.getFullTypeName());
	}

	@Override
	public String getTypeName(final FBNetworkElement fb) throws DeploymentException {
		if (fb != null && fb.getTypeEntry() != null) {
			// Configurable FBs encode the selected data type in the deployed name
			// (Type_1DataType). ForteTypeOverride would replace that synthesized name and
			// break FORTE configuration, so it is intentionally not considered here.
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
	public String getTypeNameWithHash(final TypeEntry entry)
			throws LibraryElementHashException, DeploymentException {
		final String hash = entry.getTypeHash();
		if (hash.isEmpty()) {
			return getTypeName(entry);
		}
		return getTypeName(entry) + '#' + hash;
	}

	/**
	 * Resolve a deploy type name from {@code eclipse4diac::core::ForteTypeOverride}.
	 * The attribute is dedicated to FORTE deployment and may be package-qualified or
	 * unqualified.
	 */
	private static String getDeployTypeOverride(final LibraryElement type) throws DeploymentException {
		if (type == null) {
			return null;
		}
		final String override = getForteTypeOverride(type);
		if (override == null || override.isEmpty()) {
			return null;
		}
		return convertFullTypeNameToFORTE(override);
	}

	private static String getForteTypeOverride(final LibraryElement type) throws DeploymentException {
		final Attribute attribute = type.getAttribute(FORTE_TYPE_OVERRIDE_ATTRIBUTE);
		if (attribute == null) {
			return null;
		}
		final String value = decodeAttributeValue(type, attribute.getValue());
		// Empty values (including the attribute declaration default '') mean "not
		// configured" and must keep the concrete type name.
		if (value == null || value.isBlank()) {
			return null;
		}
		validateTypeName(type, value, attribute.getValue());
		return value;
	}

	private static String decodeAttributeValue(final LibraryElement type, final String value)
			throws DeploymentException {
		if (value == null || value.isEmpty()) {
			return value;
		}
		try {
			return StringValueConverter.INSTANCE.toValue(value.trim());
		} catch (final IllegalArgumentException e) {
			throw new DeploymentException(MessageFormat.format(Messages.ForteTypeNameCreator_InvalidForteTypeOverride,
					PackageNameHelper.getFullTypeName(type), value), e);
		}
	}

	private static void validateTypeName(final LibraryElement type, final String typeName, final String rawValue)
			throws DeploymentException {
		final Optional<String> error = IdentifierVerifier.verifyPackageName(typeName);
		if (error.isPresent()) {
			throw new DeploymentException(MessageFormat.format(Messages.ForteTypeNameCreator_InvalidForteTypeOverride,
					PackageNameHelper.getFullTypeName(type), rawValue));
		}
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
		}
		return typeName;
	}

	private static String convertFullTypeNameToFORTE(final String fullTypeName) {
		return fullTypeName;
	}

}
