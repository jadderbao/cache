package com.sd.lib.cache;

import com.sd.lib.cache.handler.BooleanHandler;
import com.sd.lib.cache.handler.BytesHandler;
import com.sd.lib.cache.handler.DoubleHandler;
import com.sd.lib.cache.handler.FloatHandler;
import com.sd.lib.cache.handler.IntegerHandler;
import com.sd.lib.cache.handler.LongHandler;
import com.sd.lib.cache.handler.StringHandler;
import com.sd.lib.cache.simple.SimpleMultiObjectCache;
import com.sd.lib.cache.simple.SimpleObjectCache;

public abstract class FCache implements Cache, CacheInfo
{
    private static CacheConfig sCacheConfig;

    private boolean mEncrypt;
    private boolean mMemorySupport;
    private ObjectConverter mObjectConverter;
    private EncryptConverter mEncryptConverter;
    private ExceptionHandler mExceptionHandler;

    private IntegerHandler mIntegerHandler;
    private LongHandler mLongHandler;
    private FloatHandler mFloatHandler;
    private DoubleHandler mDoubleHandler;
    private BooleanHandler mBooleanHandler;
    private StringHandler mStringHandler;
    private BytesHandler mBytesHandler;

    private SimpleObjectCache mObjectCache;
    private SimpleMultiObjectCache mMultiObjectCache;

    /**
     * 初始化
     *
     * @param config
     */
    public static final void init(CacheConfig config)
    {
        synchronized (Cache.class)
        {
            if (config == null)
                throw new NullPointerException();

            if (sCacheConfig == null)
                sCacheConfig = config;
            else
                throw new RuntimeException("init method can only be called once");
        }
    }

    /**
     * 返回config
     *
     * @return
     */
    public static final CacheConfig getCacheConfig()
    {
        if (sCacheConfig == null)
            throw new NullPointerException("you must invoke FCache.init(CacheConfig config) before this");
        return sCacheConfig;
    }

    /**
     * 使用本地磁盘缓存
     * <p>
     * 默认使用内部存储目录"/data/包名/files/disk_file"，可以在初始化的时候设置{@link CacheConfig.Builder#setDiskCacheStore(CacheStore)}
     *
     * @return
     */
    public static Cache disk()
    {
        final Cache cache = new FCache()
        {
            @Override
            public CacheStore getCacheStore()
            {
                return FCache.getCacheConfig().mDiskCacheStore;
            }
        };
        return cache;
    }

    //---------- Cache start ----------

    @Override
    public final Cache setEncrypt(boolean encrypt)
    {
        mEncrypt = encrypt;
        return this;
    }

    @Override
    public final Cache setMemorySupport(boolean support)
    {
        mMemorySupport = support;
        return this;
    }

    @Override
    public final Cache setObjectConverter(ObjectConverter converter)
    {
        mObjectConverter = converter;
        return this;
    }

    @Override
    public final Cache setEncryptConverter(EncryptConverter converter)
    {
        mEncryptConverter = converter;
        return this;
    }

    @Override
    public final Cache setExceptionHandler(ExceptionHandler handler)
    {
        mExceptionHandler = handler;
        return this;
    }

    @Override
    public final CommonCache<Integer> cacheInteger()
    {
        if (mIntegerHandler == null)
            mIntegerHandler = new IntegerHandler(this);
        return mIntegerHandler;
    }

    @Override
    public final CommonCache<Long> cacheLong()
    {
        if (mLongHandler == null)
            mLongHandler = new LongHandler(this);
        return mLongHandler;
    }

    @Override
    public final CommonCache<Float> cacheFloat()
    {
        if (mFloatHandler == null)
            mFloatHandler = new FloatHandler(this);
        return mFloatHandler;
    }

    @Override
    public final CommonCache<Double> cacheDouble()
    {
        if (mDoubleHandler == null)
            mDoubleHandler = new DoubleHandler(this);
        return mDoubleHandler;
    }

    @Override
    public final CommonCache<Boolean> cacheBoolean()
    {
        if (mBooleanHandler == null)
            mBooleanHandler = new BooleanHandler(this);
        return mBooleanHandler;
    }

    @Override
    public final CommonCache<String> cacheString()
    {
        if (mStringHandler == null)
            mStringHandler = new StringHandler(this);
        return mStringHandler;
    }

    @Override
    public final CommonCache<byte[]> cacheBytes()
    {
        if (mBytesHandler == null)
            mBytesHandler = new BytesHandler(this);
        return mBytesHandler;
    }

    @Override
    public final ObjectCache cacheObject()
    {
        if (mObjectCache == null)
            mObjectCache = new SimpleObjectCache(this);
        return mObjectCache;
    }

    @Override
    public <T> MultiObjectCache<T> cacheMultiObject(Class<T> clazz)
    {
        if (mMultiObjectCache == null || mMultiObjectCache.mObjectClass != clazz)
            mMultiObjectCache = new SimpleMultiObjectCache(this, clazz);
        return mMultiObjectCache;
    }

    //---------- Cache end ----------

    //---------- CacheInfo start ----------

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
    public final ObjectConverter getObjectConverter()
    {
        return mObjectConverter != null ? mObjectConverter : getCacheConfig().mObjectConverter;
    }

    @Override
    public final EncryptConverter getEncryptConverter()
    {
        return mEncryptConverter != null ? mEncryptConverter : getCacheConfig().mEncryptConverter;
    }

    @Override
    public final ExceptionHandler getExceptionHandler()
    {
        return mExceptionHandler != null ? mExceptionHandler : getCacheConfig().mExceptionHandler;
    }

    //---------- CacheInfo end ----------
}
