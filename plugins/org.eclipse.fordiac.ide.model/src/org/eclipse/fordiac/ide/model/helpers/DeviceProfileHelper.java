/*******************************************************************************
 * Copyright (c) 2026 Aimirim STI
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;

/** 
 * Helper for the 'SupportedProfiles' attribute that a device type may
 * declare in its type file (.dev) to restrict the set of communication profiles
 * available for its devices. */
public final class DeviceProfileHelper {

	/**
	 * Return the comma separated list of communication profiles.
     * Blank or missing attribute means no restrictions */
	public static List<String> getSupportedProfiles(final ConfigurableObject object) {
		if (object == null) {
			return Collections.emptyList();
		}
		Attribute attribute = object.getAttribute(TypeLibraryTags.SUPPORTED_PROFILES_ATTRIBUTE_FULL_NAME);
		// Add a fallback search for the attribute to mirror TypeHash example
		if (attribute == null) {
			attribute = object.getAttribute(TypeLibraryTags.SUPPORTED_PROFILES_ATTRIBUTE_NAME);
		}
		if (attribute == null || attribute.getValue() == null || attribute.getValue().isBlank()) {
			return Collections.emptyList();
		}
		return Arrays.stream(decodeValue(attribute.getValue()).split(",")) //$NON-NLS-1$
				.map(String::trim).filter(profile -> !profile.isEmpty()).toList();
	}

	/**
     * Attribute value is stored as STRING literal */
	private static String decodeValue(final String value) {
		try {
			return StringValueConverter.INSTANCE.toValue(value.trim());
		} catch (final IllegalArgumentException e) {
			return value;
		}
	}

	private DeviceProfileHelper() {
		throw new UnsupportedOperationException();
	}
}
