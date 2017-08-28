/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import static extension org.eclipse.xtext.util.Strings.convertToJavaString

class LuaUtils {
	
	def static luaString(String s) '''"«s.convertToJavaString»"'''
	
	def static luaStringList(Iterable<String> list)
		'''{«FOR value : list SEPARATOR ', '»«value.luaString»«ENDFOR»}'''

	def static luaIntegerList(Iterable<Integer> list)
		'''{«FOR value : list SEPARATOR ', '»«value.toString»«ENDFOR»}'''

	def static luaValueList(Iterable<?> list)
		'''{«FOR value : list SEPARATOR ', '»«IF value instanceof String»«value.luaString»«ELSE»«value.toString»«ENDIF»«ENDFOR»}'''
}