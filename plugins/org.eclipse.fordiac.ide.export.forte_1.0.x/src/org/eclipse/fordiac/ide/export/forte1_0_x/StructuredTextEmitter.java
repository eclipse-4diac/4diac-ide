/*******************************************************************************
 * Copyright (c) 2010 - 20147 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger, Ingo Hegny, Matthias Plasch, Jose Cabral,
 *   Martin Melik Merkumians, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte1_0_x;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.fordiac.ide.export.ExportFilter.VarDefinition;
import org.eclipse.fordiac.ide.export.forte1_0_x.ForteExportFilter1_0_x.AdapterInstance;
import org.eclipse.fordiac.ide.util.STStringTokenHandling;

public class StructuredTextEmitter {

	private enum Conditionals {
		IF, IF_ELSE, CASE, CASE_ELSE
	}

	private boolean inExpression;

	private PrintWriter pwCPP = null;

	private ForteExportFilter1_0_x exportFilter;

	private List<Conditionals> conditionalQueue = new LinkedList<>();

	private static class ForValBuffer {
		private String forByVal;
		private String forControlVal;
	}

	List<ForValBuffer> forValBuf = new ArrayList<>();

	public StructuredTextEmitter(ForteExportFilter1_0_x exportFilter) {
		this.exportFilter = exportFilter;
	}

	public void exportStructuredTextAlgorithm(final String src, final PrintWriter pwCPP) {
		inExpression = false;
		conditionalQueue.clear();
		this.pwCPP = pwCPP;

		if (null != this.pwCPP) {
			StringTokenizer t = new StringTokenizer(src, STStringTokenHandling.stTokenDelimiters, true);
			while (t.hasMoreElements()) {
				String s = t.nextToken();
				exportSTStatement(s, t);
			}
		}
	}

	public void exportGuardCondition(final String guard, final PrintWriter pwCPP) {
		inExpression = true;
		conditionalQueue.clear();
		this.pwCPP = pwCPP;

		if (null != this.pwCPP) {
			StringTokenizer t = new StringTokenizer(guard, STStringTokenHandling.stTokenDelimiters, true);
			while (t.hasMoreElements()) {
				String s = t.nextToken();
				exportSTStatement(s, t);
			}
		}
	}

	private void exportSTComment(final StringTokenizer tokens) {
		pwCPP.print("/* "); //$NON-NLS-1$

		String var;
		boolean endTag = false;
		while (!endTag) {
			do {
				if (!tokens.hasMoreElements()) {
					return;
				}
				var = tokens.nextToken();
				pwCPP.print(var);
			} while (!var.equals("*")); //$NON-NLS-1$
			if (!tokens.hasMoreElements()) {
				return;
			}
			var = tokens.nextToken();
			if (var.equals(")")) { //$NON-NLS-1$
				endTag = true;
			} else {
				pwCPP.print(var);
			}
		}
		pwCPP.print("/"); //$NON-NLS-1$
	}

	private void exportSTStatement(String statement, final StringTokenizer tokens) {
		String upperStatement = statement.toUpperCase();
		if (upperStatement.equals("XOR")) { //$NON-NLS-1$
			pwCPP.print("^"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("MOD")) { //$NON-NLS-1$
			pwCPP.print(" % "); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("TRUE")) { //$NON-NLS-1$
			pwCPP.print("true"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("FALSE")) { //$NON-NLS-1$
			pwCPP.print("false"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("NOT")) { //$NON-NLS-1$
			pwCPP.print("!"); //$NON-NLS-1$
			return;
		}
		if (statement.equals(":")) { //$NON-NLS-1$
			if (!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("=")) { //$NON-NLS-1$
				pwCPP.print(" = "); //$NON-NLS-1$
			} else {
				pwCPP.print(" : "); //$NON-NLS-1$
				exportSTStatement(statement, tokens);
			}
			return;
		}
		if (statement.equals(".")) { //$NON-NLS-1$
			// a dot marks the access of a structure member or in/out puts of adapters
			// TODO: check for multiple levels!
			pwCPP.print("."); //$NON-NLS-1$
			if (!tokens.hasMoreElements()) {
				return;
			}
			statement = tokens.nextToken();
			exportSTStatement(statement, tokens);
			if ((!Character.isDigit(statement.codePointAt(0))) && (null == exportFilter.getVars().get(statement))) {
				pwCPP.print("()"); // in c++ we have access functions named equaly to the members //$NON-NLS-1$
			}
			return;
		}

		if ((upperStatement.equals("AND")) || (statement.equals("&"))) { //$NON-NLS-1$ //$NON-NLS-2$
			if (inExpression) {
				pwCPP.print(") && ("); //$NON-NLS-1$
			} else {
				pwCPP.print("&"); //$NON-NLS-1$
			}
			return;
		}

		if (upperStatement.equals("OR")) { //$NON-NLS-1$
			if (inExpression) {
				pwCPP.print(") || ("); //$NON-NLS-1$
			} else {
				pwCPP.print("|"); //$NON-NLS-1$
			}
			return;
		}
		if (statement.equals("<")) { //$NON-NLS-1$
			if (!tokens.hasMoreElements()) {
				return;
			}
			statement = tokens.nextToken();
			if (statement.equals("=")) { //$NON-NLS-1$
				pwCPP.print(" <= "); //$NON-NLS-1$
			} else if (statement.equals(">")) { //$NON-NLS-1$
				pwCPP.print(" != "); //$NON-NLS-1$
			} else {
				pwCPP.print(" < "); //$NON-NLS-1$
				exportSTStatement(statement, tokens);
			}
			return;
		}
		if (statement.equals(">")) { //$NON-NLS-1$
			if (!tokens.hasMoreElements()) {
				return;
			}
			statement = tokens.nextToken();
			if (statement.equals("=")) { //$NON-NLS-1$
				pwCPP.print(" >= "); //$NON-NLS-1$
			} else {
				pwCPP.print(" > "); //$NON-NLS-1$
				exportSTStatement(statement, tokens);
			}
			return;
		}

		if (statement.equals("=")) { //$NON-NLS-1$
			pwCPP.print(" == "); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("RETURN")) { //$NON-NLS-1$
			pwCPP.print("return"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("EXIT")) { //$NON-NLS-1$
			pwCPP.print("break"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("CASE")) { //$NON-NLS-1$
			emitCASEStatement(tokens);
			return;
		}

		if (upperStatement.equals("END_IF")) { //$NON-NLS-1$
			pwCPP.print("}"); //$NON-NLS-1$
			if (!conditionalQueue.isEmpty()) {
				Conditionals cond = conditionalQueue.get(conditionalQueue.size() - 1);
				conditionalQueue.remove(conditionalQueue.size() - 1);
				if ((Conditionals.IF != cond) && (Conditionals.IF_ELSE != cond)) {
					exportFilter.addErrorMsg("END_IF without corresponding IF statement found!");
				}
			} else {
				exportFilter.addErrorMsg("END_IF without corresponding IF statement found!");
			}
			return;
		}

		if (upperStatement.equals("END_FOR")) { //$NON-NLS-1$
			exportEndForStatement();
			return;
		}

		if (upperStatement.equals("END_WHILE")) { //$NON-NLS-1$
			pwCPP.print("}"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("ELSE")) { //$NON-NLS-1$
			Conditionals cond = conditionalQueue.get(conditionalQueue.size() - 1);

			if (Conditionals.IF == cond || Conditionals.IF_ELSE == cond) {
				pwCPP.print("}\nelse{"); //$NON-NLS-1$
				if (!conditionalQueue.isEmpty()) {
					conditionalQueue.remove(conditionalQueue.size() - 1);
				}
				conditionalQueue.add(Conditionals.IF_ELSE);

			} else {
				if (Conditionals.CASE == cond) {
					pwCPP.print(" break;\ndefault: "); //$NON-NLS-1$
					if (!conditionalQueue.isEmpty()) {
						conditionalQueue.remove(conditionalQueue.size() - 1);
					}
					conditionalQueue.add(Conditionals.CASE_ELSE);
				} else {
					exportFilter.addErrorMsg("Else without preceding IF, ELSIF, or CASE statement");
				}
			}
			return;
		}
		if (upperStatement.equals("ELSIF")) { //$NON-NLS-1$
			inExpression = true;

			Conditionals cond = conditionalQueue.get(conditionalQueue.size() - 1);

			if (Conditionals.IF == cond || Conditionals.IF_ELSE == cond) {
				pwCPP.print("}\nelse\n  if(("); //$NON-NLS-1$
				if (!conditionalQueue.isEmpty()) {
					conditionalQueue.remove(conditionalQueue.size() - 1);
				}
				conditionalQueue.add(Conditionals.IF_ELSE);
			}
			return;
		}
		if (upperStatement.equals("IF")) { //$NON-NLS-1$
			inExpression = true;
			conditionalQueue.add(Conditionals.IF);
			pwCPP.print("if(("); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("THEN")) { //$NON-NLS-1$
			inExpression = false;
			pwCPP.print(")){"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("REPEAT")) { //$NON-NLS-1$
			pwCPP.println("do{"); //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("UNTIL")) { //$NON-NLS-1$
			pwCPP.print("}\nwhile(("); //$NON-NLS-1$
			inExpression = true;
			return;
		}
		if (upperStatement.equals("END_REPEAT")) { //$NON-NLS-1$
			inExpression = false;
			pwCPP.print("));"); // close the do while statement //$NON-NLS-1$
			return;
		}
		if (upperStatement.equals("WHILE")) { //$NON-NLS-1$
			pwCPP.print("while(("); //$NON-NLS-1$
			inExpression = true;
			return;
		}
		if (upperStatement.equals("DO")) { //$NON-NLS-1$
			inExpression = false;
			pwCPP.println(")){"); // close the while statement //$NON-NLS-1$
			return;
		}

		if (upperStatement.equals("FOR")) { //$NON-NLS-1$
			try {
				exportForStatement(tokens);
			} catch (StructuredTextException e) {
				exportFilter.addErrorMsg(e.getMessage());
			}
			return;
		}

		if (upperStatement.equals("VAR")) { //$NON-NLS-1$
			try {
				exportLokalVariables(tokens);
			} catch (StructuredTextException e) {
				exportFilter.addErrorMsg(e.getMessage());
			}
			return;
		}

		if (upperStatement.equals("]")) { //$NON-NLS-1$
			pwCPP.print("]"); //$NON-NLS-1$
			return;
		}

		if (statement.equals("(")) { //$NON-NLS-1$
			if (!tokens.hasMoreElements()) {
				return;
			}
			statement = tokens.nextToken();
			if (statement.equals("*")) { //$NON-NLS-1$
				exportSTComment(tokens);
			} else {
				if (statement.equals(")")) { //$NON-NLS-1$
					pwCPP.print("()"); //$NON-NLS-1$
				} else {
					pwCPP.print("(("); //$NON-NLS-1$
					exportSTStatement(statement, tokens);
				}
			}
			return;
		}

		if (statement.equals(")")) { //$NON-NLS-1$
			pwCPP.print("))"); //$NON-NLS-1$
			return;
		}

		if (statement.equals(",")) { //$NON-NLS-1$
			pwCPP.print("), ("); //$NON-NLS-1$
			return;
		}

		if (statement.equals("/")) { //$NON-NLS-1$
			pwCPP.print(statement);
			if (!tokens.hasMoreElements()) {
				return;
			}
			statement = tokens.nextToken();
			if (statement.equals("/")) { //$NON-NLS-1$
				pwCPP.print("/ "); //$NON-NLS-1$
				if (!tokens.hasMoreElements()) {
					return;
				}
				statement = tokens.nextToken();
				// print till end of line
				while (!statement.equals("\n") && !statement.equals("\r")) { //$NON-NLS-1$ //$NON-NLS-2$
					pwCPP.print(statement);
					if (!tokens.hasMoreElements())
						return;
					statement = tokens.nextToken();
				}
				pwCPP.println(""); // issue a new line //$NON-NLS-1$
			} else {
				if (statement.equals("*")) { // multi line c-style comment //$NON-NLS-1$
					pwCPP.print("* "); //$NON-NLS-1$
					if (!tokens.hasMoreElements()) {
						return;
					}
					statement = tokens.nextToken();
					pwCPP.print(statement);
					// print till end of multi line comment
					String var;
					boolean endTag = false;
					while (!endTag) {
						do {
							if (!tokens.hasMoreElements()) {
								return;
							}
							var = tokens.nextToken();
							pwCPP.print(var);
						} while (!var.equals("*")); //$NON-NLS-1$
						if (!tokens.hasMoreElements())
							return;
						var = tokens.nextToken();
						if (var.equals("/")) { //$NON-NLS-1$
							endTag = true;
						} else {
							pwCPP.print(var);
						}
					}
					pwCPP.print("/"); //$NON-NLS-1$
				} else {
					exportSTStatement(statement, tokens);
				}
			}
			return;
		}

		if (statement.equals("!")) { //$NON-NLS-1$
			exportFilter.addErrorMsg(
					" Found wrong statement in ST code: \'!\' Export may not be correct. Please check exported C++ code!");
			pwCPP.print(statement);
			return;
		}

		if (statement.equals("\"")) { //$NON-NLS-1$
			exportWStringLiteral(tokens);
			return;
		}

		if (statement.equals("\'")) { //$NON-NLS-1$
			exportStringLiteral(tokens);
			return;
		}

		if (!statement.equals(" ")) { //$NON-NLS-1$
			VarDefinition var = exportFilter.getVars().get(statement);
			// check whether statement is a variable
			if (var != null) {
//				String arraySize = (var).arraySize;
//				if (!arraySize.equals("")) {
//					// Variable is an array
//					//TODO: Check!
//					pwCPP.print("(*((CIEC_" + var.type + "*) " + statement+ "()");
//				} else {
				// Variable, but no array. No special treatment necessary
				pwCPP.print(statement + "()"); //$NON-NLS-1$
//				}
			} else {
				List<AdapterInstance> myAdapters = exportFilter.getAdapters();
				boolean isAnAdapter = false;
				Iterator<AdapterInstance> myIter = myAdapters.iterator();
				while (myIter.hasNext()) {
					AdapterInstance myAdapter = myIter.next();
					if (myAdapter.getName().equals(statement)) {
						// we have an adapter!
						isAnAdapter = true;
						break;
					}
				}
				// No special treatment necessary
				if (isAnAdapter) {
					pwCPP.print(statement + "()"); //$NON-NLS-1$
				} else {
					if (statement.contains("#")) { //$NON-NLS-1$
						exportTypedLiteral(statement);
					} else {
						// No special treatment necessary
						pwCPP.print(statement);
					}
				}

			}
		}
	}

	private void exportTypedLiteral(String statement) {
		int hashIndex = statement.indexOf('#');

		String prefix = statement.substring(0, hashIndex);
		statement = statement.substring(hashIndex + 1);

		int type = isTypeName(prefix);
		if (0 != type) {

			pwCPP.print("CIEC_" + getDataTypeNameFromPrefix(prefix) + "("); //$NON-NLS-1$ //$NON-NLS-2$
			if (2 == type) {
				// it is a complex type we will give the string for parsing
				pwCPP.print("\"" + prefix + "#"); //$NON-NLS-1$ //$NON-NLS-2$
				hashIndex = -1;
			} else {
				// only for simple types we try to find a second #
				hashIndex = statement.indexOf('#');
				if (-1 != hashIndex) {
					prefix = statement.substring(0, hashIndex);
					statement = statement.substring(hashIndex + 1);
				}
			}
		}

		if (-1 != hashIndex) {
			handleLiteralBasePrefix(prefix);
		}

		pwCPP.print(statement);

		if (0 != type) {
			if (2 == type) {
				// it is a complex type we will give the string for parsing
				pwCPP.print("\""); //$NON-NLS-1$
			}
			pwCPP.print(")"); // close the type construction //$NON-NLS-1$
		}

	}

	/*
	 * Return the appropiate data type name for a given prefix. This function is in
	 * charge of handling data type prefix shortcuts, e.g., T#,
	 */
	private static String getDataTypeNameFromPrefix(String prefix) {
		if (("t".equals(prefix)) || ("T".equals(prefix))) { //$NON-NLS-1$ //$NON-NLS-2$
			return "TIME"; //$NON-NLS-1$
		}
		if (("d".equals(prefix)) || ("D".equals(prefix))) { //$NON-NLS-1$ //$NON-NLS-2$
			return "DATE"; //$NON-NLS-1$
		}
		if (("tod".equals(prefix)) || ("TOD".equals(prefix))) { //$NON-NLS-1$ //$NON-NLS-2$
			return "TIME_OF_DAY"; //$NON-NLS-1$
		}
		if (("dt".equals(prefix)) || ("DT".equals(prefix))) { //$NON-NLS-1$ //$NON-NLS-2$
			return "DATE_AND_TIME"; //$NON-NLS-1$
		}
		return prefix;
	}

	// Dataypes for which we will create correct C litarals
	private static final String elementaryTypeNames[] = { "BOOL", "SINT", "INT", "DINT", "LINT", "USINT", "UINT", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			"UDINT", "ULINT", "REAL", "LREAL", "BYTE", "WORD", "DWORD", "LWORD" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$

	private static int isTypeName(String prefix) {
		int retVal = 0;

		if (!(prefix.startsWith("1") || prefix.startsWith("8") || prefix.startsWith("2"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			for (String typeName : elementaryTypeNames) {
				if (typeName.equals(prefix)) {
					return 1;
				}
			}
			retVal = 2;
		}

		return retVal;
	}

	private void handleLiteralBasePrefix(String prefix) {
		if (prefix.equals("16")) { //$NON-NLS-1$
			pwCPP.print("0x"); //$NON-NLS-1$
		} else if (prefix.equals("8")) { //$NON-NLS-1$
			pwCPP.print("0"); //$NON-NLS-1$
		} else if (prefix.equals("2")) { //$NON-NLS-1$
			pwCPP.print("0b"); //$NON-NLS-1$
		} else {
			exportFilter.addErrorMsg("Wrong literal base prefix provided: " + prefix + "#");
		}

	}

	private void exportLokalVariables(StringTokenizer tokens) throws StructuredTextException {

		while (tokens.hasMoreElements()) {
			String token = getNextNoneSpaceToken(tokens);
			if (token.equals("END_VAR")) { //$NON-NLS-1$
				return;
			}
			if (!token.equals("\n") && !token.equals("\t") && !token.equals(" ")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				String varName = token;

				token = getNextNoneSpaceToken(tokens);
				if ((null != token) && (token.equals(":"))) { //$NON-NLS-1$
					token = getNextNoneSpaceToken(tokens);

					if (null != token) {
						if (token.equals("ARRAY")) { //$NON-NLS-1$
							exportLocalArray(varName, tokens);
						} else {
							pwCPP.print("CIEC_" + token + " "); //$NON-NLS-1$ //$NON-NLS-2$
							pwCPP.print(varName);
							handleVarInitialisation(tokens, ""); //$NON-NLS-1$
						}
						pwCPP.println(";"); //$NON-NLS-1$

					} else {
						throw new StructuredTextException("Variable declaration is missing the type");
					}
				} else {
					throw new StructuredTextException(
							"Variable declaration is missing the : between var name and type");
				}
			}
		}
		throw new StructuredTextException("VAR statement not closed with END_VAR.");
	}

	private void exportLocalArray(String varName, StringTokenizer tokens) throws StructuredTextException {
		String token = getNextNoneSpaceToken(tokens);
		if ((null != token) && (token.equals("["))) { //$NON-NLS-1$
			token = getNextNoneSpaceToken(tokens);
			if ((null != token) && (token.equals("."))) { //$NON-NLS-1$
				if (tokens.hasMoreElements() && tokens.nextToken().equals(".")) { //$NON-NLS-1$
					String upper = getNextNoneSpaceToken(tokens);
					token = getNextNoneSpaceToken(tokens);
					if ((null != token) && (token.equals("]"))) { //$NON-NLS-1$
						token = getNextNoneSpaceToken(tokens);
						if ((null != token) && (token.equals("OF"))) { //$NON-NLS-1$
							token = getNextNoneSpaceToken(tokens);
							if (null != token) {
								// TODO consider lower range
								pwCPP.print("CIEC_ARRAY " + varName + "(" + upper + ", g_nStringId" + token + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
								handleVarInitialisation(tokens, ".fromString"); //$NON-NLS-1$
							}

						} else {
							throw new StructuredTextException("Array OF missing");
						}

					} else {
						throw new StructuredTextException("Array size end missing");
					}
				} else {
					throw new StructuredTextException("Array var range missing ..");
				}
			} else {
				throw new StructuredTextException("Array var range missing ..");
			}
		} else {
			throw new StructuredTextException("Array size start missing");
		}
	}

	private static String getNextNoneSpaceToken(StringTokenizer tokens) {
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			if (!token.equals("\n") && !token.equals("\t") && !token.equals(" ") && !token.equals("\r")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				return token;
			}
		}
		return null;
	}

	private void handleVarInitialisation(StringTokenizer tokens, String initStartCode) throws StructuredTextException {
		String token = getNextNoneSpaceToken(tokens);
		if (null != token) {
			if (":".equals(tokens.nextToken())) { //$NON-NLS-1$
				if (tokens.hasMoreElements() && tokens.nextToken().equals("=")) { //$NON-NLS-1$
					pwCPP.print(initStartCode + "(\""); //$NON-NLS-1$
				} else {
					throw new StructuredTextException("VAR initialisation is mising =.");
				}

				while (tokens.hasMoreElements()) {
					token = tokens.nextToken();
					if (token.equals(";")) { //$NON-NLS-1$
						pwCPP.print("\")"); //$NON-NLS-1$
						return;
					}
					pwCPP.print(token);
				}
			} else {
				// if we don't have an assignment we need a closing statement
				if (";".equals(token)) { //$NON-NLS-1$
					return;
				}
			}
		}

		throw new StructuredTextException("variable statement not closed with ;.");
	}

	private void exportEndForStatement() {

		if (!forValBuf.isEmpty()) {
			ForValBuffer forVals = forValBuf.get(forValBuf.size() - 1);
			String strippedForControlVal = forVals.forControlVal.replace("()", "_"); //$NON-NLS-1$ //$NON-NLS-2$

			pwCPP.println("\n      if(((is" + strippedForControlVal + "Up) && ((" + forVals.forByVal + ") > 0)) || "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			pwCPP.println("         ((!is" + strippedForControlVal + "Up) && ((" + forVals.forByVal + ") < 0))){"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			pwCPP.println("        " + forVals.forControlVal + " = " + forVals.forControlVal + " + (" + forVals.forByVal //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ");"); //$NON-NLS-1$
			pwCPP.println("      }"); //$NON-NLS-1$
			pwCPP.println("      else{"); //$NON-NLS-1$
			pwCPP.println("        " + forVals.forControlVal + " = " + forVals.forControlVal + " - (" + forVals.forByVal //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ");"); //$NON-NLS-1$
			pwCPP.println("      }"); //$NON-NLS-1$
			pwCPP.println("    }"); //$NON-NLS-1$
			pwCPP.println("  }"); //$NON-NLS-1$

			forValBuf.remove(forVals);
		} else {
			exportFilter.addErrorMsg("For loop closed without an according open statement!");
		}
	}

	private void exportWStringLiteral(final StringTokenizer tokens) {
		String statement;
		pwCPP.print("\""); //$NON-NLS-1$
		do {
			statement = tokens.nextToken();
			if (statement.equals("$")) { //$NON-NLS-1$
				statement = tokens.nextToken();
				if (statement.equals("\"")) { //$NON-NLS-1$
					pwCPP.print("\\\""); //$NON-NLS-1$
					statement = ""; // necessary that we don't end the loop as it is a dollar escaped //$NON-NLS-1$
									// ' it is not the end of the wstring
				} else {
					// TODO handle dollar escaping
				}
			} else {
				pwCPP.print(statement);
			}
		} while (!statement.equals("\"")); //$NON-NLS-1$
	}

	private void exportStringLiteral(final StringTokenizer tokens) {
		String statement;

		pwCPP.print("\""); //$NON-NLS-1$
		do {
			statement = tokens.nextToken();
			if (statement.equals("\"")) { //$NON-NLS-1$
				pwCPP.print("\\\""); //$NON-NLS-1$
			} else {
				if (statement.equals("'")) { //$NON-NLS-1$
					pwCPP.print("\""); //$NON-NLS-1$
				} else {
					if (statement.equals("$")) { //$NON-NLS-1$
						statement = tokens.nextToken();
						if (statement.equals("'")) { //$NON-NLS-1$
							pwCPP.print("'"); //$NON-NLS-1$
							statement = ""; // necessary that we don't end the loop as it is a dollar //$NON-NLS-1$
											// escaped ' it is not the end of the wstring
						} else {
							// TODO handle dollar escaping
						}
					} else {
						pwCPP.print(statement);
					}
				}
			}
		} while (!statement.equals("\'")); //$NON-NLS-1$
	}

	private void exportForStatement(final StringTokenizer tokens) throws StructuredTextException {

		ForValBuffer forVals = new ForValBuffer();

		forVals.forByVal = "1"; //$NON-NLS-1$

		forVals.forControlVal = getForVar(tokens, ":"); //$NON-NLS-1$

		inExpression = true;

		String buf = tokens.nextToken();
		if ((null != buf) && (buf.equals("="))) { //$NON-NLS-1$
			String forInitialVal = getForVar(tokens, "TO"); //$NON-NLS-1$

			String forToVal = getForToAndBy(tokens, forVals);
			String strippedForControlVal = forVals.forControlVal.replace("()", "_"); //$NON-NLS-1$ //$NON-NLS-2$

			pwCPP.println("  {"); //$NON-NLS-1$
			pwCPP.println("    bool is" + strippedForControlVal + "Up = ((" + forVals.forByVal + ") > 0);"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			pwCPP.println("    " + forVals.forControlVal + " = " + forInitialVal + ";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			pwCPP.println("    while(!(((is" + strippedForControlVal + "Up) && (" + forVals.forControlVal + " > (" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ forToVal + "))) ||"); //$NON-NLS-1$
			pwCPP.println("            ((!is" + strippedForControlVal + "Up) && (" + forVals.forControlVal + " < (" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ forToVal + "))))){"); //$NON-NLS-1$

			forValBuf.add(forVals);
		} else {
			throw new StructuredTextException(
					"Unexpected end of algorithm in for argument parsing! Expecting '=' statement.");
		}
		inExpression = false;
	}

	private String getForVar(StringTokenizer tokens, String delim) throws StructuredTextException {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		PrintWriter bufPrintWriter = pwCPP;
		pwCPP = printWriter;

		String buf = getNextNoneSpaceToken(tokens);
		if (null == buf) {
			throw new StructuredTextException(
					"Unexpected end of algorithm in for argument parsing! Expecting variable statement.");
		}

		while (!buf.equalsIgnoreCase(delim)) {
			exportSTStatement(buf, tokens);
			// remove trailing spaces
			buf = getNextNoneSpaceToken(tokens);
			if (null == buf) {
				throw new StructuredTextException(
						"Unexpected end of algorithm in for argument parsing! Expecting: " + delim);
			}
		}

		pwCPP = bufPrintWriter;
		return result.toString();
	}

	private String getForToAndBy(StringTokenizer tokens, ForValBuffer forVals) throws StructuredTextException {
		StringWriter result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		PrintWriter bufPrintWriter = pwCPP;
		pwCPP = printWriter;

		boolean hadBYStatement = false;

		if (!tokens.hasMoreTokens()) {
			throw new StructuredTextException(
					"Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
		}
		String buf = tokens.nextToken();
		String retval = ""; //$NON-NLS-1$
		while (!buf.equalsIgnoreCase("DO")) { //$NON-NLS-1$
			while (buf.equals(" ")) { //$NON-NLS-1$
				if (!tokens.hasMoreTokens()) {
					throw new StructuredTextException(
							"Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
				}
				buf = tokens.nextToken(); // remove leading spaces
			}
			if (buf.equalsIgnoreCase("DO")) { //$NON-NLS-1$
				continue;
			}
			if (buf.equalsIgnoreCase("BY")) { //$NON-NLS-1$
				hadBYStatement = true;
				retval = result.toString();
				result = new StringWriter();
				printWriter = new PrintWriter(result);
				pwCPP = printWriter;
			} else {
				exportSTStatement(buf, tokens); // otherwise we export by also
			}
			if (!tokens.hasMoreTokens()) {
				throw new StructuredTextException(
						"Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
			}
			buf = tokens.nextToken();
		}
		if (hadBYStatement) {
			forVals.forByVal = result.toString();
		} else {
			retval = result.toString();
		}

		pwCPP = bufPrintWriter;

		return retval;
	}

	private void emitCASEStatement(final StringTokenizer tokens) {
		conditionalQueue.add(Conditionals.CASE);
		boolean caseElementEnded = false;
		String statement = tokens.nextToken().toUpperCase(), buf = ""; //$NON-NLS-1$

		pwCPP.print("switch("); //$NON-NLS-1$
		while (!statement.equals("OF")) { //$NON-NLS-1$
			exportSTStatement(statement, tokens);
			statement = tokens.nextToken().toUpperCase();
		}
		pwCPP.print(" ){"); //$NON-NLS-1$
		boolean first = true;

		statement = tokens.nextToken().toUpperCase();
		while (true) {
			while ((!caseElementEnded) && (!statement.equals("END_CASE"))) { //$NON-NLS-1$
				buf += statement;
				if (statement.equalsIgnoreCase("CASE")) { //$NON-NLS-1$
					exportSTStatement(statement, tokens);
					statement = tokens.nextToken().toUpperCase();
					buf = statement;
				}
				if (statement.equals(":")) { //$NON-NLS-1$
					do {
						statement = tokens.nextToken().toUpperCase();
					} while (statement.equals(" ")); //$NON-NLS-1$
					if (!statement.equals("=")) { //$NON-NLS-1$
						caseElementEnded = true;
					}
				} else {
					statement = tokens.nextToken().toUpperCase();
				}
			}
			int pos = buf.lastIndexOf(';');
			if (pos != -1) {
				StringTokenizer t = new StringTokenizer(buf.substring(0, pos), " &():=[]+-*/><;\n\r\t\"\'", true); //$NON-NLS-1$
				while (t.hasMoreElements()) {
					String s = t.nextToken();
					exportSTStatement(s, t);

				}
			}
			if (caseElementEnded) {
				StringTokenizer t;
				if (pos != -1) {
					t = new StringTokenizer(buf.substring(pos), " &():=[]+-*/><;\n\r\t\"\'.,", true); //$NON-NLS-1$
				} else {
					t = new StringTokenizer(buf, " &():=[]+-*/><;\n\r\t\"\'.,", //$NON-NLS-1$
							true);
				}
				if (!first) {
					pwCPP.println("\nbreak;"); //$NON-NLS-1$
				} else {
					first = false;
				}
				pwCPP.print("\ncase "); //$NON-NLS-1$
				String[] alltokens = new String[t.countTokens()];
				int tokenID = 0;
				while (t.hasMoreElements()) {
					String s = ""; //$NON-NLS-1$
					do {
						s = t.nextToken();
						alltokens[tokenID++] = s;
					} while (s.equals(" ") || s.equals("\n") || s.equals("\t)")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					if (s.equals(",")) { //$NON-NLS-1$
						pwCPP.print(":\ncase "); //$NON-NLS-1$
						continue;
					}
					if (s.equals(".") && t.nextToken().equals(".")) { //$NON-NLS-1$ //$NON-NLS-2$
						int lower = 0;
						int upper = 0;
						try {
							lower = Integer.parseInt(alltokens[tokenID - 2]);
							upper = Integer.parseInt(t.nextToken());

						} catch (NumberFormatException nex) {
							Activator.getDefault().logError(nex.getMessage(), nex);
						}
						if (lower < upper) {
							for (int i = lower + 1; i <= upper; i++) {
								pwCPP.print(":\ncase " + i); //$NON-NLS-1$
							}
						} else {
							for (int i = lower - 1; i >= upper; i--) {
								pwCPP.print(":\ncase " + i); //$NON-NLS-1$
							}
						}
					} else {
						// TODO error handling for single "." characters
					}

					if (!s.equals(";") && !s.equals(".")) { //$NON-NLS-1$ //$NON-NLS-2$
						pwCPP.print(s);
					}
				}
				caseElementEnded = false;
				buf = ""; //$NON-NLS-1$

			} else {
				if ((pos == -1) && (0 != buf.length())) {
					StringTokenizer t = new StringTokenizer(buf, " &():=[]+-*/><;\n\r\t\"\'", true); //$NON-NLS-1$
					while (t.hasMoreElements()) {
						String s = t.nextToken();
						exportSTStatement(s, t);

					}
				}

				// end of the case
				pwCPP.print("\n }"); //$NON-NLS-1$
				Conditionals cond = conditionalQueue.get(conditionalQueue.size() - 1);
				conditionalQueue.remove(conditionalQueue.size() - 1);
				if ((Conditionals.CASE != cond) && (Conditionals.CASE_ELSE != cond)) {
					exportFilter.addErrorMsg("END_CASE without corresponding IF statement found!");
				}
				return;
			}
		}
	}

}
