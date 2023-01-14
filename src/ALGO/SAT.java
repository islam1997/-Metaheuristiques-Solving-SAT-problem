package ALGO;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class SAT {
    public Integer variablesNumber;
    public Integer clausesNumber;
    public ArrayList<ArrayList<Integer>> clausesList = new ArrayList<>();
    public ArrayList<Integer> variablesList = new ArrayList<>();
    public BigInteger searchSpaceSize;

    public SAT(String pathToCnfFile) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(pathToCnfFile));
        String line = reader.readLine();
        while (line.charAt(0) != 'p') {
            line = reader.readLine();
        }

        String numVars[] = line.split("\\s+");
        variablesNumber = Integer.parseInt(numVars[2]);
        clausesNumber = Integer.parseInt(numVars[3]);

        int i = 0;
        while ((line = reader.readLine()) != null && i < clausesNumber) {
            if(line.length()>0 && line.charAt(0) == ' ')
                line = line.substring(1);
            String lineList[] = line.split("\\s+");
            clausesList.add(new ArrayList<>());
            for (int j = 0; j < lineList.length - 1; j++) {
                int literal = Integer.parseInt(lineList[j]);
                clausesList.get(i).add(literal);
                //System.out.print(literal + "  ");
            }
            //System.out.println();
            i++;
        }
        searchSpaceSize = new BigInteger("2").pow(variablesNumber);
        /*for (i = 0; i < searchSpaceSize; i++) {
            searchSpace.add(i);

        }*/

        for (i = 1; i <= variablesNumber; i++) {
            variablesList.add(i);
        }
    }

    public ArrayList<Integer> modelAdapter(BigInteger model) {
        String binary = model.toString(2);
        ArrayList<Integer> formattedModel = new ArrayList<>();
        char[] charArr = binary.toCharArray();
        int zerosNumber = variablesNumber - charArr.length;
        //System.out.println(zerosNumber + "    " + charArr.length);
        //System.out.println();
        for(int i = 0; i < zerosNumber; i++) {
            formattedModel.add(-1);
        }
        for (char ch: charArr) {
            //System.out.print(ch + " ");
            formattedModel.add((Integer.parseInt(String.valueOf(ch)) == 1 ? 1 : -1));
        }
        //System.out.println();
        /*for(int  i = 0; i < formattedModel.size(); i++) {
            System.out.print(formattedModel.get(i) + " ");
        }
        System.out.print("    " + formattedModel.size());
        System.out.println();*/
        return formattedModel;
    }
}
