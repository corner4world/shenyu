/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.web.filter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type health filter.
 */
public final class HealthFilter extends AbstractWebFilter {
    
    private final DispatcherHandler dispatcherHandler;
    
    private final Set<String> paths;
    
    /**
     * Instantiates a new Health filter.
     *
     * @param dispatcherHandler the dispatcher handler
     * @param paths the paths
     */
    public HealthFilter(final DispatcherHandler dispatcherHandler, final List<String> paths) {
        this.dispatcherHandler = dispatcherHandler;
        if (CollectionUtils.isEmpty(paths)) {
            this.paths = new HashSet<>(Arrays.asList("/actuator", "/health_check"));
        } else {
            this.paths = new HashSet<>(paths); 
        }
    }
    
    @Override
    protected Mono<Boolean> doMatcher(final ServerWebExchange exchange, final WebFilterChain chain) {
        return Mono.just(paths.stream().anyMatch(path -> exchange.getRequest().getURI().getPath().startsWith(path)));
    }
    
    @Override
    protected Mono<Void> doFilter(final ServerWebExchange exchange) {
        return dispatcherHandler.handle(exchange);
    }
}
