# 目的

- 支持用户通过用户名和密码登录
- 登录后通过http header返回token，每次请求，客户端需要通过header将token带回，用于鉴权
- 服务端负责token的定时刷新

# 步骤

## 项目搭建

需要在项目中引入相关的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.10.0</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.62</version>
</dependency>
```

## 登录认证流程

### Filter

对于用户登录行为，security通过定义一个Filter来拦截/login来实现的。spring security默认支持form方式登录，所以对于使用json发送登录信息的情况，我们自己定义一个Filter，这个Filter直接从`AbstractAuthenticationProcessingFilter`继承，只需要实现两部分，一个是RequestMatcher，指名拦截的Request类型；另外就是从json body中提取出username和password提交给AuthenticationManager。

### 实现UserDetailsService接口

用于根据用户名获取用户的密码以及权限信息。以便于下一步做用户信息校验。

### 使用BCryptPasswordEncoder

```java
@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```



# Spring Security认证流程

## 原理图

![img](picture/2)

## 认证步骤

在前台输入完用户名和密码之后，UsernamePasswordAuthenticationFilter类中去获取用户名和密码，然后去构建一个UsernamePasswordAuthenticationToken对象

```java
public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// 将请求的信息设到UsernamePasswordAuthenticationToken中去，包括ip、session等内容
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
```

这个对象实现了Authentication接口，Authentication接口封装了验证信息，在调用UsernamePasswordAuthenticationToken的构造函数的时候先调用父类AbstractAuthenticationToken的构造方法，传递一个null，因为在认证的时候并不知道这个用户有什么权限。之后去给用户名密码赋值，最后有一个setAuthenticated（false）方法，代表存进去的信息是否经过了身份认证，

```java
public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}
```

实例化UsernamePasswordAuthenticationToken之后调用了setDetails(request,authRequest)将请求的信息设到UsernamePasswordAuthenticationToken中去，包括ip、session等内容

然后去调用AuthenticationManager,AuthenticationManager本身不包含验证的逻辑，它的作用是用来管理AuthenticationProvider。authenticate这个方法是在ProviderManager类上的，这个类实现了AuthenticationManager接口，在authenticate方法中有一个for循环，去拿到所有的AuthenticationProvider，真正校验的逻辑是写在AuthenticationProvider中的，为什么是一个集合去进行循环？是因为不同的登陆方式认证逻辑是不一样的，可能是微信等社交平台登陆，也可能是用户名密码登陆。AuthenticationManager其实是将AuthenticationProvider收集起来，然后登陆的时候挨个去AuthenticationProvider中问你这种验证逻辑支不支持此次登陆的方式，根据传进来的Authentication类型会挑出一个适合的provider来进行校验处理。

```java
public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Class<? extends Authentication> toTest = authentication.getClass();
		AuthenticationException lastException = null;
		AuthenticationException parentException = null;
		Authentication result = null;
		Authentication parentResult = null;
		boolean debug = logger.isDebugEnabled();

		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}

			if (debug) {
				logger.debug("Authentication attempt using "
						+ provider.getClass().getName());
			}

			try {
				result = provider.authenticate(authentication);

				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			}
			catch (AccountStatusException e) {
				prepareException(e, authentication);
				// SEC-546: Avoid polling additional providers if auth failure is due to
				// invalid account status
				throw e;
			}
			catch (InternalAuthenticationServiceException e) {
				prepareException(e, authentication);
				throw e;
			}
			catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result == null && parent != null) {
			// Allow the parent to try.
			try {
				result = parentResult = parent.authenticate(authentication);
			}
			catch (ProviderNotFoundException e) {
				// ignore as we will throw below if no other exception occurred prior to
				// calling parent and the parent
				// may throw ProviderNotFound even though a provider in the child already
				// handled the request
			}
			catch (AuthenticationException e) {
				lastException = parentException = e;
			}
		}

		if (result != null) {
			if (eraseCredentialsAfterAuthentication
					&& (result instanceof CredentialsContainer)) {
				// Authentication is complete. Remove credentials and other secret data
				// from authentication
				((CredentialsContainer) result).eraseCredentials();
			}

			// If the parent AuthenticationManager was attempted and successful than it will publish an AuthenticationSuccessEvent
			// This check prevents a duplicate AuthenticationSuccessEvent if the parent AuthenticationManager already published it
			if (parentResult == null) {
				eventPublisher.publishAuthenticationSuccess(result);
			}
			return result;
		}

		// Parent was null, or didn't authenticate (or throw an exception).

		if (lastException == null) {
			lastException = new ProviderNotFoundException(messages.getMessage(
					"ProviderManager.providerNotFound",
					new Object[] { toTest.getName() },
					"No AuthenticationProvider found for {0}"));
		}

		// If the parent AuthenticationManager was attempted and failed than it will publish an AbstractAuthenticationFailureEvent
		// This check prevents a duplicate AbstractAuthenticationFailureEvent if the parent AuthenticationManager already published it
		if (parentException == null) {
			prepareException(lastException, authentication);
		}

		throw lastException;
	}
