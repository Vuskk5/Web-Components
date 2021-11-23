package com.github.vuskk5;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface FrameworkConfig extends Config {
    @Key("cleanup.thread.maxlife")
    int cleanupThreadMaxLife();
}
