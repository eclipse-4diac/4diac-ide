/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Matthias Plasch, 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The Class Utils.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class Utils {

	/**
	 * Creats a backup file of the specified file.
	 * 
	 * @param in
	 *            the file that should be backuped
	 * 
	 * @return the backup file
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static File createBakFile(final File in) throws IOException {
		String path = in.getAbsolutePath() + ".bak";
		File f;
		int i = 1;
		String temp = path;
		while ((f = new File(temp)).exists()) {
			temp = path + i;
			i++;
		}
		ImportUtils.copyFile(in, f);
		return f;
	}
	



	/**
	 * Default type compatibility check.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * 
	 * @return true, if default type compatibility check
	 */
	public static boolean defaultTypeCompatibilityCheck(
			final VarDeclaration source, final VarDeclaration target) {
		try {
			if (source.getType().getName().toUpperCase().startsWith("ANY")//$NON-NLS-1$
					&& target.getType().getName().toUpperCase()
							.startsWith("ANY")) {//$NON-NLS-1$
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase("ANY") //$NON-NLS-1$
					&& !target.getType().getName().equalsIgnoreCase("ANY")) { //$NON-NLS-1$
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase("ANY") //$NON-NLS-1$
					&& target.getType().getName().equalsIgnoreCase("ANY")) { //$NON-NLS-1$
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase("ANY") //$NON-NLS-1$
					&& target.getType().getName().equalsIgnoreCase("ANY")) { //$NON-NLS-1$
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_ELEMENTARY") //$NON-NLS-1$
					&& !target.getType().getName()
							.equalsIgnoreCase("ANY_ELEMENTARY")) { //$NON-NLS-1$
				return true;
			}
			if (!source.getType().getName().equalsIgnoreCase("ANY_ELEMENTARY") //$NON-NLS-1$
					&& target.getType().getName()
							.equalsIgnoreCase("ANY_ELEMENTARY")) { //$NON-NLS-1$
				return true;
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_ELEMENTARY") //$NON-NLS-1$
					&& target.getType().getName()
							.equalsIgnoreCase("ANY_ELEMENTARY")) { //$NON-NLS-1$
				return checkAnyAnyCompatibility();
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_MAGNITUDE")) {
				return anyMagnitudeCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase("ANY_MAGNITUDE")) {
				return anyMagnitudeCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_NUM")) {
				return anyNumCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase("ANY_NUM")) {
				return anyNumCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_INT")) {
				return anyIntCompatiblity(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase("ANY_INT")) {
				return anyIntCompatiblity(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_REAL")) {
				return anyRealCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase("ANY_REAL")) {
				return anyRealCompatibility(source.getType().getName());
			}

			if (source.getType().getName().equalsIgnoreCase("ANY_BIT")) { //$NON-NLS-1$
				return anyBitCompatibility(target.getType().getName());
			}

			if (target.getType().getName().equalsIgnoreCase("ANY_BIT")) { //$NON-NLS-1$
				return anyBitCompatibility(source.getType().getName());
			}

			if ((source.getType().getName().equalsIgnoreCase("ANY_STRING"))//$NON-NLS-1$
					&& (target.getType().getName().equalsIgnoreCase("STRING") || target.getType().getName().equalsIgnoreCase("WSTRING"))) { //$NON-NLS-1$
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase("ANY_STRING"))//$NON-NLS-1$
					&& (source.getType().getName().equalsIgnoreCase("STRING") || source.getType().getName().equalsIgnoreCase("WSTRING"))) { //$NON-NLS-1$
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase("ANY_STRING") //$NON-NLS-1$
					&& target.getType().getName()
							.equalsIgnoreCase("ANY_STRING")) { //$NON-NLS-1$
				return checkAnyAnyCompatibility();
			}

			if ((source.getType().getName().equalsIgnoreCase("ANY_DATE"))//$NON-NLS-1$
					&& (target.getType().getName().equalsIgnoreCase("DATE")
							|| target.getType().getName()
									.equalsIgnoreCase("DATE_AND_TIME") || target
							.getType().getName()
							.equalsIgnoreCase("TIME_OF_DAY"))) { //$NON-NLS-1$
				return true;
			}
			if ((target.getType().getName().equalsIgnoreCase("ANY_DATE"))//$NON-NLS-1$
					&& (source.getType().getName().equalsIgnoreCase("DATE")
							|| source.getType().getName()
									.equalsIgnoreCase("DATE_AND_TIME") || source
							.getType().getName()
							.equalsIgnoreCase("TIME_OF_DAY"))) { //$NON-NLS-1$
				return true;
			}
			if (source.getType().getName().equalsIgnoreCase("ANY_DATE") //$NON-NLS-1$
					&& target.getType().getName().equalsIgnoreCase("ANY_DATE")) { //$NON-NLS-1$
				return checkAnyAnyCompatibility();
			}
			
			return source.getType().getName()
					.equalsIgnoreCase(target.getType().getName());
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return false;
	}
	
	public static boolean adapaterTypeCompatibilityCheck(final AdapterDeclaration source, final AdapterDeclaration target){
		try {
			if (((source.getType().getName().equals("ANY_ADAPTER")) && (!target.getType().getName().equals("ANY_ADAPTER"))) || //$NON-NLS-1$
					((!source.getType().getName().equals("ANY_ADAPTER")) && (target.getType().getName().equals("ANY_ADAPTER")))) { //$NON-NLS-1$
				return true;
			}
			return source.getType().getName().equalsIgnoreCase(target.getType().getName());
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return false;
	}

	private static boolean checkAnyAnyCompatibility() {
		return false;
	}

	private static boolean anyBitCompatibility(String name) {
		return (name.equalsIgnoreCase("WORD") || name.equalsIgnoreCase("LWORD")
				|| name.equalsIgnoreCase("DWORD")
				|| name.equalsIgnoreCase("BYTE") || name
					.equalsIgnoreCase("BOOL")); //$NON-NLS-1$
	}

	private static boolean anyMagnitudeCompatibility(String name) {
		return (anyNumCompatibility(name) || name.equalsIgnoreCase("TIME")); //$NON-NLS-1$;
	}

	private static boolean anyNumCompatibility(String name) {
		return (anyIntCompatiblity(name) || anyRealCompatibility(name));
	}

	private static boolean anyRealCompatibility(String name) {
		return (name.equalsIgnoreCase("REAL") || name.equalsIgnoreCase("LREAL")); //$NON-NLS-1$;
	}

	private static boolean anyIntCompatiblity(String name) {
		return (name.equalsIgnoreCase("INT") || name.equalsIgnoreCase("UINT") //$NON-NLS-1$;
				|| name.equalsIgnoreCase("SINT") || name.equalsIgnoreCase("LINT") //$NON-NLS-1$;
				|| name.equalsIgnoreCase("DINT") || name.equalsIgnoreCase("USINT")//$NON-NLS-1$;
				|| name.equalsIgnoreCase("UDINT") || name.equalsIgnoreCase("ULINT"));//$NON-NLS-1$;
	}

}
