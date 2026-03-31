/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class PinPropertiesCache {

	private final Map<IInterfaceElement, PinProperties> cache = new HashMap<>();

	public List<PinProperty> getCurrentPinProperties(final InterfaceList il) {
		final Set<IInterfaceElement> allIEs = il.getAllInterfaceElements().collect(Collectors.toSet());

		cache.keySet().retainAll(allIEs);

		allIEs.forEach(pin -> cache.computeIfAbsent(pin, PinProperties::createFromPin));

		return cache.values().stream() //
				.flatMap(prop -> Stream.of(prop.typeProp(), prop.commentProp(), prop.withProp()))
				.filter(PinProperty.class::isInstance).map(PinProperty.class::cast) //
				.toList();
	}

}
