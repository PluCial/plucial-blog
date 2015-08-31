package com.appspot.plucial.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.appspot.plucial.meta.AlbumModelMeta;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Model(schemaVersion = 1)
public class ActivityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** activity id */
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true, unindexed = true)
    private Long version;

    /** アクティビティタイプ（post or share） */
    private Category verb;

    /** 記事のURL */
    @Attribute(unindexed = true)
    private Text url;

    /** 記事タイトル */
    @Attribute(unindexed = true)
    private Text title;

    /** 公開日時 */
    private Date published;

    /** 更新日時 */
    @Attribute(unindexed = true)
    private Date updated;

    /** 記事詳細 */
    @Attribute(unindexed = true)
    private Text content;

    /** シェア元情報フラグ */
    @Attribute(unindexed = true)
    private boolean shareActorFlg;

    /** 元の情報提供者ID */
    @Attribute(unindexed = true)
    private String actorId;

    /** 元情報提供者の表示名 */
    @Attribute(unindexed = true)
    private String actorDisplayName;

    /** 元情報提供者のプロフィールURL */
    @Attribute(unindexed = true)
    private Text actorUrl;

    /** 元情報提供者画像URL */
    @Attribute(unindexed = true)
    private Text actorImageUrl;

    /** このアクティビティを共有したユーザーによって追加された追加コンテンツで、アクティビティを再共有している場合にのみ使用できます。 */
    @Attribute(unindexed = true)
    private Text annotation;

    /** 添付フラグ */
    private boolean attachmentsFlg = false;

    /** (article or video or album or photo) */
    private Category attachmentsType;

    /** 関連コンテンツの表示名 */
    @Attribute(unindexed = true)
    private Text attachmentsDisplayName;

    /** 関連コンテンツのURL */
    @Attribute(unindexed = true)
    private Text attachmentsUrl;

    /** 関連コンテンツ画像のURL */
    @Attribute(unindexed = true)
    private Text attachmentsImageUrl;

    /** 関連コンテンツFull画像のURL */
    @Attribute(unindexed = true)
    private Text attachmentsFullImageUrl;

    /** 関連コンテンツ記事詳細 */
    @Attribute(unindexed = true)
    private Text attachmentsContent;

    /** 添付ファイルが動画の場合は、埋め込み可能なリンク。 */
    @Attribute(unindexed = true)
    private Text embedUrl;

    /** ディア タイプ(例:application/x-shockwave-flash) */
    @Attribute(unindexed = true)
    private String embedType;

    /** 公開フラグ */
    @Attribute(unindexed = true)
    private boolean publicFlg;

    /** アクティビティの更新チェック日時 */
    @Attribute(unindexed = true)
    private Date updateCheckDate;

    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    /** UserModel に対しての関連 */
    private ModelRef<UserModel> userModelRef = new ModelRef<UserModel>(UserModel.class);
    /** DateModel に対しての関連 */
    private ModelRef<DateModel> dateModelRef = new ModelRef<DateModel>(DateModel.class);

    /** アルバムの関連 */
    @Attribute(persistent = false)
    private InverseModelListRef<AlbumModel, ActivityModel> albumModelListRef =
    new InverseModelListRef<AlbumModel, ActivityModel>(
            AlbumModel.class,
            AlbumModelMeta.get().activityModelRef.getName(),
            this);



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
        ActivityModel other = (ActivityModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Text getContent() {
        return content;
    }

    public String getContentString() {
        if (content == null) {
            return null;
        }
        return content.getValue();
    }

    public void setContent(Text content) {
        this.content = content;
    }

    public boolean isAttachmentsFlg() {
        return attachmentsFlg;
    }

    public void setAttachmentsFlg(boolean attachmentsFlg) {
        this.attachmentsFlg = attachmentsFlg;
    }

    public Category getAttachmentsType() {
        return attachmentsType;
    }

    public void setAttachmentsType(Category attachmentsType) {
        this.attachmentsType = attachmentsType;
    }

    public String getEmbedType() {
        return embedType;
    }

    public void setEmbedType(String embedType) {
        this.embedType = embedType;
    }

    public Text getAttachmentsContent() {
        return attachmentsContent;
    }

    public String getAttachmentsContentString() {
        if (attachmentsContent == null) {
            return null;
        }
        return attachmentsContent.getValue();
    }

    public void setAttachmentsContent(Text attachmentsContent) {
        this.attachmentsContent = attachmentsContent;
    }

    public Text getUrl() {
        return url;
    }

    public String getUrlString() {
        if (url == null) {
            return null;
        }
        return url.getValue();
    }

    public void setUrl(Text url) {
        this.url = url;
    }

    public Text getAttachmentsUrl() {
        return attachmentsUrl;
    }

    public String getAttachmentsUrlString() {
        if (attachmentsUrl == null) {
            return null;
        }
        return attachmentsUrl.getValue();
    }

    public void setAttachmentsUrl(Text attachmentsUrl) {
        this.attachmentsUrl = attachmentsUrl;
    }

    public Text getAttachmentsImageUrl() {
        return attachmentsImageUrl;
    }

    public String getAttachmentsImageUrlString() {
        if (attachmentsImageUrl == null) {
            return null;
        }
        return attachmentsImageUrl.getValue();
    }

    public void setAttachmentsImageUrl(Text attachmentsImageUrl) {
        this.attachmentsImageUrl = attachmentsImageUrl;
    }

    public Text getEmbedUrl() {
        return embedUrl;
    }

    public String getEmbedUrlString() {
        if (embedUrl == null) {
            return null;
        }
        return embedUrl.getValue();
    }

    public void setEmbedUrl(Text embedUrl) {
        this.embedUrl = embedUrl;
    }

    public Text getTitle() {
        return title;
    }

    public String getTitleString() {
        if (title == null) {
            return null;
        }
        return title.getValue();
    }

    public void setTitle(Text title) {
        this.title = title;
    }

    public Text getAttachmentsDisplayName() {
        return attachmentsDisplayName;
    }

    public String getAttachmentsDisplayNameString() {
        if (attachmentsDisplayName == null) {
            return null;
        }
        return attachmentsDisplayName.getValue();
    }

    public void setAttachmentsDisplayName(Text attachmentsDisplayName) {
        this.attachmentsDisplayName = attachmentsDisplayName;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorDisplayName() {
        return actorDisplayName;
    }

    public void setActorDisplayName(String actorDisplayName) {
        this.actorDisplayName = actorDisplayName;
    }

    public Text getAnnotation() {
        return annotation;
    }

    public String getAnnotationString() {
        if (annotation == null) {
            return null;
        }
        return annotation.getValue();
    }

    public void setAnnotation(Text annotation) {
        this.annotation = annotation;
    }

    public Text getActorUrl() {
        return actorUrl;
    }

    public String getActorUrlString() {
        if (actorUrl == null) {
            return null;
        }
        return actorUrl.getValue();
    }

    public void setActorUrl(Text actorUrl) {
        this.actorUrl = actorUrl;
    }

    public Text getActorImageUrl() {
        return actorImageUrl;
    }

    public String getActorImageUrlString() {
        if (actorImageUrl == null) {
            return null;
        }
        return actorImageUrl.getValue();
    }

    public void setActorImageUrl(Text actorImageUrl) {
        this.actorImageUrl = actorImageUrl;
    }

    public Category getVerb() {
        return verb;
    }

    public void setVerb(Category verb) {
        this.verb = verb;
    }

    public boolean isShareActorFlg() {
        return shareActorFlg;
    }

    public void setShareActorFlg(boolean shareActorFlg) {
        this.shareActorFlg = shareActorFlg;
    }

    public ModelRef<DateModel> getDateModelRef() {
        return dateModelRef;
    }

    public boolean isPublicFlg() {
        return publicFlg;
    }

    public void setPublicFlg(boolean publicFlg) {
        this.publicFlg = publicFlg;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public ModelRef<UserModel> getUserModelRef() {
        return userModelRef;
    }

    public Text getAttachmentsFullImageUrl() {
        return attachmentsFullImageUrl;
    }

    public void setAttachmentsFullImageUrl(Text attachmentsFullImageUrl) {
        this.attachmentsFullImageUrl = attachmentsFullImageUrl;
    }

    public String getAttachmentsFullImageUrlString() {
        if (attachmentsFullImageUrl == null) {
            return null;
        }
        return attachmentsFullImageUrl.getValue();
    }

    public InverseModelListRef<AlbumModel, ActivityModel> getAlbumModelListRef() {
        return albumModelListRef;
    }

    public Date getUpdateCheckDate() {
        return updateCheckDate;
    }

    public void setUpdateCheckDate(Date updateCheckDate) {
        this.updateCheckDate = updateCheckDate;
    }
}
