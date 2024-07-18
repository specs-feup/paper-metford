package com.beemdevelopment.aegis;

import dagger.hilt.android.testing.CustomTestApplication;

import org.junit.Rule;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

@CustomTestApplication(AegisApplicationBase.class)
public interface AegisTestApplication {
    @Rule public Timeout timeout = new Timeout(60_000, TimeUnit.MILLISECONDS);
}
