package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;

/**
 * @author 织法酱
 */
public class ExpressionSigState extends BaseExpressionState{
    public ExpressionSigState(ExpressionState pContext) {
        super(EExpressionState.Sig,pContext);
    }
    @Override
    public void readC(char c) throws Exception {
        _readC(c);
        if(isSig(c))
        {
            throw new Exception("处理表达式时，遇到2个连续的符号");
        }
    }

    private void _readC(char c) throws Exception {
        if(isDigital(c))
        {
            mContext.contextChangeState(this,EExpressionState.Number.ordinal(),c);
        }
        else if('('==c)
        {
            mContext.addLeftBracket();
        }
        else if(')' == c)
        {
            mContext.addRightBracket();
        }
        else if('\"' == c)
        {
            mContext.contextChangeState(this,EExpressionState.String.ordinal(),c);
        }
    }

    @Override
    public BaseExpressElement getResult() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void onChangeState(Object message, int pFromState) throws Exception {
        char op = (char)message;
        _readC(op);
        if(isSig(op))
        {
            mContext.addOp(op);
        }

//        EExpressionState state = EExpressionState.values()[pFromState];
//        switch (state)
//        {
//            case Begin:
//                break;
//            case Number:
//                break;
//            case Sig:
//                break;
//        }
    }
}
