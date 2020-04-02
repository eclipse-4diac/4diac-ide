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
import org.junit.Test;

@SuppressWarnings("all")
public abstract class ForteNgForLoopXtend extends ForteNgTestBase {
  private static final String ALGORITHM_NAME = "algorithm";
  
  private static final String VARIABLE1_NAME = "variable1";
  
  private static final String VARIABLE2_NAME = "variable2";
  
  private static final String DINT = "DINT";
  
  @Test
  public void validForLoop() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgForLoopXtend.VARIABLE1_NAME, ForteNgForLoopXtend.DINT));
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgForLoopXtend.VARIABLE2_NAME, ForteNgForLoopXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FOR ");
    _builder.append(ForteNgForLoopXtend.VARIABLE1_NAME);
    _builder.append(":=1 TO 5 DO");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(ForteNgForLoopXtend.VARIABLE2_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_FOR;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgForLoopXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgForLoopXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5");
    _builder_1.newLine();
    _builder_1.append("auto by = 1;");
    _builder_1.newLine();
    _builder_1.append("auto to = 5;");
    _builder_1.newLine();
    _builder_1.append("for(variable1() = 1;");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("(by >  0 && variable1() <= to) ||");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("(by <= 0 && variable1() >= to);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("variable1() = variable1() + by){");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("variable2() = 0;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void validForLoopWithBy() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgForLoopXtend.VARIABLE1_NAME, ForteNgForLoopXtend.DINT));
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgForLoopXtend.VARIABLE2_NAME, ForteNgForLoopXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FOR ");
    _builder.append(ForteNgForLoopXtend.VARIABLE1_NAME);
    _builder.append(":=1 TO 5 BY 2 DO");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append(ForteNgForLoopXtend.VARIABLE2_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_FOR;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgForLoopXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgForLoopXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5");
    _builder_1.newLine();
    _builder_1.append("auto by = 2;");
    _builder_1.newLine();
    _builder_1.append("auto to = 5;");
    _builder_1.newLine();
    _builder_1.append("for(variable1() = 1;");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("(by >  0 && variable1() <= to) ||");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("(by <= 0 && variable1() >= to);");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("variable1() = variable1() + by){");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("variable2() = 0;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
}
