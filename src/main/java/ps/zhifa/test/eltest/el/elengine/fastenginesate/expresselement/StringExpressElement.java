package ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement;

/**
 * @author 织法酱
 */
public class StringExpressElement extends BaseValueExpressElement{
    String mData;
    public StringExpressElement(String pData) {
        super(EExpressElementType.String);
        mData = pData;
    }
    @Override
    public String getData()
    {
        return "\""+ mData +"\"";
    }

    @Override
    public Object getValue() {
        return mData;
    }
}
