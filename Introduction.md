**PrivilegedAccessor** is a simple to use Java framework for accessing `private` methods, attributes, and constructors via reflection.

# Introduction #
In unit-testing you often need to prepare and later inspect the unit-under-test before you can test it. For instance you might need to inject mock-objects in your unit, so your unit-test does not access other units like backend systems. Or you might need to access a private attribute to assert a post-condition of a unit after the test.

This could be easily done if Java had no modifiers like `private`. Accessing private (or `protected` or package private) methods, attributes, and constructors from outside is forbidden. And you should _never change the modifiers of your production code just for unit-testing purposes_!!!

Fortunately we have reflection to access everything in our classes. Unfortunately reflection is somehow complicated:

**Example:**
Accessing the private (`float`) field `salary` of the object `boss` of class `Manager` via reflection:
```java

try {
Field field = Manager.class.getDeclaredField(salary);
field.setAccessible(true);
float howMuchMyBossEarns = (float) field.get(boss);
} catch (NoSuchFieldException e) {
assert false: "class 'Manager' has no field 'salary'";
} catch (IllegalAccessException e) {
assert false : "could not access field 'salary'";
return null;
}```

With PrivilegedAccessor the same task is much less work:
```java

float howMuchMyBossEarns = PA.getValue(boss, "salary");
```

## Download ##
You can download PrivilegedAccessor via [Downloads](https://code.google.com/p/privilegedaccessor/downloads/list) or from maven central using:
```
<dependency>
  <groupId>com.e-movimento.tinytools</groupId>
  <artifactId>privilegedaccessor</artifactId>
  <version>1.2.2</version>
</dependency>
```

## How does it work ##
PrivilegedAccessor is based on Java reflection and the possibility to change access-rights via "setAccessible(true)".
PrivilegedAccessor tries to find the method, attribute, constructor you want to access, sets its accessibility to true and accesses it.

### Usage ###
The usage of PrivilegedAccessor is rather simple. Use the following to access your otherwise unaccessible code:

  * `PA.getValue(Object objectOrClass, String attribute-name)` - to read a private attribute
  * `PA.setValue(Object objectOrClass, String attribute-name, Object new-value)` - to change a private attribute
  * `PA.instantiate(Class clazz)` - to instantiate a class via its private constructor
  * `PA.instantiate(Class clazz, Object... parameters)` - to instantiate a class via a private constructor with parameters
  * `PA.invokeMethod(Object objectOrClass, String methodSignature, Object... args)` - to invoke a private method
  * `PA.getFieldNames(Object objectOrClass)` - to get all fields of an object or class (including fields of super-classes)
  * `PA.getMethodSignatures(Object objectOrClass)` - to get all method signatures of an object or class (including super-classes)

PA (the convenience short form for PrivilegedAccessor) works with varargs and autoboxing and only throws RuntimeExceptions.

#### Examples ####
```java

PA.getValue(myObject, "myAttribute");
PA.setValue(myObject, "myAttribute", 42);
PA.setValue(myObject, "myAttribute", 42).setValue("myOtherAttribute", newValue");
PA.instantiate(MyClass.class);
PA.instantiate(MyClass.class, "Hello", "World", 42);
PA.invokeMethod(myObject, "addInt(int)", 42);
PA.invokeMethod(myObject, "addInteger(java.lang.Integer)", 42);
PA.invokeMethod(myObject, "addIntegers(java.lang.Integer...)", 42, 43, 44);
PA.invokeMethod(myObject, "addIntegerArray(java.lang.Integer[])", new Integer[] {42, 43, 44});```
You can access methods with all sorts of arguments (primitives, objects, varargs, arrays), just be sure to qualify the arguments correctly.

### Caveats ###
  * be sure to always use the full method signature for invokeMethod - i.e. including fully qualified parameter classes (e.g. `PA.invokeMethod(myObject, "setName(java.lang.String)", "Tom");)`
  * you can access private inner classes - but you need to add the surrounding object as first argument - e.g. `PA.instantiate(Class.forName("MyClass$MyInnerClass"), myObject);` (this is not necessary for private static nested classes)
  * you can access final fields, but not change final static fields

### Compatibility ###
  * PrivilegedAccessor V 1.1 is downward compatible to Java 1.3 (tested), but should work as well with newer releases (tested up to Java 1.7)
  * PrivilegedAccessor V 1.2 and onwards is downward compatible to Java 1.5 (tested), but should work as well with newer releases (tested up to Java 1.7)

PrivilegedAccessor uses a lot of unit tests to check its functionality - if you find a bug or missing feature, please feel free to contact us.

### Advise ###
**Do not use PrivilegedAccessor in production code** - since it undermines the information hiding principle.
Use it ONLY for testing or test setup/teardown purposes - e.g. for assigning mock objects, or asserting post-conditions.

| &lt;wiki:gadget url="http://www.ohloh.net/p/PrivilegedAccessor/widgets/project\_cocomo.xml" width="350" height="195" border="0" /&gt; | &lt;wiki:gadget url="http://www.ohloh.net/p/PrivilegedAccessor/widgets/project\_factoids.xml" width="350" height="155" border="0" /&gt; | [![](http://www.cloudbees.com/sites/default/files/Button-Powered-by-CB.png)](https://privileged-accessor.ci.cloudbees.com/) |
|:--------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------|