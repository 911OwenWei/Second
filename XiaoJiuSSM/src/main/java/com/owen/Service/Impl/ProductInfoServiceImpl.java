package com.owen.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.owen.Service.ProductInfoService;
import com.owen.mapper.ProductInfoMapper;
import com.owen.pojo.ProductInfo;
import com.owen.pojo.ProductInfoExample;
import com.owen.pojo.vo.productInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper infoMapper;
    @Override
    public List<ProductInfo> getAll() {
        return infoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo spiltPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");

        List<ProductInfo> list = infoMapper.selectByExample(example);

        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return infoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pId) {
        return infoMapper.selectByPrimaryKey(pId);
    }

    @Override
    public int updateProduct(ProductInfo info) {
        return infoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return infoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return infoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectByMulCon(productInfoVo productInfoVo) {
        return infoMapper.selectCondition(productInfoVo);
    }

    @Override
    public PageInfo<ProductInfo> spiltAndMulCon(productInfoVo productInfoVo,Integer pageSize) {

        PageHelper.startPage(productInfoVo.getPage(),pageSize);
        List<ProductInfo> list = infoMapper.selectCondition(productInfoVo);
        return new PageInfo<>(list);
    }
}
