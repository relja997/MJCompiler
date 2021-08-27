// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class BoolCDecl extends ConstDecl {

    private Type Type;
    private String boolcIdent;
    private Boolean boolVal;
    private ConstExtendBool ConstExtendBool;

    public BoolCDecl (Type Type, String boolcIdent, Boolean boolVal, ConstExtendBool ConstExtendBool) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.boolcIdent=boolcIdent;
        this.boolVal=boolVal;
        this.ConstExtendBool=ConstExtendBool;
        if(ConstExtendBool!=null) ConstExtendBool.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getBoolcIdent() {
        return boolcIdent;
    }

    public void setBoolcIdent(String boolcIdent) {
        this.boolcIdent=boolcIdent;
    }

    public Boolean getBoolVal() {
        return boolVal;
    }

    public void setBoolVal(Boolean boolVal) {
        this.boolVal=boolVal;
    }

    public ConstExtendBool getConstExtendBool() {
        return ConstExtendBool;
    }

    public void setConstExtendBool(ConstExtendBool ConstExtendBool) {
        this.ConstExtendBool=ConstExtendBool;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstExtendBool!=null) ConstExtendBool.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstExtendBool!=null) ConstExtendBool.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstExtendBool!=null) ConstExtendBool.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolCDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+boolcIdent);
        buffer.append("\n");

        buffer.append(" "+tab+boolVal);
        buffer.append("\n");

        if(ConstExtendBool!=null)
            buffer.append(ConstExtendBool.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolCDecl]");
        return buffer.toString();
    }
}
