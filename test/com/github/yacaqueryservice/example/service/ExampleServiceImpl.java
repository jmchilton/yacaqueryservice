package com.github.yacaqueryservice.example.service;

import javax.jws.WebService;

import com.github.yacaqueryservice.QueryWebServiceImpl;

                                 
@WebService(endpointInterface = "com.github.yacaqueryservice.example.service.ExampleService", serviceName = "ExampleService")
public class ExampleServiceImpl extends QueryWebServiceImpl implements ExampleService  {

}
