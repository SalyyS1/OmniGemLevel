package com.salyvn.omnigemlevel.stat

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder
import net.Indyuce.mmoitems.stat.data.random.RandomStatData

class GemIdLevelRandomData(
    val data: GemIdLevelData = GemIdLevelData.EMPTY
) : RandomStatData<GemIdLevelData> {
    override fun randomize(builder: MMOItemBuilder): GemIdLevelData = data
}
