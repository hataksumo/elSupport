package ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement;

/**
 * @author 织法酱
 */
public abstract class BaseExpressElement
{
    protected EExpressElementType mType;
    protected BaseExpressElement(EExpressElementType pType)
    {
        mType = pType;
    }
    public EExpressElementType getType()
    {
        return mType;
    }
    public abstract String getData();
}
