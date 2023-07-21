package de.hybris.training.core.dao.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.training.core.dao.LastTimeCalledDao;
import de.hybris.training.core.model.LastTimeCalledModel;
import org.springframework.beans.factory.annotation.Required;


import java.util.List;

public class DefaultLastTimeCalledDao implements LastTimeCalledDao {

    private FlexibleSearchService flexibleSearchService;


    @Override
    public List<LastTimeCalledModel> getLastCalledTime() {
        final String queryString = //
                "SELECT {p:" + LastTimeCalledModel.PK + "} "//
                        + "FROM {" + LastTimeCalledModel._TYPECODE + " AS p} ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<LastTimeCalledModel>search(query).getResult();
    }

    @Required
    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

}
