/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.util

import java.nio.file.Path
import java.util.Set
import java.util.regex.Pattern
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.fordiac.ide.model.data.AnyDerivedType
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.Connection
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.value.StringValueConverter

final class ForteNgExportUtil {
	public static final CharSequence CONNECTION_EXPORT_PREFIX = "conn_"
	public static final CharSequence VARIABLE_EXPORT_PREFIX = "var_"
	public static final CharSequence EVENT_EXPORT_PREFIX = "evt_"
	public static final CharSequence FB_EXPORT_PREFIX = "fb_"

	public static final Set<String> RESERVED_KEYWORDS = Set.of(
		"alignas",
		"alignof",
		"and",
		"and_eq",
		"asm",
		"atomic_cancel",
		"atomic_commit",
		"atomic_noexcept",
		"auto",
		"bitand",
		"bitor",
		"bool",
		"break",
		"case",
		"catch",
		"char",
		"char8_t",
		"char16_t",
		"char32_t",
		"class",
		"compl",
		"concept",
		"const",
		"consteval",
		"constexpr",
		"constinit",
		"const_cast",
		"continue",
		"contract_assert",
		"co_await",
		"co_return",
		"co_yield",
		"decltype",
		"default",
		"delete",
		"do",
		"double",
		"dynamic_cast",
		"else",
		"enum",
		"explicit",
		"export",
		"extern",
		"false",
		"float",
		"for",
		"friend",
		"goto",
		"if",
		"inline",
		"int",
		"long",
		"mutable",
		"namespace",
		"new",
		"noexcept",
		"not",
		"not_eq",
		"nullptr",
		"operator",
		"or",
		"or_eq",
		"private",
		"protected",
		"public",
		"reflexpr",
		"register",
		"reinterpret_cast",
		"requires",
		"return",
		"short",
		"signed",
		"sizeof",
		"static",
		"static_assert",
		"static_cast",
		"struct",
		"switch",
		"synchronized",
		"template",
		"this",
		"thread_local",
		"throw",
		"true",
		"try",
		"typedef",
		"typeid",
		"typename",
		"union",
		"unsigned",
		"using",
		"virtual",
		"void",
		"volatile",
		"wchar_t",
		"while",
		"xor",
		"xor_eq"
	)

	def static boolean needsGenericAccess(IInterfaceElement element) {
		switch (element) {
			VarDeclaration case element.FBType instanceof CompositeFBType:
				element.inOutVar || (!element.isInput && !element.inputConnections.empty &&
					element.inputConnections.first.sourceElement.type.genericType)
			default:
				false
		}
	}

	def static CharSequence generateName(IInterfaceElement element) {
		switch (element) {
			Event: '''«EVENT_EXPORT_PREFIX»«element.name»'''
			case element.eContainmentFeature == LibraryElementPackage.Literals.
				BASE_FB_TYPE__INTERNAL_CONST_VARS: '''«VARIABLE_EXPORT_PREFIX»const_«element.name»'''
			VarDeclaration case element.FBType instanceof CompositeFBType:
				if (element.isInput || element.inOutVar)
					'''«CONNECTION_EXPORT_PREFIX»if2in_«element.name».getValue()'''
				else if (!element.inputConnections.empty && !element.inputConnections.first.negated)
					element.inputConnections.first.generateConnectionValue
				else
					'''«VARIABLE_EXPORT_PREFIX»«element.name»'''
			default: '''«VARIABLE_EXPORT_PREFIX»«element.name»'''
		}
	}

	def static CharSequence generateConnectionValue(Connection conn) {
		if (conn.sourceElement.type.genericType)
			'''«FB_EXPORT_PREFIX»«conn.sourceElement.name»->getDOConnection(«conn.source.name.FORTEStringId»)->getValue()'''
		else
			'''«FB_EXPORT_PREFIX»«conn.sourceElement.name»->«CONNECTION_EXPORT_PREFIX»«conn.source.name».getValue()'''
	}

	def static CharSequence generateName(FB feature) '''«FB_EXPORT_PREFIX»«feature.name»'''

	def static CharSequence generateName(AdapterFB feature) '''«VARIABLE_EXPORT_PREFIX»«feature.name»'''

