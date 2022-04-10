package com.graphQLNetflix.graphQLNetflix.component.fake;

import com.graphQLNetflix.DgsConstants;
import com.graphQLNetflix.graphQLNetflix.datasource.fake.FakeMobileAppDataSource;
import com.graphQLNetflix.types.MobileApp;
import com.graphQLNetflix.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeMobileAppDataResolver {
    @DgsQuery(field = DgsConstants.QUERY.MobileApps)
            public List<MobileApp> getMobileApps(@InputArgument(name="mobileAppFilter", collectionType = MobileAppFilter.class)
                                                             Optional<MobileAppFilter> filter) {
        if(filter.isEmpty()){
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
    }
        return FakeMobileAppDataSource.MOBILE_APP_LIST.stream().filter(
                mobileApp -> this.matchFilter(filter.get(), mobileApp)
        ).collect(Collectors.toList());
}

private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp){
        // does mobile application field match with filter?
    var isAppMatch = StringUtils.containsIgnoreCase(mobileApp.getName(), StringUtils.defaultIfBlank(mobileAppFilter.getName(), StringUtils.EMPTY))
            && StringUtils.containsAnyIgnoreCase(mobileApp.getVersion(), StringUtils.defaultIfBlank(mobileAppFilter.getVersion(),StringUtils.EMPTY));

    if(!isAppMatch){
        return false;
    }

    if (StringUtils.isNotBlank(mobileAppFilter.getPlatform())
    && !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform().toLowerCase())){
        return false;
    }  if (StringUtils.isNotBlank(mobileAppFilter.getPlatform())
    && !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform().toLowerCase())){
        return false;
    }
    if (mobileAppFilter.getAuthor() != null
            && !StringUtils.containsIgnoreCase(mobileApp.getAuthor().getName(),
            StringUtils.defaultIfBlank(mobileAppFilter.getAuthor().getName(), StringUtils.EMPTY))
    ) {

        return false;
    }
    return true;

}
}
