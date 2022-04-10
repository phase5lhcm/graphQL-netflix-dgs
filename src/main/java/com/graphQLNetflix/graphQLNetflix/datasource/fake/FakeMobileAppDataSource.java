package com.graphQLNetflix.graphQLNetflix.datasource.fake;

import com.github.javafaker.Faker;
import com.graphQLNetflix.types.Address;
import com.graphQLNetflix.types.Author;
import com.graphQLNetflix.types.Book;
import com.graphQLNetflix.types.MobileApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataSource {
    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();

    @Autowired
    private Faker faker;

    @PostConstruct
    private void postConstruct() throws MalformedURLException {
        for (int i = 0; i < 20; i++){
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.app().author())
                    .originCountry(faker.app().name())
                    .build();
            var mobileApp= MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author).version(faker.app().version())
                    .platform(randomMobilePlatform())
                    .appId(UUID.randomUUID().toString())
//                    .appID(UUID.randomUUID().toString())
//                    .releaseDate(LocalDate.now().minusDays(faker.random().nextInt(365)))
//                    .downloaded(faker.number().numberBetween(1,1_500_500))
//                    .homepage(new URL("https://" + faker.internet().url()))
//                    .category(MobileAppCategory.values()[faker.random().nextInt(MobileAppCategory.values().length)])
                    .build();
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
            MOBILE_APP_LIST.add(mobileApp);

        }

    }

    /**
     * returns several mobile applications with different platforms for each argument passed
     */
    private List<String> randomMobilePlatform(){
        switch (ThreadLocalRandom.current().nextInt(10)%3){
            case 0:
                return List.of("android");
            case 1:
                return List.of("ios");
            default:
                return List.of("ios", "android");

        }
    }
}
