package cc.thonly.blockuspatch.mixin;

import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.network.packet.s2c.play.RecipeBookAddS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeDisplayEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mixin to prevent recipes with polymer item ingredients from being highlighted in the recipe book.
 * <p>
 * When recipes are sent to the client, if a recipe's INGREDIENTS contain polymer items (server-side modded items),
 * this mixin removes the "highlighted" flag so that the recipe doesn't appear in the "craftable" section
 * of the recipe book in Survival mode, since the client cannot properly check if the player has these items.
 * <p>
 * Note: Recipes that only have a polymer item as the RESULT (but vanilla items as ingredients) are NOT affected,
 * because the client can correctly determine craftability based on vanilla item ingredients.
 */
@Mixin(RecipeBookAddS2CPacket.class)
public class RecipeBookAddS2CPacketMixin {
    @Shadow
    @Final
    @Mutable
    private List<RecipeBookAddS2CPacket.Entry> entries;

    @Inject(method = "<init>(Ljava/util/List;Z)V", at = @At("RETURN"))
    private void removeHighlightForPolymerItems(List<RecipeBookAddS2CPacket.Entry> entries, boolean replace, CallbackInfo ci) {
        // Only create a new list if modifications are needed
        List<RecipeBookAddS2CPacket.Entry> newEntries = null;

        for (int i = 0; i < this.entries.size(); i++) {
            RecipeBookAddS2CPacket.Entry entry = this.entries.get(i);
            if (entry.isHighlighted() && containsPolymerItemInIngredients(entry.contents())) {
                // Lazy initialization of new list only when needed
                if (newEntries == null) {
                    newEntries = new ArrayList<>(this.entries);
                }
                // Create new entry without the highlighted flag
                newEntries.set(i, new RecipeBookAddS2CPacket.Entry(entry.contents(), entry.shouldShowNotification(), false));
            }
        }

        if (newEntries != null) {
            this.entries = newEntries;
        }
    }

    private static boolean containsPolymerItemInIngredients(RecipeDisplayEntry displayEntry) {
        // Only check crafting requirements (ingredients) for polymer items
        // We do NOT check the result - if ingredients are vanilla items,
        // the client can correctly determine craftability even if the result is a polymer item
        Optional<List<Ingredient>> requirements = displayEntry.craftingRequirements();
        if (requirements.isPresent()) {
            for (Ingredient ingredient : requirements.get()) {
                if (ingredientContainsPolymerItem(ingredient)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean ingredientContainsPolymerItem(Ingredient ingredient) {
        return ingredient.getMatchingItems().anyMatch(entry ->
            PolymerItemUtils.isPolymerServerItem(entry.value())
        );
    }
}
