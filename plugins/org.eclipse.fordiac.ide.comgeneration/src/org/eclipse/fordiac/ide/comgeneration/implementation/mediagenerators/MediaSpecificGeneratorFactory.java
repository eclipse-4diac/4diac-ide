/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public void addGenerator(MediaSpecificGenerator generator) {
		generators.put(generator.getProtocolId(), generator);
	}
	
	public void removeGenerator(MediaSpecificGenerator generator) {
		generators.remove(generator.getProtocolId());
	}
	
	public MediaSpecificGenerator getForProtocolId(String protocolId) {
		return generators.get(protocolId);
	}
	
	public Collection<MediaSpecificGenerator> getForMediaType(String mediaType) {
		HashSet<MediaSpecificGenerator> generatorsForMediaType = new HashSet<MediaSpecificGenerator>();
		for (MediaSpecificGenerator generator : generators.values()) {
			if (generator.getMediaType().equals(mediaType)) {
				generatorsForMediaType.add(generator);
			}
		}
		return generatorsForMediaType;
	}
	
	public void resetAllGenerators() {
		for (MediaSpecificGenerator generator : generators.values()) {
			generator.reset();
		}
	}
}
