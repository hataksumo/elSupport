package ps.zhifa.test.eltest.el.elengine;


import ps.zhifa.test.eltest.el.elengine.fastenginesate.IState;

import java.rmi.ServerException;

/**
 * @author 织法酱
 */
public interface IStateContext
{
    /**
     * 通知上下文切换状态
     * @param pFrom 源状态
     * @param pEState 新状态枚举值
     * @param pMsg 发送的消息
     */
    void contextChangeState(IState pFrom, int pEState, Object pMsg) throws ServerException, Exception;
}