```

然后去调用provider的验证方法authenticate方法，authenticate是DaoAuthenticationProvider类中的一个方法，DaoAuthenticationProvider继承了AbstractUserDetailsAuthenticationProvider。实际上authenticate的校验逻辑写在了AbstractUserDetailsAuthenticationProvider抽象类中，首先实例化UserDetails对象，调用了retrieveUser方法获取到了一个user对象，retrieveUser是一个抽象方法。

```java
public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				() -> messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.userCache.getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;

			try {
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
				else {
					throw notFound;
				}
			}

			Assert.notNull(user,
					"retrieveUser returned null - a violation of the interface contract");
		}

		try {
			preAuthenticationChecks.check(user);
			additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
				preAuthenticationChecks.check(user);
				additionalAuthenticationChecks(user,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			else {
				throw exception;
			}
		}

		postAuthenticationChecks.check(user);

		if (!cacheWasUsed) {
			this.userCache.putUserInCache(user);
		}

		Object principalToReturn = user;

		if (forcePrincipalAsString) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}
```

DaoAuthenticationProvider实现了retrieveUser方法，在实现的方法中实例化了UserDetails对象

```java
protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		prepareTimingAttackProtection();
		try {
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}
```

也就是相当于自定义验证逻辑的那个类，去实现UserDetailService类，这个返回结果就是我们自己在数据库中根据username查询出来的用户信息。在AbstractUserDetailsAuthenticationProvider中如果没拿到信息就会抛出异常，如果查到了就会去调用preAuthenticationChecks的check方法去进行预检查。

在预检查中进行了三个检查，因为UserDetail类中有四个布尔类型，去检查其中的三个，用户是否锁定、用户是否过期，用户是否可用。

预检查之后紧接着去调用了additionalAuthenticationChecks方法去进行附加检查，这个方法也是一个抽象方法，在DaoAuthenticationProvider中去具体实现，在里面进行了加密解密去校验当前的密码是否匹配。

如果通过了预检查和附加检查，还会进行厚检查，检查4个布尔中的最后一个。所有的检查都通过，则认为用户认证是成功的。用户认证成功之后，会将这些认证信息和user传递进去，调用createSuccessAuthentication方法.

在这个方法中同样会实例化一个user，但是这个方法不会调用之前传两个参数的函数，而是会调用三个参数的构造函数。这个时候，在调super的构造函数中不会再传null，会将authorities权限设进去，之后将用户密码设进去，最后setAuthenticated(true),代表验证已经通过。

```java
public UsernamePasswordAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
```

在验证成功之后，其中会调用AbstractAuthenticationFilter中的successfulAuthentication方法，在这个方法最后会调用我们自定义的successHandle登陆成功r处理器，在调用这个方法之前会调用SecurityContextHolder.getContext()的setAuthentication方法，会将我们验证成功的那个Authentication放到SecurityContext中，然后再放到SecurityContextHolder中。SecurityContextImpl中只是重写了hashcode方法和equals方法去保证Authentication的唯一。

SecurityContextHolder是ThreadLocal的一个封装，**ThreadLocal是线程绑定的一个map，在同一个线程里在这个方法里往ThreadLocal里设置的变量是可以在另一个线程中读取到的**。它是一个线程级的全局变量，在一个线程中操作ThreadLocal中的数据会影响另一个线程。也就是说创建成功之后，塞进去，此次登陆所有的请求都会通过SecurityContextPersisenceFilter去SecurityContextHolder拿那个Authentication。SecurityContextHolder在整个过滤器的最前面。

![img](picture/Spring in Action.md)

# Spring Cloud Security OAuth2

OAuth2.0的服务提供方涵盖两个服务，即授权服务和资源服务，使用Spring Cloud Security OAuth2的时候你可以选择把他们放在同一个应用程序中实现，也可以选择建立使用同一个授权服务的多个资源服务。

**授权服务**（Authorization Server）：应包含对接入端以及登入用户的合法性进行验证并颁发token等功能，对令牌的请求端点由Spring MVC控制器进行实现，下面是配置一个认证服务必须要实现的endpoints：

- AuthorizationEndpoint：服务于认证请求，默认URL：`/oauth/authorize`
- TokenEndpoint：服务于访问令牌的请求。默认URL：`oauth/token`

**资源服务**（Resource Server）：应包含对资源的保护功能，对非法请求进行拦截，对请求中token进行解析鉴权等，下面的过滤器用于实现OAuth2.0资源服务：

- OAuth2AuthenticationProcessingFilter：用来对请求给出的身份令牌解析鉴权

## 创建oauth授权服务和User用户资源服务

认证流程如下：

1. 客户端请求oauth授权服务进行认证
2. 认证通过后由oauth颁发令牌
3. 客户端携带令牌Token请求资源服务

## pom文件

除了spring cloud、spring boot的相关依赖还需要以下的依赖：

父工程依赖

```xml
<dependency>
                <groupId>org.springframework.security</groupId>
                <artifactId>spring-security-jwt</artifactId>
                <version>1.1.0.RELEASE</version>
            </dependency>
<dependency>
                <groupId>org.springframework.security.oauth.boot</groupId>
                <artifactId>spring-security-oauth2-autoconfigure</artifactId>
            </dependency>
```

oauth Model和user Model的依赖（版本已经在父工程中声明，此处不需要版本）：

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-jwt</artifactId>
        </dependency>
```

## oauth配置

添加Server类

```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
}
```

