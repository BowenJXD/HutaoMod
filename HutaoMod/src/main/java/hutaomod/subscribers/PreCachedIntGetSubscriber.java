package hutaomod.subscribers;

import hutaomod.utils.CacheManager;

public interface PreCachedIntGetSubscriber extends IHuTaoSubscriber {
    int preCachedIntGet(CacheManager.Key key, int amount);
}
