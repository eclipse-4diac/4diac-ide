/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

	public static <T> List<List<T>> permute(final T[] nums) {
		final List<List<T>> result = new ArrayList<>();
		permuteHelper(nums, 0, result);
		return result;
	}

	private static <T> void permuteHelper(final T[] nums, final int index, final List<List<T>> result) {
		if (index == nums.length) {
			final List<T> curr = new ArrayList<>();
			for (final T num : nums) {
				curr.add(num);
			}
			result.add(curr);
			return;
		}
		for (int i = index; i < nums.length; i++) {
			swap(nums, index, i);
			permuteHelper(nums, index + 1, result);
			swap(nums, index, i);
		}
	}

	private static <T> void swap(final T[] nums, final int i, final int j) {
		final T temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
}