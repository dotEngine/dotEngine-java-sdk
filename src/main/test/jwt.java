import java.io.UnsupportedEncodingException;
import java.util.Base64;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by haizhu on 2016/12/25.
 * <p>
 * haizhu12345@gmail.com
 */
public class jwt {
//    mvn install:install-file -Dfile=dotEngine-0.1.0.jar -DgroupId=cc.dot -DartifactId=dotEngien-java-sdk -Dversion=0.1.0 -Dpackaging=jar
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub25jZSI6MSwidXNlcl9pZCI6InVzZXIiLCJyb29tIjoicm9vbSIsImV4cGlyZXMiOjg2NDAwLCJhcHBfa2V5IjoiZG90Y2MiLCJyb2xlIjoiIn0.cRFAD7G9QSkYVh39HH6y8ofppf01vXQcyO18x8N5bhc
    public static void main(String args[]) throws UnsupportedEncodingException {

                String result = Jwts.builder().signWith(SignatureAlgorithm.HS256, "dotcc".getBytes())
                        .setHeaderParam("alg", "HS256")
                        .setHeaderParam("typ", "JWT")
                        .setPayload(
                                "{\"room\":\"room\",\"user_id\":\"user\",\"app_key\":\"dotcc\",\"expires\":864000,\"role\":\"\",\"nonce\":1}")
                        .compact();
                System.out.println(result);
        //
        //        Jwts.header()

        System.exit(0);
    }

}
