package com.yarns.december.support.constant;

/**
 * 系统常量
 *
 * @author Yarns
 */
public interface Constant {

    /**
     * 基础用户角色ID
     */
    Long BASIC_ROLE_ID = 1L;

    /**
     * 超管
     */
    String ADMIN_ROLE = "admin";

    /**
     * 基础密码
     */
    String BASE_PASS = "111111";

    /**
     * redis存储授权码 key
     */
    String AUTHORIZATION_CODE = "authorization:code:";

    /**
     * 异步线程池名称
     */
    String ASYNC_POOL = "AsyncThreadPool";

    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    String OAUTH2_TOKEN_TYPE = "bearer";

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "descending";
    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "ascending";


    /**
     * Java默认临时目录
     */
    String JAVA_TEMP_DIR = "java.io.tmpdir";
    /**
     * utf-8
     */
    String UTF8 = "utf-8";

    String LOCALHOST = "localhost";
    String LOCALHOST_IP = "127.0.0.1";
    interface Redis {
        /**
         * 默认缓存过期时间（秒） 全局默认30分钟
         */
        long EXPIRE_TIME = 60 * 30;
        /**
         * 验证码 key前缀
         */
        String CODE_PREFIX = "captcha.";

    }

    interface FileConstant {
        /**
         * gif类型
         */
        String GIF = "gif";
        /**
         * png类型
         */
        String PNG = "png";

        /**
         * 算术类型
         */
        String ARITHMETIC = "Arithmetic";

        /**
         * 图片存放的路径
         */
        String STATIC_IMAGES_FOLDER = "images";

        /**
         * 视频存放的路径
         */
        String STATIC_VIDEOS_FOLDER = "videos";

        /**
         * 音频存放的路径
         */
        String STATIC_AUDIOS_FOLDER = "audios";

        /**
         * office存放的路径
         */
        String STATIC_OFFICES_FOLDER = "offices";

        /**
         * 其他文件存放的路径
         */
        String OTHERS_FILES_FOLDER = "others";
        /**
         * 图片后缀范围, 正则表达式, 必须小写
         */
        String IMAGE_SUFFIXES_SCOPE = "jpg|gif|png|jpeg";

        /**
         * 视频后缀范围, 正则表达式, 必须小写
         */
        String VIDEO_SUFFIXES_SCOPE = "mp4|flv|f4v|webm|m3u8|rmvb|avi|3gp|rm";

        /**
         * 音频后缀范围, 正则表达式, 必须小写
         */
        String AUDIO_SUFFIXES_SCOPE = "mp3|wav|ogg|flac|m4a|mp4|aac|wma";

        /**
         * office后缀范围, 正则表达式, 必须小写
         */
        String OFFICE_SUFFIXES_SCOPE = "doc|docx|ppt|pptx|xls|xlsx|pdf";

        /**
         * 压缩文件后缀范围, 正则表达式, 必须小写
         */
        String THUMB_SUFFIXES_SCOPE = "zip|rar|gzip|jar|7z";

        /**
         * 允许下载的文件类型，根据需求自己添加（小写）
         */
        String[] VALID_FILE_TYPE = {"xlsx", "zip"};
    }


}
