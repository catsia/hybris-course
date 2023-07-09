package de.hybris.training.facades.populators;

import com.training.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.training.facades.product.data.QuestionData;
import org.springframework.beans.factory.annotation.Required;



public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData>
{
	private TypeService typeService;

	protected TypeService getTypeService()
	{
		return typeService;
	}

	@Required
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

	@Override
	public void populate(final QuestionModel source, final QuestionData target)
	{
		
	}
}
