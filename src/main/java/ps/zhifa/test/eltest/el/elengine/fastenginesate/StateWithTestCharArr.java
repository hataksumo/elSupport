package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;

import java.rmi.ServerException;
import java.util.Arrays;

/**
 * @author 织法酱
 */
public abstract class StateWithTestCharArr extends BaseFastElEngineState
{
    protected char[] mTestStr;
    protected StateWithTestCharArr(FastElEngine pContext, EFastElEngineState pEHandleState, int pTestCharLen)
    {
        super(pContext,pEHandleState);
        mTestStr = new char[pTestCharLen];
    }
    protected void readTestC(char c)
    {
        int lenSub1 = mTestStr.length-1;
        for(int i=0;i<lenSub1;i++)
        {
            mTestStr[i] = mTestStr[i+1];
        }
        mTestStr[lenSub1] = c;
    }
    protected boolean strCmp(char[] pOrgStr,char[] pCmpStr)
    {
        return Arrays.equals(pOrgStr,pCmpStr);
    }

    @Override
    public void changeState(int pEState, Object pMsg) throws ServerException, Exception {
        mContext.contextChangeState(this,pEState,pMsg);
    }

    @Override
    public void init() {
        Arrays.fill(mTestStr,'\0');
    }
}
