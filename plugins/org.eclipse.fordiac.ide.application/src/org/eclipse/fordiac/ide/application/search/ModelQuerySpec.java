/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

// The inputs from the search page grouped in one place
public class ModelQuerySpec {

	private String searchString;
	private boolean checkInstanceName;
	private boolean checkPinName;
	private boolean checkType;
	private boolean checkComments;

	public ModelQuerySpec() {
	}

	public ModelQuerySpec(final String searchString, final boolean isCheckedInstanceName,
			final boolean isCheckedPinName, final boolean isCheckedType, final boolean isCheckedComment) {
		this.searchString = searchString;
		this.checkInstanceName = isCheckedInstanceName;
		this.checkPinName = isCheckedPinName;
		this.checkType = isCheckedType;
		this.checkComments = isCheckedComment;
	}

	public String getSearchString() {
		return searchString;
	}

	public boolean isCheckedInstanceName() {
		return checkInstanceName;
	}

	public boolean isCheckedPinName() {
		return checkPinName;
	}

	public boolean isCheckedType() {
		return checkType;
	}

	public boolean isCheckedComment() {
		return checkComments;
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
	}

	public void setCheckedInstanceName(final boolean isCheckedInstanceName) {
		this.checkInstanceName = isCheckedInstanceName;
	}

	public void setCheckedPinName(final boolean isCheckedPinName) {
		this.checkPinName = isCheckedPinName;
	}

	public void setCheckedType(final boolean isCheckedType) {
		this.checkType = isCheckedType;
	}

	public void setCheckedComment(final boolean isCheckedComment) {
		this.checkComments = isCheckedComment;
	}

	@Override
	public String toString() {
		return "searchString=" + searchString + ", in instance name=" + checkInstanceName + ", in pin name="
				+ checkPinName + ", in type =" + checkType + ", in comment=" + checkComments;
	}

}
