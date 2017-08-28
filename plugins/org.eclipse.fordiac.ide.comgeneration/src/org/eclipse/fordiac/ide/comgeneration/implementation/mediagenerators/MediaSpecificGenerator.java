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

import org.eclipse.fordiac.ide.comgeneration.implementation.ChannelEnd;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationMediaInfo;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public interface MediaSpecificGenerator {

	public abstract String getMediaType();

	public abstract String getProtocolId();

	public abstract FBTypePaletteEntry getPaletteType(ChannelEnd end,
			int numDataPorts, boolean local);
	
	
	public abstract void configureFBs(FB sourceFB, FB destinationFB, CommunicationMediaInfo mediaInfo);

	public abstract void reset();

	public abstract boolean isSeparatedSource();
	
	public VarDeclaration getTargetInputData(int index, FB fb);
	public VarDeclaration getTargetOutputData(int index, FB fb);
}