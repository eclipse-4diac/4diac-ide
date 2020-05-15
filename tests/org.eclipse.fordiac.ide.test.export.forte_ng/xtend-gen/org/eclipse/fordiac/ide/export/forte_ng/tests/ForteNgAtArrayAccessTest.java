package org.eclipse.fordiac.ide.export.forte_ng.tests;

import java.util.Collection;
import java.util.Collections;
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
public class ForteNgAtArrayAccessTest extends ForteNgTestBasicFBTypeBase {
  protected static final boolean VALID_ACCESS = true;
  
  protected static final boolean INVALID_ACCESS = (!ForteNgAtArrayAccessTest.VALID_ACCESS);
  
  private static final String BOOLACCESS_SHORT = "";
  
  private static final String BOOLACCESS = "%X";
  
  private static final String BYTEACCESS = "%B";
  
  private static final String WORDACCESS = "%W";
  
  private static final String DWORDACCESS = "%D";
  
  private static final String VALUE_TRUE = "true";
  
  private static final String VALUE_FALSE = "false";
  
  private static final String VALUE_42 = "42";
  
  private String sourceType;
  
  private String accessType;
  
  private String accessor;
  
  private int arrayStart;
  
  private int arrayStop;
  
  private int index;
  
  private String value;
  
  private boolean isValid;
  
