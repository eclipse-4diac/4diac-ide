/*******************************************************************************
 * Copyright (c) 2022 Felix Roithmayr
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - added enum for use with new model attributes
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

public interface ServiceSequenceTypes {
	String DEFAULT = "POSSIBLE"; //$NON-NLS-1$
	String FORBIDDEN = "FORBIDDEN"; //$NON-NLS-1$
	String ALWAYS = "ALWAYS"; //$NON-NLS-1$
	String CONDITIONAL = "CONDITIONAL"; //$NON-NLS-1$
}
