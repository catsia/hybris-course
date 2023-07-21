package de.hybris.training.core.dao;

import de.hybris.training.core.model.LastTimeCalledModel;

import java.util.List;

public interface LastTimeCalledDao {
    List<LastTimeCalledModel> getLastCalledTime();
}
