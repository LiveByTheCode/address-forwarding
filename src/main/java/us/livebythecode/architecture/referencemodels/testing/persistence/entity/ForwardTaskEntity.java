package us.livebythecode.architecture.referencemodels.testing.persistence.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Page;



@Entity(name = "forward_task")
public class ForwardTaskEntity extends PanacheEntityBase{
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;
    public String fromStreetAddress;
	public String fromCity;
	public String fromState;
	public String fromPostalCode;
	public String fromAddressType;
	public int fromAddressScore;
	//@Column(length=1000, columnDefinition="blob")
	//public Point fromAddressGeometry;
	public double fromAddressX;
	public double fromAddressY;
	
	public String toStreetAddress;
	public String toCity;
	public String toState;
	public String toPostalCode;
	public String toAddressType;
	public int toAddressScore;
	//@Column(length=1000, columnDefinition="blob")
	//@Type(type = "jts_geometry")
	//public Point toAddressGeometry;
	public double toAddressX;
	public double toAddressY;
	
	public Date created;
	public Date startDate;
	public Date endDate;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}
	
	public static void deleteCompleted() {
		//hql (jpql)
		delete("completed", true);
	}
	public List<ForwardTaskEntity> search(String word, Page page){
		return find("work like ?1 and completed = ?2", word, false).page(page).list();
	}
	
	public String getOldSingleLineAddress() {
		return fromStreetAddress + " " + fromCity + " " + fromState + " " + fromPostalCode;
	}
	
	public String getNewSingleLineAddress() {
		return toStreetAddress + " " + toCity + " " + toState + " " + toPostalCode;
	}

}
