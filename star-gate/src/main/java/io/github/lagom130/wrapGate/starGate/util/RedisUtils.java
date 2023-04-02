package io.github.lagom130.wrapGate.starGate.util;

import java.util.List;

public class RedisUtils {
  /**
   * token bucket lua script
   * KEYS[1] key
   * ARGV[1] want get token number
   * ARGV[2] request get token can wait max ms
   * ARGV[3] bucket max token number
   * ARGV[4] token generate number/second
   * return wait ms, if return -1 then do not get token
   */
  public static final String TOKEN_BUCKET_LUA_SCRIPT = """
    -- 令牌桶获取脚本（懒汉）\s
    -- KEYS[1] 令牌桶的key
    -- ARGV[1] 领取令牌数量
    -- ARGV[2] 最大等待时长
    -- ARGV[3] 令牌桶最大容量
    -- ARGV[4] 每秒令牌生成数
    -- 当前时间微秒
    local nowArr=redis.call('time')
    local nowTime=nowArr[1]*1000000+nowArr[2]
    -- 令牌桶的key
    local key=KEYS[1]\s
    -- 获取当前需要领取令牌桶数量
    local needTokens=tonumber(ARGV[1])\s
    -- 最大等待时长 微秒
    local maxWaitTime=tonumber(ARGV[2])\s
    -- 最大令牌数
    local maxTokens=tonumber(ARGV[3])\s
    -- 每秒生成令牌数
    local secTokens=tonumber(ARGV[4])
    -- 单个令牌生成所需微秒
    local singleTokenTime=1000000/secTokens


    -- 获取令牌桶， 不存在则初始化
    local tokenbucket=redis.call('hmget', key, 'nextTime', 'hasTokens')
    -- 是否初始化
    local needInit=false
    -- 下一次可生成令牌的时间 微秒
    local nextTime=0
    -- 拥有的令牌数
    local hasTokens=0
    if not tokenbucket[1] then\s
        -- 初始化
        nextTime=nowTime\s
        hasTokens=maxTokens\s
        needInit=true\s
    else
        nextTime=tokenbucket[1]\s
        hasTokens=tokenbucket[2]
    end
    -- 重新计算令牌
    local hasTime=nowTime-nextTime
    if hasTime>0 then
        hasTokens=math.min(hasTime/singleTokenTime+hasTokens, maxTokens)
        nextTime=nowTime
    end
    -- 可获取令牌
    local canGetTokens=math.min(hasTokens, needTokens)
    -- 透支令牌数
    local overdraftTokens=needTokens-canGetTokens
    -- 透支令牌所需生成时间
    local overdraftTime=overdraftTokens*singleTokenTime
    -- 返回等待时间
    local returnTime=nextTime-nowTime
    if  returnTime>maxWaitTime then
        -- 超过最大等待时间, 不获取令牌
        if needInit then
            redis.call('hmset', key, 'nextTime', nextTime, 'hasTokens', hasTokens)
        end
        return -1
    else
        -- 重新设置令牌桶中的值
        hasTokens=hasTokens-canGetTokens
        nextTime=nextTime+overdraftTime
        redis.call('hmset', key, 'nextTime', nextTime, 'hasTokens', hasTokens)
        -- 返回当前请求需要等待时间 毫秒
        return returnTime/1000
    end
    """;
}
