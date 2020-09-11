package ps.zhifa.test.eltest.el.elengine.fastenginesate;

/**
 * @author 织法酱
 */

public enum EFastElEngineState
{
    /** 读取阶段
     * 该阶段保存最近的2个字符，遇到[(时进入表达式阶段
     *
     * */
    ReadState,
    /**表达式阶段
     * 该阶段保存最近的2个字符，遇到)]时退出到读取阶段，把表达式传给表达式处理器
     * 该阶段遇到${则进入变量阶段
     * */
    Expression,
    /**变量阶段
     *该阶段保存最近的1个字符，遇到}时，则退出到表达式阶段，进行变量解析
     * */
    Var,
}
