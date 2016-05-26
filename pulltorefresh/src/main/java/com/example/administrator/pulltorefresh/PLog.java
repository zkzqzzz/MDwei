package com.example.administrator.pulltorefresh;

import android.util.Log;

/**
 * Log统一管理类
 *
 * @author way
 */
public class PLog {

    private PLog() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "wl";
    private static final int I = 0x1;
    private static final int D = 0x2;
    private static final int E = 0x3;
    private static final int V = 0x4;
    private static final int W = 0x5;

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            printLog(I, null, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            printLog(D, null, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            printLog(E, null, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            printLog(V, null, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void i(String msg, Object... args) {
        i(TAG, msg, args);
    }

    public static void d(String msg, Object... args) {
        d(TAG, msg, args);
    }

    public static void e(String msg, Object... args) {
        e(TAG, msg, args);
    }

    public static void v(String msg, Object... args) {
        v(TAG, msg, args);
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param tag
     * @param msg
     * @param args
     */
    public static void v(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        printLog(V, tag, msg);
    }

    /**
     * Send a DEBUG log message
     *
     * @param tag
     * @param msg
     * @param args
     */
    public static void d(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        printLog(D, tag, msg);
    }

    /**
     * Send an INFO log message
     *
     * @param tag
     * @param msg
     * @param args
     */
    public static void i(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        printLog(I, tag, msg);
    }

    /**
     * Send a WARNING log message
     *
     * @param tag
     * @param msg
     * @param args
     */
    public static void w(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        printLog(W, tag, msg);
    }

    /**
     * Send an ERROR log message
     *
     * @param tag
     * @param msg
     * @param args
     */
    public static void e(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        printLog(E, tag, msg);
    }

    private static void printLog(int type, String tagStr, Object msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String tag = generateTag(stackTrace[4], tagStr);
        StringBuilder stringBuilder = new StringBuilder(generateLink(stackTrace[4]));
        stringBuilder.append(msg);
        String logStr = stringBuilder.toString();
        switch (type) {
            case V:
                Log.v(tag, logStr);
                break;
            case D:
                Log.d(tag, logStr);
                break;
            case I:
                Log.i(tag, logStr);
                break;
            case E:
                Log.e(tag, logStr);
                break;
            default:
                break;
        }
    }

    private static String generateLink(StackTraceElement stackTrace) {
        String className = stackTrace.getFileName();
        String methodName = stackTrace.getMethodName();
        int lineNumber = stackTrace.getLineNumber();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");
        return stringBuilder.toString();
    }

    private static String generateTag(StackTraceElement stackTrace, String tagStr) {
        String className = stackTrace.getFileName();
        return (tagStr == null ? className : tagStr);
    }

}