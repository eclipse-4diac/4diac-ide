package org.eclipse.fordiac.ide.gef.nat;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class VisibilityManager {
	private static final Map<VarDeclaration, Boolean> inputVisibilityMap = new HashMap<>();
	private static final Map<VarDeclaration, Boolean> outputVisibilityMap = new HashMap<>();

	// Set the visibility for Input
	public static void setInputVisibility(final VarDeclaration varDeclaration, final boolean visible) {
		inputVisibilityMap.put(varDeclaration, visible);
	}

	// Get the visibility for Input
	@SuppressWarnings("boxing")
	public static boolean getInputVisibility(final VarDeclaration varDeclaration) {
		return inputVisibilityMap.getOrDefault(varDeclaration, false);
	}

	// Set the visibility for Output
	public static void setOutputVisibility(final VarDeclaration varDeclaration, final boolean visible) {
		outputVisibilityMap.put(varDeclaration, visible);
	}

	// Get the visibility for Output
	@SuppressWarnings("boxing")
	public static boolean getOutputVisibility(final VarDeclaration varDeclaration) {
		return outputVisibilityMap.getOrDefault(varDeclaration, false);
	}
}