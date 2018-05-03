package com.xyf.common.util;

import com.xyf.common.annotation.UiThread;
import io.reactivex.disposables.Disposable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class RxLifeCircle {

    @UiThread
    protected void addDisposable(@Nonnull String tag, @Nonnull Disposable disposable) {
        disposableMap.put(tag, disposable);
    }

    @UiThread
    protected void removeDisposable(@Nonnull String tag) {
        if (disposableMap.containsKey(tag)) {
            disposableMap.get(tag).dispose();
        }
        disposableMap.remove(tag);
    }

    @UiThread
    protected void clearDisposable() {
        for (Disposable disposable : disposableMap.values()) {
            disposable.dispose();
        }
        disposableMap.clear();
    }

    public static String getMethodTag() {
        final StackTraceElement element = new Throwable().getStackTrace()[1];
        return String.format("%s(%d)", element.getMethodName(), element.getLineNumber());
    }

    private final Map<String, Disposable> disposableMap = new HashMap<>();

}
