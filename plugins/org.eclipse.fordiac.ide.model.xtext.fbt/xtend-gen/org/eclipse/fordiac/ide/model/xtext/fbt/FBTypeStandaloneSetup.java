/**
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
 */
package org.eclipse.fordiac.ide.model.xtext.fbt;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class FBTypeStandaloneSetup extends FBTypeStandaloneSetupGenerated {
  public static void doSetup() {
    new FBTypeStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
