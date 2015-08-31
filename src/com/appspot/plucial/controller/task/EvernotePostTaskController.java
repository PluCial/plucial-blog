package com.appspot.plucial.controller.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.AlbumModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.ActivityService;
import com.appspot.plucial.service.UserService;
import com.appspot.plucial.utils.Utils;
import com.evernote.auth.EvernoteAuth;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.Note;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class EvernotePostTaskController extends Controller {

    private static final Logger logger = Logger.getLogger(EvernotePostTaskController.class.getName());

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public Navigation run() throws Exception {

        UserModel userModel = null;

        try{
            userModel = getUser();
        }catch(Exception e) {
            return null;
        };


        // タスクは成功するまで実行されるため、失敗時は例外をキャッチして再実行をさせない
        try{
            // アクティビティの取得
            String activityId = asString("activityId");
            ActivityModel activityModel = ActivityService.getActivity(activityId);
            if(activityModel == null) return null;

            // 再共有の場合は投稿対象外にする
            if(activityModel.getVerb().getCategory().equals(Constants.GOOGLE_ACTIVITY_VERB_TYPE_SHARE)) {
                return null;
            }

            // Evernote オブジェクトの取得
            NoteStoreClient noteStoreClient = getNoteStoreClient(userModel);
            Note note = new Note();
            note.setTitle(getNoteTitle(activityModel));

            // ノートBody
            String nBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            nBody += "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">";
            nBody += "<en-note>";

            nBody += "<div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, 'Liberation Sans', FreeSans, sans-serif; color: rgb(88, 89, 87); font-size: 14px; line-height: 1.5; overflow-y: hidden; background: rgb(230, 230, 230);margin: -20px;\">";
            nBody += "<div style=\"height: 40px;\">&nbsp;</div>";
            nBody += "<div style=\"max-width: 600px;padding: 25px 0px 0px 0px;background-color: #fff;margin: 0 auto;box-shadow: 0 0px 5px rgba(0, 0, 0, 0.2);\">";

            nBody += Utils.removeLinkTags(getNoteContents(activityModel));
            nBody += getNoteAttachmentsContents(activityModel);

            // 下部メニュー
            nBody += "<div style=\"background: #f5f5f5;min-height: 16px;padding: 1px 30px;text-align: center;border-top: 1px solid #ebebeb;margin-top: 20px;\">";
            nBody += "<p style=\"color: #747474;line-height: 1.5em;\">";
            nBody += "<a href=\"" + activityModel.getUrlString() + "\" style=\"text-decoration: none;color: #747474;\" target=\"_blank\">Google+で見る</a>";
            nBody += "</p>";
            nBody += "</div>";

            nBody += "</div>";
            nBody += "<div style=\"height: 40px;\">&nbsp;</div>";
            nBody += "</div>";
            nBody += "</en-note>";

            note.setContent(nBody);
            if(userModel.getEvernoteNotebookId() != null) {
                note.setNotebookGuid(userModel.getEvernoteNotebookId());
            }

            noteStoreClient.createNote(note);


        }catch(Exception e) {
            logger.severe(e.toString());
        }

        return null;
    }


    /**
     * UserModelの取得
     * @return
     * @throws Exception
     */
    public UserModel getUser() throws Exception {

        String userId = asString("user");

        UserModel userModel = UserService.getOrNull(userId);

        if(userModel == null) throw new Exception();

        return userModel;
    }



    /**
     * NoteStoreClientの生成
     * @param userModel
     * @param msg
     * @return
     */
    private NoteStoreClient getNoteStoreClient(UserModel userModel) throws Exception {

        EvernoteAuth evernoteAuth = new EvernoteAuth(Constants.EVERNOTE_SERVICE, userModel.getEvernoteAccessTokenString());

        return new ClientFactory(evernoteAuth).createNoteStoreClient();
    }

    /**
     * ノートタイトル
     * @return
     */
    private String getNoteTitle(ActivityModel activityModel) {

        Date date = activityModel.getPublished();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        return "Google+ Post   " + sdf.format(date);
    }

    /**
     * ノートコンテンツ
     * @return
     */
    private String getNoteContents(ActivityModel activityModel) {
        // 添付なし投稿の場合
        String contents = "";
        if(activityModel.getContentString() != null) {
            contents += "<div style=\"margin: 0px 25px;padding-bottom: 15px;\">";
            contents += activityModel.getContentString();
            contents += "</div>";
        }

        return contents;
    }

    /**
     * ノート添付コンテンツ
     * @return
     */
    private String getNoteAttachmentsContents(ActivityModel activityModel) {
        String contents = "";
        if(activityModel.isAttachmentsFlg()) {

            contents += "<div style=\"margin: 0px 25px;font-size: 14px;color: #4d4b47;background-color: #e6f4f6;border: 1px solid #c1e8ec;padding: 0px 15px 8px;clear: both;\">";

            // アルバムの場合
            if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ALBUM)) {
                if(activityModel.getAlbumModelListRef() != null && activityModel.getAlbumModelListRef().getModelList() != null) {
                    for(AlbumModel albumModel: activityModel.getAlbumModelListRef().getModelList()) {
                        contents += "<div style=\"text-align: center;margin-bottom: 20px;\">";
                        contents += "<img src=\"" + Utils.changeAlbumUrl(albumModel) + "\" />";
                        contents += "</div>";
                    }
                }

            }else if(activityModel.getAttachmentsImageUrlString() != null) {
                // アルバム以外で、添付画像がある場合
                contents += "<div style=\"text-align: center;margin-bottom: 20px;\">";
                contents += "<img src=\"" + activityModel.getAttachmentsImageUrlString() + "\" />";
                contents += "</div>";
            }

            if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)
                    || activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_VIDEO)) {

                if(activityModel.getAttachmentsDisplayNameString() != null) {
                    contents += "<p style=\"text-align: center;\">";

                    if(activityModel.getAttachmentsType().getCategory().equals(Constants.GOOGLE_ACTIVITY_ATTACHMENTS_TYPE_ARTICLE)) {
                        contents += "<a href=\"" + activityModel.getAttachmentsUrlString() + "\" style=\"text-decoration: none;\" target=\"_blank\">";

                    }else {
                        contents += "<a href=\"" + activityModel.getEmbedUrlString() + "\" style=\"text-decoration: none;\" target=\"_blank\">";

                    }

                    contents += activityModel.getAttachmentsDisplayNameString();


                    contents += "</a>";
                    contents += "</p>";
                }

                if(activityModel.getAttachmentsContentString() != null) {
                    contents += "<p style=\"text-align: left;color: #999;\">";
                    contents += activityModel.getAttachmentsContentString();
                    contents += "</p>";
                }
            }

            contents += "</div>";
        }

        return contents;
    }

}
