package cn.com.jcgroup.admin.dto;

/**
 * @author LiuYong on 17/6/25 下午10:15.
 */
public class TravelDetailUpdateDto {
    
    private String arriveMapLocation;
    private String departureMapLocation;
    private String id;
    private String type;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
