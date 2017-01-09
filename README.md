# DotEngine java server sdk

### use explame

```java
        String appKey = "key";
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
```

###  dependency

project dependency

https://oss.sonatype.org/content/groups/staging/cc/dot/dotEngine-java-sdk/

```xml
<dependency>
    <groupId>cc.dot</groupId>
    <artifactId>dotEngine-java-sdk</artifactId>
    <version>0.1.0</version>
</dependency>

```


```xml
       <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.7.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.8.1</version>
        </dependency>
```