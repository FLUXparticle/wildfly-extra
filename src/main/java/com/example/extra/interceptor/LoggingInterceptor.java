package com.example.extra.interceptor;

import jakarta.interceptor.*;
import org.jboss.logging.*;

import static java.util.Arrays.*;

@Loggable
@Interceptor
public class LoggingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        LOGGER.infof("Calling method: %s parameters: %s", ctx.getMethod().getName(), asList(ctx.getParameters()));
        return ctx.proceed();
    }

}
