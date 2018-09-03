package com.android.supermarket.service.entity;

/**
 * anther: created by zuochunsheng on 2018/8/31 11 : 51
 * descript : 返回全部 -- 登陆
 * 登陆 ，注册
 */
public class LoginBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"token":null,"userId":null,"status":0,"id":null,"createdBy":"","updatedBy":"","deletedBy":"","createdTime":null,"updatedTime":null,"deletedTime":null,"isDeleted":0,"keywords":"","description":"","sort":"","isTop":0,"isOff":0}
     * version : 1.0
     */

    private int code;
    private String msg;
    private DataBean data;
    private String version;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        /**
         * token : null
         * userId : null
         * status : 0
         * id : null
         * createdBy :
         * updatedBy :
         * deletedBy :
         * createdTime : null
         * updatedTime : null
         * deletedTime : null
         * isDeleted : 0
         * keywords :
         * description :
         * sort :
         * isTop : 0
         * isOff : 0
         */

        private String token;
        private String userId;
        private int status;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public Object getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public Object getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public Object getDeletedTime() {
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
