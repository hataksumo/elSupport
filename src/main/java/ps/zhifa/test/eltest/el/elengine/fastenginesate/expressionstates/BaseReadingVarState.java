package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;

/**
 * @author 织法酱
 */
public abstract class BaseReadingVarState extends BaseExpressionState{
    protected StringBuilder mSb;
    protected boolean hasDot;
    protected BaseReadingVarState(EExpressionState pEHandleState, ExpressionState pContext) {
        super(pEHandleState,pContext);
        mSb = new StringBuilder();
    }

    public void addPoint() throws Exception {
        if(mSb.length()==0)
        {
            mSb.append('0');
        }
        if(hasDot)
        {
            throw new Exception("表达式引擎在处理数字时遇到了2个或以上的小数点");
        }
        mSb.append('.');
    }

    @Override
    public void init()
    {
        mSb.setLength(0);
        hasDot = false;
    }
}
