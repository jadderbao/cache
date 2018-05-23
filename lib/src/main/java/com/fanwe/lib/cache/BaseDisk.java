package com.fanwe.lib.cache;

import android.content.Context;

import com.fanwe.lib.cache.converter.EncryptConverter;
import com.fanwe.lib.cache.converter.ObjectConverter;

import java.io.File;

abstract class BaseDisk<T extends BaseDisk> implements Disk<T>, DiskInfo
{
    private final File mDirectory;

    private static Context mContext;
    private static EncryptConverter sGlobalEncryptConverter;
    private static ObjectConverter sGlobalObjectConverter;

    private boolean mEncrypt;
    private EncryptConverter mEncryptConverter;
    private ObjectConverter mObjectConverter;

    private boolean mMemorySupport;

    protected BaseDisk(File directory)
    {
        if (directory == null)
            throw new NullPointerException("directory is null");
        mDirectory = directory;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context)
    {
        mContext = context.getApplicationContext();
    }

    /**
     * 设置全局加解密转换器
     *
     * @param globalEncryptConverter
     */
    public static void setGlobalEncryptConverter(EncryptConverter globalEncryptConverter)
    {
        synchronized (Disk.class)
        {
            sGlobalEncryptConverter = globalEncryptConverter;
        }
    }

    /**
     * 设置全局对象转换器
     *
     * @param globalObjectConverter
     */
    public static void setGlobalObjectConverter(ObjectConverter globalObjectConverter)
    {
        synchronized (Disk.class)
        {
            sGlobalObjectConverter = globalObjectConverter;
        }
    }

    //---------- Disk start ----------

    @Override
    public T setEncrypt(boolean encrypt)
    {
        synchronized (Disk.class)
        {
            mEncrypt = encrypt;
            return (T) this;
        }
    }

    @Override
    public T setMemorySupport(boolean memorySupport)
    {
        synchronized (Disk.class)
        {
            mMemorySupport = memorySupport;
            return (T) this;
        }
    }

    @Override
    public T setEncryptConverter(EncryptConverter encryptConverter)
    {
        synchronized (Disk.class)
        {
            mEncryptConverter = encryptConverter;
            return (T) this;
        }
    }

    @Override
    public T setObjectConverter(ObjectConverter objectConverter)
    {
        synchronized (Disk.class)
        {
            mObjectConverter = objectConverter;
            return (T) this;
        }
    }

    @Override
    public final long size()
    {
        return mDirectory.length();
    }

    @Override
    public final void delete()
    {
        synchronized (Disk.class)
        {
            deleteFileOrDir(mDirectory);
        }
    }

    //---------- Disk end ----------

    //---------- DiskInfo start ----------

    @Override
    public final boolean isEncrypt()
    {
        return mEncrypt;
    }

    @Override
    public final boolean isMemorySupport()
    {
        return mMemorySupport;
    }

    @Override
    public final File getDirectory()
    {
        if (!mDirectory.exists())
        {
            synchronized (Disk.class)
            {
                if (!mDirectory.exists())
                    mDirectory.mkdirs();
            }
        }
        return mDirectory;
    }

    @Override
    public final EncryptConverter getEncryptConverter()
    {
        return mEncryptConverter != null ? mEncryptConverter : sGlobalEncryptConverter;
    }

    @Override
    public final ObjectConverter getObjectConverter()
    {
        return mObjectConverter != null ? mObjectConverter : sGlobalObjectConverter;
    }

    //---------- DiskInfo end ----------

    //---------- util method start ----------

    private static Context getContext()
    {
        return mContext;
    }

    private static void checkContext()
    {
        if (mContext == null)
            throw new NullPointerException("you must invoke FDisk.init(Context) method before this");
    }

    /**
     * 返回外部存储"Android/data/包名/files/dirName"目录
     *
     * @param dirName
     * @return
     */
    protected static final File getExternalFilesDir(String dirName)
    {
        checkContext();
        return getContext().getExternalFilesDir(dirName);
    }

    /**
     * 返回外部存储"Android/data/包名/cache/dirName"目录
     *
     * @param dirName
     * @return
     */
    protected static final File getExternalCacheDir(String dirName)
    {
        checkContext();
        return new File(getContext().getExternalCacheDir(), dirName);
    }

    /**
     * 返回内部存储"/data/包名/files/dirName"目录
     *
     * @param dirName
     * @return
     */
    protected static final File getInternalFilesDir(String dirName)
    {
        checkContext();
        return new File(getContext().getFilesDir(), dirName);
    }

    /**
     * 返回内部存储"/data/包名/cache/dirName"目录
     *
     * @param dirName
     * @return
     */
    protected static final File getInternalCacheDir(String dirName)
    {
        checkContext();
        return new File(getContext().getCacheDir(), dirName);
    }

    private static boolean deleteFileOrDir(File path)
    {
        if (path == null || !path.exists())
            return true;

        if (path.isFile())
            return path.delete();

        File[] files = path.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    //---------- util method end ----------
}
