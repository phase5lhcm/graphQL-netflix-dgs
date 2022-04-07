package com.graphQLNetflix.graphQLNetflix.datasource.fake;


import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.graphQLNetflix.types.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class FakeBookDataSource {
    @Autowired
    private Faker faker;

    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct(){
        for (int i = 0; i< 20; i++){
            var hello = Hello.newBuilder()
                    .randomNumber(faker.random().nextInt(5000))
                    .text(faker.company().name())
                    .build();
            HELLO_LIST.add(hello);
        }
    }


}