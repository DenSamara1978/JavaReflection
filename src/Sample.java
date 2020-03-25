public class Sample {

    @BeforeSuite
    public static void before ()
    {
        System.out.println( "Before" );
    }

    @Test ( priority = 2 )
    public static void test1 ()
    {
        System.out.println( "Test2" );
    }

    @Test ( priority = 9 )
    public static void test2 ()
    {
        System.out.println( "Test9" );
    }

    @Test ( priority = 19 )
    public static void test12 ()
    {
        System.out.println( "Test19" );
    }

    @AfterSuite
    public static void after ()
    {
        System.out.println( "After" );
    }

}
