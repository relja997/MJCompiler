// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends Unmatched {

    private IfHeader IfHeader;
    private ThenStatement ThenStatement;
    private ElseStmt ElseStmt;
    private ElsePart ElsePart;

    public IfElseStatement (IfHeader IfHeader, ThenStatement ThenStatement, ElseStmt ElseStmt, ElsePart ElsePart) {
        this.IfHeader=IfHeader;
        if(IfHeader!=null) IfHeader.setParent(this);
        this.ThenStatement=ThenStatement;
        if(ThenStatement!=null) ThenStatement.setParent(this);
        this.ElseStmt=ElseStmt;
        if(ElseStmt!=null) ElseStmt.setParent(this);
        this.ElsePart=ElsePart;
        if(ElsePart!=null) ElsePart.setParent(this);
    }

    public IfHeader getIfHeader() {
        return IfHeader;
    }

    public void setIfHeader(IfHeader IfHeader) {
        this.IfHeader=IfHeader;
    }

    public ThenStatement getThenStatement() {
        return ThenStatement;
    }

    public void setThenStatement(ThenStatement ThenStatement) {
        this.ThenStatement=ThenStatement;
    }

    public ElseStmt getElseStmt() {
        return ElseStmt;
    }

    public void setElseStmt(ElseStmt ElseStmt) {
        this.ElseStmt=ElseStmt;
    }

    public ElsePart getElsePart() {
        return ElsePart;
    }

    public void setElsePart(ElsePart ElsePart) {
        this.ElsePart=ElsePart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfHeader!=null) IfHeader.accept(visitor);
        if(ThenStatement!=null) ThenStatement.accept(visitor);
        if(ElseStmt!=null) ElseStmt.accept(visitor);
        if(ElsePart!=null) ElsePart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfHeader!=null) IfHeader.traverseTopDown(visitor);
        if(ThenStatement!=null) ThenStatement.traverseTopDown(visitor);
        if(ElseStmt!=null) ElseStmt.traverseTopDown(visitor);
        if(ElsePart!=null) ElsePart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfHeader!=null) IfHeader.traverseBottomUp(visitor);
        if(ThenStatement!=null) ThenStatement.traverseBottomUp(visitor);
        if(ElseStmt!=null) ElseStmt.traverseBottomUp(visitor);
        if(ElsePart!=null) ElsePart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

        if(IfHeader!=null)
            buffer.append(IfHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ThenStatement!=null)
            buffer.append(ThenStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStmt!=null)
            buffer.append(ElseStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElsePart!=null)
            buffer.append(ElsePart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
