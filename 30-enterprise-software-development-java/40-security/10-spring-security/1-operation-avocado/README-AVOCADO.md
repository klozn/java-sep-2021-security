# Briefing
## You're in the army now

At ease, private!

So you're the soldier who's programming the Java application that will help us manage the composition of our armies? 
I hope you know what you're doing, because this piece of software will contain a lot of sensitive information once it launches.
Our enemies know this too, so they'll be trying their damnedest to gain access to it illegitimately. 
It's your job to prevent that from happening at all costs. The lives of a lot of good (wo)men depend on it!

Sarge McKnowitall told me the UI is already as secure as - and I quote - "a six foot foxhole". 
The Sarge isn't generous with his compliments, so you should feel honored, son/daughter!
However, your good work on the UI only upped our expectations of your work on the even more crucial backend code.

So get back to work and make your country proud, private! Oorah!

> Signed by General Bradbury Cumberton Louisa III

## Some tactical notes

Next up are the stories you will be developing that will help you learn about securing modern Java applications.
- To build the code for the first time, call `mvn clean install` from your command line, from within a module (e.g. `1-operation-avocado`)

Each story has a 'starting point', a Maven module that has the minimum code setup for you to start the story without too much hassle.
This can come in handy because some stories build on top of each other. 
 
Each story will have some test already implemented (found under `src/test/java/` in package `<story-name>`, in file `<story-name>@Test.java`).
    - E.g. for story Avocado, you'll find test class `AvocadoTest.java` in package `avocado`.
We do expect you to write some additional (Unit) tests where you see fit.

