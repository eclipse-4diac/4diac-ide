package org.eclipse.fordiac.ide.export.forte_ng.tests;

import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBase;
import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBasicFBTypeBase;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class ForteNgTest extends ForteNgTestBasicFBTypeBase {
  @Test
  public void emptyExpression() {
    CharSequence generatedCode = this.generateExpression(this.functionBlock, "", this.getErrors());
    ForteNgTestBase.assertErrors(this.getErrors());
    Assert.assertNull(generatedCode);
  }
  
  @Test
  public void assignmentExpression() {
    this.functionBlock.getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, ForteNgTestBase.BOOL));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" := 1");
    CharSequence generatedCode = this.generateExpression(this.functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertErrors(this.getErrors());
    Assert.assertNull(generatedCode);
  }
  
  @Test
  public void simpleAssignmentAlgorithm() {
    this.functionBlock.getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, ForteNgTestBase.BOOL));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" := 1;");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.generateAlgorithm(this.functionBlock, ForteNgTestBase.ALGORITHM_NAME, this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE_NAME);
    _builder_1.append("() = 1;");
    _builder_1.newLineIfNotEmpty();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void functionSQRTExpression() {
    this.functionBlock.getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, ForteNgTestBase.REAL));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("SQRT(");
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(")");
    CharSequence generatedCode = this.generateExpression(this.functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    String _addExportPrefix = ForteNgTestBase.addExportPrefix("SQRT");
    _builder_1.append(_addExportPrefix);
    _builder_1.append("(");
    _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE_NAME);
    _builder_1.append("())");
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void powerExpression() {
    this.functionBlock.getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, ForteNgTestBase.REAL));
    this.functionBlock.getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE2_NAME, ForteNgTestBase.REAL));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" ** ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME);
    CharSequence generatedCode = this.generateExpression(this.functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    String _addExportPrefix = ForteNgTestBase.addExportPrefix("EXPT");
    _builder_1.append(_addExportPrefix);
    _builder_1.append("(");
    _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE_NAME);
    _builder_1.append("(), ");
    _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE2_NAME);
    _builder_1.append("())");
    Assert.assertEquals(_builder_1.toString(), 
      generatedCode.toString());
  }
}
