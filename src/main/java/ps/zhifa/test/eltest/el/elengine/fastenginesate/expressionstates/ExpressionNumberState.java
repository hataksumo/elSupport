package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.EExpressElementType;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.NumberExpressElement;

/**
 * @author 织法酱
 */
public class ExpressionNumberState extends BaseReadingVarState {
    boolean hasDot;
    public ExpressionNumberState(ExpressionState pContext) {
        super(EExpressionState.Number,pContext);
    }

    @Override
    public void readC(char c) throws Exception {
        if(isDigital(c))
        {
            mSb.append(c);
        }
        else if('.' == c)
        {
            if(hasDot)
            {
                throw new Exception("表达式处理数字时，遇到2个或以上的小数点");
            }
            if(mSb.length() == 0)
            {
                mSb.append('.');
            }
            mSb.append(c);
            hasDot = true;
        }
        else if(isConSig(c))
        {

            mContext.addVarElement(getResult());
            mContext.contextChangeState(this,EExpressionState.Sig.ordinal(),c);
        }
    }

    @Override
    public void init()
    {
        super.init();
        hasDot = false;
    }

    @Override
    public BaseExpressElement getResult() {
        if(hasDot)
        {
            return new NumberExpressElement(Double.valueOf(mSb.toString()), EExpressElementType.Double);
        }
        else
        {
            return new NumberExpressElement(Integer.valueOf(mSb.toString()),EExpressElementType.Integer);
        }
    }

    @Override
    public void onChangeState(Object pMsg, int pFromState) throws Exception {
        if(null != pMsg)
        {
            readC((char)pMsg);
        }
//        EExpressionState state = EExpressionState.values()[pFromState];
//        switch (state)
//        {
//            case Begin:
//                mNumberSb.append(pMsg);
//                break;
//            case Sig:
//                mNumberSb.append(pMsg);
//                break;
//        }
    }
}
