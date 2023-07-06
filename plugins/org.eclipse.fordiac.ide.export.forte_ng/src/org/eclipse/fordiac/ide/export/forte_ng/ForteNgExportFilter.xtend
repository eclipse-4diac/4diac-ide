/*******************************************************************************
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
 *                - added support for custom language templates
 *   Alois Zoitl  - added support for structured types
 *   Ernst Blecha - added support for exporting CMakeLists.txt
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Paths
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.TemplateExportFilter
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.cmake.CMakeListsTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructBaseTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeImplTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker

class ForteNgExportFilter extends TemplateExportFilter {

	public static final String OPTION_TYPE = "type"
	public static final String OPTION_TYPE_PARAM = "type_param"
	public static final String OPTION_HEADER = "header"

	override protected getTemplates(String name, EObject source) {
		switch (source) {
			BasicFBType:
				#{
					new BasicFBHeaderTemplate(source, '''«name».h''', Paths.get("")),
					new BasicFBImplTemplate(source, '''«name».cpp''', Paths.get(""))
				}
			SimpleFBType:
				#{
					new SimpleFBHeaderTemplate(source, '''«name».h''', Paths.get("")),
					new SimpleFBImplTemplate(source, '''«name».cpp''', Paths.get(""))
				}
			FunctionFBType:
				#{
					new FunctionFBHeaderTemplate(source, '''«name».h''', Paths.get("")),
					new FunctionFBImplTemplate(source, '''«name».cpp''', Paths.get(""))
				}
			CompositeFBType:
				#{
					new CompositeFBHeaderTemplate(source, '''«name».h''', Paths.get("")),
					new CompositeFBImplTemplate(source, '''«name».cpp''', Paths.get(""))
				}
			AdapterType:
				#{
					new AdapterFBHeaderTemplate(source.adapterFBType, '''«name».h''', Paths.get("")),
					new AdapterFBImplTemplate(source.adapterFBType, '''«name».cpp''', Paths.get(""))
				}
			ServiceInterfaceFBType:
				#{
					new ServiceInterfaceFBHeaderTemplate(source, '''«name».h''', Paths.get("")),
					new ServiceInterfaceFBImplTemplate(source, '''«name».cpp''', Paths.get(""))
				}
			StructuredType:
				#{
					new StructuredTypeHeaderTemplate(source, '''«StructBaseTemplate.structuredTypeFileName(source)».h''',
						Paths.get("")),
					new StructuredTypeImplTemplate(source, '''«StructBaseTemplate.structuredTypeFileName(source)».cpp''',
						Paths.get(""))
				}
			CMakeListsMarker:
				#{
					new CMakeListsTemplate('''CMakeLists.txt''', Paths.get(""))
				}
			default: {
				val languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", source)
				if (languageSupport !== null) {
					#{
						new LanguageHeaderTemplate(languageSupport, '''«name».h''', Paths.get("")),
						new LanguageImplTemplate(languageSupport, '''«name».cpp''', Paths.get(""))
					}
				} else {
					errors.add('''Unknown source type «source.eClass.name»''')
					emptySet
				}
			}
		}
	}
}
