package ps.zhifa.test.eltest.el.elengine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 织法酱
 */
public class HashMapContext extends HashMap<String,Object> implements IContext {
    @Override
    public Object getVar(String pKey) throws Exception {
        String[] keyHeb = pKey.split("\\.");
        Map<String,Object> cur = this;
        Object var = null;
        for(int i=0;i<keyHeb.length;i++)
        {
            var = cur.get(keyHeb[i]);
            if(null == var)
            {
                throw new Exception("没有找到变量名"+keyHeb[i]);
            }
            if(i<keyHeb.length-1 )
            {
                if(!(var instanceof Map))
                {
                    throw new Exception("中间值不是Map"+keyHeb[i]);
                }
                cur = (Map<String,Object>)var;
            }
        }
        return var;
    }

    @Override
    public void setVariable(String pKey, Object pVal) {
        put(pKey,pVal);
    }

    @Override
    public void clearVariables() {
        clear();
    }

    @Override
    public void setVariables(Map<String, Object> pVals) {
        putAll(pVals);
    }
}
