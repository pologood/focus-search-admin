package cn.focus.search.admin.service;

import cn.focus.dc.commons.redis.ShardedRWRedisPool;
import cn.focus.dc.commons.redis.ShardedRWRedisPoolConfig;
import cn.focus.dc.commons.redis.sce.SCERWRedisPoolFactory;
import cn.focus.search.admin.config.WriteReadEnum;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

import java.util.*;

/**
 * redis
 *
 * @author zzz
 */
@Service
public class RedisService {
    /**
     * 在Spring文件中配置SCERWRedisPoolFactory的实例后，可以直接获取ShardedRWRedisPool就可以了。
     */
    @Autowired
    private ShardedRWRedisPool shardedRWRedisPool;

    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    /**
     * 删除某个key值
     *
     * @param key
     */
    public void delKey(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }

    public String getRedis(String prefix, String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            String value = jedis.get(prefix + key);
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }

    public String getRedis(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }
    public int exists(String key) {
    	//存在，返回1，不存在，返回0，出错，返回-1.
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            int flag=0;
            if(jedis.exists(key)) flag=1;
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return -1;
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }

    public void setRedis(String prefix, String key, String value, boolean isExpired, int time) {
        ShardedJedis jedis = null;
        try {
            // ! 这里申请一个写链接
            jedis = shardedRWRedisPool.borrowWriteResource();
            if (isExpired) {
                //setex 设置有失效时间方法;
                jedis.setex(prefix + key, time, value);
            } else {
                // setnx 永不失效，若给定的 key 已经存在，则 SETNX 不做任何动作(即不会被覆盖已存在的key)
                jedis.setnx(prefix + key, value);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    public void setRedis(String key, String value, boolean isExpired, int time) {
        ShardedJedis jedis = null;
        try {
            // ! 这里申请一个写链接
            jedis = shardedRWRedisPool.borrowWriteResource();
            if (isExpired) {
                //setex 设置有失效时间方法;
                jedis.setex(key, time, value);
            } else {
                // setnx 永不失效，若给定的 key 已经存在，则 SETNX 不做任何动作(即不会被覆盖已存在的key)
                jedis.setnx(key, value);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * @param key1
     * @param key2
     */
    public void setRedis(String key, String value) {
        ShardedJedis jedis = null;
        try {
            // ! 这里申请一个写链接
            jedis = shardedRWRedisPool.borrowWriteResource();
            jedis.set(key,value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // ! 这里还回一个写链接 【请注意一定要区分，还回链接的类型】
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * 从list类型的value中删除指定对象
     *
     * @param prefix
     * @param key
     * @param object
     */
    /**
     * 把Set存入redis
     *
     * @param prefix
     * @param key
     * @param set
     * @param T
     */
    public <T> void putRedisSet(String prefix, String key, Set<T> set, Class<?> T, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            key = (key == null ? "" : key);
            ShardedJedisPipeline p = jedis.pipelined();

            if (set != null && set.size() > 0) {
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    p.sadd(prefix + key, JSONObject.toJSONString(it.next()));
                }

                if (seconds > 0) {
                    jedis.expire(prefix + key, seconds);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * 把Set存入redis
     *@param key
     * @param set
     * @param T
     */
    public <T> void putRedisSet(String key, Set<T> set, Class<?> T, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            key = (key == null ? "" : key);
            ShardedJedisPipeline p = jedis.pipelined();

            if (set != null && set.size() > 0) {
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    p.sadd(key, (String) it.next());
                }

                if (seconds > 0) {
                    jedis.expire(key, seconds);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * 把Set存入redis
     *
     * @param key
     * @param set
     * @param T
     */
    public void putRedisSet(String key, String[] set, Class<?> T, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            key = (key == null ? "" : key);
            ShardedJedisPipeline p = jedis.pipelined();

            if (set != null && set.length > 0) {
                for (String s : set) {
                    p.sadd(key, s);
                }
                if (seconds > 0) {
                    jedis.expire(key, seconds);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * 取出Set
     *
     * @param prefix
     * @param key
     * @param T  复杂类型，不能是int，integer，String等
     * @return
     */
    public <T> Set<T> popRedisSet(String prefix, String key, Class<?> T) {
        ShardedJedis jedis = null;
        try {
            key = (key == null ? "" : key);
            Set<T> result = new HashSet<T>();

            jedis = shardedRWRedisPool.borrowReadResource();
            Set<String> set = jedis.smembers(prefix + key);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                result.add((T) JSONObject.parseObject(it.next(), T));
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }
    
    /**
     * 取出Set
     *
     * @param prefix
     * @param key
     * @param T  简单类型。
     * @return
     */
    public <T> Set<T> popRedisSet(String prefix, String key) {
        ShardedJedis jedis = null;
        try {
            key = (key == null ? "" : key);
            Set<T> result = new HashSet<T>();

            jedis = shardedRWRedisPool.borrowReadResource();
            Set<String> set = jedis.smembers(prefix + key);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                result.add((T) it.next());
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnReadResource(jedis);
            }
        }
    }

    /**
     * 删除set中某个值
     *
     * @param prefix
     * @param key
     * @param object
     * @param <T>
     */
    public <T> void remRedisSet(String prefix, String key, T object) {
        ShardedJedis jedis = null;
        try {
            key = (key == null ? "" : key);
            jedis = shardedRWRedisPool.borrowWriteResource();

            jedis.srem(prefix + key, JSONObject.toJSONString(object));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * redies 缓存map类型
     *
     * @param key
     * @param map
     * @param expireTime 失效时间
     */
    public void setRedisMap(String key, Map<String, String> map, int expireTime) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            result = jedis.hmset(key, map);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
    }

    /**
     * 从redis获取数据
     *
     * @param key
     * @return
     */
    public Map<String, String> getRedisMap(String key) {
        ShardedJedis jedis = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            jedis = shardedRWRedisPool.borrowWriteResource();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                shardedRWRedisPool.returnWriteResource(jedis);
            }
        }
        return map;
    }

    /**
     * 获取两个集合的差集，大集合的key放到前边;
     *
     * @param key2
     * @param key
     * @return
     */
    public Set<String> sdiff(String key, String key2) {
        ShardedJedis shardedJedis = null;
        Set<String> set = new HashSet<String>();
        try {
            shardedJedis = shardedRWRedisPool.borrowWriteResource();
            Jedis jedis = shardedJedis.getShard(WriteReadEnum.wirte_shared.getVlaue());
            set = jedis.sdiff(key, key2);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (shardedJedis != null) {
                shardedRWRedisPool.returnWriteResource(shardedJedis);
            }
        }
        return set;
    }

    /**
     * 获取具有读权限的客户端
     *
     * @return
     */
    public ShardedJedis getRClient() {
        ShardedJedis j = shardedRWRedisPool.borrowReadResource();
        return j;
    }

    public void returnReadBroResource(ShardedJedis redis) {
        if (redis != null) {
            shardedRWRedisPool.returnReadResource(redis);
        }
    }

    public ShardedJedis getWClient() {
        ShardedJedis j = shardedRWRedisPool.borrowWriteResource();
        return j;
    }

    public void returnWriteBroResource(ShardedJedis redis) {
        if (redis != null) {
            shardedRWRedisPool.returnWriteResource(redis);
        }
    }
}