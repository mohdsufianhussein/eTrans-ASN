/* Generated By:JJTree: Do not edit this line. ASTMultipleTypeConstraints.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javacc;

public
class ASTMultipleTypeConstraints extends SimpleNode {
  public ASTMultipleTypeConstraints(int id) {
    super(id);
  }

  public ASTMultipleTypeConstraints(AsnParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AsnParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=bdc225499be8e09a8257fb9c2dfe5b07 (do not edit this line) */