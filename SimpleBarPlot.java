/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2013 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <michael[at]erichseifert.de>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package ChartVisualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.Location;

//SimpleBarPlot 클래스를 생성한다.
public class SimpleBarPlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = -2793954497895054530L; //UID를 설정한다

	//생성자 시작
	@SuppressWarnings("unchecked")
	public SimpleBarPlot() {
		// Create example data
		// 생성자에서 예제 데이터를 만든다. 막대 차트에서 각 막대는 세개의 값을 갖는다. (x축 y축 막대이름)
		DataTable data = new DataTable(Double.class, Integer.class, String.class);
		data.add(0.1,  1, "January");
		data.add(0.2,  3, "February");
		data.add(0.3, -2, "March");
		data.add(0.4,  6, "April");
		data.add(0.5, -4, "May");
		data.add(0.6,  8, "June");
		data.add(0.7,  9, "July");
		data.add(0.8, 11, "August");

		// Create new bar plot 새로운 막대 차트 객체를 생성한다.
		BarPlot plot = new BarPlot(data);

		// Format plot 막대 차트의 크기와 막대의 너비를 설정한다
		plot.setInsets(new Insets2D.Double(40.0, 40.0, 40.0, 40.0));
		plot.setBarWidth(0.075);

		// Format bars 막대의 모양을 구성한다. 우선 데이터를 이용해 BarRenderer를 생성한다
		BarRenderer pointRenderer = (BarRenderer) plot.getPointRenderer(data);
		//막대의 색상을 설정한다
		pointRenderer.setColor(
			new LinearGradientPaint(0f,0f, 0f,1f,
					new float[] { 0.0f, 1.0f },
					new Color[] { COLOR1, GraphicsUtils.deriveBrighter(COLOR1) }
			)
		);
		pointRenderer.setBorderStroke(new BasicStroke(3f));
		pointRenderer.setBorderColor(
			new LinearGradientPaint(0f,0f, 0f,1f,
					new float[] { 0.0f, 1.0f },
					new Color[] { GraphicsUtils.deriveBrighter(COLOR1), COLOR1 }
			)
		);
		
		//막대 차트의 여러가지 특성을 설정한다
		pointRenderer.setValueVisible(true);//막대 차트에 값을 표시하기 위해 다음 코드를 사용한다
		pointRenderer.setValueColumn(2);//데이터에서 세 번째 값(월)을 칼럼 값으로 설정한다
		pointRenderer.setValueLocation(Location.CENTER);//값을 표시하는 위치를 가운데로 설정한다
		pointRenderer.setValueColor(GraphicsUtils.deriveDarker(COLOR1));//표시되는 값의 색상을 설정한다
		pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));//값을 표시하는 폰드에 볼드체 속성을 적용한다

		// Add plot to Swing component- 막대 차트를 Swing 컴포넌트에 추가한다
		add(new InteractivePanel(plot));
	}//생성자를 닫는다

	
	//GRAL 라이브러리의 ExamplePanel 클래스에 있는 두개의 메소드를 추가적으로 구현해줘야 한다.
	@Override
	public String getTitle() {
		return "Bar plot";
	}

	@Override
	public String getDescription() {
		return "Bar plot with example data and color gradients";
	}

	public static void main(String[] args) {
		new SimpleBarPlot().showInFrame();
	}
}
