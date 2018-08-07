/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
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
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.gef.requests.CreationFactory;

/**
 * The typeTemplate is used to create a new instance of the typeTemplate.
 * 
 */
public class FBTypeTemplateCreationFactory implements CreationFactory {

	/** The type template. */
	private final Object typeTemplate;

	/**
	 * The Constructor with the specified template.
	 * 
	 * @param typeTemplate the type template of an fb type
	 */
	public FBTypeTemplateCreationFactory(final Object typeTemplate) {
		this.typeTemplate = typeTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
	 */
	@Override
	public Object getNewObject() {
		// TODO implement
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
	 */
	@Override
	public Object getObjectType() {
		return typeTemplate;
	}

}
