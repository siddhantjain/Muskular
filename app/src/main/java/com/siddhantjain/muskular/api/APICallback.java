package com.siddhantjain.muskular.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.siddhantjain.muskular.models.BasicResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by akash.jatangi on 9/19/15.
 */
public abstract class APICallback<T,E> implements Callback<T> {

    private static final String RESPONSE_CODE_SUCCESS = "100";

    Context _context;


    /**
     * A context object is required to show toasts, dialogs and get error messages from strings.xml
     *
     * @param context The context object
     */
    public APICallback(Context context) {
        _context = context;
    }

    /**
     * Returns the context
     * @return
     */
    public Context getContext() {
        return _context;
    }

    /**
     * This method is called when the API returns a response and the response code is
     * {@link com.siddhantjain.muskular.api.APICallback}
     *
     * @param data The data object returned by the API
     */
    public abstract void onSuccess(E data);

    /**
     * This method is called if the API returns an unsuccessful response code. The error message
     * corresponding to the response code is fetched from the strings.xml file
     * @param errorMessage
     */
    public abstract void onFailure(String errorMessage);

    /**
     * This method is called before showing the no internet connection dialog, so that any cleanups
     * can be performed
     */
    public void onNetworkFailure() {}

    /**
     * This method is called to retry the request after showing the no internet connection dialog
     * to the user
     */
    public void retryRequest() {}

    /**
     * In Retrofit, {@code success} is called when the API returns a 200. Whereas, Loki API returns a 200
     * even in certain error cases and passes a response code which indicates the type of error.
     *
     * This method processes any responses from the server and if successful, calls the {@code onSuccess}
     * method with the data. If there is an error, it maps the response code from the server to a
     * corresponding error message and calls {@code onFailure} with an error message.
     *
     * @param serviceResponse The response object returned from the service
     * @param response The raw response data returned by Retrofit
     */
    @Override
    public final void success(T serviceResponse, Response response) {
        BasicResponse basicResponse = (BasicResponse) serviceResponse;
        Log.v("RESPONSE CODE - ", basicResponse.getResponseCode());
        //Check the response code and fetch the corresponding error
        if (!basicResponse.getResponseCode().equalsIgnoreCase(RESPONSE_CODE_SUCCESS)){
            //Map the response code to an error code
//            String errorMessage = Utils.getStringResource(_context, "response_code_" + basicResponse.getResponseCode());
            String errorMessage;
            try {
                errorMessage = basicResponse.getErrMsg();
            }catch (Exception e){
                errorMessage = null;
                Log.v("RESPONSE ERROR EXCPTN",e.getMessage());
            }
            Log.v("RESPONSE ERROR MSG - ",errorMessage);
            errorMessage = (errorMessage == null || errorMessage.equalsIgnoreCase(""))? "Unexpected error" : errorMessage;
            onFailure(errorMessage);

            return;
        }

        //Call onSuccess to return the data
        onSuccess((E) basicResponse.getData());
    }

    /**
     * In Retrofit, {@code failure} is called when the API returns a non 200 code. In case of non 200
     * code, this method checks if the device has internet connectivity. If not, it displays an
     * alert dialog to the user with option to try again.
     *
     * The {@code onNetworkFailure} method is called before showing any dialogs to the user, so that
     * any cleanups in that activity can be performed. If the failure was due to no internet
     * connectivity, the {@code retryRequest} method is called
     *
     * @param error The error object passed by Retrofit
     */
    @Override
    public final void failure(RetrofitError error) {
//        onNetworkFailure(); //To perform cleanup

        //Map the HTTP error to a string and show it
        System.out.println(error.toString());
        Toast.makeText(_context, "Connection Lost. Try again", Toast.LENGTH_SHORT).show();
    }
}
