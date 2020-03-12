package us.livebythecode.architecture.referencemodels.testing.persistence.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;
import us.livebythecode.architecture.referencemodels.testing.persistence.entity.ForwardTaskEntity;
import us.livebythecode.architecture.referencemodels.testing.persistence.mapper.api.Mapper;
import us.livebythecode.architecture.referencemodels.testing.persistence.repo.ForwardTaskRepository;
import us.livebythecode.architecture.referencemodels.testing.persistence.service.api.ForwardTaskPersistenceService;

@ApplicationScoped
public class ForwardTaskPersistenceServiceImpl implements ForwardTaskPersistenceService {
	
	@Inject
	private ForwardTaskRepository forwardTaskRepo;

	@Inject
	private Mapper <ForwardTask, ForwardTaskEntity> forwardTaskMapper;

//	@Override
//	public List<ForwardTask> findAll(){
//		return forwardTaskMapper.mapListToDomain(forwardTaskRepo.findAll());
//	}
	
	@Override
	public ForwardTask findById(long id){
		return forwardTaskMapper.mapToDomain(forwardTaskRepo.findById(id));
	}
	
	@Override
	public ForwardTask createForwardTask(ForwardTask forwardTask){
		return forwardTaskMapper.mapToDomain(forwardTaskRepo.save(forwardTaskMapper.mapFromDomain(forwardTask)));
	}

}
