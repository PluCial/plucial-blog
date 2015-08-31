package com.appspot.plucial.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.appspot.plucial.Constants;
import com.appspot.plucial.exception.DataInvalidException;
import com.appspot.plucial.model.ActivityModel;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;

public class TextSearchService {

    protected static final HttpTransport TRANSPORT = new NetHttpTransport();
    protected static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * 全件検索用のドキュメントを作成
     * @param title
     * @param content
     */
    public static void createDocument(ActivityModel model, UserModel userModel) throws Exception {

        String content = "";
        if(model.getContentString() != null && !model.getContentString().trim().equals("")) {
            content = content + " " + model.getContentString();
        }
        if(model.getAnnotationString() != null && !model.getAnnotationString().trim().equals("")) {
            content = content + " " + model.getAnnotationString();
        }
        if(model.getAttachmentsDisplayNameString() != null && !model.getAttachmentsDisplayNameString().trim().equals("")) {
            content = content + " " + model.getAttachmentsDisplayNameString();
        }
        if(model.getAttachmentsContentString() != null && !model.getAttachmentsContentString().trim().equals("")) {
            content = content + " " + model.getAttachmentsContentString();
        }

        if(content.equals("")) return;

        Document document = Document.newBuilder()
            .setId(model.getKey().getName())
            .addField(Field.newBuilder()
                .setName("content")
                .setText(Utils.removeAllTags(content)))
            .addField(Field.newBuilder()
                .setName("published")
                .setDate(model.getPublished())
        ).build();

        Index index = getDocumentIndex(userModel);

        index.put(document);
    }

    /**
     * 全件検索検索
     * @param userModel
     * @param content
     */
    public static List<ActivityModel> findDocument(UserModel userModel, String[] qstrString) throws Exception {

        String qstr = "";
        for(int i=0; i < qstrString.length; i++) {
            if(i > 0) {
                qstr = qstr + " AND ";
            }
            qstr = qstr + "content:\"" + qstrString[i] + "\"";
        }

        Index index = getDocumentIndex(userModel);


        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(100)
                    .setSortOptions(SortOptions.newBuilder()
                    .addSortExpression(SortExpression.newBuilder()
                        .setExpression("published")
                        .setDefaultValueDate(new Date())
                        .setDirection(SortDirection.DESCENDING)))
                    .build()).build(qstr);
        Results<ScoredDocument> results = index.search(query);

        // 対象のアクティビティを取得
        List<ActivityModel> activityList = new ArrayList<ActivityModel>();
        for (ScoredDocument document : results) {
            String activityId = document.getId();
            ActivityModel activityModel = ActivityService.getActivity(activityId);

            try {
                if(updateActivity(activityModel, userModel)) {
                    activityModel = ActivityService.getActivity(activityId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(activityModel != null) {
                activityList.add(activityModel);
            }
        }

        return activityList;
    }

    public static void deleteDocument(UserModel userModel, ActivityModel model) {
        try {
            Index index = getDocumentIndex(userModel);
            index.delete(model.getKey().getName());
        } catch (Exception e) {
        }
    }

    private static Index getDocumentIndex(UserModel userModel) {
        return SearchServiceFactory.getSearchService()
                .getIndex(IndexSpec.newBuilder()
                    .setName(userModel.getKey().getName()));
    }

    /**
     * アクティビティのチェックと更新
     * <pre>
     * Googleからの要求により：
     * You may store anonymized or aggregate data indefinitely,
     * and make that available for download,
     * but user information and original post content should be refreshed every 30 days, at least.
     * The easiest way to do this is probably to simply re-run your API queries every month, and replace the posts you store with the new results,
     * so that any modified/deleted posts on Google+ are consistent with your application.
     * Please let us know if/when you have updated your app to comply with this policy and we will be happy to continue processing your request.
     * </pre>
     * @param activityModel
     * @return
     * @throws IOException
     * @throws DataInvalidException
     */
    private static boolean updateActivity(ActivityModel activityModel, UserModel userModel) throws IOException, DataInvalidException {

        // The date of 30 days ago
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, Constants.CHECK_AND_UPDATE_INFO_LIMIT_DAYS);

        // When it passes on the 30th
        if(activityModel.getUpdateCheckDate() == null || activityModel.getUpdateCheckDate().before(cal.getTime())) {

            // GoogleCredential
            GoogleCredential credential = new GoogleCredential.Builder()
            .setJsonFactory(JSON_FACTORY)
            .setTransport(TRANSPORT)
            .setClientSecrets(Constants.GOOGLE_PROJECT_CLIENT_ID, Constants.GOOGLE_PROJECT_CLIENT_SECRET).build()
            .setRefreshToken(userModel.getRefreshToken());

            // リフレッシュトークンを元にアクセストークンを更新
            credential.refreshToken();

            // Plus Object
            Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(Constants.GOOGLE_APPLICATION_NAME)
            .build();

            // Re-acquisition of Activity
            Activity activity = null;
            try {
                activity = plus.activities().get(activityModel.getKey().getName()).execute();

            } catch (GoogleJsonResponseException e) {
                // When deleted
                if(e.getStatusCode() == 404) {
                    ActivityService.delete(userModel, activityModel);

                    return true;
                }
            }


            if(activity != null) {
                Date oldModelUpdateDate = activityModel.getUpdated();
                Date newModelUpdateDate = new Date(activity.getUpdated().getValue());

                // Activity Is Updated
                if(!oldModelUpdateDate.equals(newModelUpdateDate)) {
                    // Replace Activity And UpdateCheckDate
                    ActivityService.putActivity(activity, userModel);

                    return true;
                }
            }

            // Update UpdateCheckDate
            activityModel.setUpdateCheckDate(new Date());
            ActivityService.putActivity(userModel, activityModel.getDateModelRef().getModel(), activityModel);

        }

        return false;
    }

}
