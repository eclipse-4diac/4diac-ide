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

import java.util.Collection;
import java.util.Collections;
import org.eclipse.fordiac.ide.export.forte_ng.tests.DatatypeConstants;
import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBase;
import org.eclipse.fordiac.ide.export.forte_ng.tests.ForteNgTestBasicFBTypeBase;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
@SuppressWarnings("all")
public class ForteNgAtArrayDeclarationTest extends ForteNgTestBasicFBTypeBase implements DatatypeConstants {
  private static final boolean VALID_DECLARATION = true;
  
  private static final boolean INVALID_DECLARATION = (!ForteNgAtArrayDeclarationTest.VALID_DECLARATION);
  
  private String sourceType;
  
  private String accessType;
  
  private int arrayStart;
  
  private int arrayStop;
  
  private boolean isValid;
  
  @Parameterized.Parameters(name = "{index}: {0}->{1}[{2}..{3}]")
  public static Collection<Object[]> testCases() {
    Object[] _testCase = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.DWORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.DWORD)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_1 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.WORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.WORD)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_2 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.BYTE)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_3 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.BOOL)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    int _indexStop = DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.DWORD);
    int _plus = (_indexStop + 1);
    Object[] _testCase_4 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.DWORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_1 = DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.WORD);
    int _plus_1 = (_indexStop_1 + 1);
    Object[] _testCase_5 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.WORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_1), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_2 = DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.BYTE);
    int _plus_2 = (_indexStop_2 + 1);
    Object[] _testCase_6 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_2), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_3 = DatatypeConstants.indexStop(DatatypeConstants.LWORD, DatatypeConstants.BOOL);
    int _plus_3 = (_indexStop_3 + 1);
    Object[] _testCase_7 = ForteNgTestBase.testCase(DatatypeConstants.LWORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_3), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_8 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.WORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.WORD)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_9 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.BYTE)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_10 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.BOOL)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    int _indexStop_4 = DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.WORD);
    int _plus_4 = (_indexStop_4 + 1);
    Object[] _testCase_11 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.WORD, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_4), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_5 = DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.BYTE);
    int _plus_5 = (_indexStop_5 + 1);
    Object[] _testCase_12 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_5), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_6 = DatatypeConstants.indexStop(DatatypeConstants.DWORD, DatatypeConstants.BOOL);
    int _plus_6 = (_indexStop_6 + 1);
    Object[] _testCase_13 = ForteNgTestBase.testCase(DatatypeConstants.DWORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_6), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_14 = ForteNgTestBase.testCase(DatatypeConstants.WORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.WORD, DatatypeConstants.BYTE)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    Object[] _testCase_15 = ForteNgTestBase.testCase(DatatypeConstants.WORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.WORD, DatatypeConstants.BOOL)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    int _indexStop_7 = DatatypeConstants.indexStop(DatatypeConstants.WORD, DatatypeConstants.BYTE);
    int _plus_7 = (_indexStop_7 + 1);
    Object[] _testCase_16 = ForteNgTestBase.testCase(DatatypeConstants.WORD, DatatypeConstants.BYTE, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_7), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    int _indexStop_8 = DatatypeConstants.indexStop(DatatypeConstants.WORD, DatatypeConstants.BOOL);
    int _plus_8 = (_indexStop_8 + 1);
    Object[] _testCase_17 = ForteNgTestBase.testCase(DatatypeConstants.WORD, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_18 = ForteNgTestBase.testCase(DatatypeConstants.BYTE, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(DatatypeConstants.indexStop(DatatypeConstants.BYTE, DatatypeConstants.BOOL)), Boolean.valueOf(ForteNgAtArrayDeclarationTest.VALID_DECLARATION));
    int _indexStop_9 = DatatypeConstants.indexStop(DatatypeConstants.BYTE, DatatypeConstants.BOOL);
    int _plus_9 = (_indexStop_9 + 1);
    Object[] _testCase_19 = ForteNgTestBase.testCase(DatatypeConstants.BYTE, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(_plus_9), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_20 = ForteNgTestBase.testCase("LINT", DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_21 = ForteNgTestBase.testCase(DatatypeConstants.DINT, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_22 = ForteNgTestBase.testCase("INT", DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_23 = ForteNgTestBase.testCase("SINT", DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    Object[] _testCase_24 = ForteNgTestBase.testCase(DatatypeConstants.REAL, DatatypeConstants.BOOL, Integer.valueOf(DatatypeConstants.INDEX_START), Integer.valueOf(8), Boolean.valueOf(ForteNgAtArrayDeclarationTest.INVALID_DECLARATION));
    return Collections.<Object[]>unmodifiableList(CollectionLiterals.<Object[]>newArrayList(_testCase, _testCase_1, _testCase_2, _testCase_3, _testCase_4, _testCase_5, _testCase_6, _testCase_7, _testCase_8, _testCase_9, _testCase_10, _testCase_11, _testCase_12, _testCase_13, _testCase_14, _testCase_15, _testCase_16, _testCase_17, _testCase_18, _testCase_19, _testCase_20, _testCase_21, _testCase_22, _testCase_23, _testCase_24));
  }
  
  public ForteNgAtArrayDeclarationTest(final String sourceType, final String accessType, final int arrayStart, final int arrayStop, final boolean isValid) {
    super();
    this.sourceType = sourceType;
    this.accessType = accessType;
    this.arrayStart = arrayStart;
    this.arrayStop = arrayStop;
    this.isValid = isValid;
  }
  
  @Test
  public void LocatedArrayDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ");
    _builder.append(this.sourceType, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(ForteNgTestBase.VARIABLE2_NAME, "  ");
    _builder.append(" AT ");
    _builder.append(ForteNgTestBase.VARIABLE_NAME, "  ");
    _builder.append(" : ARRAY [");
    _builder.append(this.arrayStart, "  ");
    _builder.append("..");
    _builder.append(this.arrayStop, "  ");
    _builder.append("] OF ");
    _builder.append(this.accessType, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("END_VAR");
    this.functionBlock.getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.generateAlgorithm(this.functionBlock, ForteNgTestBase.ALGORITHM_NAME, this.getErrors());
    if ((this.isValid == ForteNgAtArrayDeclarationTest.VALID_DECLARATION)) {
      ForteNgTestBase.assertNoErrors(this.getErrors());
      Assert.assertNotNull(generatedCode);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("CIEC_");
      _builder_1.append(this.sourceType);
      _builder_1.append(" ");
      _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE_NAME);
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("ARRAY_AT<CIEC_");
      _builder_1.append(this.accessType);
      _builder_1.append(", CIEC_");
      _builder_1.append(this.sourceType);
      _builder_1.append(", ");
      _builder_1.append(this.arrayStart);
      _builder_1.append(", ");
      _builder_1.append(this.arrayStop);
      _builder_1.append("> ");
      _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE2_NAME);
      _builder_1.append("(");
      _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE_NAME);
      _builder_1.append(");");
      _builder_1.newLineIfNotEmpty();
      Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
    } else {
      ForteNgTestBase.assertErrors(this.getErrors());
      Assert.assertNull(generatedCode);
    }
  }
}
