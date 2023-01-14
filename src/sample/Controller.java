package sample;

import ALGO.PSO;
import Genetic.GA;
import Traitement.Bso;

import javax.swing.*;

public class Controller implements Runnable{
    @Override
    public void run() {
        try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            call();

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
   info in;
    public class info
    {
        String nbrbees;

        String maxiteration;

        String flip;

        String  maxchance;

        String  localresearch;

        String  maxsat;

        public info() {
            this.nbrbees = String.valueOf(Bso.nbrbee2);
            this.maxiteration = String.valueOf(Bso.maxit2);
            this.flip = String.valueOf(Bso.flip2);
            this.maxchance = String.valueOf(Bso.maxchance2);
            this.localresearch = String.valueOf(Bso.searchit2);
            this.maxsat =String.valueOf(Bso.maxsat);
        }
    }

    JFrame frame ;
    public void call(){
        frame = new JFrame();


        in=new info();
        String [] header = {"Algorithme" , "MaxIteration", "MaxSat","Temps(s)"};
       String [][] names={{"BSO",in.maxiteration,String.valueOf(Bso.sRef2.getNbrSat()),String.valueOf(Bso.time)},
                          {"GA",in.maxiteration, String.valueOf(GA.geneFit),String.valueOf(GA.time)},
                          {"PSO",in.maxiteration, String.valueOf(325- PSO.GBestError.intValue()),String.valueOf(PSO.time)}};
        JTable t = new JTable(names,header);
        t.setBounds(30,40,200,300);
        JScrollPane s = new JScrollPane(t);
        frame.add(s);

        frame.setSize(600, 300);

        frame.setVisible(true);

    }
}



