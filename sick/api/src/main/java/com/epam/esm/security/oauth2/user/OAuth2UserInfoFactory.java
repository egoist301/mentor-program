package com.epam.esm.security.oauth2.user;


import com.epam.esm.security.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public final class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(SupportedAuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(SupportedAuthProvider.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

    private OAuth2UserInfoFactory() {
    }
}
