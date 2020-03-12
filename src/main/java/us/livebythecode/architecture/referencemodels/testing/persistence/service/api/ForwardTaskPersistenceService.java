package us.livebythecode.architecture.referencemodels.testing.persistence.service.api;

import java.util.List;

import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;

public interface ForwardTaskPersistenceService {

	//public List<ForwardTask> findAll();

	public ForwardTask findById(long id);
	
	public ForwardTask createForwardTask(ForwardTask forwardTask);

}