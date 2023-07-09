/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.facades.populators;

import com.training.model.QuestionModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.Gender;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.training.core.model.ApparelProductModel;
import de.hybris.training.facades.product.data.GenderData;

import java.util.ArrayList;
import java.util.List;

import de.hybris.training.facades.product.data.QuestionData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * Populates {@link ProductData} with genders
 */
public class ApparelProductPopulator implements Populator<ProductModel, ProductData>
{
	private Converter<Gender, GenderData> genderConverter;

	protected Converter<Gender, GenderData> getGenderConverter()
	{
		return genderConverter;
	}

	private Converter<QuestionModel, QuestionData> questionConverter;

	protected Converter<QuestionModel, QuestionData> getQuestionConverter()
	{
		return questionConverter;
	}

	@Required
	public void setGenderConverter(final Converter<Gender, GenderData> genderConverter)
	{
		this.genderConverter = genderConverter;
	}

	@Required
	public void setQuestionConverter(final Converter<QuestionModel, QuestionData> questionConverter)
	{
		this.questionConverter = questionConverter;
	}

	@Override
	public void populate(final ProductModel source, final ProductData target) throws ConversionException
	{
		final ProductModel baseProduct = getBaseProduct(source);

		if (baseProduct instanceof ApparelProductModel)
		{
			final ApparelProductModel apparelProductModel = (ApparelProductModel) baseProduct;
			if (CollectionUtils.isNotEmpty(apparelProductModel.getGenders()))
			{
				final List<GenderData> genders = new ArrayList<GenderData>();
				for (final Gender gender : apparelProductModel.getGenders())
				{
					genders.add(getGenderConverter().convert(gender));
				}
				target.setGenders(genders);
			}
			if (CollectionUtils.isNotEmpty(apparelProductModel.getQuestion()))
			{
				final List<QuestionData> questions = new ArrayList<QuestionData>();
				for (final QuestionModel questionModel : apparelProductModel.getQuestion())
				{
					questions.add(getQuestionConverter().convert(questionModel));
				}
				target.setQuestions(questions);
			}
		}
	}

	protected ProductModel getBaseProduct(final ProductModel productModel)
	{
		ProductModel currentProduct = productModel;
		while (currentProduct instanceof VariantProductModel)
		{
			final VariantProductModel variant = (VariantProductModel) currentProduct;
			currentProduct = variant.getBaseProduct();
		}
		return currentProduct;
	}
}
