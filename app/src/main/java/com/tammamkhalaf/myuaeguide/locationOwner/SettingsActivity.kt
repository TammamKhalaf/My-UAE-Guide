package com.tammamkhalaf.myuaeguide.locationOwner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.common.loginSignup.Login
import com.tammamkhalaf.myuaeguide.databases.SessionManager
import com.tammamkhalaf.myuaeguide.databases.firebase.models.User
import com.tammamkhalaf.myuaeguide.databases.firebase.storage.Utility.FilePaths
import com.tammamkhalaf.myuaeguide.databases.firebase.storage.helperClass.ChangePhotoDialog
import com.tammamkhalaf.myuaeguide.databases.firebase.storage.helperClass.ChangePhotoDialog.OnPhotoReceivedListener
import java.io.ByteArrayOutputStream
import java.io.IOException

class SettingsActivity : AppCompatActivity(), OnPhotoReceivedListener {

    //firebase
    private var mAuthListener: AuthStateListener? = null

    //widgets
    private var mEmail: EditText? = null
    private val mCurrentPassword: EditText? = null
    private var mUserName: EditText? = null
    private var mPhone: EditText? = null
    private lateinit var mProfileImage: ImageView
    private var mSave: Button? = null
    private var mProgressBar: ProgressBar? = null

    //vars
    private var mStoragePermissions = false
    private var mSelectedImageUri: Uri? = null
    private var mSelectedImageBitmap: Bitmap? = null
    private var mBytes: ByteArray? = null
    private var progress = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        Log.d(TAG, "onCreate: started.")
        mEmail = findViewById(R.id.input_email)
        mSave = findViewById(R.id.btn_save)
        mProgressBar = findViewById(R.id.progressBar)
        mUserName = findViewById(R.id.input_name)
        mProfileImage = findViewById(R.id.profile_image)
        mPhone = findViewById(R.id.input_phone)

        // Create global configuration and initialize ImageLoader with this config
        //        var config: ImageLoaderConfiguration = ImageLoaderConfiguration.Builder(this).build();
        //        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(applicationContext))

