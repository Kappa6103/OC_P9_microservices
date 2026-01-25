package com.medilabo.risk_assessment.service;

import com.medilabo.risk_assessment.model.DoctorNote;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriggerCounterTest {

    private static final String  zeroTriggers = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  oneTriggers = "anticorps Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  twoTriggers = "anticorps hémoglobine A1C Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  threeTriggers = "anticorps hémoglobine A1C vertige Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  fourTriggers = "microalbumine anticorps hémoglobine A1C cholestérol Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  fiveTriggers = "vertige microalbumine anticorps hémoglobine A1C cholestérol Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  sixTriggers = "anormal vertige microalbumine anticorps hémoglobine A1C cholestérol Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String  sevenTriggers = "rechute anormal vertige microalbumine anticorps hémoglobine A1C cholestérol Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private static final String eightTriggers = "hémoglobine A1C rechute anormal vertige microalbumine anticorps fumeur cholestérol Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";


    @Test
    void getCount_zero_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(zeroTriggers);
        int result = triggerCounter.getCount();
        assertEquals(0, result);
    }

    @Test
    void getCount_one_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(oneTriggers);
        int result = triggerCounter.getCount();
        assertEquals(1, result);
    }

    @Test
    void getCount_two_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(twoTriggers);
        int result = triggerCounter.getCount();
        assertEquals(2, result);
    }

    @Test
    void getCount_three_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(threeTriggers);
        int result = triggerCounter.getCount();
        assertEquals(3, result);
    }

    @Test
    void getCount_four_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(fourTriggers);
        int result = triggerCounter.getCount();
        assertEquals(4, result);
    }

    @Test
    void getCount_five_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(fiveTriggers);
        int result = triggerCounter.getCount();
        assertEquals(5, result);
    }

    @Test
    void getCount_six_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(sixTriggers);
        int result = triggerCounter.getCount();
        assertEquals(6, result);
    }

    @Test
    void getCount_seven_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(sevenTriggers);
        int result = triggerCounter.getCount();
        assertEquals(7, result);
    }

    @Test
    void getCount_eight_trigger() {
        TriggerCounter triggerCounter = new TriggerCounter(eightTriggers);
        int result = triggerCounter.getCount();
        assertEquals(8, result);
    }

    @Test
    void constructorThrowsWhenNoteIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TriggerCounter(null)
        );
        assertEquals("Note was null or blank", exception.getMessage());
    }
}