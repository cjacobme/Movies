package cj.software.hierarchy.movie.spring;

/**
 * marks a method that its size shall be reported. Applicable for methods that return:
 * <ul>
 *     <li>an array of any type</li>
 *     <li>A {@link java.util.Collection} of any type</li>
 * </ul>
 */
public @interface TraceSize {
}
