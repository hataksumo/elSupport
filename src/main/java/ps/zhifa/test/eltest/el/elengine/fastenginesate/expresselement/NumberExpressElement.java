package ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement;


/**
 * @author 织法酱
 */
public class NumberExpressElement extends BaseValueExpressElement {
    protected Number mVal;
    public NumberExpressElement(Number pVal,EExpressElementType pType) {
        super(pType);
        mVal = pVal;
    }

    @Override
    public String getData() {
        return String.valueOf(mVal);
    }

    @Override
    public Object getValue() {
        return mVal;
    }
}
