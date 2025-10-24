package academic.ui;

import academic.models.ExamSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphPanel extends JPanel {

    private ChartPanel chartPanel;
    private JComboBox<String> filterComboBox;
    private List<ExamSession> currentSessions;

    public GraphPanel() {
        setLayout(new BorderLayout(10, 10));
        
        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("View Graph For:"));
        filterComboBox = new JComboBox<>();
        filterPanel.add(filterComboBox);
        
        add(filterPanel, BorderLayout.NORTH);
        
        initChart();
        
        filterComboBox.addActionListener(e -> drawChart());
    }

    private void initChart() {
        // Start with an empty bar chart as a placeholder
        JFreeChart chart = createBarChart(new DefaultCategoryDataset(), "Student Performance", "Semester");
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createLineChart(DefaultCategoryDataset dataset, String title, String xAxisLabel) {
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                xAxisLabel, // Dynamic X-axis label
                "Marks",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.setBackgroundPaint(new Color(240, 240, 240));
        
        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
        plot.setRenderer(renderer);

        // Customize renderer for better visuals
        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(2.5f));
        }
        
        return chart;
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset, String title, String xAxisLabel) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                xAxisLabel,
                "Marks",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.setBackgroundPaint(new Color(240, 240, 240));

        // Optional: Customize bar colors
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 99, 71));   // Tomato red
        renderer.setSeriesPaint(1, new Color(60, 179, 113));  // Medium sea green
        renderer.setSeriesPaint(2, new Color(30, 144, 255));  // Dodger blue
        renderer.setSeriesPaint(3, new Color(255, 140, 0));   // Dark orange
        
        return chart;
    }

    public void updateData(List<ExamSession> sessions) {
        this.currentSessions = sessions;
        
        // Populate the filter dropdown
        filterComboBox.removeAllItems();
        filterComboBox.addItem("Overall Performance");
        if (sessions != null && !sessions.isEmpty()) {
            filterComboBox.addItem(sessions.get(0).getSubject1());
            filterComboBox.addItem(sessions.get(0).getSubject2());
            filterComboBox.addItem(sessions.get(0).getSubject3());
        }
        
        // Draw the initial chart (which will be "Overall Performance")
        drawChart();
    }

    private void drawChart() {
        if (currentSessions == null || currentSessions.isEmpty()) {
            chartPanel.setChart(createBarChart(new DefaultCategoryDataset(), "No Data Available", "Semester"));
            return;
        }

        String filter = (String) filterComboBox.getSelectedItem();
        if (filter == null) {
            filter = "Overall Performance";
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String chartTitle = currentSessions.get(0).getName() + "'s ";
        JFreeChart chart;

        if ("Overall Performance".equals(filter)) {
            chartTitle += "Overall Performance Trend";
            for (ExamSession session : currentSessions) {
                String xAxisLabel = "Y" + session.getYear() + "-S" + session.getSemester();
                dataset.addValue(session.getSub1Total(), session.getSubject1() + " (Total)", xAxisLabel);
                dataset.addValue(session.getSub2Total(), session.getSubject2() + " (Total)", xAxisLabel);
                dataset.addValue(session.getSub3Total(), session.getSubject3() + " (Total)", xAxisLabel);
                dataset.addValue(session.getsgpa() * 14, "SGPA (×14)", xAxisLabel);
            }
            chart = createBarChart(dataset, chartTitle, "Exam Session (Year-Semester)");
        } else { // A specific subject is selected
            chartTitle += filter + " Intra-Semester Progress";
            for (ExamSession session : currentSessions) {
                // The line on the graph represents the semester
                String seriesName = "Y" + session.getYear() + "-S" + session.getSemester();
                
                if (filter.equals(session.getSubject1())) {
                    dataset.addValue(session.getSub1Internal1(), seriesName, "Internal 1");
                    dataset.addValue(session.getSub1Internal2(), seriesName, "Internal 2");
                    dataset.addValue(session.getSub1Sem(), seriesName, "Semester Exam");
                } else if (filter.equals(session.getSubject2())) {
                    dataset.addValue(session.getSub2Internal1(), seriesName, "Internal 1");
                    dataset.addValue(session.getSub2Internal2(), seriesName, "Internal 2");
                    dataset.addValue(session.getSub2Sem(), seriesName, "Semester Exam");
                } else if (filter.equals(session.getSubject3())) {
                    dataset.addValue(session.getSub3Internal1(), seriesName, "Internal 1");
                    dataset.addValue(session.getSub3Internal2(), seriesName, "Internal 2");
                    dataset.addValue(session.getSub3Sem(), seriesName, "Semester Exam");
                }
            }
            chart = createLineChart(dataset, chartTitle, "Exam Type");
        }
        
        chartPanel.setChart(chart);
    }
}
