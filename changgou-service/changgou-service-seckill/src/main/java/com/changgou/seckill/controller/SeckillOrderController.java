package com.changgou.seckill.controller;

import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import com.changgou.seckill.service.SeckillOrderService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/seckillOrder")
@CrossOrigin
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * @param
     * @return entity.Result
     * @author 栗子
     * @Description 获取订单的状态信息
     * @Date 12:27 2019/11/2
     **/
    @RequestMapping("/query")
    public Result query() {
        String userId = "wangwu";
        SeckillStatus seckillStatus = seckillOrderService.queryStatus(userId);
        if (seckillStatus!=null){
            return new Result(true,seckillStatus.getStatus(),"抢购信息:",seckillStatus);
        }
        return new Result(false,StatusCode.NOTFOUNDERROR,"无抢购信息");
    }


    /**
     * @param time
     * @param id
     * @return entity.Result
     * @Description 提交订单
     * @Date 11:31 2019/11/2
     **/
    @RequestMapping("/add")
    public Result add(String time, Long id) {

        try {
            String userId = "wangwu";
            Boolean bool = seckillOrderService.add(time,id,userId);
            if (bool){
                return new Result(true, StatusCode.OK, "抢单成功");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.NOTFOUNDERROR, e.getMessage());
        }
        return null;
    }

    /***
     * SeckillOrder分页条件搜索实现
     * @param seckillOrder
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) SeckillOrder seckillOrder, @PathVariable int page, @PathVariable int size) {
        //调用SeckillOrderService实现分页条件查询SeckillOrder
        PageInfo<SeckillOrder> pageInfo = seckillOrderService.findPage(seckillOrder, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * SeckillOrder分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用SeckillOrderService实现分页查询SeckillOrder
        PageInfo<SeckillOrder> pageInfo = seckillOrderService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param seckillOrder
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<SeckillOrder>> findList(@RequestBody(required = false) SeckillOrder seckillOrder) {
        //调用SeckillOrderService实现条件查询SeckillOrder
        List<SeckillOrder> list = seckillOrderService.findList(seckillOrder);
        return new Result<List<SeckillOrder>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Long id) {
        //调用SeckillOrderService实现根据主键删除
        seckillOrderService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改SeckillOrder数据
     * @param seckillOrder
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody SeckillOrder seckillOrder, @PathVariable Long id) {
        //设置主键值
        seckillOrder.setId(id);
        //调用SeckillOrderService实现修改SeckillOrder
        seckillOrderService.update(seckillOrder);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 新增SeckillOrder数据
     * @param seckillOrder
     * @return
     */
    @PostMapping
    public Result add(@RequestBody SeckillOrder seckillOrder) {
        //调用SeckillOrderService实现添加SeckillOrder
        seckillOrderService.add(seckillOrder);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询SeckillOrder数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SeckillOrder> findById(@PathVariable Long id) {
        //调用SeckillOrderService实现根据主键查询SeckillOrder
        SeckillOrder seckillOrder = seckillOrderService.findById(id);
        return new Result<SeckillOrder>(true, StatusCode.OK, "查询成功", seckillOrder);
    }

    /***
     * 查询SeckillOrder全部数据
     * @return
     */
    @GetMapping
    public Result<List<SeckillOrder>> findAll() {
        //调用SeckillOrderService实现查询所有SeckillOrder
        List<SeckillOrder> list = seckillOrderService.findAll();
        return new Result<List<SeckillOrder>>(true, StatusCode.OK, "查询成功", list);
    }
}
