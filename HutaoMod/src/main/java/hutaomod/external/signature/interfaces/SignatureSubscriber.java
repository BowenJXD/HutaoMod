package hutaomod.external.signature.interfaces;

public interface SignatureSubscriber {
	default void receiveOnSignatureUnlocked(String id, boolean unlock) {}

	default void receiveOnSignatureEnabled(String id, boolean enable) {}
}
