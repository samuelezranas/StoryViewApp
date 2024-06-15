package com.dicoding.storyviewapp

import com.dicoding.storyviewapp.data.remote.response.ListStoryItem

object DataDummy {
    fun generateDummyListStoryResponse(): List<ListStoryItem>{
        val listStory = ArrayList<ListStoryItem>()
        for (i in 0..100){
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "test $i",
                "test $i",
                -16.002,
                "id $i",
                -10.212
            )
            listStory.add(story)
        }
        return listStory
    }
}