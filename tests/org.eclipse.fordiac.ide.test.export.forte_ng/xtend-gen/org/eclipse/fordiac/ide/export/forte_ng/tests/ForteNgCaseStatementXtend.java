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
public abstract class ForteNgCaseStatementXtend extends ForteNgTestBase {
  private static final String ALGORITHM_NAME = "algorithm";
  
  private static final String VARIABLE_NAME = "variable";
  
  private static final String DINT = "DINT";
  
  @Test
  public void validCaseStatement() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgCaseStatementXtend.VARIABLE_NAME, ForteNgCaseStatementXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE variable OF");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("0: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("1: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("2: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("255: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_CASE;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgCaseStatementXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgCaseStatementXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("switch (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME);
    _builder_1.append("()) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append("case 0:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 1:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 2:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 255:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 0;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void validCaseStatementWithList() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgCaseStatementXtend.VARIABLE_NAME, ForteNgCaseStatementXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE variable OF");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("0, 1, 2: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("255: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_CASE;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgCaseStatementXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgCaseStatementXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("switch (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME);
    _builder_1.append("()) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append("case 0: case 1: case 2:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 255:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 0;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void validCaseStatementWithElse() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgCaseStatementXtend.VARIABLE_NAME, ForteNgCaseStatementXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE variable OF");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("0: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("255: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("ELSE");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 255;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_CASE;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgCaseStatementXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgCaseStatementXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("switch (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME);
    _builder_1.append("()) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append("case 0:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 255:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 0;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("default:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 255;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void validCaseStatementWithIfInside() {
    this.functionBlock.getInternalVars().add(this.createVarDeclaration(ForteNgCaseStatementXtend.VARIABLE_NAME, ForteNgCaseStatementXtend.DINT));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CASE variable OF");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("0: ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("IF ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder.append(" < 20 THEN");
    _builder.newLineIfNotEmpty();
    _builder.append("\t \t\t");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t \t\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t \t\t");
    _builder.append(" + 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t \t");
    _builder.append("ELSE");
    _builder.newLine();
    _builder.append("\t \t\t");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t \t\t");
    _builder.append(" := ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t \t\t");
    _builder.append(" - 1;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t \t");
    _builder.append("END_IF;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("255: ");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("ELSE");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t");
    _builder.append(" := 255;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_CASE;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgCaseStatementXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgCaseStatementXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("switch (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME);
    _builder_1.append("()) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append("case 0:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("if((");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() < 20)) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t\t");
    _builder_1.append("() + 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("else {");
    _builder_1.newLine();
    _builder_1.append("\t\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t\t");
    _builder_1.append("() = (");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t\t");
    _builder_1.append("() - 1);");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("case 255:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 0;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("default:");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append(ForteNgCaseStatementXtend.VARIABLE_NAME, "\t\t");
    _builder_1.append("() = 255;");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t\t");
    _builder_1.append("break;");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
}
