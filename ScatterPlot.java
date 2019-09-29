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

import java.awt.BorderLayout;
import java.util.Random;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;


public class ScatterPlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = -412699430625953887L;

	private static final int SAMPLE_COUNT = 100000;
	/** Instance to generate random data values. */
	private static final Random random = new Random();

	//무작위로 x와 y값을 갖는 데이터 테이블을 생성한다. x와 y값은 모두 double타입이고 가우스 분포를 통해 추출된다
	@SuppressWarnings("unchecked")
	public ScatterPlot() {
		// Generate 100,000 data points
		DataTable data = new DataTable(Double.class, Double.class);
		for (int i = 0; i <= SAMPLE_COUNT; i++) {
			data.add(random.nextGaussian()*2.0,  random.nextGaussian()*2.0);
		}
		//---------------------------------------------------------------->

		// Create a new xy-plot : 산점도는 XYPlot이라고도 한다. 따라서 다음과 같이 객체를 생성한다
		XYPlot plot = new XYPlot(data);

		// Format plot : 플롯의 크기를 설정하고 설명을 추가한다
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));
		plot.getTitle().setText(getDescription());

		// Format points : 데이터 포인트에 색상을 설정한다
		plot.getPointRenderer(data).setColor(COLOR1);

		// Add plot to Swing component : 플롯을 자바 Swing 컴포넌트에 추가한 다음 생서자를 닫는다
		add(new InteractivePanel(plot), BorderLayout.CENTER);
	}

	//ExamplePanel 클래스에 있는 두개의 메소드를 추가적으로 구현해야 한다
	@Override
	public String getTitle() {
		return "Scatter plot";
	}

	@Override
	public String getDescription() {
		return String.format("Scatter plot with %d data points", SAMPLE_COUNT);
	}

	
	//코드를 실행하기 위한 메인 메소드 선언
	public static void main(String[] args) {
		new ScatterPlot().showInFrame();
	}

}
