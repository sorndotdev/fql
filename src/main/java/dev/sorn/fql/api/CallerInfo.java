package dev.sorn.fql.api;

public final class CallerInfo {
    private CallerInfo() {
        // prevent direct instantiation
    }

    public static String getCallerClassAndMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            StackTraceElement caller = stackTrace[3];
            String fullClassName = caller.getClassName();
            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            return simpleClassName + "#" + caller.getMethodName();
        } else {
            return "UnknownCaller#UnknownMethod";
        }
    }
}