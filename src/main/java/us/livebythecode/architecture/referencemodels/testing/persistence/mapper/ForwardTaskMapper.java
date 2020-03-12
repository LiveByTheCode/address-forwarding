package us.livebythecode.architecture.referencemodels.testing.persistence.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import us.livebythecode.architecture.referencemodels.testing.domain.Address;
import us.livebythecode.architecture.referencemodels.testing.domain.ForwardTask;
import us.livebythecode.architecture.referencemodels.testing.domain.utility.DateUtility;
import us.livebythecode.architecture.referencemodels.testing.persistence.entity.ForwardTaskEntity;
import us.livebythecode.architecture.referencemodels.testing.persistence.mapper.api.Mapper;

@ApplicationScoped
public class ForwardTaskMapper implements Mapper<ForwardTask, ForwardTaskEntity> {

	@Inject
	DateUtility dateUtility;
	
	public ForwardTask mapToDomain(ForwardTaskEntity entity) {
		ForwardTask response = new ForwardTask();
		response.setId(entity.id);
		Address fromAddress = new Address();
		fromAddress.setStreetAddress(entity.fromStreetAddress);
		fromAddress.setCity(entity.fromCity);
		fromAddress.setState(entity.fromState);
		fromAddress.setPostalCode(entity.fromPostalCode);
		fromAddress.setAddressType(entity.fromAddressType);
		fromAddress.setScore(entity.fromAddressScore);
		//fromAddress.setX(entity.fromAddressGeometry.getX());
		//fromAddress.setY(entity.fromAddressGeometry.getY());
		fromAddress.setX(entity.fromAddressX);
		fromAddress.setY(entity.fromAddressY);
		response.setFromAddress(fromAddress);
		Address toAddress = new Address();
		toAddress.setStreetAddress(entity.toStreetAddress);
		toAddress.setCity(entity.toCity);
		toAddress.setState(entity.toState);
		toAddress.setPostalCode(entity.toPostalCode);
		toAddress.setAddressType(entity.toAddressType);
		toAddress.setScore(entity.toAddressScore);
		toAddress.setX(entity.toAddressX);
		toAddress.setY(entity.toAddressY);
		response.setToAddress(toAddress);
		response.setStart(dateToLocalDate(entity.startDate));
		response.setEnd(dateToLocalDate(entity.endDate));
		response.setForwardDaysRemaining(dateUtility.daysRemaining(dateToLocalDate(entity.startDate), dateToLocalDate(entity.endDate)));
		response.setCreated(dateToLocalDate(entity.created));
		return response;
	}
	
	private LocalDate dateToLocalDate(Date date) {
		return date != null ? new Date(date.getTime()).toInstant().atZone(TimeZone.getTimeZone("America/New_York").toZoneId()).toLocalDate() : null;
	}
	
	public List<ForwardTask> mapListToDomain(List<ForwardTaskEntity> entities) {
		List<ForwardTask> responseList = new ArrayList<>();
		for (ForwardTaskEntity entity : entities) {
			ForwardTask response = mapToDomain(entity);
			responseList.add(response);
		}
		return responseList;
	}

	@Override
	public ForwardTaskEntity mapFromDomain(ForwardTask forwardTask) {
		
		new GeometryFactory().createPoint(new Coordinate(forwardTask.getFromAddress().getX(), forwardTask.getFromAddress().getY()));
		
		ForwardTaskEntity response = new ForwardTaskEntity();
		response.id = forwardTask.getId();
		response.fromStreetAddress = forwardTask.getFromAddress().getStreetAddress();
		response.fromCity = forwardTask.getFromAddress().getCity();
		response.fromState = forwardTask.getFromAddress().getState();
		response.fromPostalCode = forwardTask.getFromAddress().getPostalCode();
		response.fromAddressType = forwardTask.getFromAddress().getAddressType();
		response.fromAddressScore = forwardTask.getFromAddress().getScore();
		//response.fromAddressGeometry = new GeometryFactory().createPoint(new Coordinate(forwardTask.getFromAddress().getX(), forwardTask.getFromAddress().getY()));
		response.fromAddressX = forwardTask.getFromAddress().getX();
		response.fromAddressY = forwardTask.getFromAddress().getY();
		response.toStreetAddress = forwardTask.getToAddress().getStreetAddress();
		response.toCity = forwardTask.getToAddress().getCity();
		response.toState = forwardTask.getToAddress().getState();
		response.toPostalCode = forwardTask.getToAddress().getPostalCode();
		response.toAddressType = forwardTask.getToAddress().getAddressType();
		response.toAddressScore = forwardTask.getToAddress().getScore();
		//response.toAddressGeometry = new GeometryFactory().createPoint(new Coordinate(forwardTask.getToAddress().getX(), forwardTask.getToAddress().getY()));
		response.toAddressX = forwardTask.getToAddress().getX();
		response.toAddressY = forwardTask.getToAddress().getY();
		response.startDate = localDateToDate(forwardTask.getStart());
		response.endDate = localDateToDate(forwardTask.getEnd());
		response.created = localDateToDate(forwardTask.getCreated());
		return response;
	}
	
	private Date localDateToDate(LocalDate date) {
		return date != null ? java.sql.Date.valueOf(date) : null;
	}
	
}
