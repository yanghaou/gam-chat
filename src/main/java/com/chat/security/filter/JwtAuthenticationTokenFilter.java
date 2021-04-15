package com.chat.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.chat.common.ResultException;
import com.chat.entity.auth.Permission;
import com.chat.entity.user.UserAccount;
import com.chat.repository.user.UserAccountRepository;
import com.chat.service.UserService;
import com.chat.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.chat.common.ResultCodeEnum.*;
import static com.chat.common.UserConstant.NOT_DEL;

/**
* Jwt登录授权过滤器
* @author chenlong
* @date 2020/12/8
*/
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value(("${jwt.tokenHead}"))
    private String tokenHead;
    @Value(("${token.valid.time:10}"))
    private int validTime;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            // 获取http请求头中的token
            String authHeader = request.getHeader(this.tokenHeader);
            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                String authToken = authHeader.substring(this.tokenHead.length());
                //token中的UID
                String uidFromToken = jwtTokenUtil.getUserNameFromToken(authToken);
                String appType = request.getHeader("AppType");
                Integer clientType = request.getIntHeader("ClientType");
                //header中的UID
                String uid = request.getHeader("Uid");
                if (StringUtils.isEmpty(uidFromToken)){
                    throw new ResultException(UID_FROM_TOKEN_EX);
                }

                if (!uidFromToken.equals(uid)){
                    log.error("uid not equal. uid from token :"+uidFromToken+",uid from header :"+uid);
                    throw new ResultException(UID_NOT_EQ_EX);
                }

                log.info("check uid from token: {}", uidFromToken);

                if (jwtTokenUtil.isTokenExpired(authToken)){
                    throw new ResultException(TOKEN_EXPIRED_EX);
                }

                //用户key
                StringBuilder key = new StringBuilder("user::")
                        .append(appType).append(":")
                        .append(clientType).append(":").append(uidFromToken);
                String userJson = stringRedisTemplate.opsForValue().get(key.toString());
                UserAccount user = null;
                if (StringUtils.isNotEmpty(userJson)){
                    user = JSONObject.parseObject(userJson,UserAccount.class);
                }else {
                    user = userAccountRepository.findByAppTypeAndClientTypeAndUidAndIsDel(appType, clientType, Long.valueOf(uidFromToken), NOT_DEL);
                    if (user == null) {
                        throw new ResultException(UID_NOT_EQ_EX);
                    }
                    //校验接口访问权限
                    Set<Permission> permissions = userService.getPermissionList(user.getUid());

                    stringRedisTemplate.opsForValue().set(key.toString(),JSONObject.toJSONString(user),validTime, TimeUnit.MINUTES);
                }

                if (user == null) {
//            throw new ResultException(USERNAME_NOT_FOUND);
                    throw new ResultException(USER_EMAIL_NOT_EXIST_EX);
                }
//                UserAccount userDetails = userService.loadUserByUsernameAndAppTypeAndClientType(uidFromToken, appType, clientType);


                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null, new HashSet<>());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user:{}", uidFromToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.info("过滤器异常",e);
            // 异常捕获，发送到error controller
            request.setAttribute("filter.error", e);

            request.getRequestDispatcher("/error/throw")
                    .forward(request, response);

        }
    }
}
