package com.freelance.lautarocutri;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "app")
public interface AppConfig {
    @WithName("problem.max.len")
    Integer problemMaxLen();
    @WithName("solution.greedy.max.depth")
    Integer solutionGreedyMaxDepth();
    @WithName("solution.full.max.depth")
    Integer solutionFullMaxDepth();
}