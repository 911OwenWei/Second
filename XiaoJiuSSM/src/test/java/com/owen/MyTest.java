package com.owen;

import com.owen.mapper.ProductInfoMapper;
import com.owen.pojo.ProductInfo;
import com.owen.pojo.vo.productInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_dao.xml","classpath:applicationContext_Service.xml"})
public class MyTest {

    @Autowired
    ProductInfoMapper infoMapper;


    @Test
    public void selectAll(){
        productInfoVo vo = new productInfoVo();
        vo.setPname("小米");
//        vo.setTypeid(3);
        vo.setLprice(2000.0);
        vo.setHprice(5000.0);
        List<ProductInfo> list = infoMapper.selectCondition(vo);

        for (ProductInfo p:list){
            System.out.println(p);
        }
    }
}
