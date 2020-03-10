// package com.rolemanager.user.auth;
//
// import com.rolemanager.commons.model.UserModel;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
// import java.util.Collection;
//
// /**
//  * 自定义用户身份信息
//  * 提供核心用户信息。
//  * 出于安全目的，Spring Security不直接使用实现。它们只是存储用户信息，这些信息稍后封装到身份验证对象中。这允许将非安全相关的用户信息(如电子邮件地址、电话号码等)存储在一个方便的位置。
//  * 具体实现必须特别注意，以确保每个方法的非空契约都得到了执行。有关参考实现(您可能希望在代码中对其进行扩展或使用)，请参见User。
//  *
//  * @author maxzhao
//  * @date 2019-05-22
//  */
// public class UserDetailsImpl implements UserDetails {
//     private static final long serialVersionUID = 1L;
//     /**
//      * 用户信息
//      */
//     private UserModel userModel;
//     /**
//      * 用户角色
//      */
//     private Collection<? extends GrantedAuthority> authorities;
//
//     public UserDetailsImpl(UserModel userModel, Collection<? extends GrantedAuthority> authorities) {
//         super();
//         this.userModel = userModel;
//         this.authorities = authorities;
//     }
//
//     /**
//      * 返回用户所有角色的封装，一个Role对应一个GrantedAuthority
//      *
//      * @return 返回授予用户的权限。
//      */
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }
//
//     /**
//      * 返回用于验证用户身份的密码。
//      *
//      * @return Returns the password used to authenticate the user.
//      */
//     @Override
//     public String getPassword() {
//         return userModel.getPwd();
//     }
//
//     /**
//      * @return
//      */
//     @Override
//     public String getUsername() {
//         return userModel.getNickname();
//     }
//
//     /**
//      * 判断账号是否已经过期，默认没有过期
//      *
//      * @return true 没有过期
//      */
//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }
//
//     /**
//      * 判断账号是否被锁定，默认没有锁定
//      *
//      * @return true 没有锁定  false 锁定
//      */
//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }
//
//     /**
//      * todo 判断信用凭证是否过期，默认没有过期
//      *
//      * @return
//      */
//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }
//
//     /**
//      * 判断账号是否可用，默认可用
//      *
//      * @return
//      */
//     @Override
//     public boolean isEnabled() {
//         return userModel.getStatus() == 0;
//     }
// }
