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
public abstract class ForteNgArrayXtend extends ForteNgTestBase {
  protected static final boolean VALID_DECLARATION = true;
  
  protected static final boolean INVALID_DECLARATION = (!ForteNgArrayXtend.VALID_DECLARATION);
  
  @Test
  public void generatedDWORDArrayDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [0..31] OF DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DWORD ");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append("[32];");
    _builder_1.newLineIfNotEmpty();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedDWORDArrayDeclarationWithInitializer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [0..31] OF DWORD := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
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
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [0..31] OF DWORD := 0;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
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
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [5..31] OF DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
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
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [0..0] OF DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
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
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [0..31] OF DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
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
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DWORD;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DWORD ");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append(";");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("// replacing all instances of DWORD:");
    _builder_1.append(ForteNgTestBase.VARIABLE2_NAME);
    _builder_1.append(" with ");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.newLineIfNotEmpty();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedRelocatedDINT() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DINT;");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DINT;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertNoErrors(this.errors);
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("CIEC_DINT ");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append(";");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("// replacing all instances of DINT:");
    _builder_1.append(ForteNgTestBase.VARIABLE2_NAME);
    _builder_1.append(" with ");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.newLineIfNotEmpty();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void generatedRelocatedDINTincorrectTypes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : DINT;");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : INT;");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.stAlgorithmFilter.generate(this.castAlgorithm(this.functionBlock.getAlgorithmNamed(ForteNgTestBase.ALGORITHM_NAME)), this.errors);
    ForteNgTestBase.assertErrors(this.errors);
    Assert.assertNull(generatedCode);
    ForteNgTestBase.assertErrorMessages(this.errors, "General located variables must have matching types");
  }
}
