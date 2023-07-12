package workoutApp.myapplication

object Constants {
    fun defaultExerciseList(): ArrayList<Model> {
        val exerciseList = ArrayList<Model>()

        val jumpingJacks = Model(
            1, "Jumping-Jacks", R.drawable.jumpingjake, false, false)
        exerciseList.add(jumpingJacks)

        val abdominalCrunch = Model(
            2, "Abdominal-Crunch", R.drawable.abdominalcrunch, false, false)
        exerciseList.add(abdominalCrunch)

        val highKneeRunningInPlace = Model(
            3,"High-knee-Jump", R.drawable.highkneejump, false, false)
        exerciseList.add(highKneeRunningInPlace)

        val lunge = Model(
            4, "Lunge", R.drawable.lunge, false, false        )
        exerciseList.add(lunge)

        val plank = Model(
            5, "Plank", R.drawable.plank, false, false        )
        exerciseList.add(plank)

        val pushup = Model(
            6, "Push-up", R.drawable.pushup, false, false        )
        exerciseList.add(pushup)

        val pushUpandRotation = Model(
            7, "Push-up_rotation", R.drawable.pushup_and_rotation, false, false        )
        exerciseList.add(pushUpandRotation)

        val sideplank = Model(
            8, "Side-Plank",R.drawable.side_plank, false, false        )
        exerciseList.add(sideplank)

        val squat = Model(
            9, "Squat", R.drawable.squat, false, false        )
        exerciseList.add(squat)

        val stepUpUponChair = Model(
            10, "Step-Up-Upon_Chair", R.drawable.stepup_upon_to_chair, false, false        )
        exerciseList.add(stepUpUponChair)

        val tricepDipOnchair = Model(
            11, "Triceps-Dip-OnChair", R.drawable.triceps_dip_on_chair, false, false        )
        exerciseList.add(tricepDipOnchair)

        val wallSit = Model(
            12, "Wall-Sit", R.drawable.wall_sit, false, false     )
        exerciseList.add(wallSit)

        return  exerciseList
    }
}