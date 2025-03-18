package com.ctrip.flight.faq.qconfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TripFaqSettings {
    @JsonProperty("AppId")
    private String appId;
    
    @JsonProperty("TestAffiliateOverride")
    private String testAffiliateOverride;
    
    @JsonProperty("IsGroupTagTestStrategy")
    private boolean isGroupTagTestStrategy;
    
    @JsonProperty("CacheDurationInSeconds")
    private int cacheDurationInSeconds;
    
    @JsonProperty("Groups")
    private List<GroupSetting> groups;
    
    // Getters and Setters
    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }
    
    public String getTestAffiliateOverride() { return testAffiliateOverride; }
    public void setTestAffiliateOverride(String value) { this.testAffiliateOverride = value; }
    
    public boolean isGroupTagTestStrategy() { return isGroupTagTestStrategy; }
    public void setGroupTagTestStrategy(boolean value) { this.isGroupTagTestStrategy = value; }
    
    public int getCacheDurationInSeconds() { return cacheDurationInSeconds; }
    public void setCacheDurationInSeconds(int value) { this.cacheDurationInSeconds = value; }
    
    public List<GroupSetting> getGroups() { return groups; }
    public void setGroups(List<GroupSetting> groups) { this.groups = groups; }
    
    public static class GroupSetting {
        @JsonProperty("GroupCode")
        private String groupCode;
        
        @JsonProperty("Tag")
        private String tag;
        
        public String getGroupCode() { return groupCode; }
        public void setGroupCode(String value) { this.groupCode = value; }
        
        public String getTag() { return tag; }
        public void setTag(String value) { this.tag = value; }
    }
} 