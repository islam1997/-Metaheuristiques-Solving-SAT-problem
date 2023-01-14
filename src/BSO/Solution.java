package Traitement;

import java.util.concurrent.ThreadLocalRandom;

public class Solution {
    private int NbrSat;
    private int tableaux[];

    public Solution(int nbrSat, int[] tableaux) {
        NbrSat = nbrSat;
        this.tableaux = tableaux;
    }
    public Solution()
    {
        tableaux=new int[150];
    }

    public void SolutionAleatoire()
    {
        tableaux=new int[150];
        for (int i=0;i<tableaux.length;i++)
            tableaux[i]= ThreadLocalRandom.current().nextInt(0,2);
    }
  public int distance(Solution sol)
    {
        int cpt=0;
        for (int i=0;i<tableaux.length;i++)
        {
            if (tableaux[i]!=sol.getTableaux()[i])
                cpt++;
        }
        return cpt;
    }
    public int getNbrSat() {
        return NbrSat;
    }

    public void setNbrSat(int nbrSat) {
        NbrSat = nbrSat;
    }

    public int[] getTableaux() {
        return tableaux;
    }

    public void setTableaux(int[] tableaux) {
        this.tableaux = tableaux;
    }
}
