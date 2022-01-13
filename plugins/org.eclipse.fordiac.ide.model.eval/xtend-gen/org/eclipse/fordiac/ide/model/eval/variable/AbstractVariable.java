/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.variable;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class AbstractVariable implements Variable {
  @Accessors
  private final VarDeclaration declaration;
  
  public AbstractVariable(final VarDeclaration declaration) {
    this.declaration = declaration;
  }
  
  @Override
  public String getName() {
    return this.declaration.getName();
  }
  
  @Override
  public String toString() {
    return this.getValue().toString();
  }
  
  @Pure
  @Override
  public VarDeclaration getDeclaration() {
    return this.declaration;
  }
}
