import dot.cc.DotEngine;
import dot.cc.exception.GenerateTokenException;

/**
 * Created by haizhu on 2016/12/24.
 * <p>
 * haizhu12345@gmail.com
 */
public class Test {

    public static void main(String args[]) {

        String appKey = "appLey";
        String appSecret = "secret";

        //init appKey appSecret
        //NOTE init is  slow ,It will be cost 5s
        DotEngine dotEngine = new DotEngine(appKey, appSecret);

        //set connect timeout  default 10s
        dotEngine.setConnectTimeout(2000);

        //set read timeout  default 10s
        dotEngine.setReadTimeout(2000);

        //enable log
        dotEngine.enableLog();

        String token = null;
        try {
            //set  room user expireTime
            token = dotEngine.createToken("room", "user", 24 * 36000);


        } catch (GenerateTokenException e) {
            //create token   failure
            e.printStackTrace();
        }

        //get result token  ; if failure return  null
        System.out.println(token);
    }

}
