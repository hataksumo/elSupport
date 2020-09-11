package ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement;

/**
 * @author 织法酱
 */
public abstract class BaseValueExpressElement extends BaseExpressElement{
    protected BaseValueExpressElement(EExpressElementType pType) {
        super(pType);
    }

    /**
     * 获取值
     * @return 值
     */
    public abstract Object getValue();
}
