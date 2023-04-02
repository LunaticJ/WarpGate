-- 令牌桶获取脚本（懒汉）
-- KEYS[1] 令牌桶的key
-- ARGV[1] 领取令牌数量
-- ARGV[2] 最大等待时长
-- ARGV[3] 令牌桶最大容量
-- ARGV[4] 每秒令牌生成数
-- return 等待的毫秒数, 返回-1则没获取到令牌
-- 当前时间微秒
local nowArr=redis.call('time')
local nowTime=nowArr[1]*1000000+nowArr[2]
-- 令牌桶的key
local key=KEYS[1]
-- 获取当前需要领取令牌桶数量
local needTokens=tonumber(ARGV[1])
-- 最大等待时长 微秒
local maxWaitTime=tonumber(ARGV[2])
-- 最大令牌数
local maxTokens=tonumber(ARGV[3])
-- 每秒生成令牌数
local secTokens=tonumber(ARGV[4])
-- 单个令牌生成所需微秒
local singleTokenTime=1000000/secTokens


-- 获取令牌桶， 不存在则初始化
local tokenBucket=redis.call('hmget', key, 'nextTime', 'hasTokens')
-- 是否初始化
local needInit=false
-- 下一次可生成令牌的时间 微秒
local nextTime=0
-- 拥有的令牌数
local hasTokens=0
if not tokenBucket[1] then
    -- 初始化
    nextTime=nowTime
    hasTokens=maxTokens
    needInit=true
else
    nextTime=tokenBucket[1]
    hasTokens=tokenBucket[2]
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