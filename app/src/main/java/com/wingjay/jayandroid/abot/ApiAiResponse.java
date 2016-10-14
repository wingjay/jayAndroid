package com.wingjay.jayandroid.abot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 10/14/16.
 */
public class ApiAiResponse {
  
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("timestamp")
  @Expose
  private String timestamp;
  @SerializedName("result")
  @Expose
  private Result result;
  @SerializedName("status")
  @Expose
  private Status status;
  @SerializedName("sessionId")
  @Expose
  private String sessionId;
  
  /**
   *
   * @return
   * The id
   */
  public String getId() {
    return id;
  }
  
  /**
   *
   * @param id
   * The id
   */
  public void setId(String id) {
    this.id = id;
  }
  
  public ApiAiResponse withId(String id) {
    this.id = id;
    return this;
  }
  
  /**
   *
   * @return
   * The timestamp
   */
  public String getTimestamp() {
    return timestamp;
  }
  
  /**
   *
   * @param timestamp
   * The timestamp
   */
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
  
  public ApiAiResponse withTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }
  
  /**
   *
   * @return
   * The result
   */
  public Result getResult() {
    return result;
  }
  
  /**
   *
   * @param result
   * The result
   */
  public void setResult(Result result) {
    this.result = result;
  }
  
  public ApiAiResponse withResult(Result result) {
    this.result = result;
    return this;
  }
  
  /**
   *
   * @return
   * The status
   */
  public Status getStatus() {
    return status;
  }
  
  /**
   *
   * @param status
   * The status
   */
  public void setStatus(Status status) {
    this.status = status;
  }
  
  public ApiAiResponse withStatus(Status status) {
    this.status = status;
    return this;
  }
  
  /**
   *
   * @return
   * The sessionId
   */
  public String getSessionId() {
    return sessionId;
  }
  
  /**
   *
   * @param sessionId
   * The sessionId
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public ApiAiResponse withSessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }
  
  private class Status {
    
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("errorType")
    @Expose
    private String errorType;
    
    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
      return code;
    }
    
    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
      this.code = code;
    }
    
    public Status withCode(Integer code) {
      this.code = code;
      return this;
    }
    
    /**
     *
     * @return
     * The errorType
     */
    public String getErrorType() {
      return errorType;
    }
    
    /**
     *
     * @param errorType
     * The errorType
     */
    public void setErrorType(String errorType) {
      this.errorType = errorType;
    }
    
    public Status withErrorType(String errorType) {
      this.errorType = errorType;
      return this;
    }
    
  }
  private class Context {
    
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parameters")
    @Expose
    private Parameters_ parameters;
    @SerializedName("lifespan")
    @Expose
    private Integer lifespan;
    
    /**
     *
     * @return
     * The name
     */
    public String getName() {
      return name;
    }
    
    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
      this.name = name;
    }
    
    public Context withName(String name) {
      this.name = name;
      return this;
    }
    
    /**
     *
     * @return
     * The parameters
     */
    public Parameters_ getParameters() {
      return parameters;
    }
    
    /**
     *
     * @param parameters
     * The parameters
     */
    public void setParameters(Parameters_ parameters) {
      this.parameters = parameters;
    }
    
    public Context withParameters(Parameters_ parameters) {
      this.parameters = parameters;
      return this;
    }
    
    /**
     *
     * @return
     * The lifespan
     */
    public Integer getLifespan() {
      return lifespan;
    }
    
    /**
     *
     * @param lifespan
     * The lifespan
     */
    public void setLifespan(Integer lifespan) {
      this.lifespan = lifespan;
    }
    
    public Context withLifespan(Integer lifespan) {
      this.lifespan = lifespan;
      return this;
    }
    
  }
  private class Fulfillment {
    
    @SerializedName("speech")
    @Expose
    private String speech;
    
    /**
     *
     * @return
     * The speech
     */
    public String getSpeech() {
      return speech;
    }
    
    /**
     *
     * @param speech
     * The speech
     */
    public void setSpeech(String speech) {
      this.speech = speech;
    }
    
    public Fulfillment withSpeech(String speech) {
      this.speech = speech;
      return this;
    }
    
  }
  private class Metadata {
    
    @SerializedName("intentId")
    @Expose
    private String intentId;
    @SerializedName("webhookUsed")
    @Expose
    private String webhookUsed;
    @SerializedName("intentName")
    @Expose
    private String intentName;
    
    /**
     *
     * @return
     * The intentId
     */
    public String getIntentId() {
      return intentId;
    }
    
    /**
     *
     * @param intentId
     * The intentId
     */
    public void setIntentId(String intentId) {
      this.intentId = intentId;
    }
    
    public Metadata withIntentId(String intentId) {
      this.intentId = intentId;
      return this;
    }
    
    /**
     *
     * @return
     * The webhookUsed
     */
    public String getWebhookUsed() {
      return webhookUsed;
    }
    
    /**
     *
     * @param webhookUsed
     * The webhookUsed
     */
    public void setWebhookUsed(String webhookUsed) {
      this.webhookUsed = webhookUsed;
    }
    
    public Metadata withWebhookUsed(String webhookUsed) {
      this.webhookUsed = webhookUsed;
      return this;
    }
    
    /**
     *
     * @return
     * The intentName
     */
    public String getIntentName() {
      return intentName;
    }
    
    /**
     *
     * @param intentName
     * The intentName
     */
    public void setIntentName(String intentName) {
      this.intentName = intentName;
    }
    
    public Metadata withIntentName(String intentName) {
      this.intentName = intentName;
      return this;
    }
    
  }
  private class Parameters {
    
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("user_name")
    @Expose
    private String userName;
    
    /**
     *
     * @return
     * The city
     */
    public String getCity() {
      return city;
    }
    
    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
      this.city = city;
    }
    
    public Parameters withCity(String city) {
      this.city = city;
      return this;
    }
    
    /**
     *
     * @return
     * The userName
     */
    public String getUserName() {
      return userName;
    }
    
    /**
     *
     * @param userName
     * The user_name
     */
    public void setUserName(String userName) {
      this.userName = userName;
    }
    
    public Parameters withUserName(String userName) {
      this.userName = userName;
      return this;
    }
    
  }
  private class Parameters_ {
    
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("city.original")
    @Expose
    private String cityOriginal;
    @SerializedName("user_name.original")
    @Expose
    private String userNameOriginal;
    
    /**
     *
     * @return
     * The city
     */
    public String getCity() {
      return city;
    }
    
    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
      this.city = city;
    }
    
    public Parameters_ withCity(String city) {
      this.city = city;
      return this;
    }
    
    /**
     *
     * @return
     * The userName
     */
    public String getUserName() {
      return userName;
    }
    
    /**
     *
     * @param userName
     * The user_name
     */
    public void setUserName(String userName) {
      this.userName = userName;
    }
    
    public Parameters_ withUserName(String userName) {
      this.userName = userName;
      return this;
    }
    
    /**
     *
     * @return
     * The cityOriginal
     */
    public String getCityOriginal() {
      return cityOriginal;
    }
    
    /**
     *
     * @param cityOriginal
     * The city.original
     */
    public void setCityOriginal(String cityOriginal) {
      this.cityOriginal = cityOriginal;
    }
    
    public Parameters_ withCityOriginal(String cityOriginal) {
      this.cityOriginal = cityOriginal;
      return this;
    }
    
    /**
     *
     * @return
     * The userNameOriginal
     */
    public String getUserNameOriginal() {
      return userNameOriginal;
    }
    
    /**
     *
     * @param userNameOriginal
     * The user_name.original
     */
    public void setUserNameOriginal(String userNameOriginal) {
      this.userNameOriginal = userNameOriginal;
    }
    
    public Parameters_ withUserNameOriginal(String userNameOriginal) {
      this.userNameOriginal = userNameOriginal;
      return this;
    }
    
  }
  public class Result {
    
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("resolvedQuery")
    @Expose
    private String resolvedQuery;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("actionIncomplete")
    @Expose
    private Boolean actionIncomplete;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("contexts")
    @Expose
    private List<Context> contexts = new ArrayList<Context>();
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("fulfillment")
    @Expose
    private Fulfillment fulfillment;
    @SerializedName("score")
    @Expose
    private Integer score;
    
    /**
     *
     * @return
     * The source
     */
    public String getSource() {
      return source;
    }
    
    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
      this.source = source;
    }
    
    public Result withSource(String source) {
      this.source = source;
      return this;
    }
    
    /**
     *
     * @return
     * The resolvedQuery
     */
    public String getResolvedQuery() {
      return resolvedQuery;
    }
    
    /**
     *
     * @param resolvedQuery
     * The resolvedQuery
     */
    public void setResolvedQuery(String resolvedQuery) {
      this.resolvedQuery = resolvedQuery;
    }
    
    public Result withResolvedQuery(String resolvedQuery) {
      this.resolvedQuery = resolvedQuery;
      return this;
    }
    
    /**
     *
     * @return
     * The action
     */
    public String getAction() {
      return action;
    }
    
    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
      this.action = action;
    }
    
    public Result withAction(String action) {
      this.action = action;
      return this;
    }
    
    /**
     *
     * @return
     * The actionIncomplete
     */
    public Boolean getActionIncomplete() {
      return actionIncomplete;
    }
    
    /**
     *
     * @param actionIncomplete
     * The actionIncomplete
     */
    public void setActionIncomplete(Boolean actionIncomplete) {
      this.actionIncomplete = actionIncomplete;
    }
    
    public Result withActionIncomplete(Boolean actionIncomplete) {
      this.actionIncomplete = actionIncomplete;
      return this;
    }
    
    /**
     *
     * @return
     * The parameters
     */
    public Parameters getParameters() {
      return parameters;
    }
    
    /**
     *
     * @param parameters
     * The parameters
     */
    public void setParameters(Parameters parameters) {
      this.parameters = parameters;
    }
    
    public Result withParameters(Parameters parameters) {
      this.parameters = parameters;
      return this;
    }
    
    /**
     *
     * @return
     * The contexts
     */
    public List<Context> getContexts() {
      return contexts;
    }
    
    /**
     *
     * @param contexts
     * The contexts
     */
    public void setContexts(List<Context> contexts) {
      this.contexts = contexts;
    }
    
    public Result withContexts(List<Context> contexts) {
      this.contexts = contexts;
      return this;
    }
    
    /**
     *
     * @return
     * The metadata
     */
    public Metadata getMetadata() {
      return metadata;
    }
    
    /**
     *
     * @param metadata
     * The metadata
     */
    public void setMetadata(Metadata metadata) {
      this.metadata = metadata;
    }
    
    public Result withMetadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }
    
    /**
     *
     * @return
     * The fulfillment
     */
    public Fulfillment getFulfillment() {
      return fulfillment;
    }
    
    /**
     *
     * @param fulfillment
     * The fulfillment
     */
    public void setFulfillment(Fulfillment fulfillment) {
      this.fulfillment = fulfillment;
    }
    
    public Result withFulfillment(Fulfillment fulfillment) {
      this.fulfillment = fulfillment;
      return this;
    }
    
    /**
     *
     * @return
     * The score
     */
    public Integer getScore() {
      return score;
    }
    
    /**
     *
     * @param score
     * The score
     */
    public void setScore(Integer score) {
      this.score = score;
    }
    
    public Result withScore(Integer score) {
      this.score = score;
      return this;
    }
  }
}




