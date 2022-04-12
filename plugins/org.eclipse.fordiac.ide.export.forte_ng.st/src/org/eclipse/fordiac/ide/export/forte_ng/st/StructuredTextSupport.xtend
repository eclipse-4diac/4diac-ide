/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
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
import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.util.STFunctionUtil.*
import static extension org.eclipse.xtext.util.Strings.convertToJavaString

abstract class StructuredTextSupport implements ILanguageSupport {
	@Accessors final List<String> errors = newArrayList
	int uniqueVariableIndex = 0;

	override getInfos() { emptyList }

	override getWarnings() { emptyList }

	def protected CharSequence generateVariables(Iterable<? extends STVarDeclarationBlock> blocks, boolean decl) '''
		«FOR block : blocks»
			«block.generateVariableBlock(decl)»
		«ENDFOR»
	'''

	def protected CharSequence generateVariableBlock(STVarDeclarationBlock block, boolean decl) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateVariable(decl, block.constant)»
		«ENDFOR»
	'''

	def protected CharSequence generateVariable(STVarDeclaration variable, boolean decl, boolean const) {
		if (variable.locatedAt !== null)
			'''«IF decl»«IF const»const «ENDIF»«variable.generateTypeName» «IF !variable.array»&«ENDIF»«ENDIF»«variable.generateFeatureName» = «variable.locatedAt.generateFeatureName»;'''
		else
			'''«IF decl»«IF const»const «ENDIF»«variable.generateTypeName» «ENDIF»«variable.generateFeatureName» = «variable.generateVariableDefaultValue»;'''
	}

	def protected dispatch CharSequence generateInitializerExpression(STElementaryInitializerExpression expr) {
		expr.value.generateExpression
	}

	def protected dispatch CharSequence generateInitializerExpression(STArrayInitializerExpression expr) //
	'''{«FOR elem : expr.values SEPARATOR ", "»«elem.generateArrayInitElement»«ENDFOR»}'''

	def protected CharSequence generateArrayInitElement(STArrayInitElement elem) //
	'''«IF elem.initExpressions.empty»«elem.indexOrInitExpression.generateExpression»«ELSE»«elem.generateMultiArrayInitElement»«ENDIF»'''

	def protected CharSequence generateMultiArrayInitElement(STArrayInitElement elem) //
	'''«FOR i : 0..<elem.indexOrInitExpression.integerFromConstantExpression SEPARATOR ", "»«FOR initExpression : elem.initExpressions SEPARATOR ", "»«initExpression.generateExpression»«ENDFOR»«ENDFOR»'''

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
	'''«stmt.left.generateExpression» = «stmt.right.generateExpression»;'''

	def protected dispatch CharSequence generateStatement(STIfStatement stmt) '''
		if («stmt.condition.generateExpression») {
		  «stmt.statements.generateStatementList»
		}
		«FOR elseif : stmt.elseifs»
			else if («elseif.condition.generateExpression») {
			  «elseif.statements.generateStatementList»
			}
		«ENDFOR»
		«IF stmt.^else !== null»
			else {
			  «stmt.^else.statements.generateStatementList»
			}
		«ENDIF»
	'''

	def protected dispatch generateStatement(STCaseStatement stmt) '''
		switch («stmt.selector.generateExpression») {
		«FOR clause : stmt.cases»«clause.generateCaseClause»«ENDFOR»
			«IF stmt.^else !== null»
				default:
				  «stmt.^else.statements.generateStatementList»
				  break;
			«ENDIF»
		}
	'''

	def protected generateCaseClause(STCaseCases clause) '''
		case «FOR value : clause.conditions SEPARATOR ' case '»«value.generateExpression»:«ENDFOR»
		  «clause.statements.generateStatementList»
		  break;
	'''

	def protected dispatch generateStatement(STForStatement stmt) '''
		for (auto «generateUniqueVariableName» : ST_FOR_ITER<«stmt.variable.generateTypeName»«IF stmt.by !== null», «(stmt.by.resultType as DataType).generateTypeName»«ENDIF»>(«stmt.variable.generateFeatureName», «stmt.from.generateExpression», «stmt.to.generateExpression»«IF stmt.by !== null», «stmt.by.generateExpression»«ENDIF»)) {
		  «stmt.statements.generateStatementList»
		}
	'''

	def protected dispatch generateStatement(STWhileStatement stmt) '''
		while («stmt.condition.generateExpression») {
		  «stmt.statements.generateStatementList»
		}
	'''

	def protected dispatch generateStatement(STRepeatStatement stmt) '''
		do {
		  «stmt.statements.generateStatementList»
		} while (!((«stmt.condition.generateExpression»)));
	'''

	def protected dispatch CharSequence generateStatement(STContinue stmt) '''continue;'''

	def protected dispatch CharSequence generateStatement(STReturn stmt) '''return;'''

	def protected dispatch CharSequence generateStatement(STExit stmt) '''break;'''

	def protected dispatch CharSequence generateStatement(STCallStatement stmt) '''«stmt.call.generateExpression»;'''

	def protected dispatch CharSequence generateExpression(STExpression expr) {
		errors.add('''The expression «expr.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			case RANGE: '''«expr.left.generateExpression», «expr.right.generateExpression»'''
			case AMPERSAND: '''func_AND(«expr.left.generateExpression», «expr.right.generateExpression»)'''
			case POWER: '''func_EXPT(«expr.left.generateExpression», «expr.right.generateExpression»)'''
			default: '''func_«expr.op.getName»(«expr.left.generateExpression», «expr.right.generateExpression»)'''
		}
	}

	def protected dispatch CharSequence generateExpression(STUnaryExpression expr) //
	'''func_«expr.op.getName»(«expr.expression.generateExpression»)'''

	def protected dispatch CharSequence generateExpression(STMemberAccessExpression expr) //
	'''«expr.receiver.generateExpression».«expr.member.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STArrayAccessExpression expr) //
	'''«expr.receiver.generateExpression»«FOR index : expr.index»[«index.generateExpression»]«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(STFeatureExpression expr) //
	'''«expr.feature.generateFeatureName»«IF expr.call»(«FOR arg : expr.generateCallArguments SEPARATOR ", "»«arg»«ENDFOR»)«ENDIF»'''

	def protected Iterable<CharSequence> generateCallArguments(STFeatureExpression expr) {
		try {
			expr.mappedInputArguments.entrySet.map[key.generateInputCallArgument(value)] +
				expr.mappedOutputArguments.entrySet.map[key.generateOutputCallArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.name»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected CharSequence generateInputCallArgument(INamedElement parameter, STExpression argument) {
		if(argument === null) parameter.generateVariableDefaultValue else argument.generateExpression
	}

	def protected CharSequence generateOutputCallArgument(INamedElement parameter, INamedElement argument) {
		if (argument === null)
			'''ST_IGNORE_OUT_PARAM(«parameter.generateVariableDefaultValue»)'''
		else
			argument.generateFeatureName
	}

	def protected dispatch CharSequence generateExpression(STMultibitPartialExpression expr) //
	'''partial<«expr.specifier.generateMultiBitAccessSpecifier»>(«IF expr.expression !== null»«expr.expression.generateExpression»«ELSE»«expr.index»«ENDIF»)'''

	def protected CharSequence generateMultiBitAccessSpecifier(STMultiBitAccessSpecifier spec) {
		switch (spec) {
			case null,
			case X: "CIEC_BOOL"
			case B: "CIEC_BYTE"
			case W: "CIEC_WORD"
			case D: "CIEC_DWORD"
			case L: "CIEC_LWORD"
		}
	}

	def protected dispatch CharSequence generateExpression(STNumericLiteral expr) //
	'''«(expr.resultType as DataType).generateTypeName»(«expr.value»)'''

	def protected dispatch CharSequence generateExpression(STStringLiteral expr) //
	'''«(expr.resultType as DataType).generateTypeName»("«expr.value.toString.convertToJavaString»")'''

	def protected dispatch CharSequence generateExpression(STDateLiteral expr) //
	'''CIEC_DATE(«expr.value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L»)'''

	def protected dispatch CharSequence generateExpression(STTimeLiteral expr) //
	'''CIEC_TIME(«expr.value.toNanos»)'''

	def protected dispatch CharSequence generateExpression(STTimeOfDayLiteral expr) //
	'''CIEC_TIME_OF_DAY(«expr.value.toNanoOfDay»)'''

	def protected dispatch CharSequence generateExpression(STDateAndTimeLiteral expr) //
	'''CIEC_DATE_AND_TIME(«LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(expr.value, ChronoUnit.NANOS)»)'''

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
		ForteNgExportUtil.generateVariableDefaultValue(variable)
	}

	def protected dispatch CharSequence generateVariableDefaultValue(STVarDeclaration variable) {
		if (variable.defaultValue !== null)
			variable.defaultValue.generateInitializerExpression
		else if (variable.array)
			"{}"
		else
			(variable.type as DataType).generateTypeDefaultValue
	}

	def protected dispatch CharSequence generateFeatureName(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateFeatureName(VarDeclaration feature) //
	'''«IF feature.rootContainer instanceof BaseFBType»st_«ENDIF»«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(STVarDeclaration feature) '''st_lv_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STFunction feature) '''func_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STMethod feature) '''method_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(FB feature) '''fb_«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(Event feature) '''evt_«feature.name»'''

	def protected dispatch INamedElement getType(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		null
	}

	def protected dispatch INamedElement getType(VarDeclaration feature) { feature.type }

	def protected dispatch INamedElement getType(STVarDeclaration feature) { feature.type }

	def protected CharSequence generateTypeName(STVarDeclaration variable) {
		variable.ranges.reverseView.fold((variable.type as DataType).generateTypeName) [ type, range |
			'''ST_ARRAY<«type», «range.generateTemplateExpression»>'''
		]
	}

	def protected int getIntegerFromConstantExpression(STExpression expr) {
		try {
			((expr as STNumericLiteral).value as BigInteger).intValueExact
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
				#[LibraryElementFactory.eINSTANCE.createLibraryElement => [
					name = object.sourceName
				]]
			default:
				emptySet
		}
	}

	def protected generateUniqueVariableName() '''st_lv_synthetic_«uniqueVariableIndex++»'''
}
