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

import java.awt.Color;
import java.util.Random;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.areas.AreaRenderer;
import de.erichseifert.gral.plots.areas.DefaultAreaRenderer2D;
import de.erichseifert.gral.plots.areas.LineAreaRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;

public class AreaPlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = 3287044991898775949L;

	/** Instance to generate random data values. */
	//영역 그래프를 그리기 위해 무작위 값을 사용할 것이다. 따라서 무작위 추출을 위한 클래스 변수를 선언한다.
	private static final Random random = new Random();

	@SuppressWarnings("unchecked")
	public AreaPlot() {//클래스를 위한 생성자를 시작한다
		// Generate data
		
		//하나의 x값과 세 개의 y값을 갖는 데이터 포인트를 담을 데이터 테이블을 생성한다. 
		DataTable data = new DataTable(Double.class, Double.class, Double.class, Double.class);
		
		
		for (double x=0.0; x<2.5*Math.PI; x+=Math.PI/15.0) {
			double y1 = Double.NaN, y2 = Double.NaN, y3 = Double.NaN;
			if (x>=0.00*Math.PI && x<2.25*Math.PI) {
				y1 = 4.0*Math.sin(x + 0.5*Math.PI) + 0.1*random.nextGaussian();
			}
			if (x>=0.25*Math.PI && x<2.50*Math.PI) {
				y2 = 4.0*Math.cos(x + 0.5*Math.PI) + 0.1*random.nextGaussian();
			}
			if (x>=0.00*Math.PI && x<2.50*Math.PI) {
				y3 = 2.0*Math.sin(2.0*x/2.5)       + 0.1*random.nextGaussian();
			}
			data.add(x, y1, y2, y3);
		}

		// Create data series : 
		DataSeries data1 = new DataSeries("red", data, 0, 1);
		DataSeries data2 = new DataSeries("blue 1", data, 0, 2);
		DataSeries data3 = new DataSeries("blue 2", data, 0, 3);

		// Create new xy-plot : 세 개의 데이터 시리즈를 갖는 XYPlot을 생성한다.
		//그래프에 범례를 보여주도록 설정한다. 또 그래프의 크기를 설정한다
		XYPlot plot = new XYPlot(data1, data2, data3);
		plot.setLegendVisible(true);
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 20.0, 20.0));

		// Format data series : 영역 그래프에서 추가적으로 해야 하는 작업은 색상으로 영역을 채우는것이다.
		//formatFilledArea와 formatLineArea라는 정적 메소드를 호출해 이작업을 할 수 있다.
		
		formatFilledArea(plot, data1, COLOR2);
		formatFilledArea(plot, data2, COLOR1);
		formatLineArea(plot, data3, GraphicsUtils.deriveDarker(COLOR1));

		// Add plot to Swing component : 플롯을 swing 컴포넌트에 추가하고, 생성자를 닫는다.
		add(new InteractivePanel(plot));
	}

	//특정 색상으로 영역을 채워주는 정적 메소드를 생성한다. 이 메소드는 생성된 XYPlot과 데이터 시리즈, 그리고 색상을 매개변수로 취한다
	private static void formatFilledArea(XYPlot plot, DataSource data, Color color) {
		//PointRenderer를 생성한다. 여기서는 2D 이미지를 랜더링하는 것이기 때문에 적절한 클래스를 사용해야 한다. 
		//PointRenderer에 대한 색상을 설정한 다음, 데이터 시리즈와 함께 PointRenderer를 설정한다
		PointRenderer point = new DefaultPointRenderer2D();
		point.setColor(color);
		plot.setPointRenderer(data, point);
		
		//위와 마찬가지로 GRAL의 적절한 클래스를 사용해 2D LineRenderer를 생성한 다음, 색상을 지정해 주고, 선 사이의 간격을 3.0포인트로 설정한다.
		//다음으로 간격의 끝을 둥글게 처리해 준다. 마지막으로 데이터 시리즈와 함꼐 LineRenderer를 설정한다
		LineRenderer line = new DefaultLineRenderer2D();
		line.setColor(color);
		line.setGap(3.0);
		line.setGapRounded(true);
		plot.setLineRenderer(data, line);
		
		//PointRenderer와 LineRenderer다음에 영역을 렌더링 하기 위한 AreaRenderer가 필요하다 
		//2D AreaRenderer를 생성하고 색상을 지정해준다. 그리고 데이터 시리즈를 넣어준 다음 메소드를 닫는다.
		AreaRenderer area = new DefaultAreaRenderer2D();
		area.setColor(GraphicsUtils.deriveWithAlpha(color, 64));
		plot.setAreaRenderer(data, area);
	}

	//위 과정과 비슷하게 정적 메소드인 formatLineArea를 생성한다. 이 메소드는 생성된 XYPlot, 데이터 시리즈, 색상을 매개변수로 취한다
	private static void formatLineArea(XYPlot plot, DataSource data, Color color) {
		//2D pointRenderer를 생성하고, 색상을 설정하고, 데이터 시리즈를 넣어준다.
		PointRenderer point = new DefaultPointRenderer2D();
		point.setColor(color);
		plot.setPointRenderer(data, point);
		
		//이 메소드에서는 LineRenderer를 사용하지 않는다. 그래서 세번째 데이터 시리즈는 다른 두 데이터 시리즈와 다르게 보일 것이다.
		plot.setLineRenderer(data, null);
		
		//앞에서 했던 것과 동일하게 2D AreaRenderer를 생성하고 영역간의 간격을 설정하고 색상을 설정하고 데이터 시리즈를 넣어준다.
		AreaRenderer area = new LineAreaRenderer2D();
		area.setGap(3.0);
		area.setColor(color);
		plot.setAreaRenderer(data, area);
	}

	
	//다음과 같이 ExamplePanel 클래스의 두 메소드를 오버라이드 해야 한다.
	@Override
	public String getTitle() {
		return "Area plot";
	}

	@Override
	public String getDescription() {
		return "Area plot of three series with different styling";
	}

	public static void main(String[] args) {
		new AreaPlot().showInFrame();
	}
}
