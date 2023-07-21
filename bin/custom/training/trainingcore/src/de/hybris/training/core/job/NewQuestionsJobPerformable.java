package de.hybris.training.core.job;

import com.training.model.QuestionModel;
import de.hybris.training.core.dao.LastTimeCalledDao;
import de.hybris.training.core.dao.QuestionsDao;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.training.core.model.LastTimeCalledModel;
import de.hybris.training.core.model.NewQuestionsEmailProcessModel;
import de.hybris.training.core.model.NewQuestionsJobPerformableModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;
import java.util.List;

public class NewQuestionsJobPerformable extends AbstractJobPerformable<NewQuestionsJobPerformableModel> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;

    private BaseSiteService baseSiteService;
    private QuestionsDao questionsDao;
    private LastTimeCalledDao lastTimeCalledDao;
    private final String SITE_UID = "apparel-uk";

    private final String EMAIL = "k.l@mail.by";

    private final String DISPLAY_NAME = "display name";


    protected QuestionsDao getQuestionsDao() {
        return questionsDao;
    }

    @Required
    public void setQuestionsDao(final QuestionsDao questionsDao) {
        this.questionsDao = questionsDao;
    }

    protected LastTimeCalledDao getLastTimeCalledDao() {
        return lastTimeCalledDao;
    }

    @Required
    public void setLastTimeCalledDao(final LastTimeCalledDao lastTimeCalledDao) {
        this.lastTimeCalledDao = lastTimeCalledDao;
    }

    protected BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    @Required
    public void setBusinessProcessService(final BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    protected ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setBaseSiteService(final BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    protected BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    @Required
    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public PerformResult perform(NewQuestionsJobPerformableModel cronJobModel) {
        final NewQuestionsEmailProcessModel newQuestionsProcessModel = (NewQuestionsEmailProcessModel) getBusinessProcessService().createProcess(
                "newQuestionsEmailProcess-" + System.currentTimeMillis(),
                "newQuestionsEmailProcess");

        BaseSiteModel baseSite = baseSiteService.getBaseSiteForUID(SITE_UID);
        newQuestionsProcessModel.setSite(baseSite);
        newQuestionsProcessModel.setLanguage(baseSite.getDefaultLanguage());
        newQuestionsProcessModel.setStore(baseSite.getStores().get(0));

        List<LastTimeCalledModel> lastTimeCalledList = lastTimeCalledDao.getLastCalledTime();
        List<QuestionModel> questionModelList;
        LastTimeCalledModel lastTimeCalledModel;
        if (lastTimeCalledList.isEmpty()) {
            lastTimeCalledModel = modelService.create(LastTimeCalledModel.class);
            lastTimeCalledModel.setCode("001");
            questionModelList = questionsDao.getQuestionsAddedAfterDate(new Date());
        } else {
            lastTimeCalledModel = lastTimeCalledList.get(0);
            questionModelList = questionsDao.getQuestionsAddedAfterDate(lastTimeCalledModel.getDate());
        }
        newQuestionsProcessModel.setQuestions(questionModelList);

        newQuestionsProcessModel.setDisplayName(DISPLAY_NAME);
        newQuestionsProcessModel.setEmail(EMAIL);

        getModelService().save(newQuestionsProcessModel);
        getBusinessProcessService().startProcess(newQuestionsProcessModel);

        Date date = new Date();
        lastTimeCalledModel.setDate(date);
        modelService.save(lastTimeCalledModel);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
