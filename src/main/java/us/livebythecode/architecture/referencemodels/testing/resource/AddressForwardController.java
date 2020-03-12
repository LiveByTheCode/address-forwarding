package us.livebythecode.architecture.referencemodels.testing.resource;

//import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationRequestDTO;
import us.livebythecode.architecture.referencemodels.testing.address.dto.AddressForwardCreationResponseDTO;
import us.livebythecode.architecture.referencemodels.testing.service.AddressForwardingService;

//import io.quarkus.panache.common.Sort;



@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressForwardController {

	@Inject
	AddressForwardingService addressForwardingService;

//	@GET
//	public List<ForwardTaskEntity> getAll(){
//		return ForwardTaskEntity.listAll(Sort.by("created"));
//	}
	
	@POST
	@Path("/forwardAddress")
	@Transactional
	public AddressForwardCreationResponseDTO getOrderStatus(@Valid AddressForwardCreationRequestDTO forwardTask) {
		return addressForwardingService.save(forwardTask);
		//forwardTask.persist();
		//return Response.status(Response.Status.CREATED).entity(forwardTask).build();
	}
//	
//	@PATCH
//	@Path("/{id}")
//	@Transactional
//	public ForwardTaskEntity update(@Valid ForwardTaskEntity forwardTaskEntity, @PathParam("id") Long id) {
//		ForwardTaskEntity entity = ForwardTaskEntity.findById(id);
//		entity.id = forwardTaskEntity.id;
//		entity.created = forwardTaskEntity.created;
//		entity.fromStreetAddress = forwardTaskEntity.fromStreetAddress;
//		
//		
//		return entity;
//	}
//	
//	@DELETE
//	@Transactional
//	public Response deleteCompleted() {
//		ForwardTaskEntity.deleteCompleted();
//		return Response.noContent().build();
//	}
}