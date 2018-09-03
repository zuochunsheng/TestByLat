package com.android.supermarket.service.entity;

import java.util.List;

/**
 * anther: created by zuochunsheng on 2018/8/31 17 : 28
 * descript :
 */
public class BannerListBean {

    /**
     * code : 0
     * msg : string
     * data : [{"title":"string","discription":"string","picUrl":"string","href":"string","category":"string","id":"string","createdBy":"string","updatedBy":"string","deletedBy":"string","createdTime":"2018-08-31T06:21:29.905Z","updatedTime":"2018-08-31T06:21:29.905Z","deletedTime":"2018-08-31T06:21:29.905Z","isDeleted":0,"keywords":"string","description":"string","sort":"string","isTop":0,"isOff":0}]
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
         * discription : string
         * picUrl : string
         * href : string
         * category : string
         * id : string
         * createdBy : string
         * updatedBy : string
         * deletedBy : string
         * createdTime : 2018-08-31T06:21:29.905Z
         * updatedTime : 2018-08-31T06:21:29.905Z
         * deletedTime : 2018-08-31T06:21:29.905Z
         * isDeleted : 0
         * keywords : string
         * description : string
         * sort : string
         * isTop : 0
         * isOff : 0
         */

        private String title;
        private String discription;
        private String picUrl;
        private String href;
        private String category;
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

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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
