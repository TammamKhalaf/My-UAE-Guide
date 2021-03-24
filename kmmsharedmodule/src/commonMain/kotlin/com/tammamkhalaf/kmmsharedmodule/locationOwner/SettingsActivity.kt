package com.tammamkhalaf.myuaeguide.locationOwner

import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity(), ChangePhotoDialog.OnPhotoReceivedListener {
    fun getImagePath(imagePath: android.net.Uri) {
        if (imagePath.toString() != "") {
            mSelectedImageBitmap = null
            mSelectedImageUri = imagePath
            android.util.Log.d(TAG, "getImagePath: got the image uri: $mSelectedImageUri")
            ImageLoader.getInstance().displayImage(imagePath.toString(), mProfileImage)
        }
    }

    fun getImageBitmap(bitmap: android.graphics.Bitmap?) {
        if (bitmap != null) {
            mSelectedImageUri = null
            mSelectedImageBitmap = bitmap
            android.util.Log.d(TAG, "getImageBitmap: got the image bitmap: $mSelectedImageBitmap")
            mProfileImage.setImageBitmap(bitmap)
        }
    }

    private var coordinatorLayout: CoordinatorLayout? = null

    //firebase
    private var mAuthListener: AuthStateListener? = null

    //widgets
    private var mPhone: TextInputEditText? = null
    private var mName: TextInputEditText? = null
    private var mEmail: TextInputEditText? = null
    private var mCurrentPassword: TextInputEditText? = null
    private var mProfileImage: android.widget.ImageView? = null
    private var mSave: android.widget.Button? = null
    private var mProgressBar: android.widget.ProgressBar? = null

    //vars
    private var mStoragePermissions = false
    private var mSelectedImageUri: android.net.Uri? = null
    private var mSelectedImageBitmap: android.graphics.Bitmap? = null
    private var mBytes: ByteArray
    private var progress = 0.0
    protected fun onCreate(@Nullable savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.settings_activity)
        android.util.Log.d(TAG, "onCreate: started.")
        mEmail = findViewById(R.id.input_email)
        mCurrentPassword = findViewById(R.id.input_password)
        mSave = findViewById(R.id.btn_save)
        mProgressBar = findViewById(R.id.progressBar)
        mName = findViewById(R.id.input_name)
        mPhone = findViewById(R.id.input_phone_edittext)
        mProfileImage = findViewById(R.id.profile_image)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        setupFirebaseAuth()
        hideSoftKeyboard()
    }

    private fun init() {
        getUserAccountData()
        mSave.setOnClickListener(android.view.View.OnClickListener { v: android.view.View? ->
            android.util.Log.d(TAG, "onClick: attempting to save settings.")

            //see if they changed the email
            if (!mEmail.getText().toString().equals("")) {
                //make sure email and current password fields are filled
                if (!isEmpty(mEmail.getText().toString()) && !isEmpty(mCurrentPassword.getText().toString())) {
                } else {
                    android.widget.Toast.makeText(this@SettingsActivity, "Email and Current Password Fields Must be Filled to Save", android.widget.Toast.LENGTH_SHORT).show()
                }
            }


            /*
            ------ METHOD 1 for changing database data (proper way in this scenario) -----
             */
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            /*
            ------ Change Name -----
             */if (!mName.getText().toString().equals("")) {
            reference.child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                    .child("fullName")
                    .setValue(mName.getText().toString())
            val snackbar: Snackbar = Snackbar.make(coordinatorLayout, "All Done", Snackbar.LENGTH_LONG)
                    .setAction("Ok") { view -> }
            snackbar.show()
        }
            if (!mEmail.getText().toString().equals("")) {
                reference.child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("email")
                        .setValue(mEmail.getText().toString())
                val snackbar: Snackbar = Snackbar.make(coordinatorLayout, "All Done", Snackbar.LENGTH_LONG)
                        .setAction("Ok") { view -> }
                snackbar.show()
            }


            /*
            ------ Change Phone Number -----
             */if (!mPhone.getText().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())) {
            val snackbar: Snackbar = Snackbar.make(coordinatorLayout, "Sorry ! Not Available  Right Now", Snackbar.LENGTH_LONG)
                    .setAction("Ok") { view ->
                        val snackbar1: Snackbar = Snackbar.make(coordinatorLayout, "Thanks", Snackbar.LENGTH_SHORT)
                        snackbar1.show()
                    }
            snackbar.show()
            android.util.Log.d(TAG, "Change phone Number !")

            //                reference.child("Users")
            //                        .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
            //                        .child("phoneNo")
            //                        .setValue(mPhone.getText().toString());
        }

            /*
            ------ Upload the New Photo -----
             */if (mSelectedImageUri != null) {
            uploadNewPhoto(mSelectedImageUri)
        } else if (mSelectedImageBitmap != null) {
            uploadNewPhoto(mSelectedImageBitmap)
        }
            android.widget.Toast.makeText(this@SettingsActivity, "saved", android.widget.Toast.LENGTH_SHORT).show()
        })
        mProfileImage.setOnClickListener(android.view.View.OnClickListener { view: android.view.View? ->
            if (mStoragePermissions) {
                val dialog = ChangePhotoDialog()
                dialog.show(getSupportFragmentManager(), "change photo")
            } else {
                verifyStoragePermissions()
            }
        })
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageUri***
     *
     * @param imageUri
     */
    fun uploadNewPhoto(imageUri: android.net.Uri?) {
        /*
            upload a new profile photo to firebase storage
         */
        android.util.Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firebase storage.")

        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        val resize: BackgroundImageResize = BackgroundImageResize(null)
        resize.execute(imageUri)
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageBitmap***
     *
     * @param imageBitmap
     */
    fun uploadNewPhoto(imageBitmap: android.graphics.Bitmap?) {
        /*
            upload a new profile photo to firebase storage
         */
        android.util.Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firebase storage.")

        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        val resize: BackgroundImageResize = BackgroundImageResize(imageBitmap)
        val uri: android.net.Uri? = null
        resize.execute(uri)
    }

    /**
     * 1) doinBackground takes an imageUri and returns the byte array after compression
     * 2) onPostExecute will print the % compression to the log once finished
     */
    inner class BackgroundImageResize(bm: android.graphics.Bitmap?) : android.os.AsyncTask<android.net.Uri?, Int?, ByteArray?>() {
        var mBitmap: android.graphics.Bitmap? = null
        protected override fun onPreExecute() {
            super.onPreExecute()
            showDialog()
            android.widget.Toast.makeText(this@SettingsActivity, "compressing image", android.widget.Toast.LENGTH_SHORT).show()
        }

        protected override fun doInBackground(vararg params: android.net.Uri): ByteArray {
            android.util.Log.d(TAG, "doInBackground: started.")
            if (mBitmap == null) {
                try {
                    mBitmap = android.provider.MediaStore.Images.Media.getBitmap(this@SettingsActivity.getContentResolver(), params[0])
                    android.util.Log.d(TAG, "doInBackground: bitmap size: megabytes: " + mBitmap.getByteCount() / MB + " MB")
                } catch (e: java.io.IOException) {
                    android.util.Log.e(TAG, "doInBackground: IOException: ", e.cause)
                }
            }
            var bytes: ByteArray? = null
            for (i in 1..10) {
                if (i == 10) {
                    android.widget.Toast.makeText(this@SettingsActivity, "That image is too large.", android.widget.Toast.LENGTH_SHORT).show()
                    break
                }
                bytes = getBytesFromBitmap(mBitmap, 100 / i)
                android.util.Log.d(TAG, "doInBackground: megabytes: (" + (11 - i) + "0%) " + bytes.size / MB + " MB")
                if (bytes.size / MB < MB_THRESHHOLD) {
                    return bytes
                }
            }
            return bytes!!
        }

        protected override fun onPostExecute(bytes: ByteArray) {
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
        val storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(filePaths.FIREBASE_IMAGE_STORAGE.toString() + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                        + "/profile_image") //just replace the old image with the new one
        if (mBytes.size / MB < MB_THRESHHOLD) {

            // Create file metadata including the content type
            val metadata: StorageMetadata = Builder()
                    .setContentType("image/jpg")
                    .setContentLanguage("en") //see nodes below
                    /*
                    Make sure to use proper language code ("English" will cause a crash)
                    I actually submitted this as a bug to the Firebase github page so it might be
                    fixed by the time you watch this video. You can check it out at https://github.com/firebase/quickstart-unity/issues/116
                     */
                    .setCustomMetadata("tammam khalaf's special meta data", "JK nothing special here")
                    .setCustomMetadata("location", "UAE")
                    .build()
            //if the image size is valid then we can submit to database
            var uploadTask: UploadTask? = null
            uploadTask = storageReference.putBytes(mBytes, metadata)
            //uploadTask = storageReference.putBytes(mBytes); //without metadata
            uploadTask.addOnSuccessListener { taskSnapshot ->
                //Now insert the download url into the firebase database
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        val result: Task<android.net.Uri> = taskSnapshot.getStorage().getDownloadUrl()
                        result.addOnSuccessListener { uri ->
                            android.widget.Toast.makeText(this@SettingsActivity, "Upload Success", android.widget.Toast.LENGTH_SHORT).show()
                            android.util.Log.d(TAG, "onSuccess: firebase download url : " + uri.toString())
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                    .child("profile_image")
                                    .setValue(uri.toString())
                            hideDialog()
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                android.widget.Toast.makeText(this@SettingsActivity, "could not upload photo", android.widget.Toast.LENGTH_SHORT).show()
                hideDialog()
            }.addOnProgressListener { taskSnapshot ->
                val currentProgress: Double = 100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()
                if (currentProgress > progress + 15) {
                    progress = 100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()
                    android.util.Log.d(TAG, "onProgress: Upload is $progress% done")
                    android.widget.Toast.makeText(this@SettingsActivity, "$progress%", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            android.widget.Toast.makeText(this, "Image is too Large", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserAccountData() {
        android.util.Log.d(TAG, "getUserAccountData: getting the user's account information")
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        /*
            ---------- QUERY Method 1 ----------
         */
        val query1: Query = reference.child("Users")
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
        query1.addListenerForSingleValueEvent(object : ValueEventListener() {
            fun onDataChange(dataSnapshot: DataSnapshot) {

                //this loop will return a single result
                for (singleSnapshot in dataSnapshot.getChildren()) {
                    android.util.Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: " + singleSnapshot.getValue(User::class.java).toString())
                    val user: User = singleSnapshot.getValue(User::class.java)
                    mName.setText(user.getFullName())
                    mPhone.setText(user.getPhoneNo())
                    mEmail.setText(user.getEmail())
                    ImageLoader.getInstance().displayImage(user.getProfile_image(), mProfileImage)
                }
            }

            fun onCancelled(databaseError: DatabaseError?) {}
        })


        /*
            ---------- QUERY Method 2 ----------
         */
    }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     */
    fun verifyStoragePermissions() {
        android.util.Log.d(TAG, "verifyPermissions: asking user for permissions.")
        val permissions = arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        permissions[0]) === android.content.pm.PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        permissions[1]) === android.content.pm.PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        permissions[2]) === android.content.pm.PackageManager.PERMISSION_GRANTED) {
            mStoragePermissions = true
        } else {
            ActivityCompat.requestPermissions(
                    this@SettingsActivity,
                    permissions,
                    REQUEST_CODE
            )
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        android.util.Log.d(TAG, "onRequestPermissionsResult: requestCode: $requestCode")
        when (requestCode) {
            REQUEST_CODE -> if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[0])
            }
        }
    }

    private fun showDialog() {
        mProgressBar.setVisibility(android.view.View.VISIBLE)
    }

    private fun hideDialog() {
        if (mProgressBar.getVisibility() == android.view.View.VISIBLE) {
            mProgressBar.setVisibility(android.view.View.INVISIBLE)
        }
    }

    private fun hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private fun isEmpty(string: String): Boolean {
        return string == ""
    }

    protected fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        android.util.Log.d(TAG, "checkAuthenticationState: checking authentication state.")
        val user: FirebaseUser = FirebaseAuth.getInstance().getCurrentUser()
        if (user == null) {
            android.util.Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.")
            val intent: android.content.Intent = android.content.Intent(this@SettingsActivity, PhoneAuthActivity::class.java)
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        } else {
            android.util.Log.d(TAG, "checkAuthenticationState: user is authenticated.")
        }
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
         */
    private fun setupFirebaseAuth() {
        android.util.Log.d(TAG, "setupFirebaseAuth: started.")
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user: FirebaseUser = firebaseAuth.getCurrentUser()
            if (user != null) {
                // User is signed in
                android.util.Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid())
                //toastMessage("Successfully signed in with: " + user.getEmail());
                verifyStoragePermissions()
                init()
            } else {
                // User is signed out
                android.util.Log.d(TAG, "onAuthStateChanged:signed_out")
                android.widget.Toast.makeText(this@SettingsActivity, "Signed out", android.widget.Toast.LENGTH_SHORT).show()
                val intent: android.content.Intent = android.content.Intent(this@SettingsActivity, PhoneAuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun onBackPressed() {
        super.onBackPressed()
    }

    fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener)
    }

    fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener)
        }
    }

    companion object {
        private const val TAG = "SettingsActivity"
        private const val REQUEST_CODE = 1234
        private const val MB_THRESHHOLD = 5.0
        private const val MB = 1000000.0

        // convert from bitmap to byte array
        fun getBytesFromBitmap(bitmap: android.graphics.Bitmap?, quality: Int): ByteArray {
            val stream: java.io.ByteArrayOutputStream = java.io.ByteArrayOutputStream()
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, quality, stream)
            return stream.toByteArray()
        }
    }
}