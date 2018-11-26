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
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
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
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.nodemodel.INode;
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
      final EObject fbCopy = EcoreUtil.<EObject>copy(EcoreUtil.getRootContainer(alg));
      fbResource.getContents().add(fbCopy);
      Resource _createResource = resourceSet.createResource(STAlgorithmFilter.SYNTHETIC_ST_URI);
      final XtextResource resource = ((XtextResource) _createResource);
      String _text = alg.getText();
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(_text);
      Pair<String, Boolean> _mappedTo = Pair.<String, Boolean>of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      resource.load(_lazyStringInputStream, Collections.<String, Boolean>unmodifiableMap(CollectionLiterals.<String, Boolean>newHashMap(_mappedTo)));
      final IParseResult parseResult = resource.getParseResult();
      boolean _hasSyntaxErrors = parseResult.hasSyntaxErrors();
      if (_hasSyntaxErrors) {
        final Function1<INode, String> _function = (INode it) -> {
          return it.getSyntaxErrorMessage().getMessage();
        };
        Iterables.<String>addAll(this.errors, IterableExtensions.<INode, String>map(parseResult.getSyntaxErrors(), _function));
        return null;
      }
      EObject _rootASTElement = parseResult.getRootASTElement();
      final StructuredTextAlgorithm stalg = ((StructuredTextAlgorithm) _rootASTElement);
      final Set<AdapterVariable> usedAdapterVariables = IteratorExtensions.<AdapterVariable>toSet(Iterators.<AdapterVariable>filter(EcoreUtil.<Object>getAllProperContents(stalg, true), AdapterVariable.class));
      final Function1<PrimaryVariable, VarDeclaration> _function_1 = (PrimaryVariable it) -> {
        return it.getVar();
      };
      final Function1<VarDeclaration, Boolean> _function_2 = (VarDeclaration it) -> {
        EObject _rootContainer = EcoreUtil.getRootContainer(it);
        return Boolean.valueOf((_rootContainer instanceof FBType));
      };
      final Set<VarDeclaration> usedFBVariables = IteratorExtensions.<VarDeclaration>toSet(IteratorExtensions.<VarDeclaration>filter(IteratorExtensions.<PrimaryVariable, VarDeclaration>map(Iterators.<PrimaryVariable>filter(EcoreUtil.<Object>getAllProperContents(stalg, true), PrimaryVariable.class), _function_1), _function_2));
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _luaFBVariablesPrefix = LuaConstants.luaFBVariablesPrefix(usedFBVariables);
      _builder.append(_luaFBVariablesPrefix);
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBAdapterVariablesPrefix = LuaConstants.luaFBAdapterVariablesPrefix(usedAdapterVariables);
      _builder.append(_luaFBAdapterVariablesPrefix);
      _builder.newLineIfNotEmpty();
      CharSequence _luaStructuredTextAlgorithm = this.luaStructuredTextAlgorithm(stalg);
      _builder.append(_luaStructuredTextAlgorithm);
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBVariablesSuffix = LuaConstants.luaFBVariablesSuffix(usedFBVariables);
      _builder.append(_luaFBVariablesSuffix);
      _builder.newLineIfNotEmpty();
      CharSequence _luaFBAdapterVariablesSuffix = LuaConstants.luaFBAdapterVariablesSuffix(usedAdapterVariables);
      _builder.append(_luaFBAdapterVariablesSuffix);
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
      boolean _hasSyntaxErrors = parseResult.hasSyntaxErrors();
      if (_hasSyntaxErrors) {
        final Function1<INode, String> _function = (INode it) -> {
          return it.getSyntaxErrorMessage().getMessage();
        };
        Iterables.<String>addAll(this.errors, IterableExtensions.<INode, String>map(parseResult.getSyntaxErrors(), _function));
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
    CharSequence _luaLocalVariables = this.luaLocalVariables(alg.getLocalVariables());
    _builder.append(_luaLocalVariables);
    _builder.newLineIfNotEmpty();
    CharSequence _luaStatementList = this.luaStatementList(alg.getStatements());
    _builder.append(_luaStatementList);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence luaLocalVariables(final List<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final VarDeclaration variable : variables) {
        _builder.append("local ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable);
        CharSequence _luaLocalVariableInitializer = this.luaLocalVariableInitializer(variable);
        _builder.append(_luaLocalVariableInitializer);
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
        _builder.append(_luaStatement);
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
    CharSequence _luaExpression = this.luaExpression(stmt.getVariable());
    _builder.append(_luaExpression);
    _builder.append(" = ");
    CharSequence _luaExpression_1 = this.luaExpression(stmt.getExpression());
    _builder.append(_luaExpression_1);
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
    CharSequence _luaExpression = this.luaExpression(stmt.getExpression());
    _builder.append(_luaExpression);
    _builder.append(" then");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Object _luaStatementList = this.luaStatementList(stmt.getStatments());
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    {
      EList<ElseIfClause> _elseif = stmt.getElseif();
      for(final ElseIfClause elseif : _elseif) {
        _builder.append("elseif ");
        CharSequence _luaExpression_1 = this.luaExpression(elseif.getExpression());
        _builder.append(_luaExpression_1);
        _builder.append(" then");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        Object _luaStatementList_1 = this.luaStatementList(elseif.getStatements());
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
        Object _luaStatementList_2 = this.luaStatementList(stmt.getElse().getStatements());
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
        Object _luaStatementList = this.luaStatementList(stmt.getElse().getStatements());
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
    CharSequence _luaExpression = this.luaExpression(stmt.getExpression());
    _builder.append(_luaExpression);
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
        _builder.append(_luaExpression);
      }
    }
    _builder.append(" then");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Object _luaStatementList = this.luaStatementList(clause.getStatements());
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
    CharSequence _luaExpression = this.luaExpression(stmt.getVariable());
    _builder.append(_luaExpression);
    _builder.append(" = ");
    CharSequence _luaExpression_1 = this.luaExpression(stmt.getFrom());
    _builder.append(_luaExpression_1);
    _builder.append(", ");
    CharSequence _luaExpression_2 = this.luaExpression(stmt.getTo());
    _builder.append(_luaExpression_2);
    {
      Expression _by = stmt.getBy();
      boolean _tripleNotEquals = (_by != null);
      if (_tripleNotEquals) {
        _builder.append(", ");
        CharSequence _luaExpression_3 = this.luaExpression(stmt.getBy());
        _builder.append(_luaExpression_3);
      }
    }
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Object _luaStatementList = this.luaStatementList(stmt.getStatements());
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("end");
    return _builder;
  }
  
  private CharSequence _luaStatement(final WhileStatement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("while ");
    CharSequence _luaExpression = this.luaExpression(stmt.getExpression());
    _builder.append(_luaExpression);
    _builder.append(" do");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Object _luaStatementList = this.luaStatementList(stmt.getStatements());
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
    Object _luaStatementList = this.luaStatementList(stmt.getStatements());
    _builder.append(_luaStatementList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("until ");
    CharSequence _luaExpression = this.luaExpression(stmt.getExpression());
    _builder.append(_luaExpression);
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
    Object _luaExpression = this.luaExpression(expr.getLeft());
    _builder.append(_luaExpression);
    _builder.append(" ");
    String _luaBinaryOperator = this.luaBinaryOperator(expr.getOperator());
    _builder.append(_luaBinaryOperator);
    _builder.append(" ");
    Object _luaExpression_1 = this.luaExpression(expr.getRight());
    _builder.append(_luaExpression_1);
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
    String _luaUnaryOperator = this.luaUnaryOperator(expr.getOperator());
    _builder.append(_luaUnaryOperator);
    _builder.append(" ");
    Object _luaExpression = this.luaExpression(expr.getExpression());
    _builder.append(_luaExpression);
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
    return Boolean.toString(expr.isValue());
  }
  
  private CharSequence _luaExpression(final IntLiteral expr) {
    return Long.toString(expr.getValue());
  }
  
  private CharSequence _luaExpression(final RealLiteral expr) {
    return Double.toString(expr.getValue());
  }
  
  private CharSequence _luaExpression(final StringLiteral expr) {
    return LuaUtils.luaString(expr.getValue());
  }
  
  private CharSequence _luaExpression(final ArrayVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    Object _luaExpression = this.luaExpression(expr.getArray());
    _builder.append(_luaExpression);
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
        _builder.append("(");
        Object _luaExpression_1 = this.luaExpression(index);
        _builder.append(_luaExpression_1);
        _builder.append(") + 1");
      }
      if (_hasElements) {
        _builder.append("]");
      }
    }
    return _builder;
  }
  
  private CharSequence _luaExpression(final AdapterVariable expr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(expr.getVar().getName(), expr.getAdapter().getName());
    _builder.append(_luaAdapterVariable);
    return _builder;
  }
  
  private CharSequence _luaExpression(final PrimaryVariable expr) {
    return LuaConstants.luaVariable(expr.getVar());
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
