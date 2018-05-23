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
package com.fanwe.lib.cache.handler;

import com.fanwe.lib.cache.DiskInfo;

/**
 * String处理类
 */
public class StringHandler extends ByteConverterHandler<String>
{
    public StringHandler(DiskInfo diskInfo)
    {
        super(diskInfo);
    }

    @Override
    protected byte[] valueToByte(String value)
    {
        return value.getBytes();
    }

    @Override
    protected String byteToValue(byte[] bytes, Class<String> clazz)
    {
        return new String(bytes);
    }

    @Override
    protected String getKeyPrefix()
    {
        return "string_";
    }
}