Also, a REST-client such as [Postman](https://www.getpostman.com/) or [ARC](https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo) 
might come in handy for some manual testing.

Also, Spring Security is extensive. We are only covering the basics in order to secure our application.
Don't forget about the online reference if you want to know more:
- https://docs.spring.io/spring-security/site/docs/5.2.1.BUILD-SNAPSHOT/reference/html5
- https://docs.spring.io/spring-security/site/docs/5.2.1.BUILD-SNAPSHOT/reference/html5/#servlet-applications

# Operation Avocado (starting point: `1-operation-avocado`)

You're given a very basic Spring Boot application with a couple of REST-calls (see ArmyResource).
This app has an imaginary UI, but it is not relevant for these exercises. 
What you will be doing is securing the REST-calls in a number of ways using Spring's security features. 

Your first task is to make sure not just anyone can call the REST-API. 
We only want people using a Basic authentication header AND whose username and password combination are known to the system to be able to do REST-calls.

## Let's get ready to rumble

> The following step-by-step guide is based on https://www.devglan.com/spring-security/spring-boot-security-rest-basic-authentication

### What is Basic Authentication
Basic authentication is a standard HTTP header with the user and password encoded in base64 : `Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`.
The userName and password is encoded in the format username:password. This is one of the simplest technique to protect 
the REST resources because it does not require cookies. session identifiers or any login pages.

In case of basic authentication, the username and password is only encoded with Base64, but not encrypted or hashed in 
any way. Hence, it can be compromised by any man in the middle. Hence, it is always recommended to authenticate REST API 
calls by this header over a SSL connection.

### BasicAuthenticationFilter in Spring
The `BasicAuthenticationFilter` in Spring Security is the class which is responsible for processing basic authentication credentials 
presented in HTTP Headers and putting the result into the `SecurityContextHolder`. We will be using it soon... 

### Maven setup
1. We are going to need different dependencies on Spring, the easiest way is to include the Spring Boot Web Starter as a dependency. 
It will include different other dependencies for creating and running a REST(ful) Web API.
    - We already included this dependency in the parent pom. 
2. Secondly, you will have to add the dependency for Spring Security: it is under Dependency Management in the parent pom.
    - You will however have to add it in the dependencies section of this module's pom.xml. Do that now.
    
### Inspect the existing code:
- `Application`: The java class containing the `main` method, our `@SpringBootApplication` application class, used for running our REST(ful) Web API Application.
- `ArmyInfoDto`:  A DTO object, used for sending data 'over the wire'. DTO's are nothing more than 'dumb' data holders... Objects of this class will be marshalled to JSON, or visa-versa (unmarshalling), by the Jackson library.
- `ArmyResource`: Our only controller. It contains end points, some of which you have to implement. For your information: `Resource` is a different name for `Controller`.
    - It will be component scanned by Spring.
- `JacksonConfig`: It is a `@Configuration` class, and thus a Spring Component as well. It is used to create an `ObjectMapper` bean which is used by - and part of - the Jackson library for mapping our 
Java classes (in our cases, our *Dto.java classes) to JSON and visa-versa.
    - It will be component scanned by Spring.

### Spring Security Configuration
Now let us define our main configuration for spring security.
Create a `security/SecurityConfig` class that you annotated with `@EnableWebSecurity` to enable Spring Security web security support.
- Also annotate it with `@configuration` as it will include certain configuration **and** should be auto-component-scanned by Spring.
- Also extend it with the `WebSecurityConfigurerAdapter` class as we want to override specific Spring Security features with our custom requirements. 

```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO
    }
```

In method `configure` we want every request to be authenticated using HTTP Basic authentication. 
If authentication fails, the configured `AuthenticationEntryPoint` will be used to retry the authentication process:

```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }
}
```

Lastly, add the `configureGlobal(AuthenticationManagerBuilder)` method (which will only correctly work when annotated 
with `@AutoWired` and part of a class annotated with `@EnabledWebSecurity` (or related)).
In it, we are going to create an in memory 'database' that will contain the username/password combinations which we can successfully authenticate.
- Make sure that you provide the correct username and password to fix your failing tests.
- The `inMemoryAuthentication` implementation is very limited, but for now, it works just fine.
    -  This is not an advisable solution for real life applications, because there is no way for you to easily change/add/remove your users when the app is running.
    We will be switching to an LDAP in later exercises.

```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("dummy username").password("{noop}dummy password").roles("HUMAN");
    }
}
```
We will not discuss the roles yet, but we have to provide at least one role, so we make-up a very generic one, "Human".  

### Authentication Entry Point
Now let us define our authentication entry point, we will name it `ArmyAuthenticationEntryPoint`. 
This class will be responsible to send an immediate response (to a request) back when the credentials are not authorized. 
If the authentication event was successful, or authentication was not attempted because the HTTP header did not contain 
a supported authentication request, the filter chain will continue as normal. 
- The only time the normal filter chain will be interrupted is if authentication fails and - as a result - the `ArmyAuthenticationEntryPoint` is called:

```
@Component
public class ArmyAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public static final String NAME_OF_REALM = "all-authenticated-users";

    public ArmyAuthenticationEntryPoint() {
        setRealmName(NAME_OF_REALM);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
```

Extra information:
- What is a Realm?: https://javaee.github.io/tutorial/security-intro005.html > section `What Is a Realm?`
- What is the WWW-Authenticate header?: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate

### Fix your tests
Run the tests! Make sure that they work now. 
You still need to change something to make them work.

### Manual call
Try starting the application and call the rest endpoint using Advanced Rest Client or Postman. 
Experiment with correct and incorrect authorization data and check if the app returns what you'd expect.
1. HTTP GET `http://localhost:9123/armies/belgium` without an Authorization header
    - Should be 401 Unauthorized (+ the WWW-Authenticate response header)
2. HTTP GET `http://localhost:9123/armies/belgium` with an Authorization header
    - Pair `JMILLER:THANKS` Base64 encoded is Sk1JTExFUjpUSEFOS1M=
        - Encode yourself using https://www.base64encode.org (UTF-8, CRLF)
    - Should be 200 OK with a JSON payload (the `ArmyInfoDto` as JSON)
3. Finally, perform a HTTP GET `http://localhost:9123/armies/belgium` with an Authorization header 
but with an invalid username:password pair.

### Extra assignment
- `AvocadoTest` currently only tests the `getDeployedArmyInfo` REST-call. Write similar tests for the other 3 REST-calls
- Implement the methods in your `ArmyResource` that have `//TODO` in their body.

## Solution
Inspect the `solutions` branch for a solution.
