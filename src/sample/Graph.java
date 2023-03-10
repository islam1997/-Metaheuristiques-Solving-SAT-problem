package sample;

import java.math.BigInteger;
import java.sql.PseudoColumnUsage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.lang.Thread;

import ALGO.PSO;
import Genetic.GA;
import Traitement.Bso;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graph extends Application implements Runnable{
    final int WINDOW_SIZE = 10;
    private ScheduledExecutorService scheduledExecutorService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Realtime Fitness plotter");

        // defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generations");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Fitness");
        yAxis.setAnimated(false); // axis animations are removed

        // creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Realtime Fitness plotter");
        lineChart.setAnimated(false); // disable animations

        // defining a bestFit to display data
        XYChart.Series<String, Number> bso = new XYChart.Series<>();
        bso.setName("Bso_Best_fitness");
        XYChart.Series<String, Number> ga = new XYChart.Series<>();
        ga.setName("GA_Best_fitness");
        XYChart.Series<String, Number> pso = new XYChart.Series<>();
        pso.setName("Pso_Best_fitness");

        // add bestFit to chart
        lineChart.getData().add(bso);
        // add wrstFit to chart
        lineChart.getData().add(ga);

        lineChart.getData().add(pso);


        // setup scene
        Scene scene = new Scene(lineChart, 900, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // get a random integer between 0-10
            Integer random = ThreadLocalRandom.current().nextInt(10);

            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put random number with current time
                bso.getData().add(
                        new XYChart.Data<>(String.valueOf(Bso.j), Bso.sRef2.getNbrSat()));
                ga.getData().add(
                        new XYChart.Data<>(String.valueOf(GA.geneCount), GA.geneFit));

               //Integer a= 325-PSO.GBestError;
                pso.getData().add(
                        new XYChart.Data<>(String.valueOf(PSO.iterNum), 325-PSO.GBestError.intValue()));

                /*if (bestFit.getData().size() > WINDOW_SIZE)
                    bestFit.getData().remove(0);*/
            });
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}