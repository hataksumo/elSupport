package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.StringExpressElement;

/**
 * @author 织法酱
 */
public class ExpressionStringState extends BaseReadingVarState{
    protected boolean mTransfer;
    public ExpressionStringState(ExpressionState pContext) {
        super(EExpressionState.String, pContext);
    }

    @Override
    public void readC(char c) throws Exception {
        if(!mTransfer)
        {
            //非转义状态
            if('\"' != c)
            {
                if('\\' == c)
                {
                    mTransfer = true;
                }
                else
                {
                    mSb.append(c);
                }
            }
            else
            {
                mContext.addVarElement(getResult());
                mContext.contextChangeState(this,EExpressionState.VarFinish.ordinal(),c);
            }
        }
        else
        {
            //转义状态
            mSb.append(c);
            mTransfer = false;
        }
    }

    @Override
    public void init()
    {
        super.init();
        mTransfer = false;
    }

    @Override
    public BaseExpressElement getResult() {
        return new StringExpressElement(mSb.toString());
    }

    @Override
    public void onChangeState(Object message, int pFromState) {

    }
}
