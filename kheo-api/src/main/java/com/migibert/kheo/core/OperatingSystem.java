package com.migibert.kheo.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatingSystem {

    @JsonProperty
    public String name;   
    
    @JsonProperty
    public String kernelName;

    @JsonProperty
    public String kernelRelease;

    @JsonProperty
    public String hardwarePlatform;
}
