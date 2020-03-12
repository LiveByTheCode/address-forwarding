package us.livebythecode.architecture.referencemodels.testing.persistence.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import us.livebythecode.architecture.referencemodels.testing.persistence.entity.ForwardTaskEntity;

@ApplicationScoped
public class ForwardTaskRepository implements PanacheRepositoryBase<ForwardTaskEntity, Long>{
	@Transactional
	public ForwardTaskEntity save(ForwardTaskEntity forwardTaskEntity) {
	    EntityManager em = JpaOperations.getEntityManager();
	    if (forwardTaskEntity.id == null) {
	        em.persist(forwardTaskEntity);
	        return forwardTaskEntity;
	    } else {
	        return em.merge(forwardTaskEntity);
	    }
	}
}