	def static CharSequence generateTypeName(LibraryElement type) {
		switch (type) {
			AdapterType: '''«type.generateTypeNamespace»::FORTE_«type.generateTypeNamePlain»'''
			ArrayType:
				type.subranges.reverseView.fold(type.baseType.generateTypeName) [ result, subrange |
					val fixed = subrange.setLowerLimit && subrange.setUpperLimit
					'''«IF fixed»CIEC_ARRAY_FIXED«ELSE»CIEC_ARRAY_VARIABLE«ENDIF»<«result»«IF fixed», «subrange.lowerLimit», «subrange.upperLimit»«ENDIF»>'''
				].toString
			StringType: '''CIEC_«type.generateTypeNamePlain»«IF type.isSetMaxLength»_FIXED<«type.maxLength»>«ENDIF»'''
			AnyElementaryType: '''CIEC_«type.generateTypeNamePlain»'''
			DataType case GenericTypes.isAnyType(type): '''CIEC_«type.generateTypeNamePlain»_VARIANT'''
			DataType: '''«type.generateTypeNamespace»::CIEC_«type.generateTypeNamePlain»'''
			case type.genericType: '''«type.generateTypeNamespace»::«type.genericClassName»'''
			default: '''«type.generateTypeNamespace»::FORTE_«type.generateTypeNamePlain»'''
		}
	}

	def static CharSequence generateTypeNameAsParameter(LibraryElement type) {
		switch (type) {
			AdapterType: '''«type.generateTypeNamespace»::FORTE_«type.generateTypeNamePlain»'''
			ArrayType:
				type.subranges.reverseView.fold(type.baseType.generateTypeName) [ result, subrange |
					'''CIEC_ARRAY_COMMON<«result»>'''
				].toString
			StringType: '''CIEC_«type.generateTypeNamePlain»«IF type.isSetMaxLength»_FIXED<«type.maxLength»>«ENDIF»'''
			AnyElementaryType: '''CIEC_«type.generateTypeNamePlain»'''
			DataType case GenericTypes.isAnyType(type): '''CIEC_«type.generateTypeNamePlain»_VARIANT'''
			DataType: '''«type.generateTypeNamespace»::CIEC_«type.generateTypeNamePlain»'''
			case type.genericType: '''«type.generateTypeNamespace»::«type.genericClassName»'''
			default: '''«type.generateTypeNamespace»::FORTE_«type.generateTypeNamePlain»'''
		}
	}

	def static CharSequence generateTypeSpec(LibraryElement type) {
		PackageNameHelper.getFullTypeName(type).FORTEStringId
	}

	def static CharSequence generateDefiningTypeName(EObject object) {
		switch (object) {
			LibraryElement: object.generateTypeName
			default: object.eResource?.generateDefiningTypeName
		}
	}

	def static CharSequence generateDefiningTypeName(Resource resource) {
		resource.contents.filter(LibraryElement)?.head?.generateTypeName
	}

	def static String generateDefiningInclude(EObject object) {
		switch (object) {
			LibraryElement: object.generateTypeIncludePath
			default: object.eResource?.generateDefiningInclude
		}
	}

	def static String generateDefiningInclude(Resource resource) {
		resource.contents.filter(LibraryElement)?.head?.generateTypeIncludePath ?:
			'''«resource.URI.trimFileExtension.lastSegment».h'''
	}

	def static String generateTypeIncludePath(LibraryElement type) {
		switch (path : type.generateTypePath.join('/')) {
			case !path.empty: '''forte/«path»/«type.generateTypeBasename».h'''
			default: '''forte/«type.generateTypeBasename».h'''
		}
	}

	def static String generateTypeHeaderFileName(LibraryElement type) '''«type.generateTypeBasename».h'''

	def static String generateTypeSourceFileName(LibraryElement type) '''«type.generateTypeBasename».cpp'''

	def static Path generateTypeHeaderFilePath(LibraryElement type) {
		Path.of("include", "forte").resolve(type.generateTypePath)
	}

	def static Path generateTypeSourceFilePath(LibraryElement type) {
		Path.of("src").resolve(type.generateTypePath)
	}

