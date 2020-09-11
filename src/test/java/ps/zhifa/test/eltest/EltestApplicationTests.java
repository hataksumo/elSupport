package ps.zhifa.test.eltest;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ps.zhifa.test.eltest.el.ElSupport;

import java.nio.charset.Charset;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EltestApplicationTests {

    ElSupport elSupport;

    @Value("classpath:gaojing.jsontep")
    private Resource gaojingConfig;

    @Autowired
    public EltestApplicationTests(ElSupport pElSupport)
    {
        elSupport = pElSupport;
        elSupport.setCalExpress(true);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void elTest() {
        try{
            String orgText = IOUtils.toString(gaojingConfig.getInputStream(), Charset.forName("UTF-8"));
            Map<String,Object> deviceShadow = new HashMap<String,Object>(){{
                put("ts",1);
                put("temperature",35.2);
                put("potency",0.0032);
                put("humidity",0.0125);
                put("fanState",1);
                put("cleanerState",0);
                put("pm",23.5);
                put("opened",0);
                put("nmch",0.0036);
                put("purificationRate",0.0058);
                put("avg10Potency",0.0137);
                put("businessHours",67);
                put("merchantType",2);
            }};

            Map<String,Object> warningInfo = new HashMap<String,Object>(){{
                put("name","smockWarning");
                put("lv",2);
            }};
            elSupport.setData("shadow",deviceShadow);
            elSupport.setData("warning",warningInfo);

            String finalText = elSupport.handleElString(orgText);
            System.out.println("=================手动解析==================");
            System.out.println(finalText);


            //开始测试效率
            testEffic(orgText);



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }




    }

    void testEffic(String orgText) throws Exception {
        int[] times = new int[]{100,1000,10000,100000};
        //int[] times = new int[]{10};
        String finalText;


        for(int scaleIdx=0;scaleIdx<times.length;scaleIdx++)
        {
            long processBegin = System.currentTimeMillis();
            int scale = times[scaleIdx];
            for(int i=0;i<scale;i++)
            {
                finalText = elSupport.handleElString(orgText);
                //System.out.println("#"+i+"\n"+finalText);
            }
            long processEnd = System.currentTimeMillis();
            long totalTime = processEnd - processBegin;
            processBegin = System.currentTimeMillis();
            for(int i=0;i<scale;i++)
            {

            }
            processEnd = System.currentTimeMillis();
            long deltaTime = processEnd - processBegin;
            System.out.println(String.format("运行%d次花费%d毫秒",scale,totalTime-deltaTime));
        }

        for(int scaleIdx=0;scaleIdx<times.length;scaleIdx++)
        {
            long processBegin = System.currentTimeMillis();
            int scale = times[scaleIdx];
            for(int i=0;i<scale;i++)
            {
                int ts = (int)(Math.random() * 100);
                double temperature = (double)(Math.random() * 100);
                double potency = (double)(Math.random() * 100);
                double humidity = (double)(Math.random() * 100);
                double fanState = (double)(Math.random() * 100);
                double cleanerState = (double)(Math.random() * 100);
                double pm = (double)(Math.random() * 100);
                double opened = (int)(Math.random() * 2);
                double nmch = (double)(Math.random() * 100);
                double purificationRate = (double)(Math.random() * 100);
                double avg10Potency = (double)(Math.random() * 100);
                double businessHours = (double)(Math.random() * 100);
                double merchantType = (double)(Math.random() * 100);
                String name = "smockWarning_"+Math.random() * 100;
                double lv = (int)(Math.random() * 100);



//                    orgText.replace("[(${shadow.ts})]","1")
//                            .replace("[(${shadow.temperature})]","35.2")
//                            .replace("[(${shadow.potency})]","0.0032")
//                            .replace("[(${shadow.humidity})]","0.0125")
//                            .replace("[(${shadow.fanState})]","1")
//                            .replace("[(${shadow.cleanerState})]","0")
//                            .replace("[(${shadow.pm})]","23.5")
//                            .replace("[(${shadow.opened})]","1")
//                            .replace("[(${shadow.nmch})]","0.0036")
//                            .replace("[(${shadow.purificationRate})]","0.0058")
//                            .replace("[(${shadow.avg10Potency})]","0.0137")
//                            .replace("[(${shadow.businessHours})]","67")
//                            .replace("[(${shadow.merchantType})]","2")
//                            .replace("[(${warning.name})]","smockWarning")
//                            .replace("[${warning.lv}]","1");


                orgText.replace("[(${shadow.ts})]",String.valueOf(ts))
                        .replace("[(${shadow.temperature})]",String.valueOf(temperature))
                        .replace("[(${shadow.potency})]",String.valueOf(potency))
                        .replace("[(${shadow.humidity})]",String.valueOf(humidity))
                        .replace("[(${shadow.fanState})]",String.valueOf(fanState))
                        .replace("[(${shadow.cleanerState})]",String.valueOf(cleanerState))
                        .replace("[(${shadow.pm})]",String.valueOf(pm))
                        .replace("[(${shadow.opened})]",String.valueOf(opened))
                        .replace("[(${shadow.nmch})]",String.valueOf(nmch))
                        .replace("[(${shadow.purificationRate})]",String.valueOf(purificationRate))
                        .replace("[(${shadow.avg10Potency})]",String.valueOf(avg10Potency))
                        .replace("[(${shadow.businessHours})]",String.valueOf(businessHours))
                        .replace("[(${shadow.merchantType})]",String.valueOf(merchantType))
                        .replace("[(${warning.name})]",String.valueOf(name))
                        .replace("[${warning.lv}]",String.valueOf(lv));
            }
            long processEnd = System.currentTimeMillis();
            long totalTime = processEnd - processBegin;
            processBegin = System.currentTimeMillis();
            for(int i=0;i<scale;i++)
            {
                int ts = (int)(Math.random() * 100);
                double temperature = (double)(Math.random() * 100);
                double potency = (double)(Math.random() * 100);
                double humidity = (double)(Math.random() * 100);
                double fanState = (double)(Math.random() * 100);
                double cleanerState = (double)(Math.random() * 100);
                double pm = (double)(Math.random() * 100);
                double opened = (int)(Math.random() * 2);
                double nmch = (double)(Math.random() * 100);
                double purificationRate = (double)(Math.random() * 100);
                double avg10Potency = (double)(Math.random() * 100);
                double businessHours = (double)(Math.random() * 100);
                double merchantType = (double)(Math.random() * 100);
                String name = "smockWarning_"+Math.random() * 100;
                double lv = (int)(Math.random() * 100);
            }
            processEnd = System.currentTimeMillis();
            long deltaTime = processEnd - processBegin;
            System.out.println(String.format("手动静态Replace，运行%d次花费%d毫秒",scale,totalTime-deltaTime));
        }
    }

}
