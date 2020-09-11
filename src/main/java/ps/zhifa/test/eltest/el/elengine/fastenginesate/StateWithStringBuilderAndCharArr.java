package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;

import java.rmi.ServerException;

/**
 * @author 织法酱
 */
abstract class StateWithStringBuilderAndCharArr extends StateWithTestCharArr
{
    protected StringBuilder mSb;
    protected StateWithStringBuilderAndCharArr(FastElEngine pContext, EFastElEngineState pEHandleState, int pTestCharLen)
    {
        super(pContext,pEHandleState,pTestCharLen);
        mSb = new StringBuilder();
    }

    @Override
    public void init() {
        super.init();
        mSb.setLength(0);
    }

    @Override
    public Object getResult() throws ServerException, Exception {
        return mSb.toString();
    }
}
