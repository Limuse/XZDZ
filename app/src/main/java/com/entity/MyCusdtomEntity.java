package com.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by huisou on 2015/11/24.
 */
public class MyCusdtomEntity implements Serializable {


    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }


    public static class ListEntity implements Serializable {
        /**
         * id : 14
         * pid : 0
         * title : 上衣
         * _child : [{"id":"19","pid":"14","title":"版型","_child":""},{"id":"20","pid":"14","title":"面料","_child":""},{"id":"21","pid":"14","title":"细节","_child":""}]
         */

        private String id;
        private String pid;

        public String getFace_pic() {
            return face_pic;
        }

        public void setFace_pic(String face_pic) {
            this.face_pic = face_pic;
        }

        private String title;
        private String face_pic;
        private List<ChildEntity> _child;

        public void setId(String id) {
            this.id = id;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void set_child(List<ChildEntity> _child) {
            this._child = _child;
        }

        public String getId() {
            return id;
        }

        public String getPid() {
            return pid;
        }

        public String getTitle() {
            return title;
        }

        public List<ChildEntity> get_child() {
            return _child;
        }

        public static class ChildEntity implements Serializable {
            /**
             * id : 19
             * pid : 14
             * title : 版型
             * _child :
             */

            private String id;
            private String pid;
            private String title;
            private String face_pic;
            private List<childV> _child;

            public String getFace_pic() {
                return face_pic;
            }

            public void setFace_pic(String face_pic) {
                this.face_pic = face_pic;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public List<childV> get_child() {
                return _child;
            }

            public void set_child(List<childV> _child) {
                this._child = _child;
            }

            public void setTitle(String title) {
                this.title = title;
            }


            public String getId() {
                return id;
            }

            public String getPid() {
                return pid;
            }

            public String getTitle() {
                return title;
            }

            public static class childV implements Serializable {
                /**
                 * id: 6
                 * pid: 4
                 * title: 休闲
                 * face_pic:
                 * _child:
                 */
                private String id;
                private String pid;
                private String title;
                private String face_pic;
                private String _child;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String get_child() {
                    return _child;
                }

                public void set_child(String _child) {
                    this._child = _child;
                }

                public String getFace_pic() {
                    return face_pic;
                }

                public void setFace_pic(String face_pic) {
                    this.face_pic = face_pic;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }


    }
}
