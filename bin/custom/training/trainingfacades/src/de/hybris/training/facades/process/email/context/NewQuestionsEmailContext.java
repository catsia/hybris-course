package de.hybris.training.facades.process.email.context;

import com.hybris.training.questions.data.QuestionData;
import com.training.model.QuestionModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.training.core.model.NewQuestionsEmailProcessModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

public class NewQuestionsEmailContext extends AbstractEmailContext<NewQuestionsEmailProcessModel> {
    private List<QuestionData> questionsList;
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Override
    public void init(final NewQuestionsEmailProcessModel processModel, final EmailPageModel emailPageModel) {
        if (processModel instanceof NewQuestionsEmailProcessModel) {
            put(EMAIL, ((NewQuestionsEmailProcessModel) processModel).getEmail());
            put(DISPLAY_NAME, ((NewQuestionsEmailProcessModel) processModel).getDisplayName());
        }
        super.init(processModel, emailPageModel);
        if (processModel instanceof NewQuestionsEmailProcessModel) {
            questionsList = new ArrayList<>();
            for (QuestionModel questionModel: ((NewQuestionsEmailProcessModel) processModel).getQuestions())  {
                questionsList.add(getQuestionConverter().convert(questionModel));
            }
        }
    }

    public List<QuestionData> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<QuestionData> questionsList) {
        this.questionsList = questionsList;
    }


    @Override
    protected BaseSiteModel getSite(NewQuestionsEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(NewQuestionsEmailProcessModel businessProcessModel) {
        return null;
    }

    @Override
    protected LanguageModel getEmailLanguage(NewQuestionsEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }

    protected Converter<QuestionModel, QuestionData> getQuestionConverter()
    {
        return questionConverter;
    }

    @Required
    public void setQuestionConverter(final Converter<QuestionModel, QuestionData> questionConverter)
    {
        this.questionConverter = questionConverter;
    }
}
