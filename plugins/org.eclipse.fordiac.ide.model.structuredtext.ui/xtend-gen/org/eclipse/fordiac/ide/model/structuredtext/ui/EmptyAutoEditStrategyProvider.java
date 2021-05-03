/**
 * Copyright (c) 2018 Johannes Kepler University Linz (JKU)
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 * Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ui;

import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;

@SuppressWarnings("all")
public class EmptyAutoEditStrategyProvider extends AbstractEditStrategyProvider {
  public EmptyAutoEditStrategyProvider() {
  }
  
  @Override
  protected void configure(final AbstractEditStrategyProvider.IEditStrategyAcceptor iEditStrategyAcceptor) {
  }
}
