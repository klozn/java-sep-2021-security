# Operation Baobab (starting point: `2-operation-baobab`)

Now that we've got basic authentication covered, let's move on to the authorization. 

## Roles
Our app has four roles:
- CIVILIAN, everyone who is a CIVILIAN can join the army
- PRIVATE, everyone who is a PRIVATE can be deployed army info
- HUMAN_RELATIONSHIPS, everyone who is (has role) HUMAN_RELATIONSHIPS can promote privates and discharge soldiers.
- GENERAL, everyone who is a GENERAL can do everything the PRIVATE can. But, small detail, they can also launch the nukes.

On top of the existing - working - authentication code, implement authorization checks that block **authenticated users** 
from accessing certain endpoints (e.g. launching the nukes) if they don't have the necessary roles.

In the end, all of your tests should be green (most will still fail now)!

## Let's get ready to rumble

> The following step-by-step guide is based on https://www.baeldung.com/spring-security-expressions

### Method Level Authorization
Security Expressions can be used to secure business functionality on the method level, by using annotations.

For this, we can use the annotation `@PreAuthorize` (and `@PostAuthorize`).
- Both support Spring Expression Language (SpEL) and provide expression-based access control.
    - For later reference (if you have some spare time): https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions
    
**First**, in order to use method level security, we need to enable this in the security configuration using `@EnableGlobalMethodSecurity`,
do this in your `SecurityConfig` class.

**Then**, we can secure the methods in our controller using the Spring `@PreAuthorize` annotation:

```
@PostMapping
@PreAuthorize("hasRole('ROLE_CIVILIAN')")
public void joinArmy() {
    //TODO
}
```  
- Spring Security automatically prefixes any role with `ROLE_`.
- The `hasRole` expression is used here to check if the currently authenticated principal has the specified authority.
- The `hasAnyRole` allows for more than one role.

Make sure that all tests become green after implementing the requirements.

### Manual call
Try starting the application and call the rest endpoint using Advanced Rest Client or Postman. 
Experiment with correct and incorrect authorization data and check if the app returns what you'd expect.
    
### Extra assignment
- The article offers multiple ways to fix this problem (we implemented the second one). Try implementing both and see if they both do the job.
    - Don't get confused with the XML configuration, it's for legacy code bases, we use java config.
- Users can have multiple roles. Create a user who is both a PRIVATE and a HUMAN_RELATIONSHIPS and check if he can do everything both roles can.

## Solution
Inspect the `solutions` branch for a solution.
