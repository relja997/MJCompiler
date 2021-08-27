// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class NumCDeclExtend extends ConstExtendNum {

    private String numcIdent;
    private Integer numVal;
    private ConstExtendNum ConstExtendNum;

    public NumCDeclExtend (String numcIdent, Integer numVal, ConstExtendNum ConstExtendNum) {
        this.numcIdent=numcIdent;
        this.numVal=numVal;
        this.ConstExtendNum=ConstExtendNum;
        if(ConstExtendNum!=null) ConstExtendNum.setParent(this);
    }

    public String getNumcIdent() {
        return numcIdent;
    }

    public void setNumcIdent(String numcIdent) {
        this.numcIdent=numcIdent;
    }

    public Integer getNumVal() {
        return numVal;
    }

    public void setNumVal(Integer numVal) {
        this.numVal=numVal;
    }

    public ConstExtendNum getConstExtendNum() {
        return ConstExtendNum;
    }

    public void setConstExtendNum(ConstExtendNum ConstExtendNum) {
        this.ConstExtendNum=ConstExtendNum;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstExtendNum!=null) ConstExtendNum.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstExtendNum!=null) ConstExtendNum.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstExtendNum!=null) ConstExtendNum.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumCDeclExtend(\n");

        buffer.append(" "+tab+numcIdent);
        buffer.append("\n");

        buffer.append(" "+tab+numVal);
        buffer.append("\n");

        if(ConstExtendNum!=null)
            buffer.append(ConstExtendNum.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumCDeclExtend]");
        return buffer.toString();
    }
}
