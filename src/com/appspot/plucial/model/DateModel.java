package com.appspot.plucial.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.Sort;

import com.appspot.plucial.meta.ActivityModelMeta;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;

@Model(schemaVersion = 1)
public class DateModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザーID + "_" + YYYYMMDD */
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true, unindexed = true)
    private Long version;

    /** 日付 YYYYMMDD(リンク用) */
    @Attribute(unindexed = true)
    private String date;

    /** タイトル */
    @Attribute(unindexed = true)
    private String title;

    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    /** ユーザーモデル に対しての関連 */
    private ModelRef<UserModel> userModelRef = new ModelRef<UserModel>(UserModel.class);

    /** アクティビティの関連 */
    @Attribute(persistent = false)
    private InverseModelListRef<ActivityModel, DateModel> activityModelListRef =
    new InverseModelListRef<ActivityModel, DateModel>(
            ActivityModel.class,
            ActivityModelMeta.get().dateModelRef.getName(),
            this,
            new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING) );

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
        DateModel other = (DateModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public ModelRef<UserModel> getUserModelRef() {
        return userModelRef;
    }

    public InverseModelListRef<ActivityModel, DateModel> getActivityModelListRef() {
        return activityModelListRef;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateJP() {
        if(date == null) return null;
        return date.substring(0,4) + "年" + date.substring(4,6) + "月"+ date.substring(6,8) + "日";
    }

    public String getLastmodOfFormat() {
        if(date == null) return null;
        return date.substring(0,4) + "-" + date.substring(4,6) + "-"+ date.substring(6,8);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
