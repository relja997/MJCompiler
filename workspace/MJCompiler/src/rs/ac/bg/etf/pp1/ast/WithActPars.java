// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class WithActPars extends OptActPars {

    private ActPars ActPars;

    public WithActPars (ActPars ActPars) {
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WithActPars(\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WithActPars]");
        return buffer.toString();
    }
}
