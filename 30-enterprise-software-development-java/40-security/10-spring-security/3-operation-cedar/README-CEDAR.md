# Operation Cedar (starting point: `3-operation-cedar`)
In the previous story we implemented 'Role based'-security. There is another way to approach this: feature based security.

Featu... _"SIR!, SIR!!"_
- "Yes? What is it private?"
    - _"We are under attack, the enemy is attacking our in memory authentication mechanism for storing users/passwords"_
- "God damn... It looks like we will have to postpone the feature based security! Quickly, private, to the in memory storage!"

Okay, so it looks like we're going to do something else first instead of working on feature-based authorization. 
Let's swap out our in-memory approach for storing users in favor of a solution that's a bit better, closer to reality. 
- Out goes the `inMemoryAuthentication` and in comes your very own `AuthenticationProvider`!

## Authentication Provider

> The following step-by-step guide is based on https://www.baeldung.com/spring-security-authentication-provider

Spring Security provides a variety of options for performing authentication. These follow a simple contract: 
**an Authentication request is processed by an AuthenticationProvider and a fully authenticated object with full credentials is returned**.

You will have to get rid of the inMemoryAuthentication in your Spring `SecurityConfig` class and replace it by a custom `AuthenticationProvider`.
This custom authentication provider (let's name it `ArmyAuthenticationProvider`) is a class that should implement `AuthenticationProvider`. 
You will have to override two methods: 'authenticate' and 'supports'.
- Method **authenticate** (on success) must return a fully authenticated object (`Authentication`) with full credentials.
- Method **supports** is a rather technical method which we will help you with to implement...

### Implementing the authenticate() method
In the package `...security.external.authentication` you will find the class `FakeAuthenticationService`. 
It can be used to access the known users (based of username and password).
It is fully ready to be used by your `ArmyAuthenticationProvider` class:
- You can call `getUser` by providing a password and username. 
- If the combination is correct, this service will return a `ExternalAuthentication` object that contains the username, 
password and roles of the user.
- If the combination is incorrect, it will return an empty optional.

The `authenticate()` method in the `ArmyAuthenticationProvider` class should:
- Return an `Authentication` object if the username/password combo exists (a fully authenticated object with full credentials).
    - It should use the `FakeAuthenticationService` to check if a given username and password is known (if we have a user for those credentials).
    - It returns an object of `Authentication` (`Authentication` is an interface of Spring Security). 
- Otherwise it should throw an exception that extends the `AuthenticationException` class.
    - Pick one you think fits the bill.
- **HINT**: This method is a great candidate for some unit-tests.

### Implementing the supports() method
This method must return true if this `AuthenticationProvider` (`ArmyAuthenticationProvider`) supports the indicated `Authentication` object. 
In our case, the provider handles Username/Password Authentication tokens. 
- `UsernamePasswordAuthenticationToken` is an already existing implementation of `Authentication`.
You don't have to know the specifics, implement it as follows:
```
@Override
public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
}
```

## Security config

We now have to remove the configuration for our in Memory Authentication, in favor of our new Authentication Provider.
1. Go to `SecurityConfig`, in the `configureGlobal` method, remove the `inMemoryAuthentication` configuration in favor of:
```
    private final AuthenticationEntryPoint authEntryPoint; // already existed
    private final AuthenticationProvider armyAuthenticationProvider;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authEntryPoint, AuthenticationProvider armyAuthenticationProvider) {
        this.authEntryPoint = authEntryPoint; // already existed
        this.armyAuthenticationProvider = armyAuthenticationProvider;
    }

    ...

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(armyAuthenticationProvider);
    }
```

Note that no extra end-to-end tests have been provided for this story. 
That's because the existing behaviour is not altered in any way. So after you're done, all the other tests should still run.

## Oh right, one more thing:

When using a custom `AuthenticationProvider`, Spring pretty much requires you to be using feature-based security instead of role-based security. 
To not make the story even bigger than it already is, we're gonna cheat a bit on that part (don't worry, we'll implement it properly in the next story).
- To fix everything up, change your `hasRole`- and `hasAnyRole`-code in the `ArmyResource` (and/or SecurityConfig) to `hasAuthority` and `hasAnyAuthority`.
- If everything is implemented correctly, all tests should run successfully.

## Solution
Inspect the `solutions` branch for a solution.
