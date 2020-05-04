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
public class ForteNgArrayXtend extends ForteNgTestBase {
  private static final String ALGORITHM_NAME = "algorithm";
  
  protected static final boolean INVALID_DECLARATION = false;
  
  protected static final boolean VALID_DECLARATION = true;
  
  @Test
  public void generatedDWORDArrayDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray : ARRAY [0..31] OF DWORD;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DWORD dwordarray[32];");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedDWORDArrayDeclarationWithInitializer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray : ARRAY [0..31] OF DWORD := 0;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "Local arrays can not be initialized.");
  }
  
  @Test
  public void generatedDWORDLocatedArrayDeclarationWithInitializer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable : DWORD;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray AT variable : ARRAY [0..31] OF DWORD := 0;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "Located variables can not be initialized.");
  }
  
  @Test
  public void generatedDWORDArrayDeclarationStartNot0() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray : ARRAY [5..31] OF DWORD;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "Only arrays with a start index of 0 are supported.");
  }
  
  @Test
  public void generatedDWORDArrayDeclarationNotIncrementing() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray : ARRAY [0..0] OF DWORD;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "Only arrays with incrementing index are supported.");
  }
  
  @Test
  public void generatedDWORDArrayDeclarationAtDWORD() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("mydword : DWORD;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("dwordarray AT mydword : ARRAY [0..31] OF DWORD;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "Piecewise located variables cannot access more bits than are available in the destination");
  }
  
  @Test
  public void generatedRelocatedDWORD() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable : DWORD;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable2 AT variable : DWORD;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DWORD variable;");
    _builder_1.newLine();
    _builder_1.append("// replacing all instances of DWORD:variable2 with variable");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedRelocatedDINT() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable : DINT;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable2 AT variable : DINT;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DINT variable;");
    _builder_1.newLine();
    _builder_1.append("// replacing all instances of DINT:variable2 with variable");
    _builder_1.newLine();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedRelocatedDINTincorrectTypes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable : DINT;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("variable2 AT variable : INT;");
    _builder.newLine();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgArrayXtend.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgArrayXtend.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "General located variables must have matching types");
  }
}
