package cj.software.hierarchy.movie.spring;

import org.apache.logging.log4j.spi.StandardLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * controls the log level for the automatic tracing of the {@link TraceAspect}. If a class for example has this setting:
 * <pre>
 *     {@code
 *     @TraceAtLogLevel(level = StandardLevel.DEBUG)
 *     public class MyClass {
 *         public void myMethod() {
 *
 *         }
 *     }}
 * </pre>
 * then the method entry and exit will only be traced if the logger for that class has at least debug level.
 * <p>
 * Watch out when using this annotation. If your method issues its own log entry, this one will be indented, but
 * it will embedded of the method description of the invoker of your method like this:
 * <pre>
 *         {@code
 *         >OuterOuterClass.outeroutermethod()
 *             >OuterClass.outermethod()
 *                log entry from outermethod
 *                    log entry from you method
 *                another log entry fro outermethod
 *             <OuterClass.outermethod()
 *          <OuterOuterClass.outeroutermethod()
 *         }
 *     </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface TraceAtLogLevel {
    StandardLevel level();
}
