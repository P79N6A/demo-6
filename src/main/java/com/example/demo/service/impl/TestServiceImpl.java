package com.example.demo.service.impl;

import com.example.demo.service.TestService;
import com.example.demo.store.domain.Test;
import com.example.demo.store.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> getTestList() {
        return testMapper.selectAll();
    }
}
