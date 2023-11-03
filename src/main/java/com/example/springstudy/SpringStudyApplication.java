package com.example.springstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class SpringStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }

}

class Coffee {
    private final String id;                           // 최초 생성시 고정하도록 final
    private String name;

    public Coffee(String id, String name){
        this.id = id;
        this.name = name;
    }

    // id 자동생성
    public Coffee(String name){
        this(UUID.randomUUID().toString(),name);        // id를 UUID로 자동생성하여 Coffee( id, name ) 생성자 실행
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
