package cj.software.util.function;

@FunctionalInterface
public interface PureFunction {
    @SuppressWarnings("java:S112")
    void doit() throws Exception;
}
