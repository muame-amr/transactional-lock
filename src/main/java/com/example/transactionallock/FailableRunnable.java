package com.example.transactionallock;

public interface FailableRunnable<T extends Exception> {
    void run() throws Exception;
}