        verifyStoragePermissions()
        setupFirebaseAuth()
        init()
        hideSoftKeyboard()
    }

    private fun init() {
        userAccountData
        mSave!!.setOnClickListener {
            Log.d(TAG, "onClick: attempting to save settings.")

            /*
                ------ METHOD 1 for changing database data (proper way in this scenario) -----
                 */
            val reference = FirebaseDatabase.getInstance().reference
            /*
                ------ Change Name -----
                 */if (mUserName!!.text.toString() != "") {
            reference.child(getString(R.string.dbnode_users))
                    .child(SessionManager.usersDetailFromSession["phoneNo"] ?: "0000")
                    .child("username")
                    .setValue(mUserName!!.text.toString())
        }
            /*
                ------ Change Email -----
                 */if (mEmail!!.text.toString() != "") {
            reference.child(getString(R.string.dbnode_users))
                    .child(SessionManager.usersDetailFromSession["phoneNo"] ?: "0000")
                    .child("email")
                    .setValue(mEmail!!.text.toString())
        }

            /*
                ------ Change Phone Number -----
                 */if (mPhone!!.text.toString() != "") {
            reference.child(getString(R.string.dbnode_users))
                    .child(SessionManager.usersDetailFromSession["phoneNo"] ?: "0000")
                    .child("phoneNo")
                    .setValue(mPhone?.text.toString())
        }
            Toast.makeText(this@SettingsActivity, "saved", Toast.LENGTH_SHORT).show()

            /*
                ------ Upload the New Photo -----
                 */if (mSelectedImageUri != null) {
            uploadNewPhoto(mSelectedImageUri)
        } else if (mSelectedImageBitmap != null) {
            uploadNewPhoto(mSelectedImageBitmap)
        }
        }

        mProfileImage.setOnClickListener {
            if (mStoragePermissions) {
                val dialog = ChangePhotoDialog()
                dialog.show(supportFragmentManager, getString(R.string.dialog_change_photo))
            } else {
                verifyStoragePermissions()
            }
        }
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageUri***
     * @param imageUri
     */
    private fun uploadNewPhoto(imageUri: Uri?) {
        /*
            upload a new profile photo to firebase storage
         */
        Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firebase storage.")

        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        val resize = BackgroundImageResize(null)
        resize.execute(imageUri)
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageBitmap***
     * @param imageBitmap
     */
    private fun uploadNewPhoto(imageBitmap: Bitmap?) {
        /*
            upload a new profile photo to firebase storage
         */
        Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firebase storage.")

        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        val resize = BackgroundImageResize(imageBitmap)
        val uri: Uri? = null
        resize.execute(uri)
    }

    /**
     * 1) doinBackground takes an imageUri and returns the byte array after compression
     * 2) onPostExecute will print the % compression to the log once finished
     */
    inner class BackgroundImageResize(bm: Bitmap?) : AsyncTask<Uri?, Int?, ByteArray?>() {
        private lateinit var mBitmap: Bitmap
        override fun onPreExecute() {
            super.onPreExecute()
            showDialog()
            Toast.makeText(this@SettingsActivity, "compressing image", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg params: Uri?): ByteArray? {
            Log.d(TAG, "doInBackground: started.")
            if (mBitmap == null) {
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this@SettingsActivity.contentResolver, params[0])
                    Log.d(TAG, "doInBackground: bitmap size: megabytes: " + mBitmap.byteCount / MB + " MB")
                } catch (e: IOException) {
                    Log.e(TAG, "doInBackground: IOException: ", e.cause)
                }
            }
            var bytes: ByteArray? = null
            for (i in 1..10) {
                if (i == 10) {
                    Toast.makeText(this@SettingsActivity, "That image is too large.", Toast.LENGTH_SHORT).show()
                    break
                }
                bytes = getBytesFromBitmap(mBitmap, 100 / i)
                Log.d(TAG, "doInBackground: megabytes: (" + (11 - i) + "0%) " + bytes.size / MB + " MB")
                if (bytes.size / MB < MB_THRESHHOLD) {
                    return bytes
                }
            }
            return bytes
        }

        override fun onPostExecute(bytes: ByteArray?) {
            super.onPostExecute(bytes)
            hideDialog()
            mBytes = bytes
            //execute the upload
            executeUploadTask()
        }

        init {
            if (bm != null) {
                mBitmap = bm
            }
        }

    }

    private fun executeUploadTask() {
        showDialog()
        val filePaths = FilePaths()
        //specify where the photo will be stored
        val storageReference = FirebaseStorage.getInstance().reference  //FirebaseAuth.getInstance().currentUser.phoneNumber
                .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + SessionManager.usersDetailFromSession["phoneNo"]
                        + "/profile_image") //just replace the old image with the new one
        if (mBytes!!.size / MB < MB_THRESHHOLD) {

            // Create file metadata including the content type
            val metadata = StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .setContentLanguage("en") //see nodes below
                    /*
                    Make sure to use proper language code ("English" will cause a crash)
                    I actually submitted this as a bug to the Firebase github page so it might be
                    fixed by the time you watch this video. You can check it out at https://github.com/firebase/quickstart-unity/issues/116
                     */
                    .setCustomMetadata("Mitch's special meta data", "JK nothing special here")
                    .setCustomMetadata("location", "Iceland")
                    .build()
            //if the image size is valid then we can submit to database
            var uploadTask: UploadTask? = null
            uploadTask = storageReference.putBytes(mBytes!!, metadata)
            //uploadTask = storageReference.putBytes(mBytes); //without metadata
            uploadTask.addOnSuccessListener(OnSuccessListener { taskSnapshot -> //Now insert the download url into the firebase database
                val firebaseURL: Uri? = taskSnapshot.uploadSessionUri
                Toast.makeText(this@SettingsActivity, "Upload Success", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onSuccess: firebase download url : $firebaseURL")
                FirebaseDatabase.getInstance().reference
                        .child(getString(R.string.dbnode_users))
                        .child(SessionManager.usersDetailFromSession["phoneNo"]!!)
                        .child(getString(R.string.field_profile_image))
                        .setValue(firebaseURL.toString())
                hideDialog()
            }).addOnFailureListener {
                Toast.makeText(this@SettingsActivity, "could not upload photo", Toast.LENGTH_SHORT).show()
                hideDialog()
            }.addOnProgressListener { taskSnapshot ->
                val currentProgress = (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toDouble()
                if (currentProgress > progress + 15) {
                    progress = (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toDouble()
                    Log.d(TAG, "onProgress: Upload is $progress% done")
                    Toast.makeText(this@SettingsActivity, "$progress%", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Image is too Large", Toast.LENGTH_SHORT).show()
        }
    }//this loop will return a single result


    /*
           ---------- QUERY Method 2 ----------
        */
//        Query query2 = reference.child(getString(R.string.dbnode_users))
//                .orderByChild(getString(R.string.field_user_id))
//                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        query2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //this loop will return a single result
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 2) found user: "
//                            + singleSnapshot.getValue(User.class).toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    /*
               ---------- QUERY Method 1 ----------
            */
    private val userAccountData: Unit
        get() {
            Log.d(TAG, "getUserAccountData: getting the user's account information")
            val reference = FirebaseDatabase.getInstance().reference

            /*
                   ---------- QUERY Method 1 ----------
                */
            val query1 = reference.child(getString(R.string.dbnode_users))
                    .orderByKey()
                    .equalTo(SessionManager.usersDetailFromSession["phoneNo"] ?: "0000")
            query1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //this loop will return a single result
                    for (singleSnapshot in dataSnapshot.children) {
                        Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: " + (singleSnapshot.getValue(User::class.java)?.fullName
                                ?: ""))
                        val user = singleSnapshot.getValue(User::class.java)
                        Log.d(TAG, "onDataChange: singleSnapshot user object detail phoneNo ${user?.phoneNo}")
                        mUserName?.setText(user?.fullName)
                        mPhone?.setText(user?.phoneNo)
                        mEmail?.setText(user?.email)
                        ImageLoader.getInstance().displayImage(user?.profile_img
                                ?: "", mProfileImage)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })


            /*
            ---------- QUERY Method 2 ----------
         */
//        Query query2 = reference.child(getString(R.string.dbnode_users))
//                .orderByChild(getString(R.string.field_user_id))
//                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        query2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //this loop will return a single result
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 2) found user: "
//                            + singleSnapshot.getValue(User.class).toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     */
    private fun verifyStoragePermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions.")
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[1]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.applicationContext,
                        permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            mStoragePermissions = true
        } else {
            ActivityCompat.requestPermissions(
                    this@SettingsActivity,
                    permissions,
                    REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: $requestCode")
        when (requestCode) {
            REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[0])
            }
        }
    }


    private fun showDialog() {
        mProgressBar!!.visibility = View.VISIBLE
    }

    private fun hideDialog() {
        if (mProgressBar!!.visibility == View.VISIBLE) {
            mProgressBar!!.visibility = View.INVISIBLE
        }
    }

    private fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private fun isEmpty(string: String): Boolean {
        return string == ""
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.")

        val user = FirebaseDatabase.getInstance().getReference("Users")


        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");
//
            var intent = Intent(applicationContext, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent);
            finish();

            Toast.makeText(applicationContext, "Please Login First", Toast.LENGTH_SHORT).show()

        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.")
        }
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
         */
    private fun setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.")

        mAuthListener = AuthStateListener { auth ->


            //val user = auth.currentUser.phoneNumber

//            if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    //toastMessage("Successfully signed in with: " + user.getEmail());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out")
//                    Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_SHORT).show()
//                    var intent: Intent = Intent(applicationContext, Login::class.java)
//                    startActivity(intent);
//                    finish();
//                }

        }
    }


    public override fun onStart() {
        super.onStart()
        mAuthListener?.let { FirebaseAuth.getInstance().addAuthStateListener(it) }
    }

    public override fun onPause() {
        super.onPause()
        mAuthListener?.let { FirebaseAuth.getInstance().removeAuthStateListener(it) }
    }

    override fun getImagePath(imagePath: Uri) {
        if (imagePath.toString() != "") {
            mSelectedImageBitmap = null
            mSelectedImageUri = imagePath
            Log.d(TAG, "getImagePath: got the image uri: $mSelectedImageUri")
            ImageLoader.getInstance().displayImage(imagePath.toString(), mProfileImage)
        }
    }

    override fun getImageBitmap(bitmap: Bitmap) {
        if (bitmap != null) {
            mSelectedImageUri = null
            mSelectedImageBitmap = bitmap
            Log.d(TAG, "getImageBitmap: got the image bitmap: $mSelectedImageBitmap")
            mProfileImage!!.setImageBitmap(bitmap)
        }
    }


    companion object {
        private const val TAG = "SettingsActivity"
        private const val DOMAIN_NAME = "tabian.ca"
        private const val REQUEST_CODE = 1234
        private const val MB_THRESHHOLD = 5.0
        private const val MB = 1000000.0

        // convert from bitmap to byte array
        fun getBytesFromBitmap(bitmap: Bitmap?, quality: Int): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            return stream.toByteArray()
        }
    }
}