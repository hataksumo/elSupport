package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import java.rmi.ServerException;

public interface IState
{
    /**
     * 状态的初始化
     */
    void init();

    /**
     * 改变状态
     * @param pEState 状态的枚举值
     * @param pMsg 向新状态传递的消息
     */
    void changeState(int pEState, Object pMsg) throws ServerException, Exception;

    /**
     * 状态改变到本状态时的回调
     * @param message 老状态传递过来的消息
     * @param pFromState 老状态枚举值
     */
    void onChangeState(Object message, int pFromState) throws ServerException, Exception;

    /**
     * 我是什么状态
     * @return 状态枚举值
     */
    int whoImI();
}
