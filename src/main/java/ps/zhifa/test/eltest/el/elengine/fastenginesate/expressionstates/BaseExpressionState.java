package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;



import ps.zhifa.test.eltest.el.elengine.fastenginesate.ExpressionState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.IState;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.BaseExpressElement;

import java.rmi.ServerException;
import java.util.HashSet;

/**
 * @author 织法酱
 */
public abstract class BaseExpressionState implements IState
{
    protected EExpressionState meHandleState;
    protected ExpressionState mContext;
    protected static final HashSet<Character> conOpSet;
    protected static final HashSet<Character> opSet;

    static {
        conOpSet = new HashSet<Character>(){{
           add('+');
           add('-');
           add('*');
           add('/');
           add('(');
           add(')');
        }};
        opSet = new HashSet<Character>(){{
            add('+');
            add('-');
            add('*');
            add('/');
        }};
    }

    protected BaseExpressionState(EExpressionState pEHandleState,ExpressionState pContext)
    {
        meHandleState = pEHandleState;
        mContext = pContext;
    }
    @Override
    public int whoImI()
    {
        return meHandleState.ordinal();
    }

    @Override
    public void changeState(int pEState, Object pMsg) throws Exception {
        mContext.contextChangeState(this,pEState,pMsg);
    }

    /**
     * 读取一个字符
     * @param c 字符
     * @throws ServerException
     */
    public abstract void readC(char c) throws ServerException, Exception;

    public boolean isDigital(char c)
    {
        return c >= '0' && c <= '9';
    }

    public static boolean isConSig(char c)
    {
        return conOpSet.contains(c);
    }

    public static boolean isSig(char c)
    {
        return opSet.contains(c);
    }

    /**
     * 强制停止时，上下文类调用状态类，让状态强行出结果
     * @return
     */
    public abstract BaseExpressElement getResult();

}
