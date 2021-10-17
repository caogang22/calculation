package com.caogang.analysis.engine;

import java.util.Set;

public interface Executor {

    boolean execute(Set<Task<?>> previousTask);
}
