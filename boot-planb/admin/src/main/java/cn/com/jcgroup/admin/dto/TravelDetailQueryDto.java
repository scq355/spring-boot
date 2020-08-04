package cn.com.jcgroup.admin.dto;

/**
 * @author LiuYong on 17/6/25 下午10:17.
 */
public class TravelDetailQueryDto {
    
    private String arriveLocation;
    private String arriveMapLocation;
    private String departureLocation;
    private String departureMapLocation;
    private String fee;
    private String id;

    public String getArriveLocation() {
        return arriveLocation;
    }

    public void setArriveLocation(String arriveLocation) {
        this.arriveLocation = arriveLocation;
    }

    public String getArriveMapLocation() {
        return arriveMapLocation;
    }

    public void setArriveMapLocation(String arriveMapLocation) {
        this.arriveMapLocation = arriveMapLocation;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDepartureMapLocation() {
        return departureMapLocation;
    }

    public void setDepartureMapLocation(String departureMapLocation) {
        this.departureMapLocation = departureMapLocation;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
