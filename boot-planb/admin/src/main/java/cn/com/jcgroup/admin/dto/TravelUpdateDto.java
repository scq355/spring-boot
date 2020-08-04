package cn.com.jcgroup.admin.dto;

/**
 * @author LiuYong on 17/6/25 下午9:46.
 */
public class TravelUpdateDto {
    
    private String[] keyWords;
    
    private String relationId;
    
    private String type;

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
