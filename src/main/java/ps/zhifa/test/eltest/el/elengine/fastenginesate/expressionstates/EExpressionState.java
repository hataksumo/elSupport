package ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates;

/**
 * @author 织法酱
 */

public enum EExpressionState
{
    /**
     * 初始状态，没有读取任何字符
     * 该状态下，只能读取数字字符和符号，如果之前没有读取任何数字，则读取 * /也是不可以的
     * 如果读取+或-，则在元素列表中加入0和刚才的符号
     */
    Begin,
    /**
     * 数字读取状态，已经读取了至少1个数字
     * 该状态下，只能读取一次.，如果开始就读.则会默认加一个0,读取任何符号后，计算数字，把符号加入元素列表。
     */
    Number,
    /**
     * 符号状态，该状态下，只能读取数字，读取数字后进入数字状态
     */
    Sig,
    /**
     * 完成一个变量读取的状态，该状态可能由读取完变量或读取完String后进入，该状态只允许读取操作符和结束符号
     * 读取操作符后进入Sig状态
     */
    VarFinish,
    /**
     * 读取String状态，由"进入，由下一个非转义的“退出
     */
    String
}
