package ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement;

/**
 * @author 织法酱
 */
public class OpExpressElement extends BaseExpressElement{
    char op;
    public OpExpressElement(char pOp) {
        super(EExpressElementType.Op);
        op = pOp;
    }

    public char getOp()
    {
        return op;
    }


    public int getPriority() {
        if('+' == op||'-'==op)
        {
            return 1;
        }
        else if('*'==op||'/'==op)
        {
            return 2;
        }
        return -1;
    }


    @Override
    public String getData() {
        return String.valueOf(op);
    }
}
