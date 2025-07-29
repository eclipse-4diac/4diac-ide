/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.helpers;

import org.eclipse.swt.graphics.Color;

public interface Painter {

	void setForeground(Color color);

	void setBackground(Color color);

	void setAlpha(int alpha);

	void drawLine(int x1, int y1, int x2, int y2);

	void drawRectangle(int x, int y, int width, int height);

	void fillRectangle(int x, int y, int width, int height);

	void fillPolygon(int[] pointArray);

	void drawTextCentered(String string, int x, int y, boolean isTransparent);
}
