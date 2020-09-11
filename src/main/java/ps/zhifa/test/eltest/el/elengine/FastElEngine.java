package ps.zhifa.test.eltest.el.elengine;


import ps.zhifa.test.eltest.el.elengine.fastenginesate.*;

/**
 * @author 织法酱
 */
public class FastElEngine implements IStateContext
{
    private EFastElEngineState eState;
    BaseFastElEngineState state;
    BaseFastElEngineState[] states;
    IContext mCurContext;

    public FastElEngine()
    {
        states = new BaseFastElEngineState[3];
        states[EFastElEngineState.ReadState.ordinal()] = new ReadState(this);
        states[EFastElEngineState.Expression.ordinal()] = new ExpressionState(this);
        states[EFastElEngineState.Var.ordinal()] = new VarState(this);
    }

    public void init()
    {
        eState = EFastElEngineState.ReadState;
        states[EFastElEngineState.ReadState.ordinal()].init();
        states[EFastElEngineState.Expression.ordinal()].init();
        states[EFastElEngineState.Var.ordinal()].init();
        state = states[EFastElEngineState.ReadState.ordinal()];
    }

    public String process(String pOrgStr,IContext pContext) throws Exception {
        init();
        eState = EFastElEngineState.ReadState;
        char[] orgStrArr = pOrgStr.toCharArray();
        mCurContext = pContext;
        StringBuilder readStateSb = new StringBuilder();
        for(int i=0;i<orgStrArr.length;i++)
        {
            char c = orgStrArr[i];
            state.readOne(c);
        }
        mCurContext = null;
        state = states[EFastElEngineState.ReadState.ordinal()];
        return state.getResult().toString();

    }

    @Override
    public void contextChangeState(IState pFrom, int pEState, Object pMsg) throws Exception {
        eState = EFastElEngineState.values()[pEState];
        BaseFastElEngineState stateFrom = (BaseFastElEngineState)pFrom;
        switch (eState)
        {
            case ReadState:
                state = states[EFastElEngineState.ReadState.ordinal()];
                break;
            case Expression:
                state = states[EFastElEngineState.Expression.ordinal()];
                if(pFrom.whoImI() != EFastElEngineState.Var.ordinal())
                {
                    state.init();
                }
                break;
            case Var:
                state = states[EFastElEngineState.Var.ordinal()];
                state.init();
                break;
        }
        state.onChangeState(pMsg,stateFrom.whoImI());
    }

    public Object GetVar(String pKey) throws Exception {
        return mCurContext.getVar(pKey);
    }

    public void setCalExpress(boolean isCal)
    {
        ((ExpressionState)states[EFastElEngineState.Expression.ordinal()]).setCalExpression(isCal);
    }

}
