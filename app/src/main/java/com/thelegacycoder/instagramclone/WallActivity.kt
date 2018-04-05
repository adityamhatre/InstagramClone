package com.thelegacycoder.instagramclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.StorageReference


class WallActivity : AppCompatActivity() {

    val storageRef by lazy { FirebaseStorage.getInstance().reference }
    val loggedInUser by lazy { intent.getParcelableExtra<User>("lu") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall)


        chooseImage()
    }

    private fun uploadImage(data: Uri?) {
        storageRef.child(loggedInUser.username).child(data?.path?.substringAfterLast("/")!!).putFile(data).addOnCompleteListener {
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    private val PICK_IMAGE_REQUEST: Int = 232

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST) {
            uploadImage(data?.data)
        }
    }
}
