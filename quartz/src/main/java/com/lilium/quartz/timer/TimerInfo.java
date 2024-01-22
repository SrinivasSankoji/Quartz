package com.lilium.quartz.timer;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class TimerInfo {
     int totalFireCount; /* total no of times the timer has to be executed */
     int remainingFireCount; /* Get the updated count */
     boolean runForever; /* Timer will be executed forever */
     long repeatIntervalInMilliSeconds; /* For every second it will repeat*/
     long initialOffsetInMilliSeconds; /* When we start the timer how long it will wait for the timer to execute*/
     String callbackData; /* Data needs to be passed to the Job */
}
