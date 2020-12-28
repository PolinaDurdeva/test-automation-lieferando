package de.liferando.api.testdata;

public interface TestDataSpecification {

    String happyChickenMessage();

    String chickenIsFullMessage();

    String collectedEggsMessage(int eggsNumber);

    String failToCollectEggsMessage();

    String dailyCollectedEggsMessage(int eggsNumber);

}
