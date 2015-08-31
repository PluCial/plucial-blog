package com.appspot.plucial.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

import com.appspot.plucial.meta.ActivityModelMeta;
import com.appspot.plucial.meta.DateModelMeta;
import com.appspot.plucial.meta.UserUrlsModelMeta;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;

@Model(schemaVersion = 1)
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** キー(Email) */
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true, unindexed = true)
    private Long version;

    /** グループ */
    private int group;

    /** email */
    @Attribute(unindexed = true)
    private String email;

    /** プロフィールURL */
    @Attribute(unindexed = true)
    private Text url;

    /** 表示名 */
    @Attribute(unindexed = true)
    private String displayName;

    /** ユーザープロフィール写真URL */
    @Attribute(unindexed = true)
    private Text imageUrl;

    /** キャッチ */
    @Attribute(unindexed = true)
    private Text tagline;

    /** 特技 */
    @Attribute(unindexed = true)
    private Text braggingRights;

    /** 自己紹介 */
    @Attribute(unindexed = true)
    private Text aboutMe;

    /** 背景画像 */
    @Attribute(unindexed = true)
    private Text coverPhotoUrl;

    /** AdSenseアカウントコード */
    @Attribute(unindexed = true)
    private String adSenseAccountId;

    /**
     * AdSense Unit Code 300_250
     */
    @Attribute(unindexed = true)
    private String adSenseUnitCodeW300H250;

    /**
     * AdSense Unit Code 300_250
     */
    @Attribute(unindexed = true)
    private String adSenseUnitCodeW728H90;

    /** アクセストークン */
    @Attribute(unindexed = true)
    private String accessToken;

    /** リフレッシュトークン */
    @Attribute(unindexed = true)
    private String refreshToken;

    /** Twitter ハッシュタグによる投稿 */
    @Attribute(unindexed = true)
    private boolean twitterRepostHashtagFlg;

    /** Twitter アカウント名 */
    @Attribute(unindexed = true)
    private String twitterAccountName;

    /** Twitter アクセストークン */
    @Attribute(unindexed = true)
    private String twitterAccessToken;

    /** Twitter トークンシークレット */
    @Attribute(unindexed = true)
    private String twitterTokenSecret;

    /** Facebook ハッシュタグによる投稿 */
    @Attribute(unindexed = true)
    private boolean facebookRepostHashtagFlg;

    /** Facebook アカウント名 */
    @Attribute(unindexed = true)
    private String facebookAccountName;

    /** FaceBook アクセストークン */
    @Attribute(unindexed = true)
    private Text facebookAccessToken;

    /** Evernote ハッシュタグによる投稿 */
    @Attribute(unindexed = true)
    private boolean evernoteRepostHashtagFlg;

    /** Evernote Access Token */
    @Attribute(unindexed = true)
    private Text evernoteAccessToken;

    /** Evernote NoteBook Id */
    @Attribute(unindexed = true)
    private String evernoteNotebookId;

    /** 最後のアクティビティを取込んだかのフラグ */
    @Attribute(unindexed = true)
    private boolean inputActivityLastFinishFlg;

    /** bot実行中フラグ */
    @Attribute(unindexed = true)
    private boolean activityBotPerformingFlg;

    /** 更新チェック日時 */
    @Attribute(unindexed = true)
    private Date updateCheckDate;

    /** アクティビティの関連 */
    @Attribute(persistent = false)
    private InverseModelListRef<ActivityModel, UserModel> activityModelListRef =
            new InverseModelListRef<ActivityModel, UserModel>(
                    ActivityModel.class,
                    ActivityModelMeta.get().userModelRef.getName(),
                    this,
                    new Sort(ActivityModelMeta.get().published, SortDirection.DESCENDING));

    /** UserUrlsModelの関連 */
    @Attribute(persistent = false)
    private InverseModelListRef<UserUrlsModel, UserModel> userUrlsModelListRef =
    new InverseModelListRef<UserUrlsModel, UserModel>(
            UserUrlsModel.class,
            UserUrlsModelMeta.get().userModelRef.getName(),
            this);

    /** DateModelの関連 */
    @Attribute(persistent = false)
    private InverseModelListRef<DateModel, UserModel> dateModelListRef =
    new InverseModelListRef<DateModel, UserModel>(
            DateModel.class,
            DateModelMeta.get().userModelRef.getName(),
            this,
            new Sort(DateModelMeta.get().key.getName(), SortDirection.DESCENDING));

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
        UserModel other = (UserModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Text getImageUrl() {
        return imageUrl;
    }

    public String getImageUrlString() {
        if (imageUrl == null) {
            return null;
        }
        return imageUrl.getValue();
    }

    public void setImageUrl(Text imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Text getTagline() {
        return tagline;
    }

    public String getTaglineString() {
        if (tagline == null) {
            return null;
        }
        return tagline.getValue();
    }

    public void setTagline(Text tagline) {
        this.tagline = tagline;
    }

    public Text getBraggingRights() {
        return braggingRights;
    }

    public String getBraggingRightsString() {
        if (braggingRights == null) {
            return null;
        }
        return braggingRights.getValue();
    }

    public void setBraggingRights(Text braggingRights) {
        this.braggingRights = braggingRights;
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

    public InverseModelListRef<DateModel, UserModel> getDateModelListRef() {
        return dateModelListRef;
    }

    public Text getAboutMe() {
        return aboutMe;
    }

    public String getAboutMeString() {
        if (aboutMe == null) {
            return null;
        }
        return aboutMe.getValue();
    }

    public void setAboutMe(Text aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAdSenseAccountId() {
        return adSenseAccountId;
    }

    public void setAdSenseAccountId(String adSenseAccountId) {
        this.adSenseAccountId = adSenseAccountId;
    }

    public String getAdSenseUnitCodeW300H250() {
        return adSenseUnitCodeW300H250;
    }

    public void setAdSenseUnitCodeW300H250(String adSenseUnitCodeW300H250) {
        this.adSenseUnitCodeW300H250 = adSenseUnitCodeW300H250;
    }

    public String getAdSenseUnitCodeW728H90() {
        return adSenseUnitCodeW728H90;
    }

    public void setAdSenseUnitCodeW728H90(String adSenseUnitCodeW728H90) {
        this.adSenseUnitCodeW728H90 = adSenseUnitCodeW728H90;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInputActivityLastFinishFlg() {
        return inputActivityLastFinishFlg;
    }

    public void setInputActivityLastFinishFlg(boolean inputActivityLastFinishFlg) {
        this.inputActivityLastFinishFlg = inputActivityLastFinishFlg;
    }

    public boolean isActivityBotPerformingFlg() {
        return activityBotPerformingFlg;
    }

    public void setActivityBotPerformingFlg(boolean activityBotPerformingFlg) {
        this.activityBotPerformingFlg = activityBotPerformingFlg;
    }

    public Text getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(Text coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public String getCoverPhotoUrlString() {
        if (coverPhotoUrl == null) {
            return "/images/index/about-back.png";
        }
        return coverPhotoUrl.getValue();
    }

    public InverseModelListRef<ActivityModel, UserModel> getActivityModelListRef() {
        return activityModelListRef;
    }

    public InverseModelListRef<UserUrlsModel, UserModel> getUserUrlsModelListRef() {
        return userUrlsModelListRef;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getTwitterAccountName() {
        return twitterAccountName;
    }

    public void setTwitterAccountName(String twitterAccountName) {
        this.twitterAccountName = twitterAccountName;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterTokenSecret() {
        return twitterTokenSecret;
    }

    public void setTwitterTokenSecret(String twitterTokenSecret) {
        this.twitterTokenSecret = twitterTokenSecret;
    }

    public Text getFacebookAccessToken() {
        return facebookAccessToken;
    }

    public void setFacebookAccessToken(Text facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    public String getFacebookAccessTokenString() {
        if (facebookAccessToken == null) {
            return null;
        }
        return facebookAccessToken.getValue();
    }

    public String getFacebookAccountName() {
        return facebookAccountName;
    }

    public void setFacebookAccountName(String facebookAccountName) {
        this.facebookAccountName = facebookAccountName;
    }

    public boolean isTwitterRepostHashtagFlg() {
        return twitterRepostHashtagFlg;
    }

    public void setTwitterRepostHashtagFlg(boolean twitterRepostHashtagFlg) {
        this.twitterRepostHashtagFlg = twitterRepostHashtagFlg;
    }

    public boolean isFacebookRepostHashtagFlg() {
        return facebookRepostHashtagFlg;
    }

    public void setFacebookRepostHashtagFlg(boolean facebookRepostHashtagFlg) {
        this.facebookRepostHashtagFlg = facebookRepostHashtagFlg;
    }

    public Text getEvernoteAccessToken() {
        return evernoteAccessToken;
    }

    public void setEvernoteAccessToken(Text evernoteAccessToken) {
        this.evernoteAccessToken = evernoteAccessToken;
    }

    public String getEvernoteAccessTokenString() {
        if (evernoteAccessToken == null) {
            return null;
        }
        return evernoteAccessToken.getValue();
    }

    public String getEvernoteNotebookId() {
        return evernoteNotebookId;
    }

    public void setEvernoteNotebookId(String evernoteNotebookId) {
        this.evernoteNotebookId = evernoteNotebookId;
    }

    public boolean isEvernoteRepostHashtagFlg() {
        return evernoteRepostHashtagFlg;
    }

    public void setEvernoteRepostHashtagFlg(boolean evernoteRepostHashtagFlg) {
        this.evernoteRepostHashtagFlg = evernoteRepostHashtagFlg;
    }

    public Date getUpdateCheckDate() {
        return updateCheckDate;
    }

    public void setUpdateCheckDate(Date updateCheckDate) {
        this.updateCheckDate = updateCheckDate;
    }
}
