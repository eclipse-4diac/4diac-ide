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
	private boolean isCheckedInstanceName;
	private boolean isCheckedPinName;
	private boolean isCheckedType;
	private boolean isCheckedComment;

	public ModelQuerySpec() {
	}

	public ModelQuerySpec(final String searchString, final boolean isCheckedInstanceName,
			final boolean isCheckedPinName, final boolean isCheckedType, final boolean isCheckedComment) {
		this.searchString = searchString;
		this.isCheckedInstanceName = isCheckedInstanceName;
		this.isCheckedPinName = isCheckedPinName;
		this.isCheckedType = isCheckedType;
		this.isCheckedComment = isCheckedComment;
	}

	public String getSearchString() {
		return searchString;
	}

	public boolean isCheckedInstanceName() {
		return isCheckedInstanceName;
	}

	public boolean isCheckedPinName() {
		return isCheckedPinName;
	}

	public boolean isCheckedType() {
		return isCheckedType;
	}

	public boolean isCheckedComment() {
		return isCheckedComment;
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
	}

	public void setCheckedInstanceName(final boolean isCheckedInstanceName) {
		this.isCheckedInstanceName = isCheckedInstanceName;
	}

	public void setCheckedPinName(final boolean isCheckedPinName) {
		this.isCheckedPinName = isCheckedPinName;
	}

	public void setCheckedType(final boolean isCheckedType) {
		this.isCheckedType = isCheckedType;
	}

	public void setCheckedComment(final boolean isCheckedComment) {
		this.isCheckedComment = isCheckedComment;
	}

	@Override
	public String toString() {
		return "ModelQuerySpec [searchString=" + searchString + ", isCheckedInstanceName=" + isCheckedInstanceName
				+ ", isCheckedPinName=" + isCheckedPinName + ", isCheckedType=" + isCheckedType + ", isCheckedComment="
				+ isCheckedComment + "]";
	}

}
