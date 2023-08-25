package com.owen.Service;

import com.github.pagehelper.PageInfo;
import com.owen.pojo.ProductInfo;
import com.owen.pojo.vo.productInfoVo;

import java.util.List;

public interface ProductInfoService {

    //获取所有商品
    public List<ProductInfo> getAll();

    //分页功能的实现
    public PageInfo spiltPage(int pageNum,int pageSize);

    int save(ProductInfo info);

    //按照主键查询
    ProductInfo getById(int pId);


    //更新商品
    int updateProduct(ProductInfo info);

    //单个商品的删除
    int delete(int pid);


    //批量删除
    int deleteBatch(String []ids);


    //多条件商品查询
    List<ProductInfo> selectByMulCon(productInfoVo productInfoVo);


    //多条件查询分页
    PageInfo spiltAndMulCon(productInfoVo productInfoVo,Integer pageSize);
}
