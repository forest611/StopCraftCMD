package red.man10.stopcraftcmd

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.InventoryClickEvent
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
        InventoryType.SMITHING
    )

    @EventHandler
    fun clickEvent(e: InventoryClickEvent) {
        if (!craftingList.contains(e.inventory.type)) return
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

            if (item.itemMeta.hasCustomModelData() && item.itemMeta.customModelData!=0){
                e.isCancelled = true
                return
            }

        }


    }
}