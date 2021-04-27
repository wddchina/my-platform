package com.wdd.myplatform.controller;

import com.wdd.myplatform.redisson.RedisLockUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redLock")
public class RedLockController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String GOODSNUMKEY = "goodsNumKey";

    @GetMapping("addGoodsNum")
    public void addGoodsNum(){
        stringRedisTemplate.opsForValue().set(GOODSNUMKEY,"100");
    }

    @GetMapping("/testSeckill")
    @ApiOperation("测试秒杀redLock锁")
    public void testSeckill() throws InterruptedException {
        String key = "goodsNumKey";
        RedisLockUtil.lock(key);
        String num = stringRedisTemplate.opsForValue().get(GOODSNUMKEY);
        if(Integer.parseInt(num)<=0){
            return;
        }
        stringRedisTemplate.opsForValue().decrement(GOODSNUMKEY,1);
        System.out.println(Thread.currentThread().getName()+"-------------"+stringRedisTemplate.opsForValue().get(GOODSNUMKEY));
    }
}
