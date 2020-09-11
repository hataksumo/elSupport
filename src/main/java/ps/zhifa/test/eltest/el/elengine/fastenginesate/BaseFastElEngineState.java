package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;

import java.rmi.ServerException;

/**
 * @author 织法酱
 */
public abstract class BaseFastElEngineState implements IState
{
    protected EFastElEngineState meHandleState;
    protected FastElEngine mContext;
    protected BaseFastElEngineState(FastElEngine pContext, EFastElEngineState pEHandleState)
    {
        mContext = pContext;
        meHandleState = pEHandleState;
    }
    /**
     * 读取一个字符
     * @param c 字符
     */
    public abstract void readOne(char c) throws ServerException, Exception;

    /**
     * 获取该状态的处理结果
     * @return
     */
    public abstract Object getResult() throws ServerException, Exception;


    /**
     * 获取自身状态的枚举值
     * @return 自身状态的枚举值
     */
    @Override
    public int whoImI()
    {
        return meHandleState.ordinal();
    }
}
