package com.github.signed.mp3;

public abstract class ExceptionTranslatingCallback<T> implements  Callback<T>{
    @Override
    public void call(T first) {
        try {
            callWithoutConstraint(first);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void callWithoutConstraint(T first) throws Exception;
}
