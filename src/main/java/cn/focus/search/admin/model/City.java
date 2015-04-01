package cn.focus.search.admin.model;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

@Repository(value = "city")
public class City implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3803126666013842618L;
    private long id;
    private long cityId;
    private String cityName;
    private String cityJianpin;
    private Date createTime;
    private Date modifiedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityJianpin() {
        return cityJianpin;
    }

    public void setCityJianpin(String cityJianpin) {
        this.cityJianpin = cityJianpin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", cityJianpin='" + cityJianpin + '\'' +
                '}';
    }
}
