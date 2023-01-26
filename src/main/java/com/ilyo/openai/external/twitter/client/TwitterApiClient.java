package com.ilyo.openai.external.twitter.client;

import com.ilyo.openai.external.twitter.dto.TweetReply;
import com.ilyo.openai.external.twitter.dto.request.TweetCreationRequest;
import com.ilyo.openai.external.twitter.dto.request.TweetReplyRequest;
import com.ilyo.openai.external.twitter.dto.response.AccessTokenResponse;
import com.ilyo.openai.external.twitter.dto.response.LatestTweetsResponse;
import com.ilyo.openai.external.twitter.dto.response.TweetCreationResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.Instant;

public interface TwitterApiClient {

    @GET("tweets/search/recent?expansions=author_id&tweet.fields=created_at")
    Call<LatestTweetsResponse> getPublicLatestTweets(@Header("Authorization") String authorization,
                                                     @Query("query") String query,
                                                     @Query("max_results") String maxResult,
                                                     @Query("start_time") Instant startTime);

    @POST("oauth2/token")
    @FormUrlEncoded
    Call<AccessTokenResponse> getAccessToken(@Header("Authorization") String authorization,
                                             @Field("code") String code,
                                             @Field("grant_type") String grantType,
                                             @Field("client_id") String clientId,
                                             @Field("code_verifier") String codeVerifier,
                                             @Field("redirect_uri") String redirectUri);

    @POST("tweets")
    Call<TweetCreationResponse> createTweet(@Header("Authorization") String bearerToken,
                                            @Body TweetCreationRequest tweetCreationRequest);

    @POST("tweets")
    Call<TweetCreationResponse> reply(@Header("Authorization") String bearerToken,
                                            @Body TweetReplyRequest tweetReplyRequest);
}
