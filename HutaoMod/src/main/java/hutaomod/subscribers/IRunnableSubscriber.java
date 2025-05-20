package hutaomod.subscribers;

public interface IRunnableSubscriber extends IHuTaoSubscriber {
    void run();
    
    SubscriptionManager.RunnableType getSubType();
}
