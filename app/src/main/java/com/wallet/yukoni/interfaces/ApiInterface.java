package com.wallet.yukoni.interfaces;

import com.wallet.yukoni.models.BuyTokenRequest;
import com.wallet.yukoni.models.BuyTokenResponse;
import com.wallet.yukoni.models.ChangePasswordRequest;
import com.wallet.yukoni.models.ChangePasswordResponse;
import com.wallet.yukoni.models.ConvertCurrency;
import com.wallet.yukoni.models.DisablePinCode;
import com.wallet.yukoni.models.ForgotPassword;
import com.wallet.yukoni.models.GetBalanceResponse;
import com.wallet.yukoni.models.LoginRequest;
import com.wallet.yukoni.models.LoginResponse;
import com.wallet.yukoni.models.NewPinCodeResponse;
import com.wallet.yukoni.models.PinCodeResponse;
import com.wallet.yukoni.models.SendTokenRequest;
import com.wallet.yukoni.models.SendTokenResponse;
import com.wallet.yukoni.models.SendTransactionRequest;
import com.wallet.yukoni.models.SendTransactionResponse;
import com.wallet.yukoni.models.SignUpRequest;
import com.wallet.yukoni.models.SignUpResponse;
import com.wallet.yukoni.models.TransactionFee;
import com.wallet.yukoni.models.UserTransactions;
import com.wallet.yukoni.models.VerifyEmailResponse;
import com.wallet.yukoni.utils.SingletonClass;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    //http:192.168.1.21:3001/api/
    //https://grand.sfccadmin.com/api/
    static ApiInterface getInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + SingletonClass.gettoken)
                    .build();
            return chain.proceed(newRequest);
        }).addInterceptor(interceptor).build();
        return new Retrofit.Builder().baseUrl("https://grand.sfccadmin.com/api/").client(client).addConverterFactory(GsonConverterFactory.create()).build().create(ApiInterface.class);
    }

    @POST("auth/register")
    Call<SignUpResponse> createAccount(@Body SignUpRequest data);

    @POST("auth/login")
    Call<LoginResponse> userLogin(@Body LoginRequest data);

    @FormUrlEncoded
    @POST("user/enablePin")
    Call<PinCodeResponse> pincode(@Field("pinCode") String data);

    @FormUrlEncoded
    @POST("user/changePinCode")
    Call<NewPinCodeResponse> newPinCode(@Field("newPinCode") String data, @Field("oldPinCode") String pincode);

    @FormUrlEncoded
    @POST("user/disablePin")
    Call<DisablePinCode> disablePincode(@Field("pinCode") String data);

    @POST("address/getBalance")
    Call<GetBalanceResponse> getBalance();

    @POST("transation/sendTransaction")
    Call<SendTransactionRequest> sendTransaction(@Body SendTransactionResponse data);

    @POST("token/buy")
    Call<BuyTokenResponse> buyToken(@Body BuyTokenRequest data);

    @POST("token/info")
    Call<TransactionFee> transactionFee();

    @POST("users/transactions")
    Call<UserTransactions> userTransactions();

    @POST("token/values")
    Call<ConvertCurrency> currencyConverter();

    @POST("token/send")
    Call<SendTokenResponse> sendToken(@Body SendTokenRequest data);

    @POST("user/resetPassword")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest data);

    @FormUrlEncoded
    @POST("user/sendForgotPasswordMail")
    Call<ForgotPassword> forgotPassword(@Field("email") String data);

    @FormUrlEncoded
    @POST("auth/resendEmail")
    Call<VerifyEmailResponse> verifyEmail(@Field("email") String data);

}

