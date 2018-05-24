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
package com.fanwe.lib.cache;

import java.io.File;

/**
 * Created by zhengjun on 2017/9/1.
 */
public interface DiskInfo
{
    /**
     * 是否需要加解密
     *
     * @return
     */
    boolean isEncrypt();

    /**
     * 是否支持内存存取
     *
     * @return
     */
    boolean isMemorySupport();

    /**
     * 返回存取的目录
     *
     * @return
     */
    File getDirectory();

    /**
     * 返回加解密转换器
     *
     * @return
     */
    Disk.EncryptConverter getEncryptConverter();

    /**
     * 返回对象转换器
     *
     * @return
     */
    Disk.ObjectConverter getObjectConverter();

    /**
     * 返回设置的异常处理对象
     *
     * @return
     */
    Disk.ExceptionHandler getExceptionHandler();
}
