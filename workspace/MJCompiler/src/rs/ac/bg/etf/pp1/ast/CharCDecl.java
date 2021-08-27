// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class CharCDecl extends ConstDecl {

    private Type Type;
    private String charcIdent;
    private Character charVal;
    private ConstExtendChar ConstExtendChar;

    public CharCDecl (Type Type, String charcIdent, Character charVal, ConstExtendChar ConstExtendChar) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.charcIdent=charcIdent;
        this.charVal=charVal;
        this.ConstExtendChar=ConstExtendChar;
        if(ConstExtendChar!=null) ConstExtendChar.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getCharcIdent() {
        return charcIdent;
    }

    public void setCharcIdent(String charcIdent) {
        this.charcIdent=charcIdent;
    }

    public Character getCharVal() {
        return charVal;
    }

    public void setCharVal(Character charVal) {
        this.charVal=charVal;
    }

    public ConstExtendChar getConstExtendChar() {
        return ConstExtendChar;
    }

    public void setConstExtendChar(ConstExtendChar ConstExtendChar) {
        this.ConstExtendChar=ConstExtendChar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstExtendChar!=null) ConstExtendChar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstExtendChar!=null) ConstExtendChar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstExtendChar!=null) ConstExtendChar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CharCDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+charcIdent);
        buffer.append("\n");

        buffer.append(" "+tab+charVal);
        buffer.append("\n");

        if(ConstExtendChar!=null)
            buffer.append(ConstExtendChar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharCDecl]");
        return buffer.toString();
    }
}
