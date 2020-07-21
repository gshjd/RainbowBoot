package com.example.rainboot.config.jwt;

import com.auth0.jwt.JWT;
import com.example.rainboot.common.util.AESUtil;
import com.example.rainboot.trunk.user.model.UserUser;
import com.example.rainboot.trunk.user.model.vo.UserUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author 小熊
 * @Created 2018-10-18 23:26
 */
public class JwtUtil {
    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param user 登录成功的user对象
     * @return token
     */
    public static String createJWT(UserUserVO user) {
        //指定签名的时候使用的签名算法，也就是header那部分，jwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = user.getPassword();

        //生成签发人
        String subject = UUID.randomUUID().toString();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(new Date(nowMillis))
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        //设置过期时间
        builder.setExpiration(new Date(Integer.MAX_VALUE));
        return AESUtil.encode(builder.compact());
    }


    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @param user  用户的对象
     * @return 解密后的数据
     */
    public static Claims parseJWT(String token, UserUser user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getPassword();

        //得到DefaultJwtParser
        return Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
    }


    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     *
     * @param token token
     * @param user  用户信息
     * @return 是否校验通过
     */
    static Boolean isVerify(String token, UserUserVO user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getPassword();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return StringUtils.equals(claims.get("password").toString(), user.getPassword());
    }

    /**
     * 获取访问用户名称
     *
     * @param token token
     * @return 用户名
     */
    public static String getUsername(String token) {
        token = AESUtil.decode(token);
        return JWT.decode(Objects.requireNonNull(token)).getClaim("username").asString();
    }

    /**
     * 查找token字段值
     *
     * @param token  token
     * @param field  字段
     * @param object 返回对象
     * @return 对象
     */
    public static Object getField(String token, String field, Object object) {
        if (StringUtils.equals("", token) || StringUtils.equals(null, token)) {
            return "";
        }
        token = AESUtil.decode(token);
        return JWT.decode(Objects.requireNonNull(token)).getClaim(field).as(object.getClass());
    }

    /**
     * 查找token字段值
     *
     * @param token  token
     * @param field  字段
     * @param object 返回对象
     * @return 对象
     */
    public static Object getFieldForList(String token, String field, Object object) {
        if (StringUtils.equals("", token) || StringUtils.equals(null, token)) {
            return "";
        }
        token = AESUtil.decode(token);
        if (token != null) {
            return JWT.decode(token).getClaim(field).asList(object.getClass());
        }
        return "";
    }
}
