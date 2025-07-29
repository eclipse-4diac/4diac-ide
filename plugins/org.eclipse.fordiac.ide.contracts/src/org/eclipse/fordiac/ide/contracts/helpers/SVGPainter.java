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
import org.eclipse.swt.graphics.Rectangle;

@SuppressWarnings("nls") // translating doesn't make sense here
public class SVGPainter implements Painter {

	private static final Color CANVAS_COLOR = new Color(240, 240, 240);

	private final StringBuilder sb;
	private Color fgCol;
	private Color bgCol;
	private double alpha = 1;

	public SVGPainter(final Rectangle view) {
		sb = new StringBuilder();
		sb.append("<svg viewBox=\"0 0 ");
		sb.append(view.width);
		sb.append(' ');
		sb.append(view.height);
		sb.append("\" xmlns=\"http://www.w3.org/2000/svg\">");

		setBackground(CANVAS_COLOR);
		fillRectangle(0, 0, view.width, view.height);
	}

	@Override
	public void setForeground(final Color color) {
		fgCol = color;
	}

	@Override
	public void setBackground(final Color color) {
		bgCol = color;
	}

	@Override
	public void setAlpha(final int alpha) {
		this.alpha = alpha / 255.0;
	}

	@Override
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		sb.append("<line ");
		addAttribute("x1", x1);
		addAttribute("y1", y1);
		addAttribute("x2", x2);
		addAttribute("y2", y2);
		addStrokeCol();
		sb.append("/>");
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height) {
		sb.append("<rect ");
		addAttribute("x", x);
		addAttribute("y", y);
		addAttribute("width", width);
		addAttribute("height", height);
		addAttribute("fill", "none");
		addStrokeCol();
		sb.append("/>");
	}

	@Override
	public void fillRectangle(final int x, final int y, final int width, final int height) {
		sb.append("<rect ");
		addAttribute("x", x);
		addAttribute("y", y);
		addAttribute("width", width);
		addAttribute("height", height);
		addFillCol(bgCol);
		sb.append("/>");
	}

	@Override
	public void fillPolygon(final int[] pointArray) {
		sb.append("<polygon points=\"");
		int i = 0;
		while (i + 1 < pointArray.length) {
			sb.append(pointArray[i]);
			i++;
			sb.append(',');
			sb.append(pointArray[i]);
			i++;
			sb.append(' ');
		}
		sb.append("\" ");
		addFillCol(bgCol);
		sb.append("/>");
	}

	@Override
	public void drawTextCentered(final String string, final int x, final int y, final boolean isTransparent) {
		sb.append("<text ");
		addAttribute("x", x);
		addAttribute("y", y + 10);
		addAttribute("text-anchor", "middle");
		addAttribute("font-size", "12px");
		addFillCol(fgCol);
		sb.append(">");
		sb.append(string); // FIXME escape string, otherwise could produce invalid SVG
		sb.append("</text>");
	}

	public String finalizeSVG() {
		sb.append("</svg>");
		return sb.toString();
	}

	private void addAttribute(final String name, final Object value) {
		sb.append(name);
		sb.append("=\"");
		sb.append(value);
		sb.append("\" ");
	}

	private void addAttribute(final String name, final int value) {
		sb.append(name);
		sb.append("=\"");
		sb.append(value);
		sb.append("\" ");
	}

	private void addStrokeCol() {
		sb.append("stroke=\"");
		addColor(fgCol);
		sb.append("\" ");
	}

	private void addFillCol(final Color color) {
		sb.append("fill=\"");
		addColor(color);
		sb.append("\" ");
	}

	private void addColor(final Color color) {
		if (alpha == 1) {
			addRGB(color);
		} else {
			addRGBA(color, alpha);
		}
	}

	private void addRGB(final Color color) {
		sb.append("rgb(");
		sb.append(color.getRed());
		sb.append(',');
		sb.append(color.getGreen());
		sb.append(',');
		sb.append(color.getBlue());
		sb.append(')');
	}

	private void addRGBA(final Color color, final double alpha) {
		sb.append("rgba(");
		sb.append(color.getRed());
		sb.append(',');
		sb.append(color.getGreen());
		sb.append(',');
		sb.append(color.getBlue());
		sb.append(',');
		sb.append(alpha);
		sb.append(')');
	}
}
