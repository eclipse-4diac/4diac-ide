/*******************************************************************************
 * Copyright (c) 2011, 2012 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;

public class TestPrimitive {
	private Event event;
	private List<DataVariable> data = new ArrayList<DataVariable>();
	private Object relatedModelElement = null;

	public TestPrimitive(Event paEvent) {
		setEvent(paEvent);
	}

	public OutputPrimitive getRelatedOutputPrimitive() {
		if (relatedModelElement instanceof OutputPrimitive) {
			return (OutputPrimitive) relatedModelElement;
		}
		return null;
	}

	public InputPrimitive getRelatedInputPrimitive() {
		if (relatedModelElement instanceof InputPrimitive) {
			return (InputPrimitive) relatedModelElement;
		}
		return null;
	}

	public void setRelatedModelElement(OutputPrimitive relatedModelElement) {
		this.relatedModelElement = relatedModelElement;
	}

	public void setRelatedModelElement(InputPrimitive relatedModelElement) {
		this.relatedModelElement = relatedModelElement;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<DataVariable> getData() {
		return data;
	}

	public void setData(List<DataVariable> data) {
		this.data = data;
	}

	public boolean addData(DataVariable paData) {
		return this.data.add(paData);
	}

	@Override
	public String toString() {
		String retval = "";
		if (null != event) {
			retval = event.toString();
		}
		if (null != data) {
			retval += data.toString();
		}
		return retval;
	}

}
