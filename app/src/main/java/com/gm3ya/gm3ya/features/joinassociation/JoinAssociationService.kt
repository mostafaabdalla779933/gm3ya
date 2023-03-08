package com.gm3ya.gm3ya.features.joinassociation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.buildingmaterials.buildingmaterials.common.showMessage
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.common.firebase.data.NotificationType
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat

class JoinAssociationService : JobIntentService() {

    var isFinished = false
    var isFailed = false
    var userModel:UserModel? = null
    var association:AssociationModel?= null
    var isChoosePlace = false
    var place :Int = 1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        progress()
        upload(intent)
        stopSelf()
        return Service.START_STICKY
    }

    override fun onHandleWork(intent: Intent) {}


    private fun upload(intent: Intent?){
        userModel = intent?.extras?.getParcelable<UserModel>(FirebaseHelp.USERS)
        association = intent?.extras?.getParcelable<AssociationModel>(FirebaseHelp.ASSOCIATION)
        isChoosePlace = intent?.extras?.getBoolean(JoinAssociationFragment.isChoosePlace) ?: false
        userModel?.profileUri?.let{
            place = userModel?.place ?: 1
            uploadImage(it)
        }
    }

    private fun uploadImage(uri: Uri){
        FirebaseHelp.uploadImageToCloudStorage(this,uri,"user",{
            userModel?.profileUrl = it
            userModel?.frontUri?.let {
                uploadFrontImage(it)
            }

        },{
            isFailed = true
            showMessage(it.localizedMessage ?: "something wrong")
        })
    }


    private fun uploadFrontImage(uri: Uri){
        FirebaseHelp.uploadImageToCloudStorage(this,uri,"user",{
            userModel?.frontUrl = it
            userModel?.backUri?.let {
                uploadBackImage(it)
            }
        },{
            isFailed = true
            showMessage(it.localizedMessage ?: "something wrong")
        })
    }

    private fun uploadBackImage(uri: Uri){
        FirebaseHelp.uploadImageToCloudStorage(this,uri,"user",{
            userModel?.backUrl = it
            addUser()
        },{
            isFailed = true
            showMessage(it.localizedMessage ?: "something wrong")
        })
    }

    private fun addUser(){

        userModel?.let{ userModel ->
            userModel.profileUri = null
            userModel.backUri = null
            userModel.frontUri = null
            FirebaseHelp
                .fireStore.collection(FirebaseHelp.USERS)
                .document(userModel.userId ?: "").set(userModel, SetOptions.merge())
                .addOnSuccessListener {
                    isFinished = true
                    addNotification(userModel)
                    showMessage("data sent")
                }.addOnFailureListener { e ->
                    isFailed = true
                    showMessage("failed  ${e.localizedMessage}")
                }


        }


    }

    private fun addNotification(userModel: UserModel){
        val notificationModel = NotificationModel(
            title = "${userModel.userName} want to join Association",
            type = NotificationType.RequestAssociation.value,
            from = userModel.also { it.place = place },
            fromId = userModel.userId,
            hash = System.currentTimeMillis(),
            date = SimpleDateFormat("dd MMM yyyy").format(System.currentTimeMillis()),
            toUserId = "ELaaIYVVubN6iN9zO9Jy74rdKCi1",
            choosePlace = isChoosePlace,
            associationModel = association,
            place = place
        )

        FirebaseHelp.addObject<NotificationModel>(
            notificationModel,
            FirebaseHelp.NOTIFICATION,
            notificationModel.hash.toString(),
            {},
            {})

    }


    private fun progress() {
        val  progressMax = 100

        val notificationManager =  this.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "Channel_id_progress", "Channel_name_progress", NotificationManager.IMPORTANCE_LOW
            )


            notificationChannel.description = "Channel_description_progress"
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, "Channel_id_progress")


        notificationBuilder.setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setTicker(resources.getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("uploading your data")
            .setContentText("upload in progress")
            .setOnlyAlertOnce(true)
            .setProgress(progressMax, 0, true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(101, notificationBuilder.build())
        }else{
            notificationManager.notify(101, notificationBuilder.build())
        }



        Thread{
            SystemClock.sleep(2000)
            var progress = 0
            while (progress <= progressMax) {
                if(isFinished || isFailed){
                    break
                }
                notificationBuilder.setProgress(progressMax, progress, false)
                    .setAutoCancel(false)
                notificationManager.notify(101, notificationBuilder.build())
                SystemClock.sleep(1000)
                if(progress < 80 ||(progress >= 80 && isFinished))
                    progress += 20
            }
            if(isFailed){
                notificationBuilder.setContentText("upload failed")
                    .setProgress(0, 0, false)
                    .setOngoing(false)
                    .setAutoCancel(true)
            }else {
                notificationBuilder.setContentText("upload finished")
                    .setProgress(0, 0, false)
                    .setOngoing(false)
                    .setAutoCancel(true)
            }
            notificationManager.notify(101, notificationBuilder.build())
        }.start()

    }

}