	def static String generateTypeBasename(LibraryElement type) {
		switch (type) {
			TimeType:
				"forte_time"
			LtimeType:
				"forte_ltime"
			DateType:
				"forte_date"
			LdateType:
				"forte_ldate"
			TimeOfDayType:
				"forte_time_of_day"
			LtodType:
				"forte_ltime_of_day"
			DateAndTimeType:
				"forte_date_and_time"
			LdtType:
				"forte_ldate_and_time"
			StringType case type.isSetMaxLength:
				"forte_string_fixed"
			StringType:
				"forte_string"
			WstringType:
				"forte_wstring"
			ArrayType:
				type.baseType.generateTypeBasename
			AdapterType:
				type.name + "_adp"
			AnyDerivedType:
				type.name + "_dtp"
			DataType case GenericTypes.isAnyType(type): '''forte_«type.generateTypeNamePlain.toLowerCase»_variant'''
			DataType: '''forte_«type.name.toLowerCase»'''
			FunctionFBType case type.genericType:
				type.genericClassName + "_fct"
			FunctionFBType:
				type.name + "_fct"
			FBType case type.genericType:
				type.genericClassName + "_fbt"
			FBType:
				type.name + "_fbt"
			GlobalConstants:
				type.name + "_gcf"
			default:
				type.name
		}
	}

	def static Path generateTypePath(LibraryElement type) {
		switch (type) {
			ArrayType:
				type.baseType.generateTypePath
			AnyType case type.typeEntry === null:
				Path.of("datatypes")
			default:
				Path.of("", PackageNameHelper.getPackageName(type).split("::"))
		}
	}

	def static String generateTypeNamespace(LibraryElement type) {
		val packageName = PackageNameHelper.getPackageName(type)
		if (packageName.nullOrEmpty)
			return "forte"
		"forte::" + escapePackageName(packageName)
	}

	def static String escapePackageName(String packageName) {
		packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER).map[escapeKeyword].join(
			PackageNameHelper.PACKAGE_NAME_DELIMITER)
	}

	def static String escapeKeyword(String name) {
		if (RESERVED_KEYWORDS.contains(name))
			name + "_"
		else
			name
	}

	def static String generateTypeNamePlain(LibraryElement type) {
		switch (type) {
			TimeType:
				"TIME"
			LtimeType:
				"LTIME"
			DateType:
				"DATE"
			LdateType:
				"LDATE"
			TimeOfDayType:
				"TIME_OF_DAY"
			LtodType:
				"LTIME_OF_DAY"
			DateAndTimeType:
				"DATE_AND_TIME"
			LdtType:
				"LDATE_AND_TIME"
			ArrayType:
				"ARRAY"
			StringType:
				"STRING"
			WstringType:
				"WSTRING"
			default:
				type.name
		}
	}

	def static CharSequence getFORTEStringId(String s) '''"«s»"_STRID'''

	def static int getAbsoluteDataPortIndex(IInterfaceElement element) {
		val interfaceList = element.interfaceList
		if (interfaceList !== null) {
			switch (element.eContainmentFeature) {
				case LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS:
					0
				case LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS:
					interfaceList.inputVars.size
				case LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS,
				case LibraryElementPackage.Literals.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
					interfaceList.inputVars.size + interfaceList.outputVars.size
				default:
					0
			} + element.interfaceElementIndex
		} else
			element.interfaceElementIndex
	}

	def static int getInterfaceElementIndex(IInterfaceElement element) {
		if (element.eContainer !== null && element.eContainingFeature.many) {
			(element.eContainer.eGet(element.eContainingFeature) as EList<? extends IInterfaceElement>).indexOf(element)
		} else
			0
	}

	static final Pattern END_COMMENT_PATTERN = Pattern.compile("\\*/")

	def static CharSequence escapeMultilineCommentString(CharSequence string) {
		END_COMMENT_PATTERN.matcher(string).replaceAll("* /")
	}

	static final String GENERIC_CLASS_NAME_ATTRIBUTE = "GenericClassName"

	def static boolean isGenericType(LibraryElement type) {
		type.attributes.exists[name == GENERIC_CLASS_NAME_ATTRIBUTE]
	}

	def static String getGenericClassName(LibraryElement type) {
		StringValueConverter.INSTANCE.toValue(type.attributes.findFirst[name == GENERIC_CLASS_NAME_ATTRIBUTE].value)
	}

	private new() {
		throw new UnsupportedOperationException
	}
}
