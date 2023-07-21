package de.hybris.training.core.dao;

import com.training.model.QuestionModel;

import java.util.Date;
import java.util.List;

public interface QuestionsDao {
    List<QuestionModel> getQuestionsAddedAfterDate(Date date);
}
