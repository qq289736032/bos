package com.jisen.crm.service;

import java.util.List;

import javax.jws.WebService;

import com.jisen.crm.domain.Customer;

@WebService
public interface ICustomerService {
	public List<Customer> findAll();
	
    //查询未关联到定区的客户
    public List<Customer> findListNotAssociation();
    //查询已经关联到指定定区的客户
    public List<Customer> findListHasAssociation(String decidedzoneId);
    //定区关联客户
    public void assigncustomerstodecidedzone(String decidedzoneId ,Integer[] customerIds);
    //根据客户手机号码查询客户信息
    public Customer findCustomerByTelephone(String telephone);
    //根据客户的地址查询定区
    public String findDecidedzoneIdByAddress(String address);
}
