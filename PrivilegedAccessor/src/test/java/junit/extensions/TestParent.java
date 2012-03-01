package junit.extensions;

/**
 * Test class with private methods to invoke via PrivilegedAccessor
 */
public class TestParent {
   private String           privateName;
   private Object           privateObject;
   private static int       privateStaticInt;
   private final int        privateFinalInt;
   private final String     privateFinalString;
   private static final int privateStaticFinalInt;
   private static final String privateStaticFinalString;
   
   static {
      privateStaticFinalInt = 3;
      privateStaticFinalString = "Tester";
   }

   public TestParent(String name) {
      this(name, "Brown");
   }

   private TestParent(String name, Object object) {
      this.privateName = name;
      this.privateObject = object;
      privateStaticInt = 1;
      privateFinalInt = 2;
      privateFinalString = "Tom";
   }

   @SuppressWarnings("unused")
   private TestParent() {
      this("Charlie", "Brown");
   }

   private String getName() {
      return this.privateName;
   }

   protected void setName(String newName) {
      this.privateName = newName;
   }

   /** overloading setName(String) **/
   @SuppressWarnings("unused")
   private void setName() {
      this.privateName = "Chaplin";
   }

   private Object getObject() {
      return this.privateObject;
   }

   @SuppressWarnings("unused")
   private void setObject(Object newObject) {
      this.privateObject = newObject;
   }

   @SuppressWarnings("unused")
   private static void setStaticInt(int number) {
      TestParent.privateStaticInt = number;
   }

   private static int getStaticInt() {
      return TestParent.privateStaticInt;
   }
   
   private int getFinalInt() {
      return privateFinalInt;
   }
   
   private String getFinalString() {
      return privateFinalString;
   }
   
   private static int getStaticFinalInt() {
      return TestParent.privateStaticFinalInt;
   }
   
   private static String getStaticFinalString() {
      return TestParent.privateStaticFinalString;
   }

   public boolean equals(Object other) {
      if (!(other instanceof TestParent)) {
         return false;
      }

      TestParent otherTestParent = (TestParent) other;

      if (this.privateName.equals(otherTestParent.privateName) && this.privateObject.equals(otherTestParent.privateObject)) {
         return true;
      }

      return false;
   }

   public String toString() {
      return this.getClass().getName() + " {privateName=" + getName() + ", privateObject=" + getObject() + ", privateStaticInt="
         + TestParent.getStaticInt() + ", privateFinalInt=" + getFinalInt() + ", privateFinalString=" + getFinalString() + ", privateStaticFinalInt=" + TestParent.getStaticFinalInt() + ", privateStaticFinalString=" + TestParent.getStaticFinalString() + "}";
   }
}