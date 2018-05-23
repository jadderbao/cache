/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.cache.api;

import com.fanwe.lib.cache.DiskInfo;
import com.fanwe.lib.cache.handler.ObjectHandler;

/**
 * 对象缓存
 */
public class SimpleObjectCache implements ObjectCache
{
    private final DiskInfo mDiskInfo;

    public SimpleObjectCache(DiskInfo diskInfo)
    {
        mDiskInfo = diskInfo;
    }

    @Override
    public boolean put(Object value)
    {
        if (value == null)
            return false;

        final String key = value.getClass().getName();
        return new ObjectHandler<>(mDiskInfo).putCache(key, value);
    }

    @Override
    public <T> T get(Class<T> clazz)
    {
        final String key = clazz.getName();
        return new ObjectHandler<T>(mDiskInfo).getCache(key, clazz);
    }

    @Override
    public boolean remove(Class clazz)
    {
        final String key = clazz.getName();
        return new ObjectHandler<>(mDiskInfo).removeCache(key);
    }
}