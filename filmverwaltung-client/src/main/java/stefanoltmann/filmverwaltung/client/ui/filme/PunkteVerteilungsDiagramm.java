package stefanoltmann.filmverwaltung.client.ui.filme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import stefanoltmann.filmverwaltung.client.ui.DiagrammArt;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
@SuppressWarnings({ "serial" })
public class PunkteVerteilungsDiagramm extends JFrame {
	
	public PunkteVerteilungsDiagramm(DiagrammArt diagrammArt, Map<Integer, Integer> punkteVerteilung) {
		
		setTitle("Meine Filmverwaltung");
		setSize(100, 30);
		
		ChartPanel diagrammPanel = null;
		
		if (diagrammArt == DiagrammArt.KREIS) {

			DefaultPieDataset dataset = new DefaultPieDataset();
			
			for (Map.Entry<Integer, Integer> entry : punkteVerteilung.entrySet())
				dataset.setValue(entry.getKey(), entry.getValue());
			
			JFreeChart diagramm = ChartFactory.createPieChart(
					"Punkteverteilung", // chart title
					dataset, 			// data
					true, 				// include legend
					true, 				// tooltips
					false 				// locale
					);
			
			PiePlot plot = (PiePlot)diagramm.getPlot();
			plot.setSectionOutlinesVisible(false);
			plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
			plot.setNoDataMessage("Keine Daten verfügbar.");
			plot.setSimpleLabels(true);
	
			diagrammPanel = new ChartPanel(diagramm);
		
			setSize(400, 400);
		}
		
		if (diagrammArt == DiagrammArt.BALKEN) {
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			for (Map.Entry<Integer, Integer> entry : punkteVerteilung.entrySet())
				dataset.setValue(entry.getValue(), entry.getKey(), entry.getKey());
			
			JFreeChart diagramm = ChartFactory.createBarChart(
					"Punkteverteilung",       // chart title
					"Punkte",                 // domain axis label
					"Anzahl Filme",           // range axis label
					dataset,                  // data
					PlotOrientation.VERTICAL, // orientation
					true,                     // include legend
					true,                     // tooltips
					false                     // URLs?
			);
			
			diagramm.setBackgroundPaint(Color.WHITE);

			diagrammPanel = new ChartPanel(diagramm);
			
			setSize(700, 300);
		}
		
		if (diagrammPanel != null)
			setContentPane(diagrammPanel);
		else
			setContentPane(new JLabel("Das Diagramm konnte nicht erstellt werden."));
	}

}
