package ALGO;
import sample.Graph;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class PSO implements Runnable{
    public static SAT sat;
    public static Integer particlesNumber;
    public static Integer iterationsNumber, vMax, iterNum;
    public static double c1, c2, w, r1, r2;
    public static BigInteger GBest, GBestError;
    public static ArrayList<ArrayList<BigInteger>> particlesTable = new ArrayList<>();
    public static long startTime, endTime, time;
    public PSO() {}

    @Override
    public void run() {
        startTime = System.currentTimeMillis()/1000;
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + "\\src\\res\\uf75-01.cnf";
            sat = new SAT(filePath);
        } catch (Exception e) {
            System.out.println("Can not read the file " + e);
        }

        particlesNumber = 1000;
        iterationsNumber = 100;
        w = 0.9;
        r1 = 1;
        r2 = 1;
        c1 = 1;
        c2 = 2;
        vMax = 75;


        for (int i = 0; i < particlesNumber; i++) {
            particlesTable.add(new ArrayList<>());
        }

        PSOFunction();
        endTime = System.currentTimeMillis()/1000;
        time=endTime-startTime;
    }

    public static void main(String[] args) {
        new PSO().run();
    }

    public void PSOFunction() {
        // Initialize N particles with positions, velocities and PBest
        for (ArrayList<BigInteger> particle : particlesTable) {
            particle.add(new BigInteger(sat.variablesNumber, new Random()).abs());
            //particle.add(BigInteger.valueOf(0));
            particle.add(BigInteger.valueOf((int)(Math.random() * vMax)).abs());
            //particle.add(BigInteger.valueOf(2));
            particle.add(particle.get(0));
            particle.add(BigInteger.valueOf(fitnessFunction(particle.get(2))));
        }
        // Calculate GBest
        GBestError = particlesTable.get(0).get(3);
        //System.out.println(GBestError);

        GBest = particlesTable.get(0).get(2);
        BigInteger oldGBest = GBest;
        for (int i = 1; i < particlesNumber; i++) {
            if (particlesTable.get(i).get(3).compareTo(GBestError) == -1 ) {
                GBestError = particlesTable.get(i).get(3);
                GBest = particlesTable.get(i).get(2);
            }
        }
        int bool = 0;
        int counterTest = 0;

        //##################### loooop #####################//
        for(int i = 0; i < iterationsNumber; i++) {
            iterNum = i;
            for(ArrayList<BigInteger> particle : particlesTable) {
                // Update the velocity

                particle.set(1,
                        (new BigDecimal(particle.get(1)).multiply(new BigDecimal(w)))

                                .add(
                                        new BigDecimal(calculateDistance(sat.modelAdapter(particle.get(2)), sat.modelAdapter(particle.get(0)) ))
                                                .multiply(new BigDecimal(c1*r1))
                                ).toBigInteger().add(
                                new BigDecimal(calculateDistance(sat.modelAdapter(GBest), sat.modelAdapter(particle.get(0)) ))
                                        .multiply(new BigDecimal(c2*r2)).toBigInteger()
                        ).mod(BigInteger.valueOf(Math.abs(vMax))));


                // randomizing the position of the particle if counterTest > 100 (Enhancements)
                if (counterTest < 100) {
                    particle.set(0, invertBits(particle.get(0), particle.get(1)));
                } else {
                    particle.set(0, new BigInteger(sat.variablesNumber, new Random()).abs());
                    bool = 1;
                }

                //particle.set(0, invertBits(particle.get(0), particle.get(1)));


                // Evaluate the fitness and Update  PBest
                BigInteger newError = BigInteger.valueOf(fitnessFunction(particle.get(0)));
                if (newError.compareTo(particle.get(3)) == -1) {
                    particle.set(2, particle.get(0));
                    particle.set(3, newError);
                }

                w = Math.random();
                r1 = Math.random();
                r2 = Math.random();
            }
            //###############################################################################//

            // Update GBest
            for (int j = 0; j < particlesNumber; j++) {
                if (particlesTable.get(j).get(3).compareTo(GBestError) == -1) {
                    GBestError = particlesTable.get(j).get(3);
                    GBest = particlesTable.get(j).get(2);
                }
            }
            //System.out.println("bool = " + bool + " counter = " + counterTest);

            if (bool == 1) {
                counterTest = 0;
                bool = 0;
            }

            if (oldGBest.compareTo(GBest) == 0) {
                counterTest++;
            } else {
                oldGBest = GBest;
            }
            System.out.println("Iteration " + i + " => GBest = " + GBest + " With accuracy = " + GBestError);
        }

    }

    public Integer fitnessFunction(BigInteger model) {
        ArrayList<Integer> formattedModel = sat.modelAdapter(model);
        Integer UCNumber = sat.clausesNumber;
        for (ArrayList<Integer> clause: sat.clausesList) {
            for (Integer literal : clause) {
                if (literal * formattedModel.get(Math.abs(literal) - 1) > 0) {
                    UCNumber--;
                    break;
                }
            }
        }
        return UCNumber;
    }

    public Integer calculateDistance(ArrayList<Integer> x, ArrayList<Integer> y) {
        Integer distance = 0;
        //System.out.println("x = " + x.size() + " y = " + y.size());
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) * y.get(i) < 0) {
                distance++;
            }
        }
        //System.out.println("Distance === " + distance);
        //System.out.println(distance);
        return distance;
    }

    /*public BigInteger invertBits2(BigInteger x, BigInteger v) {
        ArrayList<Integer> formattedX = sat.modelAdapter(x);
        Integer intY = formattedX.size() - v.intValue() - 1;
        //System.out.println("list size : " + formattedX.size() + " ,velocity : " + v.intValue());
        for (int i = formattedX.size()-1; i > intY; i--) {
            formattedX.set(i, formattedX.get(i)== -1?1:-1);
        }
        for (int i = 0; i < formattedX.size(); i++) {
            formattedX.set(i, formattedX.get(i)== -1?0:1);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < formattedX.size(); i++) {
            sb.append(formattedX.get(i).toString());
        }
        String bits = sb.toString();
        return new BigInteger(bits, 2);
    }*/

    public BigInteger invertBits(BigInteger x, BigInteger v) {
        ArrayList<Integer> formattedX = sat.modelAdapter(x);
        Integer intY = formattedX.size() - v.intValue() - 1;
        //System.out.println("list size : " + formattedX.size() + " ,velocity : " + v.intValue());
        for (int i = 0; i < v.intValue(); i++) {
            formattedX.set(i, formattedX.get(i)== -1?1:-1);
        }
        for (int i = 0; i < formattedX.size(); i++) {
            formattedX.set(i, formattedX.get(i)== -1?0:1);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < formattedX.size(); i++) {
            sb.append(formattedX.get(i).toString());
        }
        String bits = sb.toString();
        return new BigInteger(bits, 2);
    }



}
