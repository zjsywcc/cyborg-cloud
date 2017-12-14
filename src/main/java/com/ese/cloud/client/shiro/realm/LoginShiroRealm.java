package com.ese.cloud.client.shiro.realm;

import com.ese.cloud.client.entity.RoleInfo;
import com.ese.cloud.client.service.RoleInfoService;
import com.ese.cloud.client.util.SpringContextUtil;
import com.ese.cloud.client.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import com.ese.cloud.client.service.UserInfoService;

/**
 * 登录认证
 * Created by rencong on 16/9/10.
 */

public class LoginShiroRealm extends AuthorizingRealm{




    /**
     * 权限验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) throws AuthenticationException{

         return null;

    }


    /**
     * 身份认证
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        UserInfoService userInfoService =  (UserInfoService) SpringContextUtil.getBean("userInfoService");

        RoleInfoService roleInfoService = (RoleInfoService) SpringContextUtil.getBean("roleInfoService");




        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println(token.getPrincipal());

        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.getUserInfoByUsername(username);

        RoleInfo roleInfo = roleInfoService.findById(userInfo.getRoleId());

        //获取当前的subject
        Subject currentUser = SecurityUtils.getSubject();
        //设定权限组id
        currentUser.getSession().setAttribute("role_id",userInfo.getRoleId());
        //设定userid都权限组中
        currentUser.getSession().setAttribute("user_id",userInfo.getId());
        //存储权限信息
        currentUser.getSession().setAttribute("role_level",roleInfo.getLevel());


        //System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            return null;
        }

       /*
        * 获取权限信息:这里没有进行实现，
        * 请自行根据UserInfo,Role,Permission进行实现；
        * 获取之后可以在前端for循环显示所有链接;
        */
        //userInfo.setPermissions(userService.findPermissions(user));


        //账号判断;

        //加密方式;
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                //ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
                getName()  //realm name
        );

        //明文: 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
//      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//           userInfo, //用户名
//           userInfo.getPassword(), //密码
//             getName()  //realm name
//      );

        return authenticationInfo;
    }
}
