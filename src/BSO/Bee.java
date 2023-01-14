package Traitement;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Bee {
    private Sat_Bso satInit;
    private Solution Sref=new Solution();
    private int N,searchIter,flip,zone,numAbeille;
    private Solution satMaxGlobal;
    private static Vector<Solution> dance;
    private int[][] rechercher;
    public Bee(Object args[]){
        satInit = (Sat_Bso) args[0];
        Sref =(Solution) args[1];
        N=(Integer) args[2];
        searchIter=(Integer) args[3];
        zone=(Integer) args[4];
        flip=(Integer) args[5];
        dance=((Vector<Solution>) args[6]);
        numAbeille=(Integer) args[7];
    }
    public int[][] Rechreche () {
        rechercher = new int[searchIter][150];
        int h = 0;
        int zone1=0;
        Solution sz=new Solution();
        while (h < searchIter && h < flip) {
            int[] s = Sref.getTableaux().clone();
            int p = 0;
            while (150 > flip * p + zone) {
                s[flip * p + zone] = s[flip * p + zone] ^ 1;
                p++;
            }
            sz.setTableaux(s);
            System.out.println("sol="+h+" "+satInit.evaluer(sz));
            //System.out.println(zone);
            rechercher[h] = s;
            zone=zone+1;
            h++;
        }


            return rechercher;
        }

        public Solution Mielleur_Sol()
        {
          int tab[][]=this.Rechreche();
          Solution solution=new Solution();
          solution.setTableaux(tab[0].clone());
          for (int i=1;i<tab.length;i++)
          {
              Solution solution2=new Solution();
              solution2.setTableaux(tab[i].clone());
                  if (satInit.evaluer(solution2)>satInit.evaluer(solution))
                  {
                      //System.out.println("sol1="+satInit.evaluer(solution));
                      solution.setTableaux(tab[i].clone());
                      //System.out.println("sol2="+satInit.evaluer(solution));
                  }
          }
          System.out.println("sol="+satInit.evaluer(solution));
          return solution;
        }



}
