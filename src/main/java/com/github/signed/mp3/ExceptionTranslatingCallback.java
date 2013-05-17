package com.github.signed.mp3;

public abstract class ExceptionTranslatingCallback<T> implements  CallbackWithFallback<T>{
    @Override
    public void call(T first) {
        try {
            callWithoutConstraint(first);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fallback() {
        try{
            fallbackWithoutConstraint();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void callWithoutConstraint(T first) throws Exception;

    protected void fallbackWithoutConstraint() throws Exception {
        //do nothing
    }
}