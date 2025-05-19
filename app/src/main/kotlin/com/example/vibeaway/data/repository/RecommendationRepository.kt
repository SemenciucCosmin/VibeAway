package com.example.vibeaway.data.repository

import com.example.vibeaway.data.recommendation.model.Activity
import com.example.vibeaway.data.recommendation.model.ActivityCategory
import com.example.vibeaway.data.recommendation.model.Location

/**
 * Repository responsible with computing a set of compatible [Location] with a BFI dataset, using
 * multiple datasets that include [ActivityCategory], [Activity] etc.
 */
interface RecommendationRepository
