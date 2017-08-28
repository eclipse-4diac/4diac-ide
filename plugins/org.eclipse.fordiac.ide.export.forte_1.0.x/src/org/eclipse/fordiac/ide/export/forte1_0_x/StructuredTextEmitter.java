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
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.fordiac.ide.export.ExportFilter.VarDefinition;
import org.eclipse.fordiac.ide.export.forte1_0_x.ForteExportFilter1_0_x.adapterInstance;
import org.eclipse.fordiac.ide.util.STStringTokenHandling;

public class StructuredTextEmitter {
	
	private enum Conditionals {
	     IF, IF_ELSE, CASE, CASE_ELSE
	}

	private boolean inExpression;

	private PrintWriter pwCPP = null;

	private ForteExportFilter1_0_x exportFilter;
	
	private LinkedList<Conditionals> condtionalQueue =  new LinkedList<Conditionals>();

	
	private class ForValBuffer{
		public String forByVal;
		public String forControlVal;
	}

	Vector<ForValBuffer> forValBuf = new Vector<ForValBuffer>(); 
	
	public StructuredTextEmitter(ForteExportFilter1_0_x exportFilter) {
		this.exportFilter = exportFilter;
	}

	public void exportStructuredTextAlgorithm(final String src,
			final PrintWriter pwCPP) {
		inExpression = false;
		condtionalQueue.clear();
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
		condtionalQueue.clear();
		this.pwCPP = pwCPP;

		if (null != this.pwCPP) {
			StringTokenizer t = new StringTokenizer(guard, STStringTokenHandling.stTokenDelimiters,
					true);
			while (t.hasMoreElements()) {
				String s = t.nextToken();
				exportSTStatement(s, t);
			}
		}
	}

	private void exportSTComment(final StringTokenizer tokens) {
		pwCPP.print("/* ");

		String var;
		boolean endTag = false;
		while (!endTag) {
			do {
				if(!tokens.hasMoreElements())
					return;
				var = tokens.nextToken();
				pwCPP.print(var);
			} while (!var.equals("*"));
			if(!tokens.hasMoreElements())
				return;
			var = tokens.nextToken();
			if (var.equals(")")) {
				endTag = true;
			} else {
				pwCPP.print(var);
			}
		}
		pwCPP.print("/");
	}

	private void exportSTStatement(String statement,
			final StringTokenizer tokens) {
		String upperStatement = statement.toUpperCase();
		if (upperStatement.equals("XOR")) {
			pwCPP.print("^");
			return;
		}
		if (upperStatement.equals("MOD")) {
			pwCPP.print(" % ");
			return;
		}
		if (upperStatement.equals("TRUE")) {
			pwCPP.print("true");
			return;
		}
		if (upperStatement.equals("FALSE")) {
			pwCPP.print("false");
			return;
		}
		if (upperStatement.equals("NOT")) {
			pwCPP.print("!");
			return;
		}
		if (statement.equals(":")) {
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("=")) {
				pwCPP.print(" = ");
			} else {
				pwCPP.print(" : ");
				exportSTStatement(statement, tokens);
			}
			return;
		}
		if(statement.equals(".")){
			//a dot marks the access of a structure member or in/out puts of adapters
			//TODO: check for multiple levels!
			pwCPP.print(".");
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			exportSTStatement(statement, tokens);
			if((!Character.isDigit(statement.codePointAt(0))) && 
			   (null == exportFilter.getVars().get(statement))){
				pwCPP.print("()");   // in c++ we have access functions named equaly to the members
			}
			return;
		}

		if ((upperStatement.equals("AND")) || (statement.equals("&"))) {
			if (inExpression) {
				pwCPP.print(") && (");
			} else {
				pwCPP.print("&");
			}
			return;
		}
				
