package dot.cc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import dot.cc.exception.GenerateTokenException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by haizhu on 2016/12/24.
 * 
 * 
 * <p>
 * haizhu12345@gmail.com
 * 
 */
public class DotEngine {

    private final JwtBuilder jwtBuilder;

    private final ObjectMapper objectMapper;

    private String dot_engine_api_url = "https://janus.dot.cc/api/createToken";

    private final String app_key;

    private final String app_secret;

    private boolean debug = false;

    private int connectTimeout = 10000;

    private int readTimeout = 10000;

    /**
     * enable print log info
     */
    public void enableLog() {
        this.debug = true;
    }

    private static final String FORMAT = "{" + "\"room\":\"%s\"," + "\"user\":\"%s\","
            + "\"appkey\":\"%s\"," + "\"expires\":%d," + "\"role\":\"%S\"," + "\"nonce\":%d" + "}";

    /**
     * init by appKey appSecret
     * 
     * @param appKey you app key
     * @param appSecret you app secret
     */
    public DotEngine(String appKey, String appSecret) {
        this.app_key = appKey;
        this.app_secret = appSecret;

        jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, app_secret.getBytes())
                .setHeaderParam("alg", "HS256")//
                .setHeaderParam("typ", "JWT");//这是一个坑
        jwtBuilder.setSubject("test").compact();

        objectMapper = new ObjectMapper();
    }

    /**
     *
     * default not need set
     * 
     * @param url set engine api url
     */
    public void setDotE2ngineApiUrl(String url) {
        this.dot_engine_api_url = url;
    }

    /**
     * 
     * @param room chat room
     * @param user chat user
     * @param expireTime expire time
     * @param role not use yet
     * @param nonce not use yet
     * @throws GenerateTokenException if failure return exception
     * 
     * @return get jwt src data
     */
    public String getJWTData(String room, String user, long expireTime, String role, int nonce)
            throws GenerateTokenException {
        return String.format(FORMAT, room, user, app_key, expireTime, role, nonce);
    }

    /**
     *
     * @param room room
     * @param user userName
     * @param expireTime expire time
     * @return create token
     * @throws GenerateTokenException   throw msg when  get token failure
     * 
     * @see DotEngine#getJWTData(java.lang.String, java.lang.String, long,
     *      java.lang.String, int)
     */
    public String createToken(String room, String user, long expireTime)
            throws GenerateTokenException {
        return createToken(room, user, expireTime, "");
    }

    /**
     * 
     * 
     * @param room room
     * @param user userName
     * @param expireTime expire time
     * @param role role default null
     * @return create token
     */
    private String createToken(String room, String user, long expireTime, String role)
            throws GenerateTokenException {

        int not = new Random().nextInt(9999999);
        String srcData = getJWTData(room, user, expireTime, role, not);
        if (debug) {
            System.out.println("get jwt src data=" + srcData);
        }
        long time = System.currentTimeMillis();
        String data = jwtBuilder.setSubject(srcData).compact();
        if (debug) {
            System.out.println("get jwt src cost=" + (System.currentTimeMillis() - time)
                    + "ms encode =" + data);
        }

        String responseJson = getResponseJson(data);
        if (debug) {
            System.out.println("responseJson=" + responseJson);
        }
        if (responseJson.length() > 0) {
            try {
                JsonNode jsonpObject = objectMapper.readTree(responseJson);
                JsonNode e = jsonpObject.get("e");
                if (e != null && e.asText().length() > 0) {
                    throw new GenerateTokenException(e.asText());
                }
                return jsonpObject.get("d").get("token").asText();

            } catch (IOException e) {
                throw new GenerateTokenException(
                        "can'n parse response :" + responseJson + "\n" + e.getMessage());
            }
        } else {
            throw new GenerateTokenException("response return failure");
        }
    }

    private String getResponseJson(String data) throws GenerateTokenException {

        BufferedInputStream in = null;
        try {
            if (debug) {
                System.out.println("start send request to " + dot_engine_api_url);
            }
            URL url = new URL(dot_engine_api_url);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(connectTimeout);
            urlConn.setReadTimeout(readTimeout);
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.connect();

            BufferedOutputStream out = new BufferedOutputStream(urlConn.getOutputStream());
            StringBuilder paramsContent = new StringBuilder();
            paramsContent.append("appkey=").append(URLEncoder.encode(app_key, "UTF-8"));
            paramsContent.append("&sign=").append(URLEncoder.encode(data, "UTF-8"));
            out.write(paramsContent.toString().getBytes("UTF-8"));
            out.flush();
            out.close();

            int responseCode = urlConn.getResponseCode();
            if (debug) {
                System.out.println("httpcode=" + responseCode);
            }
            if (responseCode != 200) {
                throw new GenerateTokenException(
                        "send request failure   responseCode=" + responseCode);
            }
            in = new BufferedInputStream(urlConn.getInputStream());

            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[8192];
            int lg = -1;
            while ((lg = in.read(buffer, 0, buffer.length)) > 0) {
                sb.append(new String(buffer, 0, lg));
            }
            in.close();
            return sb.toString();
        } catch (Exception e) {
            if (debug) {
                System.err.println("err " + e);
            }
            throw new GenerateTokenException("send request failure  \n" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * default 10s
     * 
     * @param connectTimeout connect timeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * default 10s
     * 
     * @param readTimeout read timeout
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
