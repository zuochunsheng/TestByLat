package com.android.superplayer.service.entity;

import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/9/3 10 : 57
 * descript :
 */
public class NoticeListBean {

    /**
     * code : 0
     * msg : string
     * data : [{"title":"string","details":"string","category":0,"assertId":"string","userId":"string","id":"string","createdBy":"string","updatedBy":"string","deletedBy":"string","createdTime":"2018-09-03T01:46:29.235Z","updatedTime":"2018-09-03T01:46:29.235Z","deletedTime":"2018-09-03T01:46:29.235Z","isDeleted":0,"keywords":"string","description":"string","sort":"string","isTop":0,"isOff":0}]
     * version : string
     */

    private int code;
    private String msg;
    private String version;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : string
         * details : string
         * category : 0
         * assertId : string
         * userId : string
         * id : string
         * createdBy : string
         * updatedBy : string
         * deletedBy : string
         * createdTime : 2018-09-03T01:46:29.235Z
         * updatedTime : 2018-09-03T01:46:29.235Z
         * deletedTime : 2018-09-03T01:46:29.235Z
         * isDeleted : 0
         * keywords : string
         * description : string
         * sort : string
         * isTop : 0
         * isOff : 0
         */

        private String title;
        private String details;
        private int category;
        private String assertId;
        private String userId;
        private String id;
        private String createdBy;
        private String updatedBy;
        private String deletedBy;
        private String createdTime;
        private String updatedTime;
        private String deletedTime;
        private int isDeleted;
        private String keywords;
        private String description;
        private String sort;
        private int isTop;
        private int isOff;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getAssertId() {
            return assertId;
        }

        public void setAssertId(String assertId) {
            this.assertId = assertId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getDeletedBy() {
            return deletedBy;
        }

        public void setDeletedBy(String deletedBy) {
            this.deletedBy = deletedBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public String getDeletedTime() {
            return deletedTime;
        }

        public void setDeletedTime(String deletedTime) {
            this.deletedTime = deletedTime;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public int getIsOff() {
            return isOff;
        }

        public void setIsOff(int isOff) {
            this.isOff = isOff;
        }
    }
}
