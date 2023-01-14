package Traitement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sat_Bso {
    int Clause[][];
    int VariableListe[];
    Litteral tabSo[];
    public Integer variablesNumber;
    public Integer clausesNumber;
    //public int nbrsat;

    public Sat_Bso(String Path) throws IOException {
        //litterals=new Vector<>();
        BufferedReader reader = new BufferedReader(new FileReader(Path));
        String line = reader.readLine();
        while (line.charAt(0) != 'p') {
            line = reader.readLine();
        }

        String numVars[] = line.split("\\s+");
        variablesNumber = Integer.parseInt(numVars[2]);
        clausesNumber = Integer.parseInt(numVars[3]);
        Clause = new int[clausesNumber][3];
        tabSo = new Litteral[150];
        //System.out.print(variablesNumber);
        for (int i=0;i<tabSo.length;i++)
            tabSo[i]=new Litteral(0);
        int i = 0;
        int k=0;

        while ((line = reader.readLine()) != null && i < clausesNumber) {
            if (line.length() > 0 && line.charAt(0) == ' ')
                line = line.substring(1);
            String lineList[] = line.split("\\s+");
            for (int j = 0; j < lineList.length - 1; j++) {
                int literal = Integer.parseInt(lineList[j]);
                Clause[i][j] = literal;
                if (!existe(literal,tabSo)) {
                    //System.out.println(k+"  "+literal);
                    if (literal<0) {
                        tabSo[k].Set(literal, k);
                        tabSo[k+75].Set(-literal,k+75);
                    }
                    else {
                        tabSo[k + 75].Set(literal, k + 75);
                        tabSo[k].Set(-literal, k);
                    }
                    //System.out.println(k+"  "+tabSo[k].getVar());
                    k++;
                }
            }
                i++;
            }

        }


    public Boolean existe(int VAR, Litteral tab[]) {

            for (int i = 0; i < tab.length; i++) {
                if (VAR == tab[i].getVar() || VAR==-tab[i].getVar())
                    return true;
            }
            return false;



    }
    public int evaluer(Solution sol)
    {
       int tab[]=sol.getTableaux().clone();
       int nbrsat=0;
       Boolean b;
        for (int i=0;i<Clause.length;i++)
        {
            b=false;
            for (int j=0;j<Clause[i].length;j++) {
                if (b) break;
                if (Clause[i][j] < 0) {
                    for (int k = 0; k < 75; k++) {

                        if (tabSo[k].getVar() == Clause[i][j]) {
                            if (tabSo[k].getVal() == tab[tabSo[k].getPostion()]) {
                                nbrsat++;
                                b = true;
                                break;
                            }


                        }
                    }
                }else
                {
                    for (int k = 75; k < 150; k++) {

                        if (tabSo[k].getVar() == Clause[i][j]) {
                            if (tabSo[k].getVal() == tab[tabSo[k].getPostion()]) {
                                nbrsat++;
                                b = true;
                                break;
                            }


                        }
                    }
                }
            }
        }
        sol.setNbrSat(nbrsat);
        return nbrsat;
    }

    public int[][] getClause() {
        return Clause;
    }

    public void setClause(int[][] clause) {
        Clause = clause;
    }

    public int[] getVariableListe() {
        return VariableListe;
    }

    public void setVariableListe(int[] variableListe) {
        VariableListe = variableListe;
    }

    public Litteral[] getTabSo() {
        return tabSo;
    }

    public void setTabSo(Litteral[] tabSo) {
        this.tabSo = tabSo;
    }

    public Integer getVariablesNumber() {
        return variablesNumber;
    }

    public void setVariablesNumber(Integer variablesNumber) {
        this.variablesNumber = variablesNumber;
    }

    public Integer getClausesNumber() {
        return clausesNumber;
    }

    public void setClausesNumber(Integer clausesNumber) {
        this.clausesNumber = clausesNumber;
    }
}
