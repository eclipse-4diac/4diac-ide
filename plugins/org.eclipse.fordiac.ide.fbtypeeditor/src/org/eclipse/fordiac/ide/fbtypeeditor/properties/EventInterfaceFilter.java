/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.viewers.IFilter;

public class EventInterfaceFilter implements IFilter{

	@Override
	public boolean select(Object toTest) {
		if(toTest instanceof InterfaceEditPart && ((InterfaceEditPart) toTest).getCastedModel() instanceof Event){
			return true;
		}
		if(toTest instanceof TypeEditPart && ((TypeEditPart) toTest).getCastedModel() instanceof Event){
			return true;
		}
		if(toTest instanceof CommentEditPart && ((CommentEditPart) toTest).getCastedModel() instanceof Event){
			return true;
		}
		if(toTest instanceof Event){
			return true;
		}
		return false;
	}

}
