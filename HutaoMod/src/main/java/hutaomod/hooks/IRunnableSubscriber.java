package hutaomod.hooks;

public interface IRunnableSubscriber extends IHuTaoSubscriber {
    void run();
    
    SubscriptionManager.RunnableType getSubType();
}
