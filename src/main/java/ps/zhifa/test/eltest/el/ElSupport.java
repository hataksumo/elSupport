package ps.zhifa.test.eltest.el;
import org.springframework.stereotype.Component;
import ps.zhifa.test.eltest.el.elengine.FastElEngine;
import ps.zhifa.test.eltest.el.elengine.HashMapContext;
import ps.zhifa.test.eltest.el.elengine.IContext;

import java.rmi.ServerException;
import java.util.Map;

/**
 * @author 织法酱
 */
@Component
public class ElSupport
{
    private IContext context;
    FastElEngine engine;


    public ElSupport()
    {
        engine = new FastElEngine();
        context = new HashMapContext();
    }

    public void setData(String pKey,Map<String,Object> pMap)
    {
        context.setVariable(pKey,pMap);
    }

    public void setData(String pKey,String pData)
    {
        context.setVariable(pKey,pData);
    }

    public void setData(String pKey,int pData)
    {
        context.setVariable(pKey,pData);
    }

    public void setData(String pKey,double pData)
    {
        context.setVariable(pKey,pData);
    }

    public void initWithMap(Map<String,Object> pMap)
    {
        context.clearVariables();
        context.setVariables(pMap);
    }


    public String handleElString(String pOrgString) throws Exception {
        return engine.process(pOrgString,context);
    }

    public void setCalExpress(boolean isCal)
    {
        engine.setCalExpress(isCal);
    }

}
