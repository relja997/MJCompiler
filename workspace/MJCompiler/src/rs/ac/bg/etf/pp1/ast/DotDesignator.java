// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class DotDesignator extends Designator {

    private String designatorIdent;
    private String classMemberIdent;

    public DotDesignator (String designatorIdent, String classMemberIdent) {
        this.designatorIdent=designatorIdent;
        this.classMemberIdent=classMemberIdent;
    }

    public String getDesignatorIdent() {
        return designatorIdent;
    }

    public void setDesignatorIdent(String designatorIdent) {
        this.designatorIdent=designatorIdent;
    }

    public String getClassMemberIdent() {
        return classMemberIdent;
    }

    public void setClassMemberIdent(String classMemberIdent) {
        this.classMemberIdent=classMemberIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DotDesignator(\n");

        buffer.append(" "+tab+designatorIdent);
        buffer.append("\n");

        buffer.append(" "+tab+classMemberIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DotDesignator]");
        return buffer.toString();
    }
}
