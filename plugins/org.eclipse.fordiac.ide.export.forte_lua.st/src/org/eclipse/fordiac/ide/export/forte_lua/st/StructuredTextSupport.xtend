/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst, Patrick Aigner
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Patrick Aigner - adapted for Lua Code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.st

import java.math.BigDecimal
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.List
import java.util.Set
import java.util.Stack
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.ByteType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.DwordType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.LwordType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.data.WordType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.value.ValueConverterFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.xtext.util.Strings.convertToJavaString

abstract class StructuredTextSupport implements ILanguageSupport {
	@Accessors final List<String> errors = newArrayList
	@Accessors final List<String> warnings = newArrayList
	@Accessors final List<String> infos = newArrayList
	int uniqueVariableIndex = 0;
	int loopIndex = 0;
	Stack<String> loopStack = new Stack<String>();

	def protected CharSequence generateFBVariablePrefix(InterfaceList vars) '''
		«FOR in : vars.inputVars»
			ENV.fb_var_«in.name» = fb[DI_«in.name»]
		«ENDFOR»
		«FOR out : vars.outputVars»
			ENV.fb_var_«out.name» = fb[DO_«out.name»]
		«ENDFOR»
	'''

	def protected CharSequence generateFBVariableSuffix(InterfaceList vars) '''
		«FOR out : vars.outputVars»
			fb[DO_«out.name»] = ENV.fb_var_«out.name»
		«ENDFOR»
	'''

	def protected CharSequence generateInternalVariablePrefix(Iterable<? extends VarDeclaration> vars) '''
		«FOR in : vars»
			ENV.fb_var_«in.name» = fb[IN_«in.name»]
		«ENDFOR»
	'''

	def protected CharSequence generateInternalVariableSuffix(Iterable<? extends VarDeclaration> vars) '''
		«FOR in : vars»
			fb[IN_«in.name»] = ENV.fb_var_«in.name»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariables(Iterable<? extends STVarDeclarationBlock> blocks) '''
		«FOR block : blocks»
			«block.generateLocalVariableBlock»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariableBlock(STVarDeclarationBlock block) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateLocalVariable(block.constant)»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariable(STVarDeclaration variable, boolean const) {
		/*if (variable.array) {
		 * 	return '''«variable.generateFeatureName(false)» = STfunc.array({«variable.generateArrayRanges»}, «(variable.type as DataType).generateTypeDefaultValue», {})'''
		 * }
		 '''«variable.generateFeatureName(false)» = «IF variable.defaultValue !== null»«variable.defaultValue.generateInitializerExpression»«ELSE»nil«ENDIF»'''*/
		'''«variable.generateFeatureName(false)» = «IF variable.array»STfunc.array({«variable.generateArrayRanges»}, «(variable.type as DataType).generateTypeDefaultValue», «IF variable.defaultValue !== null»«variable.defaultValue.generateInitializerExpression»«ELSE»{nil}«ENDIF»)
		«ELSE»«IF variable.defaultValue !== null»«variable.defaultValue.generateInitializerExpression»«ELSE»«(variable.type as DataType).generateTypeDefaultValue»«ENDIF»«ENDIF»'''

	}

	def protected CharSequence generateArrayRanges(STVarDeclaration variable) {
		'''«FOR range : variable.ranges SEPARATOR ', '»{«(range as STBinaryExpression).left.generateExpression», «(range as STBinaryExpression).right.generateExpression»}«ENDFOR»'''
	}

	def protected dispatch CharSequence generateInitializerExpression(STElementaryInitializerExpression expr) {
		expr.value.generateExpression
	}

	def protected dispatch CharSequence generateInitializerExpression(STArrayInitializerExpression expr) //
	'''{«FOR elem : expr.values SEPARATOR ", "»«elem.generateArrayInitElement»«ENDFOR»}'''

	def protected CharSequence generateArrayInitElement(STArrayInitElement elem) //
	'''«IF elem.initExpressions.empty»«elem.indexOrInitExpression.generateInitializerExpression»«ELSE»«elem.generateMultiArrayInitElement»«ENDIF»'''