  @Parameterized.Parameters(name = "{index}: {0}.{2}{5}={6}")
  public static Collection<Object[]> testCases() {
    Object[] _testCase = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_1 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _indexStop_1 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _plus = (_indexStop_1 + 1);
    Object[] _testCase_2 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop), Integer.valueOf(_plus), 
      ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_3 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_4 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_2 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _indexStop_3 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _plus_1 = (_indexStop_3 + 1);
    Object[] _testCase_5 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_2), Integer.valueOf(_plus_1), ForteNgAtArrayAccessTest.VALUE_TRUE, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_6 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_7 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_4 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _indexStop_5 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _plus_2 = (_indexStop_5 + 1);
    Object[] _testCase_8 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_4), Integer.valueOf(_plus_2), 
      ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_9 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_10 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_6 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _indexStop_7 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL);
    int _plus_3 = (_indexStop_7 + 1);
    Object[] _testCase_11 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_6), Integer.valueOf(_plus_3), ForteNgAtArrayAccessTest.VALUE_FALSE, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_12 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_13 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(2), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    int _indexStop_8 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE);
    int _indexStop_9 = ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE);
    int _plus_4 = (_indexStop_9 + 1);
    Object[] _testCase_14 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_8), Integer.valueOf(_plus_4), ForteNgAtArrayAccessTest.VALUE_42, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_15 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.WORD, ForteNgAtArrayAccessTest.WORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.WORD)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_16 = ForteNgTestBase.testCase(ForteNgTestBase.DWORD, ForteNgTestBase.WORD, ForteNgAtArrayAccessTest.WORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.DWORD, ForteNgTestBase.WORD)), Integer.valueOf(2), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_17 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_18 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_10 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _indexStop_11 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _plus_5 = (_indexStop_11 + 1);
    Object[] _testCase_19 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_10), Integer.valueOf(_plus_5), 
      ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_20 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_21 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_TRUE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_12 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _indexStop_13 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _plus_6 = (_indexStop_13 + 1);
    Object[] _testCase_22 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_12), Integer.valueOf(_plus_6), ForteNgAtArrayAccessTest.VALUE_TRUE, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_23 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_24 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_14 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _indexStop_15 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _plus_7 = (_indexStop_15 + 1);
    Object[] _testCase_25 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS_SHORT, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_14), Integer.valueOf(_plus_7), 
      ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_26 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_27 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_FALSE, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_16 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _indexStop_17 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL);
    int _plus_8 = (_indexStop_17 + 1);
    Object[] _testCase_28 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BOOL, ForteNgAtArrayAccessTest.BOOLACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_16), Integer.valueOf(_plus_8), ForteNgAtArrayAccessTest.VALUE_FALSE, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_29 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    Object[] _testCase_30 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(1), Integer.valueOf(2), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    int _indexStop_18 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE);
    int _indexStop_19 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE);
    int _plus_9 = (_indexStop_19 + 1);
    Object[] _testCase_31 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.BYTE, ForteNgAtArrayAccessTest.BYTEACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_18), Integer.valueOf(_plus_9), ForteNgAtArrayAccessTest.VALUE_42, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_32 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.WORD, ForteNgAtArrayAccessTest.WORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.WORD)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_20 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.WORD);
    int _indexStop_21 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.WORD);
    int _plus_10 = (_indexStop_21 + 1);
    Object[] _testCase_33 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.WORD, ForteNgAtArrayAccessTest.WORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_20), Integer.valueOf(_plus_10), ForteNgAtArrayAccessTest.VALUE_42, 
      Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    Object[] _testCase_34 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.DWORD, ForteNgAtArrayAccessTest.DWORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.DWORD)), Integer.valueOf(0), ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.VALID_ACCESS));
    int _indexStop_22 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.DWORD);
    int _indexStop_23 = ForteNgTestBase.indexStop(ForteNgTestBase.LWORD, ForteNgTestBase.DWORD);
    int _plus_11 = (_indexStop_23 + 1);
    Object[] _testCase_35 = ForteNgTestBase.testCase(ForteNgTestBase.LWORD, ForteNgTestBase.DWORD, ForteNgAtArrayAccessTest.DWORDACCESS, Integer.valueOf(ForteNgTestBase.INDEX_START), Integer.valueOf(_indexStop_22), Integer.valueOf(_plus_11), 
      ForteNgAtArrayAccessTest.VALUE_42, Boolean.valueOf(ForteNgAtArrayAccessTest.INVALID_ACCESS));
    return Collections.<Object[]>unmodifiableList(CollectionLiterals.<Object[]>newArrayList(_testCase, _testCase_1, _testCase_2, _testCase_3, _testCase_4, _testCase_5, _testCase_6, _testCase_7, _testCase_8, _testCase_9, _testCase_10, _testCase_11, _testCase_12, _testCase_13, _testCase_14, _testCase_15, _testCase_16, _testCase_17, _testCase_18, _testCase_19, _testCase_20, _testCase_21, _testCase_22, _testCase_23, _testCase_24, _testCase_25, _testCase_26, _testCase_27, _testCase_28, _testCase_29, _testCase_30, _testCase_31, _testCase_32, _testCase_33, _testCase_34, _testCase_35));
  }
  
  public ForteNgAtArrayAccessTest(final String sourceType, final String accessType, final String accessor, final int arrayStart, final int arrayStop, final int index, final String value, final boolean isValid) {
    super();
    this.sourceType = sourceType;
    this.accessType = accessType;
    this.accessor = accessor;
    this.arrayStart = arrayStart;
    this.arrayStop = arrayStop;
    this.index = index;
    this.value = value;
    this.isValid = isValid;
  }
  
  @Test
  public void locatedArrayAtAccess() {
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
    _builder.newLine();
    _builder.newLine();
    _builder.append(ForteNgTestBase.VARIABLE2_NAME);
    _builder.append(".");
    _builder.append(this.accessor);
    _builder.append(this.index);
    _builder.append(" := ");
    _builder.append(this.value);
    _builder.append(";");
    this.getFunctionBlock().getAlgorithm().add(this.createSTAlgorithm(ForteNgTestBase.ALGORITHM_NAME, _builder.toString()));
    CharSequence generatedCode = this.generateAlgorithm(this.functionBlock, ForteNgTestBase.ALGORITHM_NAME, this.getErrors());
    if ((this.isValid == ForteNgAtArrayAccessTest.VALID_ACCESS)) {
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
      _builder_1.append(ForteNgTestBase.EXPORTED_VARIABLE2_NAME);
      _builder_1.append(".partial<CIEC_");
      _builder_1.append(this.accessType);
      _builder_1.append(",");
      _builder_1.append(this.index);
      _builder_1.append(">() = ");
      _builder_1.append(this.value);
      _builder_1.append(";");
      _builder_1.newLineIfNotEmpty();
      Assert.assertEquals(_builder_1.toString(), generatedCode.toString());
    } else {
      ForteNgTestBase.assertErrors(this.getErrors());
      Assert.assertNull(generatedCode);
      ForteNgTestBase.assertErrorMessages(this.getErrors(), "Incorrect partial access: index not within limits.");
    }
  }
}
