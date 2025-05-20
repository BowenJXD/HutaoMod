package hutaomod.subscribers;

public interface INumChangerSubscriber extends IHuTaoSubscriber {
    float changeNum(float base);

    SubscriptionManager.NumChangerType getSubType();
}
