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
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_ng.tests;

import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBase;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public abstract class ForteNgArrayAtDeclarationXtend extends ForteNgTestBase {
  protected static final boolean VALID_DECLARATION = true;
  
  protected static final boolean INVALID_DECLARATION = (!ForteNgArrayAtDeclarationXtend.VALID_DECLARATION);
  
  protected void testLocatedArrayDeclaration(final String sourceType, final String accessType, final int arrayStart, final int arrayStop, final boolean isValid) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ");
    _builder.append(sourceType, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [");
    _builder.append(arrayStart, "  ");
    _builder.append("..");
    _builder.append(arrayStop, "  ");
    _builder.append("] OF ");
    _builder.append(accessType, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.getFunctionBlock().getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.generateAlgorithm(this.getFunctionBlock(), ForteNgTestBase.ALGORITHM_NAME, this.getErrors());
    if ((isValid == ForteNgArrayAtDeclarationXtend.VALID_DECLARATION)) {
      ForteNgTestBase.assertNoErrors(this.getErrors());
      Assert.assertNotNull(generatedCode);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("CIEC_");
      _builder_1.append(sourceType);
      _builder_1.append(" ");
      _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("ARRAY_AT<CIEC_");
      _builder_1.append(accessType);
      _builder_1.append(", CIEC_");
      _builder_1.append(sourceType);
      _builder_1.append(", ");
      _builder_1.append(arrayStart);
      _builder_1.append(", ");
      _builder_1.append(arrayStop);
      _builder_1.append("> ");
      _builder_1.append(ForteNgTestBase.VARIABLE2_NAME);
      _builder_1.append("(");
      _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
      _builder_1.append(");");
      _builder_1.newLineIfNotEmpty();
      Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
    } else {
      ForteNgTestBase.assertErrors(this.getErrors());
      Assert.assertNull(generatedCode);
    }
  }
}
