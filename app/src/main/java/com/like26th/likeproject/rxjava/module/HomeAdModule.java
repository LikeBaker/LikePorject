package com.like26th.likeproject.rxjava.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class HomeAdModule implements Serializable{

    /**
     * code : 0
     * msg : 成功了
     * data : [{"id":"6","title":"这是一个图片","url":"http:%/%/www.baidu.com","thumb":"http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png"},{"id":"7","title":"1","url":"http:%/%/www.baidu.com","thumb":"http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png"},{"id":"8","title":"这是一个图片","url":"http:%/%/www.baidu.com","thumb":"http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png"},{"id":"9","title":"这是一个图片","url":"http:%/%/www.baidu.com","thumb":"http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png"},{"id":"10","title":"这是一个图片","url":"http:%/%/www.baidu.com","thumb":"http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 6
         * title : 这是一个图片
         * url : http:%/%/www.baidu.com
         * thumb : http:%/%/192.168.1.206%/uploads%/20170216%/dXNlclVwbG9hZDE0ODcyMzAxNDIyNzcyMTA3MzU=.png
         */

        private String id;
        private String title;
        private String url;
        private String thumb;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
