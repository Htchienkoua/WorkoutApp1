package com.blackdiamondstudios.android.a7minworkoutapp

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>
    {
        val exerciseList = ArrayList<ExerciseModel>()

        val Bridge_pose = ExerciseModel(
            1,
            "BRIDGE POSE EXERCISE",
            R.drawable.bridge_pose,
            false,
            false
        )
        exerciseList.add(Bridge_pose)

        val Warrior_pose = ExerciseModel(
            2,
            "WARRIOR POSE EXERCISE",
            R.drawable.warrior_pose,
            false,
            false

        )
        exerciseList.add(Warrior_pose)

        val Forward_fold_pose = ExerciseModel(
            3,
            "FORWARD FOLD POSE EXERCISE",
            R.drawable.forward_fold_pose,
            false,
            false
        )
        exerciseList.add(Forward_fold_pose)

        val Plank_pose = ExerciseModel(
            4,
            "PLANK POSE EXERCISE",
            R.drawable.plank_pose,
            false,
            false
        )
        exerciseList.add(Plank_pose)

        val Bow_pose = ExerciseModel(
            5,
            "BOW POSE EXERCISE",
            R.drawable.bow_pose,
            false ,
            false
        )
        exerciseList.add(Bow_pose)

        val Bound_angle_pose = ExerciseModel(
            6,
            "BOUND ANGLE POSE EXERCISE",
            R.drawable.bound_angle_pose,
            false,
            false
        )
        exerciseList.add(Bound_angle_pose)

        val Cobra_stretch = ExerciseModel(
            7,
            "COBRA STRETCH POSE EXERCISE",
            R.drawable.cobra_pose,
            false,
            false
        )
        exerciseList.add(Cobra_stretch)

        val Spine_lumbar_stretch_left = ExerciseModel(
            8,
            "SPINE LUMBAR STRETCH LEFT EXERCISE",
            R.drawable.spine_lumbar_stretch_pose,
            false,
            false
        )
        exerciseList.add(Spine_lumbar_stretch_left)

        val Spine_lumbar_stretch_right = ExerciseModel(
            9,
            "SPINE LUMBAR STRETCH RIGHT EXERCISE",
            R.drawable.spine_lumbar_stretch_right_pose,
            false,
            false
        )
        exerciseList.add(Spine_lumbar_stretch_right)

        val Childs_pose = ExerciseModel(
            10,
            "CHILD'S POSE EXERCISE",
            R.drawable.child_pose,
            false,
            false
        )
        exerciseList.add(Childs_pose)

        val Side_plank_left = ExerciseModel(
            11,
            "SIDE PLANK LEFT EXERCISE",
            R.drawable.side_plank_left_pose,
            false,
            false
        )
        exerciseList.add(Side_plank_left)

        val Side_plank_right = ExerciseModel(
            12,
            "SIDE PLANK RIGHT EXERCISE",
            R.drawable.side_plank_right_pose,
            false,
            false
        )
        exerciseList.add(Side_plank_right)


        return exerciseList
    }
}