package cn.com.jcgroup.service.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 出差接待明细
 * @author LiuYong on 17/6/16 上午12:29.
 */
@Entity
@Table(name = "pb_travel_reception_detail")
public class PbTravelReceptionDetail {

    @Id
    @SequenceGenerator(name = "travelReceptionId", sequenceName = "seq_travel_reception_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelReceptionId")
    private int id;
    private String departureLocation;
    private String arriveMapLocation;
    private String departureMapLocation;
    private String arriveLocation;
    private Date startTime;
    private String company;
    @Column(columnDefinition = "number(19, 0)", nullable = true)
    private Long fee;
    private String type;
    private String relationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArriveMapLocation() {
        return arriveMapLocation;
    }

    public void setArriveMapLocation(String arriveMapLocation) {
        this.arriveMapLocation = arriveMapLocation;
    }

    public String getDepartureMapLocation() {
        return departureMapLocation;
    }

    public void setDepartureMapLocation(String departureMapLocation) {
        this.departureMapLocation = departureMapLocation;
    }

    public String getArriveLocation() {
        return arriveLocation;
    }

    public void setArriveLocation(String arriveLocation) {
        this.arriveLocation = arriveLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
