package com.appspot.plucial.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Model(schemaVersion = 1)
public class UserUrlsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 自動 */
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    /** 値(URL) */
    @Attribute(unindexed = true)
    private Text value;

    /** タイプ */
    @Attribute(unindexed = true)
    private String type;

    /** ラベル */
    @Attribute(unindexed = true)
    private Text label;

    /** ユーザーモデル に対しての関連 */
    private ModelRef<UserModel> userModelRef = new ModelRef<UserModel>(UserModel.class);

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
        UserUrlsModel other = (UserUrlsModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public Text getValue() {
        return value;
    }

    public void setValue(Text value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public String getValueString() {
        if (value == null) {
            return null;
        }
        return value.getValue();
    }

    public String getLabelString() {
        if (label == null) {
            return null;
        }
        return label.getValue();
    }

    public ModelRef<UserModel> getUserModelRef() {
        return userModelRef;
    }
}
