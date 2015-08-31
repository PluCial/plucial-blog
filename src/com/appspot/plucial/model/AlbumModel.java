package com.appspot.plucial.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class AlbumModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(unindexed = true)
    private Text url;

    @Attribute(unindexed = true)
    private Long height;

    @Attribute(unindexed = true)
    private Long width;

    @Attribute(unindexed = true)
    private Text imageUrl;

    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    /** ActivityModel に対しての関連 */
    private ModelRef<ActivityModel> activityModelRef = new ModelRef<ActivityModel>(ActivityModel.class);

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AlbumModel other = (AlbumModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public Text getUrl() {
        return url;
    }

    public void setUrl(Text url) {
        this.url = url;
    }

    public Text getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Text imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlString() {
        if (url == null) {
            return null;
        }
        return url.getValue();
    }

    public String getImageUrlString() {
        if (imageUrl == null) {
            return null;
        }
        return imageUrl.getValue();
    }

    public ModelRef<ActivityModel> getActivityModelRef() {
        return activityModelRef;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }
}
