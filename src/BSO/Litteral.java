package Traitement;

public class Litteral {
    private int var;
    private int val;
    int postion;

    public Litteral(int var) {
        this.var = var;

    }

   public void Set(int var, int postion)
   {
       this.var = var;
       if (var>0) {
           this.val = 1;
           this.postion=postion;
       }
       else {
           this.postion=postion;
           this.val = 0;
       }

   }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
