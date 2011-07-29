package com.github.yacaqueryservice.example.service;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.github.yacaqueryservice.QueryWebServiceBase;
import com.github.yacaqueryservice.example.Unit;

@XmlSeeAlso(Unit.class)
@WebService
public interface ExampleService extends QueryWebServiceBase {
  
}
