package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;

/**
 * @author 织法酱
 */
public class ReadState extends StateWithStringBuilderAndCharArr
{
    final char[] expressionBegStr = "[(".toCharArray();

    public ReadState(FastElEngine pContext)
    {
        super(pContext, EFastElEngineState.ReadState,2);
    }

    @Override
    public void readOne(char c) throws Exception {
        mSb.append(c);
        readTestC(c);
        if(strCmp(mTestStr,expressionBegStr))
        {
            //删掉刚才添加的前缀
            mSb.delete(mSb.length()-expressionBegStr.length,mSb.length());
            changeState(EFastElEngineState.Expression.ordinal(),"");
        }
    }

    @Override
    public void onChangeState(Object message, int pFromState) {
        EFastElEngineState eState = EFastElEngineState.values()[pFromState];
        switch (eState)
        {
            case Expression:
                mSb.append(message.toString());
                break;
        }
    }
}
