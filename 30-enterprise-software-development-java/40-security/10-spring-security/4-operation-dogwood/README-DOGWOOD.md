# Operation Dogwood (starting point: `4-operation-dogwood`)

Back to feature (permission)-based security!

## What is so bad about checking roles for access (authorization)?

> What is so "bad" about checking Roles for access control? What benefits are gained by checking for features/permissions instead?
> _https://softwareengineering.stackexchange.com/questions/299729/role-vs-permission-based-access-control_

At the moment of checking, the calling code only needs to know "does user X have permission to perform action Y?".
The calling code does not care about and should not be aware of relationships between roles and permissions.
```
// Role based
if(user.hasRole("ADMIN") || user.hasRole("SECRETARY")) { viewReport(); }

// Feature / Permission based
if(user.hasFeature("VIEW_REPORT")) { viewReport(); }
```

The authorization layer will then check if the user has this permission, typically by checking if the user's role has this permission (thus, a role should have a collection of permissions/features). This allows you to change authorization logic without updating the calling code.

If you directly check for role at the call site, you are implicitly forming role ⇄ permission relationships and injecting authorization logic into the calling code, violating separation of concerns.

E.g., In a purely role-based approach, should you later decide that role SECRETARY should not have the permission **to view reports**, you would have to change every code related to viewing reports which checks if the user is a SECRETARY.

```
// Role based (HAD TO BE CHANGED)
if(user.hasRole("ADMIN")) { viewReport(); }

// Feature / Permission based (no change required)
if(user.hasFeature("VIEW_REPORT")) { viewReport(); }
```

## Roles to features
You've already completed one step along the way: using the `hasAuthority` method/annotation on your REST endpoint methods.
Next, we'll have to create the different features we would like to have and couple them to the role(s) that are allowed to have these features.
- Validate that you are using `hasAuthority` instead of `hasRole`.

Remember how an `Authentication` object represents a fully authenticated user with full credentials? Such an object was
returned by our `authenticate()` method of the `ArmyAuthenticationProvider` class. We used the built-in `UsernamePasswordAuthenticationToken` object (which implements `Authentication`) of which
we created a new object in this `authenticate()` method.

Currently, in the body of the `authenticate()` method, when we create a new `UsernamePasswordAuthenticationToken` object, we convert the roles (of `ExternalAuthentication`) to `SimpleGrantedAuthority` objects:
```
return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), rolesToGrantedAuthorities(user.getRoles()));
```

You will have to change this so that (firstly) all roles of a user are converted to features and then (secondly) all features are converted into `SimpleGrantedAuthority` objects.
- Roles conceptually represent a collection of features.

You will have to create a mapping between the following features and roles:
- Feature/permission **get_army_info**: role private & role general
- Feature/permission **join_army**: role civilian
- Feature/permission **promote_private**: role human_relationships
- Feature/permission **discharge_soldier**: role human_relationships
- Feature/permission **launch_nukes**: role.general

The `FakeAuthenticationService` will provide you with the role(s) of the logged in users. You'll have to map those roles to the allowed features manually upon `authentication()`

## Resource

In your `ArmyResource` controller, change the values of the `hasAuthority` to the values of the correct features.

Once again, there are no new end to end tests. The old tests should keep working after you're done!
- Validate tis.

Extra info:
- [Another article on role based vs feature based (called 'activity based' here)](https://lostechies.com/derickbailey/2011/05/24/dont-do-role-based-authorization-checks-do-activity-based-checks/)

## Solution
Inspect the `solutions` branch for a solution.
