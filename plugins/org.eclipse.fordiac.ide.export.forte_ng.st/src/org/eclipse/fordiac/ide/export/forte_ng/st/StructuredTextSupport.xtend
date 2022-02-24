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

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
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
import static extension org.eclipse.xtext.util.Strings.convertToJavaString
import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.util.STFunctionUtil.*
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory

abstract class StructuredTextSupport implements ILanguageSupport {
	@Accessors final List<String> errors = newArrayList

	override getInfos() { emptyList }

	override getWarnings() { emptyList }

	def protected CharSequence generateLocalVariables(List<STVarDeclarationBlock> blocks, boolean temp) '''
		«FOR block : blocks»
			«block.generateLocalVariableBlock(temp)»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariableBlock(STVarDeclarationBlock block, boolean temp) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateLocalVariable(temp, block.constant)»
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariable(STVarDeclaration variable, boolean temp, boolean const) {
		if (variable.locatedAt !== null)
			'''«IF !temp»static «ENDIF»«IF const»const «ENDIF»«variable.generateTypeName» «IF !variable.array»&«ENDIF»«variable.generateFeatureName» = «variable.locatedAt.generateFeatureName»;'''
		else
			'''«IF !temp»static «ENDIF»«IF const»const «ENDIF»«variable.generateTypeName» «variable.generateFeatureName»«IF variable.defaultValue !== null» = «variable.defaultValue.generateInitializerExpression»«ENDIF»;'''
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
		for (auto «stmt.variable.generateFeatureName» : ST_FOR_ITER(«stmt.variable.generateFeatureName», «stmt.from.generateExpression», «stmt.to.generateExpression», «IF stmt.by !== null»«stmt.by.generateExpression»«ELSE»1«ENDIF»)) {
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
			case AMPERSAND: '''AND(«expr.left.generateExpression», «expr.right.generateExpression»)'''
			case POWER: '''EXPT(«expr.left.generateExpression», «expr.right.generateExpression»)'''
			default: '''«expr.op.getName»(«expr.left.generateExpression», «expr.right.generateExpression»)'''
		}
	}

	def protected dispatch CharSequence generateExpression(STUnaryExpression expr) //
	'''«expr.op.getName»(«expr.expression.generateExpression»)'''

	def protected dispatch CharSequence generateExpression(STMemberAccessExpression expr) //
	'''«expr.receiver.generateExpression».«expr.member.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STArrayAccessExpression expr) //
	'''«expr.receiver.generateExpression»«FOR index : expr.index»[«index.generateExpression»]«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(STFeatureExpression expr) //
	'''«expr.feature.generateFeatureName»«FOR param : expr.transformedCallArguments BEFORE "(" SEPARATOR ", " AFTER ")"»«param.generateCallArgument»«ENDFOR»'''

	def protected getTransformedCallArguments(STFeatureExpression expr) {
		expr.parameters // TODO match named arguments to parameters
	}

	def protected dispatch CharSequence generateCallArgument(STCallUnnamedArgument arg) { arg.arg.generateExpression }

	def protected dispatch CharSequence generateCallArgument(STCallNamedInputArgument arg) {
		arg.source.generateExpression
	}

	def protected dispatch CharSequence generateCallArgument(STCallNamedOutputArgument arg) //
	'''«IF arg.not»ST_NOT_ARG(«ENDIF»«arg.target.generateFeatureName»«IF arg.not»)«ENDIF»'''

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

	def protected dispatch CharSequence generateFeatureName(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		""
	}

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

	def protected dispatch CharSequence generateFeatureName(VarDeclaration feature) //
	'''«IF feature.rootContainer instanceof BaseFBType»st_«ENDIF»«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(STVarDeclaration feature) '''st_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STFunction feature) '''st_func_«feature.name»'''

	def protected CharSequence generateTypeName(STVarDeclaration variable) {
		if (variable.locatedAt !== null && variable.array) {
			return '''ARRAY_AT<«(variable.type as DataType).generateTypeName», «((variable.locatedAt as STVarDeclaration).type as DataType).generateTypeName», «variable.ranges.head.generateTemplateExpression»>'''
		}
		variable.ranges.reverseView.fold((variable.type as DataType).generateTypeName) [ type, range |
			'''ST_ARRAY<«type», «range.generateTemplateExpression»>'''
		]
	}

	def protected CharSequence generateTypeName(DataType type) {
		switch (type) {
			TimeType,
			LtimeType: "CIEC_TIME"
			DateType,
			LdateType: "CIEC_DATE"
			TimeOfDayType,
			LtodType: "CIEC_TIME_OF_DAY"
			DateAndTimeType,
			LdtType: "CIEC_DATE_AND_TIME"
			default: '''CIEC_«type.name»'''
		}
	}

	def protected int getIntegerFromConstantExpression(STExpression expr) {
		try {
			(expr as STNumericLiteral).value.intValueExact
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
}
