-- KEYS[1]=auction:item:{id}, KEYS[2]=auction:bid:dedup
-- ARGV: userId, amount, idemKey, thresholdSec, extendSec, nowMs
-- return {code, msg, newEndSec}
local key = KEYS[1]
local dedup = KEYS[2]
if redis.call('HEXISTS', dedup, ARGV[3]) == 1 then return {1,'idempotent',0} end
local status = redis.call('HGET', key, 'status')
if not status or status ~= 'ONGOING' then return {4,'not-ongoing',0} end
local cur = tonumber(redis.call('HGET', key, 'currentPrice') or '0')
local amt = tonumber(ARGV[2])
if amt <= cur then return {2,'price-too-low',0} end
local leader = redis.call('HGET', key, 'leaderId') or ''
if leader == ARGV[1] then return {3,'you-are-leader',0} end
redis.call('HMSET', key, 'currentPrice', tostring(amt), 'leaderId', ARGV[1])
local endMs = tonumber(redis.call('HGET', key, 'endTimeMs') or '0')
local thr = tonumber(ARGV[4]) * 1000
local ext = tonumber(ARGV[5]) * 1000
local now = tonumber(ARGV[6])
local newEnd = endMs
if endMs - now <= thr then
    newEnd = endMs + ext
    redis.call('HSET', key, 'endTimeMs', tostring(newEnd))
end
redis.call('HSET', dedup, ARGV[3], ARGV[3])
return {0, 'ok', math.floor(newEnd/1000)}
