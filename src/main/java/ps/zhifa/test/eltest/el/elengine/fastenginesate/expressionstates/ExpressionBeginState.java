package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;

/**
 * @author 织法酱
 */
public class ExpressionBeginState extends BaseExpressionState {
    public ExpressionBeginState(ExpressionState pContext) {
        super(EExpressionState.Begin,pContext);
    }


    @Override
    public void init() {

    }


    @Override
    public void onChangeState(Object message, int pFromState) {

    }

    @Override
    public void readC(char c) throws Exception {
        if('$' == c)
        {
            return;
        }


        if(isDigital(c))
        {
            mContext.contextChangeState(this,EExpressionState.Number.ordinal(),c);
        }
        else if('.' == c)
        {
            mContext.contextChangeState(this,EExpressionState.Number.ordinal(),c);
        }
        else if('+'==c||'-'==c)
        {
            mContext.contextChangeState(this,EExpressionState.Number.ordinal(),c);
        }
        else if('(' == c)
        {
            mContext.addLeftBracket();
            mContext.contextChangeState(this,EExpressionState.Sig.ordinal(),c);
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

}
