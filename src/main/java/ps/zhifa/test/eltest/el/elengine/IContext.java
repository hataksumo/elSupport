package ps.zhifa.test.eltest.el.elengine;

import java.util.Map;

/**
 * @author 织法酱
 */
public interface IContext
{
    Object getVar(String pKey) throws Exception;
    void setVariable(String pKey,Object pVal);
    void clearVariables();
    void setVariables(Map<String,Object> pVals);
}
