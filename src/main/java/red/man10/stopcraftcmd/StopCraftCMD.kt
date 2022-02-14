package red.man10.stopcraftcmd

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

class StopCraftCMD : JavaPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private val craftingList = mutableListOf(
        InventoryType.WORKBENCH,
        InventoryType.ANVIL,
        InventoryType.SMITHING,
        InventoryType.FURNACE,
        InventoryType.BEACON,
        InventoryType.ENCHANTING,
        InventoryType.GRINDSTONE,
        InventoryType.SMITHING,
        InventoryType.BREWING,
        InventoryType.BLAST_FURNACE
    )

    @EventHandler
    fun clickEvent(e: InventoryClickEvent) {
        if (!craftingList.contains(e.view.type)) return

        if (e.clickedInventory != e.whoClicked.inventory)return

        if (e.action == InventoryAction.HOTBAR_SWAP){
            e.isCancelled = true
            return
        }

        val item = e.currentItem?:return

        if (!item.hasItemMeta()) return

        if (item.itemMeta.hasCustomModelData() && item.itemMeta.customModelData != 0) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun craftEvent(e: CraftItemEvent){

        val items = e.inventory.storageContents

        for (item in items){
            if (item.type == Material.AIR)continue
            if (!item.hasItemMeta())continue
            if (item.isSimilar(e.recipe.result))continue

            if (item.itemMeta.hasCustomModelData() && item.itemMeta.customModelData!=0){
                e.isCancelled = true
                return
            }

        }

    }
}