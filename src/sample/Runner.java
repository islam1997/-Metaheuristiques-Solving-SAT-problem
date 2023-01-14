package sample;

import ALGO.PSO;
import Genetic.GA;
import Traitement.Bso;

public class Runner {
    public static void main(String[] args) {
        Thread gene  = new Thread(new GA());
        Thread gene2  = new Thread(new PSO());
        Thread gene3  = new Thread(new Bso());
        Thread graph = new Thread(new Graph());
        Thread tab=new Thread(new Controller());
        graph.start();
        gene.start();
        gene2.start();
        gene3.start();
        while (gene.isAlive() || gene2.isAlive() || gene3.isAlive())
        {

        }
        tab.start();

    }
}