	def protected CharSequence generateMultiArrayInitElement(STArrayInitElement elem) //
	//'''«FOR i : 0..<(elem.indexOrInitExpression as STElementaryInitializerExpression).value.integerFromConstantExpression SEPARATOR ", "»«FOR initExpression : elem.initExpressions SEPARATOR ", "»«initExpression.generateInitializerExpression»«ENDFOR»«ENDFOR»'''
	'''{«elem.indexOrInitExpression.generateInitializerExpression», {«FOR initExpression : elem.initExpressions SEPARATOR ", "»«initExpression.generateInitializerExpression»«ENDFOR»}}'''

	def protected CharSequence generateStatementList(List<STStatement> statements) '''
		«FOR statement : statements»
			«statement.generateStatement»
		«ENDFOR»
	'''

	def protected dispatch CharSequence generateStatement(STStatement stmt) {
		errors.add('''The statement «stmt.eClass.name» is not supported''')
		'''#error "The statement «stmt.eClass.name» is not supported"'''
	}

	def protected dispatch CharSequence generateStatement(STNop stmt) {
		"" // nop
	}

	def protected dispatch CharSequence generateStatement(STAssignmentStatement stmt) //
	'''«stmt.left.generateExpression» = «stmt.right.generateExpression»'''

	def protected dispatch CharSequence generateStatement(STIfStatement stmt) '''
		if «stmt.condition.generateExpression» then
		  «stmt.statements.generateStatementList»
		«FOR elseif : stmt.elseifs»
			elsif «elseif.condition.generateExpression» then
			  «elseif.statements.generateStatementList»
		«ENDFOR»
		«IF stmt.^else !== null»
			else
			  «stmt.^else.statements.generateStatementList»
		«ENDIF»
		end
	'''

	def protected dispatch generateStatement(STCaseStatement stmt) '''
		«var selector = stmt.selector.generateExpression»
		if «FOR clause : stmt.cases SEPARATOR ' elsif '»«clause.generateCaseClause(selector)»«ENDFOR»
		«IF stmt.^else !== null»
			else
			  «stmt.^else.statements.generateStatementList»
		«ENDIF»
	'''

	def protected generateCaseClause(STCaseCases clause, CharSequence selector) '''
		«FOR value : clause.conditions SEPARATOR ' or '»«selector» == «value.generateExpression»«ENDFOR» then
		  «clause.statements.generateStatementList»
	'''

	def protected dispatch generateStatement(STForStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			for «stmt.variable.generateExpression» = «stmt.from.generateExpression», «stmt.to.generateExpression», «IF stmt.by !== null»«stmt.by.generateExpression»«ELSE»1«ENDIF» do
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			end
		'''
	}

	def protected dispatch generateStatement(STWhileStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			while «stmt.condition.generateExpression» do
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			end
		'''
	}

	def protected dispatch generateStatement(STRepeatStatement stmt) {
		loopStack.push("loop_" + loopIndex);
		loopIndex++;
		'''
			repeat
			  «stmt.statements.generateStatementList»
			  ::«loopStack.pop()»::
			until «stmt.condition.generateExpression»
		'''
	}

	def protected dispatch CharSequence generateStatement(STContinue stmt) '''goto «loopStack.peek»''' // Lua 5.2 or LuaJit 2.0.1

	def protected dispatch CharSequence generateStatement(STReturn stmt) '''return'''

	def protected dispatch CharSequence generateStatement(STExit stmt) '''break'''

