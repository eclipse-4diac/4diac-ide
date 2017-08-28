/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor;

import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.model.data.DataType;

public class DataTypeCreationFactory extends TemplateCreationFactory {
	public DataTypeCreationFactory(DataType typeTemplate) {		
		super(typeTemplate);
	}
}
