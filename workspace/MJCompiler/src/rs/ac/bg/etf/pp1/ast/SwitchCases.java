// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class SwitchCases extends Cases {

    private Cases Cases;
    private Case Case;

    public SwitchCases (Cases Cases, Case Case) {
        this.Cases=Cases;
        if(Cases!=null) Cases.setParent(this);
        this.Case=Case;
        if(Case!=null) Case.setParent(this);
    }

    public Cases getCases() {
        return Cases;
    }

    public void setCases(Cases Cases) {
        this.Cases=Cases;
    }

    public Case getCase() {
        return Case;
    }

    public void setCase(Case Case) {
        this.Case=Case;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Cases!=null) Cases.accept(visitor);
        if(Case!=null) Case.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Cases!=null) Cases.traverseTopDown(visitor);
        if(Case!=null) Case.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Cases!=null) Cases.traverseBottomUp(visitor);
        if(Case!=null) Case.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchCases(\n");

        if(Cases!=null)
            buffer.append(Cases.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Case!=null)
            buffer.append(Case.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchCases]");
        return buffer.toString();
    }
}
