package de.hybris.training.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

public class CustomSearchResultVariantProductPopulator extends SearchResultVariantProductPopulator {
    @Override
    public void populate(SearchResultValueData source, ProductData target) {

        super.populate(source, target);

        target.setQuestionsCount(this.<String> getValue(source, "questionsCount"));
        target.setWarrantyYears(this.<Integer> getValue(source, "warrantyYears"));
    }
}
