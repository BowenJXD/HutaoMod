package hutaomod.external.signature.patches.game;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import hutaomod.external.signature.SignatureLibCore;

@SuppressWarnings("unused")
public class PostPostInitializePatch {
	@SpirePatch(clz = BaseMod.class, method = "publishPostInitialize")
	public static class PostPostInitialize {
		public static void Postfix() {
			System.out.println("PostPostInitializePatch Postfix");

			SignatureLibCore.postPostInitialize();
		}
	}
}
