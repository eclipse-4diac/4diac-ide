package org.eclipse.fordiac.ide.export.forte_ng.tests;

import org.eclipse.fordiac.ide.export.forte_ng.tests.DatatypeConstants;
import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class ForteNgXtend extends ForteNgTestBase implements DatatypeConstants {
  @Test
  public void emptyExpression() {
    CharSequence generatedCode = this.generateExpression(this.getFunctionBlock(), "", this.getErrors());
    ForteNgTestBase.assertErrors(this.getErrors());
    Assert.assertNull(generatedCode);
  }
  
  @Test
  public void assignmentExpression() {
    this.getFunctionBlock().getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, DatatypeConstants.BOOL));
    BasicFBType _functionBlock = this.getFunctionBlock();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" := 1");
    CharSequence generatedCode = this.generateExpression(_functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertErrors(this.getErrors());
    Assert.assertNull(generatedCode);
  }
  
  @Test
  public void simpleAssignmentAlgorithm() {
    this.getFunctionBlock().getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, DatatypeConstants.BOOL));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" := 1;");
    this.getFunctionBlock().getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.generateAlgorithm(this.getFunctionBlock(), ForteNgTestBase.ALGORITHM_NAME, this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append("() = 1;");
    _builder_1.newLineIfNotEmpty();
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void functionSQRTExpression() {
    this.getFunctionBlock().getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, DatatypeConstants.REAL));
    BasicFBType _functionBlock = this.getFunctionBlock();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("SQRT(");
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(")");
    CharSequence generatedCode = this.generateExpression(_functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("SQRT(");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append("())");
    Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
  }
  
  @Test
  public void powerExpression() {
    this.getFunctionBlock().getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE_NAME, DatatypeConstants.REAL));
    this.getFunctionBlock().getInterfaceList().getInputVars().add(this.createVarDeclaration(ForteNgTestBase.VARIABLE2_NAME, DatatypeConstants.REAL));
    BasicFBType _functionBlock = this.getFunctionBlock();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteNgTestBase.VARIABLE_NAME);
    _builder.append(" ** ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME);
    CharSequence generatedCode = this.generateExpression(_functionBlock, _builder.toString(), this.getErrors());
    ForteNgTestBase.assertNoErrors(this.getErrors());
    Assert.assertNotNull(generatedCode);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("EXPT(");
    _builder_1.append(ForteNgTestBase.VARIABLE_NAME);
    _builder_1.append("(), ");
    _builder_1.append(ForteNgTestBase.VARIABLE2_NAME);
    _builder_1.append("())");
    Assert.assertEquals(_builder_1.toString(), 
      generatedCode.toString());
  }
}
