package com.example.gym2.data.models

data class ExerciseHistoryItem(
    val exercise: Exercise? = null,
    val date: Long? = System.currentTimeMillis(),
    val exerciseVolume: List<ExerciseVolume>? = null,
    val maxWeight: Double? = exerciseVolume?.let {
        it.maxOf { volume ->
            volume.weight!!
        }
    },
    val minWeight: Double? = exerciseVolume?.let {
        it.minOf { volume ->
            volume.weight!!
        }
    },
    val totalSets: Int? = exerciseVolume?.count(),
    val totalReps: Int? = exerciseVolume?.let {
        it.sumOf { volume -> volume.reps!! }
    }
)
