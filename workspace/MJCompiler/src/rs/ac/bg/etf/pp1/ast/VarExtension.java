// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class VarExtension extends VarExtend {

    private String varIdent;
    private OptArray OptArray;
    private VarExtend VarExtend;

    public VarExtension (String varIdent, OptArray OptArray, VarExtend VarExtend) {
        this.varIdent=varIdent;
        this.OptArray=OptArray;
        if(OptArray!=null) OptArray.setParent(this);
        this.VarExtend=VarExtend;
        if(VarExtend!=null) VarExtend.setParent(this);
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
        if(OptArray!=null) OptArray.accept(visitor);
        if(VarExtend!=null) VarExtend.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptArray!=null) OptArray.traverseTopDown(visitor);
        if(VarExtend!=null) VarExtend.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptArray!=null) OptArray.traverseBottomUp(visitor);
        if(VarExtend!=null) VarExtend.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarExtension(\n");

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
        buffer.append(") [VarExtension]");
        return buffer.toString();
    }
}
