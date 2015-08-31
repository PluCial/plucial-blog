package com.appspot.plucial.controller.account.evernote;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.EvernoteApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slim3.controller.Navigation;

import com.appspot.plucial.Constants;
import com.appspot.plucial.controller.account.BaseController;
import com.appspot.plucial.model.UserModel;
import com.appspot.plucial.service.UserService;
import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;
import com.google.appengine.api.datastore.Text;

public class AddEvernoteAccountController extends BaseController {

    @Override
    protected Navigation execute(UserModel loginUserModel) throws Exception {

        EvernoteAuth evernoteAuth = null;
        try {
            evernoteAuth = getEvernoteAuth();

        } catch (Exception e) {
            return redirect("/account/setting");
        }


        NoteStoreClient noteStoreClient = new ClientFactory(evernoteAuth).createNoteStoreClient();

        // アクセストークンが期限切れで再設定する場合、NoteBookは既に作成されている可能性があるため
        // 作成する前に既存のNotebookが存在するかをチェック
        Notebook notebook = null;
        if(loginUserModel.getEvernoteNotebookId() == null) {
            notebook = createNotebook(noteStoreClient);

        }else {
            try {
                notebook = noteStoreClient.getNotebook(loginUserModel.getEvernoteNotebookId());

            } catch (Exception e) {
                // ユーザーに削除された場合再作成する もしく アカウント変更した場合
                notebook = createNotebook(noteStoreClient);
            }
        }

        loginUserModel.setEvernoteAccessToken(new Text(evernoteAuth.getToken()));
        if(notebook != null) {
            loginUserModel.setEvernoteNotebookId(notebook.getGuid());
        }

        // ユーザー情報を更新
        UserService.put(loginUserModel);

        return redirect("/account/setting");

    }

    /**
     * Evernote承認情報を取得
     * @return
     */
    private EvernoteAuth getEvernoteAuth() {
        String thisUrl = request.getRequestURL().toString();
        String cbUrl = thisUrl.substring(0, thisUrl.lastIndexOf('/') + 1) + Constants.EVERNOTE_APP_OAUTH_CALLBACK;

        // Evernote Service の生成
        Class<? extends EvernoteApi> providerClass = EvernoteApi.Sandbox.class;
        if (Constants.EVERNOTE_SERVICE == EvernoteService.PRODUCTION) {
          providerClass = org.scribe.builder.api.EvernoteApi.class;
        }


        OAuthService service = new ServiceBuilder()
            .provider(providerClass)
            .apiKey(Constants.EVERNOTE_CONSUMER_KEY)
            .apiSecret(Constants.EVERNOTE_CONSUMER_SECRET)
            .callback(cbUrl)
            .build();

        String oauthToken = request.getParameter("oauth_token");
        String requestTokenSecret = sessionScope("evernoteRequestTokenSecret");
        String verifier = asString("oauth_verifier");

        // アクセストークンの取得
        Token scribeRequestToken = new Token(oauthToken, requestTokenSecret);
        Verifier scribeVerifier = new Verifier(verifier);
        Token scribeAccessToken = service.getAccessToken(scribeRequestToken, scribeVerifier);
        EvernoteAuth evernoteAuth = EvernoteAuth.parseOAuthResponse(Constants.EVERNOTE_SERVICE, scribeAccessToken.getRawResponse());

        return evernoteAuth;
    }

    /**
     * Notebookの作成
     * @param noteStoreClient
     * @return
     * @throws TException
     * @throws EDAMSystemException
     * @throws Exception
     */
    private Notebook createNotebook(NoteStoreClient noteStoreClient) {
        Notebook notebook = new Notebook();
        notebook.setName("PluCial");
        try {
            notebook =  noteStoreClient.createNotebook(notebook);
        } catch (Exception e) {
        }

        return notebook;
    }

    @Override
    protected String setPageTitle() {
        return null;
    }

    @Override
    protected String setPageDescription() {
        return null;
    }
}
