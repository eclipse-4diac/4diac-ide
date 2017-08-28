/**
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Jobst, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_lua.filter;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser;
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource;
import org.eclipse.fordiac.ide.model.structuredtext.services.StructuredTextGrammarAccess;
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
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class STAlgorithmFilter {
  private final static URI SYNTHETIC_FB_URI = URI.createFileURI("__synthetic.xtextfbt");
  
  private final static URI SYNTHETIC_ST_URI = URI.createFileURI("__synthetic.st");
  
  private final static IResourceServiceProvider SERVICE_PRIVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(STAlgorithmFilter.SYNTHETIC_ST_URI);
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<String> errors = new ArrayList<String>();
  
  public String lua(final STAlgorithm alg) {
    try {
      final ResourceSet resourceSet = STAlgorithmFilter.SERVICE_PRIVIDER.<ResourceSet>get(ResourceSet.class);
      final Resource fbResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_FB_URI);
      EObject _rootContainer = EcoreUtil.getRootContainer(alg);
      final EObject fbCopy = EcoreUtil.<EObject>copy(_rootContainer);
      EList<EObject> _contents = fbResource.getContents();
      _contents.add(fbCopy);
      Resource _createResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_ST_URI);
      final XtextResource resource = ((XtextResource) _createResource);
      String _text = alg.getText();
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(_text);
      Pair<String, Boolean> _mappedTo = Pair.<String, Boolean>of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      resource.load(_lazyStringInputStream, Collections.<String, Boolean>unmodifiableMap(CollectionLiterals.<String, Boolean>newHashMap(_mappedTo)));
      final IParseResult parseResult = resource.getParseResult();
      boolean _hasSyntaxErrors = parseResult.hasSyntaxErrors();
      if (_hasSyntaxErrors) {
        Iterable<INode> _syntaxErrors = parseResult.getSyntaxErrors();
        final Function1<INode, String> _function = (INode it) -> {
          SyntaxErrorMessage _syntaxErrorMessage = it.getSyntaxErrorMessage();
          return _syntaxErrorMessage.getMessage();
        };
        Iterable<String> _map = IterableExtensions.<INode, String>map(_syntaxErrors, _function);
        Iterables.<String>addAll(this.errors, _map);
        return null;
      }
      EObject _rootASTElement = parseResult.getRootASTElement();
      final StructuredTextAlgorithm stalg = ((StructuredTextAlgorithm) _rootASTElement);
      TreeIterator<Object> _allProperContents = EcoreUtil.<Object>getAllProperContents(stalg, true);
      Iterator<AdapterVariable> _filter = Iterators.<AdapterVariable>filter(_allProperContents, AdapterVariable.class);
      final Set<AdapterVariable> usedAdapterVariables = IteratorExtensions.<AdapterVariable>toSet(_filter);
      TreeIterator<Object> _allProperContents_1 = EcoreUtil.<Object>getAllProperContents(stalg, true);
      Iterator<PrimaryVariable> _filter_1 = Iterators.<PrimaryVariable>filter(_allProperContents_1, PrimaryVariable.class);
      final Function1<PrimaryVariable, VarDeclaration> _function_1 = (PrimaryVariable it) -> {
        return it.getVar();
      };
      Iterator<VarDeclaration> _map_1 = IteratorExtensions.<PrimaryVariable, VarDeclaration>map(_filter_1, _function_1);
      final Function1<VarDeclaration, Boolean> _function_2 = (VarDeclaration it) -> {
        EObject _rootContainer_1 = EcoreUtil.getRootContainer(it);
        return Boolean.valueOf((_rootContainer_1 instanceof FBType));
      };
      Iterator<VarDeclaration> _filter_2 = IteratorExtensions.<VarDeclaration>filter(_map_1, _function_2);
      final Set<VarDeclaration> usedFBVariables = IteratorExtensions.<VarDeclaration>toSet(_filter_2);
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _luaFBVariablesPrefix = LuaConstants.luaFBVariablesPrefix(usedFBVariables);
      _builder.append(_luaFBVariablesPrefix, "");
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBAdapterVariablesPrefix = LuaConstants.luaFBAdapterVariablesPrefix(usedAdapterVariables);
      _builder.append(_luaFBAdapterVariablesPrefix, "");
      _builder.newLineIfNotEmpty();
      CharSequence _luaStructuredTextAlgorithm = this.luaStructuredTextAlgorithm(stalg);
      _builder.append(_luaStructuredTextAlgorithm, "");
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBVariablesSuffix = LuaConstants.luaFBVariablesSuffix(usedFBVariables);
      _builder.append(_luaFBVariablesSuffix, "");
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBAdapterVariablesSuffix = LuaConstants.luaFBAdapterVariablesSuffix(usedAdapterVariables);
      _builder.append(_luaFBAdapterVariablesSuffix, "");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public CharSequence lua(final BasicFBType fb, final String expression) {
    try {
      final ResourceSet resourceSet = STAlgorithmFilter.SERVICE_PRIVIDER.<ResourceSet>get(ResourceSet.class);
      final Resource fbResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_FB_URI);
      final BasicFBType fbCopy = EcoreUtil.<BasicFBType>copy(fb);
      EList<EObject> _contents = fbResource.getContents();
      _contents.add(fbCopy);
      Resource _createResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_ST_URI);
      final XtextResource resource = ((XtextResource) _createResource);
      IParser _parser = resource.getParser();
      final StructuredTextParser parser = ((StructuredTextParser) _parser);
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(expression);
      StructuredTextGrammarAccess _grammarAccess = parser.getGrammarAccess();
      ParserRule _expressionRule = _grammarAccess.getExpressionRule();
      Pair<String, ParserRule> _mappedTo = Pair.<String, ParserRule>of(StructuredTextResource.OPTION_PARSER_RULE, _expressionRule);
      resource.load(_lazyStringInputStream, Collections.<String, ParserRule>unmodifiableMap(CollectionLiterals.<String, ParserRule>newHashMap(_mappedTo)));
      final IParseResult parseResult = resource.getParseResult();
      boolean _hasSyntaxErrors = parseResult.hasSyntaxErrors();
      if (_hasSyntaxErrors) {
        Iterable<INode> _syntaxErrors = parseResult.getSyntaxErrors();
        final Function1<INode, String> _function = (INode it) -> {
          SyntaxErrorMessage _syntaxErrorMessage = it.getSyntaxErrorMessage();
          return _syntaxErrorMessage.getMessage();
        };
        Iterable<String> _map = IterableExtensions.<INode, String>map(_syntaxErrors, _function);
        Iterables.<String>addAll(this.errors, _map);
        return null;
      }
      EObject _rootASTElement = parseResult.getRootASTElement();
      final Expression expr = ((Expression) _rootASTElement);
      return this.luaExpression(expr);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private CharSequence luaStructuredTextAlgorithm(final StructuredTextAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    EList<VarDeclaration> _localVariables = alg.getLocalVariables();
    CharSequence _luaLocalVariables = this.luaLocalVariables(_localVariables);
    _builder.append(_luaLocalVariables, "");
    _builder.newLineIfNotEmpty();
    StatementList _statements = alg.getStatements();
    CharSequence _luaStatementList = this.luaStatementList(_statements);
    _builder.append(_luaStatementList, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence luaLocalVariables(final List<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final VarDeclaration variable : variables) {
        _builder.append("local ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable, "");
        CharSequence _luaLocalVariableInitializer = this.luaLocalVariableInitializer(variable);
        _builder.append(_luaLocalVariableInitializer, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence luaLocalVariableInitializer(final VarDeclaration variable) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (variable instanceof LocalVariable) {
      Constant _initialValue = ((LocalVariable)variable).getInitialValue();
      boolean _notEquals = (!Objects.equal(_initialValue, null));
      if (_notEquals) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("= variable.initialValue.luaExpression");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      _switchResult = "";
    }
    return _switchResult;
  }
  
  private CharSequence luaStatementList(final StatementList list) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Statement> _statements = list.getStatements();
      for(final Statement stmt : _statements) {
        CharSequence _luaStatement = this.luaStatement(stmt);
        _builder.append(_luaStatement, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  private CharSequence _luaStatement(final Statement stmt) {
    EClass _eClass = stmt.eClass();
    String _plus = (_eClass + " not supported");
    throw new UnsupportedOperationException(_plus);
  }
  
  private CharSequence _luaStatement(final AssignmentStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    Variable _variable = stmt.getVariable();
    CharSequence _luaExpression = this.luaExpression(_variable);
    _builder.append(_luaExpression, "");
    _builder.append(" = ");
    Expression _expression = stmt.getExpression();
    CharSequence _luaExpression_1 = this.luaExpression(_expression);
    _builder.append(_luaExpression_1, "");
    return _builder;
  }
  
  private CharSequence _luaStatement(final Call stmt) {
    return this.luaExpression(stmt);
  }
  
  private CharSequence _luaStatement(final ReturnStatement stmt) {
    return "return";
  }
  
  private CharSequence _luaStatement(final IfStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if ");
    Expression _expression = stmt.getExpression();
    CharSequence _luaExpression = this.luaExpression(_expression);
    _builder.append(_luaExpression, "");
    _builder.append(" then");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    StatementList _statments = stmt.getStatments();
    Object _luaStatementList = this.luaStatementList(_statments);
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    {
      EList<ElseIfClause> _elseif = stmt.getElseif();
      for(final ElseIfClause elseif : _elseif) {
        _builder.append("elseif ");
        Expression _expression_1 = elseif.getExpression();
        CharSequence _luaExpression_1 = this.luaExpression(_expression_1);
        _builder.append(_luaExpression_1, "");
        _builder.append(" then");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        StatementList _statements = elseif.getStatements();
        Object _luaStatementList_1 = this.luaStatementList(_statements);
        _builder.append(_luaStatementList_1, "  ");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      ElseClause _else = stmt.getElse();
      boolean _notEquals = (!Objects.equal(_else, null));
      if (_notEquals) {
        _builder.append("else");
        _builder.newLine();
        _builder.append("  ");
        ElseClause _else_1 = stmt.getElse();
        StatementList _statements_1 = _else_1.getStatements();
        Object _luaStatementList_2 = this.luaStatementList(_statements_1);
        _builder.append(_luaStatementList_2, "  ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end");
    return _builder;
  }
  
  private CharSequence _luaStatement(final CaseStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local function case(val)");
    _builder.newLine();
    _builder.append("  ");
    {
      EList<CaseClause> _case = stmt.getCase();
      boolean _hasElements = false;
      for(final CaseClause clause : _case) {
        if (!_hasElements) {
          _hasElements = true;
          _builder.append("if ", "  ");
        } else {
          _builder.appendImmediate("\nelseif ", "  ");
        }
        CharSequence _luaCaseClause = this.luaCaseClause(clause);
        _builder.append(_luaCaseClause, "  ");
      }
    }
    _builder.newLineIfNotEmpty();
    {
      ElseClause _else = stmt.getElse();
      boolean _notEquals = (!Objects.equal(_else, null));
      if (_notEquals) {
        _builder.append("  ");
        _builder.append("else");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("  ");
        ElseClause _else_1 = stmt.getElse();
        StatementList _statements = _else_1.getStatements();
        Object _luaStatementList = this.luaStatementList(_statements);
        _builder.append(_luaStatementList, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    _builder.append("case(");
    Expression _expression = stmt.getExpression();
    CharSequence _luaExpression = this.luaExpression(_expression);
    _builder.append(_luaExpression, "");
    _builder.append(")");
    return _builder;
  }
  
  private CharSequence luaCaseClause(final CaseClause clause) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Constant> _case = clause.getCase();
      boolean _hasElements = false;
      for(final Constant value : _case) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(" or ", "");
        }
        _builder.append("val == ");
        CharSequence _luaExpression = this.luaExpression(value);
        _builder.append(_luaExpression, "");
      }
    }
    _builder.append(" then");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    StatementList _statements = clause.getStatements();
    Object _luaStatementList = this.luaStatementList(_statements);
    _builder.append(_luaStatementList, "  ");
    return _builder;
  }
  
  private CharSequence _luaStatement(final ExitStatement stmt) {
    return "break";
  }
  
  private CharSequence _luaStatement(final ContinueStatement stmt) {
    return "continue";
  }
  
  private CharSequence _luaStatement(final ForStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("for ");
    PrimaryVariable _variable = stmt.getVariable();
    CharSequence _luaExpression = this.luaExpression(_variable);
    _builder.append(_luaExpression, "");
    _builder.append(" = ");
    Expression _from = stmt.getFrom();
    CharSequence _luaExpression_1 = this.luaExpression(_from);
    _builder.append(_luaExpression_1, "");
    _builder.append(", ");
    Expression _to = stmt.getTo();
    CharSequence _luaExpression_2 = this.luaExpression(_to);
    _builder.append(_luaExpression_2, "");
    {
      Expression _by = stmt.getBy();
      boolean _tripleNotEquals = (_by != null);
      if (_tripleNotEquals) {
        _builder.append(", ");
        Expression _by_1 = stmt.getBy();
        CharSequence _luaExpression_3 = this.luaExpression(_by_1);
        _builder.append(_luaExpression_3, "");
      }
    }
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    StatementList _statements = stmt.getStatements();
    Object _luaStatementList = this.luaStatementList(_statements);
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("end");
    return _builder;
  }
  
  private CharSequence _luaStatement(final WhileStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while ");
    Expression _expression = stmt.getExpression();
    CharSequence _luaExpression = this.luaExpression(_expression);
    _builder.append(_luaExpression, "");
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    StatementList _statements = stmt.getStatements();
    Object _luaStatementList = this.luaStatementList(_statements);
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("end");
    return _builder;
  }
  
  private CharSequence _luaStatement(final RepeatStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("repeat");
    _builder.newLine();
    _builder.append("  ");
    StatementList _statements = stmt.getStatements();
    Object _luaStatementList = this.luaStatementList(_statements);
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("until ");
    Expression _expression = stmt.getExpression();
    CharSequence _luaExpression = this.luaExpression(_expression);
    _builder.append(_luaExpression, "");
    return _builder;
  }
  
  private CharSequence _luaExpression(final Expression expr) {
    EClass _eClass = expr.eClass();
    String _plus = (_eClass + " not supported");
    throw new UnsupportedOperationException(_plus);
  }
  
  private CharSequence _luaExpression(final BinaryExpression expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    Expression _left = expr.getLeft();
    Object _luaExpression = this.luaExpression(_left);
    _builder.append(_luaExpression, "");
    _builder.append(" ");
    BinaryOperator _operator = expr.getOperator();
    String _luaBinaryOperator = this.luaBinaryOperator(_operator);
    _builder.append(_luaBinaryOperator, "");
    _builder.append(" ");
    Expression _right = expr.getRight();
    Object _luaExpression_1 = this.luaExpression(_right);
    _builder.append(_luaExpression_1, "");
    _builder.append(")");
    return _builder;
  }
  
  private String luaBinaryOperator(final BinaryOperator op) {
    String _switchResult = null;
    if (op != null) {
      switch (op) {
        case OR:
          _switchResult = "or";
          break;
        case XOR:
          _switchResult = "~";
          break;
        case AND:
          _switchResult = "and";
          break;
        case EQ:
          _switchResult = "==";
          break;
        case NE:
          _switchResult = "~=";
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
        case POWER:
          _switchResult = "^";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  private CharSequence _luaExpression(final UnaryExpression expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    UnaryOperator _operator = expr.getOperator();
    String _luaUnaryOperator = this.luaUnaryOperator(_operator);
    _builder.append(_luaUnaryOperator, "");
    _builder.append(" ");
    Expression _expression = expr.getExpression();
    Object _luaExpression = this.luaExpression(_expression);
    _builder.append(_luaExpression, "");
    _builder.append(")");
    return _builder;
  }
  
  private String luaUnaryOperator(final UnaryOperator op) {
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
          _switchResult = "not";
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  private CharSequence _luaExpression(final BoolLiteral expr) {
    boolean _isValue = expr.isValue();
    return Boolean.toString(_isValue);
  }
  
  private CharSequence _luaExpression(final IntLiteral expr) {
    long _value = expr.getValue();
    return Long.toString(_value);
  }
  
  private CharSequence _luaExpression(final RealLiteral expr) {
    double _value = expr.getValue();
    return Double.toString(_value);
  }
  
  private CharSequence _luaExpression(final StringLiteral expr) {
    String _value = expr.getValue();
    return LuaUtils.luaString(_value);
  }
  
  private CharSequence _luaExpression(final ArrayVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    Variable _array = expr.getArray();
    Object _luaExpression = this.luaExpression(_array);
    _builder.append(_luaExpression, "");
    {
      EList<Expression> _index = expr.getIndex();
      boolean _hasElements = false;
      for(final Expression index : _index) {
        if (!_hasElements) {
          _hasElements = true;
          _builder.append("[", "");
        } else {
          _builder.appendImmediate("][", "");
        }
        _builder.append("(");
        Object _luaExpression_1 = this.luaExpression(index);
        _builder.append(_luaExpression_1, "");
        _builder.append(") + 1");
      }
      if (_hasElements) {
        _builder.append("]", "");
      }
    }
    return _builder;
  }
  
  private CharSequence _luaExpression(final AdapterVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    VarDeclaration _var = expr.getVar();
    String _name = _var.getName();
    AdapterDeclaration _adapter = expr.getAdapter();
    String _name_1 = _adapter.getName();
    CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(_name, _name_1);
    _builder.append(_luaAdapterVariable, "");
    return _builder;
  }
  
  private CharSequence _luaExpression(final PrimaryVariable expr) {
    VarDeclaration _var = expr.getVar();
    return LuaConstants.luaVariable(_var);
  }
  
  private CharSequence luaStatement(final Statement stmt) {
    if (stmt instanceof AssignmentStatement) {
      return _luaStatement((AssignmentStatement)stmt);
    } else if (stmt instanceof Call) {
      return _luaStatement((Call)stmt);
    } else if (stmt instanceof CaseStatement) {
      return _luaStatement((CaseStatement)stmt);
    } else if (stmt instanceof ContinueStatement) {
      return _luaStatement((ContinueStatement)stmt);
    } else if (stmt instanceof ExitStatement) {
      return _luaStatement((ExitStatement)stmt);
    } else if (stmt instanceof ForStatement) {
      return _luaStatement((ForStatement)stmt);
    } else if (stmt instanceof IfStatement) {
      return _luaStatement((IfStatement)stmt);
    } else if (stmt instanceof RepeatStatement) {
      return _luaStatement((RepeatStatement)stmt);
    } else if (stmt instanceof ReturnStatement) {
      return _luaStatement((ReturnStatement)stmt);
    } else if (stmt instanceof WhileStatement) {
      return _luaStatement((WhileStatement)stmt);
    } else if (stmt != null) {
      return _luaStatement(stmt);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(stmt).toString());
    }
  }
  
  private CharSequence luaExpression(final Expression expr) {
    if (expr instanceof IntLiteral) {
      return _luaExpression((IntLiteral)expr);
    } else if (expr instanceof RealLiteral) {
      return _luaExpression((RealLiteral)expr);
    } else if (expr instanceof AdapterVariable) {
      return _luaExpression((AdapterVariable)expr);
    } else if (expr instanceof ArrayVariable) {
      return _luaExpression((ArrayVariable)expr);
    } else if (expr instanceof BoolLiteral) {
      return _luaExpression((BoolLiteral)expr);
    } else if (expr instanceof PrimaryVariable) {
      return _luaExpression((PrimaryVariable)expr);
    } else if (expr instanceof StringLiteral) {
      return _luaExpression((StringLiteral)expr);
    } else if (expr instanceof BinaryExpression) {
      return _luaExpression((BinaryExpression)expr);
    } else if (expr instanceof UnaryExpression) {
      return _luaExpression((UnaryExpression)expr);
    } else if (expr != null) {
      return _luaExpression(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
  
  @Pure
  public List<String> getErrors() {
    return this.errors;
  }
}
