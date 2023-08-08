package de.hybris.training.core.job;

import de.hybris.platform.cronjob.model.CronJobHistoryModel;
import de.hybris.platform.servicelayer.cronjob.CronJobHistoryService;
import de.hybris.training.core.dao.QuestionsDao;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.training.core.model.NewQuestionsEmailProcessModel;
import de.hybris.training.core.model.NewQuestionsJobPerformableModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewQuestionsJobPerformable extends AbstractJobPerformable<NewQuestionsJobPerformableModel> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;
    private CronJobHistoryService cronJobHistoryService;
    private BaseSiteService baseSiteService;
    private QuestionsDao questionsDao;
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

    @Required
    public void setCronJobHistoryService(final CronJobHistoryService cronJobHistoryService) {
        this.cronJobHistoryService = cronJobHistoryService;
    }

    @Override
    public PerformResult perform(NewQuestionsJobPerformableModel cronJobModel) {
        final NewQuestionsEmailProcessModel newQuestionsProcessModel = (NewQuestionsEmailProcessModel) getBusinessProcessService().createProcess(
                "newQuestionsEmailProcess-" + System.currentTimeMillis(),
                "newQuestionsEmailProcess");

        Date date = getLastCalledTime(cronJobModel);

        setAttributesToProcess(newQuestionsProcessModel, date);

        getModelService().save(newQuestionsProcessModel);
        getBusinessProcessService().startProcess(newQuestionsProcessModel);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private Date getLastCalledTime(NewQuestionsJobPerformableModel cronJobModel) {
        List<CronJobHistoryModel> histories = cronJobHistoryService.getCronJobHistoryBy(cronJobModel.getCode());
        CronJobHistoryModel lastSuccessHistory = histories.stream()
                .filter(history -> history.getStatus() == CronJobStatus.FINISHED)
                .max(Comparator.comparing(CronJobHistoryModel::getStartTime))
                .orElse(null);

        if (lastSuccessHistory != null) {
            return lastSuccessHistory.getEndTime();
        } else {
            return new Date();
        }
    }

    private void setAttributesToProcess(NewQuestionsEmailProcessModel newQuestionsProcessModel, Date date) {
        BaseSiteModel baseSite = baseSiteService.getBaseSiteForUID(SITE_UID);
        newQuestionsProcessModel.setSite(baseSite);
        newQuestionsProcessModel.setLanguage(baseSite.getDefaultLanguage());
        newQuestionsProcessModel.setStore(baseSite.getStores().get(0));
        newQuestionsProcessModel.setDate(date);
        newQuestionsProcessModel.setDisplayName(DISPLAY_NAME);
        newQuestionsProcessModel.setEmail(EMAIL);
    }
}
