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
	private boolean checkCaseSensitive;
	private boolean checkExactMatching;

	public ModelQuerySpec() {
	}

	public ModelQuerySpec(final String searchString, final boolean isCheckedInstanceName,
			final boolean isCheckedPinName, final boolean isCheckedType, final boolean isCheckedComment,
			final boolean isCaseSensitive, final boolean isExactNameMatching) {
		this.searchString = searchString;
		this.checkInstanceName = isCheckedInstanceName;
		this.checkPinName = isCheckedPinName;
		this.checkType = isCheckedType;
		this.checkComments = isCheckedComment;
		this.checkCaseSensitive = isCaseSensitive;
		this.checkExactMatching = isExactNameMatching;
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

	public boolean isCheckCaseSensitive() {
		return checkCaseSensitive;
	}

	public boolean isCheckExactMatching() {
		return checkExactMatching;
	}

	public void setCheckCaseSensitive(final boolean checkCaseSensitive) {
		this.checkCaseSensitive = checkCaseSensitive;
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

	public void setCheckExactMatching(final boolean checkExactMatching) {
		this.checkExactMatching = checkExactMatching;
	}

	@Override
	public String toString() {
		return "ModelQuerySpec [searchString=" + searchString + ", checkInstanceName=" + checkInstanceName
				+ ", checkPinName=" + checkPinName + ", checkType=" + checkType + ", checkComments=" + checkComments
				+ ", checkCaseSensitive=" + checkCaseSensitive + ", checkExactMatching=" + checkExactMatching + "]";
	}


}
