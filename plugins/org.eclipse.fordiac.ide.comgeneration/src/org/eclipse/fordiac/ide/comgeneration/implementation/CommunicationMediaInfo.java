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
package org.eclipse.fordiac.ide.comgeneration.implementation;

import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public class CommunicationMediaInfo {
	private Link sourceLink;
	private Link destinationLink;
	private Segment segment;
	
	public CommunicationMediaInfo(Link sourceLink, Link destinationLink,
			Segment segment) {
		super();
		this.sourceLink = sourceLink;
		this.destinationLink = destinationLink;
		this.segment = segment;
	}

	public Link getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(Link sourceLink) {
		this.sourceLink = sourceLink;
	}

	public Link getDestinationLink() {
		return destinationLink;
	}

	public void setDestinationLink(Link destinationLink) {
		this.destinationLink = destinationLink;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	
	
	
	
}
