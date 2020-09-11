package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;

/**
 * @author 织法酱
 */
public class ExpressionVarFinishState extends BaseExpressionState {
    public ExpressionVarFinishState(ExpressionState pContext) {
        super(EExpressionState.VarFinish, pContext);
    }

    @Override
    public void readC(char c) throws Exception {
        if(isSig(c))
        {
            mContext.contextChangeState(this,EExpressionState.Sig.ordinal(),c);
        }
        else if(')' == c)
        {
            mContext.contextChangeState(this,EExpressionState.Sig.ordinal(),c);
        }
        else
        {
            throw new Exception("VarFinish状态下，只能读取+-*/和)");
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
    public void onChangeState(Object message, int pFromState) {

    }
}
