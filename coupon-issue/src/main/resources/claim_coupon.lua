local couponKey = KEYS[1]
local userId = ARGV[1]

-- 成功领取
local SUCCESS = 1
-- 库存不足
local OUT_OF_STOCK = 0
-- 已领取
local ALREADY_CLAIMED = -1
-- 优惠券不存在
local COUPON_NOT_FOUND = -2


-- 检查优惠券是否存在
print("1Debug message: value = " .. couponKey)

local couponExists = redis.call("HEXISTS", couponKey, "stock")
print("2Debug message: value = " .. couponExists)
if couponExists == 1 then
    -- 检查用户是否已经领取了优惠券
    local userClaimed = redis.call("SISMEMBER", "coupon_claimed:" .. userId, couponKey)

    if userClaimed == 0 then
        -- 试图扣除库存
        local currentStock = redis.call("HGET", couponKey, "stock")

        if tonumber(currentStock) > 0 then
            redis.call("HINCRBY", couponKey, "stock", -1) -- 扣减库存
            redis.call("SADD", "coupon_claimed:" .. userId, couponKey) --添加到已领取
            return SUCCESS -- 领取成功
        else
            return OUT_OF_STOCK -- 库存不足
        end
    else
        return ALREADY_CLAIMED -- 已经领取
    end
else
    return COUPON_NOT_FOUND -- 优惠券不存在
end
