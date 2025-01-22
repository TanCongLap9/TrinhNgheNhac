package com.example.trinhnghenhac.api.soundcloud.models;

import java.io.Serializable;

public class SoundCloudOAuthTokenModel implements Serializable {
    public String grant_type; // authorization_code, client_credentials, refresh_token
    public String client_id;
    public String client_secret;
    public String code;
    public String redirect_uri;
    public String refresh_token;
}
