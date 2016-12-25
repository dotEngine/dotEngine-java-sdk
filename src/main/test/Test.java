import dot.cc.DotEngine;
import dot.cc.exception.GenerateTokenException;

/**
 * Created by haizhu on 2016/12/24.
 * <p>
 * haizhu12345@gmail.com
 */
public class Test {

    public static void main(String args[]) {

        String appKey = "dotcc";
        String appSecret = "dotcc";

        //init appKey appSecret
        //NOTE init is  slow ,It will be cost 5s
        DotEngine dotEngine = new DotEngine(appKey, appSecret);

        //set connect timeout  default 10s
        dotEngine.setConnectTimeout(2000);

        //set read timeout  default 10s
        dotEngine.setReadTimeout(2000);

        //enable log
//        dotEngine.enableLog();

        String token = null;
        try {
            //set  room user expireTime
            token = dotEngine.createToken("room", "user", 24 * 36000);


        } catch (GenerateTokenException e) {
            //create token   failure
            e.printStackTrace();
        }
//        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6MSwidXNlcl9pZCI6InVzZXIiLCJyb29tIjoicm9vbSIsImV4cGlyZXMiOjg2NDAwLCJhcHBfa2V5IjoiZG90Y2MiLCJyb2xlIjoiIn0.cRFAD7G9QSkYVh39HH6y8ofppf01vXQcyO18x8N5bhc
//        eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJyb29tXCI6XCJyb29tXCIsXCJ1c2VyX2lkXCI6XCJ1c2VyXCIsXCJhcHBfa2V5XCI6XCJkb3RjY1wiLFwiZXhwaXJlc1wiOjg2NDAwMCxcInJvbGVcIjpcIlwiLFwibm9uY2VcIjpcIjFcIn0ifQ.ApJm6cB27UyKUO7lytovqQlBPsyM_YI1H4zprdxSCXo

        //get result token  ; if failure return  null
        System.out.println(token);
    }

}