		if (upperStatement.equals("OR")) {
			if (inExpression) {
				pwCPP.print(") || (");
			} else {
				pwCPP.print("|");
			}
			return;
		}
		if (statement.equals("<")) {
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("=")) {
				pwCPP.print(" <= ");
			} else if (statement.equals(">")) {
				pwCPP.print(" != ");
			} else {
				pwCPP.print(" < ");
				exportSTStatement(statement, tokens);
			}
			return;
		}
		if (statement.equals(">")) {
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("=")) {
				pwCPP.print(" >= ");
			} else {
				pwCPP.print(" > ");
				exportSTStatement(statement, tokens);
			}
			return;
		}

		if (statement.equals("=")) {
			pwCPP.print(" == ");
			return;
		}
		if (upperStatement.equals("RETURN")) {
			pwCPP.print("return");
			return;
		}
		if (upperStatement.equals("EXIT")) {
			pwCPP.print("break");
			return;
		}
		if (upperStatement.equals("CASE")) {
			emitCASEStatement(tokens);
			return;
		}

		if (upperStatement.equals("END_IF")) {
			pwCPP.print("}");
			if(!condtionalQueue.isEmpty()){
				Conditionals cond = condtionalQueue.removeLast();
				if((Conditionals.IF != cond) && (Conditionals.IF_ELSE != cond)){
					exportFilter.addErrorMsg("END_IF without corresponding IF statement found!");
				}
			} else {
				exportFilter.addErrorMsg("END_IF without corresponding IF statement found!");
			}
			return;
		}

		if (upperStatement.equals("END_FOR")){			
			exportEndForStatement();
			return;
		}
		
		if (upperStatement.equals("END_WHILE")) {
			pwCPP.print("}");
			return;
		}
		if (upperStatement.equals("ELSE")) {
			Conditionals cond = condtionalQueue.peekLast();
			
			if(Conditionals.IF == cond || Conditionals.IF_ELSE == cond){
				pwCPP.print("}\nelse{");
				condtionalQueue.removeLast();
				condtionalQueue.add(Conditionals.IF_ELSE);
				
			}else{
				if(Conditionals.CASE == cond){
					pwCPP.print(" break;\ndefault: ");
					condtionalQueue.removeLast();
					condtionalQueue.add(Conditionals.CASE_ELSE);					
				}else{
					exportFilter.addErrorMsg("Else without preceding IF, ELSIF, or CASE statement");
				}
			}
			return;
		}
		if (upperStatement.equals("ELSIF")) {
			inExpression = true;
			
			Conditionals cond = condtionalQueue.peekLast();
			
			if(Conditionals.IF == cond || Conditionals.IF_ELSE == cond){
				pwCPP.print("}\nelse\n  if((");
				condtionalQueue.removeLast();
				condtionalQueue.add(Conditionals.IF_ELSE);				
			}
			return;
		}
		if (upperStatement.equals("IF")) {
			inExpression = true;
			condtionalQueue.add(Conditionals.IF);
			pwCPP.print("if((");
			return;
		}
		if (upperStatement.equals("THEN")) {
			inExpression = false;
			pwCPP.print(")){");
			return;
		}
		if (upperStatement.equals("REPEAT")) {
			pwCPP.println("do{");
			return;
		}
		if (upperStatement.equals("UNTIL")) {
			pwCPP.print("}\nwhile((");
			inExpression = true;
			return;
		}
		if (upperStatement.equals("END_REPEAT")) {
			inExpression = false;
			pwCPP.print("));"); // close the do while statement
			return;
		}
		if (upperStatement.equals("WHILE")) {
			pwCPP.print("while((");
			inExpression = true;
			return;
		}
		if (upperStatement.equals("DO")) {
			inExpression = false;
			pwCPP.println(")){"); // close the while statement
			return;
		}

		if (upperStatement.equals("FOR")) {
			try {
				exportForStatement(tokens);
			} catch (StructuredTextException e) {
				exportFilter.addErrorMsg(e.getMessage());
			}
			return;
		}
		
		if (upperStatement.equals("VAR")) {
			try {
				exportLokalVariables(tokens);
			} catch (StructuredTextException e) {
				exportFilter.addErrorMsg(e.getMessage());
			}
			return;
		}

		if (upperStatement.equals("]")) {
			pwCPP.print("]");
			return;
		}

		if (statement.equals("(")) {
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("*")) {
				exportSTComment(tokens);
			} else {
				if (statement.equals(")")) {
					pwCPP.print("()");
				}
				else {
					pwCPP.print("((");
					exportSTStatement(statement, tokens);
				}
			}
			return;
		}
		
		if(statement.equals(")")){
			pwCPP.print("))");
			return;
		}
		
		if(statement.equals(",")){
			pwCPP.print("), (");
			return;
		}

		if (statement.equals("/")) {
			pwCPP.print(statement);
			if(!tokens.hasMoreElements())
				return;
			statement = tokens.nextToken();
			if (statement.equals("/")) {
				pwCPP.print("/ ");
				if(!tokens.hasMoreElements())
					return;
				statement = tokens.nextToken();
				// print till end of line
				while (!statement.equals("\n") && !statement.equals("\r")) {
					pwCPP.print(statement);
					if(!tokens.hasMoreElements())
						return;
					statement = tokens.nextToken();
				}
				pwCPP.println(""); //issue a new line
			} else {
				if (statement.equals("*")) { // multi line c-style comment
					pwCPP.print("* ");
					if(!tokens.hasMoreElements())
						return;
					statement = tokens.nextToken();
					// print till end of multi line comment

					String var;
					boolean endTag = false;
					while (!endTag) {
						do {
							if(!tokens.hasMoreElements())
								return;
							var = tokens.nextToken();
							pwCPP.print(var);
						} while (!var.equals("*"));
						if(!tokens.hasMoreElements())
							return;
						var = tokens.nextToken();
						if (var.equals("/")) {
							endTag = true;
						} else {
							pwCPP.print(var);
						}
					}
					pwCPP.print("/");
				} else {
					exportSTStatement(statement, tokens);
				}
			}
			return;
		}

		if (statement.equals("!")) {
			exportFilter
					.addErrorMsg(" Found wrong statement in ST code: \'!\' Export may not be correct. Please check exported C++ code!");
			pwCPP.print(statement);
			return;
		}

		if (statement.equals("\"")) {
			exportWStringLiteral(tokens);
			return;
		}
		
		if (statement.equals("\'")) {
			exportStringLiteral(tokens);
			return;
		}
		
		if (!statement.equals(" ")) {
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
					pwCPP.print(statement+"()");
//				}
			} else {
				ArrayList<adapterInstance> myAdapters = exportFilter.getAdapters();
				boolean isAnAdapter=false;
				Iterator<adapterInstance> myIter = myAdapters.iterator();
				while (myIter.hasNext()) {
					adapterInstance myAdapter = myIter.next();
					if (myAdapter.stName.equals(statement)) {
						//we have an adapter!
						isAnAdapter=true;
						break;
					}
				}
				// No special treatment necessary
				if (isAnAdapter) {
					pwCPP.print(statement+"()");
				} else {
					if(statement.contains("#")){
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
		int hashIndex = statement.indexOf("#");
		
		String prefix = statement.substring(0, hashIndex);
		statement = statement.substring(hashIndex + 1);
		
		int type = isTypeName(prefix);
		if(0 != type){
			
			pwCPP.print("CIEC_" + getDataTypeNameFromPrefix(prefix) + "(");
			if(2 == type){
				//it is a complex type we will give the string for parsing
				pwCPP.print("\"" + prefix + "#");	
				hashIndex = -1;
			} else { 
				//only for simple types we try to find a second #
				hashIndex = statement.indexOf("#");
				if(-1 != hashIndex){
					prefix = statement.substring(0, hashIndex);
					statement = statement.substring(hashIndex + 1);
				}
			}
		}
		
		if(-1 != hashIndex){
			handleLiteralBasePrefix(prefix);
		}
		
		pwCPP.print(statement);
		
		if(0 != type){
			if(2 == type){
				//it is a complex type we will give the string for parsing
				pwCPP.print("\"");	
			}
			pwCPP.print(")"); //close the type construction
		}
		
	}
	
	/* Return the appropiate data type name for a given prefix.
	 * This function is in charge of handling data type prefix shortcuts, e.g., T#, 
	 */
	private String getDataTypeNameFromPrefix(String prefix) {
		if(("t".equals(prefix)) || ("T".equals(prefix))){
			return "TIME";
		}
		if(("d".equals(prefix)) || ("D".equals(prefix))){
			return "DATE";
		}
		if(("tod".equals(prefix)) || ("TOD".equals(prefix))){
			return "TIME_OF_DAY";
		}
		if(("dt".equals(prefix)) || ("DT".equals(prefix))){
			return "DATE_AND_TIME";
		}
		return prefix;
	}

	//Dataypes for which we will create correct C litarals
	final static String elementaryTypeNames[] = {"BOOL", "SINT", "INT", "DINT", "LINT", "USINT", "UINT", "UDINT", "ULINT", "REAL", "LREAL", "BYTE", "WORD", "DWORD", "LWORD" };

	private int isTypeName(String prefix) {
		int retVal = 0;
		
		if(!(prefix.startsWith("1") || prefix.startsWith("8") || prefix.startsWith("2"))){
			for (String typeName : elementaryTypeNames) {
				if(typeName.equals(prefix)){
					return 1;
				}
			}
			retVal = 2;
		}
		
		return retVal;
	}

	private void handleLiteralBasePrefix(String prefix) {
		if(prefix.equals("16")){
			pwCPP.print("0x");
		}else if(prefix.equals("8")){
			pwCPP.print("0");
		}else if(prefix.equals("2")){
			pwCPP.print("0b");
		}else{
			exportFilter.addErrorMsg("Wrong literal base prefix provided: " + prefix + "#");
		}
		
	}

	private void exportLokalVariables(StringTokenizer tokens)  throws StructuredTextException  {
		
		while(tokens.hasMoreElements()){
			String token = getNextNoneSpaceToken(tokens);
			if(token.equals("END_VAR")){
				return;
			}
			if(!token.equals("\n")&&!token.equals("\t")&&!token.equals(" ")){
				String varName = token;
				
				token = getNextNoneSpaceToken(tokens);
				if((null != token) && (token.equals(":"))){
					token = getNextNoneSpaceToken(tokens);
					
					if(null != token){
						if(token.equals("ARRAY")){
							exportLocalArray(varName, tokens);
						}else{
							 pwCPP.print("CIEC_" + token + " ");						 
							 pwCPP.print(varName);
							 handleVarInitialisation(tokens, "");						
						}						
						pwCPP.println(";");
						
					}else{
						throw new StructuredTextException("Variable declaration is missing the type");
					}
				}else{
					throw new StructuredTextException("Variable declaration is missing the : between var name and type");
				}
			}
		}
		throw new StructuredTextException("VAR statement not closed with END_VAR.");
	}

	private void exportLocalArray(String varName, StringTokenizer tokens) throws StructuredTextException {
		String token = getNextNoneSpaceToken(tokens);
		if((null != token) && (token.equals("["))){
			String lower = getNextNoneSpaceToken(tokens);
			token = getNextNoneSpaceToken(tokens);
			if((null != token) && (token.equals("."))){
				if(tokens.hasMoreElements() && tokens.nextToken().equals(".")) {
					String upper = getNextNoneSpaceToken(tokens);
					token = getNextNoneSpaceToken(tokens);
					if((null != token) && (token.equals("]"))){
						token = getNextNoneSpaceToken(tokens);
						if((null != token) && (token.equals("OF"))){
							token = getNextNoneSpaceToken(tokens);
							if(null != token){
								//TODO consider lower range 
								pwCPP.print("CIEC_ARRAY " + varName + "(" + upper +", g_nStringId" + token +")");
								handleVarInitialisation(tokens, ".fromString");
							}
							
						}else{
							throw new StructuredTextException("Array OF missing");
						}
						
					}else{
						throw new StructuredTextException("Array size end missing");
					}
				}else{
					throw new StructuredTextException("Array var range missing ..");
				}				
			}else{
				throw new StructuredTextException("Array var range missing ..");
			}			
		}else{
			throw new StructuredTextException("Array size start missing");
		}
	}

	private String getNextNoneSpaceToken(StringTokenizer tokens) {
		while(tokens.hasMoreElements()){
			String token = tokens.nextToken();
			if(!token.equals("\n")&&!token.equals("\t")&&!token.equals(" ")&&!token.equals("\r")){
				return token;
			}
		}
		return null;
	}

	private void handleVarInitialisation(StringTokenizer tokens, String initStartCode)  throws StructuredTextException {
		String token = getNextNoneSpaceToken(tokens);
		if(null != token){
			if(":".equals(tokens.nextToken())){
				if(tokens.hasMoreElements() && tokens.nextToken().equals("=")){
					pwCPP.print(initStartCode + "(\"");
				}else{
					throw new StructuredTextException("VAR initialisation is mising =.");
				}
			
				while(tokens.hasMoreElements()){
					token = tokens.nextToken();
					if(token.equals(";")){
						pwCPP.print("\")");
						return;
					}					
					pwCPP.print(token);
				}
			}else{
				//if we don't have an assignment we need a closing statement
				if(";".equals(token)){
					return; 
				}
			}
		}
		
		throw new StructuredTextException("variable statement not closed with ;.");
	}

	private void exportEndForStatement() {
		
		if(!forValBuf.isEmpty()){
			ForValBuffer forVals = forValBuf.lastElement();
			String strippedForControlVal = forVals.forControlVal.replace("()", "_");
			
			pwCPP.println("\n      if(((is" + strippedForControlVal + "Up) && ((" + forVals.forByVal +") > 0)) || ");
			pwCPP.println("         ((!is" + strippedForControlVal + "Up) && ((" + forVals.forByVal + ") < 0))){");		
			pwCPP.println("        " + forVals.forControlVal + " = " + forVals.forControlVal + " + (" + forVals.forByVal +");");
			pwCPP.println("      }");
			pwCPP.println("      else{");
			pwCPP.println("        " + forVals.forControlVal + " = " + forVals.forControlVal + " - (" + forVals.forByVal +");");
			pwCPP.println("      }");
			pwCPP.println("    }");
			pwCPP.println("  }");

			forValBuf.remove(forVals);
		}else{
			exportFilter.addErrorMsg("For loop closed without an according open statement!");
		}
	}

	private void  exportWStringLiteral(final StringTokenizer tokens) {
		String statement;
		pwCPP.print("\"");
		do {
			statement = tokens.nextToken();
			if(statement.equals("$")){
				statement = tokens.nextToken();
				if(statement.equals("\"")){
					pwCPP.print("\\\"");
					statement = "";  //necessary that we don't end the loop as it is a dollar escaped ' it is not the end of the wstring
				}else{
					//TODO handle dollar escaping
				}
			}else{
				pwCPP.print(statement);
			}
		} while (!statement.equals("\""));
	}

	private void exportStringLiteral(final StringTokenizer tokens) {
		String statement;
		
		pwCPP.print("\"");
		do {
			statement = tokens.nextToken();			
			if (statement.equals("\"")) {
				pwCPP.print("\\\"");
			} else {				
				if (statement.equals("'")) {
					pwCPP.print("\"");
				} else {
					if(statement.equals("$")){
						statement = tokens.nextToken();
						if(statement.equals("'")){
							pwCPP.print("'");
							statement = "";  //necessary that we don't end the loop as it is a dollar escaped ' it is not the end of the wstring
						}else{
							//TODO handle dollar escaping	
						}
					}else{
						pwCPP.print(statement);
					}
				}
			}
		} while (!statement.equals("\'"));
	}
	

	private void exportForStatement(final StringTokenizer tokens) throws StructuredTextException {
		
		ForValBuffer forVals = new ForValBuffer();
		
		forVals.forByVal = "1";
		
		forVals.forControlVal = getForVar(tokens, ":");
		
		inExpression = true;
		
		String buf = tokens.nextToken();
		if((null != buf) && (buf.equals("="))){
			String forInitialVal = getForVar(tokens, "TO");
			
			String forToVal = getForToAndBy(tokens, forVals);
			String strippedForControlVal = forVals.forControlVal.replace("()", "_");
			
			pwCPP.println("  {");
			pwCPP.println("    bool is" + strippedForControlVal + "Up = ((" + forVals.forByVal + ") > 0);");
			pwCPP.println("    " + forVals.forControlVal + " = " + forInitialVal + ";");
			pwCPP.println("    while(!(((is" + strippedForControlVal + "Up) && (" + forVals.forControlVal + " > (" + forToVal + "))) ||");
			pwCPP.println("            ((!is" + strippedForControlVal + "Up) && (" + forVals.forControlVal + " < (" + forToVal + "))))){");
			
			forValBuf.add(forVals);
		}else{
			throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting '=' statement.");
		}
		inExpression = false;
	}

	private String getForVar(StringTokenizer tokens, String delim) throws StructuredTextException {
		Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    PrintWriter bufPrintWriter = pwCPP;
	    pwCPP = printWriter;
	    
	    String buf = getNextNoneSpaceToken(tokens);
	    if(null == buf){
	    	throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting variable statement.");	    	
	    }
	    
		while (!buf.equalsIgnoreCase(delim)) {
			exportSTStatement(buf, tokens);			
			// remove trailing spaces
			buf = getNextNoneSpaceToken(tokens);
		    if(null == buf){
		    	throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting: " + delim);	    	
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
		
	    if(!tokens.hasMoreTokens()){
			throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
		}
		String buf = tokens.nextToken();
		String retval = "";
		while (!buf.equalsIgnoreCase("DO")) {
			while (buf.equals(" ")) {
				if(!tokens.hasMoreTokens()){
					throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
				}
				buf = tokens.nextToken(); // remove leading spaces
			}
			if (buf.equalsIgnoreCase("DO")) {
				continue;
			}
			if (buf.equalsIgnoreCase("BY")) {
				hadBYStatement = true;
				retval = result.toString();
				result = new StringWriter();
			    printWriter = new PrintWriter(result);
			    pwCPP = printWriter;
			} else {
				exportSTStatement(buf, tokens); // otherwise we export by also
			}
			if(!tokens.hasMoreTokens()){
				throw new StructuredTextException("Unexpected end of algorithm in for argument parsing! Expecting DO or BY");
			}
			buf = tokens.nextToken();
		}
		if (hadBYStatement) {
			forVals.forByVal = result.toString();
		}
		else {
			retval = result.toString();
		}
		
		pwCPP = bufPrintWriter;
		
		return retval;
	}

	private void emitCASEStatement(final StringTokenizer tokens) {
		condtionalQueue.add(Conditionals.CASE);
		boolean caseElementEnded = false;
		String statement = tokens.nextToken().toUpperCase(), buf = "";

		pwCPP.print("switch(");
		while (!statement.equals("OF")) {
			exportSTStatement(statement, tokens);
			statement = tokens.nextToken().toUpperCase();
		}
		pwCPP.print(" ){");
		Boolean first = true;
		
		statement = tokens.nextToken().toUpperCase();
		while (true) {
			while ((!caseElementEnded) && (!statement.equals("END_CASE"))) {
				buf += statement;
				if (statement.equalsIgnoreCase("CASE")) {
					exportSTStatement(statement, tokens);
					statement = tokens.nextToken().toUpperCase();
					buf = statement;
				}
				if (statement.equals(":")) {
					do {
						statement = tokens.nextToken().toUpperCase();
					} while (statement.equals(" "));
					if (!statement.equals("=")) {
						caseElementEnded = true;
					}
				} else {
					statement = tokens.nextToken().toUpperCase();
				}
			}
			int pos = buf.lastIndexOf(";");
			if (pos != -1) {
				StringTokenizer t = new StringTokenizer(buf.substring(0, pos),
						" &():=[]+-*/><;\n\r\t\"\'", true);
				while (t.hasMoreElements()) {
					String s = t.nextToken();
					exportSTStatement(s, t);

				}
			}
			if (caseElementEnded) {
				StringTokenizer t;
				if (pos != -1) {
					t = new StringTokenizer(buf.substring(pos),
							" &():=[]+-*/><;\n\r\t\"\'.,", true);
				} else {
					t = new StringTokenizer(buf, " &():=[]+-*/><;\n\r\t\"\'.,",
							true);
				}
				if(!first){
					pwCPP.println("\nbreak;");
				}else{
					first = false;
				}
				pwCPP.print("\ncase ");
				String[] alltokens = new String[t.countTokens()];
				int tokenID = 0;
				while (t.hasMoreElements()) {
					String s = "";
					do {
						s = t.nextToken();
						alltokens[tokenID++] = s;
					} while (s.equals(" ") || s.equals("\n") || s.equals("\t)"));
					if (s.equals(",")) {
						pwCPP.print(":\ncase ");
						continue;
					}
					if (s.equals(".") && t.nextToken().equals(".")) {
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
								pwCPP.print(":\ncase " + i);
							}
						} else {
							for (int i = lower - 1; i >= upper; i--) {
								pwCPP.print(":\ncase " + i);
							}
						}
					} else {
						// TODO error handling for single "." characters
					}

					if (!s.equals(";") && !s.equals(".")) {
						pwCPP.print(s);
					}
				}
				caseElementEnded = false;
				buf = "";

			} else {
				if((pos == -1) && (0 != buf.length())){
					StringTokenizer t = new StringTokenizer(buf,
							" &():=[]+-*/><;\n\r\t\"\'", true);
					while (t.hasMoreElements()) {
						String s = t.nextToken();
						exportSTStatement(s, t);

					}	
				}
				
				// end of the case
				pwCPP.print("\n }");
				Conditionals cond = condtionalQueue.removeLast();
				if((Conditionals.CASE != cond) && (Conditionals.CASE_ELSE != cond)){
					exportFilter.addErrorMsg("END_CASE without corresponding IF statement found!");
				}
				return;
			}
		}
	}

}
