package com.github.signed.mp3;

public interface CallbackWithFallback<T> extends Callback<T>{
    void fallback();
}
