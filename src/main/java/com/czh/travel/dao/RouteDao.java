package com.czh.travel.dao;

import com.czh.travel.domain.Route;
import com.czh.travel.domain.Seller;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RouteDao {


    @Select("<script>select * from route where 1 = 1 <if test='cid != 0'> and cid = #{cid} </if> <if test='!\"null\".equals(rname)'> and rname like '%${rname}%' </if> limit #{start},#{pageSize} </script>")

    List<Route> findAll(@Param("cid") int cid, @Param("start") int start, @Param("pageSize") int pageSize, @Param("rname") String rname);

    @Select("<script>select count(*) from route where <if test='cid != null'>cid = #{cid} </if> <if test='!\"null\".equals(rname)'> and rname like '%${rname}%'</if> </script>")
    int findTotalCount(@Param("cid") int cid, @Param("rname") String rname);

    @Results({
            @Result(id = true,property = "rid",column = "rid"),
            @Result(property = "rname",column = "rname"),
            @Result(property = "price",column = "price"),
            @Result(property = "routeIntroduce",column = "routeIntroduce"),
            @Result(property = "rflag",column = "rflag"),
            @Result(property = "rdate",column = "rdate"),
            @Result(property = "isThemeTour",column = "isThemeTour"),
            @Result(property = "count",column = "count"),
            @Result(property = "rimage",column = "rimage"),
            @Result(property = "sourceId",column = "sourceId"),
            @Result(property = "cid",column = "cid"),
            @Result(property = "seller",column = "sid",javaType = Seller.class,one = @One(select = "com.czh.travel.dao.SellerDao.findById")),
            @Result(property = "routeImgList",column = "rid",javaType = List.class,many = @Many(select = "com.czh.travel.dao.RouteImgDao.findByRid")),

    })
    @Select("select * from route where rid = #{rid}")
    Route findOne(int rid);

    @Select("select * from route where rid in (select rid from favorite where uid = #{uid}) limit #{start} , #{pageSize}")
    List<Route> findByFavoritePage(@Param("uid") int uid, @Param("start") int start, @Param("pageSize") int pageSize);

    @Update("update route set count = count + 1 where rid = #{rid}")
    void updateCount(int rid);

    @Select("<script> select * from route where 1 = 1 <if test='cid != 0'>and cid = #{cid} </if> <if test='!\"null\".equals(rname)'> and rname like '%${rname}%'</if> <if test='lPrice != 0'> and price &gt;  #{lPrice}</if> <if test='hPrice != 0'> and price  &lt; #{hPrice}</if> order by count DESC limit #{start},#{pageSize} </script>")
    List<Route> findRouteByRnameAndPrice(@Param("cid") int cid, @Param("rname") String rname, @Param("lPrice") int lPrice, @Param("hPrice") int hPrice, @Param("start") int start, @Param("pageSize") int pageSize);

    @Select("<script> select count(*) from route where 1 = 1 <if test='cid != 0'> and cid = #{cid} </if> <if test='!\"null\".equals(rname)'> and rname like '%${rname}%'</if> <if test='lPrice != 0'> and price &gt; #{lPrice}</if> <if test='hPrice != 0'> and price &lt; #{hPrice} </if> </script>")
    int findTotalCountByRnameAndPrice(@Param("cid") int cid, @Param("rname") String rname, @Param("lPrice") int lPrice, @Param("hPrice") int hPrice);
}