	def protected dispatch CharSequence generateStatement(STCallStatement stmt) '''«stmt.call.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STExpression expr) {
		errors.add('''The expression «expr.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			// case RANGE: '''«expr.left.generateExpression», «expr.right.generateExpression»'''
			case OR:
				if (expr.left.resultType instanceof BoolType) {
					'''(«expr.left.generateExpression» or «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof LwordType) {
					'''STfunc.OR64(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				} else {
					'''STfunc.OR(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				}
			case XOR:
				if (expr.left.resultType instanceof BoolType) {
					'''(«expr.left.generateExpression» ~= «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof LwordType) {
					'''STfunc.XOR64(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				} else {
					'''STfunc.XOR(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				}
			case AND:
				if (expr.left.resultType instanceof BoolType) {
					return '''(«expr.left.generateExpression» and «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof LwordType) {
					'''STfunc.AND64(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				} else {
					'''STfunc.AND(«expr.left.generateExpression», «expr.right.generateExpression»)'''
				}
			case AMPERSAND: '''(«expr.left.generateExpression» and «expr.right.generateExpression»)'''
			case EQ: '''(«expr.left.generateExpression» == «expr.right.generateExpression»)'''
			case NE: '''(«expr.left.generateExpression» ~= «expr.right.generateExpression»)'''
			case LT: '''(«expr.left.generateExpression» < «expr.right.generateExpression»)'''
			case LE: '''(«expr.left.generateExpression» <= «expr.right.generateExpression»)'''
			case GT: '''(«expr.left.generateExpression» > «expr.right.generateExpression»)'''
			case GE: '''(«expr.left.generateExpression» >= «expr.right.generateExpression»)'''
			case ADD:
				if (expr.left.resultType instanceof AnyNumType) {
					'''(«expr.left.generateExpression» + «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof AnyDurationType) {
					'''(«expr.left.generateExpression» + «expr.right.generateExpression»)'''
				} else {
					errors.add('''The ADD operation for «expr.left.resultType.name» is not yet supported''')
					''''''
				}
			case SUB:
				if (expr.left.resultType instanceof AnyNumType) {
					'''(«expr.left.generateExpression» - «expr.right.generateExpression»)'''
				} else if (expr.left.resultType instanceof AnyDurationType) {
					'''(«expr.left.generateExpression» - «expr.right.generateExpression»)'''
				} else {
					errors.add('''The SUB operation for «expr.left.resultType.name» is not yet supported''')
					''''''
				}
			case MUL: '''(«expr.left.generateExpression» * «expr.right.generateExpression»)'''
			case DIV:
				if (expr.left.resultType instanceof AnyRealType || expr.right.resultType instanceof AnyRealType) {
					'''(«expr.left.generateExpression» / «expr.right.generateExpression»)'''
				} else {
					'''math.floor(«expr.left.generateExpression» / «expr.right.generateExpression»)'''
				}
			case MOD: '''(«expr.left.generateExpression» % «expr.right.generateExpression»)'''
			case POWER: '''(«expr.left.generateExpression»^«expr.right.generateExpression»)'''
			default: {
				errors.add('''The operation «expr.op.getName» is not supported''')
				''''''
			}
		}
	}

	def protected dispatch CharSequence generateExpression(STUnaryExpression expr) {
		switch (expr.op) {
			case MINUS: '''-«expr.expression.generateExpression»'''
			case PLUS: '''«expr.expression.generateExpression»'''
			case NOT: {
				var bits = ""
				if (expr.resultType instanceof BoolType) {
					return '''(not «expr.expression.generateExpression»)'''
				} else if (expr.resultType instanceof ByteType) {
					bits = "8"
				} else if (expr.resultType instanceof WordType) {
					bits = "16"
				} else if (expr.resultType instanceof DwordType) {
					bits = "32"
				} else if (expr.resultType instanceof LwordType) {
					bits = "64"
				}
				return '''STfunc.NOT«bits»(«expr.expression.generateExpression»)'''
			}
			default: {
				errors.add('''The operation «expr.op.getName» is not supported''')
				''''''
			}
		}
	}

	def protected dispatch CharSequence generateExpression(STMemberAccessExpression expr) //
	'''«expr.receiver.generateExpression».«expr.member.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STArrayAccessExpression expr) //
	'''«expr.receiver.generateExpression»«FOR index : expr.index»[«index.generateExpression»]«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(STFeatureExpression expr) {
		if (expr.call) {
			if (expr.feature instanceof STStandardFunction) {
				var name = expr.feature.name.toLowerCase
				var call = ""
				var addPars = ""
				var type = (expr.feature as STStandardFunction).returnType
				switch name {
					// math functions
					case "abs",
					case "sqrt",
					case "ln": {
						call = '''math.«name»'''
					}
					case "log": {
						call = "math.ln"
						addPars = ", 10"
					}
					case "exp",
					case "sin",
					case "cos",
					case "tan",
					case "asin",
					case "acos",
					case "atan": {
						call = '''math.«name»'''
					}
					case "atan2": { // TODO: check if functionally the same
						call = "math.atan"
					}
					// selection functions
					case "min",
					case "max": {
						call = '''math.«name»'''
					}
					case "limit",
					case "sel",
					case "mux": {
						call = '''STfunc.«name.toUpperCase»'''
					}
					// bit operations
					case "shl",
					case "shr",
					case "rol",
					case "ror": {
						var bits = ""
						if (type instanceof BoolType) {
							bits = "1"
						} else if (type instanceof ByteType) {
							bits = "8"
						} else if (type instanceof WordType) {
							bits = "16"
						} else if (type instanceof DwordType) {
							bits = "32"
						} else if (type instanceof LwordType) {
							bits = "64"
						}
						call = '''STfunc.«name.toUpperCase»«bits»'''
					}
					// array functions
					case "lower_bound",
					case "upper_bound": {
						call = '''STfunc.«name.toUpperCase»'''
					}
					// cast operations - byte values
					case "bool_to_byte",
					case "bool_to_word",
					case "bool_to_dword",
					case "bool_to_lword",
					case "bool_to_sint",
					case "bool_to_usint",
					case "bool_to_int",
					case "bool_to_uint",
					case "bool_to_dint",
					case "bool_to_udint",
					case "bool_to_lint",
					case "bool_to_ulint": {
						call = '''STfunc.BOOL_TO_INT'''
					}
					case "byte_to_bool": {
						call = '''STfunc.BYTE_TO_BOOL'''
					}
					case "byte_to_char": {
						call = '''string.char'''
					}
					case "byte_to_word",
					case "byte_to_dword",
					case "byte_to_lword",
					case "byte_to_sint",
					case "byte_to_usint",
					case "byte_to_int",
					case "byte_to_uint",
					case "byte_to_dint",
					case "byte_to_udint",
					case "byte_to_lint",
					case "byte_to_ulint": {
						call = ''''''
					}
					case "word_to_bool": {
						call = '''STfunc.BYTE_TO_BOOL'''
					}
					case "word_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "word_to_dword",
					case "word_to_lword": {
						call = ''''''
					}
					case "word_to_sint",
					case "word_to_usint": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "word_to_int",
					case "word_to_uint",
					case "word_to_dint",
					case "word_to_udint",
					case "word_to_lint",
					case "word_to_ulint": {
						call = ''''''
					}
					case "dword_to_bool": {
						call = '''STfunc.BYTE_TO_BOOL'''
					}
					case "dword_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "dword_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "dword_to_lword": {
						call = ''''''
					}
					case "dword_to_sint",
					case "dword_to_usint": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "dword_to_int",
					case "dword_to_uint": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "dword_to_dint",
					case "dword_to_udint",
					case "dword_to_lint",
					case "dword_to_ulint",
					case "dword_to_real": {
						call = ''''''
					}
					case "lword_to_bool": {
						call = '''STfunc.BYTE_TO_BOOL'''
					}
					case "lword_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "lword_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "lword_to_dword": {
						call = '''STfunc.VAL_TO_DWORD'''
					}
					case "lword_to_sint",
					case "lword_to_usint": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "lword_to_int",
					case "lword_to_uint": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "lword_to_dint",
					case "lword_to_udint": {
						call = '''STfunc.VAL_TO_DWORD'''
					}
					case "lword_to_lint",
					case "lword_to_ulint",
					case "lword_to_lreal": {
						call = ''''''
					}
					// cast operations - integer values
					case "sint_to_byte",
					case "sint_to_word",
					case "sint_to_dword",
					case "sint_to_lword",
					case "sint_to_usint",
					case "sint_to_int",
					case "sint_to_uint",
					case "sint_to_dint",
					case "sint_to_udint",
					case "sint_to_lint",
					case "sint_to_ulint": {
						call = ''''''
					}
					case "usint_to_byte",
					case "usint_to_word",
					case "usint_to_dword",
					case "usint_to_lword",
					case "usint_to_sint",
					case "usint_to_int",
					case "usint_to_uint",
					case "usint_to_dint",
					case "usint_to_udint",
					case "usint_to_lint",
					case "usint_to_ulint": {
						call = ''''''
					}
					case "int_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "int_to_word",
					case "int_to_dword",
					case "int_to_lword",
					case "int_to_sint",
					case "int_to_usint",
					case "int_to_uint",
					case "int_to_dint",
					case "int_to_udint",
					case "int_to_lint",
					case "int_to_ulint": {
						call = ''''''
					}
					case "uint_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "uint_to_word",
					case "uint_to_dword",
					case "uint_to_lword",
					case "uint_to_sint",
					case "uint_to_usint",
					case "uint_to_int",
					case "uint_to_dint",
					case "uint_to_udint",
					case "uint_to_lint",
					case "uint_to_ulint": {
						call = ''''''
					}
					case "dint_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "dint_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "dint_to_dword",
					case "dint_to_lword",
					case "dint_to_sint",
					case "dint_to_usint",
					case "dint_to_int",
					case "dint_to_udint",
					case "dint_to_uint",
					case "dint_to_lint",
					case "dint_to_ulint",
					case "dint_to_real",
					case "dint_to_lreal": {
						call = ''''''
					}
					case "udint_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "udint_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "udint_to_dword",
					case "udint_to_lword",
					case "udint_to_sint",
					case "udint_to_usint",
					case "udint_to_int",
					case "udint_to_dint",
					case "udint_to_uint",
					case "udint_to_lint",
					case "udint_to_ulint",
					case "udint_to_real",
					case "udint_to_lreal": {
						call = ''''''
					}
					case "lint_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "lint_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "lint_to_dword": {
						call = '''STfunc.VAL_TO_DWORD'''
					}
					case "lint_to_lword",
					case "lint_to_sint",
					case "lint_to_usint",
					case "lint_to_int",
					case "lint_to_dint",
					case "lint_to_udint",
					case "lint_to_uint",
					case "lint_to_ulint",
					case "lint_to_real",
					case "lint_to_lreal": {
						call = ''''''
					}
					case "ulint_to_byte": {
						call = '''STfunc.VAL_TO_BYTE'''
					}
					case "ulint_to_word": {
						call = '''STfunc.VAL_TO_WORD'''
					}
					case "ulint_to_dword": {
						call = '''STfunc.VAL_TO_DWORD'''
					}
					case "ulint_to_lword",
					case "ulint_to_sint",
					case "ulint_to_usint",
					case "ulint_to_int",
					case "ulint_to_dint",
					case "ulint_to_udint",
					case "ulint_to_uint",
					case "ulint_to_lint",
					case "ulint_to_real",
					case "ulint_to_lreal": {
						call = ''''''
					}
					// cast operations - integer values
					case "real_to_dword": {
						call = ''''''
					}
					case "real_to_sint",
					case "real_to_usint",
					case "real_to_int",
					case "real_to_uint",
					case "real_to_dint",
					case "real_to_udint",
					case "real_to_lint",
					case "real_to_ulint": {
						call = '''math.floor'''
					}
					case "real_to_lreal": {
						call = ''''''
					}
					case "lreal_to_lword": {
						call = ''''''
					}
					case "lreal_to_sint",
					case "lreal_to_usint",
					case "lreal_to_int",
					case "lreal_to_uint",
					case "lreal_to_dint",
					case "lreal_to_udint",
					case "lreal_to_lint",
					case "lreal_to_ulint": {
						call = '''math.floor'''
					}
					case "lreal_to_real": {
						call = ''''''
					}
					// cast operations - string values
					case "char_to_byte",
					case "char_to_word",
					case "char_to_dword",
					case "char_to_lword": {
						call = '''string.byte'''
					}
					case "char_to_string": {
						call = ''''''
					}
					case "char_to_wchar": {
						call = '''STfunc.CHAR_TO_WCHAR'''
					}
					case "wchar_to_word",
					case "wchar_to_dword",
					case "wchar_to_lword": {
						call = '''STfunc.WCHAR_TO_BYTE'''
					}
					case "wchar_to_wstring": {
						call = ''''''
					}
					case "wchar_to_char": {
						call = '''string.sub'''
						addPars = ''',2,2'''
					}
					case "string_to_char": {
						call = '''string.sub'''
						addPars = ''',1,1'''
					}
					case "string_to_wstring": {
						call = '''STfunc.STRING_TO_WSTRING'''
					}
					case "wstring_to_wchar": {
						call = '''string.sub'''
						addPars = ''',1,2'''
					}
					case "wstring_to_string": {
						call = '''STfunc.WSTRING_TO_STRING'''
					}
					// cast operations - temporal values
					case "ltime_to_time",
					case "time_to_ltime",
					case "dt_to_date",
					case "dt_to_ldt",
					case "dt_to_ltod",
					case "dt_to_tod",
					case "ldt_to_date",
					case "ldt_to_dt",
					case "ldt_to_ltod",
					case "ldt_to_tod",
					case "tod_to_ltod",
					case "ltod_to_tod": {
						call = ''''''
					}
					// aliases
					default:
						call = '''«expr.feature.generateFeatureName(true)»'''
				}
				return '''«call»(«FOR arg : expr.generateCallArguments SEPARATOR ", "»«arg»«ENDFOR»«addPars»)'''
			} else {
				val list = '''{«FOR arg : expr.generateReturnArguments SEPARATOR ", "»'«arg»'«ENDFOR»}'''
				return '''STfunc.wrapfunc(«expr.feature.generateFeatureName(true)», fb, ENV, «list»«FOR arg : expr.generateCallArguments BEFORE ', ' SEPARATOR ", "»«arg»«ENDFOR»)'''
			}
		}
		'''«expr.feature.generateFeatureName(false)»'''
	}

	def protected Iterable<CharSequence> generateCallArguments(STFeatureExpression expr) {
		try {
			expr.mappedInputArguments.entrySet.map[key.generateInputCallArgument(value)] +
				expr.mappedInOutArguments.entrySet.map[key.generateInOutCallArgument(value)] // +
				// expr.mappedOutputArguments.entrySet.map[key.generateOutputCallArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.name»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected Iterable<CharSequence> generateReturnArguments(STFeatureExpression expr) {
		try {
			expr.mappedInOutArguments.entrySet.map[key.generateOutputReturnArgument(value)] +
				expr.mappedOutputArguments.entrySet.map[key.generateOutputReturnArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.name»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected CharSequence generateInputCallArgument(INamedElement parameter, STCallArgument argument) {
		if(argument === null) parameter.generateVariableDefaultValue else argument.argument.generateExpression
	}

	def protected CharSequence generateInOutCallArgument(INamedElement parameter, STCallArgument argument) {
		if (argument === null)
			'''nil'''
		else
			argument.argument.generateExpression
	}

	def protected CharSequence generateOutputReturnArgument(INamedElement parameter, STCallArgument argument) {
		if (argument === null)
			'''nil'''
		else
			argument.argument.generateOutputExpression
	}

	def protected dispatch CharSequence generateOutputExpression(STExpression expr) {
		errors.add('''The expression «expr.eClass.name» is not supported as an out parameter''')
		""
	}

	def protected dispatch CharSequence generateOutputExpression(STFeatureExpression expr) {
		'''«expr.feature.generateOutputFeatureName»'''
	}

	def protected dispatch CharSequence generateOutputFeatureName(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported as an out parameter''')
		""
	}

	def protected dispatch CharSequence generateOutputFeatureName(VarDeclaration feature) '''fb_var_«feature.name»'''

	def protected dispatch CharSequence generateOutputFeatureName(STVarDeclaration feature) '''st_lv_«feature.name»'''

	// def protected dispatch CharSequence generateExpression(STMultibitPartialExpression expr) //
	// '''partial<«expr.specifier.generateMultiBitAccessSpecifier»>(«IF expr.expression !== null»«expr.expression.generateExpression»«ELSE»«expr.index»«ENDIF»)'''
//	def protected CharSequence generateMultiBitAccessSpecifier(STMultiBitAccessSpecifier spec) {
//		switch (spec) {
//			case null,
//			case X: "CIEC_BOOL"
//			case B: "CIEC_BYTE"
//			case W: "CIEC_WORD"
//			case D: "CIEC_DWORD"
//			case L: "CIEC_LWORD"
//		}
//	}
	def protected dispatch CharSequence generateExpression(STNumericLiteral expr) //
	'''«expr.value»'''

	def protected dispatch CharSequence generateExpression(STStringLiteral expr) //
	'''"«expr.value.toString.convertToJavaString»"'''

	def protected dispatch CharSequence generateExpression(STDateLiteral expr) //
	'''«expr.value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L»'''

	def protected dispatch CharSequence generateExpression(STTimeLiteral expr) //
	'''«expr.value.toNanos»'''

	def protected dispatch CharSequence generateExpression(STTimeOfDayLiteral expr) //
	'''«expr.value.toNanoOfDay»'''

	def protected dispatch CharSequence generateExpression(STDateAndTimeLiteral expr) //
	'''«LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(expr.value, ChronoUnit.NANOS)»'''

	def protected dispatch CharSequence generateTemplateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			case RANGE: '''«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»'''
			case AMPERSAND: '''AND(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
			default: '''«expr.op.getName»(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
		}
	}

	def protected dispatch CharSequence generateTemplateExpression(STUnaryExpression expr) //
	'''«expr.op.getName»(«expr.expression.generateTemplateExpression»)'''

	def protected dispatch CharSequence generateTemplateExpression(STNumericLiteral expr) { expr.value.toString }

	def protected dispatch CharSequence generateVariableDefaultValue(INamedElement feature) {
		errors.add('''The variable «feature.eClass.name» is not supported''')
		"0"
	}

	def protected dispatch CharSequence generateVariableDefaultValue(VarDeclaration variable) {
		// generateVariableDefaultValue(variable)
		'''nil'''
	}

	def protected dispatch CharSequence generateVariableDefaultValue(STVarDeclaration variable) {
		if (variable.defaultValue !== null)
			variable.defaultValue.generateInitializerExpression
		else if (variable.array)
			"{lo=0, up=0}"
		else
			(variable.type as DataType).generateTypeDefaultValue
	}

	def protected dispatch CharSequence generateFeatureName(INamedElement feature, boolean call) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateFeatureName(VarDeclaration feature,
		boolean call) '''ENV.fb_var_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STVarDeclaration feature,
		boolean call) '''ENV.st_lv_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STFunction feature, boolean call) '''func_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STStandardFunction feature,
		boolean call) '''STfunc.«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STMethod feature, boolean call) '''method_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(FB feature, boolean call) '''fb_«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(Event feature, boolean call) '''evt_«feature.name»'''

	def protected dispatch INamedElement getType(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		null
	}

	def protected dispatch INamedElement getType(VarDeclaration feature) { feature.type }

	def protected dispatch INamedElement getType(STVarDeclaration feature) { feature.type }

	def protected int getIntegerFromConstantExpression(STExpression expr) {
		try {
			((expr as STNumericLiteral).value as BigDecimal).intValueExact
		} catch (Exception e) {
			errors.add("Not a constant integer expression")
			1
		}
	}

	def protected Set<INamedElement> getContainedDependencies(EObject object) {
		object.<EObject>getAllProperContents(true).toIterable.flatMap[dependencies].toSet
	}

	def protected Iterable<INamedElement> getDependencies(EObject object) {
		switch (object) {
			STVarDeclaration:
				#[object.type]
			STNumericLiteral:
				#[object.resultType]
			STStringLiteral:
				#[object.resultType]
			STDateLiteral:
				#[object.type]
			STTimeLiteral:
				#[object.type]
			STTimeOfDayLiteral:
				#[object.type]
			STDateAndTimeLiteral:
				#[object.type]
			STFeatureExpression:
				object.feature.dependencies
			STFunction:
				#[object]
			default:
				emptySet
		}
	}

	def protected generateUniqueVariableName() '''st_lv_synthetic_«uniqueVariableIndex++»'''

	def static CharSequence generateVarDefaultValue(VarDeclaration decl) {
		if (decl.value?.value.nullOrEmpty) {
			decl.type.generateTypeDefaultValue
		} else {
			val converter = ValueConverterFactory.createValueConverter(decl.type)
			if (converter !== null) {
				val value = converter.toValue(decl.value.value)
				'''«decl.type.generateTypeName»(«switch (value) {
				String: '''"«value.convertToJavaString»"'''
				Duration: Long.toString(value.toNanos)
				LocalTime: Long.toString(value.toNanoOfDay)
				LocalDate: Long.toString(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L)
				LocalDateTime: Long.toString(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS))
				default: value
			}»)'''
			} else
				throw new UnsupportedOperationException("No value converter for type " + decl.type?.name)
		}
	}

	def static CharSequence generateTypeDefaultValue(DataType type) {
		switch (type) {
			AnyStringType: '''""'''
			BoolType: '''false'''
			AnyElementaryType: '''0'''
			ArrayType: '''{lo=0, up=0}'''
			StructuredType: '''{}'''
			default: '''0'''
		}
	}

	def static CharSequence generateTypeName(DataType type) {
		switch (type) {
			TimeType,
			LtimeType: "CIEC_TIME"
			DateType,
			LdateType: "CIEC_DATE"
			TimeOfDayType,
			LtodType: "CIEC_TIME_OF_DAY"
			DateAndTimeType,
			LdtType: "CIEC_DATE_AND_TIME"
			ArrayType: "CIEC_ARRAY"
			default: '''CIEC_«type.name»'''
		}
	}
}
