package com.graphQLNetflix.graphQLNetflix.component.fake;

import com.graphQLNetflix.DgsConstants;
import com.graphQLNetflix.graphQLNetflix.datasource.fake.FakeBookDataSource;
import com.graphQLNetflix.types.Book;
import com.graphQLNetflix.types.ReleaseHistory;
import com.graphQLNetflix.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * returns a list of books and accepts string as argument
 */
@DgsComponent
public class FakeBookDataResolver {

    // rename the schema property author to authorName
    @DgsQuery(field = "books")
    public  List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String>  authorName){
        if (authorName.isEmpty() || StringUtils.isAllBlank((authorName.get()))){
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> StringUtils.containsAnyIgnoreCase(
                        b.getAuthor().getName(), authorName.get()
                )).collect(Collectors.toList());
    }
//copied from build/generated-examples
@DgsQuery(field = DgsConstants.QUERY.BooksByReleased) // using Dgs constants instead of hard coded strings allows for dynamity
public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
           var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
           var releasedInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                   .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                   .build();
        return FakeBookDataSource.BOOK_LIST.stream().filter(
                b -> this.matchReleaseHistory(releasedInput, b.getReleased())
        ).collect(Collectors.toList());
}

    /**
     * compares releaseInput with releaseObject and return True if objects are the same
     */
    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element){
        return input.getPrintedEdition().equals(element.getPrintedEdition())
                && input.getYear() == element.getYear();
    }
}
