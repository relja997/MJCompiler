// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class VarDeclaration extends VarDecl {

    private Type Type;
    private String varIdent;
    private OptArray OptArray;
    private VarExtend VarExtend;

    public VarDeclaration (Type Type, String varIdent, OptArray OptArray, VarExtend VarExtend) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.varIdent=varIdent;
        this.OptArray=OptArray;
        if(OptArray!=null) OptArray.setParent(this);
        this.VarExtend=VarExtend;
        if(VarExtend!=null) VarExtend.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVarIdent() {
        return varIdent;
    }

    public void setVarIdent(String varIdent) {
        this.varIdent=varIdent;
    }

    public OptArray getOptArray() {
        return OptArray;
    }

    public void setOptArray(OptArray OptArray) {
        this.OptArray=OptArray;
    }

    public VarExtend getVarExtend() {
        return VarExtend;
    }

    public void setVarExtend(VarExtend VarExtend) {
        this.VarExtend=VarExtend;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptArray!=null) OptArray.accept(visitor);
        if(VarExtend!=null) VarExtend.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptArray!=null) OptArray.traverseTopDown(visitor);
        if(VarExtend!=null) VarExtend.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptArray!=null) OptArray.traverseBottomUp(visitor);
        if(VarExtend!=null) VarExtend.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varIdent);
        buffer.append("\n");

        if(OptArray!=null)
            buffer.append(OptArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarExtend!=null)
            buffer.append(VarExtend.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclaration]");
        return buffer.toString();
    }
}
