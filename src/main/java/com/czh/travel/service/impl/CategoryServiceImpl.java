package com.czh.travel.service.impl;

import com.czh.travel.dao.CategoryDao;
import com.czh.travel.domain.Category;
import com.czh.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Category> findAll() throws Exception {
        //Set<String> categorys = redisTemplate.opsForZSet().range("category", 0, -1);
        List<Category> ca = null;
        Set<ZSetOperations.TypedTuple<String>> categorys = redisTemplate.opsForZSet().rangeWithScores("category", 0, -1);
        //如果redis中没有数据的话
        if(null == categorys)
        {
            //查询数据库获得数据
            ca = categoryDao.findAll();
            for (Category category : ca) {
                redisTemplate.opsForZSet().add("category",category.getCname(),category.getCid());
            }
            //System.out.println("===============从数据库获得数据===============");
        }else{
            ca = new ArrayList<Category>();
            for (ZSetOperations.TypedTuple<String> tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getValue());
                int score = (new Double(tuple.getScore())).intValue();
                category.setCid(score);
                ca.add(category);
            }
            //System.out.println("===============从redis缓存中获得数据===============");
        }
        return ca;
        /*//1.从jedis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2使用sortedset排序查询
        //这个语句没有查询到分数（cid）
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //查询sortedset中的分数和值
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> ca = null;
        //2.判断查询的集合是否为空
        if(categorys == null || categorys.size() == 0){
            //3.如果为空，要从数据库中查询，再将数据存入redis中
            //3.1.从数据库中查询
            ca = categoryDao.findAll();
            //3.2将集合数据传入redis中
            for (Category category : ca) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }else {
            //因为返回的是list集合类型的数据，所以要将set结合数据转移到list集合中
            ca = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                ca.add(category);
            }
        }
        return ca;*/
    }
}
