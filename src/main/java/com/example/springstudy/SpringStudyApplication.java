package com.example.springstudy;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

@RestController
@RequestMapping("/")
class RestApiDemoController {

    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        coffees.addAll(List.of(
                new Coffee("Cafe Latte"),
                new Coffee("Americano"),
                new Coffee("Espresso"),
                new Coffee("Cafe Moca")
        ));
    }

    // ./coffees : 커피목록 출력
    @GetMapping("/coffees")
    Iterable<Coffee> getCoffees(){
        return coffees;
    }

    // ./{id} : 커피찾기
    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
        for (Coffee c : coffees) {
            if(c.getId().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee){
        coffees.add(coffee);
        return coffee;
    }

    @PutMapping("coffees/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        // id가 있으면 Update 하고, 없으면 추가 한다
        int coffeeIndex = -1;

        for(Coffee c : coffees){
            if(c.getId().equals(id)){
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex,coffee);
            }
        }

        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id){
        // id 일치하면 리스트에서 삭제
        coffees.removeIf(c->c.getId().equals(id));
    }
}

