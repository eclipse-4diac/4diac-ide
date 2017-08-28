/*******************************************************************************
 * Copyright (c) 2012 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.dnd;

import org.eclipse.gef.dnd.SimpleObjectTransfer;

public final class ParameterValueTemplateTransfer extends SimpleObjectTransfer {

	private static final ParameterValueTemplateTransfer INSTANCE = new ParameterValueTemplateTransfer();
	private static final String TYPE_NAME = "Parameter transfer"//$NON-NLS-1$
			+ System.currentTimeMillis() + ":" + INSTANCE.hashCode();//$NON-NLS-1$
	private static final int TYPEID = registerType(TYPE_NAME);

	private ParameterValueTemplateTransfer() {
		// empty private constructor
	}

	/**
	 * Returns the singleton instance.
	 * 
	 * @return the singleton
	 */
	public static ParameterValueTemplateTransfer getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the <i>template</i> object.
	 * 
	 * @return the template
	 */
	public Object getTemplate() {
		return getObject();
	}

	/**
	 * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
	 */
	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

	/**
	 * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
	 */
	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

	/**
	 * Sets the <i>template</i> Object.
	 * 
	 * @param template the template
	 */
	public void setTemplate(final Object template) {
		setObject(template);
	}

}