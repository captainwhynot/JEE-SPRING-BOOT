package com.mainapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Test;
import com.mainapp.repository.TestRepository;

@Service
public class TestService {
	
	
	private TestRepository tr;
	
	@Autowired
	public TestService(TestRepository tr) {
		this.tr=tr;
	}
	
	public List<Test> getAllTest(){
		return tr.findAll();
	}
	
	
	public void saveTest(Test test) {
        tr.save(test);
    }
	
	public void deleteTest(int id) {
        tr.deleteById(id);
    }
	
	public Test findById(int id) {
        return tr.testById(id);
    }
	
	public Test findByTestObject(Test test) {
        return tr.findByTestObject(test);
    }
	
	public void updateTest(Test test, double price, String name) {
		tr.updateTest(test, price, name);
	}
	
}
