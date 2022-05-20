/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.xtext.fbt

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class FBTypeRuntimeModule extends AbstractFBTypeRuntimeModule {

	override bindISerializer() {
		return org.eclipse.xtext.serializer.impl.Serializer
	}
}
