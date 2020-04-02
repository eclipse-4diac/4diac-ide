/**
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Ernst Blecha - add multibit partial access
 */
package org.eclipse.fordiac.ide.export.forte_ng.st;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser;
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ArrayVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Call;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Constant;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ContinueStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseIfClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ExitStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocatedVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PartialAccess;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ReturnStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StringLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class STAlgorithmFilter {
  private static final URI SYNTHETIC_FB_URI = URI.createFileURI("__synthetic.xtextfbt");
  
  private static final URI SYNTHETIC_ST_URI = URI.createFileURI("__synthetic.st");
  
  private static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(STAlgorithmFilter.SYNTHETIC_ST_URI);
  
  public CharSequence generate(final STAlgorithm alg, final List<String> errors) {
    try {
      final ResourceSet resourceSet = STAlgorithmFilter.SERVICE_PROVIDER.<ResourceSet>get(ResourceSet.class);
      final Resource fbResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_FB_URI);
      final EObject fbCopy = EcoreUtil.<EObject>copy(EcoreUtil.getRootContainer(alg));
      fbResource.getContents().add(fbCopy);
      Resource _createResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_ST_URI);
      final XtextResource resource = ((XtextResource) _createResource);
      String _text = alg.getText();
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(_text);
      Pair<String, Boolean> _mappedTo = Pair.<String, Boolean>of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      resource.load(_lazyStringInputStream, Collections.<String, Boolean>unmodifiableMap(CollectionLiterals.<String, Boolean>newHashMap(_mappedTo)));
      final IParseResult parseResult = resource.getParseResult();
      final IResourceValidator validator = resource.getResourceServiceProvider().getResourceValidator();
      final List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
      boolean _isEmpty = issues.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final Function1<Issue, String> _function = (Issue it) -> {
          String _name = alg.getName();
          String _plus = (_name + ", Line ");
          String _string = Long.toString((it.getLineNumber()).intValue());
          String _plus_1 = (_plus + _string);
          String _plus_2 = (_plus_1 + ": ");
          String _message = it.getMessage();
          return (_plus_2 + _message);
        };
        errors.addAll(ListExtensions.<Issue, String>map(issues, _function));
        return null;
      }
      EObject _rootASTElement = parseResult.getRootASTElement();
      final StructuredTextAlgorithm stalg = ((StructuredTextAlgorithm) _rootASTElement);
      return this.generateStructuredTextAlgorithm(stalg);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public CharSequence generate(final String expression, final BasicFBType fb, final List<String> errors) {
    try {
      final ResourceSet resourceSet = STAlgorithmFilter.SERVICE_PROVIDER.<ResourceSet>get(ResourceSet.class);
      final Resource fbResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_FB_URI);
      final BasicFBType fbCopy = EcoreUtil.<BasicFBType>copy(fb);
      fbResource.getContents().add(fbCopy);
      Resource _createResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_ST_URI);
      final XtextResource resource = ((XtextResource) _createResource);
      IParser _parser = resource.getParser();
      final StructuredTextParser parser = ((StructuredTextParser) _parser);
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(expression);
      ParserRule _expressionRule = parser.getGrammarAccess().getExpressionRule();
      Pair<String, ParserRule> _mappedTo = Pair.<String, ParserRule>of(StructuredTextResource.OPTION_PARSER_RULE, _expressionRule);
      resource.load(_lazyStringInputStream, Collections.<String, ParserRule>unmodifiableMap(CollectionLiterals.<String, ParserRule>newHashMap(_mappedTo)));
      final IParseResult parseResult = resource.getParseResult();
      final IResourceValidator validator = resource.getResourceServiceProvider().getResourceValidator();
      final List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
      boolean _isEmpty = issues.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final Function1<Issue, String> _function = (Issue it) -> {
          String _string = Long.toString((it.getLineNumber()).intValue());
          String _plus = ("Line " + _string);
          String _plus_1 = (_plus + ": ");
          String _message = it.getMessage();
          return (_plus_1 + _message);
        };
        errors.addAll(ListExtensions.<Issue, String>map(issues, _function));
        return null;
      }
      EObject _rootASTElement = parseResult.getRootASTElement();
      final Expression expr = ((Expression) _rootASTElement);
      return this.generateExpression(expr);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected CharSequence generateStructuredTextAlgorithm(final StructuredTextAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateLocalVariables = this.generateLocalVariables(alg.getLocalVariables());
    _builder.append(_generateLocalVariables);
    _builder.newLineIfNotEmpty();
    CharSequence _generateStatementList = this.generateStatementList(alg.getStatements());
    _builder.append(_generateStatementList);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private int BitSize(final String str) {
    int _switchResult = (int) 0;
    boolean _matched = false;
    boolean _equals = str.equals("LWORD");
    if (_equals) {
      _matched=true;
      _switchResult = 64;
    }
    if (!_matched) {
      boolean _equals_1 = str.equals("DWORD");
      if (_equals_1) {
        _matched=true;
        _switchResult = 32;
      }
    }
    if (!_matched) {
      boolean _equals_2 = str.equals("WORD");
      if (_equals_2) {
        _matched=true;
        _switchResult = 16;
      }
    }
    if (!_matched) {
      boolean _equals_3 = str.equals("BYTE");
      if (_equals_3) {
        _matched=true;
        _switchResult = 8;
      }
    }
    if (!_matched) {
      boolean _equals_4 = str.equals("BOOL");
      if (_equals_4) {
        _matched=true;
        _switchResult = 1;
      }
    }
    if (!_matched) {
      _switchResult = 0;
    }
    return _switchResult;
  }
  
  protected CharSequence generateArrayDecl(final LocatedVariable variable) {
    CharSequence _xblockexpression = null;
    {
      final Variable l = variable.getLocation();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (l instanceof PrimaryVariable) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          if (((this.BitSize(variable.getType().getName()) > 0) && (this.BitSize(((PrimaryVariable)l).getVar().getType().getName()) > 0))) {
            {
              int _BitSize = this.BitSize(((PrimaryVariable)l).getVar().getType().getName());
              int _BitSize_1 = this.BitSize(variable.getType().getName());
              boolean _greaterThan = (_BitSize > _BitSize_1);
              if (_greaterThan) {
                _builder.append("ARRAY_AT<CIEC_");
                String _name = variable.getType().getName();
                _builder.append(_name);
                _builder.append(", CIEC_");
                String _name_1 = ((PrimaryVariable)l).getVar().getType().getName();
                _builder.append(_name_1);
                _builder.append(", 0, ");
                int _arraySize = variable.getArraySize();
                int _minus = (_arraySize - 1);
                _builder.append(_minus);
                _builder.append("> ");
                String _name_2 = variable.getName();
                _builder.append(_name_2);
                _builder.append("(");
                String _name_3 = ((PrimaryVariable)l).getVar().getName();
                _builder.append(_name_3);
                _builder.append(");");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("#error Accessing CIEC_");
                String _name_4 = ((PrimaryVariable)l).getVar().getType().getName();
                _builder.append(_name_4);
                _builder.append(" via CIEC_");
                String _name_5 = variable.getType().getName();
                _builder.append(_name_5);
                _builder.append(" would result in undefined behaviour");
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            _builder.append("#error Piecewise access is supported only for types with defined bit-representation (e.g. not CIEC_");
            String _name_6 = ((PrimaryVariable)l).getVar().getType().getName();
            _builder.append(_name_6);
            _builder.append(" via CIEC_");
            String _name_7 = variable.getType().getName();
            _builder.append(_name_7);
            _builder.append(") ");
            _builder.newLineIfNotEmpty();
          }
        }
        _switchResult = _builder;
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#error unhandled located array");
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  protected CharSequence generateArrayDecl(final LocalVariable variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CIEC_");
    String _name = variable.getType().getName();
    _builder.append(_name);
    _builder.append(" ");
    String _name_1 = variable.getName();
    _builder.append(_name_1);
    _builder.append("[");
    int _arraySize = variable.getArraySize();
    _builder.append(_arraySize);
    _builder.append("]");
    CharSequence _generateLocalVariableInitializer = this.generateLocalVariableInitializer(variable);
    _builder.append(_generateLocalVariableInitializer);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateVariableDecl(final LocatedVariable variable) {
    CharSequence _xblockexpression = null;
    {
      final Variable l = variable.getLocation();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (l instanceof PrimaryVariable) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// replacing all instances of ");
        String _extractTypeInformation = this.extractTypeInformation(variable);
        _builder.append(_extractTypeInformation);
        _builder.append(":");
        String _name = variable.getName();
        _builder.append(_name);
        _builder.append(" with ");
        CharSequence _generateVarAccess = this.generateVarAccess(variable);
        _builder.append(_generateVarAccess);
        _switchResult = _builder;
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#error located variable of unhandled type");
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  protected CharSequence generateVariableDecl(final LocalVariable variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CIEC_");
    String _name = variable.getType().getName();
    _builder.append(_name);
    _builder.append(" ");
    String _name_1 = variable.getName();
    _builder.append(_name_1);
    CharSequence _generateLocalVariableInitializer = this.generateLocalVariableInitializer(variable);
    _builder.append(_generateLocalVariableInitializer);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateLocalVariables(final List<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final VarDeclaration variable : variables) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (variable instanceof LocalVariable) {
          boolean _isArray = ((LocalVariable)variable).isArray();
          boolean _not = (!_isArray);
          if (_not) {
            _matched=true;
            _switchResult = this.generateVariableDecl(((LocalVariable)variable));
          }
        }
        if (!_matched) {
          if (variable instanceof LocalVariable) {
            boolean _isArray = ((LocalVariable)variable).isArray();
            if (_isArray) {
              _matched=true;
              _switchResult = this.generateArrayDecl(((LocalVariable)variable));
            }
          }
        }
        if (!_matched) {
          if (variable instanceof LocatedVariable) {
            if (((null != ((LocatedVariable)variable).getLocation()) && (!((LocatedVariable)variable).isArray()))) {
              _matched=true;
              _switchResult = this.generateVariableDecl(((LocatedVariable)variable));
            }
          }
        }
        if (!_matched) {
          if (variable instanceof LocatedVariable) {
            if (((null != ((LocatedVariable)variable).getLocation()) && ((LocatedVariable)variable).isArray())) {
              _matched=true;
              _switchResult = this.generateArrayDecl(((LocatedVariable)variable));
            }
          }
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateLocalVariableInitializer(final VarDeclaration variable) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (variable instanceof LocalVariable) {
      Constant _initialValue = ((LocalVariable)variable).getInitialValue();
      boolean _tripleNotEquals = (_initialValue != null);
      if (_tripleNotEquals) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("= ");
        CharSequence _generateExpression = this.generateExpression(((LocalVariable)variable).getInitialValue());
        _builder.append(_generateExpression, " ");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      _switchResult = "";
    }
    return _switchResult;
  }
  
  protected CharSequence generateStatementList(final StatementList list) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Statement> _statements = list.getStatements();
      for(final Statement stmt : _statements) {
        CharSequence _generateStatement = this.generateStatement(stmt);
        _builder.append(_generateStatement);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateStatement(final Statement stmt) {
    EClass _eClass = stmt.eClass();
    String _plus = (_eClass + " not supported");
    throw new UnsupportedOperationException(_plus);
  }
  
  protected CharSequence _generateStatement(final AssignmentStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateExpression = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression);
    _builder.append(" = ");
    CharSequence _generateExpression_1 = this.generateExpression(stmt.getExpression());
    _builder.append(_generateExpression_1);
    _builder.append(";");
    return _builder;
  }
  
  protected CharSequence _generateStatement(final Call stmt) {
    return this.generateExpression(stmt);
  }
  
  protected CharSequence _generateStatement(final ReturnStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("return;");
    return _builder;
  }
  
  protected CharSequence _generateStatement(final IfStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if(");
    CharSequence _generateExpression = this.generateExpression(stmt.getExpression());
    _builder.append(_generateExpression);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateStatementList = this.generateStatementList(stmt.getStatments());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    {
      EList<ElseIfClause> _elseif = stmt.getElseif();
      for(final ElseIfClause elseif : _elseif) {
        _builder.append("else if(");
        CharSequence _generateExpression_1 = this.generateExpression(elseif.getExpression());
        _builder.append(_generateExpression_1);
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _generateStatementList_1 = this.generateStatementList(elseif.getStatements());
        _builder.append(_generateStatementList_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    {
      ElseClause _else = stmt.getElse();
      boolean _tripleNotEquals = (_else != null);
      if (_tripleNotEquals) {
        _builder.append("else {");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _generateStatementList_2 = this.generateStatementList(stmt.getElse().getStatements());
        _builder.append(_generateStatementList_2, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateStatement(final CaseStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("switch (");
    CharSequence _generateExpression = this.generateExpression(stmt.getExpression());
    _builder.append(_generateExpression);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      EList<CaseClause> _case = stmt.getCase();
      for(final CaseClause clause : _case) {
        CharSequence _generateCaseClause = this.generateCaseClause(clause);
        _builder.append(_generateCaseClause, "\t");
      }
    }
    _builder.newLineIfNotEmpty();
    {
      ElseClause _else = stmt.getElse();
      boolean _tripleNotEquals = (_else != null);
      if (_tripleNotEquals) {
        _builder.append("\t");
        _builder.append("default:");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _generateStatementList = this.generateStatementList(stmt.getElse().getStatements());
        _builder.append(_generateStatementList, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateCaseClause(final CaseClause clause) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case ");
    {
      EList<Constant> _case = clause.getCase();
      boolean _hasElements = false;
      for(final Constant value : _case) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" case ", "");
        }
        CharSequence _generateExpression = this.generateExpression(value);
        _builder.append(_generateExpression);
        _builder.append(":");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateStatementList = this.generateStatementList(clause.getStatements());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("break;");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateStatement(final ExitStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("break;");
    return _builder;
  }
  
  protected CharSequence _generateStatement(final ContinueStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("continue;");
    return _builder;
  }
  
  protected CharSequence _generateStatement(final ForStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5");
    _builder.newLine();
    _builder.append("auto by = ");
    {
      Expression _by = stmt.getBy();
      boolean _tripleNotEquals = (_by != null);
      if (_tripleNotEquals) {
        CharSequence _generateExpression = this.generateExpression(stmt.getBy());
        _builder.append(_generateExpression);
      } else {
        _builder.append("1");
      }
    }
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("auto to = ");
    CharSequence _generateExpression_1 = this.generateExpression(stmt.getTo());
    _builder.append(_generateExpression_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("for(");
    CharSequence _generateExpression_2 = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression_2);
    _builder.append(" = ");
    CharSequence _generateExpression_3 = this.generateExpression(stmt.getFrom());
    _builder.append(_generateExpression_3);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("(by >  0 && ");
    CharSequence _generateExpression_4 = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression_4, "    ");
    _builder.append(" <= to) ||");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("(by <= 0 && ");
    CharSequence _generateExpression_5 = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression_5, "    ");
    _builder.append(" >= to);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    CharSequence _generateExpression_6 = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression_6, "    ");
    _builder.append(" = ");
    CharSequence _generateExpression_7 = this.generateExpression(stmt.getVariable());
    _builder.append(_generateExpression_7, "    ");
    _builder.append(" + by){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateStatementList = this.generateStatementList(stmt.getStatements());
    _builder.append(_generateStatementList, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateStatement(final WhileStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while(");
    CharSequence _generateExpression = this.generateExpression(stmt.getExpression());
    _builder.append(_generateExpression);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateStatementList = this.generateStatementList(stmt.getStatements());
    _builder.append(_generateStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateStatement(final RepeatStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("do {");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateStatementList = this.generateStatementList(stmt.getStatements());
    _builder.append(_generateStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("} while(!((");
    CharSequence _generateExpression = this.generateExpression(stmt.getExpression());
    _builder.append(_generateExpression);
    _builder.append(")));");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateExpression(final Expression expr) {
    EClass _eClass = expr.eClass();
    String _plus = (_eClass + " not supported");
    throw new UnsupportedOperationException(_plus);
  }
  
  protected CharSequence _generateExpression(final BinaryExpression expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _generateExpression = this.generateExpression(expr.getLeft());
    _builder.append(_generateExpression);
    _builder.append(" ");
    CharSequence _generateBinaryOperator = this.generateBinaryOperator(expr.getOperator());
    _builder.append(_generateBinaryOperator);
    _builder.append(" ");
    CharSequence _generateExpression_1 = this.generateExpression(expr.getRight());
    _builder.append(_generateExpression_1);
    _builder.append(")");
    return _builder;
  }
  
  protected CharSequence generateBinaryOperator(final BinaryOperator op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case OR:
          _switchResult = "||";
          break;
        case XOR:
          _switchResult = "^";
          break;
        case AND:
          _switchResult = "&&";
          break;
        case EQ:
          _switchResult = "==";
          break;
        case NE:
          _switchResult = "!=";
          break;
        case LT:
          _switchResult = "<";
          break;
        case LE:
          _switchResult = "<=";
          break;
        case GT:
          _switchResult = ">";
          break;
        case GE:
          _switchResult = ">=";
          break;
        case ADD:
          _switchResult = "+";
          break;
        case SUB:
          _switchResult = "-";
          break;
        case MUL:
          _switchResult = "*";
          break;
        case DIV:
          _switchResult = "/";
          break;
        case MOD:
          _switchResult = "%";
          break;
        default:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("The operator ");
          _builder.append(op);
          _builder.append(" is not supported");
          throw new UnsupportedOperationException(_builder.toString());
      }
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The operator ");
      _builder.append(op);
      _builder.append(" is not supported");
      throw new UnsupportedOperationException(_builder.toString());
    }
    return _switchResult;
  }
  
  protected CharSequence _generateExpression(final UnaryExpression expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _generateUnaryOperator = this.generateUnaryOperator(expr.getOperator());
    _builder.append(_generateUnaryOperator);
    _builder.append(" ");
    CharSequence _generateExpression = this.generateExpression(expr.getExpression());
    _builder.append(_generateExpression);
    _builder.append(")");
    return _builder;
  }
  
  protected CharSequence generateUnaryOperator(final UnaryOperator op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case MINUS:
          _switchResult = "-";
          break;
        case PLUS:
          _switchResult = "+";
          break;
        case NOT:
          _switchResult = "!";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  protected CharSequence _generateExpression(final BoolLiteral expr) {
    return Boolean.toString(expr.isValue());
  }
  
  protected CharSequence _generateExpression(final IntLiteral expr) {
    return Long.toString(expr.getValue());
  }
  
  protected CharSequence _generateExpression(final RealLiteral expr) {
    return Double.toString(expr.getValue());
  }
  
  protected CharSequence _generateExpression(final StringLiteral expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _convertToJavaString = Strings.convertToJavaString(expr.getValue());
    _builder.append(_convertToJavaString);
    _builder.append("\"");
    return _builder;
  }
  
  protected CharSequence _generateExpression(final ArrayVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateExpression = this.generateExpression(expr.getArray());
    _builder.append(_generateExpression);
    {
      EList<Expression> _index = expr.getIndex();
      boolean _hasElements = false;
      for(final Expression index : _index) {
        if (!_hasElements) {
          _hasElements = true;
          _builder.append("[");
        } else {
          _builder.appendImmediate("][", "");
        }
        CharSequence _generateExpression_1 = this.generateExpression(index);
        _builder.append(_generateExpression_1);
      }
      if (_hasElements) {
        _builder.append("]");
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateExpression(final AdapterVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = expr.getAdapter().getName();
    _builder.append(_name);
    _builder.append("().");
    String _name_1 = expr.getVar().getName();
    _builder.append(_name_1);
    _builder.append("()");
    CharSequence _generateBitaccess = this.generateBitaccess(expr);
    _builder.append(_generateBitaccess);
    return _builder;
  }
  
  protected CharSequence _generateExpression(final PrimaryVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateVarAccess = this.generateVarAccess(expr.getVar());
    _builder.append(_generateVarAccess);
    CharSequence _generateBitaccess = this.generateBitaccess(expr);
    _builder.append(_generateBitaccess);
    return _builder;
  }
  
  protected CharSequence _generateVarAccess(final LocalVariable variable) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = variable.getName();
    _builder.append(_name);
    return _builder;
  }
  
  protected CharSequence _generateVarAccess(final VarDeclaration variable) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = variable.getName();
    _builder.append(_name);
    _builder.append("()");
    return _builder;
  }
  
  protected CharSequence _generateVarAccess(final LocatedVariable variable) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isArray = variable.isArray();
      if (_isArray) {
        String _name = variable.getName();
        _builder.append(_name);
      } else {
        CharSequence _generateExpression = this.generateExpression(variable.getLocation());
        _builder.append(_generateExpression);
        CharSequence _generateBitaccess = this.generateBitaccess(this.extractTypeInformation(variable.getLocation()), this.extractTypeInformation(variable), 0);
        _builder.append(_generateBitaccess);
      }
    }
    return _builder;
  }
  
  protected CharSequence generateBitaccess(final AdapterVariable variable) {
    CharSequence _xifexpression = null;
    PartialAccess _part = variable.getPart();
    boolean _tripleNotEquals = (null != _part);
    if (_tripleNotEquals) {
      _xifexpression = this.generateBitaccess(variable.getVar().getType().getName(), this.extractTypeInformation(variable), variable.getPart().getIndex());
    }
    return _xifexpression;
  }
  
  protected CharSequence generateBitaccess(final PrimaryVariable variable) {
    CharSequence _xifexpression = null;
    PartialAccess _part = variable.getPart();
    boolean _tripleNotEquals = (null != _part);
    if (_tripleNotEquals) {
      _xifexpression = this.generateBitaccess(variable.getVar().getType().getName(), this.extractTypeInformation(variable), variable.getPart().getIndex());
    }
    return _xifexpression;
  }
  
  protected CharSequence generateBitaccess(final String DataType, final String AccessorType, final int Index) {
    CharSequence _xifexpression = null;
    if (((this.BitSize(AccessorType) > 0) && (this.BitSize(DataType) > this.BitSize(AccessorType)))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(".partial<CIEC_");
      _builder.append(AccessorType);
      _builder.append(",");
      String _string = Long.toString(Index);
      _builder.append(_string);
      _builder.append(">()");
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      int _BitSize = this.BitSize(DataType);
      int _BitSize_1 = this.BitSize(AccessorType);
      boolean _equals = (_BitSize == _BitSize_1);
      if (_equals) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _xifexpression_1 = _builder_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  private String _extractTypeInformation(final PartialAccess part, final String DataType) {
    String _xifexpression = null;
    if ((null != part)) {
      String _xifexpression_1 = null;
      boolean _isBitaccess = part.isBitaccess();
      if (_isBitaccess) {
        _xifexpression_1 = "BOOL";
      } else {
        String _xifexpression_2 = null;
        boolean _isByteaccess = part.isByteaccess();
        if (_isByteaccess) {
          _xifexpression_2 = "BYTE";
        } else {
          String _xifexpression_3 = null;
          boolean _isWordaccess = part.isWordaccess();
          if (_isWordaccess) {
            _xifexpression_3 = "WORD";
          } else {
            String _xifexpression_4 = null;
            boolean _isDwordaccess = part.isDwordaccess();
            if (_isDwordaccess) {
              _xifexpression_4 = "DWORD";
            } else {
              _xifexpression_4 = "";
            }
            _xifexpression_3 = _xifexpression_4;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    } else {
      _xifexpression = DataType;
    }
    return _xifexpression;
  }
  
  private String _extractTypeInformation(final PrimaryVariable variable, final String DataType) {
    String _xifexpression = null;
    PartialAccess _part = variable.getPart();
    boolean _tripleNotEquals = (null != _part);
    if (_tripleNotEquals) {
      _xifexpression = this.extractTypeInformation(variable.getPart(), DataType);
    } else {
      _xifexpression = DataType;
    }
    return _xifexpression;
  }
  
  protected String _extractTypeInformation(final PrimaryVariable variable) {
    return this.extractTypeInformation(variable, this.extractTypeInformation(variable.getVar()));
  }
  
  protected String _extractTypeInformation(final LocalVariable variable) {
    return variable.getType().getName();
  }
  
  protected String _extractTypeInformation(final VarDeclaration variable) {
    return variable.getType().getName();
  }
  
  protected CharSequence generateStatement(final Statement stmt) {
    if (stmt instanceof AssignmentStatement) {
      return _generateStatement((AssignmentStatement)stmt);
    } else if (stmt instanceof Call) {
      return _generateStatement((Call)stmt);
    } else if (stmt instanceof CaseStatement) {
      return _generateStatement((CaseStatement)stmt);
    } else if (stmt instanceof ContinueStatement) {
      return _generateStatement((ContinueStatement)stmt);
    } else if (stmt instanceof ExitStatement) {
      return _generateStatement((ExitStatement)stmt);
    } else if (stmt instanceof ForStatement) {
      return _generateStatement((ForStatement)stmt);
    } else if (stmt instanceof IfStatement) {
      return _generateStatement((IfStatement)stmt);
    } else if (stmt instanceof RepeatStatement) {
      return _generateStatement((RepeatStatement)stmt);
    } else if (stmt instanceof ReturnStatement) {
      return _generateStatement((ReturnStatement)stmt);
    } else if (stmt instanceof WhileStatement) {
      return _generateStatement((WhileStatement)stmt);
    } else if (stmt != null) {
      return _generateStatement(stmt);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(stmt).toString());
    }
  }
  
  protected CharSequence generateExpression(final Expression expr) {
    if (expr instanceof IntLiteral) {
      return _generateExpression((IntLiteral)expr);
    } else if (expr instanceof RealLiteral) {
      return _generateExpression((RealLiteral)expr);
    } else if (expr instanceof AdapterVariable) {
      return _generateExpression((AdapterVariable)expr);
    } else if (expr instanceof ArrayVariable) {
      return _generateExpression((ArrayVariable)expr);
    } else if (expr instanceof BoolLiteral) {
      return _generateExpression((BoolLiteral)expr);
    } else if (expr instanceof PrimaryVariable) {
      return _generateExpression((PrimaryVariable)expr);
    } else if (expr instanceof StringLiteral) {
      return _generateExpression((StringLiteral)expr);
    } else if (expr instanceof BinaryExpression) {
      return _generateExpression((BinaryExpression)expr);
    } else if (expr instanceof UnaryExpression) {
      return _generateExpression((UnaryExpression)expr);
    } else if (expr != null) {
      return _generateExpression(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
  
  protected CharSequence generateVarAccess(final VarDeclaration variable) {
    if (variable instanceof LocalVariable) {
      return _generateVarAccess((LocalVariable)variable);
    } else if (variable instanceof LocatedVariable) {
      return _generateVarAccess((LocatedVariable)variable);
    } else if (variable != null) {
      return _generateVarAccess(variable);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable).toString());
    }
  }
  
  private String extractTypeInformation(final EObject variable, final String DataType) {
    if (variable instanceof PrimaryVariable) {
      return _extractTypeInformation((PrimaryVariable)variable, DataType);
    } else if (variable instanceof PartialAccess) {
      return _extractTypeInformation((PartialAccess)variable, DataType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable, DataType).toString());
    }
  }
  
  protected String extractTypeInformation(final EObject variable) {
    if (variable instanceof LocalVariable) {
      return _extractTypeInformation((LocalVariable)variable);
    } else if (variable instanceof VarDeclaration) {
      return _extractTypeInformation((VarDeclaration)variable);
    } else if (variable instanceof PrimaryVariable) {
      return _extractTypeInformation((PrimaryVariable)variable);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable).toString());
    }
  }
}
