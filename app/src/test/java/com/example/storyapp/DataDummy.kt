package com.example.storyapp

import com.example.storyapp.response.ListStoryItem

object DataDummy {
    fun generateDummyStories() : List<ListStoryItem> {
        val listStory: ArrayList<ListStoryItem> = arrayListOf()
        for (i in 0..10){
            val story = ListStoryItem(
                id = "$i",
                name = "name $i",
                createdAt = "createdAt $i",
                lat = i.toDouble(),
                lon = i.toDouble(),
                photoUrl = "iniPhoto",
                description = "description $i"
            )
            listStory.add(story)
        }

        return listStory
    }
}