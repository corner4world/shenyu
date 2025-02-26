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

package org.apache.shenyu.e2e.model.handle;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * Divide rule handle.
 */
public class DivideRuleHandle implements RuleHandle {
    
    private String loadBalance;
    
    private String retryStrategy;
    
    @JsonSerialize(converter = IntConverter.class)
    private int retry;
    
    private long timeout;
    
    private long headerMaxSize;
    
    private long requestMaxSize;
    
    static class IntConverter implements Converter<Integer, String> {
        private static final JavaType _IN = SimpleType.constructUnsafe(int.class);
        private static final JavaType _OUT = ReferenceType.constructUnsafe(String.class);
        
        @Override
        public String convert(Integer integer) {
            return String.valueOf(integer);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return _IN;
        }
        
        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return _OUT;
        }
    }

    /**
     * builder constructor.
     *
     * @param builder builder
     */
    private DivideRuleHandle(Builder builder) {
        this.loadBalance = builder.loadBalance;
        this.retryStrategy = builder.retryStrategy;
        this.retry = builder.retry;
        this.timeout = builder.timeout;
        this.headerMaxSize = builder.headerMaxSize;
        this.requestMaxSize = builder.requestMaxSize;
    }

    /**
     * class builder.
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * get loadBalance.
     *
     * @return loadBalance
     */
    public String getLoadBalance() {
        return loadBalance;
    }

    /**
     * set loadBalance.
     *
     * @param loadBalance loadBalance
     */
    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    /**
     * get retryStrategy.
     *
     * @return retryStrategy
     */
    public String getRetryStrategy() {
        return retryStrategy;
    }

    /**
     * set retryStrategy.
     *
     * @param retryStrategy retryStrategy
     */
    public void setRetryStrategy(String retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    /**
     * get retry.
     *
     * @return retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * set retry.
     *
     * @param retry retry
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * get timeout.
     *
     * @return timeout
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * set timeout.
     *
     * @param timeout timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * get headerMaxSize.
     *
     * @return headerMaxSize
     */
    public long getHeaderMaxSize() {
        return headerMaxSize;
    }

    /**
     * set headerMaxSize.
     *
     * @param headerMaxSize
     */
    public void setHeaderMaxSize(long headerMaxSize) {
        this.headerMaxSize = headerMaxSize;
    }

    /**
     * get requestMaxSize.
     *
     * @return requestMaxSize
     */
    public long getRequestMaxSize() {
        return requestMaxSize;
    }

    /**
     * set requestMaxSize.
     *
     * @param requestMaxSize requestMaxSize
     */
    public void setRequestMaxSize(long requestMaxSize) {
        this.requestMaxSize = requestMaxSize;
    }

    /**
     * class builder.
     */
    public static final class Builder {

        private String loadBalance;

        private String retryStrategy;

        @JsonSerialize(converter = IntConverter.class)
        private int retry;

        private long timeout;

        private long headerMaxSize;

        private long requestMaxSize;

        /**
         * no args constructor.
         */
        private Builder() {

        }

        /**
         * build new Object.
         *
         * @return DivideRuleHandle
         */
        public DivideRuleHandle build() {
            return new DivideRuleHandle(this);
        }

        /**
         * build loadBalance.
         *
         * @param loadBalance loadBalance
         * @return this
         */
        public Builder loadBalance(String loadBalance) {
            this.loadBalance = loadBalance;
            return this;
        }

        /**
         * build retryStrategy.
         *
         * @param retryStrategy retryStrategy
         * @return this
         */
        public Builder retryStrategy(String retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        /**
         * build retry.
         *
         * @param retry retry
         * @return this
         */
        public Builder retry(int retry) {
            this.retry = retry;
            return this;
        }

        /**
         * build timeout.
         *
         * @param timeout timeout
         * @return this
         */
        public Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * build headerMaxSize.
         *
         * @param headerMaxSize headerMaxSize
         * @return this
         */
        public Builder headerMaxSize(long headerMaxSize) {
            this.headerMaxSize = headerMaxSize;
            return this;
        }

        /**
         * build requestMaxSize.
         *
         * @param requestMaxSize requestMaxSize
         * @return this
         */
        public Builder requestMaxSize(long requestMaxSize) {
            this.requestMaxSize = requestMaxSize;
            return this;
        }
    }
}
