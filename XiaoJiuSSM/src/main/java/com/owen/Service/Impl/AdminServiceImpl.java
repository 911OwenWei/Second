package com.owen.Service.Impl;

import com.owen.Service.AdminService;
import com.owen.mapper.AdminMapper;
import com.owen.pojo.Admin;
import com.owen.pojo.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin Login(String name, String pwd) {
        AdminExample example=new AdminExample();
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);

        if (list.size()>0) {
            Admin admin=list.get(0);
            if (pwd.equals(admin.getaPass())) {
                return admin;
            }
        }
        return null;
    }
}
