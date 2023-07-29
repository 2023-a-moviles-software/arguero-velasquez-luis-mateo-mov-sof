package com.luism.tarearecycler

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

data class Video(
    val title:String,
    val uploader:String,
    var views:Int,
    val uploadTime: Instant,
    val lengthSeconds:Int,
    val tags: Array<String>
){
    companion object{
        val collection = arrayListOf<Video>(
            Video(
                "r/Maliciouscompliance Stupid Karen Ruins Her Own Party!",
                "rSlash",
                538_165,
                LocalDate.of(2022  ,2,8).atStartOfDay().toInstant(ZoneOffset.UTC),
                1878,
                arrayOf("All")
            ),
            Video(
                "RED: Shadow Maggots [Trailer Parody]",
                "Eltorro64Sus",
                901,
                LocalDate.of(2020,7,4).atStartOfDay().toInstant(ZoneOffset.UTC),
                21,
                arrayOf("All","Gaming","Team Fortress 2","Animated films")
            ),
            Video(
                "Like My Coffee | Game Changer [Full Episode]",
                "CollegeHumor",
                1_091_346,
                LocalDate.of(2023,5,10).atStartOfDay().toInstant(ZoneOffset.UTC),
                1878,
                arrayOf("All","Sitcoms")
            ),
            Video(
                "It's Raining Antibiotic Resistant Bacteria",
                "SciShow",
                770,
                LocalDate.of(2023,7,14).atStartOfDay().toInstant(ZoneOffset.UTC),
                393,
                arrayOf("All",)
            ),
            Video(
                "GTA IV Chat Voting Chaos Mod Highlights",
                "Hugo One",
                492,
                LocalDate.of(2023,7,14).atStartOfDay().toInstant(ZoneOffset.UTC),
                665,
                arrayOf("All","Gaming")
            ),
            Video(
                "Sam Reich Launches Dropout America | Breaking News [Full Episode] ",
                "CollegeHumor",
                1_368_956,
                LocalDate.of(2022,11,23).atStartOfDay().toInstant(ZoneOffset.UTC),
                632,
                arrayOf("All","Sitcoms")
            ),
            Video(
                "Dr Brights Has Event Servers Now - SCP:SL",
                "Baron Chet",
                2_349,
                LocalDate.of(2023,7,14).atStartOfDay().toInstant(ZoneOffset.UTC),
                710,
                arrayOf("All","Gaming")
            ),
            Video(
                "Youtube video game essayist",
                "Jeaney Collects",
                24_535,
                LocalDate.of(2023,7,14).atStartOfDay().toInstant(ZoneOffset.UTC),
                41,
                arrayOf("All")
            ),
            Video(
                "i’m so excited about my super weapon that will take over boom beach",
                "The Moist One",
                440_520,
                LocalDate.of(2020,11,14).atStartOfDay().toInstant(ZoneOffset.UTC),
                6,
                arrayOf("All","Gaming")
            ),
            Video(
                "We Can't Solve this until we Invent New Words",
                "sirrandalot",
                52_113,
                LocalDate.of(2021,7,27).atStartOfDay().toInstant(ZoneOffset.UTC),
                271,
                arrayOf("All","Animated films")
            ),
        )
    }
}