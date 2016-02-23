package ww.com.http;

import com.squareup.okhttp.MediaType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxParams {

    // private MultipartBuilder builder;
    private List<BaseParams> baseParams;
    private List<FileParams> fileParams;
    private Map<String, String> headers;
    private byte[] bufs;

    public AjaxParams() {
        headers = new HashMap<>();
    }

    public AjaxParams addParameters(String _key, String _value) {
        if (baseParams == null) {
            baseParams = new ArrayList<>();
        }
        if (_value != null) {
            baseParams.add(new BaseParams(_key, _value));
        }
        return this;
    }

    public AjaxParams addParametersFile(String _key, File _file, String mimeType) {
        // builder.addFormDataPart(_key, _file.getName(),
        // RequestBody.create(MediaType.parse(mimeType), _file));
        if (fileParams == null) {
            fileParams = new ArrayList<>();
        }
        if (_file != null && _file.exists()) {
            fileParams.add(new FileParams(_key, _file, MediaType
                    .parse(mimeType)));
        }
        return this;
    }

    public AjaxParams addBytes(byte[] bufs){
        this.bufs = bufs;
        return this;
    }

    public AjaxParams addParametersMp4(String _key, File _file) {
        addParametersFile(_key, _file, "video/mp4");
        return this;
    }

    public AjaxParams addParametersJPG(String _key, File _file) {
        addParametersFile(_key, _file, "image/jpg");
        return this;
    }

    public AjaxParams addParametersPNG(String _key, File _file) {
        addParametersFile(_key, _file, "image/png");
        return this;
    }

    public AjaxParams addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public List<BaseParams> getBaseParams() {
        return baseParams;
    }

    public List<FileParams> getFileParams() {
        return fileParams;
    }

    public Map<String, String> getHeader() {
        return headers;
    }

    public byte[] getBufs(){
        return this.bufs;
    }

    public static final class BaseParams {
        private String _key;
        private String _value;

        public BaseParams(String _key, String _value) {
            super();
            this._key = _key;
            this._value = _value;
        }

        public String get_key() {
            return _key;
        }

        public void set_key(String _key) {
            this._key = _key;
        }

        public String get_value() {
            return _value;
        }

        public void set_value(String _value) {
            this._value = _value;
        }
    }

    public static final class FileParams {
        private String _key;
        private File file;
        private MediaType mediaType;

        public FileParams(String _key, File file, MediaType mediaType) {
            super();
            this._key = _key;
            this.file = file;
            this.mediaType = mediaType;
        }

        public String get_key() {
            return _key;
        }

        public void set_key(String _key) {
            this._key = _key;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public MediaType getMediaType() {
            return mediaType;
        }

        public void setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
        }

        public String getFileName() {
            if (file == null) {
                return "unknown_filename";
            }

            return file.getName();
        }
    }

}
