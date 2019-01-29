package com.demo.springbootapis.security.oauth2.user;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public abstract class OAuth2UserInfo {
	protected Map<String, Object> attributes;
	
	public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
    
    public String getFirstName() {
    	int index = getName().indexOf(" ");
    	return getName().substring(0, index);
    }
    
    public String getLastName() {
    	int index = getName().indexOf(" ");
    	return getName().substring(index + 1, getName().length());
    }
}
