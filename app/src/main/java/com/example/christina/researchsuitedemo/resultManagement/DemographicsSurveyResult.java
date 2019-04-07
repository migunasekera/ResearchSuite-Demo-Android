package com.example.christina.researchsuitedemo.resultManagement;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.RSRPIntermediateResult;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jameskizer on 9/18/17.
 */

public class DemographicsSurveyResult extends RSRPIntermediateResult {

    public static String TYPE = "DemographicsSurvey";

//    private String travelPlans;
//    private String[] daysOnCampus;

    private String bedtime;
    private String sleeptime;
    private String fallSleepTime;
    private String wakeupCount;
    private String wakeupLength;
    private String awakeTime;
    private String outOfBedTime;
    private String sleepQuality;


    public DemographicsSurveyResult(UUID uuid,
                                    String taskIdentifier,
                                    UUID taskRunUUID,
                                    String bedtime,
                                    String sleeptime,
                                    String fallSleepTime,
                                    String wakeupCount,
                                    String wakeupLength,
                                    String awakeTime,
                                    String outOfBedTime,
                                    String sleepQuality) {
        super(TYPE, uuid, taskIdentifier, taskRunUUID);
//        this.travelPlans = travelPlans;
//        this.daysOnCampus = daysOnCampus;

        this.bedtime = bedtime;
        this.sleeptime = sleeptime;
        this.fallSleepTime = fallSleepTime;
        this.wakeupCount = wakeupCount;
        this.wakeupLength = wakeupLength;
        this.awakeTime = awakeTime;
        this.outOfBedTime = outOfBedTime;
        this.sleepQuality = sleepQuality;

    }

    public String getBedtime(){
        return bedtime;
    }
    public String getSleepTime(){
        return sleeptime;
    }
    public String getFallSleepTime(){
        return fallSleepTime;
    }
    public String getWakeupCount(){
        return wakeupCount;
    }
    public String getAwakeTime() {
        return awakeTime;
    }
    public String getWakeupLength(){
        return wakeupLength;
    }
    public String getOutOfBedTime(){
        return outOfBedTime;
    }
    public String getSleepQuality(){
        return sleepQuality;
    }


//    public String getTravelPlans() {
//        return travelPlans;
//    }
//
//    public String[] getDaysOnCampus() {
//        return daysOnCampus;
//    }

    public class FrontEndTransformer implements RSRPFrontEnd {

        public FrontEndTransformer() {}

        @Nullable
        public <T> T extractResult(Map<String, Object> parameters, String identifier) {

            Object param = parameters.get(identifier);
            if (param != null && (param instanceof StepResult)) {
                StepResult stepResult = (StepResult)param;
                if (stepResult.getResult() != null) {
                    return (T)stepResult.getResult();
                }
            }
            return null;
        }

        @Nullable
        @Override
        public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {

            String bedtime = extractResult(parameters,"bedtime");
            String sleeptime = extractResult(parameters,"sleeptime");
            String fallSleepTime = extractResult(parameters,"fallSleepTime");
            String wakeupCount = extractResult(parameters,"wakeupCount");
            String wakeupLength = extractResult(parameters,"wakeupLength");
            String awakeTime = extractResult(parameters,"awakeTime");
            String outOfBedTime = extractResult(parameters,"outOfBedTime");
            String sleepQuality = extractResult(parameters,"sleepQuality");



            DemographicsSurveyResult result = new DemographicsSurveyResult(
                    UUID.randomUUID(),
                    taskIdentifier,
                    taskRunUUID,
                    bedtime,
                    sleeptime,
                    fallSleepTime,
                    wakeupCount,
                    wakeupLength,
                    awakeTime,
                    outOfBedTime,
                    sleepQuality
//                gender,
//                age,
//                zipcode
            );

//            StepResult firstStepResult = (StepResult) (parameters.get("gender") != null ? parameters.get("gender") : parameters.get("employment"));
            StepResult firstStepResult = (StepResult) (parameters.get("bedtime") != null ? parameters.get("bedtime") : parameters.get("sleepQuality"));
//            StepResult firstStepResult = (StepResult) (parameters.get("days_on_campus") != null ? parameters.get("days_on_campus") : parameters.get("travel_plans"));
//            StepResult lastStepResult = (StepResult) (parameters.get("travel_plans") != null ? parameters.get("travel_plans") : parameters.get("days_on_campus"));

            if (firstStepResult != null) {
                result.setStartDate(firstStepResult.getStartDate());
            }
            else {
                result.setStartDate(new Date());
            }

            if (firstStepResult != null) {
                result.setEndDate(firstStepResult.getStartDate());
            }
            else {
                result.setEndDate(new Date());
            }

            return result;
        }

        @Override
        public boolean supportsType(String type) {
            return false;
        }

    }
}
