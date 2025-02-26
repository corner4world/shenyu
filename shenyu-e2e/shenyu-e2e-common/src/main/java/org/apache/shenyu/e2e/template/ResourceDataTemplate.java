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

package org.apache.shenyu.e2e.template;

import com.google.common.base.Strings;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import org.apache.shenyu.e2e.model.MatchMode;
import org.apache.shenyu.e2e.model.Plugin;
import org.apache.shenyu.e2e.model.SelectorType;
import org.apache.shenyu.e2e.model.data.Condition;
import org.apache.shenyu.e2e.model.data.RuleData;
import org.apache.shenyu.e2e.model.data.SelectorData;
import org.apache.shenyu.e2e.model.handle.DivideRuleHandle;
import org.apache.shenyu.e2e.model.handle.Upstreams;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Templates for various entity classes.
 */
public class ResourceDataTemplate {

    /**
     * Build new SelectorBuilder.
     *
     * @param name name
     * @param plugin plugin
     * @return SelectorData.Builder
     */
    public static SelectorData.SelectorDataBuilder newSelectorBuilder(@NotNull String name, Plugin plugin) {
        return SelectorData.builder()
                .name(name)
                .plugin(plugin)
                .type(SelectorType.CUSTOM)
                .matchMode(MatchMode.AND)
                .continued(true)
                .logged(true)
                .enabled(true)
                .matchRestful(false)
                .sort(1);
    }

    /**
     * Build new RuleBuilder.
     *
     * @param name name
     * @return RuleData.Builder
     */
    public static RuleData.RuleDataBuilder newRuleBuilder(@Nonnull String name) {
        return newRuleBuilder(name, null);
    }
    
    public static RuleData.RuleDataBuilder newRuleBuilder(@Nonnull String name, String selectorId) {
        return RuleData.builder()
                .name(name)
                .matchMode(MatchMode.AND)
                .selectorId(selectorId)
                .enabled(true)
                .logged(true)
                .matchRestful(false)
                .sort(1);
    }

    /**
     * Build new DivideRuleHandle.
     *
     * @return DivideRuleHandle
     */
    public static DivideRuleHandle newDivideRuleHandle() {
        return DivideRuleHandle.builder()
                .loadBalance("hash")
                .retryStrategy("current")
                .retry(1)
                .timeout(3000)
                .headerMaxSize(10240)
                .requestMaxSize(10240)
                .build();
    }

    /**
     * Build new Condition.
     *
     * @param type type
     * @param opt opt
     * @param value value
     * @return Condition
     */
    public static Condition newCondition(Condition.ParamType type, Condition.Operator opt, String value) {
        return newCondition(type, opt, null, value);
    }
    
    public static Condition newCondition(Condition.ParamType type, Condition.Operator opt, @Nullable String key, String value) {
        return Condition.builder()
                .paramType(type)
                .operator(opt)
                .paramName(Strings.isNullOrEmpty(key) ? "/" : key)
                .paramValue(value)
                .build();
    }
    
    public static List<Condition> newConditions(Condition.ParamType type, Condition.Operator opt, String value) {
        ArrayList<Condition> list = new ArrayList<>();
        list.add(newCondition(type, opt, value));
        return list;
    }

    /**
     * Build new Upstream.
     *
     * @param url url
     * @return Upstream
     */
    public static Upstreams.Upstream newUpstream(String url) {
        return Upstreams.Upstream.builder()
                .upstreamUrl(url)
                .build();
    }
    
    public static Upstreams newUpstreamsBuilder(String url) {
        return Upstreams.builder().add(newUpstream(url)).build();
    }
}
