package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.EExpressElementType;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.NumberExpressElement;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.StringExpressElement;

/**
 * @author 织法酱
 */
public class VarState extends StateWithStringBuilderAndCharArr
{
    final char varBegStr = '}';
    private FastElEngine mVarContext;


    public VarState(FastElEngine pContext)
    {
        super(pContext, EFastElEngineState.Var,1);
        mVarContext = (FastElEngine)mContext;
    }

    @Override
    public void readOne(char c) throws Exception {
        if(varBegStr == c)
        {
            Object result = getResult();
            changeState(EFastElEngineState.Expression.ordinal(),result);
        }
        else
        {
            mSb.append(c);
        }
    }

    public Object retrieveVar(String pVarStr) throws Exception {
        Object var = mVarContext.GetVar(pVarStr);

        if(null == var)
        {
            throw new Exception("找不到变量 "+pVarStr);
        }

        if (var instanceof String)
        {
            return new StringExpressElement((String)var);
        }
        else if(var instanceof Number)
        {
            if(var instanceof Integer)
            {
                return new NumberExpressElement((Number) var, EExpressElementType.Integer);
            }
            else
            {
                return new NumberExpressElement((Number)var,EExpressElementType.Double);
            }
        }
        else
        {
            throw new Exception("未知变量类型"+var.getClass().getName());
        }
    }

    @Override
    public Object getResult() throws Exception {
        return retrieveVar(mSb.toString());
    }

    @Override
    public void onChangeState(Object message, int pFromState)
    {
        EFastElEngineState eState = EFastElEngineState.values()[pFromState];
        switch (eState)
        {
            case Expression:
                break;
        }
    }
}
