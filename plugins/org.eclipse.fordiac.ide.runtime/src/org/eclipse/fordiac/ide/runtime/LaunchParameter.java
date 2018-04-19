/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime;

/**
 * The Class LaunchParameter.
 */
public class LaunchParameter {
	private String name;
	private String value;
	private boolean fixedValues;
	private String[] values;
	
	public boolean isFixedValues() {
		return fixedValues;
	}

	public void setFixedValues(boolean fixedValues) {
		this.fixedValues = fixedValues;
	}

	public String[] getValues() {
		return values.clone();
	}

	public void setValues(String[] values) {
		this.values = values.clone();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
