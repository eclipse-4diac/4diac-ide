/**
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_ng.struct;

import java.nio.file.Path;
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class StructBaseTemplate extends ForteLibraryElementTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private StructuredType type;
  
  @Override
  protected CharSequence getExportPrefix() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  public StructBaseTemplate(final StructuredType type, final String name, final Path prefix) {
    super(name, prefix);
    this.type = type;
  }
  
  protected CharSequence getStructClassName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CIEC_");
    String _name = this.type.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence structuredTypeFileName(final StructuredType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("forte_");
    String _lowerCase = type.getName().toLowerCase();
    _builder.append(_lowerCase);
    return _builder;
  }
  
  @Pure
  @Override
  protected StructuredType getType() {
    return this.type;
  }
}
