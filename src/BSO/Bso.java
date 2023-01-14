package Traitement;

import java.io.File;
import java.util.SplittableRandom;
import java.util.Vector;

public class Bso implements Runnable{
    public static int j,k=0;
    public static  Solution sRef2,SRef3;
    public static Integer maxit2,flip2,searchit2,maxchance2,nbrbee2,maxsat;
    public static long startTime, endTime, time;

    @Override
    public void run() {
        Sat_Bso Sat = null;
        Bso  bso=null;
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + "\\src\\res\\uf75-04.cnf";
            Sat = new Sat_Bso(filePath);

        } catch (Exception e) {
            System.out.println("Can not read the file " + e);
        }
        Solution listeRand=new Solution();
        listeRand.SolutionAleatoire();
        System.out.println(Sat.evaluer(listeRand));
        startTime = System.currentTimeMillis()/1000;
        Solution resultat = bso.Bso(Sat, 100, 25, 5, listeRand,5,20);
        endTime = System.currentTimeMillis()/1000;
        time=endTime-startTime;
        System.out.println("Resultat="+Sat.evaluer(resultat));



    }

    public static Solution Bso(Sat_Bso s, int maxIter, int flip, int searchIter, Solution init, int maxchance,int nbrbee) {
        maxit2=maxIter;
        flip2=flip;
        searchit2=searchIter;
        maxchance2=maxchance;
        nbrbee2=nbrbee;
        boolean found = false;
        Solution satMaxGlobal = new Solution();
        Vector<Solution> tabou = new Vector<Solution>();
        Solution dance2[];
        sRef2=init;
        //tabou.add(sRef2);
        Vector<Solution> dance = new Vector<Solution>();
        Bee abeilles[] = new Bee[nbrbee];
        j = 0;
        int indice = 0;
        int MaxChance = maxchance;
        Litteral choisi = null;
        Litteral contraire = null;
        int max = 0;
        tabou.add(init);
        //System.out.println(init.getLitterals().size());
        boolean b = false;
        int NbrChance=0;
        int m=maxIter,l=maxIter/100;
        while (j < maxIter && found == false) {
            //System.out.println("INIT==" + s.evaluer(init));
            dance2 = new Solution[nbrbee];
            indice=0;

            for (int i = 0; i <nbrbee; i++) {
                abeilles[i] = new Bee(new Object[]{s, init, 75, searchIter, indice, flip, dance, i + 1});
                dance2[i] = abeilles[i].Mielleur_Sol();
                //System.out.println(dance[i]);
                indice = indice + 1;

            }

            Solution Serf=new Solution();
            Serf=MilleurSol(dance2,s);
            dance2=null;
            if (s.evaluer(Serf)==325) {
                found = true;
                return Serf;
            }
            Boolean exist=false;
            Solution sa=tabou.get(0);
            for (int i=1;i<tabou.size();i++)
            {
                if (s.evaluer(sa)<s.evaluer(tabou.get(i)))
                    sa=tabou.get(i);
            }
            if (s.evaluer(sa)>s.evaluer(sRef2))
                sRef2=sa;
            for (int i=0;i<tabou.size();i++)
            {
                if (Serf.distance(tabou.get(i))==0)
                {
                    exist=true;
                    break;
                }
            }
            int delta=s.evaluer(Serf)-s.evaluer(init);

            if (delta>0 && !exist) {
                init = new Solution(s.evaluer(Serf), Serf.getTableaux().clone());
                tabou.add(Serf);
                if (MaxChance > NbrChance) NbrChance = maxchance;
            }else
            {
                NbrChance--;
                if (NbrChance>0 && !exist) {


                    init = new Solution(s.evaluer(Serf), Serf.getTableaux().clone());
                    tabou.add(Serf);
                }else {
                    if (exist) {

                        NbrChance = maxchance;

                        Solution NewInit = new Solution();
                        boolean val = true;
                        while (val) {
                            val = false;
                            NewInit.SolutionAleatoire();

                            if (NewInit.distance(Serf) < 14) {
                                val = true;
                                break;
                            }
                        }
                        init = new Solution(s.evaluer(NewInit), NewInit.getTableaux().clone());
                        tabou.add(init);
                    }else {



                        Solution distance = new Solution(s.evaluer(Serf), Serf.getTableaux().clone());

                        for (Solution Sol : tabou) {
                            if (distance.distance(init) > Sol.distance(init))
                                distance = new Solution(s.evaluer(Sol), Sol.getTableaux().clone());

                        }
                        init = new Solution(s.evaluer(distance), distance.getTableaux().clone());
                        tabou.add(distance);
                    }


                }


            }
            j++;
        }

        satMaxGlobal=tabou.get(0);
        for(Solution sol: tabou){
            if(s.evaluer(sol)>s.evaluer(satMaxGlobal)) {satMaxGlobal=sol;}
        }
        maxsat=satMaxGlobal.getNbrSat();
        return satMaxGlobal;
    }


    public static Solution MilleurSol(Solution []dance,Sat_Bso sat) {
        Solution max=dance[0],min=dance[0];
        for (int i=1;i<dance.length;i++) {
            if (sat.evaluer(max) < sat.evaluer(dance[i]))
                max = dance[i];
            if (sat.evaluer(min)>sat.evaluer(dance[i]))
                min=dance[i];
        }
        if(k==0) {
            SRef3 = min;
            k=1;
        }
        else
        {
            if (SRef3.getNbrSat()>min.getNbrSat())
                SRef3=min;
        }
        return max;
    }

    public void Bso()
    {

    }


}
