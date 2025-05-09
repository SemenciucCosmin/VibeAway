package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.Activity

interface ActivitiesDataSource {
    fun getActivities(): List<Activity>
}
