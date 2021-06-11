/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MediaSpecificGeneratorFactory {
	private final Map<String, MediaSpecificGenerator> generators = new HashMap<>();

	public MediaSpecificGeneratorFactory() {
		super();
	}

	public void addGenerator(final MediaSpecificGenerator generator) {
		generators.put(generator.getProtocolId(), generator);
	}

	public void removeGenerator(final MediaSpecificGenerator generator) {
		generators.remove(generator.getProtocolId());
	}

	public MediaSpecificGenerator getForProtocolId(final String protocolId) {
		return generators.get(protocolId);
	}

	public Collection<MediaSpecificGenerator> getForMediaType(final String mediaType) {
		final HashSet<MediaSpecificGenerator> generatorsForMediaType = new HashSet<>();
		for (final MediaSpecificGenerator generator : generators.values()) {
			if (generator.getMediaType().equals(mediaType)) {
				generatorsForMediaType.add(generator);
			}
		}
		return generatorsForMediaType;
	}

	public void resetAllGenerators() {
		for (final MediaSpecificGenerator generator : generators.values()) {
			generator.reset();
		}
	}
}
