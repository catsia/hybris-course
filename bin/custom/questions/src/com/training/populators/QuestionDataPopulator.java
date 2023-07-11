package com.training.populators;

import com.training.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.type.TypeService;
import com.hybris.training.questions.data.QuestionData;
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
        target.setCode(source.getCode());
        target.setQuestion(source.getQuestion());
        target.setQuestionCustomer(source.getQuestionCustomer().getOriginalUid());
        target.setAnswer(source.getAnswer());
        target.setAnswerCustomer(source.getAnswerCustomer().getOriginalUid());
    }
}
