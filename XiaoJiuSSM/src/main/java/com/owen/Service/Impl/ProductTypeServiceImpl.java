package com.owen.Service.Impl;

import com.owen.Service.ProductTypeService;
import com.owen.mapper.ProductTypeMapper;
import com.owen.pojo.ProductType;
import com.owen.pojo.ProductTypeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper typeMapper;

    @Override
    public List<ProductType> getAll() {
        List<ProductType> list = typeMapper.selectByExample(new ProductTypeExample());
        return list;
    }
}
