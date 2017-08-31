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
package com.fanwe.library.cache.handler;

import android.util.Log;

import com.fanwe.library.cache.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by zhengjun on 2017/8/31.
 */
public class SerializableHandler extends ObjectHandler<Serializable>
{
    private static final String SERIALIZABLE = "serializable_";

    public SerializableHandler(File directory)
    {
        super(directory);
    }

    @Override
    public String getKeyPrefix()
    {
        return SERIALIZABLE;
    }

    @Override
    protected boolean onPutObject(String key, Serializable object, File file)
    {
        ObjectOutputStream os = null;
        try
        {
            os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(object);
            os.flush();
            return true;
        } catch (Exception e)
        {
            Log.e(TAG, "putSerializable:" + e);
        } finally
        {
            Utils.closeQuietly(os);
        }
        return false;
    }

    @Override
    protected Serializable onGetObject(String key, File file)
    {
        ObjectInputStream is = null;
        try
        {
            is = new ObjectInputStream(new FileInputStream(file));
            return (Serializable) is.readObject();
        } catch (Exception e)
        {
            Log.e(TAG, "getSerializable:" + e);
        } finally
        {
            Utils.closeQuietly(is);
        }
        return null;
    }
}