import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JTest {
    public static void main(String[] args) {
        try {
            start ( Sample.class );
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean start ( String className ) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return start ( Class.forName( className ));
    }

    public static boolean start ( Class cl ) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = cl.getMethods();
        Method beforeMethod = null;
        Method afterMethod = null;
        for ( Method method: methods )
        {
            if ( method.isAnnotationPresent ( BeforeSuite.class ))
            {
                if (beforeMethod != null)
                    throw new RuntimeException("Более одного метода, аннотированного @BeforeSuite");
                else
                    beforeMethod = method;
            }
            if ( method.isAnnotationPresent ( AfterSuite.class ))
            {
                if (afterMethod != null)
                    throw new RuntimeException("Более одного метода, аннотированного @AfterSuite");
                else
                    afterMethod = method;
            }
        }

        if ( beforeMethod != null )
            beforeMethod.invoke ( null );

        for ( int priority = 1; priority <= 10; ++priority ) {
            for (Method method : methods) {
                if ( method.isAnnotationPresent ( Test.class )) {
                    int prio = method.getAnnotation( Test.class ).priority ();
                    if (( prio < 1 ) || ( prio > 10 ))
                        throw new RuntimeException( "Неверный приоритет в аннотации @Test" );
                    if ( prio == priority )
                        method.invoke ( null );
                }
            }
        }

        if ( afterMethod != null )
            afterMethod.invoke ( null );

        return false;
    }
}
