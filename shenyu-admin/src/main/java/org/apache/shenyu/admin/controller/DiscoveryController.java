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

package org.apache.shenyu.admin.controller;

import org.apache.shenyu.admin.model.dto.DiscoveryDTO;
import org.apache.shenyu.admin.model.result.ShenyuAdminResult;
import org.apache.shenyu.admin.service.DiscoveryService;
import org.apache.shenyu.admin.utils.ShenyuResultMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * this is discovery controller.
 */
@Validated
@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    public DiscoveryController(final DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    /**
     * Discovery type enums API.
     *
     * @return {@linkplain ShenyuAdminResult}
     */
    @GetMapping("/typeEnums")
    public ShenyuAdminResult typeEnums() {
        return ShenyuAdminResult.success(ShenyuResultMessage.SUCCESS, discoveryService.typeEnums());
    }

    /**
     * query Discovery by plugin name and level.
     *
     * @param pluginName plugin name
     * @param level level
     * @return {@link org.apache.shenyu.admin.model.vo.DiscoveryVO}
     */
    @GetMapping("")
    public ShenyuAdminResult discovery(final String pluginName, final String level) {
        return ShenyuAdminResult.success(ShenyuResultMessage.SUCCESS, discoveryService.discovery(pluginName, level));
    }

    /**
     * Insert or update discovery.
     *
     * @param discoveryDTO {@link DiscoveryDTO}
     * @return {@linkplain ShenyuAdminResult}
     */
    @PostMapping("/insertOrUpdate")
    public ShenyuAdminResult createOrUpdate(@Valid @RequestBody final DiscoveryDTO discoveryDTO) {
        return ShenyuAdminResult.success(ShenyuResultMessage.SUCCESS, discoveryService.createOrUpdate(discoveryDTO));
    }
}
