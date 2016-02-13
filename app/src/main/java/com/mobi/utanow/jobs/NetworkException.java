package com.mobi.utanow.jobs;

/**
 * Created by anthony on 2/10/16.
 */
public class NetworkException extends RuntimeException {

    private final int mErrorCode;

    public NetworkException(int errorCode) {
        mErrorCode = errorCode;
    }

    public boolean shouldRetry() {
        return mErrorCode < 400 || mErrorCode > 499;
    }
}