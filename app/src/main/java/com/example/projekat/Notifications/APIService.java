package com.example.projekat.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAx7DRarc:APA91bH03hOOHqiozamOcFGDOAALBeyd6KTlnafBsslfSVVElEtoQqic2pwr3TzNVvRuxoYS10PQnbulGmTe5Hii1EToHteNqWxbXiPT8m4GfD-XyMfM_sWdUaOZWifBxXRwIaYywJ3I" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
