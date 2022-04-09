package com.graphQLNetflix.graphQLNetflix.datasource.fake;


//import com.github.javafaker.Address;
//import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.graphQLNetflix.types.Author;
import com.graphQLNetflix.types.Book;
import com.graphQLNetflix.types.Address;


import com.graphQLNetflix.types.ReleaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



@Configuration
public class FakeBookDataSource {
    @Autowired
    private Faker faker;

    public static final List<Book> BOOK_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct(){
        for (int i = 0; i< 20; i++){
            ArrayList<Address> addresses = new ArrayList<>();

//            initialize each class (generated from the Schema), and add it to the BOOK_LIST
            var author = Author.newBuilder().addresses(addresses)
            .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var released = ReleaseHistory.newBuilder()
                    .printedEdition(faker.bool().bool())
                    .releasedCountry(faker.country().name())
                    .year(faker.number().numberBetween(2019,2021))
                    .build();
            var book = Book.newBuilder().author(author)
                   .publisher(faker.book().publisher())
                  .title(faker.book().title())
                  .released(released)
                  .build();

// create 3 addresses for each author
           for (int j = 0; j < ThreadLocalRandom.current().nextInt(1,3); j++){
               var address = Address.newBuilder()
                       .country(faker.address().country())
                      .city(faker.address().cityName())
                       .country(faker.address().country())
                      .street(faker.address().streetAddress())
                       .zipCode(faker.address().zipCode())
                     .build();
              addresses.add(address);
           }

           BOOK_LIST.add(book);
        }
    }


}
