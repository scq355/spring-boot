package cn.com.jcgroup.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 资产组成
 *
 * @author LiuYong on 17/5/27 下午11:51.
 */
@Entity
@Table(name = "pb_asset_composition")
public class PbAssetComposition {

    @Id
    @SequenceGenerator(name = "assetCompositionId", sequenceName = "seq_asset_composition_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetCompositionId")
    private int id;
    @Temporal(TemporalType.DATE)
    private Date recordTime;
    private String proCode;
    private String assetType;
    private long assetValue;

    public PbAssetComposition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public long getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(long assetValue) {
        this.assetValue = assetValue;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}
